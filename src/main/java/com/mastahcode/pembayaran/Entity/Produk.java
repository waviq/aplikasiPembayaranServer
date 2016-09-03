package com.mastahcode.pembayaran.Entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Waviq on 01/09/2016.
 *
 */
@Entity @Table(name = "m_produk")
public class Produk extends BaseEntity {

    @NotNull @NotEmpty
    @Column(nullable = false, unique = true)
    private String kode;

    @NotNull @NotEmpty
    @Column(nullable = false, unique = true)
    private String nama;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }


}