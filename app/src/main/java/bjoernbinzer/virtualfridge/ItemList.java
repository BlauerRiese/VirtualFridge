package bjoernbinzer.virtualfridge;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by BJOERN on 06.11.2015.
 */
public class ItemList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setting up view with necessary GUI elements
        setContentView(R.layout.activity_item_list);
        View activityBackground = getWindow().getDecorView();
        ImageView categoryIcon = (ImageView) findViewById(R.id.item_list_symbol);
        LinearLayout listContainer = (LinearLayout) findViewById(R.id.list_container);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        /*
        We decided to leave out the ActionBar in this activity and replace it with the symbol of
        the equivalent category
         */
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        /*
        creating the String for the category from the Button pushed to get to this activity
         - this string will be used throughout the entire class and is essential to determine
           category-specific database queries, colors and GUI elements
         */
        Intent intent = getIntent();
        final String button = intent.getStringExtra("Button");


        /*
        Sets
            - background of activity to category color
            - background of listContainer to faded category color
            - color of FloatingButton to category color
            - Category icon to category image
         */
        if (button.equals(getString(R.string.text_box01))) {
            activityBackground.setBackgroundColor(getResources().getColor(R.color.colorVegetables));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorVegetablesFaded));
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorVegetables)));
            categoryIcon.setImageResource(R.drawable.vegetables);
        }
        else if (button.equals(getString(R.string.text_box02))) {
            activityBackground.setBackgroundColor(getResources().getColor(R.color.colorFruits));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorFruitsFaded));
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorFruits)));
            categoryIcon.setImageResource(R.drawable.fruits);
        }
        else if (button.equals(getString(R.string.text_box03))) {
            activityBackground.setBackgroundColor(getResources().getColor(R.color.colorMeat));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorMeatFaded));
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorMeat)));
            categoryIcon.setImageResource(R.drawable.meat);
        }
        else if (button.equals(getString(R.string.text_box04))) {
            activityBackground.setBackgroundColor(getResources().getColor(R.color.colorBeverages));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorBeveragesFaded));
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorBeverages)));
            categoryIcon.setImageResource(R.drawable.beverages);
        }
        else if (button.equals(getString(R.string.text_box05))) {
            activityBackground.setBackgroundColor(getResources().getColor(R.color.colorSpicery));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorSpiceryFaded));
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorSpicery)));
            categoryIcon.setImageResource(R.drawable.spicery);
        }
        else if (button.equals(getString(R.string.text_box06))) {
            activityBackground.setBackgroundColor(getResources().getColor(R.color.colorFrozen));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorFrozenFaded));
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorFrozen)));
            categoryIcon.setImageResource(R.drawable.frozen);
        }
        else if (button.equals(getString(R.string.text_box07))) {
            activityBackground.setBackgroundColor(getResources().getColor(R.color.colorSauces));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorSaucesFaded));
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorSauces)));
            categoryIcon.setImageResource(R.drawable.sauce);
        }
        else if (button.equals(getString(R.string.text_box08))) {
            activityBackground.setBackgroundColor(getResources().getColor(R.color.colorCereals));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorCerealsFaded));
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorCereals)));
            categoryIcon.setImageResource(R.drawable.cereal);
        }
        else if (button.equals(getString(R.string.text_box09))) {
            activityBackground.setBackgroundColor(getResources().getColor(R.color.colorSnacks));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorSnacksFaded));
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorSnacks)));
            categoryIcon.setImageResource(R.drawable.snacks);
        }
        else if (button.equals(getString(R.string.text_box10))) {
            activityBackground.setBackgroundColor(getResources().getColor(R.color.colorMilk));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorMilkFaded));
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorMilk)));
            categoryIcon.setImageResource(R.drawable.milk);
        }
        else if (button.equals(getString(R.string.text_box11))) {
            activityBackground.setBackgroundColor(getResources().getColor(R.color.colorOthers));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorOthersFaded));
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorOthers)));
            categoryIcon.setImageResource(R.drawable.others);
        }

        // Create database cursor to get entries for specific category
        Cursor cursor = FridgeDB.getEntries(button);

        // ArrayList with FridgeItems for passing on to other activities
        final ArrayList<FridgeItem> productList = new ArrayList<FridgeItem>();

        // Declaration of Date format for best before date
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        // Declaration of three array Lists that will be populated with the content pulled from the database
        ArrayList<String> productNameArrayList = new ArrayList<String>();
        ArrayList<String> quantitiesArrayList = new ArrayList<String>();
        ArrayList<String> uomArrayList = new ArrayList<String>();

        /* Iterating through the cursor to get all entries in database while new
          * data set is still available
           * */

        if (cursor.moveToFirst()) {
            do {

                // for every entry: save all data in local variables first
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                Date durability = new Date();
                try {
                    durability = sdf.parse(cursor.getString(2));
                } catch(Exception e) {};
                double price = Double.parseDouble(cursor.getString(3));
                String quantityString = cursor.getString(4);
                double quantity = Double.parseDouble(quantityString);
                String uom = cursor.getString(5);
                String category = cursor.getString(6);

                //Populating the array Lists
                productNameArrayList.add(name);
                quantitiesArrayList.add(quantityString);
                uomArrayList.add(uom);

                // Creating a FridgeItem object for each entry; adding it to the array list that will be passed on
                FridgeItem product = new FridgeItem(id, name, durability, quantity, uom, price, category);
                productList.add(product);

            } while (cursor.moveToNext());
        }

        /*
        Creating String arrays from the array lists, since ItemListAdapter expects String arrays
         */
        String [] productNameArray = new String[productNameArrayList.size()];
        productNameArray = productNameArrayList.toArray(productNameArray);
        String [] quantitiesArray = new String[quantitiesArrayList.size()];
        quantitiesArray = quantitiesArrayList.toArray(quantitiesArray);
        String [] uomArray = new String[uomArrayList.size()];
        uomArray = uomArrayList.toArray(uomArray);

        /*  Initialization and set-up of ItemListAdapter */
        ItemListAdapter adapter = new ItemListAdapter(getApplicationContext(), productNameArray, quantitiesArray, uomArray);

        /* Connect ListView lv with ListView in XML*/
        ListView lv = (ListView)findViewById(R.id.listView);

        /* Set Adapter of ListView to adapter created above*/
        lv.setAdapter(adapter);



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /*
            Standard Click Listener for ListView-Element:
            Display of detailed view of List-Item -> ItemListDetail.class
            */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplication(), ItemListDetail.class);
                intent.putExtra(("Position"), productList.get(position).id);
                startActivity(intent);
                finish();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            /*
            Long Click Listener for ListView-Element:
            Calling openItemListDelete ()
            */
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                openItemListDelete(view, button, productList);
                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
             /*
             Standard Click Listener for Floating Action Button:
             Segue to Adding new List-item to this specific category -> AddFridgeItem.class
             */
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddFridgeItem.class);
                String product = "";
                intent.putExtra(("Product"), product);
                intent.putExtra(("Category"), button);
                startActivity(intent);
                finish();
            }
        });
    }

    public void openItemListDelete(View view, String category, ArrayList<FridgeItem> productList ) {
       /*
       Segue to ItemListDelete in order to select the items that should be deleted
        */
        if(!productList.isEmpty()) {
            Intent intent = new Intent(this, ItemListDelete.class);
            intent.putExtra(("Button"), category);
            intent.putExtra(("ItemList"), productList);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
        finish();
    }



}
