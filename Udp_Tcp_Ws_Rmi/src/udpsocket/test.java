/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpsocket;

import java.util.Collections;
import java.util.Scanner;
import java.util.TreeMap;



/**
 *
 * @author Duc Loc
 */
public class test {
    public static void main(String[] args) {
        TreeMap<Integer, Integer> m = new TreeMap<>(Collections.reverseOrder());
        
        Scanner sc = new Scanner(System.in);
         
        int n = sc.nextInt();
        for(int i = 0;i<n;i++){
            int x = sc.nextInt();
            if(m.containsKey(x)){
                m.put(x, m.get(x)+1);
            }
            else{
                m.put(x, 1);
            }
        }
        
        m.forEach((key,value)->{
            System.out.println(key + " " + value);
        });
        
    }
}
