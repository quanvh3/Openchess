package com.bsencan.openchess.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends Activity {

    TextView newGameTv, highScoreTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        newGameTv = (TextView) findViewById(R.id.menu_activity_new_tv);
        highScoreTv = (TextView) findViewById(R.id.menu_activity_high_score_tv);
        newGameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, AndroidLauncher.class));
            }
        });
    }

}
