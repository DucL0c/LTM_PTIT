/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import model.User;

/**
 *
 * @author Duc Loc
 */
public class Room {
    String host;
    String roomName;
    int maxPlayers;
    List<String> players;
    boolean gameStarted = false;
    CDT matchTimer;
    CDT waitingTimer;
    String time = "00:00";
    public int dem = 0;
    
    
    public Room(String roomName, int maxPlayers,String host) {
        this.roomName = roomName;
        this.maxPlayers = maxPlayers;
        this.host = host;
        this.players = new ArrayList<>();
    }
    
    
    
    
    //////send data đến những người chơi khác
    
    public void broadcastt(String msg,String host) throws IOException {
        players.forEach((c) -> {
            if(!c.equals(host)){
                
            }
        });
    }
    
    

    ///// xử lý thông tin player
    public synchronized boolean addPlayer(String player) {
        if (players.size() < maxPlayers) {
            players.add(player);
            return true;
        }
        return false;  // Nếu phòng đầy, không thể thêm người chơi
    }
    public int getCurrentPlayers() {
        return players.size();  // Số lượng phần tử trong danh sách players
    }
    
    public int getAvailableSlots() {
        return maxPlayers - players.size();
    }
    public synchronized boolean removePlayer(String player) {
        players.remove(player);
        return true;
    }
    
    
    
    //xu ly thong tin game
    public void startRoomGame(List<String> players) {
        gameStarted = true;
        matchTimer = new CDT(31);
        matchTimer.setTimerCallBack(
            null,
            (Callable) () -> {
                time = "" + CDTF.secondsToMinutes(matchTimer.getCurrentTick());
//                System.out.println(time);
//
//                if (time.equals("00:00")) {
//                    waitingClienttTimer(players);
//
//                    // Kiểm tra nếu tất cả người chơi chưa có kết quả
//                    boolean check;
//                    for(String i:players){
//                        ServerHandle s = ServerRun.manager.find(i);
//                        if(s.getResult()==null){
//                            check = false;
//                        }
//                    }
                    

//                    if (check) {
//                        draww(players);
//
//                        // Tạo chuỗi kết quả cho tất cả người chơi
//                        String resultMessage = players.stream()
//                            .map(Client::getLoginUser)
//                            .collect(Collectors.joining(";"));
//
//                        // Broadcast kết quả cho tất cả người chơi
//                        broadcast("RESULT_ROOM_GAME;success;DRAW;" + resultMessage + ";" + id);
//                    }
//                }
             return null;
            },
            1
        );
    }
    public String handleResultRoomClients(List<ServerHandle> players) throws SQLException {
        Map<ServerHandle, Integer> playerPoints = new HashMap<>();
        Map<ServerHandle, Integer> playerTimes = new HashMap<>();

        // Lặp qua từng người chơi và tính toán kết quả
        for (ServerHandle player : players) {
            String result = player.getResult();
            if (result != null) {
                String[] splittedResult = result.split(",");

                // Kiểm tra nếu mảng splittedResult có đủ phần tử để tránh lỗi
                if (splittedResult.length > 9) {
                    int time = Integer.parseInt(splittedResult[9]);  // Lấy thời gian từ chuỗi kết quả
                    int points = calculateResult(result, player.getLoginUser());  // Tính điểm của người chơi

                    playerPoints.put(player, points);      // Lưu điểm
                    playerTimes.put(player, time);         // Lưu thời gian
                } else {
                    System.out.println("Invalid result data for player: " + player.getLoginUser());
                }
            }
        }

        // Nếu không có kết quả từ bất kỳ người chơi nào -> hòa
        if (playerPoints.isEmpty()) {
            return "DRAW";
        }

        // Tạo danh sách chứa thông tin người chơi để sắp xếp
        List<ServerHandle> rankedPlayers = new ArrayList<>(players);

        // Sắp xếp theo điểm (giảm dần) và thời gian (tăng dần nếu điểm bằng nhau)
        rankedPlayers.sort((p1, p2) -> {
            int points1 = playerPoints.getOrDefault(p1, 0);
            int points2 = playerPoints.getOrDefault(p2, 0);
            int time1 = playerTimes.getOrDefault(p1, Integer.MAX_VALUE);
            int time2 = playerTimes.getOrDefault(p2, Integer.MAX_VALUE);

            if (points1 != points2) {
                return Integer.compare(points2, points1); // Sắp xếp giảm dần theo điểm
            } else {
                return Integer.compare(time1, time2);     // Sắp xếp tăng dần theo thời gian
            }
        });

        // Tạo chuỗi xếp hạng kết quả
        StringBuilder leaderboard = new StringBuilder("");
        for (int i = 0; i < rankedPlayers.size(); i++) {
            ServerHandle player = rankedPlayers.get(i);
            int points = playerPoints.get(player);
            int time = playerTimes.get(player);

            leaderboard.append(i + 1).append(". ")
                       .append(player.getLoginUser()).append(" - ")
                       .append("Points: ").append(points).append(" | ")
                       .append("Time: ").append(CDTF.secondsToMinutes(time)).append(",");
        }
        String r = leaderboard.toString();
        r = r.substring(0, r.length() - 1);
        return r;
}

