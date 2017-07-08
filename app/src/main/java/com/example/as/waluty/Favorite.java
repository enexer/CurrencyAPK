package com.example.as.waluty;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Favorite extends AppCompatActivity {

    DBHelper mydb = new DBHelper(this);
    private ListView lvf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        lvf = (ListView) findViewById(R.id.listFavorite);
        fillListView();

        lvf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,String> map =(HashMap<String,String>)lvf.getItemAtPosition(position);
                String value = map.get("currency");
                String value2 = map.get("code");
                Toast.makeText(Favorite.this, "CLIKCED AT: " + value ,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),Special.class);
                intent.putExtra("lol",value);
                intent.putExtra("lol2",value2);
                startActivity(intent);
            }

        });




        lvf.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
//                Intent intent = new Intent(getApplicationContext(),Calculator.class);
//                startActivity(intent);
                HashMap<String,String> map =(HashMap<String,String>)lvf.getItemAtPosition(position);
                String value = map.get("code");

                Cursor mCursor = mydb.getFavorite(value);
                mCursor.moveToFirst();
                int count1 = 0;
                while(!mCursor.isAfterLast()) {
                    count1 = mCursor.getCount();
                    mCursor.moveToNext();
                }
                if(count1>0){
                    if(mydb.deleteFavorite(value)==1){
                        Toast.makeText(Favorite.this, "Waluta: " + value + " usunięta z ulubionych!" ,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Favorite.this, "ERROR: " + value ,Toast.LENGTH_SHORT).show();
                    }
                    fillListView();
                }
                else{
                    if(mydb.insertFavorite(value)){
                        Toast.makeText(Favorite.this, "Waluta: " + value + " dodana do ulubionych!" ,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Favorite.this, "NIE MOZNA DODAC DO ULUBIONYCH!" ,Toast.LENGTH_SHORT).show();
                    }
                    fillListView();
                }


                Log.v("long clicked","pos: " + position);


                return true;
            }
        });
    }

    public void fillListView(){
        Cursor mCursor = mydb.getLastDataFavorite();
        ArrayList<HashMap<String, Object>> contactList3;
        contactList3 = new ArrayList<>();
        mCursor.moveToFirst();

        while(!mCursor.isAfterLast()) {
            HashMap<String, Object> contact2 = new HashMap<>();
            String currency = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_CURRENCY));
            String code = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_CODE));
            String mid = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_MID));

            String fine_code = code.toLowerCase();

            if (fine_code == "try") {
                fine_code = "mytry";
            }
            // int index = mid.indexOf(".");
            //int positions = 4;
            //String mm = mid.substring(0, index + positions);
            int resID = getResources().getIdentifier(fine_code, "drawable", getPackageName());
            contact2.put("img", resID);
            contact2.put("currency", currency);
            contact2.put("code", code);
            contact2.put("mid", mid);
            contactList3.add(contact2);
            mCursor.moveToNext();
        }

        ListAdapter adapter = new SimpleAdapter(Favorite.this, contactList3,
                R.layout.list_item, new String[]{"img", "currency", "code", "mid"},
                new int[]{R.id.imageView1, R.id.textViewCurrency, R.id.textViewCode, R.id.textViewMid});
        lvf.setAdapter(adapter);
    }
}
