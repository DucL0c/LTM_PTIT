/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.CDT;
import dao.CDTF;
import java.util.Enumeration;
import java.util.concurrent.Callable;
import javax.swing.AbstractButton;
import javax.swing.JOptionPane;
import run.ClientRun;

/**
 *
 * @author admin
 */
public class RoomGameView extends javax.swing.JFrame {
    private boolean isHost; 
    public String[] players;
    String question1;
    String correctAnswer1;
    String question2;
    String correctAnswer2;
    String question3;
    String correctAnswer3;
    CDT matchTimer;
    CDT waitingClientTimer;
    
    /**
     * Creates new form GameView
     */
    public RoomGameView() {
        initComponents();
        panel.setVisible(false);
        btnSubmit.setVisible(false);
        pbgTimer.setVisible(false);
        
        if (!isHost) {
            btnStart.setVisible(false); 
        }
    }
    public void updateRoomInfo(String roomName, int remainingSlots, String[] players) {
        jComboBox1.removeAllItems();
        this.players = players;
        jComboBox1.addItem("Danh sách người chơi");
        for(String i:players){
            jComboBox1.addItem(i);
        }
        nameRoom.setText("Tên Phòng: " + roomName);
    }
    public void setQuestion1 (String a, String b, String answerA, String answerB, String answerC, String answerD) {
        question1 = a;
        correctAnswer1 = b;
        lbQuestion1.setText("1. "+a);
        answer1a.setText(answerA);
        answer1b.setText(answerB);
        answer1c.setText(answerC);
        answer1d.setText(answerD);
    }
    
    public void setQuestion2 (String a, String b, String answerA, String answerB, String answerC, String answerD) {
        question2 = a;
        correctAnswer2 = b;
        lbQuestion2.setText("2. " + a);
        answer2a.setText(answerA);
        answer2b.setText(answerB);
        answer2c.setText(answerC);
        answer2d.setText(answerD);
    }
    
