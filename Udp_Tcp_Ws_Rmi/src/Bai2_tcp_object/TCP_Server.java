/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bai2_tcp_object;

import TCP.Book;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Duc Loc
 */
public class TCP_Server {
    private static final Map<String, Book> requestMap = new HashMap<>();
    private static final String[] BOOK_TITLES = {"The Great Adventure", "Mysterious Island", "War of the Worlds", "Journey to the Center"};
    private static final String[] AUTHORS = {"jules verne", "george orwell", "mark twain", "leo tolstoy"};
    private static final double MIN_PRICE = 100.0;
    private static final double MAX_PRICE = 500.0;
    private static final int MAX_PAGE_COUNT = 600;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(2209)) {
            System.out.println("Server is running...");

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    handleClient(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {


            String receivedMessage = (String) ois.readObject();
            if (receivedMessage.startsWith(";")) {
                // Tạo requestId và Book ngẫu nhiên
                String requestId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                Random random = new Random();

                String id = "B" + (random.nextInt(900) + 100);
                String title = BOOK_TITLES[random.nextInt(BOOK_TITLES.length)];
                String author = AUTHORS[random.nextInt(AUTHORS.length)];
                double price = MIN_PRICE + (MAX_PRICE - MIN_PRICE) * random.nextDouble();
                int pageCount = random.nextInt(MAX_PAGE_COUNT);

                Book book = new Book(id, title, author, price, pageCount);
                requestMap.put(requestId, book);

                // Gửi lại requestId và Book cho client
                oos.writeObject(requestId);
                oos.writeObject(book);
                oos.flush();
                System.out.println("Sent Book: " + book + " with requestId: " + requestId);

                // Nhận lại requestId và Book đã chỉnh sửa từ client
                String clientRequestId = (String) ois.readObject();
                Book modifiedBook = (Book) ois.readObject();

                // Kiểm tra requestId và dữ liệu
                if (requestMap.containsKey(clientRequestId)) {
                    Book originalBook = requestMap.get(clientRequestId);

                    if (isProcessedCorrectly(originalBook, modifiedBook)) {
                        System.out.println("Book processed correctly: " + modifiedBook);
                        oos.writeObject("SUCCESS: Book processed correctly.");
                    } else {
                        System.out.println("Book processing is incorrect. Received: " + modifiedBook);
                        oos.writeObject("ERROR: Book processing is incorrect.");
                    }

                    
                    requestMap.remove(clientRequestId);
                } else {
                    System.out.println("Invalid requestId: " + clientRequestId);
                    oos.writeObject("ERROR: Invalid requestId.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close(); 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean isProcessedCorrectly(Book original, Book modified) {
        String expectedTitle = reverseWords(original.getTitle());
        String expectedAuthor = toTitleCase(original.getAuthor());
        boolean isTitleCorrect = expectedTitle.equals(modified.getTitle());
        boolean isAuthorCorrect = expectedAuthor.equals(modified.getAuthor());

        boolean isPriceProcessed = original.getPageCount() > 300
                                   ? modified.getPrice() == original.getPrice() * 1.2
                                   : modified.getPrice() == original.getPrice();

        return isTitleCorrect && isAuthorCorrect && isPriceProcessed;
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
