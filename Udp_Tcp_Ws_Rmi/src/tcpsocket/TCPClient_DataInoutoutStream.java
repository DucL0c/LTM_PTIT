/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcpsocket;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Duc Loc
 */
public class TCPClient_DataInoutoutStream {
    // Hàm tính UCLN (Ước chung lớn nhất) bằng thuật toán Euclid
    public static int ucln(int a, int b) {
        if(b==0) return a;
        return ucln(b,a % b);
    }

    // Hàm tính BCNN (Bội chung nhỏ nhất) dựa vào UCLN
    public static int bcnn(int a, int b) {
        return (a * b) / ucln(a, b);
    }
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("172.188.19.218",1605);
        System.out.println(client);
        
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeUTF("B21DCCN492;3US5fBd");

        DataInputStream dis = new DataInputStream(client.getInputStream());
        int a = dis.readInt();
        int b = dis.readInt();
        
        dos.writeInt(ucln(a, b));
        dos.writeInt(bcnn(a, b));
                    
        dis.close();
        dos.close();
        client.close();
    }
}

//Socket client = new Socket("localhost",1107);
//        
//        //logic
//        System.out.println(client);
//        
//        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
//        dos.writeInt(100);
//        dos.writeInt(200);
//        System.out.println("sent succes");
//        
//        
//        
//        DataInputStream dis = new DataInputStream(client.getInputStream());
//        int a = dis.readInt();
//        int b = dis.readInt();
//        System.out.println(a + " " + b);
//        
//        dis.close();
//        dos.close();
//        client.close();
