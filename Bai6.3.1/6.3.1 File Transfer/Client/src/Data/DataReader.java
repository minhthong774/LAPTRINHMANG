/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import io.socket.client.Ack;
import io.socket.client.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import javax.swing.JTable;
import org.json.JSONException;
import org.json.JSONObject;
import swing.PanelStatus;

/**
 *
 * @author Quang Nhat
 */
public class DataReader {

    /**
     * @return the status
     */
    public PanelStatus getStatus() {
        return status;
    }

    /**
     * @return the fileID
     */
    public int getFileID() {
        return fileID;
    }

    /**
     * @param fileID the fileID to set
     */
    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @return the fileSize
     */
    public long getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the accFile
     */
    public RandomAccessFile getAccFile() {
        return accFile;
    }

    /**
     * @param accFile the accFile to set
     */
    public void setAccFile(RandomAccessFile accFile) {
        this.accFile = accFile;
    }

    public DataReader(File file, JTable table) throws IOException{
        //r la mode "chi doc - read only"
        accFile = new RandomAccessFile(file,"r");
        this.file = file;
        this.fileSize = accFile.length();
        this.fileName = file.getName() ;
        this.status = new PanelStatus();
        this.status.addEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!status.isPause()&&pause){
                    pause = false;
                    client.emit("r_f_l", fileID, new Ack() {
                        @Override
                        public void call(Object... os) {
                            if(os.length>0){
                                long length = Long.valueOf(os[0].toString());
                                try {
                                      accFile.seek(length);
                                      sendingFile(client);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } );
                }
            }
        });
        this.table=table;
    }
        
    private int fileID;
    private File file;
    private long fileSize;
    private String fileName;
    private RandomAccessFile accFile;
    private PanelStatus status;
    private Socket client;
    private JTable table;
    
    public synchronized byte[] readFile() throws IOException{
        long filePointer = accFile.getFilePointer();
        if(filePointer!=fileSize){
            int max =2000;//2000 la gioi han moi lan gui
            long length=filePointer+max>=fileSize?fileSize-filePointer:max;
            byte[] data = new byte[(int) length];
            accFile.read(data);
            return data;
        }else{
            return null;
        }
    }
    
    public void close() throws IOException{
        accFile.close();
    }
    
    public String getFileSizeConverted(){
        double bytes = fileSize;
        String[] fileSizeUnits = {"bytes","KB","MB","GB","TB","EB","ZB","YB"};
        String sizeToReturn;
        DecimalFormat df = new DecimalFormat("0.#");
        int index;
        for(index = 0;index<fileSizeUnits.length;index++){
            if(bytes<1024){
                break;
            }
            bytes = bytes/1024;
        }
        sizeToReturn = df.format(bytes)+""+fileSizeUnits[index];
        return sizeToReturn;
    }
    
    public double getPercentage()throws IOException{
        double percentage;
        long filePointer=accFile.getFilePointer();
        percentage=filePointer*100/fileSize;
        return percentage;
    }
    
    public Object[] toRowTable (int no){
        return new Object[]{this,no,fileName,getFileSizeConverted(),"Next Update"};
    }
    
    public void startSend(Socket socket)throws JSONException{
        this.client = socket;
        JSONObject data = new JSONObject();
        data.put("fileName", fileName);
        data.put("fileSize",fileSize);
        socket.emit("send_file", data, new Ack() {
            @Override
            public void call(Object... os) {
                  //Tra lai function
                  //index 0 boolean, index 1 fileID
                  if(os.length>0){
                      boolean action = (boolean)os[0];
                      if(action){
                          //tao fileid 
                          fileID=(int)os[1];
                          //bat dau gui file
                          try {
                              sendingFile(socket);
                          } catch (Exception e) {
                              e.printStackTrace();
                          }
                          
                      }
                  }
            }
        });
    }
    
    private boolean pause = false;
    private void sendingFile(Socket socket)throws IOException,JSONException{
        JSONObject data = new JSONObject();
        data.put("fileID", fileID);
        byte[] bytes =readFile();
        if(bytes!=null){
            data.put("data",bytes);
            data.put("finish",false);
        }else{
            data.put("finish",true);
            close(); //dong file
            status.done();
        }
        socket.emit("sending",data, new Ack() {
            @Override
            public void call(Object... os) {
                //goi lai function de gui nhieu file
                //function nay cho biet sv da nhan duoc cac file da gui
                if(os.length>0){
                    boolean act = (boolean) os[0];
                    if(act){
                        try {
                            if(!status.isPause()){
                                showStatus( (int) getPercentage());
                                sendingFile(socket);
                            }else{
                                pause= true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    
    public void showStatus(int values ){
        status.showStatus(values);
        table.repaint();
    }
}
