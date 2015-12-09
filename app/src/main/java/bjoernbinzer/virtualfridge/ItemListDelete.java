package bjoernbinzer.virtualfridge;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ItemListDelete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_delete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        /** setSupportActionBar(toolbar); **/

        Intent intent = getIntent();
        final String button = intent.getStringExtra("Button");
        toolbar.setTitle(button);
        final ArrayList<FridgeItem> productList = (ArrayList<FridgeItem>) intent.getSerializableExtra("ItemList");

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

        ListAdapter adapter = new DeleteItemAdapter(getApplicationContext(),
                R.layout.fridge_item_info_delete, productList);
        ListView lv = (ListView)findViewById(R.id.fridgeItemList);
        lv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
