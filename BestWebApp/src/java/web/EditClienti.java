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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

@ManagedBean(name = "editClienti")
@ViewScoped
public class EditClienti implements Serializable {

    private List<Client> clienti;
    
    private String numeNou;
    private String prenumeNou;
    private String adresaNoua;
    
    private Client selectedClient;

    public Client getSelectedClient() {
        return selectedClient;
    }

    public void setSelectedClient(Client selectedClient) {
        this.selectedClient = selectedClient;
    }

    public String getNumeNou() {
        return numeNou;
    }

    public void setNumeNou(String numeNou) {
        this.numeNou = numeNou;
    }

    public String getPrenumeNou() {
        return prenumeNou;
    }

    public void setPrenumeNou(String prenumeNou) {
        this.prenumeNou = prenumeNou;
    }

    public String getAdresaNoua() {
        return adresaNoua;
    }

    public void setAdresaNoua(String adresaNoua) {
        this.adresaNoua = adresaNoua;
    }
    
    public List<Client> getClienti() {
        return clienti;
    }

    public void setClienti(List<Client> clienti) {
        this.clienti = clienti;
    }

    @PostConstruct
    public void init() {
        clienti = getValues();
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            PreparedStatement pst;
            String sql = "update clienti set nume=?, prenume=?, adresa=? where clientId=?;"; 
            Connection conn = DBConnect.getConnection();
            
            pst = conn.prepareStatement(sql);
            Client client = (Client)event.getObject();
            pst.setString(1, client.getNume());
            pst.setString(2, client.getPrenume());
            pst.setString(3, client.getAdresa());
            pst.setInt(4, client.getId());
            
            pst.execute();
            conn.close();
            
            FacesMessage msg = new FacesMessage("Client editat", (((Client) event.getObject()).getId()).toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (SQLException ex) {
            Logger.getLogger(EditClienti.class.getName()).log(Level.SEVERE, null, ex);
            FacesMessage msg = new FacesMessage("Editare esuata", (((Client) event.getObject()).getId()).toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    /**
     *
     * @param event
     */
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Editare anulata", (((Client) event.getObject()).getId()).toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public List<Client> getValues() {
        List<Client> cls = new ArrayList<Client>();
        try {
            String queryString = "select * from clienti;";
            ResultSet result = null;
            Connection conn = DBConnect.getConnection();
            Statement stmt;
            
            stmt = conn.createStatement();
            result = stmt.executeQuery(queryString);         
            
            while(result.next()){

               Client client = new Client();

               client.setId(result.getInt(1));
               client.setNume(result.getString(2));
               client.setPrenume(result.getString(3));
               client.setAdresa(result.getString(4));             

               cls.add(client);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditClienti.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        return cls;
    }
    
    public void addclient() {
        try {
            Client clAdd = new Client();
            clAdd.setNume(numeNou);
            clAdd.setPrenume(prenumeNou);
            clAdd.setAdresa(adresaNoua);
            
            PreparedStatement pst;
            String sql = "insert into clienti(nume, prenume, adresa) values(?, ?, ?);";
            Connection conn = DBConnect.getConnection();
                       
            pst = conn.prepareStatement(sql);
            Client client = clAdd;
            pst.setString(1, client.getNume());
            pst.setString(2, client.getPrenume());
            pst.setString(3, client.getAdresa());
             
            pst.execute();
            if (clienti.size() > 0)
                clAdd.setId(clienti.get(clienti.size() - 1).getId() + 1);
            else clAdd.setId(0);
            
            clienti.add(clAdd);
        } catch (SQLException ex) {
            Logger.getLogger(EditClienti.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String deleteClient(Client client) {
        try {
            String sql = "delete from clienti where clientId=?;";
            Connection conn = DBConnect.getConnection();
            PreparedStatement pst;
            pst = conn.prepareStatement(sql);
            pst.setInt(1, client.getId());
            pst.execute();
            conn.close();            
        } catch (SQLException ex) {
            Logger.getLogger(EditClienti.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "clienti.xhtml?faces-redirect=true";
    }
    
    public List<Produs> getProduse() {
        List<Produs> produse = new ArrayList<>();
       
        return null;
    }

}
