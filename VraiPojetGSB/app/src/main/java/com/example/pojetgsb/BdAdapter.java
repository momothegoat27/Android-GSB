package com.example.pojetgsb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log; // ✅ Ajout des logs

public class BdAdapter {
    static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "gsb.db";
    private static final String TABLE_ECHANT = "echantillons";
    static final String COL_ID = "_id";
    static final int NUM_COL_ID = 0;
    static final String COL_CODE = "CODE";
    static final int NUM_COL_CODE = 1;
    static final String COL_LIB = "LIB";
    static final int NUM_COL_LIB = 2;
    static final String COL_STOCK = "STOCK";
    static final int NUM_COL_STOCK = 3;

    private CreateBdEchantillon bdArticles;
    private Context context;
    private SQLiteDatabase db;

    public BdAdapter(Context context) {
        this.context = context;
        bdArticles = new CreateBdEchantillon(context, NOM_BDD, null, VERSION_BDD);
    }

    // ✅ LOG : Vérifier si la base s'ouvre bien
    public BdAdapter open() {
        try {
            db = bdArticles.getWritableDatabase();
            Log.d("DB_OPEN", "Base de données ouverte avec succès.");
        } catch (Exception e) {
            Log.e("DB_OPEN", "Erreur lors de l'ouverture de la base : " + e.getMessage());
        }
        return this;

    }

    public BdAdapter close() {
        db.close();
        Log.d("DB_CLOSE", "Base de données fermée.");
        return null;
    }

    // ✅ LOG : Vérifier si l'insertion fonctionne bien
    public long insererEchantillon(Echantillon unEchant) {
        ContentValues values = new ContentValues();
        values.put(COL_CODE, unEchant.getCode());
        values.put(COL_LIB, unEchant.getLibelle());
        values.put(COL_STOCK, unEchant.getQuantiteStock());

        try {
            long result = db.insert(TABLE_ECHANT, null, values);
            if (result == -1) {
                Log.e("DB_INSERT", "Échec de l'insertion : Code=" + unEchant.getCode()
                        + ", Libellé=" + unEchant.getLibelle() + ", Stock=" + unEchant.getQuantiteStock());
            } else {
                Log.d("DB_INSERT", "Ajout réussi : Code=" + unEchant.getCode()
                        + ", Libellé=" + unEchant.getLibelle() + ", Stock=" + unEchant.getQuantiteStock());
            }
            return result;
        } catch (Exception e) {
            Log.e("DB_INSERT", "Erreur SQL lors de l'insertion : " + e.getMessage());
            return -1;
        }
    }

    private Echantillon cursorToEchant(Cursor c) {
        Echantillon unEchant = null;
        if (c.getCount() != 0) {
            c.moveToFirst();
            unEchant = new Echantillon();
            unEchant.setCode(c.getString(NUM_COL_CODE));
            unEchant.setLibelle(c.getString(NUM_COL_LIB));
            unEchant.setQuantiteStock(c.getString(NUM_COL_STOCK));
        }
        c.close();
        return unEchant;
    }

    public Echantillon getEchantillonWithLib(String unCode) {
        Cursor c = db.query(TABLE_ECHANT, new String[]{COL_ID, COL_CODE, COL_LIB, COL_STOCK},
                COL_CODE + " LIKE ?", new String[]{unCode}, null, null, null);
        return cursorToEchant(c);
    }

    public int updateEchantillon(String unCode, Echantillon unEchant) {
        ContentValues values = new ContentValues();
        values.put(COL_LIB, unEchant.getLibelle());
        values.put(COL_STOCK, unEchant.getQuantiteStock());

        int result = db.update(TABLE_ECHANT, values, COL_CODE + " = ?", new String[]{unCode});
        if (result > 0) {
            Log.d("DB_UPDATE", "Mise à jour réussie : Code=" + unCode);
        } else {
            Log.e("DB_UPDATE", "Échec de la mise à jour : Code=" + unCode);
        }
        return result;
    }

    public int removeEchantillonWithCode(String unCode) {
        int result = db.delete(TABLE_ECHANT, COL_CODE + " = ?", new String[]{unCode});
        if (result > 0) {
            Log.d("DB_DELETE", "Suppression réussie : Code=" + unCode);
        } else {
            Log.e("DB_DELETE", "Échec de la suppression : Code=" + unCode);
        }
        return result;
    }

    // ✅ LOG : Vérifier les données en base
    public Cursor getData() {
        Cursor cursor = db.rawQuery("SELECT * FROM echantillons", null);
        Log.d("DB_DATA", "Nombre d'échantillons dans la base : " + cursor.getCount());
        return cursor;
    }
}
