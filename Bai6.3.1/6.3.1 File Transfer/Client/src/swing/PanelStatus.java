/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import java.awt.event.ActionListener;

/**
 *
 * @author Quang Nhat
 */
public class PanelStatus extends javax.swing.JPanel {

    /**
     * Creates new form PanelStatus
     */
    public PanelStatus() {
        initComponents();
    }
    public void showStatus(int values){
        pro.setValue(values);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pro = new javax.swing.JProgressBar();
        cmd = new javax.swing.JButton();

        pro.setStringPainted(true);

        cmd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pause.png"))); // NOI18N
        cmd.setContentAreaFilled(false);
        cmd.setName("R"); // NOI18N
        cmd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pro, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmd, javax.swing.GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pro, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
            .addComponent(cmd, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        cmd.getAccessibleContext().setAccessibleDescription("R");
    }// </editor-fold>//GEN-END:initComponents

    private void cmdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdActionPerformed
        if(cmd.getName().equals("R")){
            cmd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/resume.png")));
            cmd.setName("P");
        }else if(cmd.getName().equals("P")){
            cmd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pause.png")));
            cmd.setName("R");
        }
        event.actionPerformed(evt);
    }//GEN-LAST:event_cmdActionPerformed

    public void done(){
        cmd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/done.png")));
        cmd.setName("D");
    }
    
    public boolean isPause(){
        return cmd.getName().equals("P");
    }
    
    private ActionListener event;
    public void addEvent(ActionListener event){
      this.event = event;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmd;
    private javax.swing.JProgressBar pro;
    // End of variables declaration//GEN-END:variables
}
