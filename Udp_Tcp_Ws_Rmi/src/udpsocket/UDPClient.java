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
public class UDPClient {
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException {
        // TODO code application logic here
        DatagramSocket client = new DatagramSocket();
        byte[] data = new byte[1024];
        data = "hello from UDPClient".getBytes();
        
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, InetAddress.getByName("localhost"),1107);
       
        client.send(sendPacket);
        System.out.println("send success");
        
        
        //receive respone
        byte[] receiveBuf = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuf,receiveBuf.length);
        client.receive(receivePacket);
        System.out.println(new String(receivePacket.getData()).trim());
        
        
    }
}
