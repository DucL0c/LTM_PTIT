/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multicast_nic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author Duc Loc
 */
public class MulticastClient {
    public static void main(String[] args) throws IOException {
        MulticastSocket client = new MulticastSocket(1107);
        client.joinGroup(InetAddress.getByName("224.2.2.3"));
        
        while(true){
            byte[] buf = new byte[256];
            DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
            client.receive(receivePacket);
            
            System.out.println(new String(receivePacket.getData()));
        }
    }
}
