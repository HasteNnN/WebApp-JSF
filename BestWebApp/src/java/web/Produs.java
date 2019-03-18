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
public class Produs implements Serializable {
    private Integer id;
    private String denumire;
    private String dataProducere;
    private String dataExpirare;

    public Produs() {
    }

    public Produs(Integer id, String denumire, String dataProducere, String dataExpirare) {
        this.id = id;
        this.denumire = denumire;
        this.dataProducere = dataProducere;
        this.dataExpirare = dataExpirare;
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

    public String getDataProducere() {
        return dataProducere;
    }

    public void setDataProducere(String dataProducere) {
        this.dataProducere = dataProducere;
    }

    public String getDataExpirare() {
        return dataExpirare;
    }

    public void setDataExpirare(String dataExpirare) {
        this.dataExpirare = dataExpirare;
    }
}
