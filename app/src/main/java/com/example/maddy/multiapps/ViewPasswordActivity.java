package com.example.maddy.multiapps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Maddy on 3/29/2020.
 */

public class ViewPasswordActivity extends Activity {

    private LinearLayout linearLayout;
    private UserDetails userDetails;
    public LinearLayout.LayoutParams p;
    private String[] columns_to_view = {"id","applicationType","email","username","password","number","description","passwordcreatedDate","passwordExpiryDate","securityKey"};

    public LinearLayout createLayout(Context context, String val, int id, int index){
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

        return linearLayout;
    }



    public void printOnScreen(Context context) {

            LinearLayout l = new LinearLayout(context);
            l.setId(R.id.linear );
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_page);



        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);


        TextView textView = (TextView) findViewById(R.id.textDraw);
        textView.setCompoundDrawablesWithIntrinsicBounds(null,null,getDrawable(R.drawable.ic_cancel_black_24dp),null);

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

                //finish();

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
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
