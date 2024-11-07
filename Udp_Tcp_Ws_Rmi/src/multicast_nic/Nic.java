/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package multicast_nic;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 *
 * @author Duc Loc
 */
public class Nic {
    public static void main(String[] args) throws SocketException {
         Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
         while(nifs.hasMoreElements()){
             NetworkInterface nif = nifs.nextElement();
             if(nif.isUp()){
                 System.out.println(nif);
             }
         }
    }
}
