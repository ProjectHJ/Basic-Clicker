package org.illuminationstudios.myapplication;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.WindowDecorActionBar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.kosalgeek.android.caching.FileCacher;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView text;

    double num;
    double total;

    final FileCacher<Double> totalCache = new FileCacher<>(MainActivity.this, "values.ahe");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

        TabHost.TabSpec tab1 = tabHost.newTabSpec("Upgrades");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("(WIP)");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("(WIP)");

        tab1.setIndicator("Tab1");
        tab1.setContent(new Intent(this,Tab1Activity.class));

        tab2.setIndicator("Tab2");
        tab2.setContent(new Intent(this,Tab2Activity.class));

        tab3.setIndicator("Tab3");
        tab3.setContent(new Intent(this,Tab3Activity.class));

        /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);


        button = (Button) findViewById(R.id.button);
        text = (TextView) findViewById(R.id.text);

        load_data();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num += 0.5;
                total = num;

                text.setText(Double.toString(total));

                save_data();
            }
        });
    }

    public void load_data() {
        try {
            num = totalCache.readCache();
            text.setText(Double.toString(num));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save_data() {
        try {
            totalCache.writeCache(num);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
