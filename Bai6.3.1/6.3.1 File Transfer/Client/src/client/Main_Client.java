/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import Data.DataFileServer;
import Data.DataReader;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.Component;
import java.io.File;
import java.net.URISyntaxException;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.json.JSONException;
import org.json.JSONObject;
import sun.swing.table.DefaultTableCellHeaderRenderer;
import swing.CellEditor;
/**
 *
 * @author Quang Nhat
 */
public class Main_Client extends javax.swing.JFrame {

    /**
     * Creates new form Client
     */
    public Main_Client() {
        initComponents();
        model=(DefaultTableModel)table.getModel();
        modelFile =(DefaultTableModel)tableFile.getModel();
        table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellHeaderRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) { 
                Object data =jtable.getValueAt(i, 0);
                if(data instanceof DataReader){
                    DataReader reader = (DataReader) data;
                    return reader.getStatus();
                }else{
                    return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
                }
            }
            
        });
        table.getColumnModel().getColumn(4).setCellEditor(new CellEditor());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btnFile = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        cmdConnect = new javax.swing.JButton();
        txtName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableFile = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnFile.setText("Send File");
        btnFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFileActionPerformed(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "No", "File Name", "Size", "Status"
            }
        ));
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setPreferredWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);
            table.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        cmdConnect.setText("Connect");
        cmdConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdConnectActionPerformed(evt);
            }
        });

        jLabel1.setText("Name");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmdConnect)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnFile)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdConnect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFile)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Client", jPanel1);

        tableFile.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "No", "File name", "File Size", "Status"
            }
        ));
        jScrollPane2.setViewportView(tableFile);
        if (tableFile.getColumnModel().getColumnCount() > 0) {
            tableFile.getColumnModel().getColumn(0).setMinWidth(0);
            tableFile.getColumnModel().getColumn(0).setPreferredWidth(0);
            tableFile.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("File on Server", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private final int DEFAULT_PORT=9999; //Phai trung voi port cua sv
    private final DefaultTableModel model;
    private final DefaultTableModel modelFile;
    private Socket client;
    private String IP = "localhost";
    private void cmdConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdConnectActionPerformed
        try {
              client =IO.socket("http://"+IP+":"+DEFAULT_PORT);
              //Them event vao client
              client.on("Exit", new Emitter.Listener() {
                  @Override
                  public void call(Object... os) {
                     System.exit(0);
                  }
              });
              client.on("new_file",new Emitter.Listener() {
                  @Override
                  public void call(Object... os) {
                     //them file moi
                      try {
                            addFile(new DataFileServer((JSONObject)os[0]));
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              });
            client.open();
            String userName = txtName.getText().trim();
            client.emit("set_user", userName);
            client.emit("request","list_file", new Ack() {
                  @Override
                  public void call(Object... os) {
                      try {
                           for(Object o:os){
                          addFile(new DataFileServer((JSONObject) o));
                        }
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }
              });
        } catch (URISyntaxException e) {
            System.err.print(e);
        }
    }//GEN-LAST:event_cmdConnectActionPerformed

    private void btnFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFileActionPerformed
        JFileChooser ch = new JFileChooser();
        ch.setMultiSelectionEnabled(true);
        int opt = ch.showOpenDialog(this);
        if(opt==JFileChooser.APPROVE_OPTION){
            File[] files = ch.getSelectedFiles();
            for(File file :files){
                try {
                    DataReader reader = new DataReader(file,table);
                    model.addRow(reader.toRowTable(table.getRowCount()+1));
                    reader.startSend(client);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btnFileActionPerformed

    private void addFile(DataFileServer data){
        modelFile.addRow(data.toTableRow(tableFile.getRowCount() +1));
    }
    
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
            java.util.logging.Logger.getLogger(Main_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main_Client().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFile;
    private javax.swing.JButton cmdConnect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable table;
    private javax.swing.JTable tableFile;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
