package com.example.as.waluty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private ListView lv;
    public String code1; // global
    public String code2; // global
    public String firstCodeFrom;
    public String firstCodeTo;
    public boolean switchStatus = true;
    final Handler handler = new Handler();
    public Runnable runnable;
    public Runnable runnable2;
    public boolean repeatTask = false;
    public long repeatTime = 0;
    private SeekBar seekBar;
    private TextView textViewSeek;

    public int progress = 0;

    private void initializeVariables() {
        lv = (ListView) findViewById(R.id.listViewLive);
        seekBar = (SeekBar) findViewById(R.id.seekBarTime);
        textViewSeek = (TextView) findViewById(R.id.textViewSeek);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        View view = new View(Main2Activity.this);
        initializeVariables();

        firstCodeFrom = "USD";
        firstCodeTo = "PLN";
        System.out.println("Info1  " + switchStatus);
        System.out.println("Info2" + firstCodeFrom + "\n" + firstCodeTo);

        seekBar.setVisibility(view.GONE);
        textViewSeek.setVisibility(view.GONE);


        // textViewSeek.setText(String.format("Covered: %d/%d", seekBar.getProgress(), seekBar.getMax()));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            long prog=0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                // Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
                prog=progresValue;
                textViewSeek.setText(prog + " sec");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textViewSeek.setText(prog + " sec");
                repeatTime = prog*1000;
                System.out.println(repeatTime);
                // Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });




        runnable = new Runnable() {
            public void run() {
                new MyTask(lv, firstCodeFrom, firstCodeTo, repeatTask, "1").execute();

                if(repeatTask==true) {
                    handler.postDelayed(this, repeatTime);
                }

            }
        };

//        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
//        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
//            public void run() {
//                new MyTask(lv, firstCodeFrom, firstCodeTo, "1").execute();
//
//                //  anim.start();
//                // lv.setAnimation(anim);
//                //anim2.start();
//            }
//        }, 0, 3, TimeUnit.SECONDS);



        Spinner spinner1 = (Spinner) findViewById(R.id.spinnerCurrency1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinnerCurrency2);

        final List<String> codeList = new ArrayList<String>(Arrays.asList("*", "***", "AED", "ALL", "ARS", "AUD", "BAM", "BGN",
                "BHD", "BOB", "BTC", "BRL", "BYR", "CAD", "CHF", "CLP",
                "CNY", "COP", "CRC", "CSD", "CZK", "DKK", "DOP",
                "DZD", "EEK", "EGP", "EUR", "GBP", "GTQ", "HKD",
                "HNL", "HRK", "HUF", "IDR", "ILS", "INR", "IQD",
                "ISK", "JOD", "JPY", "KRW", "KWD", "LBP", "LTL",
                "LVL", "LYD", "MAD", "MKD", "MXN", "MYR", "NIO",
                "NOK", "NZD", "OMR", "PAB", "PEN", "PHP", "PLN",
                "PYG", "QAR", "RON", "RSD", "RUB", "SAR", "SDG",
                "SEK", "SGD", "SKK", "SVC", "SYP", "THB", "TND",
                "TRY", "TWD", "UAH", "USD", "UYU", "VEF", "VND",
                "YER", "ZAR"));

        final List<String> codeList2 = new ArrayList<String>(Arrays.asList("AED", "ALL", "ARS", "AUD", "BAM", "BGN",
                "BHD", "BOB", "BTC", "BRL", "BYR", "CAD", "CHF", "CLP",
                "CNY", "COP", "CRC", "CSD", "CZK", "DKK", "DOP",
                "DZD", "EEK", "EGP", "EUR", "GBP", "GTQ", "HKD",
                "HNL", "HRK", "HUF", "IDR", "ILS", "INR", "IQD",
                "ISK", "JOD", "JPY", "KRW", "KWD", "LBP", "LTL",
                "LVL", "LYD", "MAD", "MKD", "MXN", "MYR", "NIO",
                "NOK", "NZD", "OMR", "PAB", "PEN", "PHP", "PLN",
                "PYG", "QAR", "RON", "RSD", "RUB", "SAR", "SDG",
                "SEK", "SGD", "SKK", "SVC", "SYP", "THB", "TND",
                "TRY", "TWD", "UAH", "USD", "UYU", "VEF", "VND",
                "YER", "ZAR"));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_spinner_item, codeList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_spinner_item, codeList2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        assert spinner1 != null;
        spinner1.setAdapter(dataAdapter);
        spinner1.setSelection(0);
        assert spinner2 != null;
        spinner2.setAdapter(dataAdapter2);
        spinner2.setSelection(dataAdapter2.getPosition("PLN"));

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                code1 = item;
                firstCodeFrom = code1;
//                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 0);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                code2 = item;
                firstCodeTo = code2;
//                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 0);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Switch onOffSwitch = (Switch) findViewById(R.id.switch1);
        onOffSwitch.setChecked(false);
        assert onOffSwitch != null;
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", "" + isChecked);
                switchStatus = isChecked;

                if (switchStatus == false) {
                    seekBar.setVisibility(View.GONE);
                    textViewSeek.setVisibility(View.GONE);

                    handler.removeCallbacks(runnable);
                    repeatTime = 0;
                    repeatTask = false;
                }
                if (switchStatus == true){
                    seekBar.setVisibility(View.VISIBLE);
                    textViewSeek.setVisibility(View.VISIBLE);
                    repeatTime = 5000;
                    repeatTask = true;
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, 0);
                }

            }
        });


        Button button = (Button) findViewById(R.id.buttonCurrency);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    String fcode1 = code1;
