package com.mastahcode.pembayaran.Entity;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by Waviq on 01/09/2016.
 *
 * Tabel User
 */

@Entity
@Table(name = "m_user")
public class User extends BaseEntity{

    @NotNull
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull @NotEmpty
    @Column(nullable = false)
    private String fullname;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
