/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import com.corundumstudio.socketio.SocketIOClient;
import java.awt.Component;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JTable;
import swing.PanelStatus;


public class DataClient {

    /**
     * @return the status
     */
    public PanelStatus getStatus() {
        return status;
    }

   
    public SocketIOClient getClient() {
        return client;
    }

    
    public void setClient(SocketIOClient client) {
        this.client = client;
    }

   
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }
    
    public DataClient(SocketIOClient client, String name, JTable table){
      this.client = client;
      this.name = name;
      this.status = new PanelStatus();
      this.table =table;
    }
    public DataClient(){
    }
    
    private SocketIOClient client;
    private String name;
    //Key inter la file ID
    //Dung Hash de luu tru nhieu file duoc truyen
    private final HashMap<Integer,DataWriter> list = new HashMap<>();
    private PanelStatus status;
    private JTable table;
    
    public void addWrite(DataWriter data, int fileID){
        list.put(fileID, data);
        status.addItem(fileID, data.getFile().getName(), data.getMaxFileSize());
        autoRowHeight(table, 3);
    }
    public void writeFile(byte [] data, int fileID)throws IOException{
        list.get(fileID).writeFile(data);
        status.updateStatus(fileID, (int) list.get(fileID).getPercentage());
        table.repaint();
    }
    
    public void closeWriter(int fileID)throws IOException{
        list.get(fileID).close();
    }
    public Object[] toRowTable (int row){
        return new Object[]{this,row,name};
    }
    
    private void autoRowHeight(JTable table, int... cols){
        for(int row =0; row<table.getRowCount(); row ++){
            int rowHeight = table.getRowHeight();
            for(int col: cols){
                Component comp = table.prepareRenderer(table.getCellRenderer(row, col), row, col);
                if(comp.getPreferredSize().height>rowHeight){
                    rowHeight = comp.getPreferredSize().height;
                }
            }
            table.setRowHeight(row,rowHeight);
        }
    }
    
    public long getFileLength(int fileID) throws IOException{
        return list.get(fileID).getFileLength();
    }
    
    public DataFileServer getDataFileServer(int fileID){
        DataWriter data = list.get(fileID);
        String fileName = data.getFile().getName();
        return new DataFileServer(fileID, fileName.substring(fileName.indexOf("-", 0) +1 , fileName.length()), data.getMaxFileSize(), data.getFile());
    }
}
