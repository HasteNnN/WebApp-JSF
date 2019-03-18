package web;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class Login implements Serializable {
	
    private String pwd;
    private String msg;
    private String user;
    private Boolean admin;

    public String isadmin() {
        return admin?"true":"false";
    }

    public String getPwd() {
            return pwd;
    }

    public void setPwd(String pwd) {
            this.pwd = pwd;
    }

    public String getMsg() {
            return msg;
    }

    public void setMsg(String msg) {
            this.msg = msg;
    }

    public String getUser() {
            return user;
    }

    public void setUser(String user) {
            this.user = user;
    }

    //validate login
    public String validateUsernamePassword() {
            int priviledged = validateUser(user, pwd);
            if (priviledged != -1) {
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("username", user);
                if (priviledged == 1) {
                    session.setAttribute("admin", "true");
                    admin = true;
                } else {
                    session.setAttribute("admin", "false");
                    admin = false;
                }
                return "logged";
            } else {
                    FacesContext.getCurrentInstance().addMessage(
                                    null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    "Incorrect Username and Passowrd",
                                                    "Please enter correct username and Password"));
                    return "login";
            }
    }

    //logout event, invalidate session
    public String logout() {
            HttpSession session = SessionUtils.getSession();
            session.invalidate();
            return "login.xhtml?faces-redirect=true";
    }

    public void reload() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) { 

        }
    }

    // Compara parola pass cu parola din baza de date pt userul user
    // Daca userul este admin returneaza 1 altfel returneaza 0 pentru
    // user normal si -1 pt autentificare esuata
    private int validateUser(String user, String pass){
        String queryString = "select pass, priviledged from users where username = " 
                        + "'" + user + "';";
        ResultSet result = null;
        Connection conn = DBConnect.getConnection();
        Statement stmt;
        boolean priviledged = false;

        String dbPass = null;
        String givenPass = null;

        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(queryString);
            result.next();
            priviledged = result.getBoolean("priviledged");
            dbPass = result.getString("pass");
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            givenPass = bytesToHex(digest.digest(pass.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!dbPass.equals(givenPass)) {
            return -1;
        }
        if (priviledged) {
            return 1;
        }
        return 0;
    }

    private final static char[] hexArray = "0123456789abcdef".toCharArray();
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}