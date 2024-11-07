package dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import run.ClientRun;

public class ClientHander {
     
    Socket s;
    DataInputStream dis;
    DataOutputStream dos;

    public String loginUser = null; // lưu tài khoản đăng nhập hiện tại
    public String nameRooom = null;
    public String host = null;
    float score = 0;
    
    Thread listener = null;

    public String connect(String addr, int port) {
        try {
            // getting ip 
            InetAddress ip = InetAddress.getByName(addr);

            // establish the connection with server port 
            s = new Socket();
            s.connect(new InetSocketAddress(ip, port), 4000);
            System.out.println("Connected to " + ip + ":" + port + ", localport:" + s.getLocalPort());

            // obtaining input and output streams
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

            // close old listener
            if (listener != null && listener.isAlive()) {
                listener.interrupt();
            }

            // listen to server
            listener = new Thread(this::listen);
            listener.start();

            // connect success
            return "success";

        } catch (IOException e) {

            // connect failed
            return "failed;" + e.getMessage();
        }
    }

    private void listen() {
        boolean running = true;

        while (running) {
            try {
                // receive the data from server
                String received = dis.readUTF();

                System.out.println("RECEIVED: " + received);

                String type = received.split(";")[0];

                switch (type) {
                    case "LOGIN":
                        onReceiveLogin(received);
                        break;
                    case "REGISTER":
                        onReceiveRegister(received);
                        break;
                    case "GET_LIST_ONLINE":
                        onReceiveGetListOnline(received);
                        break;
                    case "GET_SCORE":
                        onGetScore(received);
                        break;
                    case "LOGOUT":
                        onReceiveLogout(received);
                        break;
                    case "INVITE_TO_CHAT":
                        onReceiveInviteToChat(received);
                        break;
                    case "GET_INFO_USER":
                        onReceiveGetInfoUser(received);
                        break;
                    case "ACCEPT_MESSAGE":
                        onReceiveAcceptMessage(received);
                        break;
                    case "NOT_ACCEPT_MESSAGE":
                        onReceiveNotAcceptMessage(received);
                        break;
                    case "LEAVE_TO_CHAT":
                        onReceiveLeaveChat(received);
                        break;
                    case "CHAT_MESSAGE":
                        onReceiveChatMessage(received);
                        break;
                        
                        
                    ////JOIN,CREATE,Update ROOM
                    case "GET_ROOM_LIST_SUCCESS":
                        onGetRoomListSucces(received);
                        break;
                    case "ROOM_CREATED_SUCCESSFULLY":
                        onRoomCreateSuccess(received);
                        break;    
                    case "JOIN_ROOM_SUCCESS":
                        onJoinRoomSuccess(received);
                        break;    
                    case "UPDATE_PLAYER_LIST":
                        onUpdatePlayerList(received);
                        break;
                    case "ROOM_CANCELLED":
                        onRoomCancelled(received);
                        break;
                    case "LEAVE_ROOM_SUCCESS":
                        onLeaveRoomSuccess(received);
                        break;
                        
                    //game
                    case "START_ROOM_GAME":
                        onStartRoomGame(received);
                        break;    
                    case "RESULT_ROOM_GAME":
                        onReceiveResultRoomGame(received);
                        break;    
                    case "EXIT":
                        running = false;
                }

            } catch (IOException ex) {
                Logger.getLogger(ClientHander.class.getName()).log(Level.SEVERE, null, ex);
                running = false;
            }
        }

        try {
            // closing resources
            s.close();
            dis.close();
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHander.class.getName()).log(Level.SEVERE, null, ex);
        }
        // alert if connect interup
        JOptionPane.showMessageDialog(null, "Mất kết nối tới server", "Lỗi", JOptionPane.ERROR_MESSAGE);
        ClientRun.closeAllScene();
        ClientRun.openScene(ClientRun.SceneName.CONNECTSERVER);
    }
    
    /***
     * Handle from client
     */
    public void login(String email, String password) {
        // prepare data
        String data = "LOGIN" + ";" + email + ";" + password;
        // send data
        sendData(data);
    }
    
    public void register(String email, String password) {
        // prepare data
        String data = "REGISTER" + ";" + email + ";" + password;
        // send data
        sendData(data);
    }
    
    public void logout () {
        this.loginUser = null;
        sendData("LOGOUT");
    }
    
    public void close () {
        sendData("CLOSE");
    }
    
    public void getListOnline() {
        sendData("GET_LIST_ONLINE");
    }
    
    public void getInfoUser(String username) {
        sendData("GET_INFO_USER;" + username);
    }
    
    public void checkStatusUser(String username) {
        sendData("CHECK_STATUS_USER;" + username);
    }
    
    // Chat
    public void inviteToChat (String userInvited) {
        sendData("INVITE_TO_CHAT;" + loginUser + ";" + userInvited);
    }
    
    public void leaveChat (String userInvited) {
        sendData("LEAVE_TO_CHAT;" + loginUser + ";" + userInvited);
    }
    
    public void sendMessage (String userInvited, String message) {
        String chat = "[" + loginUser + "] : " + message + "\n";
        ClientRun.messageView.setContentChat(chat);
            
        sendData("CHAT_MESSAGE;" + loginUser + ";" + userInvited  + ";" + message);
    }
    
    /// room
    public void joinRoom(String roomName){
        sendData("JOIN_ROOM;" + loginUser +";"+ roomName);
    }
    public void createRoom(String name,int size){
        sendData("CREATE_ROOM;" + loginUser + ";" + name + ";" + size);
    }
    
    /// game
    public void startRoomGame (String players) { 
        sendData("START_ROOM_GAME;" + loginUser + ";" + nameRooom + ";" + players);
    }
    public void submitResult (String players, String formattedTime) { 
        String result1 = ClientRun.roomGameView.getSelectedButton1();
        String result2 = ClientRun.roomGameView.getSelectedButton2();
        String result3 = ClientRun.roomGameView.getSelectedButton3();
        System.out.println(result1 + ";" + result2 + ";" + result3 + ";" + formattedTime);
        if (result1 == null || result2 == null || result3 == null) {
            ClientRun.roomGameView.showMessage("Don't leave blank!");
        } else {
            ClientRun.roomGameView.pauseTime();
            // Handle calculate time
            String[] splitted = ClientRun.roomGameView.pbgTimer.getString().split(":");
            String countDownTime = splitted[1];
            int time = 30 - Integer.parseInt(countDownTime);
            System.out.println(time);
            String data = ClientRun.roomGameView.getQuestion1()+ "," + ClientRun.roomGameView.getCorrectAnswer1()+ "," + result1 + ","
                        + ClientRun.roomGameView.getQuestion2() + "," + ClientRun.roomGameView.getCorrectAnswer2() + "," + result2 + ","
                        + ClientRun.roomGameView.getQuestion3() + "," + ClientRun.roomGameView.getCorrectAnswer3() + "," + result3 + ","
                        + time;
            sendData("SUBMIT_ROOM_RESULT;" + loginUser + ";" + nameRooom + ";" + players + ";" + data);
            System.out.println("SUBMIT_ROOM_RESULT;" + loginUser + ";" + nameRooom + ";" + players + ";" + data);
            ClientRun.roomGameView.afterSubmit();
        }
    }
    private void onReceiveResultRoomGame(String received) {
        System.out.println("Received data: " + received);

        String[] splitted = received.split(";");
        String status = splitted[1];
        String leaderboardData = splitted[2]; // Chuỗi chứa dữ liệu xếp hạng
        String[] listPlayer = splitted[3].split(";");
        String nameRoom = splitted[4];

        if (status.equals("success")) {
            // Chuẩn bị hiển thị bảng xếp hạng
            StringBuilder leaderboardMessage = new StringBuilder("BXH:\n\n");

            // Tách từng mục trong bảng xếp hạng
            String[] rankings = leaderboardData.split(",");
            for (String rank : rankings) {
                leaderboardMessage.append(rank).append("\n");
            }

            // Hiển thị bảng xếp hạng trong hộp thoại
            JOptionPane.showMessageDialog(null, leaderboardMessage.toString(), "BXH", JOptionPane.INFORMATION_MESSAGE);

            // Tự động chuyển về home view sau khi hiển thị bảng xếp hạng
            // ClientRun.closeAllScene();
            // ClientRun.socketHandler.joinRoom(nameRoom);
        } else {
            System.out.println("Failed to process game result.");
        }
}


    
    /***
     * Handle send data to server
     */
    public void sendData(String data) {
        try {
            dos.writeUTF(data);
        } catch (IOException ex) {
            Logger.getLogger(ClientHander.class
                .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
    
    
    /***
     * LOGIN, REGISTER,CHAT,GETINFO
     */
    private void onReceiveLogin(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("failed")) {
            // hiển thị lỗi
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(ClientRun.loginView, failedMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            // lưu user login
            this.loginUser = splitted[2];
            this.score = Float.parseFloat(splitted[3]) ;
            // chuyển scene
            ClientRun.closeScene(ClientRun.SceneName.LOGIN);
            ClientRun.openScene(ClientRun.SceneName.HOMEVIEW);

            // auto set info user
            ClientRun.homeView.setUsername(loginUser);
            ClientRun.homeView.setUserScore(score);
        }
    }
    
    private void onGetScore(String received){
        String[] splitted = received.split(";");
        float diem = Float.parseFloat(splitted[3]);
        ClientRun.homeView.setUserScore(diem);
    }
  
    private void onReceiveRegister(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("failed")) {
            // hiển thị lỗi
            String failedMsg = splitted[2];
            JOptionPane.showMessageDialog(ClientRun.registerView, failedMsg, "Lỗi", JOptionPane.ERROR_MESSAGE);

        } else if (status.equals("success")) {
            JOptionPane.showMessageDialog(ClientRun.registerView, "Register account successfully! Please login!");
            // chuyển scene
            ClientRun.closeScene(ClientRun.SceneName.REGISTER);
            ClientRun.openScene(ClientRun.SceneName.LOGIN);
        }
    }
    
    private void onReceiveGetListOnline(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            int userCount = Integer.parseInt(splitted[2]);

            // https://niithanoi.edu.vn/huong-dan-thao-tac-voi-jtable-lap-trinh-java-swing.html
            Vector vheader = new Vector();
            vheader.add("Danh sách người đang hoạt động");

            Vector vdata = new Vector();
            if (userCount > 1) {
                for (int i = 3; i < userCount + 3; i++) {
                    String user = splitted[i];
                    if (!user.equals(loginUser) && !user.equals("null")) {
                        Vector vrow = new Vector();
                        vrow.add(user);
                        vdata.add(vrow);
                    }
                }

                ClientRun.homeView.setListUser(vdata, vheader);
            } else {
                ClientRun.homeView.resetTblUser();
            }
            
        } else {
            JOptionPane.showMessageDialog(ClientRun.loginView, "Have some error!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void onReceiveGetInfoUser(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String userName = splitted[2];
            String userScore =  splitted[3];
            String userWin =  splitted[4];
            String userDraw =  splitted[5];
            String userLose =  splitted[6];
            String userAvgCompetitor =  splitted[7];
            String userAvgTime =  splitted[8];
            String userStatus = splitted[9];
            
            ClientRun.openScene(ClientRun.SceneName.INFOPLAYER);
            ClientRun.infoPlayerView.setInfoUser(userName, userScore, userWin, userDraw, userLose, userAvgCompetitor, userAvgTime, userStatus);
        }
    }
    
    private void onReceiveLogout(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            ClientRun.closeScene(ClientRun.SceneName.HOMEVIEW);
            ClientRun.openScene(ClientRun.SceneName.LOGIN);
        }
    }
    
    // chat
    private void onReceiveInviteToChat(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String userHost = splitted[2];
            String userInvited = splitted[3];
            if (JOptionPane.showConfirmDialog(ClientRun.homeView, userHost + " want to chat with you?", "Chat?", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
                ClientRun.openScene(ClientRun.SceneName.MESSAGEVIEW);
                ClientRun.messageView.setInfoUserChat(userHost);
                sendData("ACCEPT_MESSAGE;" + userHost + ";" + userInvited);
            } else {
                sendData("NOT_ACCEPT_MESSAGE;" + userHost + ";" + userInvited);
            }
        }
    }
    
    private void onReceiveAcceptMessage(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String userHost = splitted[2];
            String userInvited = splitted[3];
                
            ClientRun.openScene(ClientRun.SceneName.MESSAGEVIEW);
            ClientRun.messageView.setInfoUserChat(userInvited);
        }
    }
    
    private void onReceiveNotAcceptMessage(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String userHost = splitted[2];
            String userInvited = splitted[3];
                
            JOptionPane.showMessageDialog(ClientRun.homeView, userInvited + " don't want to chat with you!");
        }
    }
    
    private void onReceiveLeaveChat(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String userHost = splitted[2];
            String userInvited = splitted[3];
            
            ClientRun.closeScene(ClientRun.SceneName.MESSAGEVIEW);   
            JOptionPane.showMessageDialog(ClientRun.homeView, userHost + " leave to chat!");
        }
    }
    
    private void onReceiveChatMessage(String received) {
        // get status from data
        String[] splitted = received.split(";");
        String status = splitted[1];

        if (status.equals("success")) {
            String userHost = splitted[2];
            String userInvited = splitted[3];
            String message = splitted[4];
            
            String chat = "[" + userHost + "] : " + message + "\n";
            ClientRun.messageView.setContentChat(chat);
        }
    }
    
    
    
    
    
    
    ///////////////JOIN,CREATE ROOM
    
    private void onGetRoomListSucces(String received){
        String[] splitted = received.split(";");
        String status = splitted[0];
        
        ClientRun.openScene(ClientRun.SceneName.JOINROOMVIEW);
        ClientRun.closeScene(ClientRun.SceneName.HOMEVIEW);
        if (status.equals("GET_ROOM_LIST_SUCCESS")) {    
            int x = 1;
            Vector vheader = new Vector<>();
            vheader.add("Stt");
            vheader.add("Room");
            vheader.add("Max Players");
            vheader.add("Available Slots");

            // Tạo dữ liệu cho bảng
            Vector vdata = new Vector<>();

            // Bắt đầu từ phần tử thứ 1 trở đi, mỗi phần tử chứa thông tin phòng dạng roomName:maxPlayers:availableSlots
            for (int i = 1; i < splitted.length; i++) {
                String[] roomInfo = splitted[i].split(":");
                String roomName = roomInfo[0];  // Tên phòng
                String maxPlayers = roomInfo[1];  // Số người tối đa
                String availableSlots = roomInfo[2];  // Số chỗ còn trống

                // Tạo một hàng dữ liệu mới
                Vector vrow = new Vector<>();
                vrow.add(Integer.toString(x)); x++;
                vrow.add(roomName);
                vrow.add(maxPlayers);
                vrow.add(availableSlots);

                vdata.add(vrow);  // Thêm hàng vào dữ liệu
            }
            ClientRun.joinRoomView.showTable();
            ClientRun.joinRoomView.resetTblRoom();
            if(splitted.length>1){
                ClientRun.joinRoomView.setListRoom(vdata, vheader);   
                
            }
            else{
                ClientRun.joinRoomView.resetTblRoom();
            }
            
        } else {
            JOptionPane.showMessageDialog(ClientRun.loginView, "Có lỗi xảy ra!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void onRoomCreateSuccess(String received){
        System.out.println(received+"hello toi da nhan dcuoc");
        String[] splitted = received.split(";");
        String status = splitted[1];
        String listPlayer[] = splitted[5].split(",");
        if (status.equals("success")) {
            String userHost = splitted[2];
            int slot = Integer.parseInt(splitted[3]);
            nameRooom = splitted[4];
            ClientRun.openScene(ClientRun.SceneName.ROOMGAMEVIEW);
            ClientRun.roomGameView.updateRoomInfo(nameRooom, slot,listPlayer);
            ClientRun.roomGameView.setStateHostRoom();
            ClientRun.closeScene(ClientRun.SceneName.JOINROOMVIEW);
        }
    }
    
    private void onJoinRoomSuccess(String received){
        String[] parts = received.split(";");
        String command = parts[0];

        if (command.equals("JOIN_ROOM_SUCCESS")) {
            String roomName = parts[1];
            String slot = parts[2];
            String playerList[] = parts[3].split(",");
            ClientRun.openScene(ClientRun.SceneName.ROOMGAMEVIEW);
            ClientRun.roomGameView.updateRoomInfo(roomName, Integer.parseInt(slot),playerList);
            ClientRun.closeScene(ClientRun.SceneName.JOINROOMVIEW);
        } else {
            String errorMessage = parts[1];
            JOptionPane.showMessageDialog(null, "Failed to join room: " + errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void onUpdatePlayerList(String received){
        System.out.println(received+" update list player");
        String[] parts = received.split(";");
        String command = parts[0];

        String roomName = parts[1];
        String slot = parts[2];
        String playerList[] = parts[3].split(",");
        ClientRun.roomGameView.updateRoomInfo(roomName, Integer.parseInt(slot),playerList);
    }
    private  void onRoomCancelled(String received){
        // Tách dữ liệu nhận được
        String[] data = received.split(";");
        String roomName = data[1];  // Lấy tên phòng từ dữ liệu nhận được

        // Hiển thị thông báo cho người chơi biết rằng phòng đã bị hủy
        JOptionPane.showMessageDialog(null, 
                                      "Phòng " + roomName + " đã bị hủy bởi chủ phòng.", 
                                      "Phòng bị hủy", 
                                      JOptionPane.INFORMATION_MESSAGE);

        // Đóng giao diện phòng hiện tại (RoomGameView)
        ClientRun.closeAllScene();

        // Chuyển người chơi về giao diện HomeView
        ClientRun.openScene(ClientRun.SceneName.HOMEVIEW);

        // Cập nhật giao diện chính với thông tin người chơi hiện tại
        ClientRun.homeView.setUsername(ClientRun.clientHander.getLoginUser());
        ClientRun.homeView.setUserScore(ClientRun.clientHander.getScore());
        ClientRun.clientHander.getListOnline();
    }
    private void onLeaveRoomSuccess(String received){
        // Đóng giao diện phòng hiện tại (RoomGameView)
        ClientRun.closeAllScene();

        // Chuyển người chơi về giao diện HomeView
        ClientRun.openScene(ClientRun.SceneName.HOMEVIEW);

        // Cập nhật giao diện chính với thông tin người chơi hiện tại
        ClientRun.homeView.setUsername(ClientRun.clientHander.getLoginUser());
        ClientRun.homeView.setUserScore(ClientRun.clientHander.getScore());
        ClientRun.clientHander.getListOnline();
    }
    
    //////game
    private void onStartRoomGame(String received) {
        // get status from data
        System.out.println(received);
        String[] splitted = received.split(";");
        String status = splitted[1];
        System.out.println(splitted.length);
        
        if (status.equals("success")) {
            String a1 = splitted[3];
            String b1 = splitted[4];
            String answer1a = splitted[5];
            String answer1b = splitted[6];
            String answer1c = splitted[7];
            String answer1d = splitted[8];
            ClientRun.roomGameView.setQuestion1(a1, b1, answer1a, answer1b, answer1c, answer1d);
            
            String a2 = splitted[9];
            String b2 = splitted[10];
            String answer2a = splitted[11];
            String answer2b = splitted[12];
            String answer2c = splitted[13];
            String answer2d = splitted[14];
            ClientRun.roomGameView.setQuestion2(a2, b2, answer2a, answer2b, answer2c, answer2d);
            
            String a3 = splitted[15];
            String b3 = splitted[16];
            String answer3a = splitted[17];
            String answer3b = splitted[18];
            String answer3c = splitted[19];
            String answer3d = splitted[20];
            ClientRun.roomGameView.setQuestion3(a3, b3, answer3a, answer3b, answer3c, answer3d);
            
            
            ClientRun.roomGameView.setStartGame(30);
        }
    }
    
    
    
    
    
    
    
     
    // get set
    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }

    public String getNameRooom() {
        return nameRooom;
    }

    public void setNameRooom(String nameRooom) {
        this.nameRooom = nameRooom;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
    
    

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

}
