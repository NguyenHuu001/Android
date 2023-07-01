package com.example.da.dulieu;

import android.provider.BaseColumns;

public final class Contract {
    public static class Account {
        public static final String TABLE_NAME = "account";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_DISPLAY_NAME = "display_name";
        public static final String COLUMN_PASSWORD = "password";
    }

    // Định nghĩa các hằng số cho bảng giỏ hàng
    public static class Cart {
        public static final String TABLE_NAME = "cart";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_PRODUCT_NAME = "product_name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_CONFIRMED="confirm";
    }


}
