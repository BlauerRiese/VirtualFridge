package bjoernbinzer.virtualfridge;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddFridgeItem extends AppCompatActivity implements AddUomDialogFragment.Communicator{

    // Declaration of GUI Elements and all other required variables
    private EditText durability;
    private EditText uom;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    public DatePickerDialog.OnDateSetListener listener;
    int calendar_year, calendar_month, calendar_day_of_month;
    Calendar c;
    private Spinner spinner;
    private ArrayAdapter adapter;
    public String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fridge_item);

        Intent intent = getIntent();

        /* for segue from shopping list: name of the product is automatically synchronized with what
        was added to the shopping list */
        String product = intent.getStringExtra("Product");

        if (!product.isEmpty()){
            EditText editProduct = (EditText) findViewById(R.id.editText_product);
            editProduct.setText(product);
        }


        /* for segue from ItemList add-Button: name of category is automatically synchronized with
        the name of the category and set over the spinner*/
        String intent_category = intent.getStringExtra("Category");

        if (!intent_category.isEmpty()) {
            Spinner spinner = (Spinner) findViewById(R.id.spinner_category);
            switch (intent_category) {
                case "Gemüse":
                    spinner.setSelection(1);
                    break;
                case "Obst":
                    spinner.setSelection(2);
                    break;
                case "Fleisch & Fisch":
                    spinner.setSelection(3);
                    break;
                case "Getränke":
                    spinner.setSelection(4);
                    break;
                case "Gewürze":
                    spinner.setSelection(5);
                    break;
                case "Tiefkühl":
                    spinner.setSelection(6);
                    break;
                case "Saucen & Öle":
                    spinner.setSelection(7);
                    break;
                case "Getreide & Co.":
                    spinner.setSelection(8);
                    break;
                case "Snacks":
                    spinner.setSelection(9);
                    break;
                case "Milch & Eier":
                    spinner.setSelection(10);
                    break;
                case "Sonstiges":
                    spinner.setSelection(11);
                    break;
            }
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        /**setSupportActionBar(toolbar); **/
        /**getSupportActionBar().setTitle("Produkt hinzufügen"); **/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check if mandatory fields are all filled
                // save all entries of the text fields into local variables

                EditText editProduct = (EditText) findViewById(R.id.editText_product);
                String product = editProduct.getText().toString();
                if (product.isEmpty()){
                    Toast.makeText(getApplication(), getString(R.string.ToastMessage), Toast.LENGTH_LONG).show();
                    return;
                }
                EditText editDurability = (EditText) findViewById(R.id.editText_durability);
                String durability = editDurability.getText().toString();
                if (durability.isEmpty()){
                    Toast.makeText(getApplication(),getString(R.string.ToastMessage), Toast.LENGTH_LONG).show();
                    return;
                }
                EditText editQuantity = (EditText) findViewById(R.id.editText_quantity);
                String quantitystr = editQuantity.getText().toString();
                if (quantitystr.isEmpty()){
                    Toast.makeText(getApplication(),getString(R.string.ToastMessage), Toast.LENGTH_LONG).show();
                    return;
                }

                int quantity = Integer.parseInt(quantitystr);
                EditText editUom = (EditText) findViewById(R.id.editText_uom);
                String uom = editUom.getText().toString();
                EditText editPrice = (EditText) findViewById(R.id.editText_price);
                String pricestr = editPrice.getText().toString().replace(",",".");
                double price;
                if (pricestr.isEmpty()){
                    price = 0;
                }else{
                    price = Double.parseDouble(pricestr);
                }

                Spinner spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
                String category = spinnerCategory.getSelectedItem().toString();
                if (category.equals(getString(R.string.default_category))){
                    Toast.makeText(getApplication(),getString(R.string.ToastMessage), Toast.LENGTH_LONG).show();
                    return;
                }

                // save all local variables in database
                long rowID = FridgeDB.insertEntry(product, durability, quantity, uom, price, category);

                // go back to Main Activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
                finish();
            }
        });

        // finding GUI elements of this view in XML, setting them up
        dateFormatter = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
        Date date = new Date();
        durability = (EditText) findViewById(R.id.editText_durability);
        durability.setText(dateFormatter.format(date));
        durability.setInputType(InputType.TYPE_NULL);
        durability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog(view);
            }
        });

        uom = (EditText) findViewById(R.id.editText_uom);
        uom.setInputType(InputType.TYPE_NULL);
        uom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openUomPickerDialog(view);
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner_category);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // Change color for Floating Action Button depending on selected category
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

                if (category.equals(getString(R.string.default_category))){
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3F51B5")));
                    toolbar.setBackgroundColor(Color.parseColor("#3F51B5"));
                    toolbar.setTitle(category);
                }
                else if(category.equals(getString(R.string.text_box01))) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#459b63")));
                    toolbar.setBackgroundColor(Color.parseColor("#459b63"));
                    toolbar.setTitle(category);
                }
                else if (category.equals(getString(R.string.text_box02))) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f1b941")));
                    toolbar.setBackgroundColor(Color.parseColor("#f1b941"));
                    toolbar.setTitle(category);
                }
                else if (category.equals(getString(R.string.text_box03))) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#c15660")));
                    toolbar.setBackgroundColor(Color.parseColor("#c15660"));
                    toolbar.setTitle(category);
                }
                else if (category.equals(getString(R.string.text_box04))) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#549dd0")));
                    toolbar.setBackgroundColor(Color.parseColor("#549dd0"));
                    toolbar.setTitle(category);
                }
                else if (category.equals(getString(R.string.text_box05))) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#a08f53")));
                    toolbar.setBackgroundColor(Color.parseColor("#a08f53"));
                    toolbar.setTitle(category);
                }
                else if (category.equals(getString(R.string.text_box06))) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6bb8bb")));
                    toolbar.setBackgroundColor(Color.parseColor("#6bb8bb"));
                    toolbar.setTitle(category);
                }
                else if (category.equals(getString(R.string.text_box07))) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#402c38")));
                    toolbar.setBackgroundColor(Color.parseColor("#402c38"));
                    toolbar.setTitle(category);
                }
                else if (category.equals(getString(R.string.text_box08))) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#c6af52")));
                    toolbar.setBackgroundColor(Color.parseColor("#c6af52"));
                    toolbar.setTitle(category);
                }
                else if (category.equals(getString(R.string.text_box09))) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9d63a9")));
                    toolbar.setBackgroundColor(Color.parseColor("#9d63a9"));
                    toolbar.setTitle(category);
                }
                else if (category.equals(getString(R.string.text_box10))) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#919aa9")));
                    toolbar.setBackgroundColor(Color.parseColor("#919aa9"));
                    toolbar.setTitle(category);
                }
                else if (category.equals(getString(R.string.text_box11))) {
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#6bd3a8")));
                    toolbar.setBackgroundColor(Color.parseColor("#6bd3a8"));
                    toolbar.setTitle(category);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }

    // setting up the date picker
    public void openDatePickerDialog(View view) {
        c = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, listener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                durability.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar_year = c.get(Calendar.YEAR), calendar_month = c.get(Calendar.MONTH), calendar_day_of_month = c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setSpinnersShown(false);
        datePickerDialog.getDatePicker().setCalendarViewShown(true);
        datePickerDialog.show();
    }

    // setting up the Unit of Measure picker
    public void openUomPickerDialog(View view) {
        FragmentManager manager = getFragmentManager();

        AddUomDialogFragment uomDialogFragment = new AddUomDialogFragment();
        uomDialogFragment.show(manager, "uomDialogFragment");
    }

    @Override
    public void onDialogMessage(String message) {
        uom.setText(message);
    }
}
