package bjoernbinzer.virtualfridge;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/** Class specifiying the design of the detail View of a specific Fridge Item
 * Created on 09.12.2015.
 */

public class ItemListDetail extends AppCompatActivity {
    private String category;
    private String quantity;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Details");

        Intent intent = getIntent();

        // Reads the entry out of the database according to the id of the selected item
        id = intent.getStringExtra("Position");
        Cursor cursor = FridgeDB.getEntrybyId(id);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // Reads the according product name, durability, quantity, uom, price and displays them
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                TextView txtname = (TextView) findViewById(R.id.product);
                txtname.setText(name);
                Date durability = new Date();
                String dura = new String();
                try {
                    durability = sdf.parse(cursor.getString(2));
                    dura = sdf.format(durability);
                } catch(Exception e) {};
                TextView txtdura = (TextView) findViewById(R.id.durability);
                txtdura.setText(dura);
                String quantity = cursor.getString(4);
                final EditText txtquant = (EditText) findViewById(R.id.quantity);
                txtquant.setText(quantity);
                // Registers a Listener to the quantity edit view
                txtquant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtquant.setText(" ");
                    }
                });
                String uom = cursor.getString(5);
                TextView txtuom = (TextView) findViewById(R.id.uom);
                txtuom.setText(uom);
                String price = cursor.getString(3);
                TextView txtprice = (TextView) findViewById(R.id.price);
                txtprice.setText(price);
                category = cursor.getString(6);
                TextView txtcat = (TextView) findViewById(R.id.category);
                txtcat.setText(category);
                fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openListItem(view,category);
                }
            });


        }
        // Sets the color of the Toolbar and the Button according to the category
        if (category.equals(getString(R.string.text_box01))) {
            toolbar.setBackgroundColor(Color.parseColor("#459b63"));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#459b63")));
        }
        else if (category.equals(getString(R.string.text_box02))) {
            toolbar.setBackgroundColor(Color.parseColor("#f1b941"));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f1b941")));
        }
        else if (category.equals(getString(R.string.text_box03))) {
            toolbar.setBackgroundColor(Color.parseColor("#c15660"));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#c15660")));
        }
        else if (category.equals(getString(R.string.text_box04))) {
            toolbar.setBackgroundColor(Color.parseColor("#549dd0"));
        }
        else if (category.equals(getString(R.string.text_box05))) {
            toolbar.setBackgroundColor(Color.parseColor("#a08f53"));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#08f53")));
        }
        else if (category.equals(getString(R.string.text_box06))) {
            toolbar.setBackgroundColor(Color.parseColor("#6bb8bb"));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6bb8bb")));
        }
        else if (category.equals(getString(R.string.text_box07))) {
            toolbar.setBackgroundColor(Color.parseColor("#402c38"));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#402c38")));
        }
        else if (category.equals(getString(R.string.text_box08))) {
            toolbar.setBackgroundColor(Color.parseColor("#c6af52"));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#c6af52")));
        }
        else if (category.equals(getString(R.string.text_box09))) {
            toolbar.setBackgroundColor(Color.parseColor("#9d63a9"));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9d63a9")));
        }
        else if (category.equals(getString(R.string.text_box10))) {
            toolbar.setBackgroundColor(Color.parseColor("#919aa9"));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#919aa9")));
        }
        else if (category.equals(getString(R.string.text_box11))) {
            toolbar.setBackgroundColor(Color.parseColor("#6bd3a8"));
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6bd3a8")));
        }
    }

    // Closes the Activity and opens the ItemList Activity
    public void openListItem(View view, String category) {
        EditText txtquant = (EditText) findViewById(R.id.quantity);
        String newquantity = txtquant.getText().toString();
        // Updates the quantity if it was changed
        if(newquantity != quantity){
            FridgeDB.updateEntry(id, newquantity);
        }
        Intent intent = new Intent(this, ItemList.class);
        intent.putExtra(("Button"), category);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent (this, ItemList.class);
        intent.putExtra(("Button"), category);
        startActivity(intent);
        finish();
    }
}

