/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "editProducatori")
@ViewScoped
public class EditProducatori implements Serializable {

    private List<Producator> producatori;

    private String denumireNoua;
    private String taraNoua;
    private String adresaNoua;

    public List<Producator> getProducatori() {
        return producatori;
    }

    public void setProducatori(List<Producator> producatori) {
        this.producatori = producatori;
    }

    public String getDenumireNoua() {
        return denumireNoua;
    }

    public void setDenumireNoua(String denumireNoua) {
        this.denumireNoua = denumireNoua;
    }

    public String getTaraNoua() {
        return taraNoua;
    }

    public void setTaraNoua(String taraNoua) {
        this.taraNoua = taraNoua;
    }

    public String getAdresaNoua() {
        return adresaNoua;
    }

    public void setAdresaNoua(String adresaNoua) {
        this.adresaNoua = adresaNoua;
    }

    @PostConstruct
    public void init() {
        producatori = getValues();
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            PreparedStatement pst;
            String sql = "update producatori set Denumire=?, TaraOrigine=?, Adresa=? where ProducatorID=?;";
            Connection conn = DBConnect.getConnection();

            pst = conn.prepareStatement(sql);
            Producator producator = (Producator)event.getObject();
            pst.setString(1, producator.getDenumire());
            pst.setString(2, producator.getTara());
            pst.setString(3, producator.getAdresa());
            pst.setInt(4, producator.getId());

            pst.execute();
            conn.close();

            FacesMessage msg = new FacesMessage("Producator editat", (((Producator) event.getObject()).getId()).toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (SQLException ex) {
            Logger.getLogger(EditClienti.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage msg = new FacesMessage("Editare esuata", (((Producator) event.getObject()).getId()).toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     *
     * @param event
     */
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Editare anulata", (((Producator) event.getObject()).getId()).toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Producator> getValues() {
        List<Producator> prd = new ArrayList<Producator>();
        try {
            String queryString = "select * from producatori;";
            ResultSet result = null;
            Connection conn = DBConnect.getConnection();
            Statement stmt;

            stmt = conn.createStatement();
            result = stmt.executeQuery(queryString);

            while (result.next()) {

                Producator producator = new Producator();

                producator.setId(result.getInt(1));
                producator.setDenumire(result.getString(2));
                producator.setTara(result.getString(3));
                producator.setAdresa(result.getString(4));

                prd.add(producator);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditClienti.class.getName()).log(Level.SEVERE, null, ex);
        }

        return prd;
    }

    public void addProducator() {
        try {
            Producator prd = new Producator();
            prd.setDenumire(denumireNoua);
            prd.setTara(taraNoua);
            prd.setAdresa(adresaNoua);

            PreparedStatement pst;
            String sql = "insert into producatori(Denumire, TaraOrigine, Adresa) values(?, ?, ?);";
            Connection conn = DBConnect.getConnection();

            pst = conn.prepareStatement(sql);
            Producator producator = prd;
            pst.setString(1, producator.getDenumire());
            pst.setString(2, producator.getTara());
            pst.setString(3, producator.getAdresa());

            pst.execute();
            if (producatori.size() > 0) {
                prd.setId(producatori.get(producatori.size() - 1).getId() + 1);
            } else {
                prd.setId(0);
            }

            producatori.add(prd);
        } catch (SQLException ex) {
            Logger.getLogger(EditClienti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String deleteProducator(Producator producator) {
        try {
            String sql = "delete from producatori where ProducatorID=?;";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pst;
            pst = conn.prepareStatement(sql);
            pst.setInt(1, producator.getId());
            pst.execute();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditClienti.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "producatori.xhtml?faces-redirect=true";
    }
}
