package com.barisdeneme.contactbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME,null,Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);
        onCreate(db);
    }

    //kişi kaydetme metodu
    public long savePerson(String personname, String personsurname, String personbirthday, String personemail, String personphone, String personnote){

            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constants.columnName, personname);
            values.put(Constants.columnSurname, personsurname);
            values.put(Constants.columnBirthday, personbirthday);
            values.put(Constants.columnEmail, personemail);
            values.put(Constants.columnPhone, personphone);
            values.put(Constants.columnNote, personnote);

            long id = database.insert(Constants.TABLE_NAME, null, values);
            database.close();
            return id;


    }

    //kişi bilgileri güncelleniyor
    public void updatePerson(String id, String personname, String personsurname, String personbirthday, String personemail, String personphone, String personnote){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.columnName, personname);
        values.put(Constants.columnSurname, personsurname);
        values.put(Constants.columnBirthday, personbirthday);
        values.put(Constants.columnEmail, personemail);
        values.put(Constants.columnPhone, personphone);
        values.put(Constants.columnNote, personnote);

        database.update(Constants.TABLE_NAME,values, Constants.columnId + "=?", new String[]{id});
        database.close();


    }



    public ArrayList<Model> getAllData(String orderBy){
        ArrayList<Model> arrayList = new ArrayList<>();

        //veritabanından tüm bilgiler çekiliyor
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        //veriler çekildikten sonra sütunlardan veriler alınıyor
        if (cursor.moveToNext()){
            do {
                //ilk verinin çekilmesi için do kullanıldı, sonra cursor ilerliyor
                Model model = new Model(
                        ""+cursor.getInt(cursor.getColumnIndex(Constants.columnId)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.columnName)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.columnSurname)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.columnBirthday)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.columnEmail)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.columnPhone)),
                        ""+cursor.getString(cursor.getColumnIndex(Constants.columnNote))
                );

                arrayList.add(model);
            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return arrayList;
    }

    //kişi bilgilerini silmek için kullanılan metot
    public void deletePerson(String id){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(Constants.TABLE_NAME,Constants.columnId + " =? ", new String[]{id});
        database.close();
    }
}
