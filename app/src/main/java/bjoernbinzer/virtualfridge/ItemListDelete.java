package bjoernbinzer.virtualfridge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ItemListDelete extends AppCompatActivity {

    public String[] rowsToDelete;
    public View actualView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_delete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        /** setSupportActionBar(toolbar); **/

        rowsToDelete = null;
        actualView = null;

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
                actualView = view;
                ListView lv = (ListView)findViewById(R.id.fridgeItemList);
                ListAdapter adapter = lv.getAdapter();
                ArrayList<String> rowIds = new ArrayList<String>();
                CheckBox cb;

                for (int i = 0; i < adapter.getCount(); i++) {
                    FridgeItem item = (FridgeItem) adapter.getItem(i);
                    if (item.selected){
                        rowIds.add(item.id);
                    }
                }

                rowsToDelete = rowIds.toArray(new String[0]);

                AlertDialog.Builder builder = new AlertDialog.Builder(ItemListDelete.this);
                builder.setTitle("Vorräte löschen");
                builder.setMessage("Möchten Sie diese " + rowsToDelete.length + " Elemente wirklich löschen?");
                builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.dismiss();
                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface diaologinterface, int i) {
                        FridgeDB.deleteEntry(rowsToDelete);
                        openListItem(actualView, button);
                        finish();
                    }
                });

                builder.show();

            }
        });
    }

    public void openListItem(View view, String category) {
        Intent intent = new Intent(this, ItemList.class);
        intent.putExtra(("Button"), category);
        startActivity(intent);
        finish();
    }

}
