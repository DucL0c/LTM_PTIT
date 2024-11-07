package tcpsocket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Duc Loc
 */
public class TCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1605);
        System.out.println("Server is running.....");
        
        while(true){
            Socket conn = server.accept();
            System.out.println("A new conn: " + conn);
            
            BufferedReader dis = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str = dis.readLine();
            
            String strs[] = str.split(";");
            System.out.println(strs[0]);
            
//            DataInputStream dis = new DataInputStream(conn.getInputStream());
//            String a = dis.readUTF();
//            String b = dis.readUTF();
//            System.out.println(a + " " + b);
//            
//            
//            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
//            dos.writeInt(12);
//            dos.writeInt(15);
//            
//            int x = dis.readInt();
//            int y = dis.readInt();
//            System.out.println(x + " " + y);
            
            
//            dos.close();
            dis.close();
            conn.close();
        }
        
    }
}


//ServerSocket server = new ServerSocket(1107);
//        System.out.println("Server is listening......");
//        
//        while(true){
//            Socket conn = server.accept();
//            System.out.println("A new conn" + conn);
//            
//            //logic
//            DataInputStream dis = new DataInputStream(conn.getInputStream());
//            int a = dis.readInt();
//            int b = dis.readInt();
//            System.out.println(a + " " + b);
//            
//            
//            
//            
//            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
//            dos.writeInt(200);
//            dos.writeInt(200);
//            System.out.println("respone sucess");
//            
//            dos.close();
//            dis.close();
//            conn.close();
//        }
