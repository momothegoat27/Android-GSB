package com.example.pojetgsb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AjoutEchantillon extends Activity {
    private BdAdapter bdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_echantillon);

        // Initialisation de la base de données
        bdAdapter = new BdAdapter(this);
        bdAdapter.open();

        // Récupération des éléments du layout
        EditText editTextCode = findViewById(R.id.ajoutEditTextCode);
        EditText editTextLib = findViewById(R.id.ajoutEditTextLib);
        EditText editTextStock = findViewById(R.id.ajoutEditTextStock);
        Button btnAjouter = findViewById(R.id.ajoutButtonAjouter);
        Button btnQuitter = findViewById(R.id.ajoutButtonQuitter);
        TextView textViewMessage = findViewById(R.id.ajoutTextViewMessage);

        // Bouton "Ajouter"
        btnAjouter.setOnClickListener(v -> {
            String code = editTextCode.getText().toString().trim();
            String libelle = editTextLib.getText().toString().trim();
            String stockStr = editTextStock.getText().toString().trim();

            if (code.isEmpty() || libelle.isEmpty() || stockStr.isEmpty()) {
                textViewMessage.setText("Veuillez remplir tous les champs !");
                return;
            }

            int stock;
            try {
                stock = Integer.parseInt(stockStr);
            } catch (NumberFormatException e) {
                textViewMessage.setText("Veuillez entrer un nombre valide !");
                return;
            }

            long inserted = bdAdapter.insererEchantillon(new Echantillon(code, libelle, stockStr));
            if (inserted != -1) {
                textViewMessage.setText("Échantillon ajouté !");
                editTextCode.setText("");
                editTextLib.setText("");
                editTextStock.setText("");
            } else {
                textViewMessage.setText("Erreur lors de l'ajout.");
            }
        });

        // Bouton "Quitter"
        btnQuitter.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bdAdapter.close(); // Ferme la base proprement
    }
}
