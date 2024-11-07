/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tcpsocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Duc Loc
 */
public class TCPClient_Buffer {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("172.188.19.218",1606);
        
        BufferedWriter dos = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        dos.write("B21DCCN492;4vSjXio");
        dos.newLine();
        dos.flush();
        
        BufferedReader dis = new BufferedReader(new InputStreamReader(client.getInputStream()));
        String str = dis.readLine();
        
        String strs[] = str.split(", ");
        List<String> s = new ArrayList<>();
        for(int i=0;i<strs.length;i++){
            if(strs[i].endsWith(".edu")){
                s.add(strs[i]);
            }
        }
        
        String result = String.join(", ", s);
        dos.write(result);
        dos.newLine();
        dos.flush();
        
        dis.close();
        dos.close();
        client.close();
    }
}
