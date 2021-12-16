/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaiThucHanh3_RMI;
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author minht
 */
public interface CongPhanSoItf extends Remote {
    public PhanSo CongPhanSo(PhanSo a, PhanSo b) throws RemoteException;
}
