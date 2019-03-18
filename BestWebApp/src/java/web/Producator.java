/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.Serializable;

/**
 *
 * @author Theos
 */
public class Producator implements Serializable {
    private Integer id;
    private String denumire;
    private String tara;
    private String adresa;

    public Producator() {
    }

    public Producator(Integer id, String denumire, String tara, String adresa) {
        this.id = id;
        this.denumire = denumire;
        this.tara = tara;
        this.adresa = adresa;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
 
    
}
