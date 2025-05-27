package View;

import Controller.GiangDayController;
import Model.GiangDay;
import java.util.ArrayList;

public class GiangDayView {
    public static void main(String[] args) {
        ArrayList<GiangDay> list = GiangDayController.getAllGiangDay();
        System.out.println("Danh sách Giảng Dạy:");
        for (GiangDay gd : list) {
            System.out.println(
                "Lớp: " + gd.getMaLop() +
                ", Môn: " + gd.getMaMH() +
                ", GV: " + gd.getMaGV() +
                ", Học kỳ: " + gd.getHocKy() +
                ", Năm: " + gd.getNam() +
                ", Ngày bắt đầu: " + gd.getNgBatDau() +
                ", Ngày kết thúc: " + gd.getNgKetThuc()
            );
        }
    }
}