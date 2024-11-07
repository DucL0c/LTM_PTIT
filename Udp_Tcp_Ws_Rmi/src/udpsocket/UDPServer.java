/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpsocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 * @author Duc Loc
 */
public class UDPServer {
    public static void main(String[] args) throws SocketException, IOException {
        DatagramSocket server = new DatagramSocket(1107);
        System.out.println("UDP server is running....");
        while(true){
            
            //accept rep
            byte[] buf = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(buf,buf.length);
            server.receive(receivePacket);
            
            System.out.println(new String(receivePacket.getData()).trim());
            
//            respone
            byte[] sendBuf = new byte[1024];
            sendBuf = "wellcome...".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length
                    , receivePacket.getAddress(),receivePacket.getPort());
            
            server.send(sendPacket);
            
        }
    }
}
