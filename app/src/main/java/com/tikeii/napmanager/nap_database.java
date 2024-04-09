package com.tikeii.napmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class nap_database extends SQLiteOpenHelper {

    // TẠO CÁC BIẾN CHỨA THUỘC TÍNH CỦA BẢNG NHANVIEN

    private static final String TB_NV = "NHANVIEN";
    private static final String TB_NV_MANV = "MANHANVIEN";
    private static  String TB_NV_MK = "MATKHAUDANGNHAP";
    private static String TB_NV_TENNV = "HOTEN";
    private static String TB_NV_GT = "GIOITINH";
    private static String TB_NV_NGAYSINH = "NGAYSINH";
    private static String TB_NV_SDT = "SODIENTHOAI";
    private static String TB_NV_DIACHI = "DIACHI";
    private static String TB_NV_NGAYLAMVIEC = "NGAYLAMVIEC";
    private static String TB_NV_LUONGCB = "LUONGCOBAN";

    // LỆNH TẠO BẢNG NHANVIEN
    private String CreateTB_NHANVIEN = "CREATE TABLE " + TB_NV + "(" + TB_NV_MANV + " TEXT PRIMARY KEY, " + TB_NV_MK + " TEXT, " + TB_NV_TENNV + " TEXT, " +
            TB_NV_GT + " TEXT, " + TB_NV_NGAYSINH + " DATE, " + TB_NV_SDT + " TEXT, " + TB_NV_DIACHI + " TEXT, " + TB_NV_NGAYLAMVIEC + " DATE, " +
            TB_NV_LUONGCB + " TEXT, " + TB_CV_MACV + " TEXT REFERENCES " + TB_CHUCVU + "(" + TB_CV_MACV + "), " + TB_TD_MATD + " TEXT REFERENCES " + TB_TRINHDO + "(" + TB_TD_MATD + "), " +
            TB_PB_MAPB + " TEXT REFERENCES " + TB_PHONGBAN + "(" + TB_PB_MAPB + "))";


    // TẠO CÁC BIẾN CHỨA CÁC THUỘC TÍNH CỦA BẢN QUAN LY
    private static String TB_QUANLY = "QUANLY";
    private static String TB_QL_MAQL = "MAQUANLY";
    private static String TB_QL_MK = "MATKHAUDANGNHAP";
    private static String TB_QL_TENQL = "HOTENQUANLY";

    // LỆNH TẠO BẢNG QUANLY
    private String CreateTB_QUANLY = "CREATE TABLE " + TB_QUANLY + "(" + TB_QL_MAQL + " TEXT PRIMARY KEY, " + TB_QL_MK + " TEXT, " + TB_QL_TENQL + " TEXT, " + TB_CV_MACV + " TEXT REFERENCES " + TB_CHUCVU + "(" + TB_CV_MACV + "), " +
            TB_PB_MAPB + " TEXT REFERENCES " + TB_PHONGBAN + "(" + TB_PB_MAPB + "))";

    // TẠO CÁC BIẾN CHỨA CÁC THUỘC TÍNH CỦA BẢN CHUCVU
    private static String TB_CHUCVU = "CHUCVU";
    private static String TB_CV_MACV = "MACHUCVU";
    private static String TB_CV_TENCHUCVU = "TENCHUCVU";

    // LỆNH TẠO BẢNG CHUCVU
    private String CreateTB_CHUCVU = "CREATE TABLE " + TB_CHUCVU + "(" + TB_CV_MACV + " TEXT PRIMARY KEY, " + TB_CV_TENCHUCVU + " TEXT)";

    // TẠO CÁC BIẾN CHỨA CÁC THUỘC TÍNH CỦA BẢN PHONGBAN
    private static String TB_PHONGBAN = "PHONGBAN";
    private static String TB_PB_MAPB = "MAPHONGBAN";
    private static String TB_PB_TENPB = "TENPHONGBAN";

    // LỆNH TẠO BẢNG PHONGBAN
    private String CreateTB_PHONGBAN = "CREATE TABLE " + TB_PHONGBAN + "(" + TB_PB_MAPB + " TEXT PRIMARY KEY ," + TB_PB_TENPB + " TEXT)";

    // TẠO CÁC BIẾN CHỨA CÁC THUỘC TÍNH CỦA BẢNG TRINHDOHOCVAN
    private static String TB_TRINHDO = "TRINHDO";
    private static String TB_TD_MATD = "MATRINHDO";
    private static String TB_TD_TENTD = "TENTRINHDO";

    // LỆNH TẠO BẢNG TRINHDOHOCVAN

    private String CreateTB_TRINHDO = "CREATE TABLE " + TB_TRINHDO + "(" + TB_TD_MATD + " TEXT PRIMARY KEY, " + TB_TD_TENTD + " TEXT)";

    // TẠO CÁC BIẾN CHỨA CÁC THUỘC TÍNH CỦA BẢNG CHAMCONG
    private static String TB_CHAMCONG = "CHAMCONG";
    private static String TB_CC_THANG = "THANG";
    private static String TB_CC_NGAYLAM = "NGAYLAM";
    private static String TB_CC_GIOVAO = "GIOVAOCA";
    private static String TB_CC_NGAYCONG = "NGAYCONG";
    private static String TB_CC_NGAYPHEP = "NGAYPHEP";
    private static String TB_CC_TANGCA = "TANGCA";

    //LỆNH TẠO BẢNG CHAMCONG
    private String CreateTB_CHAMCONG = "CREATE TABLE " + TB_CHAMCONG + "(" + TB_CC_THANG + " TEXT , " + TB_CC_NGAYLAM + " DATE," +
            TB_CC_GIOVAO + " DATETIME," + TB_CC_NGAYCONG + " INT, " + TB_CC_NGAYPHEP + " INT, " + TB_CC_TANGCA + " INT ,"+TB_NV_MANV+" TEXT REFERENCES "+TB_NV+"("+TB_NV_MANV+"))";


    //TẠO CÁC BIẾN CHỨA CÁC THUỘC TÍNH CỦA BẢNG LUONH
    private static String TB_LUONG = "LUONG";
    private static String TB_L_THANGLUONG = "THANGLUONG";
    private static String TB_L_TAMTINH = "LUONGTAMTINH";
    private static String TB_L_LUONGTHUONG = "LUONGTHUONG";
    private static String TB_L_LUONGPHAT = "LUONGPHAT";
    private static String TB_L_THUCLANH = "THUCLANH";
    private static String TB_L_GHICHU =  "GHICHU";
    // LỆNH TẠO BẢNG LUONG
    private String CreateTB_LUONG = "CREATE TABLE " + TB_LUONG + "("+TB_L_THANGLUONG+" DATE PRIMARY KEY, " + TB_NV_MANV + " TEXT REFERENCES " + TB_NV + "(" + TB_NV_MANV + "), "+
            TB_L_TAMTINH + " INT ," + TB_L_LUONGTHUONG + " INT," + TB_L_LUONGPHAT + " INT ,"+TB_L_THUCLANH+" INT,"+TB_L_GHICHU+" TEXT)";


    public nap_database(@Nullable Context context) {
        super(context, "NAP_DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        DB.execSQL(CreateTB_PHONGBAN);
        DB.execSQL(CreateTB_TRINHDO);
        DB.execSQL(CreateTB_CHUCVU);
        DB.execSQL(CreateTB_NHANVIEN);
        DB.execSQL(CreateTB_QUANLY);
        DB.execSQL(CreateTB_CHAMCONG);
        DB.execSQL(CreateTB_LUONG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //-------------------------------------------------------XÂY DỰNG SỰ KIỆN Ở CÁC BẢNG---------------------------------------------------
    //                   BẢNG NHAN VIEN
    //-------------------sự kiện thêm nhân viên------------------------------
    public Boolean insertNHANVIEN(String manv, String matkhau, String hoten, String gioitinh, String ngaysinh, String sdt, String diachi,
                                  String ngaylamviec, String luongcb, String macv, String matd, String mapb) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MANHANVIEN", manv);
        values.put("MATKHAUDANGNHAP", matkhau);
        values.put("HOTEN", hoten);
        values.put("GIOITINH", gioitinh);
        values.put("NGAYSINH", ngaysinh);
        values.put("SODIENTHOAI", sdt);
        values.put("DIACHI", diachi);
        values.put("NGAYLAMVIEC", ngaylamviec);
        values.put("LUONGCOBAN", luongcb);
        values.put("MACHUCVU", macv);
        values.put("MATRINHDO", matd);
        values.put("MAPHONGBAN", mapb);
        Long kq = DB.insert("NHANVIEN", null, values);
        if (kq == -1) {
            return false;
        } else {
            return true;
        }

    }

    //-----------------sự kiện update nhân viên

    public Boolean updateNHANVIEN(String manv, String matkhau, String hoten, String gioitinh, String ngaysinh, String sdt, String diachi,
                                  String ngaylamviec, String luongcb, String macv, String matd, String mapb) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MANHANVIEN", manv);
        values.put("MATKHAUDANGNHAP", matkhau);
        values.put("HOTEN", hoten);
        values.put("GIOITINH", gioitinh);
        values.put("NGAYSINH", ngaysinh);
        values.put("SODIENTHOAI", sdt);
        values.put("DIACHI", diachi);
        values.put("NGAYLAMVIEC", ngaylamviec);
        values.put("LUONGCOBAN", luongcb);
        values.put("MACHUCVU", macv);
        values.put("MATRINHDO", matd);
        values.put("MAPHONGBAN", mapb);
        Cursor cursor = DB.rawQuery("SELECT * FROM NHANVIEN WHERE MANHANVIEN = ?", new String[]{manv});
        if (cursor.getCount() > 0) {
            long kq = DB.update("NHANVIEN", values, "MANHANVIEN = ?", new String[]{manv});
            if (kq == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }


    }



    //--------------------------sự kiên xóa nhân viên-----------------------
    public Boolean deleteNHANVIEN(String manv) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM NHANVIEN WHERE MANHANVIEN = ?", new String[]{manv});
        if (cursor.getCount() > 0) {
            long kq = DB.delete("NHANVIEN", "MANHANVIEN = ?", new String[]{manv});
            if (kq == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //--------------------------sự kiên kiểm tra đăng nhập-----------------------
    public Boolean checkLG_staff(String user, String pass) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM NHANVIEN WHERE MANHANVIEN = ? AND MATKHAUDANGNHAP = ?",new String[]{user,pass});
        if (cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }

    public Cursor getdata_staff_withID(String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM NHANVIEN WHERE MANHANVIEN = ?",new String[]{id});
        return cursor;
    }

    public Cursor getdata_staff_stafflist() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT a.HOTEN,a.MANHANVIEN, b.TENCHUCVU FROM NHANVIEN a, CHUCVU b WHERE a.MACHUCVU = b.MACHUCVU",null);
        return cursor;
    }
    public Cursor getdata_staff_stafflist_ofRoom(String mapb) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT a.HOTEN,a.MANHANVIEN, b.TENCHUCVU FROM NHANVIEN a, CHUCVU b , PHONGBAN c WHERE a.MACHUCVU = b.MACHUCVU AND a.MAPHONGBAN = c.MAPHONGBAN AND c.MAPHONGBAN = ?",new String[]{mapb});
        return cursor;
    }

    public Cursor  getdata_staff_with_ID(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT a.HOTEN, a.MANHANVIEN, a.GIOITINH, a.NGAYSINH, a.SODIENTHOAI, a.DIACHI, a.NGAYLAMVIEC, a.LUONGCOBAN, b.TENCHUCVU, c.TENPHONGBAN, d.TENTRINHDO "+
                "FROM NHANVIEN a, CHUCVU b, PHONGBAN c, TRINHDO d " +
                "WHERE a.MACHUCVU = B.MACHUCVU AND a.MAPHONGBAN = c.MAPHONGBAN AND a.MATRINHDO = d.MATRINHDO AND a.MANHANVIEN = ?", new String[]{id});
        return cursor;
    }

    public Cursor  getdatastaff_with_ID_salary_cal(String id, String thang) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT a.HOTEN, b.TENCHUCVU, c.TENPHONGBAN , a.LUONGCOBAN, SUM(d.NGAYCONG) AS NCONG, SUM(d.NGAYPHEP) AS NPHEP, SUM(d.TANGCA) AS TCA "+
                "FROM NHANVIEN a, CHUCVU b, PHONGBAN c, CHAMCONG d " +
                "WHERE a.MACHUCVU = B.MACHUCVU AND a.MAPHONGBAN = c.MAPHONGBAN AND a.MANHANVIEN = d.MANHANVIEN AND a.MANHANVIEN = ? AND d.THANG = ? ", new String[]{id,thang});
        return cursor;
    }
    public Cursor  getdatastaff_with_ID(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT a.HOTEN, b.TENCHUCVU, c.TENPHONGBAN "+
                "FROM NHANVIEN a, CHUCVU b, PHONGBAN c " +
                "WHERE a.MACHUCVU = B.MACHUCVU AND a.MAPHONGBAN = c.MAPHONGBAN AND a.MANHANVIEN = ?", new String[]{id});
        return cursor;
    }

    public Cursor  getdatastaff_with_ID_salary_see(String id, String thang) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT a.HOTEN, a.LUONGCOBAN, c.LUONGTHUONG, c.LUONGPHAT, c.THUCLANH, SUM(b.NGAYCONG) AS NCONG, SUM(b.NGAYPHEP) AS NPHEP, SUM(b.TANGCA) AS TCA"+
                " FROM NHANVIEN a, CHAMCONG b, LUONG c " +
                "WHERE a.MANHANVIEN = b.MANHANVIEN AND a.MANHANVIEN = c.MANHANVIEN AND a.MANHANVIEN = ? AND b.THANG = ? ", new String[]{id,thang});
        return cursor;
    }



