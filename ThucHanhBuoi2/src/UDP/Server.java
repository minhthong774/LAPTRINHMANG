/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author minht
 */
public class Server {

    public static final int port = 7;

    public static String bai1(File sourceLocation, File targetLocation)
            throws IOException {

        if (!sourceLocation.exists()) {
            System.out.println("Thu muc nguon khong ton tai!");
        }

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i = 0; i < children.length; i++) {
                bai1(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }

        return "Thao tac thanh cong";
    }
    
    public static String bai2(String name){
        String email;
        int length;
        
        String[] nameSplit = name.toLowerCase().split(" ");
        length=nameSplit.length;
        
        email = nameSplit[length-1];
        
        for(int i = 0; i < length - 1; i++){
            email += nameSplit[i].charAt(0);
        }
        
        email+="@ptithcm.edu.vn";
        
        return email;
    }

    public static void main(String[] args) {
        try {
            DatagramSocket ds = new DatagramSocket(port);
            byte[] buffer = new byte[6000];

            while (true) {
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                ds.receive(incoming);

                String[] theString = new String(incoming.getData(), 0, incoming.getLength()).split("-.-");

                switch (theString[0]) {
                    case "1":{
                        String nguon = theString[1];
                        String dich = theString[2];
                        String fileName = theString[3];
                        File fileNguon = new File(nguon+"/"+fileName);
                        File fileDich = new File(dich);
                        System.out.println(fileNguon);
                        System.out.println(fileDich);
                        
                        String result;
                        if (fileNguon.exists() && fileDich.exists()) {
                            
                            result = bai1(fileNguon, new File(dich+"/"+fileName));
                        }else{
                            result = "file hoac thu muc khong ton tai";
                        }

                        DatagramPacket outsending = new DatagramPacket(
                                result.getBytes(),
                                result.length(),
                                incoming.getAddress(),
                                incoming.getPort());
                        ds.send(outsending);
                    
                        break;
                    }
                    case "2":{
                        String name, result;
                        name = theString[1];
                        System.out.println(name);
                        result = bai2(name);

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
