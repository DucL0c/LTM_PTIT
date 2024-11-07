/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcpsocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Duc Loc
 */
public class TCPClient_InoutputStream {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("172.188.19.218",1604);
        
        OutputStream dos = client.getOutputStream();
        dos.write("B21DCCN492;6AlFuaA".getBytes());
        
        InputStream dis = client.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = dis.read(buffer);
        String src = new String(buffer,0,bytesRead);
        
        String numbers[] = src.split("\\|");
        int a = Integer.parseInt(numbers[0]);
        int b = Integer.parseInt(numbers[1]);
        
        int result = (int) Math.pow(a, b);
        dos.write(Integer.toString(result).getBytes());
        
        dos.close();
        client.close();
    }
}
