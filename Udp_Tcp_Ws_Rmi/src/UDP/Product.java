/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UDP;

import java.io.Serializable;

/**
 *
 * @author Duc Loc
 */
public class Product implements Serializable{
    private static final long serialVersionUID = 20161107;
    public String id;
    public String code;
    public String name;
    public int quantity;

    public Product() {
    }

    public Product(String id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }
    
    public void reverseName(){
        String split[] = this.name.split(" ");
        String dau = split[0];
        String cuoi = split[split.length-1];
        String tmp = "";
        for(int i=1;i<split.length-1;i++){
            tmp += split[i] + " ";
        }
        this.name = cuoi + " " + tmp + dau;
    }
    public void reverseQuantity(){
        String s = Integer.toString(this.quantity);
        String tmp = "";
        for(int i=s.length()-1;i>=0;i--){
            tmp += s.charAt(i);
        }
        this.quantity = Integer.parseInt(tmp);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
