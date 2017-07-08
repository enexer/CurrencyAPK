package com.example.as.waluty;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.AdapterView.OnItemSelectedListener;
import org.w3c.dom.Text;

public class Calculator extends AppCompatActivity {

    private EditText editTextFrom;
    private TextView textViewFrom;
    private TextView textViewTo;
    private TextView tv;
    public String code1 = "USD";
    public String code2 = "PLN";
    public int position1,position2;
    DBHelper mydb = new DBHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        editTextFrom = (EditText) findViewById(R.id.editTextFrom);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        editTextFrom.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    sendMessage();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                code1 = item;
                position1=position;
              //  Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                sendMessage();

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
                position2=position;
               // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                sendMessage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button button = (Button) findViewById(R.id.buttonSwitch);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  spinner.setSelection(position2);
                    spinner2.setSelection(position1);
                }
            });
        }


        List<String> categories = new ArrayList<String>();
        Cursor mCursor = mydb.getDistinctCode();
        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            String code = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_CODE));
            categories.add(code);
            mCursor.moveToNext();
        }
        categories.add("PLN");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter);


    }

    public void sendMessage() {

        tv = (TextView) findViewById(R.id.textViewResult);
        textViewFrom = (TextView) findViewById(R.id.textViewFrom);
        String fcode1 = code1;
        String fcode2 = code2;
        double from=0;
        if(editTextFrom.getText().toString()!="") {
            from = Double.parseDouble(editTextFrom.getText().toString());
        }
        if (from == 0) {
            from = 1;
        }
        List<String> midList1 = new ArrayList<String>();
        List<String> midList2 = new ArrayList<String>();

        if (fcode1 == "PLN") {
            midList1.add("1");
        } else {
            Cursor mCursor = mydb.getLastMidByCode(fcode1);
            mCursor.moveToFirst();
            while (!mCursor.isAfterLast()) {
                String mid = mCursor.getString(mCursor.getColumnIndex(DBHelper.CONTACTS_COLUMN_MID));
                midList1.add(mid);
                mCursor.moveToNext();
            }
        }
        if (fcode2 == "PLN") {
            midList2.add("1");
        } else {
            Cursor mCursor2 = mydb.getLastMidByCode(fcode2);
            mCursor2.moveToFirst();
            while (!mCursor2.isAfterLast()) {
                String mid = mCursor2.getString(mCursor2.getColumnIndex(DBHelper.CONTACTS_COLUMN_MID));
                midList2.add(mid);
                mCursor2.moveToNext();
            }
        }

        double midFrom = Double.parseDouble(midList1.get(0));
        double midTo = Double.parseDouble(midList2.get(0));

        double rate = (midFrom/midTo);
        String rateS = String.format("%1.4f", rate);
        double sum = from * rate;
        String wynik = String.format("%1.4f", sum);
        tv.setText(wynik);
        textViewFrom.setText(rateS);


    }
}
