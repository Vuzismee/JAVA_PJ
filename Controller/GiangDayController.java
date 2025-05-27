package Controller;

import Model.GiangDay;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GiangDayController {
    public static ArrayList<GiangDay> getAllGiangDay() {
        ArrayList<GiangDay> list = new ArrayList<>();
        try {
            Connection conn = ConnectSQL.getConnection();
            if (conn != null) {
                String sql = "SELECT * FROM GiangDay";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    GiangDay gd = new GiangDay();
                    gd.setMaLop(rs.getString("MALOP"));
                    gd.setMaMH(rs.getString("MAMH"));
                    gd.setMaGV(rs.getString("MAGV"));
                    gd.setHocKy(rs.getInt("HOCKY"));
                    gd.setNam(rs.getInt("NAM"));
                    gd.setNgBatDau(rs.getString("TUNGAY"));
                    gd.setNgKetThuc(rs.getString("DENNGAY"));
                    list.add(gd);
                }
                rs.close();
                st.close();
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}