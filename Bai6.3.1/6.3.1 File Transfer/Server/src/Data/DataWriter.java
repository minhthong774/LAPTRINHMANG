/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

/**
 *
 * @author Quang Nhat
 */
public class DataWriter {

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

    public DataWriter(File file, long fileSize) throws IOException{
        //rw la mode "doc va viet- Read and Write"
        accFile = new RandomAccessFile(file, "rw");
        this.file = file;
        this.fileSize = fileSize;
     
    }
    
    
    private File file;
    private long fileSize;
    private RandomAccessFile accFile;
    
    public synchronized long writeFile(byte [] data) throws IOException{
        accFile.seek(accFile.length());
        accFile.write(data);
        return accFile.length();
    }
    
    public void close() throws IOException{
        accFile.close();
    }
    
    public String getMaxFileSize(){
       return convertFile(fileSize);
    }
    
   public String getCurrentFileSize()throws IOException{
       return convertFile(accFile.length());
   }
    public double getPercentage()throws IOException{
        double percentage;
        long filePointer=accFile.length();
        percentage=filePointer*100/fileSize;
        return percentage;
    }
    
    public long getFileLength() throws IOException{
        return accFile.length();
    }
     public String convertFile(double bytes){
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
}
