package org.vatsag.petshots;

import org.vatsag.R;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;

/*
 * Author		:	Srivatsa Haridas
 * Date			:	October 15th 2013
 * Description	:	Splash screen	
 * */
public class SplashActivity extends Activity {
	
	//TODO: Make the splash screen for 5 secs
	private static int SPLASH_SCREEN_DELAY = 3000;
	private Bitmap bmSplashLogo;
	private ImageView imgviewapplogo;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        imgviewapplogo = (ImageView) findViewById(R.id.imgLogo);
//
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inPurgeable = true;
//        bmOptions.inJustDecodeBounds = true;
//        bmSplashLogo = BitmapFactory.decodeResource(this.getResources(),R.drawable.ic_logo,bmOptions);
//
//        imgviewapplogo.setImageBitmap(bmSplashLogo);
        
        new Handler().postDelayed(new Runnable() {
			
			public void run() {

				Intent i = new Intent(SplashActivity.this,DashBoardActivity.class);
				startActivity(i);
				//close the splash screen activity
				finish();
			}
		}, SPLASH_SCREEN_DELAY);
        
//        bmSplashLogo.recycle();
//        bmSplashLogo = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }
}
