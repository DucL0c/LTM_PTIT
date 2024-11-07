/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpsocket;

import UDP.Product;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Duc Loc
 */
public class Obbject {
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException, ClassNotFoundException {
        DatagramSocket client = new DatagramSocket();
        
        byte sendData[] = new byte[1024];
        byte receiveData[] = new byte[1024];
        
        sendData = ";B21DCCN492;pHbP1hkW".getBytes();
        DatagramPacket dos = new DatagramPacket(sendData, sendData.length,
                InetAddress.getByName("203.162.10.109"), 2209);
        client.send(dos);
        
        DatagramPacket dis = new DatagramPacket(receiveData, receiveData.length);
        client.receive(dis);
        
        byte requestIdBytes [] = new byte[8];
        System.arraycopy(receiveData, 0, requestIdBytes, 0, 8);
        String requestId = new String(requestIdBytes);
        
        ByteArrayInputStream is = new ByteArrayInputStream(receiveData, 8, receiveData.length-8);
        ObjectInputStream ois = new ObjectInputStream(is);
        Product product = (Product) ois.readObject();
        
        System.out.println(product.getName() + product.getQuantity());
        product.reverseName();
        product.reverseQuantity();
        System.out.println(product.getName() + product.getQuantity());
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(product);
        oos.flush();
        
        byte productByte[] = os.toByteArray();
        byte responseByte[] = new byte[8 + productByte.length];
        System.arraycopy(requestIdBytes, 0, responseByte, 0, 8);
        System.arraycopy(productByte, 0, responseByte, 8, productByte.length);
        
        dos = new DatagramPacket(responseByte, responseByte.length, 
                InetAddress.getByName("203.162.10.109"), 2209);
        client.send(dos);
        
        client.close();
    }
}
