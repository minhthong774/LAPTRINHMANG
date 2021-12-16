/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaiThucHanh3_RMI;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 *
 * @author minht
 */
public class CongPhanSo extends UnicastRemoteObject implements CongPhanSoItf{

    public CongPhanSo() throws RemoteException {
        super();
    }

    @Override
    public PhanSo CongPhanSo(PhanSo a, PhanSo b) throws RemoteException {
        return a.sum(b);
    }
    
}
