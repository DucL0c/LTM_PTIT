/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bai2_tcp_object;

import TCP.Book;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 *
 * @author Duc Loc
 */
public class TCP_Client {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("localhost", 2209);
             ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {

            String studentCode = "B21DCCN492";
            String questionCode = "2";
            oos.writeObject(";" + studentCode + ";" + questionCode);
            oos.flush();

            String requestId = (String) ois.readObject();
            Book book = (Book) ois.readObject();
            System.out.println("Received Book: " + book);

            // Xử lý Book: 
            book.setTitle(reverseWords(book.getTitle()));
            book.setAuthor(toTitleCase(book.getAuthor()));
            if (book.getPageCount() > 300) {
                book.setPrice(book.getPrice() * 1.2);
            }

            oos.writeObject(requestId);
            oos.writeObject(book);
            oos.flush();
            System.out.println("Processed and sent: " + book);

            // Nhận phản hồi từ server
            String response = (String) ois.readObject();
            System.out.println("Server response: " + response);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String reverseWords(String input) {
        String[] words = input.split(" ");
        StringBuilder reversed = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            reversed.append(words[i]).append(" ");
        }
        return reversed.toString().trim();
    }

    private static String toTitleCase(String input) {
        String[] words = input.toLowerCase().split(" ");
        StringBuilder titleCase = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                titleCase.append(Character.toUpperCase(word.charAt(0)))
                         .append(word.substring(1)).append(" ");
            }
        }
        return titleCase.toString().trim();
    }
}
