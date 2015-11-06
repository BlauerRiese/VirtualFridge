package bjoernbinzer.virtualfridge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BJOERN on 06.11.2015.
 */
public class FridgeDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "fridge.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "ProductList";
    public static final String COLUMN_ENTRY_ID = "entryid";
    public static final String COLUMN_PRODUCT = "product";
    public static final String COLUMN_DURABILITY = "durability";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_UOM = "uom";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_CATEGORY = "category";

    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_PRODUCT + " TEXT NOT NULL," +
                    COLUMN_DURABILITY + " TEXT NOT NULL," +
                    COLUMN_QUANTITY + " DOUBLE(6,2) NOT NULL," +
                    COLUMN_UOM + " TEXT NOT NULL," +
                    COLUMN_PRICE + " DOUBLE(5,2)," +
                    COLUMN_CATEGORY + " TEXT NOT NULL);";

    public FridgeDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
