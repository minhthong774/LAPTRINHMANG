package UDP;

import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.*;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author minht
 */
public class Server {
    public static final int port = 4562;
    
    public static String dangNhap(String username, String password){
        try{
            DBAccess acc = new DBAccess();
            ResultSet rs = acc.Query("select * from Login where [User] = '"+username+"' and Password = N'"+password+"'");
            if(rs.next()){
                return "1";
            } else {
                return "0";
            }
        }catch(Exception ex){
            return "-1";
        }
    }
    
    public static String dangKy(String username, String password){
        try{
            DBAccess acc = new DBAccess();
            int kq = acc.Update("insert into Login ([User], Password) values(N'"+username+"', N'"+password+"')");
            if(kq!=0){
                return "1";
            } else {
                return "0";
            }
        }catch(Exception ex){
            return "-1";
        }
    }
    
    public static String timDuongDi(String filePath){
        try{
            int n = 0, m = 0;
            int[][] matrix = new int[100][100];
            List<String> list = new ArrayList<String>();
            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
              list.add(sc.nextLine());
            }
            for(String line: list){
                line = line.trim().replaceAll(" +", " ");
                String[] arrLine = line.split(" ");
                if(n>0 && m != arrLine.length) return "0";
                m = arrLine.length;
                for(int i=0; i < m;i++){
                    matrix[n][i] = Integer.parseInt(arrLine[i]);
                }
                n+=1;
            }
            
            int row = 0, col = 0, sum=0;
            
            String result = "";
            while(row < n-1 || col < m-1){
                Integer left, bottom, bottomleft, min;
                if(row >= n-1){
                    left = matrix[row][col + 1];
                    sum+= left;
                    result+= left.toString()+" ";
                    row=row;
                    col=col+1;
                    continue;
                }
                    
                if(col >= m-1){
                    bottom = matrix[row+1][col];
                    sum+=bottom;
                    result+=bottom.toString()+" ";
                    row=row+1;
                    col=col;
                    continue;
                }
                
                left = matrix[row][col + 1];
                bottom = matrix[row+1][col];
                bottomleft = matrix[row+1][col + 1];
                
                if(left<bottom && left < bottomleft){
                    sum+=left;
                    result+=left.toString()+" ";
                    row=row;
                    col=col+1;
                    continue;
                }
                
                if(bottom < bottomleft){
                    sum+=bottom;
                    result+=bottom.toString()+" ";
                    row=row+1;
                    col=col;
                    continue;
                }else{
                    sum+=bottomleft;
                    result+=bottomleft.toString()+" ";
                    row=row+1;
                    col=col+1;
                    continue;
                }
            }
            
            sc.close();
            return result + "= " + sum;
          } catch (FileNotFoundException e) {
              return "-1";
         }
    }
    
    public static void main(String[] args) throws IOException {
        try {
            DatagramSocket ds = new DatagramSocket(port);
            byte[] buffer = new byte[6000];

            while (true) {
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                ds.receive(incoming);

                String[] theString = new String(incoming.getData(), 0, incoming.getLength()).split("-.-");

                switch (theString[0]) {
                    case "1":{
                        String username = theString[1];
                        String password = theString[2];
                        
                        String result = dangNhap(username, password);
                        DatagramPacket outsending = new DatagramPacket(
                                result.getBytes(),
                                result.length(),
                                incoming.getAddress(),
                                incoming.getPort());
                        ds.send(outsending);
                    
                        break;
                    }
                    case "2":{
                        String username = theString[1];
                        String password = theString[2];
                        
                        String result = dangKy(username, password);
                        DatagramPacket outsending = new DatagramPacket(
                                result.getBytes(),
                                result.length(),
                                incoming.getAddress(),
                                incoming.getPort());
                        ds.send(outsending);
                        break;
                    }
                    case "3":{
                        String filePath = theString[1];
                        
                        String result = timDuongDi(filePath);
                        DatagramPacket outsending = new DatagramPacket(
                                result.getBytes(),
                                result.length(),
                                incoming.getAddress(),
                                incoming.getPort());
                        ds.send(outsending);
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
