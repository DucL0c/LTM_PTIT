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
public class DataType2 {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        DatagramSocket client = new DatagramSocket();
        
        byte sendData[] = new byte[1024];
        byte receiveData[] = new byte[1024];
        
        sendData = ";B21DCCN492;07azPsXT".getBytes();
        DatagramPacket dos = new DatagramPacket(sendData, sendData.length, 
                InetAddress.getByName("203.162.10.109"), 2207);
        client.send(dos);
        
        DatagramPacket dis = new DatagramPacket(receiveData, receiveData.length);
        client.receive(dis);
        String str = new String(dis.getData(),0,dis.getLength());
        System.out.println(str);
        
        String split[] = str.split(";");
        String requestId = split[0];
        String number[] = split[1].split(",");
        
        int max1 = Integer.MIN_VALUE;
        int max2 = max1;
        int min1 = Integer.MAX_VALUE;
        int min2 = min1;
        
        for(String i:number){
            int n = Integer.parseInt(i);
            if(n > max1){
                max2 = max1;
                max1 = n;
            }else if(n>max2 && n<max1){
                max2 = n;
            }
            
            if(n<min1){
                min2 = min1;
                min1 = n;
            }else if(n<min2 && n>min1){
                min2 = n;
            }
        }
        
        String reponse = requestId+";"+max2+","+min2;
        sendData = reponse.getBytes();
        dos = new DatagramPacket(sendData, sendData.length, 
                InetAddress.getByName("203.162.10.109"), 2207);
        client.send(dos);
        
        
        client.close();
    }
}