    public void setQuestion3 (String a, String b, String answerA, String answerB, String answerC, String answerD) {
        question3 = a;
        correctAnswer3 = b;
        lbQuestion3.setText("3. " + a);
        answer3a.setText(answerA);
        answer3b.setText(answerB);
        answer3c.setText(answerC);
        answer3d.setText(answerD);
    }
     public String getSelectedButton1() {  
        for (Enumeration<AbstractButton> buttons = buttonGroup1.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                    return button.getText();
            }
        }
        return null;
    }
    
    public String getSelectedButton2() {  
        for (Enumeration<AbstractButton> buttons = buttonGroup2.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                    return button.getText();
            }
        }
        return null;
    }
    
    public String getSelectedButton3() {  
        for (Enumeration<AbstractButton> buttons = buttonGroup3.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                    return button.getText();
            }
        }
        return null;
    }
    public void setStartGame (int matchTimeLimit) {
        //answer = false;
        buttonGroup1.clearSelection();
        buttonGroup2.clearSelection();
        buttonGroup3.clearSelection();
        
        btnStart.setVisible(false);
        lbWaiting.setVisible(false);
        panel.setVisible(true);
        btnSubmit.setVisible(true);
        pbgTimer.setVisible(true);
        
        matchTimer = new CDT(matchTimeLimit);
        matchTimer.setTimerCallBack(
                // end match callback
                null,
                // tick match callback
                (Callable) () -> {
                    pbgTimer.setValue(100 * matchTimer.getCurrentTick() / matchTimer.getTimeLimit());
                    pbgTimer.setString("" + CDTF.secondsToMinutes(matchTimer.getCurrentTick()));
                    if (pbgTimer.getString().equals("00:00")) {
                        afterSubmit();
                    }
                    return null;
                },
                // tick interval
                1
        );
    }
    public void afterSubmit() {
        panel.setVisible(false);
        btnSubmit.setVisible(false);
        lbWaiting.setVisible(true);
        lbWaiting.setText("Waiting result from server...");
    }
    
    public boolean isIsHost() {
        return isHost;
    }

    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
    }
    public void setStateHostRoom () {
        btnStart.setVisible(true);
        lbWaiting.setVisible(false);
    }
    
    public void showMessage(String msg){
        JOptionPane.showMessageDialog(this, msg);
    }
    public void pauseTime () {
         // pause timer
         matchTimer.pause();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        nameRoom = new javax.swing.JLabel();
        btnLeaveGame = new javax.swing.JButton();
        pbgTimer = new javax.swing.JProgressBar();
        btnSubmit = new javax.swing.JButton();
        btnStart = new javax.swing.JButton();
        lbWaiting = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        panel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbQuestion1 = new javax.swing.JLabel();
        answer1a = new javax.swing.JRadioButton();
        answer1b = new javax.swing.JRadioButton();
        answer1c = new javax.swing.JRadioButton();
        answer1d = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        lbQuestion2 = new javax.swing.JLabel();
        answer2a = new javax.swing.JRadioButton();
        answer2b = new javax.swing.JRadioButton();
        answer2c = new javax.swing.JRadioButton();
        answer2d = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        lbQuestion3 = new javax.swing.JLabel();
        answer3a = new javax.swing.JRadioButton();
        answer3b = new javax.swing.JRadioButton();
        answer3c = new javax.swing.JRadioButton();
        answer3d = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        nameRoom.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nameRoom.setText("Tên phòng:");

        btnLeaveGame.setBackground(new java.awt.Color(255, 51, 51));
        btnLeaveGame.setForeground(new java.awt.Color(255, 255, 255));
        btnLeaveGame.setText("Leave Game");
        btnLeaveGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaveGameActionPerformed(evt);
            }
        });

        pbgTimer.setStringPainted(true);

        btnSubmit.setText("Nộp");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnStart.setText("Bắt đầu");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        lbWaiting.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lbWaiting.setText("Chờ chủ phòng bắt đầu trò chơi");

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Danh sách người chơi" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Question"));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Question 1"));

        lbQuestion1.setText("1. 61 + 23 = ?");

        buttonGroup1.add(answer1a);
        answer1a.setText("jRadioButton1");

        buttonGroup1.add(answer1b);
        answer1b.setText("jRadioButton2");

        buttonGroup1.add(answer1c);
        answer1c.setText("jRadioButton3");

        buttonGroup1.add(answer1d);
        answer1d.setText("jRadioButton4");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lbQuestion1, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(answer1c)
                            .addComponent(answer1a))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(answer1b, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(answer1d, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(108, 108, 108))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbQuestion1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(answer1a)
                    .addComponent(answer1b, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(answer1c)
                    .addComponent(answer1d))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Question 2"));

        lbQuestion2.setText("1. 61 + 23 = ?");

        buttonGroup2.add(answer2a);
        answer2a.setText("jRadioButton1");

        buttonGroup2.add(answer2b);
        answer2b.setText("jRadioButton2");

        buttonGroup2.add(answer2c);
        answer2c.setText("jRadioButton3");

        buttonGroup2.add(answer2d);
        answer2d.setText("jRadioButton4");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lbQuestion2, javax.swing.GroupLayout.PREFERRED_SIZE, 697, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(answer2b)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(answer2d, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(answer2a)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(answer2c, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(111, 111, 111))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbQuestion2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(answer2a)
                    .addComponent(answer2c))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(answer2b)
                    .addComponent(answer2d)))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Question 3"));

        lbQuestion3.setText("1. 61 + 23 = ?");

        buttonGroup3.add(answer3a);
        answer3a.setText("jRadioButton1");

        buttonGroup3.add(answer3b);
        answer3b.setText("jRadioButton2");

        buttonGroup3.add(answer3c);
        answer3c.setText("jRadioButton3");

        buttonGroup3.add(answer3d);
        answer3d.setText("jRadioButton4");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lbQuestion3, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(answer3b)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(answer3d, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(answer3a)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(answer3c, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(99, 99, 99))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbQuestion3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(answer3a)
                    .addComponent(answer3c))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(answer3b)
                    .addComponent(answer3d)))
        );

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(lbWaiting, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pbgTimer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(nameRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
                        .addComponent(btnLeaveGame, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLeaveGame, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pbgTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbWaiting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        pbgTimer.getAccessibleContext().setAccessibleName("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLeaveGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeaveGameActionPerformed
        if (JOptionPane.showConfirmDialog(RoomGameView.this, "Are you sure want to leave game? You will lose?", "LEAVE GAME", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
            // dùng để gửi thông báo phòng đã bị huỷ
//            ClientRun.socketHandler.leaveGame(competitor);
//            ClientRun.socketHandler.setRoomIdPresent(null);
            if(ClientRun.clientHander.getHost()!=null){
                ClientRun.clientHander.sendData("ROOM_CANCELLED;" + ClientRun.clientHander.getHost() 
                        + ";"+ ClientRun.clientHander.nameRooom);
                ClientRun.clientHander.setHost(null);
                ClientRun.clientHander.setNameRooom(null);
                System.out.println("Host"  + ClientRun.clientHander.getHost());
            }
            else{
                ClientRun.clientHander.sendData("LEAVE_ROOM;"+ClientRun.clientHander.getLoginUser()
                        +";"+ ClientRun.clientHander.nameRooom);
            }
            dispose();
            ClientRun.openScene(ClientRun.SceneName.HOMEVIEW);
            ClientRun.homeView.setUsername(ClientRun.clientHander.getLoginUser());
            ClientRun.homeView.setUserScore(ClientRun.clientHander.getScore());
            ClientRun.clientHander.getListOnline();
        } 
    }//GEN-LAST:event_btnLeaveGameActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        String list = String.join(",", players);
        int remainingTime = matchTimer.getCurrentTick();
        String formattedTime = CDTF.secondsToMinutes(remainingTime);
        ClientRun.clientHander.submitResult(list,formattedTime);
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        String list = String.join(",", players);
        System.out.println(list+"hello");
        ClientRun.clientHander.startRoomGame(list);
    }//GEN-LAST:event_btnStartActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RoomGameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RoomGameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RoomGameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RoomGameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RoomGameView().setVisible(true);
            }
        });
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getCorrectAnswer1() {
        return correctAnswer1;
    }

    public void setCorrectAnswer1(String correctAnswer1) {
        this.correctAnswer1 = correctAnswer1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getCorrectAnswer2() {
        return correctAnswer2;
    }

    public void setCorrectAnswer2(String correctAnswer2) {
        this.correctAnswer2 = correctAnswer2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getCorrectAnswer3() {
        return correctAnswer3;
    }

    public void setCorrectAnswer3(String correctAnswer3) {
        this.correctAnswer3 = correctAnswer3;
    }

    
    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton answer1a;
    private javax.swing.JRadioButton answer1b;
    private javax.swing.JRadioButton answer1c;
    private javax.swing.JRadioButton answer1d;
    private javax.swing.JRadioButton answer2a;
    private javax.swing.JRadioButton answer2b;
    private javax.swing.JRadioButton answer2c;
    private javax.swing.JRadioButton answer2d;
    private javax.swing.JRadioButton answer3a;
    private javax.swing.JRadioButton answer3b;
    private javax.swing.JRadioButton answer3c;
    private javax.swing.JRadioButton answer3d;
    private javax.swing.JButton btnLeaveGame;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnSubmit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lbQuestion1;
    private javax.swing.JLabel lbQuestion2;
    private javax.swing.JLabel lbQuestion3;
    private javax.swing.JLabel lbWaiting;
    private javax.swing.JLabel nameRoom;
    private javax.swing.JPanel panel;
    public static javax.swing.JProgressBar pbgTimer;
    // End of variables declaration//GEN-END:variables
}
