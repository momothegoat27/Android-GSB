package com.example.pojetgsb;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListeEchantillon extends Activity {
    private BdAdapter bdAdapter;
    private ListView listViewEchantillons;
    private TextView textViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_echantillon);

        // Initialisation de la base de données
        bdAdapter = new BdAdapter(this);
        bdAdapter.open();

        // Récupération des éléments du layout
        listViewEchantillons = findViewById(R.id.listeListViewEchantillon);
        textViewMessage = findViewById(R.id.listeTextViewTitre);
        Button buttonQuitter = findViewById(R.id.listeButtonQuitter);

        // Gestion du bouton "Quitter"
        buttonQuitter.setOnClickListener(v -> finish());

        // Charger et afficher les échantillons
        afficherEchantillons();
    }

    private void afficherEchantillons() {
        Cursor cursor = bdAdapter.getData();
        ArrayList<String> listEchantillons = new ArrayList<>();

        if (cursor.getCount() == 0) {
            textViewMessage.setText("Aucun échantillon enregistré.");
        } else {
            while (cursor.moveToNext()) {
                String code = cursor.getString(1);
                String libelle = cursor.getString(2);
                String quantite = cursor.getString(3);
                listEchantillons.add("Code : " + code + " | Libellé : \" + libelle + \" |  Stock : " + quantite);
            }
        }
        cursor.close();

        // Remplir le ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listEchantillons);
        listViewEchantillons.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bdAdapter.close();
    }
}
