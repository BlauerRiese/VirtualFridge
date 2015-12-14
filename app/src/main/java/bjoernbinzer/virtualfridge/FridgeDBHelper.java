package bjoernbinzer.virtualfridge;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/** Class providing tools for DB creation & access
 * Created on 06.11.2015.
 */
public class FridgeDBHelper extends SQLiteOpenHelper {
    // General DB information
    public static final String DB_NAME = "fridge.db";
    public static final int DB_VERSION = 4;
    // Table names
    public static final String TABLE_NAME = "ProductList";
    public static final String TABLE_NAME_SHOPPING_LIST = "ShoppingList";
    // Column names
    public static final String COLUMN_ENTRY_ID = "entryid";
    public static final String COLUMN_PRODUCT = "product";
    public static final String COLUMN_DURABILITY = "durability";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_UOM = "uom";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_CATEGORY = "category";

    // Statement for creation of the fridge inventory DB table
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "( " +
                    COLUMN_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PRODUCT + " TEXT NOT NULL, " +
                    COLUMN_DURABILITY + " TEXT NOT NULL, " +
                    COLUMN_QUANTITY + " TEXT NOT NULL, " +
                    COLUMN_UOM + " TEXT NOT NULL, " +
                    COLUMN_PRICE + " TEXT, " +
                    COLUMN_CATEGORY + " TEXT NOT NULL );";

    //Statement for creation of the shopping list DB table
    public static final String SQL_CREATE_SHOPPING_LIST =
            "CREATE TABLE " + TABLE_NAME_SHOPPING_LIST + "( " +
                    COLUMN_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PRODUCT + " TEXT NOT NULL );";

    // Constructor
    public FridgeDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create database tables for fridge inventory and the shopping list
        db.execSQL(SQL_CREATE);
        db.execSQL(SQL_CREATE_SHOPPING_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
