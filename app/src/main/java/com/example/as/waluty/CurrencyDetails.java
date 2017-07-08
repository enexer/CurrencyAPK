package com.example.as.waluty;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CurrencyDetails extends AppCompatActivity {

    public String time="5d";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_details);

        Intent intent = getIntent();
        final String code = intent.getStringExtra("code");

        final String codeto = intent.getStringExtra("codeto");
        String mid = intent.getStringExtra("mid");
        String amount = intent.getStringExtra("amount");
        String timeOnShow = intent.getStringExtra("time");

        TextView tw = (TextView) findViewById(R.id.textViewDetails);
        TextView tw2 = (TextView) findViewById(R.id.textViewDetails2);
        TextView tw_time = (TextView) findViewById(R.id.textViewDetailsTime);
        TextView tw_chart = (TextView) findViewById(R.id.textViewDetailsChart);

        final ImageView iv = (ImageView) findViewById(R.id.imageViewDetailsChart);
        ImageView ivfrom = (ImageView) findViewById(R.id.imageViewDetailFrom);
        ImageView ivto = (ImageView) findViewById(R.id.imageViewDetailTo);

        String fine_code_from = code.toLowerCase();
        String fine_code_to = codeto.toLowerCase();

        int resID = getResources().getIdentifier(fine_code_from, "drawable", getPackageName());
        int resID2 = getResources().getIdentifier(fine_code_to, "drawable", getPackageName());

        assert ivfrom != null;
        ivfrom.setImageResource(resID);
        assert ivto != null;
        ivto.setImageResource(resID2);
        assert tw != null;
        tw.setTypeface(null, Typeface.BOLD_ITALIC);
        tw .setText(String.format("%s %s", mid, code));

        assert tw2 != null;
        tw2.setTypeface(null, Typeface.BOLD_ITALIC);
        tw2 .setText(String.format("%s %s",amount, codeto));

        assert tw_time != null;
        tw_time.setTypeface(null, Typeface.BOLD_ITALIC);
        tw_time .setText(String.format("%s",timeOnShow));

        assert tw_chart != null;
        tw_chart.setTypeface(null, Typeface.BOLD_ITALIC);
        tw_chart .setText(String.format("%s/%s",codeto,code));
        final String con =code+codeto;

        final Button button1d = (Button) findViewById(R.id.button1d);
        if (button1d != null) {
            button1d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    time="1d";
                    String src="https://chart.finance.yahoo.com/z?s="+con+"%3dX&t="+time+"&q=l&l=on&z=m&a=v&p=s&lang=pl-PL&region=PL";
                    Picasso.with(CurrencyDetails.this).load(src).error(R.drawable.default_status).placeholder(R.drawable.loading).resize(1800, 1800).centerInside().into(iv);
                }
            });
        }
        Button button5d = (Button) findViewById(R.id.button5d);
        if (button5d != null) {
            button5d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    time="5d";
                    String src="https://chart.finance.yahoo.com/z?s="+con+"%3dX&t="+time+"&q=l&l=on&z=m&a=v&p=s&lang=pl-PL&region=PL";
                    Picasso.with(CurrencyDetails.this).load(src).error(R.drawable.default_status).placeholder(R.drawable.loading).resize(1800, 1800).centerInside().into(iv);
                }
            });
        }
        Button button1m = (Button) findViewById(R.id.button1m);
        if (button1m != null) {
            button1m.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    time="1m";
                    String src="https://chart.finance.yahoo.com/z?s="+con+"%3dX&t="+time+"&q=l&l=on&z=m&a=v&p=s&lang=pl-PL&region=PL";
                    Picasso.with(CurrencyDetails.this).load(src).error(R.drawable.default_status).placeholder(R.drawable.loading).resize(1800, 1800).centerInside().into(iv);
                }
            });
        }
        Button button3m = (Button) findViewById(R.id.button3m);
        if (button3m != null) {
            button3m.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    time="3m";
                    String src="https://chart.finance.yahoo.com/z?s="+con+"%3dX&t="+time+"&q=l&l=on&z=m&a=v&p=s&lang=pl-PL&region=PL";
                    Picasso.with(CurrencyDetails.this).load(src).error(R.drawable.default_status).placeholder(R.drawable.loading).resize(1800, 1800).centerInside().into(iv);
                }
            });
        }
        Button button1y = (Button) findViewById(R.id.button1y);
        if (button1y != null) {
            button1y.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    time="1y";
                    String src="https://chart.finance.yahoo.com/z?s="+con+"%3dX&t="+time+"&q=l&l=on&z=m&a=v&p=s&lang=pl-PL&region=PL";
                    Picasso.with(CurrencyDetails.this).load(src).error(R.drawable.default_status).placeholder(R.drawable.loading).resize(1800, 1800).centerInside().into(iv);
                }
            });
        }
        Button button5y = (Button) findViewById(R.id.button5y);
        if (button5y != null) {
            button5y.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    time="5y";
                    String src="https://chart.finance.yahoo.com/z?s="+con+"%3dX&t="+time+"&q=l&l=on&z=m&a=v&p=s&lang=pl-PL&region=PL";
                    Picasso.with(CurrencyDetails.this).load(src).error(R.drawable.default_status).placeholder(R.drawable.loading).resize(1800, 1800).centerInside().into(iv);
                }
            });
        }

        Button buttonLine = (Button) findViewById(R.id.buttonLine);
        if (buttonLine != null) {
            buttonLine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type = "l";
                    String src="https://chart.finance.yahoo.com/z?s="+con+"%3dX&t="+time+"&q="+type+"&l=on&z=m&a=v&p=s&lang=pl-PL&region=PL";
                    Picasso.with(CurrencyDetails.this).load(src).error(R.drawable.default_status).placeholder(R.drawable.loading).resize(1800, 1800).centerInside().into(iv);
                }
            });
        }

        Button buttonBar = (Button) findViewById(R.id.buttonBar);
        if (buttonBar != null) {
            buttonBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type = "b";
                    String src="https://chart.finance.yahoo.com/z?s="+con+"%3dX&t="+time+"&q="+type+"&l=on&z=m&a=v&p=s&lang=pl-PL&region=PL";
                    Picasso.with(CurrencyDetails.this).load(src).error(R.drawable.default_status).placeholder(R.drawable.loading).resize(1800, 1800).centerInside().into(iv);
                }
            });
        }

        Button buttonCandle = (Button) findViewById(R.id.buttonCandle);
        if (buttonCandle != null) {
            buttonCandle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String type = "c";
                    String src="https://chart.finance.yahoo.com/z?s="+con+"%3dX&t="+time+"&q="+type+"&l=on&z=m&a=v&p=s&lang=pl-PL&region=PL";
                    Picasso.with(CurrencyDetails.this).load(src).error(R.drawable.default_status).placeholder(R.drawable.loading).resize(1800, 1800).centerInside().into(iv);
                }
            });
        }
        String src="https://chart.finance.yahoo.com/z?s="+con+"%3dX&t="+time+"&q=l&l=on&z=m&a=v&p=s&lang=pl-PL&region=PL";
        Picasso.with(CurrencyDetails.this).load(src).error(R.drawable.default_status).placeholder(R.drawable.loading).resize(1800, 1800).centerInside().into(iv);



    }



}