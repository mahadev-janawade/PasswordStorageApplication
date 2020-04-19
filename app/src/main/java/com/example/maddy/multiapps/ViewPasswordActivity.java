package com.example.maddy.multiapps;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Maddy on 3/29/2020.
 */

public class ViewPasswordActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private UserDetails userDetails;
    public LinearLayout.LayoutParams p;
    private String[] columns_to_view = {"id","applicationType","email","username","password","number","description","passwordcreatedDate","passwordExpiryDate","securityKey"};
    private String dataToCopy;

    public LinearLayout createLayout(Context context, String val, int id, int index){
        dataToCopy = val;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(p);
        TextView text1 = new TextView(this);
        text1.setText(columns_to_view[index] + " : ");
        linearLayout.addView(text1);

        TextView value = new TextView(this);
        value.setId(id);
        value.setTextIsSelectable(true);
        value.setPaddingRelative(10, 10, 10, 10);
        value.setText(val);
        linearLayout.addView(value);

        ImageButton imageButton = new ImageButton(this);
        LinearLayout.LayoutParams l1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        imageButton.setId(id+8738);
        imageButton.setBackgroundDrawable(null);
        imageButton.setImageResource(R.drawable.ic_content_copy_black_24dp);
        imageButton.setLayoutParams(l1);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = (view.getId())-8738;
                //ProcessData.printvalue(getApplicationContext(),String.valueOf(id));
                //ProcessData.printvalue(getApplicationContext(),String.valueOf(((TextView)((View)view.getParent()).findViewById(id)).getText()));
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("CopiedValue", String.valueOf(((TextView)((View)view.getParent()).findViewById(id)).getText()));
                clipboardManager.setPrimaryClip(clipData);

                ClipData abc = clipboardManager.getPrimaryClip();
                ClipData.Item item = abc.getItemAt(0);
                String text = item.getText().toString();

                if(text.equals("")){
                    ProcessData.printvalue(getApplicationContext(),"Copied: Content empty");
                }else{
                    ProcessData.printvalue(getApplicationContext(),"Copied Contents");
                }

            }
        });
        linearLayout.addView(imageButton);

        return linearLayout;
    }



    public void printOnScreen(Context context) {

            LinearLayout l = new LinearLayout(context);
            l.setId(R.id.linear);
            l.setOrientation(LinearLayout.VERTICAL);
            p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            p.setMargins(10, 10, 10, 10);
            l.setLayoutParams(p);

            l.addView(createLayout(getApplicationContext(),userDetails.applicationType,1, 1));

            l.addView(createLayout(getApplicationContext(),userDetails.email, 2, 2));


            l.addView(createLayout(getApplicationContext(), userDetails.userName,
                    3, 3));


            l.addView(createLayout(getApplicationContext(), userDetails.password,
                    4, 4));

            l.addView(createLayout(getApplicationContext(), userDetails.number,
                    5, 5));

            l.addView(createLayout(getApplicationContext(), userDetails.description,
                    6, 6));

            l.addView(createLayout(getApplicationContext(), userDetails.passwordCreatedDate,
                    7, 7));

            l.addView(createLayout(getApplicationContext(), userDetails.passwordExpiryDate,
                    8, 8));


        l.setBackground(getResources().getDrawable(R.drawable.textviewborder));
        linearLayout.addView(l);
    }

    private void create(){
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        getSupportActionBar().hide();
        findViewById(R.id.app_bar).setVisibility(View.GONE);

        TextView textView = (TextView) findViewById(R.id.textDraw);
        textView.setCompoundDrawablesWithIntrinsicBounds(null,null, ContextCompat.getDrawable(this,R.drawable.ic_cancel_black_24dp),null);

        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    int x = (int) motionEvent.getX();
                    int y = (int) motionEvent.getY();

                    TextView view1 = (TextView)view.findViewById(R.id.textDraw);
                    Drawable[] drawables = view1.getCompoundDrawables();

                    if(x>= view.getRight()-view.getPaddingRight()-drawables[2].getBounds().width() && x<= view.getRight()-view.getPaddingRight() &&
                            y>= view.getTop()-view.getPaddingTop() && y<= view.getBottom()-view.getPaddingBottom()
                            ) {
                        finish();
                    }
                }
                return false;
            }
        });

        Intent intent = getIntent();
        userDetails = intent.getParcelableExtra("userdetailsView");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height =  dm.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.9) );

        printOnScreen(getApplicationContext());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_page);
        create();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