     public int calculateResult (String received,String username) {
        String[] splitted = received.split(",");
        
        String user1 = username;
        
        String a1 = splitted[0];
        String b1 = splitted[1];
        String r1 = splitted[2];
        String a2 = splitted[3];
        String b2 = splitted[4];
        String r2 = splitted[5];
        String a3 = splitted[6];
        String b3 = splitted[7];
        String r3 = splitted[8];
        
        int i = 0;
        
        if (b1.equals(r1)) {
            i++;
        } 
        if (b2.equals(r2)) {
            i++;
        } 
        if (b3.equals(r3)) {
            i++;
        } 
        
        System.out.println(user1 + " : " + i + " cau dung");
        return i;
    }
     public void draw(List<ServerHandle> players) throws SQLException {
        // Duyệt qua từng người chơi để cập nhật thông tin hòa và điểm
        for (ServerHandle player : players) {
            User user = new UserController().getUser(player.getLoginUser());

            // Cập nhật số trận hòa
            user.setDraw(user.getDraw() + 1);

            // Cập nhật điểm số (cộng 0.5 điểm cho mỗi người chơi)
            user.setScore(user.getScore() + 0.5f);

            // Tính tổng số trận của người chơi
            int totalMatch = user.getWin() + user.getDraw() + user.getLose();

            // Tính điểm trung bình đối thủ cho người chơi này
            float avgCompetitorSum = 0;
            for (ServerHandle competitor : players) {
                if (!competitor.getLoginUser().equals(player.getLoginUser())) {
                    User competitorUser = new UserController().getUser(competitor.getLoginUser());
                    avgCompetitorSum += competitorUser.getScore();
                }
            }

            // Tính điểm trung bình đối thủ mới
            float newAvgCompetitor = (totalMatch * user.getAvgCompetitor() + avgCompetitorSum) / (totalMatch + players.size() - 1);

            // Cập nhật điểm trung bình đối thủ
            user.setAvgCompetitor(newAvgCompetitor);

            // Cập nhật lại người chơi trong database
            new UserController().updateUser(user);
        }
    }
     public void clientWin(ServerHandle winner, int winnerTime,List<ServerHandle> players) throws SQLException {
        // Lấy UserModel của người thắng
        User winnerUser = new UserController().getUser(winner.getLoginUser());

        // Cập nhật số trận thắng và điểm cho người thắng
        winnerUser.setWin(winnerUser.getWin() + 1);
        winnerUser.setScore(winnerUser.getScore() + 1);

        // Tính tổng số trận cho người thắng
        int totalMatchesWinner = winnerUser.getWin() + winnerUser.getDraw() + winnerUser.getLose();

        // Cập nhật thời gian trung bình cho người thắng
        float newAvgTimeWinner = (totalMatchesWinner * winnerUser.getAvgTime() + winnerTime) / (totalMatchesWinner + 1);
        winnerUser.setAvgTime(newAvgTimeWinner);

        // Duyệt qua tất cả người chơi và cập nhật trạng thái thắng/thua
        for (ServerHandle player : players) {
            if (!player.getLoginUser().equals(winner.getLoginUser())) {
                // Lấy UserModel của người thua
                User loserUser = new UserController().getUser(player.getLoginUser());

                // Cập nhật số trận thua cho người thua
                loserUser.setLose(loserUser.getLose() + 1);

                // Tính tổng số trận cho người thua
                int totalMatchesLoser = loserUser.getWin() + loserUser.getDraw() + loserUser.getLose();

                // Cập nhật điểm trung bình đối thủ cho cả người thắng và người thua
                float newAvgCompetitorWinner = (totalMatchesWinner * winnerUser.getAvgCompetitor() + loserUser.getScore()) / (totalMatchesWinner + 1);
                float newAvgCompetitorLoser = (totalMatchesLoser * loserUser.getAvgCompetitor() + winnerUser.getScore()) / (totalMatchesLoser + 1);

                winnerUser.setAvgCompetitor(newAvgCompetitorWinner);
                loserUser.setAvgCompetitor(newAvgCompetitorLoser);

                // Cập nhật dữ liệu của người thua trong database
                new UserController().updateUser(loserUser);
            }
        }

        // Cập nhật dữ liệu của người thắng trong database
        new UserController().updateUser(winnerUser);
    }
    public void waitingClienttTimer(List<String> players) {
//        waitingTimer = new CDT(12);
//        waitingTimer.setTimerCallBack(
//            null,
//            (Callable) () -> {
//                waitingTime = "" + CDTF.secondsToMinutes(waitingTimer.getCurrentTick());
//                System.out.println("waiting: " + waitingTime);
//                boolean allPlayersNoResponse = players.stream()
//                .allMatch(player -> player.getPlayAgain()== null);
//                if (waitingTime.equals("00:00")) {
//                    if (allPlayersNoResponse) {
//                        broadcast("ASK_PLAY_AGAIN;NO");
//                        deleteRoomGame(players);
//                    } 
//                }
//                return null;
//            },
//            1
//        );
    }
    
    
    
    //// get set mac dinh

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    Room getRRoom(String roomName) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDem() {
        return dem;
    }

    public void setDem(int dem) {
        this.dem = dem;
    }
    
    
}
