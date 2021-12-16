/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaiThucHanh3_RMI;
import java.rmi.registry.*;
/**
 *
 * @author minht
 */
public class Server {
    public static void main(String[] args) {
        try{
            CongPhanSo c = new CongPhanSo();
            Registry r = LocateRegistry.createRegistry(3456);
            r.bind("CongPhanSo", c);
        }catch(Exception e){
            System.out.print(e);
        }
    }
}
