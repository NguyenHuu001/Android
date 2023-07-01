package com.example.da.dulieu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.da.R;

import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "banhang.db";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_NAME = "hinh_anh";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TEN = "ten";
    private static final String COLUMN_HINH = "hinh";
    private static final String COLUMN_GIA = "gia";
    private static final String COLUMN_SOLUONG = "soluong";
    private static final String COLUMN_DABAN = "daban";
    private ArrayList<HinhAnh> arrayImage;

    public DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertInitialData() {





    }
    private static final String CREATE_TABLE_ACCOUNT =
            "CREATE TABLE " + Contract.Account.TABLE_NAME + " (" +
                    Contract.Account.COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                    Contract.Account.COLUMN_DISPLAY_NAME + " TEXT, " +
                    Contract.Account.COLUMN_PASSWORD + " TEXT)";

    // Phương thức tạo bảng giỏ hàng
    private static final String CREATE_TABLE_CART =
            "CREATE TABLE " + Contract.Cart.TABLE_NAME + " (" +
                    Contract.Cart.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Contract.Cart.COLUMN_IMAGE + " INTEGER, " +
                    Contract.Cart.COLUMN_PRODUCT_NAME + " TEXT, " +
                    Contract.Cart.COLUMN_PRICE + " INTEGER, " +
                    Contract.Cart.COLUMN_QUANTITY + " INTEGER, " +
                    Contract.Cart.COLUMN_ADDRESS + " TEXT, " +
                    Contract.Cart.COLUMN_USERNAME + " TEXT REFERENCES " + Contract.Account.TABLE_NAME + "(" + Contract.Account.COLUMN_USERNAME + "), " +
                    Contract.Cart.COLUMN_CONFIRMED + " INTEGER DEFAULT 0)";

    private static final String CREATE_TABLE_HinhAnh =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TEN + " TEXT, " +
                    COLUMN_HINH + " INTEGER, " +
                    COLUMN_GIA + " INTEGER, " +
                    COLUMN_SOLUONG + " TEXT, " +
                    COLUMN_DABAN + " INTEGER" +
                    ")";

    @Override
    public void onCreate(SQLiteDatabase db  ) {
        arrayImage=new ArrayList<>();
        arrayImage.add(new HinhAnh("Áo dài", R.drawable.aodai,1200000,"30 đã bán",5));
        arrayImage.add(new HinhAnh("Váy cưới vàng",R.drawable.anhcuoi1,1200000,"30 đã bán",5));
        arrayImage.add(new HinhAnh("Đầm công sở",R.drawable.aolen,500000,"35 đã bán",5));
        arrayImage.add(new HinhAnh("Áo cưới xòe",R.drawable.anhcuoi2,1000000,"5 đã bán",4));
        arrayImage.add(new HinhAnh("Áo lông chim",R.drawable.aolongchim,2500000,"47 đã bán",4));
        arrayImage.add(new HinhAnh("Áo cưới trắng",R.drawable.anhcuoi3,3500000,"2 đã bán",5));
        arrayImage.add(new HinhAnh("Váy công chúa",R.drawable.anhcuoi4,7000000,"0 đã bán",1));
        arrayImage.add(new HinhAnh("Áo vest nam",R.drawable.anhcuoi5,1000000,"7 đã bán",2));
        arrayImage.add(new HinhAnh("Áo khoác đỏ",R.drawable.aokhoacdo,7000000,"0 đã bán",1));
        arrayImage.add(new HinhAnh("Áo con hổ",R.drawable.anhcuoi7,500000,"35 đã bán",5));
        arrayImage.add(new HinhAnh("Áo cưới Ấn Độ",R.drawable.damcuoiando,2500000,"47 đã bán",4));
        arrayImage.add(new HinhAnh("Đầm trắng",R.drawable.damtrang,2500000,"47 đã bán",4));
        arrayImage.add(new HinhAnh("Giày trắng",R.drawable.daytrang,2500000,"47 đã bán",4));
        arrayImage.add(new HinhAnh("Áo hoa hồng",R.drawable.hoahong,2500000,"47 đã bán",4));
        arrayImage.add(new HinhAnh("Quần jean",R.drawable.jeanando,2500000,"47 đã bán",4));
        arrayImage.add(new HinhAnh("Áo sơ mi",R.drawable.somi,2500000,"47 đã bán",4));
        arrayImage.add(new HinhAnh("Váy vàng",R.drawable.vayvang,2500000,"47 đã bán",4));

        db.execSQL(CREATE_TABLE_ACCOUNT);
        db.execSQL(CREATE_TABLE_CART);
        db.execSQL(CREATE_TABLE_HinhAnh);
        db.delete("hinh_anh", null, null);

        ContentValues values = new ContentValues();
        for (HinhAnh hinhAnh : arrayImage) {
            values.put("ten", hinhAnh.getTenSp());
            values.put("hinh", hinhAnh.getHinh());
            values.put("gia", hinhAnh.getGia());
            values.put("soluong", hinhAnh.getSoLuong());
            values.put("daban", hinhAnh.getDanhGia());
            db.insert("hinh_anh", null, values);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Cart.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Account.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);

    }
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = { "username" };

        String selection = "username = ?" + " AND " + "password = ?";
        String[] selectionArgs = { username, password };

        Cursor cursor = db.query("account", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

}
