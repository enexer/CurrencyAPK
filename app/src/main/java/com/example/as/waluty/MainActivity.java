package com.example.as.waluty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DBHelper mydb = new DBHelper(this);
    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;
    private ListView lv2;
    private Button bt;
    public Runnable runnable;
    final Handler handler = new Handler();
    ArrayList<HashMap<String, String>> contactList;
    ArrayList<HashMap<String, String>> contactList2;
    ArrayList<HashMap<String, String>> contactList3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        contactList2 = new ArrayList<>();

        lv = (ListView) findViewById(R.id.listCurrency);
        lv2 = (ListView) findViewById(R.id.list2);
        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        bt = (Button) findViewById(R.id.buttoncc);
        lv.setLongClickable(true);
        fillListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,String> map =(HashMap<String,String>)lv.getItemAtPosition(position);
                String value = map.get("currency");
                String value2 = map.get("code");
                //Toast.makeText(MainActivity.this, "CLIKCED AT: " + value ,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),Special.class);
                intent.putExtra("lol",value);
                intent.putExtra("lol2",value2);
                startActivity(intent);
            }

        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,String> map =(HashMap<String,String>)lv.getItemAtPosition(position);
                String value = map.get("code");

                Cursor mCursor = mydb.getFavorite(value);
                mCursor.moveToFirst();
                int count1 = 0;
                while(!mCursor.isAfterLast()) {
                    count1 = mCursor.getCount();
                    mCursor.moveToNext();
                }
                if(count1>0){
                        Toast.makeText(MainActivity.this, "Już dodałeś te walute do ulubionych!" ,Toast.LENGTH_SHORT).show();
                   // fillListView();
                }
                else{
                    if(mydb.insertFavorite(value)){
                        Toast.makeText(MainActivity.this, "Waluta: " + value + " dodana do ulubionych!" ,Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Blad podczas dodawania!" ,Toast.LENGTH_SHORT).show();
                    }
                   // fillListView();
                }
                Log.v("long clicked","pos: " + position);
                return true;
            }
        });


        runnable = new Runnable() {
            public void run() {
                new GetContacts().execute();

            }
        };
        handler.postDelayed(runnable, 1);

        Button button = (Button) findViewById(R.id.buttonRefresh);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, 0);
                    fillListView();
                }
            });
        }


    }

    public void fillListView(){
        Cursor mCursor = mydb.getLastData();
        ArrayList<HashMap<String, Object>> contactList3;
        contactList3 = new ArrayList<>();
        mCursor.moveToFirst();

        while(!mCursor.isAfterLast()) {
            HashMap<String, Object> contact2 = new HashMap<>();
            String currency = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_CURRENCY));
            String code = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_CODE));
            String mid = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_MID));
            String fine_code = code.toLowerCase();

            mid = mid.substring(0, Math.min(mid.length(), 6));


            int resID = getResources().getIdentifier(fine_code, "drawable", getPackageName());
            contact2.put("img", resID);
            contact2.put("currency", currency);
            contact2.put("code", code);
            contact2.put("mid", mid);
            contactList3.add(contact2);
            mCursor.moveToNext();
        }

        ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList3,
                R.layout.list_item, new String[]{"img", "currency", "code", "mid"},
                new int[]{R.id.imageView1, R.id.textViewCurrency, R.id.textViewCode, R.id.textViewMid});
        lv.setAdapter(adapter);
    }
    public void openCalculator(View view) {
        Intent intent = new Intent(this, Calculator.class);
       // EditText editText = (EditText) findViewById(R.id.edit_message);
       // String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    public void openFavorite(View view) {
        Intent intent = new Intent(this, Favorite.class);
        startActivity(intent);
    }

    public class GetContacts extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog;
        @Override
        protected void onPreExecute() {

            this.dialog = ProgressDialog.show(MainActivity.this, null, null);
            this.dialog.setMessage("Ladowanie...");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            Cursor mCursor = mydb.getCountAll();
            mCursor.moveToFirst();
            int count1 = 0;
            while(!mCursor.isAfterLast()) {
                count1 = mCursor.getCount();
                mCursor.moveToNext();
            }
           // String url = "http://api.nbp.pl/api/exchangerates/tables/A/";
            String url = "";
            if (count1<100)
            {
                url = "http://api.nbp.pl/api/exchangerates/tables/A/last/30/";
            }else if (count1>100)
            {
                url = "http://api.nbp.pl/api/exchangerates/tables/A/";
            }

            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray contacts = new JSONArray(jsonStr);



                    for (int i = 0; i < contacts.length(); i++) {


                        Cursor cursor = mydb.getLastDate();
                        String dateold;
                        if (cursor != null && cursor.moveToFirst()) {
                            dateold = cursor.getString(cursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_DATE));
                            cursor.close();
                        }
                        else
                        {
                            mydb.insertFavorite("USD");
                            mydb.insertFavorite("EUR");
                            mydb.insertFavorite("GBP");
                            mydb.insertFavorite("CHF");

                            dateold="2012-12-12";
                        }
                        String table = contacts.getJSONObject(i).getString("table");
                        String no = contacts.getJSONObject(i).getString("no");
                        String effectiveDate = contacts.getJSONObject(i).getString("effectiveDate");
                        Log.e(TAG, "Date DB: " + dateold + "Date Current JSON:" + effectiveDate);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date olddate = sdf.parse(dateold);
                        Date newdate = sdf.parse(effectiveDate);
                        Log.e(TAG, "Date DB: " + olddate + " JSON:" + newdate);
                        String count = "Zapisane rekordy w bazie: " + Integer.toString(count1);

                        if (olddate.compareTo(newdate) == 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),
                                            "Dane są aktualne!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                            Log.e(TAG, "ERROR: Date DB: " + dateold + "Date Current JSON:" + effectiveDate);
                            HashMap<String, String> contact2 = new HashMap<>();
                            String table1 = "Kursy walut z dnia: " + dateold;
                            contact2.put("table", table1);
                            contact2.put("effectiveDate", count);
                            contactList2.add(contact2);
                        } else {
                            HashMap<String, String> contact2 = new HashMap<>();
                            String table2 = "Kursy walut z dnia: " + effectiveDate;
                            contact2.put("table", table2);
                            contact2.put("effectiveDate", effectiveDate);
                            contactList2.add(contact2);
                            String rate = contacts.getJSONObject(i).getString("rates");
                            JSONArray rates = new JSONArray(rate);
                            for (int j = 0; j < rates.length(); j++) {
                                String currency = rates.getJSONObject(j).getString("currency");
                                String code = rates.getJSONObject(j).getString("code");
                                String mid = rates.getJSONObject(j).getString("mid");

                                if (mydb.insertContact(currency, code, mid, effectiveDate)) {
                                    Log.e(TAG, "Zapisano walute: " + currency + "date: " + effectiveDate);
                                } else {
                                    Log.e(TAG, "BLAD  W ZAPISIE DO BAZY!");
                                }
                            }
                        }


                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing err: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing err: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } else {
                Log.e(TAG, "json serwer blad ladowania.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Nie moge pobrac danych JSON z serwera!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (this.dialog != null) {
                this.dialog.dismiss();
            }
            super.onPostExecute(result);
            ListAdapter adapter2 = new SimpleAdapter(MainActivity.this, contactList2,
                    R.layout.list_item2, new String[]{"table", "effectiveDate"},
                    new int[]{R.id.table, R.id.effectiveDate});
            lv2.setAdapter(adapter2);
            fillListView();


        }
    }
}