//--------------------------------------------------------------------------------------------------
    //------------------------------bảng QUANLY-----------------------------------------------------
/*
private static String TB_QUANLY = "QUANLY";
    private static String TB_QL_MAQL = "MAQUANLY";
    private static String TB_QL_MK = "MATKHAUDANGNHAP";
    private static String TB_QL_TENQL = "HOTENQUANLY";
 */
    //---Sự kiện thêm quản lý--
    public boolean insertQUANLY(String maql, String mk, String hotenql, String macv, String maphongban) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MAQUANLY",maql);
        values.put("MATKHAUDANGNHAP",mk);
        values.put("HOTENQUANLY",hotenql);
        values.put("MACHUCVU",macv);
        values.put("MAPHONGBAN",maphongban);
        long kq = db.insert("QUANLY",null,values);
        if (kq == -1) {
            return false;
        } else {
            return true;
        }
    }

    //---Sự kiên chỉnh sửa quản lý--
    public Boolean updateQUANLY(String maql,String mk, String hotenql, String macv, String maphongban) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MATKHAUDANGNHAP",mk);
        values.put("HOTENQUANLY",hotenql);
        values.put("MACHUCVU",macv);
        values.put("MAPHONGBAN",maphongban);
        Cursor cursor = DB.rawQuery("SELECT * FROM QUANLY WHERE MAQUANLY = ?", new String[]{maql});
        if (cursor.getCount() > 0) {
            long kq = DB.update("QUANLY", values, "MAQUANLY = ?", new String[]{maql});
            if (kq == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //---Sự kiện xóa quản lý--
    public Boolean deleteQUANLY(String maql) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM QUANLY WHERE MAQUANLY = ?", new String[]{maql});
        if (cursor.getCount() > 0) {
            long kq = DB.delete("QUANLY", "MAQUANLY = ?", new String[]{maql});
            if (kq == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //--------------------------sự kiên kiểm tra đăng nhập quản lý-----------------------
    public Boolean checkLG_manager(String user, String pass) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM QUANLY WHERE MAQUANLY = ? AND MATKHAUDANGNHAP = ?",new String[]{user,pass});
        if (cursor.getCount()>0) {
            return true;
        } else {
            return false;
        }
    }

    //--------------------------sự kiên lấy dữ liệu quản lý-----------------------
    public Cursor getdata_mng_withID(String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM QUANLY WHERE MAQUANLY = ?",new String[]{id});
        return cursor;
    }


//---------------------------------------------------------------------------------------------------------------------
    //---------------------Bảng CHUCVU----------------------------------------------------

    /*
    private static String TB_CV_MACV = "MACHUCVU";
    private static String TB_CV_TENCHUCVU = "TENCHUCVU";
     */

    //---Sự kiện thêm chức vụ--
    public boolean insertCHUCVU(String macv, String tencv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MACHUCVU",macv);
        values.put("TENCHUCVU",tencv);
        long kq = db.insert("CHUCVU",null,values);
        if (kq == -1) {
            return false;
        } else {
            return true;
        }
    }

    //--SỰ kiện chỉnh sủa chức vụ
    public boolean updateCHUCVU(String macv, String tencv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MACHUCVU",macv);
        values.put("TENCHUCVU",tencv);
        Cursor cursor = db.rawQuery("SELECT * FROM CHUCVU WHERE MACHUCVU = ?",new String[]{macv});
        if (cursor.getCount() > 0 ) {
            long kq = db.update("CHUCVU",values,"MACHUCVU = ?",new String[]{macv});
            if (kq == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    //-- Sự kiện xóa chức vụ--
    public Boolean deleteCHUCVU(String macv) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM CHUCVU WHERE MACHUCVU = ?", new String[]{macv});
        if (cursor.getCount() > 0) {
            long kq = DB.delete("CHUCVU", "MACHUCVU= ?", new String[]{macv});
            if (kq == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    // sự kiện lấy dữ liệu cho dropmenu

    public Cursor getdatachucvu() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM CHUCVU",null);
        return cursor;
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------Bảng Phòng ban--------------------------------------------------

    /*
     private static String TB_PHONGBAN = "PHONGBAN";
    private static String TB_PB_MAPB = "MAPHONGBAN";
    private static String TB_PB_TENPB = "TENPHONGBAN";
     */


    //---Sự kiên thêm phòng ban----
    public Boolean insertPHONGBAN(String mapb, String tenphongban) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MAPHONGBAN",mapb);
        values.put("TENPHONGBAN",tenphongban);
        long kq = db.insert("PHONGBAN",null,values);
        if (kq == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updatePHONGBAN(String mapb, String tenphongban) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MAPHONGBAN",mapb);
        values.put("TENPHONGBAN",tenphongban);
        Cursor cursor = db.rawQuery("SELECT * FROM PHONGBAN WHERE MAPHONGBAN=?",new String[]{mapb});
        if (cursor.getCount() > 0 ) {
            long kq = db.insert("PHONGBAN",null,values);
            if (kq == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    public Boolean deletePHONGBAN(String mapb) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM PHONGBAN WHERE MAPHONGBAN = ?", new String[]{mapb});
        if (cursor.getCount() > 0) {
            long kq = DB.delete("PHONGBAN", "MAPHONGBAN = ?", new String[]{mapb});
            if (kq == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdataPHONGBAN() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM PHONGBAN",null);
        return cursor;
    }

    public Cursor getdataPHONGBAN_with_ID(String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM PHONGBAN WHERE MAPHONGBAN = ?",new String[]{id});
        return cursor;
    }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------Bảng Lương--------------------------------------------------


    public Boolean insertLUONG(String thang, String manv, String luongtt,  String luongthuong, String luongphat, String ghichu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Double tt = Double.parseDouble((String.valueOf(luongtt)));
        Double thuong = Double.parseDouble((String.valueOf(luongthuong)));
        Double phat = Double.parseDouble((String.valueOf(luongphat)));
        values.put("THANGLUONG",thang);
        values.put("MANHANVIEN",manv);
        values.put("LUONGTAMTINH",tt);
        values.put("LUONGTHUONG",thuong);
        values.put("LUONGPHAT",phat);
        Double thuclanh = (tt + thuong) - phat;
        values.put("THUCLANH",thuclanh);
        values.put("GHICHU",ghichu);
        long kq = db.insert("LUONG",null,values);
        if (kq == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updateLUONG(String thang, String manv, String luongtt,  String luongthuong, String luongphat, String ghichu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Double tt = Double.parseDouble((String.valueOf(luongtt)));
        Double thuong = Double.parseDouble((String.valueOf(luongthuong)));
        Double phat = Double.parseDouble((String.valueOf(luongphat)));
        values.put("MANHANVIEN",manv);
        values.put("LUONGTAMTINH",tt);
        values.put("LUONGTHUONG",thuong);
        values.put("LUONGPHAT",phat);
        Double thuclanh = (tt + thuong) - phat;
        values.put("THUCLANH",thuclanh);
        values.put("GHICHU",ghichu);
        Cursor cursor = db.rawQuery("SELECT * FROM LUONG WHERE THANGLUONG=? ",new String[]{thang});
        if (cursor.getCount() > 0 ) {
            long kq = db.update("LUONG",values,"THANGLUONG = ? ",new String[]{thang});
            if (kq == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    public Boolean deleteLUONG(String thang) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM LUONG WHERE THANGLUONG = ?", new String[]{thang});
        if (cursor.getCount() > 0) {
            long kq = DB.delete("LUONG", "THANGLUONG = ?", new String[]{thang});
            if (kq == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdata_luong_detail(String id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT a.GHICHU, b.HOTEN FROM LUONG a, NHANVIEN b WHERE a.MANHANVIEN=b.MANHANVIEN AND b.MANHANVIEN = ? ",new String[]{id});
        return cursor;
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------Bảng CHAMCONG--------------------------------------------------

    /*
     private static String TB_CC_THANG = "THANG";
    private static String TB_CC_NGAYLAM = "NGAYLAM";
    private static String TB_CC_GIOVAO = "GIOVAOCA";
    private static String TB_CC_NGAYCONG = "NGAYCONG";
    private static String TB_CC_NGAYPHEP = "NGAYPHEP";
    private static String TB_CC_TANGCA = "TANGCA";

     */
    public Boolean insertCHAMCONG(String thang, String ngaylam, String giovao, String ngaycong, String ngayphep, String tangca, String manv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("THANG",thang);
        values.put("NGAYLAM",ngaylam);
        values.put("GIOVAOCA",giovao);
        values.put("NGAYCONG",ngaycong);
        values.put("NGAYPHEP",ngayphep);
        values.put("TANGCA",tangca);
        values.put("MANHANVIEN",manv);
        long kq = db.insert("CHAMCONG",null,values);
        if (kq == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updateLUONG(String thang, String ngaylam, String giovao, String ngaycong, String ngayphep, String tangca, String manv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NGAYLAM",ngaylam);
        values.put("GIOVAOCA",giovao);
        values.put("NGAYCONG",ngaycong);
        values.put("NGAYPHEP",ngayphep);
        values.put("TANGCA",tangca);
        values.put("MANHANVIEN",manv);
        Cursor cursor = db.rawQuery("SELECT * FROM CHAMCONG WHERE THANG=?",new String[]{thang});
        if (cursor.getCount() > 0 ) {
            long kq = db.update("CHAMCONG",values,"THANG=?",new String[]{thang});
            if (kq == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deleteCHAMCONG(String thang) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM CHAMCONG WHERE THANG = ?", new String[]{thang});
        if (cursor.getCount() > 0) {
            long kq = DB.delete("CHAMCONG", "THANG = ?", new String[]{thang});
            if (kq == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdata_CHAMCONG(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CHAMCONG a, NHANVIEN b WHERE a.MANHANVIEN = b.MANHANVIEN AND b.MANHANVIEN = ?",new String[]{id});
        return cursor;
    }

    public Cursor getdata_xemchamcong(String id, String month) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT a.THANG,SUM(a.NGAYCONG) AS NGAYLAM,SUM(a.NGAYPHEP) AS NGAYNGHI,SUM(a.TANGCA) AS NGOAIGIO FROM CHAMCONG a, NHANVIEN b WHERE a.MANHANVIEN = b.MANHANVIEN AND a.MANHANVIEN = ? AND a.THANG =? GROUP BY a.THANG ",new String[]{id,month});
        return cursor;
    }

    public Cursor getdata_CHAMCONG_staff_salary_cal(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT  d.THANG FROM NHANVIEN a, CHAMCONG d WHERE  a.MANHANVIEN = d.MANHANVIEN AND a.MANHANVIEN = ? GROUP BY d.THANG",new String[]{id});
        return cursor;
    }


//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //------------------------------------------Bảng TRINHDO--------------------------------------------------

    public Boolean insertTRINHDO(String matrinhdo ,String tentrinhdo) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MATRINHDO",matrinhdo);
        values.put("TENTRINHDO",tentrinhdo);
        long kq = DB.insert("TRINHDO",null,values);
        if (kq == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updateTRINHDO(String matrinhdo ,String tentrinhdo) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MATRINHDO",matrinhdo);
        values.put("TENTRNHDO",tentrinhdo);
        long kq = DB.insert("TRINHDO",null,values);
        if (kq == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Boolean deleteTRINHDO(String matrinhdo) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM TRINHDO WHERE MANHANVIEN = ?", new String[]{matrinhdo});
        if (cursor.getCount() > 0) {
            long kq = DB.delete("TRINHDO", "MATRINHDO = ?", new String[]{matrinhdo});
            if (kq == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getdata_TRINHDO() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM TRINHDO",null);
        return cursor;
    }



}

