/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import run.ServerRun;

/**
 *
 * @author Duc Loc
 */
public class RoomManager {
    Map<String, Room> rooms;
    public RoomManager() {
        rooms = new HashMap<>();
    }
    
    public synchronized Room createRoom(String roomName, int maxPlayers,String host) {
        if (!rooms.containsKey(roomName)) {
            Room newRoom = new Room(roomName, maxPlayers,host);
            rooms.put(roomName, newRoom);
            return newRoom;
        }
        return null;  // Trả về null nếu phòng đã tồn tại
    }
    public synchronized List<Room> getRoomList() {
        if (rooms.isEmpty()) {
            return new ArrayList<>(); // Trả về danh sách rỗng nếu room là null
        }
        return rooms.values().stream().collect(Collectors.toList());
    }
    
    // Lấy một phòng cụ thể
    public synchronized Room getRoom(String roomName) {
        return rooms.get(roomName);
    }

    // Xóa một phòng
    public synchronized void removeRoom(String roomName) {
        rooms.remove(roomName);
    }

    // Kiểm tra nếu phòng đã tồn tại
    public synchronized boolean isRoomExists(String roomName) {
        return rooms.containsKey(roomName);
    }
    
    public synchronized void removePlayerFromRoom(String roomName, String playerName) {
        Room room = rooms.get(roomName);
        System.out.println(room.getRoomName());
        if (room != null) {  
            boolean isRoomEmpty = room.removePlayer(playerName); //xoá người chơi khỏi phòng
            System.out.println(isRoomEmpty);
        }
    }
    private synchronized void removeAllPlayersAndDeleteRoom(Room room) {
        // Gửi thông báo cho tất cả người chơi trong phòng rằng phòng đã bị giải tán
        for (String client : room.getPlayers()) {
            ServerHandle s = ServerRun.manager.find(client);
            s.sendData("ROOM_DISBANDED;" + room.getRoomName());
        }

        // Xóa phòng khỏi Map
        rooms.remove(room.getRoomName());
        System.out.println("Room " + room.getRoomName() + " has been disbanded by the host.");
    }
}
