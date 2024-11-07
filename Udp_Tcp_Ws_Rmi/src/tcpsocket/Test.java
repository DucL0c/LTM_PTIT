/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcpsocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Duc Loc
 */
public class Test {
    public static int ucln(int a,int b){
        if(b==0) return a;
        return ucln(b,a%b);
    }
    public static int bcnn(int a, int b){
        return (a*b)/ucln(a, b);
    }
    public static String lbna(String str){
        return str.replaceAll("[ueoai]", "");
    }
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("172.188.19.218",1606);
        
        BufferedWriter dos = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        BufferedReader dis = new BufferedReader(new InputStreamReader(client.getInputStream()));
        
        dos.write("B21DCCN493;gLtH1cv");
        dos.newLine();
        dos.flush();
        
        String s = dis.readLine();
        System.out.println(s);
        
        String result = lbna(s);
        System.out.println(result);
        dos.write(result);
        dos.newLine();
        dos.flush();
        
        client.close();
    }
}
