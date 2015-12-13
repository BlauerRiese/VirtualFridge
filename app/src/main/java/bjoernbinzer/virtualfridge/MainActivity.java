package bjoernbinzer.virtualfridge;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DeleteDialog.NoticeDialogListener{

    private int counter;
    private ListView mDrawerList;
    private ShoppingListItemAdapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<ShoppingListItem> array = new ArrayList<ShoppingListItem>();
    private ArrayList<ShoppingListItem> arrayNew = new ArrayList<ShoppingListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        FridgeDB.createFridgeDB(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddFridgeItem(view);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LinearLayout vegetableButton = (LinearLayout)findViewById(R.id.linearLayoutVegetables);
        vegetableButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewVegetables)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout fruitButton = (LinearLayout)findViewById(R.id.linearLayoutFruits);
        fruitButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewFruits)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout meatFishButton = (LinearLayout)findViewById(R.id.linearLayoutMeat);
        meatFishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewMeat)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout beveragesButton = (LinearLayout)findViewById(R.id.linearLayoutBeverages);
        beveragesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewBeverages)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout spiceryButton = (LinearLayout)findViewById(R.id.linearLayoutSpicery);
        spiceryButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewSpicery)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout frozenButton = (LinearLayout)findViewById(R.id.linearLayoutFrozen);
        frozenButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewFrozen)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout saucesButton = (LinearLayout)findViewById(R.id.linearLayoutSauces);
        saucesButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewSauces)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout cerealsButton = (LinearLayout)findViewById(R.id.linearLayoutCereals);
        cerealsButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewCereals)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout snacksButton = (LinearLayout)findViewById(R.id.linearLayoutSnacks);
        snacksButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewSnacks)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout milkButton = (LinearLayout)findViewById(R.id.linearLayoutMilk);
        milkButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewMilk)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout othersButton = (LinearLayout)findViewById(R.id.linearLayoutOthers);
        othersButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewOthers)).getText().toString();
                openListItem(view, category);
            }
        });

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close){
            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
                getSupportActionBar().hide();
                if (array.isEmpty()) {
                    mAdapter = new ShoppingListItemAdapter(getApplication(), R.layout.shopping_list_layout, array);
                    mDrawerList.setAdapter(mAdapter);
                    Cursor cursor = FridgeDB.getShoppingList();
                    if (cursor.moveToFirst()) {
                        do {
                            String name = cursor.getString(1);
                            ShoppingListItem product = new ShoppingListItem(name);
                            array.add(product);
                        } while (cursor.moveToNext());
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
            public void onDrawerClosed(View view){
                super.onDrawerClosed(view);
                getSupportActionBar().hide();
                FridgeDB.deleteShoppingList();
                if (!array.isEmpty()){
                    FridgeDB.saveShoppingList(array);
                } else {
                    if (!arrayNew.isEmpty()){
                        FridgeDB.saveShoppingList(arrayNew);
                        arrayNew.clear();
                    }
                }
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        ImageView add = (ImageView) findViewById(R.id.add_product);
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText einkauf = (EditText) findViewById(R.id.EditText01);
                addDrawerItems(einkauf);
            }
        });
        Button shoppinglist = (Button) findViewById(R.id.shoppinglistbutton);
        shoppinglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishShopping();
            }
        });
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DeleteDialog deleteDialog = new DeleteDialog();
                deleteDialog.setItem(position);
                deleteDialog.show(getFragmentManager(),"DeleteDialog");
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openAddFridgeItem(View view) {
        Intent intent = new Intent(this, AddFridgeItem.class);
        String product = "";
        intent.putExtra(("Product"), product);
        String category = "";
        intent.putExtra(("Category"), category);
        startActivity(intent);
    }

    public void openListItem(View view, String category) {
        Intent intent = new Intent(this, ItemList.class);
        intent.putExtra(("Button"), category);
        startActivity(intent);
    }

    public void addDrawerItems(EditText einkauf){
        String name = einkauf.getText().toString();
        if (!name.isEmpty()) {
            ShoppingListItem product = new ShoppingListItem(name);
            array.add(product);
            mAdapter = new ShoppingListItemAdapter(getApplication(), R.layout.shopping_list_layout, array);
            mDrawerList.setAdapter(mAdapter);
            einkauf.setText("");
        }
    }
    public void finishShopping(){
        Intent intent = new Intent(this, AddFridgeItem.class);
        for(int i = 0; i < array.size(); ){
            ShoppingListItem item = array.get(i);
            String product = item.getProduct();
            array.remove(i);
            mAdapter.notifyDataSetChanged();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            if(item.selected){
                intent.putExtra(("Product"), product);
                startActivity(intent);
            } else {
                arrayNew.add(item);
            }
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int item) {
        deleteItem(item);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
    public void deleteItem(int item){
        mAdapter.remove(array.get(item));
        mAdapter.notifyDataSetChanged();
    }


}
