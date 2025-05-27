/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author REMMY
 */
public class GiangDay {
     private String maLop;
     private String maMH;
     private String maGV;
     private int hocKy ;
     private int nam;
     private String ngBatDau;
     private String ngKetThuc;
     
     public GiangDay(){
         super();
     }

     public String getMaLop() {
         return maLop;
     }

     public void setMaLop(String maLop) {
         this.maLop = maLop;
     }

     public String getMaMH() {
         return maMH;
     }

     public void setMaMH(String maMH) {
         this.maMH = maMH;
     }

     public String getMaGV() {
         return maGV;
     }

     public void setMaGV(String maGV) {
         this.maGV = maGV;
     }

     public int getHocKy() {
         return hocKy;
     }

     public void setHocKy(int hocKy) {
         this.hocKy = hocKy;
     }

     public int getNam() {
         return nam;
     }

     public void setNam(int nam) {
         this.nam = nam;
     }

     public String getNgBatDau() {
         return ngBatDau;
     }

     public void setNgBatDau(String ngBatDau) {
         this.ngBatDau = ngBatDau;
     }

     public String getNgKetThuc() {
         return ngKetThuc;
     }

     public void setNgKetThuc(String ngKetThuc) {
         this.ngKetThuc = ngKetThuc;
     }
}
