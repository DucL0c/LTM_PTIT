/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Duc Loc
 */
public class Question implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String de_bai;
    private String dap_an_dung;
    private String dap_an_1;
    private String dap_an_2;
    private String dap_an_3;
    private String dap_an_4;

    public Question() {
    }

    public Question(String id, String de_bai, String dap_an_dung, String dap_an_1, String dap_an_2, String dap_an_3, String dap_an_4) {
        this.id = id;
        this.de_bai = de_bai;
        this.dap_an_dung = dap_an_dung;
        this.dap_an_1 = dap_an_1;
        this.dap_an_2 = dap_an_2;
        this.dap_an_3 = dap_an_3;
        this.dap_an_4 = dap_an_4;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDe_bai() {
        return de_bai;
    }

    public void setDe_bai(String de_bai) {
        this.de_bai = de_bai;
    }

    public String getDap_an_dung() {
        return dap_an_dung;
    }

    public void setDap_an_dung(String dap_an_dung) {
        this.dap_an_dung = dap_an_dung;
    }

    public String getDap_an_1() {
        return dap_an_1;
    }

    public void setDap_an_1(String dap_an_1) {
        this.dap_an_1 = dap_an_1;
    }

    public String getDap_an_2() {
        return dap_an_2;
    }

    public void setDap_an_2(String dap_an_2) {
        this.dap_an_2 = dap_an_2;
    }

    public String getDap_an_3() {
        return dap_an_3;
    }

    public void setDap_an_3(String dap_an_3) {
        this.dap_an_3 = dap_an_3;
    }

    public String getDap_an_4() {
        return dap_an_4;
    }

    public void setDap_an_4(String dap_an_4) {
        this.dap_an_4 = dap_an_4;
    }
    
    
}
