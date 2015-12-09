package bjoernbinzer.virtualfridge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashScreen extends Activity {

    //Duration of wait
    public final int SPLASH_DISPLAY_LENGTH = 1000;

    //Called on first creation of the activity
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //Handler to start main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Create intent to start main activity
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}

