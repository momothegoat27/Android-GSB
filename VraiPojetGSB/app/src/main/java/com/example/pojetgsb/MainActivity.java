package com.example.pojetgsb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private BdAdapter bdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Initialisation de la base de données
        bdAdapter = new BdAdapter(this);
        bdAdapter.open();
        jeuEssaiBd(); // Ajout de données de test

        // Gestion des marges pour l'affichage plein écran
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Récupération du bouton "Ajouter un nouvel échantillon"
        Button buttonAjout = findViewById(R.id.menuButtonAjoutEchantillon);
        buttonAjout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AjoutEchantillon.class);
            startActivity(intent);
        });

        // Bouton "Liste des échantillons"
        Button buttonListe = findViewById(R.id.menuButtonListeEchantillon);
        buttonListe.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListeEchantillon.class);
            startActivity(intent);
        });

        // Bouton "Maj d'un échantillon"
        Button buttonMaj = findViewById(R.id.menuButtonMajEchantillon);
        buttonMaj.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MajEchantillon.class);
            startActivity(intent);
        });
    }

    private void jeuEssaiBd() {
        bdAdapter.insererEchantillon(new Echantillon("code1", "Doliprane", "3"));
        bdAdapter.insererEchantillon(new Echantillon("code2", "Efferalgan", "5"));
        bdAdapter.insererEchantillon(new Echantillon("code3", "Spasfon", "7"));

        Cursor unCurseur = bdAdapter.getData();
        System.out.println("Il y a " + unCurseur.getCount() + " échantillons dans la BD");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bdAdapter.close(); // Fermeture de la base de données
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.quitter) {
            finish();
            return true;
        } else if (itemId == R.id.ajout) {
            Toast.makeText(getApplicationContext(), "Ouverture fenêtre Ajout !", Toast.LENGTH_LONG).show();
            Intent intentAjout = new Intent(this, AjoutEchantillon.class);
            startActivity(intentAjout);
            return true;
        } else if (itemId == R.id.liste) {
            Toast.makeText(getApplicationContext(), "Ouverture fenêtre Liste !", Toast.LENGTH_LONG).show();
            Intent intentListe = new Intent(this, ListeEchantillon.class);
            startActivity(intentListe);
            return true;
        } else if (itemId == R.id.maj) {
            Toast.makeText(getApplicationContext(), "Ouverture fenêtre Maj !", Toast.LENGTH_LONG).show();
            Intent intentMaj = new Intent(this, MajEchantillon.class);
            startActivity(intentMaj);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}