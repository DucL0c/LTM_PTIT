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

/**
 *
 * @author Duc Loc
 */
public class UDP_Client {
    public static void main(String[] args) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            String studentCode = "B21DCCN492";
            String qCode = "1";
            String requestMessage = ";" + studentCode + ";" + qCode;
            
            // Gửi thông điệp yêu cầu lên server
            byte[] sendRequest = requestMessage.getBytes();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            DatagramPacket sendPacket = new DatagramPacket(sendRequest, sendRequest.length, serverAddress, 2209);
            clientSocket.send(sendPacket);
            
            // Nhận phản hồi từ server chứa requestId và đối tượng Employee
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            clientSocket.receive(receivePacket);
            
            // Parse requestId và đối tượng Employee
            byte[] receivedData = receivePacket.getData();
            String requestId = new String(receivedData, 0, 8).trim();
            
            ByteArrayInputStream bais = new ByteArrayInputStream(receivedData, 8, receivePacket.getLength() - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Employee employee = (Employee) ois.readObject();
            
            System.out.println("Received from server: " + employee);
            String strs[] = employee.getName().split(" ");
            // Thực hiện sửa đổi như yêu cầu (tăng/giảm lương dựa trên position)
            if ("Intern".equalsIgnoreCase(employee.getPosition())) {
                employee.setSalary(employee.getSalary() * 0.9);
            } else if ("Manager".equalsIgnoreCase(employee.getPosition())) {
                employee.setSalary(employee.getSalary() * 1.2);
            }
            String result = "";
            for(String i:strs){
                String tmp = i.substring(0,1).toUpperCase()+ i.substring(1,i.length()).toLowerCase();
                result += tmp + " ";
            }
            employee.setName(result.trim());
            
            // Đóng gói lại requestId và đối tượng Employee gửi lại server
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(employee);
            byte[] sendData = baos.toByteArray();
            
            byte[] finalSendData = new byte[8 + sendData.length];
            System.arraycopy(requestId.getBytes(), 0, finalSendData, 0, 8);
            System.arraycopy(sendData, 0, finalSendData, 8, sendData.length);
            
            DatagramPacket finalSendPacket = new DatagramPacket(finalSendData, finalSendData.length, serverAddress, 2209);
            clientSocket.send(finalSendPacket);
            
            System.out.println("Sent modified Employee back to server: " + employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
