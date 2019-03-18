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
public class Client implements Serializable {
    private Integer id;
    private String nume;
    private String prenume;
    private String adresa;

    public Client(Integer id, String nume, String prenume, String adresa) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
    }

    public Client() {
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    } 
}
