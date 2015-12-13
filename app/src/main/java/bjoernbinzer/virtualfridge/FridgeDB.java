package bjoernbinzer.virtualfridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by BJOERN on 06.11.2015.
 */
/* Interface for FridgeDB access */

public class FridgeDB {
    public static Cursor cursor;
    public static long newRowId;

    private static String[] columns = {
            FridgeDBHelper.COLUMN_ENTRY_ID,
            FridgeDBHelper.COLUMN_PRODUCT,
            FridgeDBHelper.COLUMN_DURABILITY,
            FridgeDBHelper.COLUMN_PRICE,
            FridgeDBHelper.COLUMN_QUANTITY,
            FridgeDBHelper.COLUMN_UOM,
            FridgeDBHelper.COLUMN_CATEGORY
    };

    private static FridgeDBHelper mDBHelper;
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FridgeDBHelper.TABLE_NAME;

    public static void createFridgeDB(Context context) {
        if (mDBHelper == null) mDBHelper = new FridgeDBHelper(context);
    }

    public static long insertEntry(String product, String durability,
                                   double quantity, String uom, double price,
                                   String category) {

        // Gets the data repository in write mode
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FridgeDBHelper.COLUMN_PRODUCT, product);
        values.put(FridgeDBHelper.COLUMN_DURABILITY, durability);
        values.put(FridgeDBHelper.COLUMN_QUANTITY, String.valueOf(quantity));
        values.put(FridgeDBHelper.COLUMN_UOM, uom);
        values.put(FridgeDBHelper.COLUMN_PRICE, String.valueOf(price));
        values.put(FridgeDBHelper.COLUMN_CATEGORY, category);

        //Insert the new row, returning the primary key of the new row
        newRowId = db.insert(FridgeDBHelper.TABLE_NAME, null, values);

        return newRowId;

    }

    public static void deleteEntry (String[] rowIds){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        //Delete rows
        int x;
        for(x=0;x<rowIds.length;x++){
            db.delete(FridgeDBHelper.TABLE_NAME, FridgeDBHelper.COLUMN_ENTRY_ID + "=" + rowIds[x], null );
        }

    }

    public static Cursor getEntries(String category) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        String[] selectionArgs = {category};

        cursor = db.query(FridgeDBHelper.TABLE_NAME, columns, FridgeDBHelper.COLUMN_CATEGORY + "=?", selectionArgs, null, null, null, null);

        return cursor;
    }

    public static Cursor getEntrybyId(String id){
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        String[] selectionArgs = {id};

        cursor = db.query(FridgeDBHelper.TABLE_NAME, columns, FridgeDBHelper.COLUMN_ENTRY_ID + "=?", selectionArgs, null, null, null, null);

        return cursor;
    }

    public static Cursor getEntryByDate(String durability, String durability2, String durability3) {
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        String[] selectionArgs = {durability, durability2, durability3};

        cursor = db.query(FridgeDBHelper.TABLE_NAME, columns, FridgeDBHelper.COLUMN_DURABILITY + "=? OR " +
                FridgeDBHelper.COLUMN_DURABILITY + "=? OR " + FridgeDBHelper.COLUMN_DURABILITY + "=?", selectionArgs, null, null, null, null);

        return cursor;
    }

    public static void saveShoppingList(ArrayList<ShoppingListItem> array){
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        for (int i = 0; i < array.size(); i++) {
            String product = array.get(i).getProduct();
            ContentValues values = new ContentValues();
            values.put(FridgeDBHelper.COLUMN_PRODUCT, product);

            //Insert the new row, returning the primary key of the new row
            newRowId = db.insert(FridgeDBHelper.TABLE_NAME_SHOPPING_LIST, null, values);
        }
    }

    public static Cursor getShoppingList(){
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        cursor = db.rawQuery("select * from " + FridgeDBHelper.TABLE_NAME_SHOPPING_LIST, null);
        return cursor;
    }

    public static void deleteShoppingList() {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        db.execSQL("delete from " + FridgeDBHelper.TABLE_NAME_SHOPPING_LIST);
    }

    public static void deleteEntryShoppingList(String product) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        //Delete rows
        db.delete(FridgeDBHelper.TABLE_NAME_SHOPPING_LIST, FridgeDBHelper.COLUMN_PRODUCT + "=" + product, null );
    }
}