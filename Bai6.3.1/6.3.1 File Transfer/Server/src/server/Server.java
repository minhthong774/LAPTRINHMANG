/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import Data.DataClient;
import Data.DataFileSending;
import Data.DataFileServer;
import Data.DataInitFile;
import Data.DataWriter;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import sun.swing.table.DefaultTableCellHeaderRenderer;

/**
 *
 * @author Quang Nhat
 */
public class Server extends javax.swing.JFrame {

    /**
     * Creates new form Server
     */
    public Server() {
        initComponents();
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellHeaderRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                Object data = jtable.getValueAt(i, 0);
                if(data instanceof DataClient){
                    return ((DataClient) data).getStatus();
                }
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1); 
            }
            
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JPopupMenu();
        disconnect = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        cmdStart = new javax.swing.JButton();

        disconnect.setText("Disconnect this Client");
        disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectActionPerformed(evt);
            }
        });
        menu.add(disconnect);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Data", "No", "Name", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setPreferredWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);
            table.getColumnModel().getColumn(1).setPreferredWidth(30);
            table.getColumnModel().getColumn(3).setPreferredWidth(300);
        }

        cmdStart.setText("Start Server");
        cmdStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdStartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(885, Short.MAX_VALUE)
                        .addComponent(cmdStart))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmdStart)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    private final int DEFAULT_PORT = 9999;
    private final List<DataFileServer> listFiles = new ArrayList<>();
    private void cmdStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdStartActionPerformed
        Configuration configuration = new Configuration();
        configuration.setPort(DEFAULT_PORT);
        SocketIOServer server = new SocketIOServer(configuration);
        //Them event vao sv khi client ket noi
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient sioc) {
                //Phuong thuc nay chay khi co client moi ket noi vao
                
                DataClient client = new DataClient(sioc,"",table);
                //Them client vao table
                addTableRow(client);
            }
        });
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient sioc) {
                removeClient(sioc);
            }
        });
        server.addEventListener("set_user", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient sioc, String t, AckRequest ar) throws Exception {
                setUserName(sioc, t);
            }
        });
        server.addEventListener("send_file", DataInitFile.class, new DataListener<DataInitFile>() {
            @Override
            public void onData(SocketIOClient sioc, DataInitFile t, AckRequest ar) throws Exception {
                System.out.println("get data init file");
                int fileID = initFileTransfer(sioc, t);
                if(fileID>0){
                    //goi lai function den client
                    ar.sendAckData(true,fileID);
                }
            }
        });
        server.addEventListener("sending", DataFileSending.class, new DataListener<DataFileSending>() {
            @Override
            public void onData(SocketIOClient sioc, DataFileSending t, AckRequest ar) throws Exception {
                if(!t.isFinish()){
                    writeFile(sioc, t);
                    ar.sendAckData(true);
                }else{
                    //ket thuc gui file
                    //
                    ar.sendAckData(false);
                    DataFileServer data = closeFile(sioc,t);
                    if(data != null){
                        server.getBroadcastOperations().sendEvent("new_file", data);
                    }
                    
                }
            }
        });
        server.addEventListener("r_f_l", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer t, AckRequest ar) throws Exception {
                try {
                    long length = getFileLength(sioc, t);
                    if(length>0){
                        ar.sendAckData(length + "");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        server.addEventListener("request", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient sioc, String t, AckRequest ar) throws Exception {
              if(t.equals("list_file")){
                  ar.sendAckData(listFiles.toArray());
              }
            }
        });
        server.start();
    }//GEN-LAST:event_cmdStartActionPerformed

    private void disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectActionPerformed
       if(table.getSelectedRow()>=0){
           int row = table.getSelectedRow();
           DataClient data = (DataClient)table.getValueAt(row, 0);
           data.getClient().sendEvent("Exit", "");
       }
    }//GEN-LAST:event_disconnectActionPerformed

    private void tableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseReleased
        if(table.getSelectedRow()>=0&&SwingUtilities.isRightMouseButton(evt)){
            menu.show(table, evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tableMouseReleased

    
    private void addTableRow(DataClient data){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(data.toRowTable(table.getRowCount()+1));
    }
    
    private void removeClient(SocketIOClient client){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for(int i=0;i<table.getRowCount();i++){
            DataClient data = (DataClient)table.getValueAt(i, 0);
            if(data.getClient()==client){
                model.removeRow(i);
                break;
            }
        }
    }
    
    private void setUserName(SocketIOClient client, String name){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for(int i=0;i<table.getRowCount();i++){
            DataClient data = (DataClient)table.getValueAt(i, 0);
            if(data.getClient()==client){
                data.setName(name);
                model.setValueAt(name, i, 2);
                break;
            }
        }
    }
    
    private int initFileTransfer(SocketIOClient client,DataInitFile dataInit){
        int id=0;
        for(int i=0;i<table.getRowCount();i++){
            DataClient data = (DataClient)table.getValueAt(i, 0);
            if(data.getClient()==client){
                try {
                    id=generateFileID();
                File file = new File("E:/Test/"+id+"-"+dataInit.getFileName());
                DataWriter writer = new DataWriter(file, dataInit.getFileSize());
                data.addWrite(writer, id);
                    System.out.println("Server Init File");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return id;
    }
    
    private boolean writeFile(SocketIOClient client,DataFileSending file){
        boolean error=false;
        for(int i=0;i<table.getRowCount();i++){
            DataClient data = (DataClient)table.getValueAt(i, 0);
            if(data.getClient()==client){
                try {
                   data.writeFile(file.getData(), file.getFileID());
                } catch (Exception e) {
                    error=true;
                    e.printStackTrace();
                }
                break;
            }
        }
        //tra ve true neu k loi
        return !error;
    }
    
    public DataFileServer closeFile(SocketIOClient client, DataFileSending file){
        DataFileServer fileServer = null;
        for(int i=0;i<table.getRowCount();i++){
            DataClient data = (DataClient)table.getValueAt(i, 0);
            if(data.getClient()==client){
                try {
                   data.closeWriter(file.getFileID());
                   fileServer = data.getDataFileServer(fileID);
                   listFiles.add(fileServer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return fileServer;
    }
    
    private long getFileLength(SocketIOClient client, int fileID) throws IOException{
        for(int i=0;i<table.getRowCount();i++){
            DataClient data = (DataClient)table.getValueAt(i, 0);
            if(data.getClient()==client){
                return data.getFileLength(fileID);
            }
        }
        return 0;
    }
    private int fileID;
    private synchronized int generateFileID(){
        fileID++;
        return fileID;
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
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdStart;
    private javax.swing.JMenuItem disconnect;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu menu;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
