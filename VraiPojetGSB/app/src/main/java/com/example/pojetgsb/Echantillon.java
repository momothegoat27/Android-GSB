package com.example.pojetgsb;

public class Echantillon {
    protected String code;
    protected String libelle;
    protected String quantiteStock;
    public Echantillon(String code, String libelle, String qteStock) {
        this.code = code;
        this.libelle = libelle;
        this.quantiteStock = qteStock;
    }
    public Echantillon() {
        this.code = null;
        this.libelle = null;
        this.quantiteStock = null;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    public void setQuantiteStock(String quantiteStock) {
        this.quantiteStock = quantiteStock;
    }
    public String getCode() {
        return code;
    }
    public String getLibelle() {
        return libelle;
    }
    public String getQuantiteStock() {
        return quantiteStock;
    }
}
