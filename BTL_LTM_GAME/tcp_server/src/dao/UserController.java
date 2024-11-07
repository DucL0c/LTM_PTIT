/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DbConnect;
import model.Question;
import model.User;
/**
 *
 * @author admin
 */
public class UserController {
    //  SQL
    private final String INSERT_USER = "INSERT INTO users (username, password, score, win, draw, lose, avgCompetitor, avgTime) VALUES (?, ?, 0, 0, 0, 0, 0, 0)";
    private final String CHECK_USER = "SELECT TOP 1 userId FROM users WHERE username = ?";
    private final String LOGIN_USER = "SELECT username, password, score FROM users WHERE username = ? AND password = ?";
    private final String GET_INFO_USER = "SELECT username, password, score, win, draw, lose, avgCompetitor, avgTime FROM users WHERE username = ?";
    private final String UPDATE_USER = "UPDATE users SET score = ?, win = ?, draw = ?, lose = ?, avgCompetitor = ?, avgTime = ? WHERE username = ?";
    private final String GET_QUESTION = "SELECT * FROM question WHERE id = ?";
    // Instance
    private final Connection con;

    
    public UserController() {
        this.con = DbConnect.getInstance().getConnection();
    }

    public String register(String username, String password) {
    	//  Check user exit
        try {
            PreparedStatement p = con.prepareStatement(CHECK_USER);
            System.out.println(username+" "+password +" "+ p);
            p.setString(1, username);
            ResultSet r = p.executeQuery();
            
            if (r.next()) {
                return "failed;" + "User Already Exit";
            } else {
                r.close();
                p.close();
                p = con.prepareStatement(INSERT_USER);
                p.setString(1, username);
                p.setString(2, password);
                p.executeUpdate();
                p.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "success;";
    }
  
    public String login(String username, String password) {
    // Kiểm tra kết nối có null không
    if (con == null) {
        System.out.println("Connection is null. Please check your database connection.");
        return "Connection failed.";
    }

    try {
        // Chuẩn bị câu truy vấn
        PreparedStatement p = con.prepareStatement(LOGIN_USER, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        
        // Thiết lập tham số cho câu lệnh SQL
        p.setString(1, username);
        p.setString(2, password);
        
        // Thực hiện truy vấn
        ResultSet r = p.executeQuery();
        
        // Kiểm tra kết quả
        if (r.next()) {
            float score = r.getFloat("score");
            return "success;" + username + ";" + score;
        } else {
            return "failed;" + "Please enter the correct account password!";
        }
    } catch (SQLException e) {
        // In ra lỗi nếu có
        e.printStackTrace();
        return "Database error: " + e.getMessage();
    }
}
    
    public String getInfoUser(String username) {
        User user = new User();
        try {
            PreparedStatement p = con.prepareStatement(GET_INFO_USER);
            p.setString(1, username);
            
            ResultSet r = p.executeQuery();
            while(r.next()) {
                user.setUserName(r.getString("username"));
                user.setScore(r.getFloat("score"));
                user.setWin(r.getInt("win"));
                user.setDraw(r.getInt("draw"));
                user.setLose(r.getInt("lose"));
                user.setAvgCompetitor(r.getFloat("avgCompetitor"));
                user.setAvgTime(r.getFloat("avgTime"));
            }
            return "success;" + user.getUserName() + ";" + user.getScore() + ";" + user.getWin() + ";" + user.getDraw() + ";" + user.getLose() + ";" + user.getAvgCompetitor() + ";" + user.getAvgTime() ;
        } catch (SQLException e) {
            e.printStackTrace();
        }   
        return null;
    }
    
    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        PreparedStatement p = con.prepareStatement(UPDATE_USER);
        //  Login User 
        p.setFloat(1, user.getScore());
        p.setInt(2, user.getWin());
        p.setInt(3, user.getDraw());
        p.setInt(4, user.getLose());
        p.setFloat(5, user.getAvgCompetitor());
        p.setFloat(6, user.getAvgTime());
        p.setString(7, user.getUserName());

//            ResultSet r = p.executeQuery();
        rowUpdated = p.executeUpdate() > 0;
        return rowUpdated;
    }

    public User getUser(String username) {
        User user = new User();
        try {
            PreparedStatement p = con.prepareStatement(GET_INFO_USER);
            p.setString(1, username);
            
            ResultSet r = p.executeQuery();
            while(r.next()) {
                user.setUserName(r.getString("username"));
                user.setScore(r.getFloat("score"));
                user.setWin(r.getInt("win"));
                user.setDraw(r.getInt("draw"));
                user.setLose(r.getInt("lose"));
                user.setAvgCompetitor(r.getFloat("avgCompetitor"));
                user.setAvgTime(r.getFloat("avgTime"));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }   
        return null;
    }
    
    public String getQuestion(int i) {
        Question question = new Question();
        try {
            PreparedStatement p = con.prepareStatement(GET_QUESTION);
            p.setInt(1, i);
            ResultSet r = p.executeQuery();
            while(r.next()) {
                question.setId(r.getString("id"));
                question.setDe_bai(r.getString("de_bai"));
                question.setDap_an_dung(r.getString("dap_an_dung"));
                question.setDap_an_1(r.getString("dap_an_1"));
                question.setDap_an_2(r.getString("dap_an_2"));
                question.setDap_an_3(r.getString("dap_an_3"));
                question.setDap_an_4(r.getString("dap_an_4"));
            }
            return question.getDe_bai() + ";" + question.getDap_an_dung() + ";" + question.getDap_an_1() + ";" + 
                    question.getDap_an_2() + ";" + question.getDap_an_3() + ";" + question.getDap_an_4();
        } catch (SQLException e) {
            e.printStackTrace();
        }   
        return null;
    }
}
