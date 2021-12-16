/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaiThucHanh1;

import java.io.Serializable;

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
        return c;
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
