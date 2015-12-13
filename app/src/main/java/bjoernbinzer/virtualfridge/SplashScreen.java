package bjoernbinzer.virtualfridge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashScreen extends Activity {

    //Declare length of splash screen
    public final int SPLASH_DISPLAY_LENGTH = 1100;
    AnimationDrawable animation;

    //Show splash screen and start animation
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        startAnimation();

    }

    //Start main activity after splash screen animation
    class Starter implements Runnable {
        public void run() {
            animation.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    SplashScreen.this.startActivity(intent);
                    SplashScreen.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);

        }
    }

    //Animation of the splash screen
    private void startAnimation() {
        ImageView imageView = (ImageView) findViewById(R.id.splashView);

        animation = new AnimationDrawable();
        //Switch between the images of the fridge logo
        animation.addFrame(getResources().getDrawable(R.drawable.splash_a), 250);
        animation.addFrame(getResources().getDrawable(R.drawable.splash_b), 250);
        animation.addFrame(getResources().getDrawable(R.drawable.splash_c), 250);
        animation.addFrame(getResources().getDrawable(R.drawable.splash_d), 250);
        animation.addFrame(getResources().getDrawable(R.drawable.splash_a), 100);
        animation.setOneShot(true);

        imageView.setImageDrawable(animation);

        //Start animation of the splash screen
        imageView.post(new Starter());
    }
}