//                    String fcode2 = code2;
                    firstCodeFrom = code1;
                    firstCodeTo = code2;
                    handler.removeCallbacks(runnable);
                    handler.postDelayed(runnable, 1);
//                    handler.removeCallbacks(runnable);
//                    handler.postDelayed(runnable, 1);
                }
            });
        }


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String, String> map = (HashMap<String, String>) lv.getItemAtPosition(position);
                String code = map.get("code");
                String codeto = map.get("codeto");
                String mid = map.get("mid");
                String amount = map.get("amount");
//                Toast.makeText(MainActivity.this, "CLIKCED AT: " + code, Toast.LENGTH_LONG).show();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
                String timeOnShow = sdf.format(new Date());
                Intent intent = new Intent(getApplicationContext(), CurrencyDetails.class);
                intent.putExtra("code", code);
                intent.putExtra("codeto", codeto);
                intent.putExtra("mid", mid);
                intent.putExtra("amount", amount);
                intent.putExtra("time", timeOnShow);
                startActivity(intent);

            }
        });




    }

    private class MyTask extends AsyncTask<String, String, ArrayList<HashMap<String, String>>> {

        ListView lv;
        String CODEfrom;
        String CODEto;
        String amount;
        boolean repeatTask;
        private ProgressDialog dialog;

        public MyTask(ListView lv, String CODEfrom, String CODEto, boolean repeatTask, String amount) {

            this.lv = lv;
            this.CODEto = CODEto;
            this.CODEfrom = CODEfrom;
            this.repeatTask = repeatTask;
            this.amount = amount;
        }

        double mid = 0;

        @Override
        protected void onPreExecute() {

            if (!this.repeatTask) {
                this.dialog = ProgressDialog.show(Main2Activity.this, null, null);
                this.dialog.setMessage("Loading...");
                this.dialog.show();
            }

        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... params) {


            String CODEfrom = this.CODEfrom;
            String CODEto = this.CODEto;
            String amount = this.amount;
            HttpHandler sh = new HttpHandler();

            ArrayList<HashMap<String, String>> codeValue;
            codeValue = new ArrayList<>();
            //////////////////////////
            List<String> codeList = new ArrayList<String>();
            ////////////////////////

            //////////////////////////
//            List<String> codeListB = new ArrayList<String>(Arrays.asList("AED", "ALL", "ARS", "AUD", "BAM", "BGN",
//                    "BHD", "BOB", "BTC", "BRL", "BYR", "CAD", "CHF", "CLP",
//                    "CNY", "COP", "CRC", "CSD", "CZK", "DKK", "DOP",
//                    "DZD", "EEK", "EGP", "EUR", "GBP", "GTQ", "HKD",
//                    "HNL", "HRK", "HUF", "IDR", "ILS", "INR", "IQD",
//                    "ISK", "JOD", "JPY", "KRW", "KWD", "LBP", "LTL",
//                    "LVL", "LYD", "MAD", "MKD", "MXN", "MYR", "NIO",
//                    "NOK", "NZD", "OMR", "PAB", "PEN", "PHP", "PLN",
//                    "PYG", "QAR", "RON", "RSD", "RUB", "SAR", "SDG",
//                    "SEK", "SGD", "SKK", "SVC", "SYP", "THB", "TND",
//                    "TRY", "TWD", "UAH", "USD", "UYU", "VEF", "VND",
//                    "YER", "ZAR"));
            ////////////////////////
//            final List<String> codeListPom = new ArrayList<String>();
            ///////////////////
            ArrayList<Object> myObject = new ArrayList<Object>();

            if (CODEfrom != "***" && CODEfrom!="*") {
                codeList.clear();
                codeList.add(CODEfrom);
            }
            else if (CODEfrom == "*") {
                codeList = Arrays.asList("USD", "EUR", "GBP", "NOK", "CHF", "SEK", "RUB", "UAH", "CZK", "CAD", "JPY", "CNY", "BTC");
            }else{
                codeList = Arrays.asList("AED", "ALL", "ARS", "AUD", "BAM", "BGN",
                        "BHD", "BOB", "BTC", "BRL", "BYR", "CAD", "CHF", "CLP",
                        "CNY", "COP", "CRC", "CSD", "CZK", "DKK", "DOP",
                        "DZD", "EEK", "EGP", "EUR", "GBP", "GTQ", "HKD",
                        "HNL", "HRK", "HUF", "IDR", "ILS", "INR", "IQD",
                        "ISK", "JOD", "JPY", "KRW", "KWD", "LBP", "LTL",
                        "LVL", "LYD", "MAD", "MKD", "MXN", "MYR", "NIO",
                        "NOK", "NZD", "OMR", "PAB", "PEN", "PHP", "PLN",
                        "PYG", "QAR", "RON", "RSD", "RUB", "SAR", "SDG",
                        "SEK", "SGD", "SKK", "SVC", "SYP", "THB", "TND",
                        "TRY", "TWD", "UAH", "USD", "UYU", "VEF", "VND",
                        "YER", "ZAR");
            }


            String title = "";
            /////////////////////////
            for (String object : codeList) {

                String url_parse = "https://www.google.com/finance/converter?a=" + amount + "&from=" + object + "&to=" + CODEto;
                String wynik = "";
                System.out.println(object);
                String url_json = "http://query.yahooapis.com/v1/public/yql?q=select%20rate%2Cname%20from%20csv%20where%20url%3D%27http%3A%2F%2Fdownload.finance.yahoo.com%2Fd%2Fquotes%3Fs%3D" + object + CODEto + "%253DX%26f%3Dl1n%27%20and%20columns%3D%27rate%2Cname%27&format=json";

                String jsonStr = sh.makeServiceCall(url_json);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        JSONObject reader = jsonObj.getJSONObject("query");
                        for (int i = 0; i < reader.length(); i++) {
                            JSONObject c = reader.getJSONObject("results");
                            for (int j = 0; j < c.length(); j++) {
                                JSONObject b = c.getJSONObject("row");
                                wynik = b.getString("rate");
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                LinkedHashMap<String, String> eachCodeValue = new LinkedHashMap<>();
                eachCodeValue.put("VALUE", wynik);
                eachCodeValue.put("CODE", object);
                eachCodeValue.put("CODE_TO", CODEto);
                eachCodeValue.put("AMOUNT", amount);
                codeValue.add(eachCodeValue);
            }
            return codeValue;

//            String url = "https://www.google.com/finance/converter?a=" + amount + "&from=" + object + "&to=" + CODEto;
//
//
//            try {
//                String wynik = "";
//                String url_json = "http://query.yahooapis.com/v1/public/yql?q=select%20rate%2Cname%20from%20csv%20where%20url%3D%27http%3A%2F%2Fdownload.finance.yahoo.com%2Fd%2Fquotes%3Fs%3D" + object + CODEto + "%253DX%26f%3Dl1n%27%20and%20columns%3D%27rate%2Cname%27&format=json";
//
//
//                String jsonStr = sh.makeServiceCall(url_json);
//                if (jsonStr != null) {
//
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//                    JSONObject reader = jsonObj.getJSONObject("query");
//                    for (int i = 0; i < reader.length(); i++) {
//                        JSONObject c = reader.getJSONObject("results");
//                        for (int j = 0; j < c.length(); j++) {
//                            JSONObject b = c.getJSONObject("row");
//                            wynik = b.getString("rate");
//                        }
//                    }
//                }
//                ///////////////////////////////////////////////
//                Connection connect = Jsoup.connect(url);
//                Document document = connect.get();
//                Elements allH1 = document.select("span.bld");
//                for (Element elem : allH1) {
//                    LinkedHashMap<String, String> eachCodeValue = new LinkedHashMap<>();
//                    //System.out.println(elem.text());
//                    title = title + elem.text();
//                    ////categories.add(title);//////////////////////////////////////
//                    String result = elem.text();
//                    //System.out.println("\nURL_RESULT:" + result);
//                    int index;
//                    int positions;
//                    if (result.contains(".")) {
//                        index = result.indexOf(".");
//                        positions = 5;
//                    } else {
//                        index = result.indexOf(" ");
//                        positions = 0;
//                    }
//
//                    String mm = result.substring(0, index + positions);
//                    // System.out.println("\nCUT_STRING:" + mm);
//                    //String mm = result.split("\\.", 2)[0];
//                    // mid = mid+Double.parseDouble(elem.text());
//                    mid = Double.parseDouble(mm);
//                    // System.out.println("KURS"+mm+"\nMID: "+mid);
//                    // System.out.println("\nREZULTAT:" + result);
//                    eachCodeValue.put("VALUE", wynik);
//                    eachCodeValue.put("CODE", object);
//                    eachCodeValue.put("CODE_TO", CODEto);
//                    eachCodeValue.put("AMOUNT", amount);
//                    codeValue.add(eachCodeValue);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        return codeValue;

        }

        protected void onProgressUpdate(String... progress) {
            // Set progress percentage
        }

        @Override
        protected void onPostExecute(ArrayList<HashMap<String, String>> result) {

            if (this.dialog != null) {
                this.dialog.dismiss();
            }
            ListView newlv = this.lv;
            ArrayList<HashMap<String, Object>> contactList3;
            contactList3 = new ArrayList<>();


            // System.out.println("\nHASHMAP" + result);
            //  int i =0;
            for (HashMap<String, String> map : result) {
                LinkedHashMap<String, Object> contact2 = new LinkedHashMap<>();
                // contact2.put("currency", map.toString());
                String img_code = (map.get("CODE"));
                String img_code_to = (map.get("CODE_TO"));

                String fine_code = img_code.toLowerCase();
                String fine_code_to = img_code_to.toLowerCase();

                if (fine_code == "try" || fine_code_to == "try") {
                    fine_code = "mytry";
                    fine_code_to = "mytry";
                }
                int resID = getResources().getIdentifier(fine_code, "drawable", getPackageName());
                int resID2 = getResources().getIdentifier(fine_code_to, "drawable", getPackageName());
                contact2.put("code", map.get("CODE"));
                contact2.put("mid", map.get("VALUE"));
                contact2.put("img", resID);
                contact2.put("img2", resID2);
                contact2.put("codeto", map.get("CODE_TO"));
                contact2.put("amount", map.get("AMOUNT"));

                //String src = "http://www.xe.com/themes/xe/images/flags/big/" + fine_code + ".png";
                // System.out.println("***********SRC " + map.get("CODE"));

//
//                if ((i % 2) == 1) {
//
//                } else {
//
//                }

                contactList3.add(contact2);


                // i++;

            }
            ListAdapter adapter = new SimpleAdapter(Main2Activity.this, contactList3,
                    R.layout.list_item_live, new String[]{"code", "mid", "img", "img2", "codeto", "amount"},
                    new int[]{R.id.textViewTo, R.id.textViewCurrency, R.id.imageViewStatus, R.id.imageViewBase, R.id.textViewFrom, R.id.textViewAmount});
            newlv.setAdapter(adapter);


        }

    }

}