package com.mastahcode.pembayaran.Entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Created by Waviq on 01/09/2016.
 *
 */
@Entity @Table(name = "t_pembayaran")
public class Pembayaran extends BaseEntity {

    @NotNull
    @OneToOne
    @JoinColumn(name = "id_tagihan", nullable = false)
    private Tagihan tagihan;

    @NotNull
    @Column(name = "waktu_transaksi", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date waktuTransaksi = new Date();

    public Tagihan getTagihan() {
        return tagihan;
    }

    public void setTagihan(Tagihan tagihan) {
        this.tagihan = tagihan;
    }

    public Date getWaktuTransaksi() {
        return waktuTransaksi;
    }

    public void setWaktuTransaksi(Date waktuTransaksi) {
        this.waktuTransaksi = waktuTransaksi;
    }


}