package com.barisdeneme.contactbook;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

public class KisiListesi extends AppCompatActivity {

    ImageView imageView;
    ActionBar actionBar;
    RecyclerView mrecyclerView;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();

        mrecyclerView = findViewById(R.id.recyclerView);
        databaseHelper = new DatabaseHelper(this);

        showRecord();

        imageView = findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KisiListesi.this,KisiEkle.class);
                intent.putExtra("editMode",false);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void showRecord(){
        Adapter adapter = new Adapter(KisiListesi.this,databaseHelper.getAllData(Constants.columnName +" ASC"));
        mrecyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showRecord();
    }
}