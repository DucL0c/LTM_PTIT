/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Question;
import run.ServerRun;

/**
 *
 * @author admin
 */
public class ServerHandle implements Runnable {
    Socket s;
    DataInputStream dis;
    DataOutputStream dos;

    String loginUser;
    String result;
    ServerHandle cCompetitor;
   

    public ServerHandle(Socket s) throws IOException {
        this.s = s;

        // obtaining input and output streams 
        this.dis = new DataInputStream(s.getInputStream());
        this.dos = new DataOutputStream(s.getOutputStream());
    }

    @Override
    public void run() {

        String received;
        boolean running = true;

        while (!ServerRun.isShutDown) {
            try {
                // receive the request from client
                received = dis.readUTF();

                System.out.println(received);
                String type = received.split(";")[0];
               
                switch (type) {
                    case "LOGIN":
                        onReceiveLogin(received);
                        break;
                    case "REGISTER":
                        onReceiveRegister(received);
                        break;
                    case "GET_LIST_ONLINE":
                        onReceiveGetListOnline();
                        break;
                    case "GET_SCORE":
                        onGetScore();
                        break;
                    case "GET_INFO_USER":
                        onReceiveGetInfoUser(received);
                        break;
                    case "LOGOUT":
                        onReceiveLogout();
                        break;  
                    case "CLOSE":
                        onReceiveClose();
                        break; 
                    // chat
                    case "INVITE_TO_CHAT":
                        onReceiveInviteToChat(received);
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
                        
                    //////join,create,leave room
                    case "GET_ROOM_LIST":
                        onGetRoomList(received);
                        break;
                    case "CREATE_ROOM":
                        onCreateRoom(received);
                        break;    
                    case "JOIN_ROOM":
                        onJoinRoom(received);
                        break;
                    case "LEAVE_ROOM":
                        onLeaveRoom(received);
                        break;
                    case "ROOM_CANCELLED":
                        onRooomCancel(received);
                        break;
                        
                    ////game
                    case "START_ROOM_GAME":
                        onStartRoomGame(received);
                        break;
                    case "SUBMIT_ROOM_RESULT":
                        onReceiveSubmitRResult(received);
                        break;
                    case "EXIT":
                        running = false;
                }

            } catch (IOException ex) {
                break;
            } catch (SQLException ex) {
                Logger.getLogger(ServerHandle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            // closing resources 
            this.s.close();
            this.dis.close();
            this.dos.close();
            System.out.println("- Client disconnected: " + s);

            // remove from manager
            ServerRun.manager.remove(this);

        } catch (IOException ex) {
            Logger.getLogger(ServerHandle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // send data fucntions
    public String sendData(String data) {
        try {
            this.dos.writeUTF(data);
            return "success";
        } catch (IOException e) {
            System.err.println("Send data failed!");
            return "failed;" + e.getMessage();
        }
    }
    private void onReceiveLogin(String received) {
        // get email / password from data
        String[] splitted = received.split(";");
        String username = splitted[1];
        String password = splitted[2];


        // check login
        String result = new UserController().login(username, password);

        if (result.split(";")[0].equals("success")) {
            // set login user
            this.loginUser = username;
        }
        
        // send result
        sendData("LOGIN" + ";" + result);
        onReceiveGetListOnline();
    }
    
    private void onGetScore() {
        String result = new UserController().getInfoUser(this.loginUser);
        sendData("GET_INFO_USER;"+result);
    }
    
    private void onReceiveRegister(String received) {
        // get email / password from data
        String[] splitted = received.split(";");
        String username = splitted[1];
        String password = splitted[2];

        // reigster
        String result = new UserController().register(username, password);

        // send result
        sendData("REGISTER" + ";" + result);
    }
    
    private void onReceiveGetListOnline() {
        String result = ServerRun.manager.getListUseOnline();
        
        // send result
        String msg = "GET_LIST_ONLINE" + ";" + result;
        ServerRun.manager.broadcast(msg);
    }
    
    private void onReceiveGetInfoUser(String received) {
        String[] splitted = received.split(";");
        String username = splitted[1];
        // get info user
        String result = new UserController().getInfoUser(username);
        
        String status = "";
        ServerHandle c = ServerRun.manager.find(username);
        if (c == null) {
            status = "Offline";
        } else {
            status = "In Game";
        }
                
        // send result
        sendData("GET_INFO_USER" + ";" + result + ";" + status);
    }
    
    private void onReceiveLogout() {
        this.loginUser = null;
        // send result
        sendData("LOGOUT" + ";" + "success");
        onReceiveGetListOnline();
    }
    
    private void onReceiveInviteToChat(String received) {
        String[] splitted = received.split(";");
        String userHost = splitted[1];
        String userInvited = splitted[2];
        
        // send result
        String msg = "INVITE_TO_CHAT;" + "success;" + userHost + ";" + userInvited;
        ServerRun.manager.sendToAClient(userInvited, msg);
    }
    
    private void onReceiveAcceptMessage(String received) {
        String[] splitted = received.split(";");
        String userHost = splitted[1];
        String userInvited = splitted[2];
        
        // send result
        String msg = "ACCEPT_MESSAGE;" + "success;" + userHost + ";" + userInvited;
        ServerRun.manager.sendToAClient(userHost, msg);
    }      
      
    private void onReceiveNotAcceptMessage(String received) {
        String[] splitted = received.split(";");
        String userHost = splitted[1];
        String userInvited = splitted[2];
        
        // send result
        String msg = "NOT_ACCEPT_MESSAGE;" + "success;" + userHost + ";" + userInvited;
        ServerRun.manager.sendToAClient(userHost, msg);
    }      
    
    private void onReceiveLeaveChat(String received) {
        String[] splitted = received.split(";");
        String userHost = splitted[1];
        String userInvited = splitted[2];
        
        // send result
        String msg = "LEAVE_TO_CHAT;" + "success;" + userHost + ";" + userInvited;
        ServerRun.manager.sendToAClient(userInvited, msg);
    }      
    
    private void onReceiveChatMessage(String received) {
        String[] splitted = received.split(";");
        String userHost = splitted[1];
        String userInvited = splitted[2];
        String message = splitted[3];
        
        // send result
        String msg = "CHAT_MESSAGE;" + "success;" + userHost + ";" + userInvited + ";" + message;
        ServerRun.manager.sendToAClient(userInvited, msg);
    }     
    
    // Close app
    private void onReceiveClose() {
        this.loginUser = null;
        ServerRun.manager.remove(this);
        onReceiveGetListOnline();
    }
    
    
    
    //////////JOIN, CREATE ROOM
    
    private void onGetRoomList(String received){
        String[] parts = received.split(";");
        String command = parts[0];

        if (command.equals("GET_ROOM_LIST")) {
            List<Room> rooms = Optional.ofNullable(ServerRun.roomManager.getRoomList())
                           .orElse(new ArrayList<>());
            StringBuilder roomListMessage = new StringBuilder("GET_ROOM_LIST_SUCCESS");

            // Duyệt qua danh sách các phòng và thêm thông tin vào thông điệp gửi đi
            for (Room room : rooms) {
                roomListMessage.append(";")
                    .append(room.getRoomName()).append(":")
                    .append(room.getMaxPlayers()).append(":")
                    .append(room.getAvailableSlots());
            }

            // Gửi danh sách phòng về cho client
            sendData(roomListMessage.toString());
            System.out.println(roomListMessage.toString());
        }
    }
    private void onCreateRoom(String received) {
        System.out.println(received);
        String[] parts = received.split(";");
        String host = parts[1];  // Người dùng hiện tại
        String roomName = parts[2];   // Tên phòng
        int size = Integer.parseInt(parts[3]);  // Kích thước phòng

        // Tạo phòng mới thông qua manager
        if (ServerRun.roomManager.isRoomExists(roomName)) {
            sendData("ROOM_CREATION_FAILED;Room already exists.");
        } else {
            Room room = ServerRun.roomManager.createRoom(roomName, size,host);
            room.addPlayer(host);
            
            List<String> playersInRoom = room.getPlayers();
            String playerList = String.join(",", playersInRoom);
            
            sendData("ROOM_CREATED_SUCCESSFULLY;" + "success;" + host +";" + size+";" + room.getRoomName()+";"+playerList);
        }
    }
    private void onJoinRoom(String received) throws IOException {
        String[] parts = received.split(";");
        String command = parts[0];

        if (command.equals("JOIN_ROOM")) {
            String loginUser = parts[1];    // Tên người dùng gửi yêu cầu tham gia
            String roomName = parts[2];     // Tên phòng

            // Tìm phòng dựa trên tên phòng
            Room room = ServerRun.roomManager.getRoom(roomName);

            if (room == null) {
                // Phòng không tồn tại
                sendData("JOIN_ROOM_FAILED;Room does not exist");
            } else {
                // Kiểm tra xem phòng đã đầy chưa
                if (room.getCurrentPlayers() < room.getMaxPlayers()) {
                    // Thêm người chơi hiện tại (client) vào phòng
                    boolean joined = room.addPlayer(loginUser);
                    
                    //lấy list client
                    List<String> playersInRoom = room.getPlayers();
                    String playerList = String.join(",", playersInRoom);
                    
                    if (joined) {
                        // Thông báo cho client rằng đã tham gia phòng thành công
                        sendData("JOIN_ROOM_SUCCESS;" + roomName + ";"+room.getCurrentPlayers()+";"+playerList);
                        ServerRun.manager.broadcast("UPDATE_PLAYER_LIST;"+roomName +";" +room.getCurrentPlayers() + ";" + playerList);
                        System.out.println(loginUser + " has joined the room: " + roomName);
                    } else {
                        // Thất bại trong việc thêm người chơi vào phòng
                        sendData("JOIN_ROOM_FAILED;Failed to add player to room");
                    }
                } else {
                    // Phòng đã đầy
                    sendData("JOIN_ROOM_FAILED;Room is full");
                }
            }
        }
    }
    private void onLeaveRoom(String received){
        String[] parts = received.split(";");
        String command = parts[0];
        
        if (command.equals("LEAVE_ROOM")) {
            String playerName = parts[1];  // Tên người chơi gửi yêu cầu rời phòng
            System.out.println(playerName);
            String roomName = parts[2];    // Tên phòng cần rời khỏi
            Room room = ServerRun.roomManager.getRoom(roomName);
            List<String> playersInRoom = room.getPlayers();
            playersInRoom.remove(playerName);
            String playerList = String.join(",", playersInRoom);
            // Xóa người chơi khỏi phòng;
            //ServerRun.roomManager.removePlayerFromRoom(roomName, playerName);
            for (String client : playersInRoom) {
                System.out.println("Tesst +++"+ client);
                ServerHandle s = ServerRun.manager.find(client);
                s.sendData("UPDATE_PLAYER_LIST;"+roomName +";" +room.getCurrentPlayers() + ";" + playerList);
            }
            room.setPlayers(playersInRoom);
            // Thông báo cho client rằng họ đã rời phòng thành công
            sendData("LEAVE_ROOM_SUCCESS;" + roomName);
        }
    }
    private void onRooomCancel(String received){
        String[] parts = received.split(";");
        String command = parts[0];
        String host = parts[1];
        String roomName = parts[2];
        System.out.println(roomName+"testtttt");
        
        if (command.equals("ROOM_CANCELLED")) {
            Room room = ServerRun.roomManager.getRoom(roomName);

            // Gửi thông báo tới tất cả người chơi trong phòng
            for (String client : room.getPlayers()) {
                if(!client.equals(host)){
                    ServerHandle s = ServerRun.manager.find(client);
                    s.sendData("ROOM_CANCELLED;" + roomName);
                }
            }
            // Hủy phòng
            ServerRun.roomManager.removeRoom(roomName);
        }
    }
    
    
    //game
    private void onStartRoomGame(String received){
        String[] splitted = received.split(";");
        String host = splitted[1];
        String roomName = splitted[2];
        String listPlayer[] = splitted[3].split(",");
        List<String> playersInRoom = new ArrayList<>();
        for(String i:listPlayer){
            playersInRoom.add(i);
        }
        
        
        Random random = new Random();
        Set<Integer> uniqueNumbers = new HashSet<>();

        while (uniqueNumbers.size() < 3) {
            int randomNumber = random.nextInt(15) + 1;
            uniqueNumbers.add(randomNumber);
        }
        Integer[] randomNumbers = uniqueNumbers.toArray(new Integer[0]);
        
        String question1 = new UserController().getQuestion(randomNumbers[0]);
        String question2 = new UserController().getQuestion(randomNumbers[1]);
        String question3 = new UserController().getQuestion(randomNumbers[2]);
        System.out.println(question1);
        System.out.println(question2);
        System.out.println(question3);
        
        
        // Send question here
        String data = "START_ROOM_GAME;success;" + roomName + ";" + question1+ ";" + question2+";"+ question3; 
        Room room = ServerRun.roomManager.getRoom(roomName);
        // Gửi thông báo tới tất cả người chơi trong phòng
            for (String client : room.getPlayers()) {
                ServerHandle s = ServerRun.manager.find(client);
                s.sendData(data);
            }
        room.startRoomGame(playersInRoom);
    }
    private synchronized void onReceiveSubmitRResult(String received) throws SQLException{
        System.out.println(received);
        String[] splitted = received.split(";");
        String host = splitted[1];
        String nameRoom = splitted[2];
        String listPlayer[] = splitted[3].split(",");
        String data = splitted[4];
        List<ServerHandle> lis = new ArrayList<>();
        for(String i:listPlayer){
            lis.add(ServerRun.manager.find(i));
        }
        
        Room room = ServerRun.roomManager.getRoom(nameRoom);
        System.out.println("rooooomm " + room.getRoomName());
        int dem = room.getDem()+1;
        room.setDem(dem);
        // Lưu kết quả của người chơi
        ServerHandle s = ServerRun.manager.find(host);
        s.setResult(data);
        System.out.println("s.result "+ room.getRoomName() + " " +s.getResult());
        // Chờ cho đến khi tất cả người chơi đã gửi kết quả
        while (!room.getTime().equals("00:00") && room.getTime() != null) {
           
            System.out.println(room.getTime()+ "....");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerHandle.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        // Sau khi thời gian kết thúc, xử lý kết quả của tất cả người chơi
        String finalResult = "RESULT_ROOM_GAME;success;" + room.handleResultRoomClients(lis)+";";

        // Thêm tên của tất cả người chơi vào kết quả
        finalResult += splitted[3] + ";";

        // Thêm ID phòng vào cuối chuỗi kết quả
        finalResult += nameRoom;

        // Phát kết quả cho tất cả người chơi trong phòng
        System.out.println(finalResult);
        if(dem==listPlayer.length){   
            for(ServerHandle c:lis){
                //if(!c.getLoginUser().equals(host))
                    c.sendData(finalResult);
            }
            room.setDem(0);
        }
        else{
            System.out.println("next "+dem);
        }
        //rooom.broadcasttt(finalResult);
    }
    
    
    
    
    
    
    
    
    // Get set
    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public ServerHandle getcCompetitor() {
        return cCompetitor;
    }

    public void setcCompetitor(ServerHandle cCompetitor) {
        this.cCompetitor = cCompetitor;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
}
