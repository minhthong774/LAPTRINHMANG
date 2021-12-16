package quanlitaikhoan;

import sqlServer.*;
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
    
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9999);
        System.out.println("server đã chạy!");
        
        while(true){
            Socket client = server.accept();

            try{
                DataInputStream din = new DataInputStream(client.getInputStream());
                DataOutputStream dout = new DataOutputStream(client.getOutputStream());

                String matt, tenthongtin, matkhau;

                matt = din.readUTF();
                tenthongtin = din.readUTF();
                matkhau = din.readUTF();

                DBAccess acc = new DBAccess();

                int result = acc.Update("insert into thongtin (matt, tenthongtin, matkhau) values(N'" + matt + "'," + "N'" + tenthongtin + "'," + "N'" + matkhau + "')");
                if(result!=0){
                    dout.writeUTF("Them Thanh Cong!!!" + "Mã thông tin:" + matt + ", Tên thông tin: "+ tenthongtin + ", Mật Khẩu: " + matkhau);
                }else{
                    dout.writeUTF("Them That Bai!!!");
                }
            }catch(Exception e){
                System.out.println(e);
            }

            client.close();
        }
    }
}
