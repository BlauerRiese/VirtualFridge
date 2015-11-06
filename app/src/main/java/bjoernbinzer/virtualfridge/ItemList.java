package bjoernbinzer.virtualfridge;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

/**
 * Created by BJOERN on 06.11.2015.
 */
public class ItemList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_item_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String button = intent.getStringExtra("Button");
        getSupportActionBar().setTitle(button);

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
    }
}
