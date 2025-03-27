package com.example.pojetgsb;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MajEchantillon extends Activity {
    private BdAdapter bdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maj_echantillon);

        // Initialisation de la base de données
        bdAdapter = new BdAdapter(this);
        bdAdapter.open();

        // Récupération des éléments du layout
        EditText editTextCode = findViewById(R.id.majEditTextCode);
        EditText editTextQte = findViewById(R.id.majEditTextQte);
        Button btnModifier = findViewById(R.id.majButtonModifier);
        Button btnSupprimer = findViewById(R.id.majButtonSupprimer);
        Button btnQuitter = findViewById(R.id.majButtonQuitter);
        TextView textViewMessage = findViewById(R.id.majTextViewMessage);

        // Bouton "Modifier"
        btnModifier.setOnClickListener(v -> {
            String code = editTextCode.getText().toString().trim();
            String quantiteStr = editTextQte.getText().toString().trim();

            if (code.isEmpty() || quantiteStr.isEmpty()) {
                textViewMessage.setText("Veuillez remplir tous les champs !");
                return;
            }

            Echantillon echantillon = new Echantillon(code, "", quantiteStr);
            int updated = bdAdapter.updateEchantillon(code, echantillon);
            if (updated > 0) {
                textViewMessage.setText("Quantité mise à jour !");
                editTextCode.setText("");
                editTextQte.setText("");
            } else {
                textViewMessage.setText("Échantillon introuvable !");
            }
        });

        // Bouton "Supprimer"
        btnSupprimer.setOnClickListener(v -> {
            String code = editTextCode.getText().toString().trim();

            if (code.isEmpty()) {
                textViewMessage.setText("Veuillez entrer un code !");
                return;
            }

            int deleted = bdAdapter.removeEchantillonWithCode(code);
            if (deleted > 0) {
                textViewMessage.setText("Échantillon supprimé !");
                editTextCode.setText("");
                editTextQte.setText("");
            } else {
                textViewMessage.setText("Échantillon introuvable !");
            }
        });

        // Bouton "Quitter"
        btnQuitter.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bdAdapter.close();
    }
}
