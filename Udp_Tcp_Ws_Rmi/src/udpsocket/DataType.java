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
public class DataType {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket client = new DatagramSocket();
        
        byte sendData[] = new byte[1024];
        sendData = ";B21DCCN492;fnIXsFvA".getBytes();
        
        DatagramPacket dos = new DatagramPacket(sendData, sendData.length, 
                InetAddress.getByName("203.162.10.109"), 2207);
        client.send(dos);
        
        byte receiveData[] = new byte[1024];
        DatagramPacket dis = new DatagramPacket(receiveData,receiveData.length);
        client.receive(dis);
        String str = new String(dis.getData(),0,dis.getLength());
        System.out.println(str);
        
        String split[] = str.split(";");
        String requestId = split[0];
        String number[] = split[1].split(",");
        
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int i=0;i<number.length;i++){
            if(Integer.parseInt(number[i])>max){
                max = Integer.parseInt(number[i]);
            }
            if(Integer.parseInt(number[i])<min){
                min = Integer.parseInt(number[i]);
            }
        }
        String reponse = requestId + ";"+Integer.toString(max)+","+Integer.toString(min);
        sendData = reponse.getBytes();
        dos = new DatagramPacket(sendData, sendData.length, 
                InetAddress.getByName("203.162.10.109"), 2207);
        System.out.println(reponse);
        client.send(dos);
        
        
        client.close();
    }
}
