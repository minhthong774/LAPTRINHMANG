/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quanlitaikhoan;
import java.util.*;
import java.io.*;
import java.net.*;
/**
 *
 * @author minht
 */
public class Client {
    
    public static void main(String[] args) throws IOException {
        while(true){
            Socket client = new Socket("localhost", 9999);
            try{
                DataInputStream din = new DataInputStream(client.getInputStream());
                DataOutputStream dout = new DataOutputStream(client.getOutputStream());

                Scanner sc = new Scanner(System.in);

                String matt, tenthongtin, matkhau;
                System.out.print("Nhập mã thông tin: ");
                matt = sc.nextLine();
                System.out.print("Nhập tên thông tin: ");
                tenthongtin = sc.nextLine();
                System.out.print("Nhập mật khẩu: ");
                matkhau = sc.nextLine();

                dout.writeUTF(matt);
                dout.writeUTF(tenthongtin);
                dout.writeUTF(matkhau);

                String result = din.readUTF();
                System.out.println(result);
                client.close();
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
