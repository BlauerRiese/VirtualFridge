package bjoernbinzer.virtualfridge;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

//This class is about the main features of our application. You can find the ClickListener for the Buttons, the shopping list,
//
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DeleteDialog.NoticeDialogListener {

    private int counter;
    private ListView mDrawerList;
    private ShoppingListItemAdapter mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ArrayList<ShoppingListItem> array = new ArrayList<ShoppingListItem>();
    private ArrayList<ShoppingListItem> arrayNew = new ArrayList<ShoppingListItem>();
    NotificationManager notificationManager;
    PendingIntent pendingIntent;
    final ArrayList<FridgeItem> productList = new ArrayList<FridgeItem>();
    ArrayList<String> productNameList = new ArrayList<String>();

    @Override
    protected void onRestart() {
        super.onRestart();
        this.setCategoryAlpha();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        FridgeDB.createFridgeDB(this);

        handleCommand();
        createNotification(productNameList);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddFridgeItem(view);
                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //The following parts describe the different category-buttons.
        //Every button has its own ClickListener. --> Go to the different CategoryView.

        LinearLayout vegetableButton = (LinearLayout) findViewById(R.id.linearLayoutVegetables);
        vegetableButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewVegetables)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout fruitButton = (LinearLayout) findViewById(R.id.linearLayoutFruits);
        fruitButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewFruits)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout meatFishButton = (LinearLayout) findViewById(R.id.linearLayoutMeat);
        meatFishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String category = ((TextView) findViewById(R.id.textViewMeat)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout beveragesButton = (LinearLayout) findViewById(R.id.linearLayoutBeverages);
        beveragesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewBeverages)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout spiceryButton = (LinearLayout) findViewById(R.id.linearLayoutSpicery);
        spiceryButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewSpicery)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout frozenButton = (LinearLayout) findViewById(R.id.linearLayoutFrozen);
        frozenButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewFrozen)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout saucesButton = (LinearLayout) findViewById(R.id.linearLayoutSauces);
        saucesButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewSauces)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout cerealsButton = (LinearLayout) findViewById(R.id.linearLayoutCereals);
        cerealsButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewCereals)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout snacksButton = (LinearLayout) findViewById(R.id.linearLayoutSnacks);
        snacksButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewSnacks)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout milkButton = (LinearLayout) findViewById(R.id.linearLayoutMilk);
        milkButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewMilk)).getText().toString();
                openListItem(view, category);
            }
        });

        LinearLayout othersButton = (LinearLayout) findViewById(R.id.linearLayoutOthers);
        othersButton.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category = ((TextView) findViewById(R.id.textViewOthers)).getText().toString();
                openListItem(view, category);
            }
        });

        this.setCategoryAlpha();

        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().hide();

                //Initialize button to share shopping list
                ImageButton shareShoppingList = (ImageButton) findViewById(R.id.shareShoppingList);
                shareShoppingList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareShoppingList();
                    }
                });

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

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().hide();
                FridgeDB.deleteShoppingList();
                if (!array.isEmpty()) {
                    FridgeDB.saveShoppingList(array);
                } else {
                    if (!arrayNew.isEmpty()) {
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
                deleteDialog.show(getFragmentManager(), "DeleteDialog");
            }
        });
    }

    public void setCategoryAlpha() {
        LinearLayout vegetableButton = (LinearLayout) findViewById(R.id.linearLayoutVegetables);
        LinearLayout fruitButton = (LinearLayout) findViewById(R.id.linearLayoutFruits);
        LinearLayout meatButton = (LinearLayout) findViewById(R.id.linearLayoutMeat);
        LinearLayout milkButton = (LinearLayout) findViewById(R.id.linearLayoutMilk);
        LinearLayout spiceryButton = (LinearLayout) findViewById(R.id.linearLayoutSpicery);
        LinearLayout sauceButton = (LinearLayout) findViewById(R.id.linearLayoutSauces);
        LinearLayout snacksButton = (LinearLayout) findViewById(R.id.linearLayoutSnacks);
        LinearLayout frozenButton = (LinearLayout) findViewById(R.id.linearLayoutFrozen);
        LinearLayout cerealButton = (LinearLayout) findViewById(R.id.linearLayoutCereals);
        LinearLayout otherButton = (LinearLayout) findViewById(R.id.linearLayoutOthers);
        LinearLayout beveragesButton = (LinearLayout) findViewById(R.id.linearLayoutBeverages);

        if (!FridgeDB.getEntries(((TextView) findViewById(R.id.textViewVegetables)).getText().toString()).moveToFirst()) {
            vegetableButton.setAlpha(0.2f);
        }
        ;
        if (!FridgeDB.getEntries(((TextView) findViewById(R.id.textViewFruits)).getText().toString()).moveToFirst()) {
            fruitButton.setAlpha(0.2f);
        }
        ;
        if (!FridgeDB.getEntries(((TextView) findViewById(R.id.textViewMeat)).getText().toString()).moveToFirst()) {
            meatButton.setAlpha(0.2f);
        }
        ;
        if (!FridgeDB.getEntries(((TextView) findViewById(R.id.textViewMilk)).getText().toString()).moveToFirst()) {
            milkButton.setAlpha(0.2f);
        }
        ;
        if (!FridgeDB.getEntries(((TextView) findViewById(R.id.textViewSpicery)).getText().toString()).moveToFirst()) {
            spiceryButton.setAlpha(0.2f);
        }
        ;
        if (!FridgeDB.getEntries(((TextView) findViewById(R.id.textViewSauces)).getText().toString()).moveToFirst()) {
            sauceButton.setAlpha(0.2f);
        }
        ;
        if (!FridgeDB.getEntries(((TextView) findViewById(R.id.textViewSnacks)).getText().toString()).moveToFirst()) {
            snacksButton.setAlpha(0.2f);
        }
        ;
        if (!FridgeDB.getEntries(((TextView) findViewById(R.id.textViewFrozen)).getText().toString()).moveToFirst()) {
            frozenButton.setAlpha(0.2f);
        }
        ;
        if (!FridgeDB.getEntries(((TextView) findViewById(R.id.textViewCereals)).getText().toString()).moveToFirst()) {
            cerealButton.setAlpha(0.2f);
        }
        ;
        if (!FridgeDB.getEntries(((TextView) findViewById(R.id.textViewOthers)).getText().toString()).moveToFirst()) {
            otherButton.setAlpha(0.2f);
        }
        ;
        if (!FridgeDB.getEntries(((TextView) findViewById(R.id.textViewBeverages)).getText().toString()).moveToFirst()) {
            beveragesButton.setAlpha(0.2f);
        }
        ;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        System.exit(0);
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
    //This method opens and configurates the AddFridgeItem-Window(View)
    //In this View you can add different products to the database
    public void openAddFridgeItem(View view) {
        Intent intent = new Intent(this, AddFridgeItem.class);
        String product = "";
        intent.putExtra(("Product"), product);
        String category = "";
        intent.putExtra(("Category"), category);
        startActivity(intent);
    }
    //This method opens and configurates the ListItem-Window(View)
    //In this View you can 
    public void openListItem(View view, String category) {
        Intent intent = new Intent(this, ItemList.class);
        intent.putExtra(("Button"), category);
        startActivity(intent);
    }

    public void addDrawerItems(EditText einkauf) {
        String name = einkauf.getText().toString();
        if (!name.isEmpty()) {
            ShoppingListItem product = new ShoppingListItem(name);
            array.add(product);
            mAdapter = new ShoppingListItemAdapter(getApplication(), R.layout.shopping_list_layout, array);
            mDrawerList.setAdapter(mAdapter);
            einkauf.setText("");
        }
    }
    
    //This method closes and finishs the shopping process. If you have a shopping list and picked
    //the bought products, you can delete them from the shopping list and insert them directly to the database.
    public void finishShopping() {
        Intent intent = new Intent(this, AddFridgeItem.class);
        for (int i = 0; i < array.size(); ) {
            ShoppingListItem item = array.get(i);
            String product = item.getProduct();
            array.remove(i);
            mAdapter.notifyDataSetChanged();
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            if (item.selected) {
                intent.putExtra(("Product"), product);
                String category = "";
                intent.putExtra(("Category"), category);
                startActivity(intent);
            } else {
                arrayNew.add(item);
            }
        }
    }

    public void shareShoppingList() {
        String listText = "Einkaufszettel:\n\n";

        FridgeDB.deleteShoppingList();
        if (!array.isEmpty()) {
            FridgeDB.saveShoppingList(array);
            for (int i = 0; i < array.size(); i++) {
                ShoppingListItem item = array.get(i);
                listText = listText + "- " + item.getProduct() + "\n";
            }
            listText = listText + "\n**Sent from VirtualFridge**";
        }

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, listText);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int item) {
        deleteItem(item);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    public void deleteItem(int item) {
        mAdapter.remove(array.get(item));
        mAdapter.notifyDataSetChanged();
    }

    public void handleCommand() {
        //Get date of today, tomorrow and after tomorrow
        Calendar today = Calendar.getInstance();
        int day = today.get(Calendar.DATE);
        int month = today.get(Calendar.MONTH);
        month++;
        int year = today.get(Calendar.YEAR);
        String date = String.valueOf(day) + "." + String.valueOf(month) + "." + String.valueOf(year);

        today.add(Calendar.DATE, 1);
        int day2 = today.get(Calendar.DATE);
        int month2 = today.get(Calendar.MONTH);
        month2++;
        int year2 = today.get(Calendar.YEAR);
        String date2 = String.valueOf(day2) + "." + String.valueOf(month2) + "." + String.valueOf(year2);

        today.add(Calendar.DATE, 1);
        int day3 = today.get(Calendar.DATE);
        int month3 = today.get(Calendar.MONTH);
        month3++;
        int year3 = today.get(Calendar.YEAR);
        String date3 = String.valueOf(day3) + "." + String.valueOf(month3) + "." + String.valueOf(year3);

        //Read from DB which products are due within the next 3 days
        Cursor cursor = FridgeDB.getEntryByDate(date, date2, date3);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String item;

        //Read DB results
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                Date durability = new Date();
                try {
                    durability = sdf.parse(cursor.getString(2));
                } catch (Exception e) {
                }
                ;
                double quantity = Double.parseDouble(cursor.getString(4));
                String uom = cursor.getString(5);
                double price = Double.parseDouble(cursor.getString(3));
                String category = cursor.getString(6);
                FridgeItem product = new FridgeItem(id, name, durability, quantity, uom, price, category);
                productList.add(product);
                if (uom.equals("Stück")) {
                    item = quantity + " " + name;
                } else {
                    item = quantity + " " + uom + " " + name;
                }
                productNameList.add(item);
            } while (cursor.moveToNext());
        }
    }

    //Create notification to notify user via the homescreen about products that need to be consumed soon
    public void createNotification(ArrayList<String> productNameList) {

        if (!productNameList.isEmpty()) {
            //Set notification icon, title and text
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_fridge)
                    .setContentTitle("Zeit zu kochen!")
                    .setContentText("Einige Produkte laufen demnächst ab.");

            //Set long text of notification if user expands notification text
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.setBigContentTitle("Zeit zu kochen!");
            inboxStyle.addLine("Einige Produkte laufen demnächst ab:");

            //List products that are due soon in notification
            String[] products = new String[productNameList.size()];
            for (int i = 0; i < productNameList.size(); i++) {
                inboxStyle.addLine(productNameList.get(i));
            }

            //Apply all settings to the notification
            builder.setStyle(inboxStyle);

            //Get notification service of the system
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            //Build the notification and notify user on home screen
            notificationManager.notify(0, builder.build());
        }
    }
}
