package org.illuminationstudios.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;

import com.kosalgeek.android.caching.FileCacher;

import java.io.IOException;

/**
 * Created by Dustin on 4/2/17.
 */
public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_INSTALL_PACKAGES = 0x00000001;
    Button button;
    TextView text;
    ProgressBar progress;

    double num;
    double total;

    int max = 50;
    int adder = 1;
    int progressStatus = 0;

    final FileCacher<Double> DoubleValueCache = new FileCacher<>(MainActivity.this, "ValueCacheDouble");
    final FileCacher<Integer> IntegerValueCache = new FileCacher<>(MainActivity.this, "ValueCacheInt");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setMax(max);

        button = (Button) findViewById(R.id.button);
        text = (TextView) findViewById(R.id.text);

        load_data();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (progressStatus < progress.getMax()) progressStatus += 1;
                if (progressStatus == progress.getMax()) {
                    max += 5;
                    progressStatus = 0;
                }

                num += adder;
                total = num;

                text.setText(Double.toString(total));
                progress.setProgress(progressStatus);

                save_data();
            }
        });
    }

    public void load_data() {
        try {
            num = DoubleValueCache.readCache();
            max = IntegerValueCache.readCache();
            adder = IntegerValueCache.readCache();
            progressStatus = IntegerValueCache.readCache();
        } catch (IOException e) {
            e.printStackTrace();
        }

        text.setText(Double.toString(num));
        progress.setProgress(progressStatus);
    }

    public void save_data() {
        try {
            DoubleValueCache.writeCache(num);
            IntegerValueCache.writeCache(max);
            IntegerValueCache.writeCache(adder);
            IntegerValueCache.writeCache(progressStatus);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
