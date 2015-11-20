package bjoernbinzer.virtualfridge;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //**setSupportActionBar(toolbar); **/

        Intent intent = getIntent();
        final String button = intent.getStringExtra("Button");
        toolbar.setTitle(button);
        /**getActionBar().setTitle(button); **/

        if (button.equals(getString(R.string.text_box01))) {
            toolbar.setBackgroundColor(Color.parseColor("#459b63"));
        }
        else if (button.equals(getString(R.string.text_box02))) {
            toolbar.setBackgroundColor(Color.parseColor("#f1b941"));
        }
        else if (button.equals(getString(R.string.text_box03))) {
            toolbar.setBackgroundColor(Color.parseColor("#c15660"));
        }
        else if (button.equals(getString(R.string.text_box04))) {
            toolbar.setBackgroundColor(Color.parseColor("#549dd0"));
        }
        else if (button.equals(getString(R.string.text_box05))) {
            toolbar.setBackgroundColor(Color.parseColor("#a08f53"));
        }
        else if (button.equals(getString(R.string.text_box06))) {
            toolbar.setBackgroundColor(Color.parseColor("#6bb8bb"));
        }
        else if (button.equals(getString(R.string.text_box07))) {
            toolbar.setBackgroundColor(Color.parseColor("#402c38"));
        }
        else if (button.equals(getString(R.string.text_box08))) {
            toolbar.setBackgroundColor(Color.parseColor("#c6af52"));
        }
        else if (button.equals(getString(R.string.text_box09))) {
            toolbar.setBackgroundColor(Color.parseColor("#9d63a9"));
        }
        else if (button.equals(getString(R.string.text_box10))) {
            toolbar.setBackgroundColor(Color.parseColor("#919aa9"));
        }
        else if (button.equals(getString(R.string.text_box11))) {
            toolbar.setBackgroundColor(Color.parseColor("#6bd3a8"));
        }

        Cursor cursor = FridgeDB.getEntries(button);
        final List<FridgeItem> productList = new ArrayList<FridgeItem>();
        List<String> productNameList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String item;
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                Date durability = new Date();
                try {
                    durability = sdf.parse(cursor.getString(2));
                } catch(Exception e) {};
                double quantity = Double.parseDouble(cursor.getString(4));
                String uom = cursor.getString(5);
                double price = Double.parseDouble(cursor.getString(3));
                String category = cursor.getString(6);
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

        ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
                                    R.layout.item_list_view, productNameList);
        ListView lv = (ListView)findViewById(R.id.fridgeItemList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new ItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplication(), ItemListDetail.class);
                intent.putExtra(("Position"), productList.get(position).id);
                startActivity(intent);
            }
        });
    }
}
