package bjoernbinzer.virtualfridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/** Class providing access to the database
 * Created on 06.11.2015.
 */

public class FridgeDB {
    public static Cursor cursor;
    public static long newRowId;
    private static FridgeDBHelper mDBHelper;
    private static String[] columns = {
            FridgeDBHelper.COLUMN_ENTRY_ID,
            FridgeDBHelper.COLUMN_PRODUCT,
            FridgeDBHelper.COLUMN_DURABILITY,
            FridgeDBHelper.COLUMN_PRICE,
            FridgeDBHelper.COLUMN_QUANTITY,
            FridgeDBHelper.COLUMN_UOM,
            FridgeDBHelper.COLUMN_CATEGORY
    };

    public static void createFridgeDB(Context context) {
        // Create instance of mDBHelper (Singleton)
        if (mDBHelper == null) mDBHelper = new FridgeDBHelper(context);
    }

    /***************************************************/
    /** METHODS CONCERNING FRIDGE INVENTORY DB TABLE **/

    public static long insertEntry(String product, String durability,
                                   int quantity, String uom, double price,
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

        //Delete selected rows given in imported array rowIds
        int x;
        for(x=0;x<rowIds.length;x++){
            db.delete(FridgeDBHelper.TABLE_NAME,
                      FridgeDBHelper.COLUMN_ENTRY_ID + "=" + rowIds[x], null );
        }
    }

    public static Cursor getEntries(String category) {
        // Gets the data repository in read mode
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        //Prepare the selection arguments
        String[] selectionArgs = {category};

        // Run the select SQL statement, returning the values for the chosen category
        cursor = db.query(FridgeDBHelper.TABLE_NAME, columns,
                          FridgeDBHelper.COLUMN_CATEGORY + "=?",
                          selectionArgs, null, null, null, null);
        return cursor;
    }

    public static Cursor getEntrybyId(String id){
        // Gets the data repository in read mode
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        // Prepare the selection arguments
        String[] selectionArgs = {id};

        // Run the select SQL statement, returning the value for the chosen ID
        cursor = db.query(FridgeDBHelper.TABLE_NAME, columns,
                          FridgeDBHelper.COLUMN_ENTRY_ID + "=?",
                          selectionArgs, null, null, null, null);
        return cursor;
    }

    public static Cursor getEntryByDate(String durability, String durability2, String durability3) {
        // Gets the data repository in read mode
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        // Prepare the selection arguments
        String[] selectionArgs = {durability, durability2, durability3};

        // Run the select SQL statement, returning the values of items with the chosen durabilities
        cursor = db.query(FridgeDBHelper.TABLE_NAME, columns,
                          FridgeDBHelper.COLUMN_DURABILITY + "=? OR " +
                          FridgeDBHelper.COLUMN_DURABILITY + "=? OR " +
                          FridgeDBHelper.COLUMN_DURABILITY + "=?",
                          selectionArgs, null, null, null, null);
        return cursor;
    }
    public static void updateEntry (String id, String quantity){
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        String[] selectionArgs = {id};
        ContentValues args = new ContentValues();
        args.put(FridgeDBHelper.COLUMN_QUANTITY, quantity);

        db.update(FridgeDBHelper.TABLE_NAME, args, FridgeDBHelper.COLUMN_ENTRY_ID + "=?", selectionArgs );
    }

    /************************************************/
    /** METHODS CONCERNING SHOPPING LIST DB TABLE **/

    public static void saveShoppingList(ArrayList<ShoppingListItem> array){
        // Gets the data repository in write mode
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
        // Gets the data repository in read mode
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        // Run the select SQL statement, returning the entries of the shopping list
        cursor = db.rawQuery("SELECT * FROM " + FridgeDBHelper.TABLE_NAME_SHOPPING_LIST, null);
        return cursor;
    }

    public static void deleteShoppingList() {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        // Delete all entries of the shopping list DB table
        db.execSQL("DELETE FROM " + FridgeDBHelper.TABLE_NAME_SHOPPING_LIST);
    }

    public static void deleteEntryFromShoppingList(String product) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        //Delete row for the chosen product
        db.delete(FridgeDBHelper.TABLE_NAME_SHOPPING_LIST,
                  FridgeDBHelper.COLUMN_PRODUCT + "=" + product, null );
    }
}