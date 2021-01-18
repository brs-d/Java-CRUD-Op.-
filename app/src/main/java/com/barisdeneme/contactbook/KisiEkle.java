package com.barisdeneme.contactbook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class KisiEkle extends AppCompatActivity {

    ImageView imageView2;
    EditText personNameText, personSurnameText,personBirthdayText,personEmailText,personPhoneText,personNoteText;
    Button btnSave;

    private String id,personname,personsurname,personbirthday,personemail,personphone,personnote;
    private boolean editMode = false;
    private DatabaseHelper dbHelper;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisi_ekle);

        imageView2 = findViewById(R.id.imageView2);
        personNameText = findViewById(R.id.personNameText);
        personSurnameText = findViewById(R.id.personSurnameText);
        personBirthdayText = findViewById(R.id.personBirthdayText);
        personEmailText = findViewById(R.id.personEmailText);
        personPhoneText = findViewById(R.id.personPhoneText);
        personNoteText = findViewById(R.id.personNoteText);
        btnSave = findViewById(R.id.btnSave);

        //veritabanı nesnesi initiate edildi
        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        editMode = intent.getBooleanExtra("editMode",editMode);
        id = intent.getStringExtra("ID");
        personname = intent.getStringExtra("NAME");
        personsurname = intent.getStringExtra("SURNAME");
        personbirthday = intent.getStringExtra("BİRTHDAY");
        personemail = intent.getStringExtra("EMAIL");
        personphone = intent.getStringExtra("PHONE");
        personnote = intent.getStringExtra("NOTE");

        if (editMode){

            editMode = intent.getBooleanExtra("editMode",editMode);
            id = intent.getStringExtra("ID");
            personname = intent.getStringExtra("NAME");
            personsurname = intent.getStringExtra("SURNAME");
            personbirthday = intent.getStringExtra("BİRTHDAY");
            personemail = intent.getStringExtra("EMAIL");
            personphone = intent.getStringExtra("PHONE");
            personnote = intent.getStringExtra("NOTE");

            personNameText.setText(personname);
            personSurnameText.setText(personsurname);
            personBirthdayText.setText(personbirthday);
            personEmailText.setText(personemail);
            personPhoneText.setText(personphone);
            personNoteText.setText(personnote);

        }

        //takvim işlemleri
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateBirthday();
            }
        };

        personBirthdayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(KisiEkle.this, date,myCalendar
                        .get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KisiEkle.this,KisiListesi.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePerson();
            }
        });
    }

    //seçilen tarih ilgili formatta alınıyor
    private void updateBirthday(){
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        personBirthdayText.setText(sdf.format(myCalendar.getTime()));
    }

    private void savePerson() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        personname = "" + personNameText.getText().toString().trim();
        personsurname = "" + personSurnameText.getText().toString().trim();
        personbirthday = "" + personBirthdayText.getText().toString().trim();
        personemail = "" + personEmailText.getText().toString().trim();
        personphone = "" + personPhoneText.getText().toString().trim();
        personnote = "" + personNoteText.getText().toString().trim();

        if (personname.equals("") || personsurname.equals("") || personbirthday.equals("") || personemail.equals("") || personphone.equals("")) {
            Toast.makeText(KisiEkle.this, "Lütfen tüm alanları doldurunuz", Toast.LENGTH_SHORT).show();
        } else {
            if (personname.length() < 2 || personname.length() > 20) {
                Toast.makeText(KisiEkle.this, "İsim 2 ile 20 karakter arasında olmak zorundadır", Toast.LENGTH_SHORT).show();
            } else {
                if (personsurname.length() < 2 || personsurname.length() > 20) {
                    Toast.makeText(KisiEkle.this, "Soyisim 2 ile 20 karakter arasında olmak zorundadır", Toast.LENGTH_SHORT).show();
                } else {
                    if (!(personemail.matches(emailPattern))){
                        Toast.makeText(this, "Lütfen geçerli bir eposta giriniz", Toast.LENGTH_SHORT).show();
                    }else{



                    if (editMode) {
                        String newUpdateTime = "" + System.currentTimeMillis();

                        dbHelper.updatePerson(
                                "" + id,
                                "" + personname,
                                "" + personsurname,
                                "" + personbirthday,
                                "" + personemail,
                                "" + personphone,
                                "" + personnote
                        );
                    } else {
                        dbHelper.savePerson(
                                "" + personname,
                                "" + personsurname,
                                "" + personbirthday,
                                "" + personemail,
                                "" + personphone,
                                "" + personnote
                        );
                    }

                    personNameText.setText("");
                    personSurnameText.setText("");
                    personBirthdayText.setText("");
                    personEmailText.setText("");
                    personPhoneText.setText("");
                    personNoteText.setText("");

                    Toast.makeText(getApplicationContext(), "Başarılı şekilde kaydedildi", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}