package com.example.as.waluty;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class Special extends AppCompatActivity {

    DBHelper mydb = new DBHelper(this);
    ArrayList<HashMap<String, String>> contactList3;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);

        Intent intent = getIntent();
        String message = intent.getStringExtra("lol");
        String code = intent.getStringExtra("lol2");

        String codepom= code.toLowerCase();
        if (codepom == "try") {
            codepom = "mytry";
        }

        int resID = getResources().getIdentifier(codepom, "drawable", getPackageName());

        TextView textView = (TextView) findViewById(R.id.textViewCode);
        TextView textView2 = (TextView) findViewById(R.id.textViewCode2);
        textView.setTypeface(null, Typeface.BOLD_ITALIC);
        textView.setText(message);
        textView2.setText(code);
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        ImageView iv2 = (ImageView) findViewById(R.id.imageViewSpecialC);
        iv2.setImageResource(resID);






        lv = (ListView) findViewById(R.id.sp_list);

        Cursor mCursor = mydb.getDataByCode(code);
        contactList3 = new ArrayList<>();
        mCursor.moveToFirst();
        while(!mCursor.isAfterLast()) {
            HashMap<String, String> contact2 = new HashMap<>();
            //String currency = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_CURRENCY));
            //String code = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_CODE));
            String mid = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_MID));
            String date = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_DATE));
           // contact2.put("currency", currency);
           // contact2.put("code", code);
            contact2.put("mid", mid);
            contact2.put("date", date);
            contactList3.add(contact2);
            mCursor.moveToNext();
        }

        ListAdapter adapter = new SimpleAdapter(Special.this, contactList3,
                R.layout.sp_list_item, new String[]{"mid", "date"},
                new int[]{R.id.mid, R.id.date});
        lv.setAdapter(adapter);
        String src="http://www.nbp.pl/wykresy2.aspx?cc="+code;
        //new DownloadImageTask((ImageView) findViewById(R.id.imageView))
        //        .execute(src);
        Picasso.with(this).load(src).error(R.drawable.charterror).resize(1800, 1800).centerInside().into(iv);
    }
}
