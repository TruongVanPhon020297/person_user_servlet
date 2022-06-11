package com.codegym.model;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class User implements java.io.Serializable{
    protected int id;
    protected String name;
    protected String email;
    protected int id_country;
    public User(){

    }

    public User(String name, String email, int id_country) {
        this.name = name;
        this.email = email;
        this.id_country = id_country;
    }

    public User(int id, String name, String email, int id_country) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.id_country = id_country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @NotEmpty(message = "TÊN KHÔNG ĐƯỢC ĐỂ TRỐNG ")
    @Length(min = 3, max = 100 ,message = "TÊN LỚN HƠN 3 KÝ TỰ VÀ NHỎ HƠN 100 KÝ TỰ")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = "EMAIL KHÔNG ĐƯỢC ĐỂ TRỐNG")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",message = "TÊN EMAIL KHÔNG HỢP LỆ")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_country() {
        return id_country;
    }

    public void setId_country(int id_country) {
        this.id_country = id_country;
    }
}
