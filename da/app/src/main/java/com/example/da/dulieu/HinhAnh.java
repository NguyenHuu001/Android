package com.example.da.dulieu;

public class HinhAnh {
    public HinhAnh(String tenSp, int hinh, Integer gia, String soLuong, int danhGia) {
        this.tenSp = tenSp;
        Hinh = hinh;
        this.gia = gia;
        this.soLuong = soLuong;
        this.danhGia = danhGia;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public int getHinh() {
        return Hinh;
    }

    public void setHinh(int hinh) {
        Hinh = hinh;
    }

    public Integer getGia() {
        return gia;
    }

    public void setGia(Integer gia) {
        this.gia = gia;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public int getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(int danhGia) {
        this.danhGia = danhGia;
    }

    private String tenSp;
    private int Hinh;
    private Integer gia;
    private String soLuong;
    private int danhGia;


}
