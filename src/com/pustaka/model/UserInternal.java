/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pustaka.model;

import com.pustaka.common.BaseAudit;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author dwi
 */
@Table(name="user_internal")
@Entity
public class UserInternal extends BaseAudit implements Serializable{
    
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_user")
    private int idUser;
    
    @Column(name="nama")
    private String nama;
    
    @Column(name="username")
    private String username;
    
    @Column(name="password")
    private String password;
    
    @Column(name="email")
    private String email;
    
    @Column(name="level")
    private String level;
    
    public UserInternal(){
        
    }
    
    public UserInternal(String nama, String username, String password, String email, String level){
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.email = email;
        this.level = level;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    
}
