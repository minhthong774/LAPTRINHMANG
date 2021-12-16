/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaiThucHanh3_RMI;

import java.io.Serializable;
import static java.lang.Math.abs;

/**
 *
 * @author ADMIN
 */
public class PhanSo implements Serializable{
    int tu, mau;
    public PhanSo(){
        tu = 0;
        mau = 1;
    }
    public PhanSo(int t, int m){
        tu = t;
        mau = m;
    }
    public PhanSo sum(PhanSo b){
        PhanSo c = new PhanSo();
        c.setTu(tu*b.getMau() + b.getTu() * mau);
        c.setMau(mau*b.getMau());
        return rutGon(c);
    }
    
    public PhanSo rutGon(PhanSo c){
        int a,b;
        a = abs(c.tu);
        b = abs(c.mau);
        if(a<b){
            a=a+b;
            b=a-b;
            a=a-b;
        }
        int temp;
        while(b != 0) {
            temp = a % b;
            a= b;
            b= temp;
        }
        return new PhanSo(c.getTu()/a, c.getMau()/a);
    }

    public int getTu() {
        return tu;
    }

    public void setTu(int tu) {
        this.tu = tu;
    }

    public int getMau() {
        return mau;
    }

    public void setMau(int mau) {
        this.mau = mau;
    }
    
}
