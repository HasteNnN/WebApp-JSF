package web;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

@ManagedBean
public class MenuView {
 
    private MenuModel model;
 
    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();
    }
 
    public MenuModel getModel() {
        return model;
    }
 
    public String clienti() {
        return "clienti.xhtml?faces-redirect=true";
    }
 
    public String produse() {
        return "produse.xhtml?faces-redirect=true";
    }
 
    public String producatori() {
        return "producatori.xhtml?faces-redirect=true";
    }
}