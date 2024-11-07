/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bai3_tcp_bytestream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Duc Loc
 */
public class TCP_Client {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost", 2206);

        OutputStream dos = client.getOutputStream();
        String request = ";B21DCCN492;NhDek2Hl";
        dos.write(request.getBytes());
        dos.flush();

        InputStream dis = client.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = dis.read(buffer);
        String src = new String(buffer, 0, bytesRead);
        System.out.println("Received data from server: " + src);

        Map<Character, Integer> charCount = new HashMap<>();

        for (char c : src.toCharArray()) {
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
        }

        List<Map.Entry<Character, Integer>> sortedEntries = new ArrayList<>(charCount.entrySet());
        sortedEntries.sort((a, b) -> {
            int freqCompare = b.getValue() - a.getValue();
            if (freqCompare == 0) {
                return Character.compare(a.getKey(), b.getKey());
            }
            return freqCompare;
        });

        // Tìm ký tự xuất hiện nhiều thứ hai
        int firstMostCount = sortedEntries.get(0).getValue();
        int secondMostCount = -1;
        List<Character> secondMostChars = new ArrayList<>();

        for (Map.Entry<Character, Integer> entry : sortedEntries) {
            if (entry.getValue() < firstMostCount) {
                if (secondMostCount == -1) {
                    secondMostCount = entry.getValue();
                }
                if (entry.getValue() == secondMostCount) {
                    secondMostChars.add(entry.getKey());
                }
            }
        }

        // Sắp xếp danh sách các ký tự thứ hai theo thứ tự a-z, 0-9
        Collections.sort(secondMostChars);

        // Gửi ký tự xuất hiện nhiều thứ hai đầu tiên theo thứ tự a-z, 0-9
        if (!secondMostChars.isEmpty()) {
            char resultChar = secondMostChars.get(0);
            String result = resultChar + ";" + secondMostCount;
            dos.write(result.getBytes());
            dos.flush();
            System.out.println("Sent result to server: " + result);
        } else {
            System.out.println("No second most frequent character found.");
        }

        dos.close();
        dis.close();
        client.close();
    }
}
