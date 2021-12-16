/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaiThucHanh1;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author ADMIN
 */
public class Calc extends UnicastRemoteObject implements InterfaceCalc{

    public Calc() throws RemoteException{
        
    }
    @Override
    public int sum(int a, int b) throws RemoteException {
        return a + b;
    }

    @Override
    public PhanSo sum(PhanSo a, PhanSo b) throws RemoteException {
         return a.sum(b);
    }
    
}
