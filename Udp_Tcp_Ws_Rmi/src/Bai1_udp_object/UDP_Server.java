/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Bai1_udp_object;

import UDP.Employee;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Duc Loc
 */
public class UDP_Server {
    private static final String[] NAMES = {"nguYen VaN A", "TraN Thi B", "Le VAn C", "PhaM THi D"};
    private static final String[] POSITIONS = {"Intern", "Manager", "Developer", "Designer"};
    private static final double MIN_SALARY = 5000.0;
    private static final double MAX_SALARY = 20000.0;
    private static final Map<String, Employee> requestMap = new HashMap<>(); // Lưu requestId và Employee ban đầu

    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(2209)) {
            byte[] receiveBuffer = new byte[1024];
            Random random = new Random();

            while (true) {
                // Nhận yêu cầu từ client
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                System.out.println("Received request: " + receivedMessage);

                if (receivedMessage.startsWith(";")) {
                    // Client gửi mã sinh viên và mã bài tập
                    String studentCode = receivedMessage.split(";")[1];
                    String questionCode = receivedMessage.split(";")[2];
                    
                    // Tạo requestId ngẫu nhiên
                    String requestId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

                    // Tạo thông tin Employee ngẫu nhiên
                    String id = "E" + (random.nextInt(900) + 100); // Tạo mã ID ngẫu nhiên dạng E100 - E999
                    String name = NAMES[random.nextInt(NAMES.length)];
                    String position = POSITIONS[random.nextInt(POSITIONS.length)];
                    double salary = MIN_SALARY + (MAX_SALARY - MIN_SALARY) * random.nextDouble();

                    Employee employee = new Employee(id, name, position, salary);

                    // Lưu requestId và Employee vào bản đồ để kiểm tra sau này
                    requestMap.put(requestId, employee);

                    // Đóng gói dữ liệu thành mảng byte để gửi lại
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(employee);
                    byte[] sendData = baos.toByteArray();

                    byte[] responseData = new byte[8 + sendData.length];
                    System.arraycopy(requestId.getBytes(), 0, responseData, 0, 8);
                    System.arraycopy(sendData, 0, responseData, 8, sendData.length);

                    // Gửi lại cho client
                    InetAddress clientAddress = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();
                    DatagramPacket sendPacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
                    serverSocket.send(sendPacket);

                    System.out.println("Sent Employee data to client with requestId: " + requestId);

                } else {
                    // Client gửi requestId để kiểm tra
                    String clientRequestId = receivedMessage.substring(0, 8).trim();

                    if (requestMap.containsKey(clientRequestId)) {
                        // Nhận đối tượng Employee đã được xử lý từ client
                        ByteArrayInputStream bais = new ByteArrayInputStream(receivePacket.getData(), 8, receivePacket.getLength() - 8);
                        ObjectInputStream ois = new ObjectInputStream(bais);
                        Employee modifiedEmployee = (Employee) ois.readObject();

                        // Lấy Employee ban đầu
                        Employee originalEmployee = requestMap.get(clientRequestId);

                        // Kiểm tra xem dữ liệu đã được xử lý đúng chưa
                        if (isProcessedCorrectly(originalEmployee, modifiedEmployee)) {
                            System.out.println("Employee processed correctly: " + modifiedEmployee);
                        } else {
                            System.out.println("Employee processing is incorrect. Received: " + modifiedEmployee);
                        }

                        // Xóa yêu cầu khỏi bản đồ sau khi đã xử lý xong
                        requestMap.remove(clientRequestId);
                    } else {
                        System.out.println("Invalid requestId received from client: " + clientRequestId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isProcessedCorrectly(Employee original, Employee modified) {
        // Chuyển tên thành dạng viết hoa chữ cái đầu mỗi từ
        String expectedName = toTitleCase(original.getName());
        boolean isNameCorrect = expectedName.equals(modified.getName());

        // Kiểm tra lương
        boolean isSalaryProcessed = original.getPosition().equalsIgnoreCase("Intern")
                                    ? modified.getSalary() == original.getSalary() * 0.9
                                    : modified.getSalary() == original.getSalary();

        return isNameCorrect && isSalaryProcessed;
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