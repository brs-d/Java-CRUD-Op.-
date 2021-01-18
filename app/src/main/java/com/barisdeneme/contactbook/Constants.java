package com.barisdeneme.contactbook;

public class Constants {

    //veritabanı ismi
    public static final String DB_NAME = "Contacts";

    //veritabanı versiyonu
    public static final int DB_VERSION = 1;

    //tablo adı
    public static final String TABLE_NAME = "persons";

    //tablo sütunları
    public static final String columnId = "id";
    public static final String columnName = "name";
    public static final String columnSurname = "surname";
    public static final String columnBirthday = "birthday";
    public static final String columnEmail = "email";
    public static final String columnPhone = "phone";
    public static final String columnNote = "note";

    //tablo oluşturmak için sorgu
        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +"(" + columnId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + columnName+" TEXT,"
            + columnSurname+" TEXT,"
            + columnBirthday+" TEXT,"
            + columnEmail+" TEXT,"
            + columnPhone+" TEXT,"
            + columnNote+" TEXT"
            + ");";
}
