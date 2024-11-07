/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpsocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Duc Loc
 */
public class Sstring {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket client = new DatagramSocket();
        
        byte sendData[] = new byte[1024];
        byte receiveData[] = new byte[1024];
        
        sendData = ";B21DCCN492;jAOMnPP6".getBytes();
        DatagramPacket dos = new DatagramPacket(sendData, sendData.length, 
                InetAddress.getByName("203.162.10.109"), 2208);
        client.send(dos);
        
        DatagramPacket dis = new DatagramPacket(receiveData, receiveData.length);
        client.receive(dis);
        String str = new String(dis.getData(),0,dis.getLength());
        System.out.println(str);
        
        String split[] = str.split(";");
        String requestId = split[0];
        String strs[] = split[1].split(" ");
        
        String result = "";
        for(String i:strs){
            String tmp = i.substring(0,1).toUpperCase()+ i.substring(1,i.length()).toLowerCase();
            result += tmp + " ";
        }
        
        sendData = (requestId + ";" + result.trim()).getBytes();
        dis = new DatagramPacket(sendData, sendData.length, 
                InetAddress.getByName("203.162.10.109"), 2208);
        System.out.println(result);
        client.send(dis);
        
        
        client.close();
    }
}
