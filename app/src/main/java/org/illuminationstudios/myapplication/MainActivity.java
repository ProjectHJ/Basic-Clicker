package org.illuminationstudios.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.kosalgeek.android.caching.FileCacher;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView text;

    double num;
    double total;

    UpdateApp atualizaApp;

    final FileCacher<Double> totalCache = new FileCacher<>(MainActivity.this, "values.ahe");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        atualizaApp = new UpdateApp();
        atualizaApp.setContext(getApplicationContext());
        atualizaApp.execute("https://github.com/ProjectHJ/Basic-Clicker/blob/master/app/app-release.apk");

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
