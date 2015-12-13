package bjoernbinzer.virtualfridge;

import android.content.Intent;
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
        setContentView(R.layout.activity_item_list);


        /* Find Views from XML
         Set up required graphical interfaces */
        ImageView imageView = (ImageView) findViewById(R.id.item_list_symbol);
        LinearLayout listContainer = (LinearLayout) findViewById(R.id.list_container);
        View background = getWindow().getDecorView();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide(); // no action bar in this activity

        // Get the name of the category
        Intent intent = getIntent();
        final String button = intent.getStringExtra("Button");

        /*
        * The following if-queries will determine
        *  - background color for category activity
        *  - background color for category item list
        *  - category image displayed as subsitute to headline
         *
         *  */

        if (button.equals(getString(R.string.text_box01))) {
            background.setBackgroundColor(getResources().getColor(R.color.colorVegetables));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorVegetablesFaded));
            imageView.setImageResource(R.drawable.vegetables);
        }
        else if (button.equals(getString(R.string.text_box02))) {
            background.setBackgroundColor(getResources().getColor(R.color.colorFruits));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorFruitsFaded));
            imageView.setImageResource(R.drawable.fruits);
        }
        else if (button.equals(getString(R.string.text_box03))) {
            background.setBackgroundColor(getResources().getColor(R.color.colorMeat));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorMeatFaded));
            imageView.setImageResource(R.drawable.meat);
        }
        else if (button.equals(getString(R.string.text_box04))) {
            background.setBackgroundColor(getResources().getColor(R.color.colorBeverages));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorBeveragesFaded));
            imageView.setImageResource(R.drawable.beverages);
        }
        else if (button.equals(getString(R.string.text_box05))) {
            background.setBackgroundColor(getResources().getColor(R.color.colorSpicery));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorSpiceryFaded));
            imageView.setImageResource(R.drawable.spicery);
        }
        else if (button.equals(getString(R.string.text_box06))) {
            background.setBackgroundColor(getResources().getColor(R.color.colorFrozen));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorFrozenFaded));
            imageView.setImageResource(R.drawable.frozen);
        }
        else if (button.equals(getString(R.string.text_box07))) {
            background.setBackgroundColor(getResources().getColor(R.color.colorSauces));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorSaucesFaded));
            imageView.setImageResource(R.drawable.sauce);
        }
        else if (button.equals(getString(R.string.text_box08))) {
            background.setBackgroundColor(getResources().getColor(R.color.colorCereals));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorCerealsFaded));
            imageView.setImageResource(R.drawable.cereal);
        }
        else if (button.equals(getString(R.string.text_box09))) {
            background.setBackgroundColor(getResources().getColor(R.color.colorSnacks));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorSnacksFaded));
            imageView.setImageResource(R.drawable.snacks);
        }
        else if (button.equals(getString(R.string.text_box10))) {
            background.setBackgroundColor(getResources().getColor(R.color.colorMilk));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorMilkFaded));
            imageView.setImageResource(R.drawable.milk);
        }
        else if (button.equals(getString(R.string.text_box11))) {
            background.setBackgroundColor(getResources().getColor(R.color.colorOthers));
            listContainer.setBackgroundColor(getResources().getColor(R.color.colorOthersFaded));
            imageView.setImageResource(R.drawable.others);
        }

        Cursor cursor = FridgeDB.getEntries(button);
        final ArrayList<FridgeItem> productList = new ArrayList<FridgeItem>();
        ArrayList<String> productNameList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String item;



        ArrayList<String> productNameArrayL = new ArrayList<String>();
        ArrayList<String> quantitiesArrayL = new ArrayList<String>();
        ArrayList<String> uomArrayL = new ArrayList<String>();


       // int round = 0;

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                Date durability = new Date();
                try {
                    durability = sdf.parse(cursor.getString(2));
                } catch(Exception e) {};
                String quantityString = cursor.getString(4);
                double quantity = Double.parseDouble(quantityString);
                String uom = cursor.getString(5);
                double price = Double.parseDouble(cursor.getString(3));
                String category = cursor.getString(6);

                //Befüllen der Arrays für ListView
                productNameArrayL.add(name);
                quantitiesArrayL.add(quantityString);
                uomArrayL.add(uom);

                FridgeItem product = new FridgeItem(id, name, durability, quantity, uom, price, category);
                productList.add(product);
                if(uom.equals("Stück")){
                    item = quantity + " " +name;
                }else{
                    item = quantity + " " + uom + " " +name;
                }
                productNameList.add(item);
            } while (cursor.moveToNext());
        }

        String [] productNameArray = new String[productNameArrayL.size()];
        productNameArray = productNameArrayL.toArray(productNameArray);
        String [] quantitiesArray = new String[quantitiesArrayL.size()];
        quantitiesArray = quantitiesArrayL.toArray(quantitiesArray);
        String [] uomArray = new String[uomArrayL.size()];
        uomArray = uomArrayL.toArray(uomArray);

        //  ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
        //                            R.layout.item_list_view, productNameList);

        ItemListAdapter adapter = new ItemListAdapter(getApplicationContext(), productNameArray, quantitiesArray, uomArray);
        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplication(), ItemListDetail.class);
                intent.putExtra(("Position"), productList.get(position).id);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openItemListDelete(view, button, productList);
            }
        });
   }

    public void openItemListDelete(View view, String category, ArrayList<FridgeItem> productList ) {
        if(!productList.isEmpty()) {
            Intent intent = new Intent(this, ItemListDelete.class);
            intent.putExtra(("Button"), category);
            intent.putExtra(("ItemList"), productList);
            startActivity(intent);
            finish();
        }
    }

}
