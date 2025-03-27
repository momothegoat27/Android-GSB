package com.example.pojetgsb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateBdEchantillon extends SQLiteOpenHelper {
    private static final String TABLE_ECHANT = "echantillons";
    static final String COL_ID = "_id";
    private static final String COL_CODE = "CODE";
    private static final String COL_LIB = "LIB";
    private static final String COL_STOCK = "STOCK";

    // ✅ Correction : Ajout du + et fermeture correcte de la chaîne de caractères
    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_ECHANT + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_CODE + " TEXT NOT NULL, "
            + COL_LIB + " TEXT NOT NULL, "
            + COL_STOCK + " TEXT NOT NULL);";

    public CreateBdEchantillon(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD); // ✅ Crée la table proprement
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ECHANT);
        onCreate(db);
    }
}
