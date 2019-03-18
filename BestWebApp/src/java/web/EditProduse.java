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

@ManagedBean(name = "editProduse")
@ViewScoped
public class EditProduse implements Serializable {

    private List<Produs> produse;
 
    private String denumireNoua;
    private String dataProdNoua;
    private String dataExpNoua;

    public List<Produs> getProduse() {
        return produse;
    }

    public void setProduse(List<Produs> produse) {
        this.produse = produse;
    }
    
    public String getDenumireNoua() {
        return denumireNoua;
    }

    public void setDenumireNoua(String denumireNoua) {
        this.denumireNoua = denumireNoua;
    }

    public String getDataProdNoua() {
        return dataProdNoua;
    }

    public void setDataProdNoua(String dataProdNoua) {
        this.dataProdNoua = dataProdNoua;
    }

    public String getDataExpNoua() {
        return dataExpNoua;
    }

    public void setDataExpNoua(String dataExpNoua) {
        this.dataExpNoua = dataExpNoua;
    }
 
    @PostConstruct
    public void init() {
        produse = getValues();
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            PreparedStatement pst;
            String sql = "update produsAlimentar set Denumire=?, DataProducere=?, DataExpirare=? where ProdusID=?;"; 
            Connection conn = DBConnect.getConnection();
            
            pst = conn.prepareStatement(sql);
            Produs produs = (Produs)event.getObject();
            pst.setString(1, produs.getDenumire());
            pst.setString(2, produs.getDataProducere());
            pst.setString(3, produs.getDataExpirare());
            pst.setInt(4, produs.getId());
            
            pst.execute();
            conn.close();
            
            FacesMessage msg = new FacesMessage("Produs editat", (((Produs) event.getObject()).getId()).toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (SQLException ex) {
            Logger.getLogger(EditClienti.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage msg = new FacesMessage("Editare esuata", (((Produs) event.getObject()).getId()).toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     *
     * @param event
     */
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Editare anulata", (((Produs) event.getObject()).getId()).toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Produs> getValues() {
        List<Produs> prd = new ArrayList<Produs>();
        try {
            String queryString = "select * from produsAlimentar;";
            ResultSet result = null;
            Connection conn = DBConnect.getConnection();
            Statement stmt;
            
            stmt = conn.createStatement();
            result = stmt.executeQuery(queryString);         
            
            while(result.next()){

               Produs produs = new Produs();

               produs.setId(result.getInt(1));
               produs.setDenumire(result.getString(2));
               produs.setDataProducere(result.getString(3).split(" ")[0]);
               produs.setDataExpirare(result.getString(4).split(" ")[0]);             

               prd.add(produs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditClienti.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        return prd;
    }
    
    public void addProdus() {
        try {
            Produs prd = new Produs();
            prd.setDenumire(denumireNoua);
            prd.setDataProducere(dataProdNoua);
            prd.setDataExpirare(dataExpNoua);
            
            PreparedStatement pst;
            String sql = "insert into produsAlimentar(Denumire, DataProducere, DataExpirare) values(?, ?, ?);";
            Connection conn = DBConnect.getConnection();
                       
            pst = conn.prepareStatement(sql);
            Produs produs = prd;
            pst.setString(1, produs.getDenumire());
            pst.setString(2, produs.getDataProducere());
            pst.setString(3, produs.getDataExpirare());
                    
            pst.execute();
            if (produse.size() > 0) {
                prd.setId(produse.get(produse.size() - 1).getId() + 1);
            } else {
                prd.setId(0);
            }
            
            produse.add(prd);
        } catch (SQLException ex) {
            Logger.getLogger(EditClienti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String deleteProdus(Produs client) {
        try {
            String sql = "delete from produsAlimentar where produsID=?;";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pst;
            pst = conn.prepareStatement(sql);
            pst.setInt(1, client.getId());
            pst.execute();
            conn.close();            
        } catch (SQLException ex) {
            Logger.getLogger(EditClienti.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "produse.xhtml?faces-redirect=true";
    }

}
