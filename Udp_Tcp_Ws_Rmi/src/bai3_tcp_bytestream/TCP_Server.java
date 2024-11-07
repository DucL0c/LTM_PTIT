/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bai3_tcp_bytestream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Duc Loc
 */
public class TCP_Server {
    private static String lastRandomData = "";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(2206)) {
            System.out.println("Server is running... Waiting for connection...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected.");

                    InputStream dis = clientSocket.getInputStream();
                    OutputStream dos = clientSocket.getOutputStream();

                    byte[] buffer = new byte[1024];
                    int bytesRead = dis.read(buffer);
                    String clientMessage = new String(buffer, 0, bytesRead).trim();
                    System.out.println("Received: " + clientMessage);

                    lastRandomData = generateRandomString();
                    dos.write(lastRandomData.getBytes());
                    dos.flush();

                    String expectedResult = calculateSecondMostFrequent(lastRandomData);
                    bytesRead = dis.read(buffer);
                    clientMessage = new String(buffer, 0, bytesRead).trim();
                    if (clientMessage.equals(expectedResult)) {
                        dos.write("Correct".getBytes());
                        dos.flush();
                        System.out.println("Client's result is correct.");
                    } else {
                        dos.write("Incorrect".getBytes());
                        dos.flush();
                        System.out.println("Client's result is incorrect. Expected: " + expectedResult);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateRandomString() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        int length = 20 + random.nextInt(81);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char randomChar = chars.charAt(random.nextInt(chars.length()));
            sb.append(randomChar);
        }

        return sb.toString();
    }

    private static String calculateSecondMostFrequent(String data) {
        Map<Character, Integer> charCount = new HashMap<>();

        for (char c : data.toCharArray()) {
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

        Collections.sort(secondMostChars);

        if (!secondMostChars.isEmpty()) {
            return secondMostChars.get(0) + ";" + secondMostCount;
        } else {
            return "";
        }
    }
}