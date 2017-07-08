package com.example.as.waluty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


    }
    public void openCalculator(View view) {
        Intent intent = new Intent(getApplicationContext(),Calculator.class);
        // quest tabela z MATMA
        //intent.putExtra("kategoria", "quest");
        startActivity(intent);
    }
    public void openNbp(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        // quest tabela z MATMA
        //intent.putExtra("kategoria", "quest");
        startActivity(intent);
    }
    public void openLive(View view) {
        Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
        // quest tabela z MATMA
        //intent.putExtra("kategoria", "quest");
        startActivity(intent);
    }
    public void openFavoriteF(View view) {
        Intent intent = new Intent(getApplicationContext(),Favorite.class);
        // quest tabela z MATMA
        //intent.putExtra("kategoria", "quest");
        startActivity(intent);
    }
    public void openMaps(View view) {
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        // quest tabela z MATMA
        //intent.putExtra("kategoria", "quest");
        startActivity(intent);
    }
    public void openAbout(View view) {
        Intent intent = new Intent(getApplicationContext(),Calculator.class);
        // quest tabela z MATMA
        //intent.putExtra("kategoria", "quest");
        startActivity(intent);
    }
}
