package com.example.maddy.multiapps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Maddy on 3/24/2020.
 */

public class PopupCLass extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        //((LinearLayout)findViewById(R.id.popup)).getBackground().setAlpha(200);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height =  dm.heightPixels;

        getWindow().setLayout((int)(width*.6),(int)(height*.4) );

        Intent i = getIntent();
        ((TextView)findViewById(R.id.message)).setText((i.getStringExtra("message")).toString());
        Button b1 = (Button)findViewById(R.id.button1);
        String text1 = i.getStringExtra("button1");
        b1.setText(text1);
        Button b2 = (Button)findViewById(R.id.button2);
        String text2 = i.getStringExtra("button2");
        b2.setText(text2);

        ((LinearLayout)findViewById(R.id.popup)).setBackground(getResources().getDrawable(R.drawable.textviewborder));


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result","yes");
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result","no");
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("result","no");
        setResult(RESULT_OK,intent);
        finish();
        super.onBackPressed();
    }
}
