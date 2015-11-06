package bjoernbinzer.virtualfridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by BJOERN on 06.11.2015.
 */
/* Interface for FridgeDB access */

public class FridgeDB {

    private static FridgeDBHelper mDBHelper;
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FridgeDBHelper.TABLE_NAME;

    public static void createFridgeDB (Context context){
        if (mDBHelper == null ) mDBHelper = new FridgeDBHelper(context);
    }}

    /** public static long insertEntry(String product, String durability,
                                   double quantity, String uom, double price,
                                   String category ){

        // Gets the data repository in write mode
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FridgeDBHelper.COLUMN_PRODUCT, product);
        values.put(FridgeDBHelper.COLUMN_DURABILITY, durability);
        values.put(FridgeDBHelper.COLUMN_QUANTITY, quantity);
        values.put(FridgeDBHelper.COLUMN_UOM, uom);
        values.put(FridgeDBHelper.COLUMN_PRICE, price);
        values.put(FridgeDBHelper.COLUMN_CATEGORY, category);

        //Insert the new row, returning the primary key of the new row
        long newRowId;
        return newRowId = db.insert(FridgeDBHelper.TABLE_NAME, null,values);
    }

    public static Cursor getEntries(String category){
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        String[] columns = {
                FridgeDBHelper.COLUMN_ENTRY_ID,
                FridgeDBHelper.COLUMN_PRODUCT,
                FridgeDBHelper.COLUMN_DURABILITY,
                FridgeDBHelper.COLUMN_PRICE,
                FridgeDBHelper.COLUMN_QUANTITY,
                FridgeDBHelper.COLUMN_UOM,
                FridgeDBHelper.COLUMN_CATEGORY
        };
        String[] selectionArgs = { category };

        Cursor cursor = db.query(FridgeDBHelper.TABLE_NAME, columns, FridgeDBHelper.COLUMN_CATEGORY + "=?", selectionArgs, null, null, null, null);

        return cursor;
    }

    /**public static  void deleteTable() {
     SQLiteDatabase db = mDBHelper.getWritableDatabase();
     db.execSQL(SQL_DELETE_ENTRIES);
     mDBHelper.onCreate(db);
     }**/

