/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multicast_nic;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author Duc Loc
 */
public class MulticastServer {
    public static void main(String args[]) throws SocketException, IOException, InterruptedException {
        DatagramSocket server = new DatagramSocket();
        System.out.println("server starts send multicast..");
        int i = 0;
        while(true){
            String sendStr = "data package: " + i;
            byte[] buf = sendStr.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, InetAddress.getByName("224.2.2.3"), 1107);
            server.send(sendPacket);
            
            System.out.println(sendStr);
            i++;
            
            Thread.sleep(1500);
        }
        
    }
}
