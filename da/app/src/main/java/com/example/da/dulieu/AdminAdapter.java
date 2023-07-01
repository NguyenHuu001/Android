package com.example.da.dulieu;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da.Admin;
import com.example.da.R;

import java.util.List;

public class AdminAdapter extends BaseAdapter {
    ImageButton btnAdd,btnQuayLai,btnGioHang;
    TextView txtSoLuong,tvChuDe;
    private Context context;
    private int layout;
    private List<HinhAnh> hinhAnhList;
    public AdminAdapter(Context context, int layout, List<HinhAnh> hinhAnhList) {
        this.context = context;
        this.layout = layout;
        this.hinhAnhList = hinhAnhList;
    }
    @Override
    public int getCount() {
        return hinhAnhList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder
    {
        ImageView imgHinh;
        TextView tvTen;
        TextView gia;
        TextView soLuong;
        RatingBar danhGia;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AdminAdapter.ViewHolder holder;
        if(view==null)
        {
            holder=new AdminAdapter.ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);
            holder.imgHinh=(ImageView) view.findViewById(R.id.picture1);
            holder.tvTen=(TextView) view.findViewById(R.id.tvTenSp);
            holder.gia=(TextView) view.findViewById(R.id.tvGia);
            holder.soLuong=(TextView) view.findViewById(R.id.tvsoluong);
            holder.danhGia=(RatingBar) view.findViewById(R.id.ratingBar1);
            view.setTag(holder);
        }
        else
        {
            holder= (AdminAdapter.ViewHolder) view.getTag();
        }
        ImageButton btnEdit,btnDelete;


        HinhAnh hinhAnh=hinhAnhList.get(i);
        holder.imgHinh.setImageResource(hinhAnh.getHinh());
        holder.tvTen.setText(hinhAnh.getTenSp());
        holder.gia.setText(hinhAnh.getGia().toString()+" VNĐ");
        holder.soLuong.setText(hinhAnh.getSoLuong());
        holder.danhGia.setNumStars(hinhAnh.getDanhGia());


        btnDelete=(ImageButton)view.findViewById(R.id.btnDelete);
        btnEdit=(ImageButton)view.findViewById(R.id.btnEdit);
        holder.imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEdit.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(hinhAnh.getTenSp(),hinhAnh.getGia());
               Intent intent=new Intent(context,Admin.class);
                context.startActivity(intent);

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditPopup(hinhAnh.getTenSp());


            }
        });

        return view;
    }
    public void deleteProduct(String tenSanPham, int giaSanPham) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = "ten = ? AND gia = ?";
        String[] selectionArgs = {tenSanPham, String.valueOf(giaSanPham)};

        int rowsDeleted = db.delete("hinh_anh", selection, selectionArgs);
        if (rowsDeleted > 0) {
            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    private void showEditPopup(String tenCu) {

        // Tạo dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_popup_edit_data);

        // Ánh xạ các thành phần giao diện trong popup
        final EditText editTextTen = dialog.findViewById(R.id.editTextTen);
        final EditText editTextGia = dialog.findViewById(R.id.editTextGia);

        Button buttonSave = dialog.findViewById(R.id.buttonSave);

        // Lấy dữ liệu hiện tại từ bảng "hinh_anh" và điền vào các trường trong popup

        // Xử lý sự kiện khi nút "Lưu" được nhấn
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu đã nhập từ các trường trong popup
                String newTen = editTextTen.getText().toString();
                int newGia = Integer.parseInt(editTextGia.getText().toString());
                updateDataInTable(tenCu,newTen,newGia);
                dialog.dismiss();
                Intent intent=new Intent(context,Admin.class);
                context.startActivity(intent);
            }
        });

        // Hiển thị dialog
        dialog.show();
    }
    private void updateDataInTable(String tencu, String newTen, int newGia) {
        // Mở kết nối đến cơ sở dữ liệu
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Dữ liệu mới cần cập nhật

        String newSoLuong = "0 đã bán";
        int newDanhGia = 5;

        // Tạo đối tượng ContentValues để chứa dữ liệu cần cập nhật
        ContentValues values = new ContentValues();
        values.put("ten", newTen);

        values.put("gia", newGia);
        values.put("soluong", newSoLuong);
        values.put("daban", newDanhGia);

        // Điều kiện để xác định dữ liệu cần sửa đổi (vd: theo tên)
        String selection = "ten = ?";
        String[] selectionArgs = { tencu};

        // Thực hiện cập nhật dữ liệu trong bảng "hinh_anh"
        int numRowsUpdated = db.update("hinh_anh", values, selection, selectionArgs);

        // Kiểm tra xem có bao nhiêu dòng dữ liệu đã được cập nhật
        if (numRowsUpdated > 0) {
            // Cập nhật thành công
            Toast.makeText(context, "Dữ liệu đã được cập nhật", Toast.LENGTH_SHORT).show();
        } else {
            // Không có dòng nào được cập nhật
            Toast.makeText(context, "Không tìm thấy dữ liệu cần cập nhật", Toast.LENGTH_SHORT).show();
        }

        // Đóng kết nối cơ sở dữ liệu
        db.close();
    }

}
