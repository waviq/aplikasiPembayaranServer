package com.mastahcode.pembayaran.dao;

import com.mastahcode.pembayaran.Entity.Produk;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by waviq on 3/09/2016.
 *
 * Produk Dao
 */
public interface ProdukDao extends PagingAndSortingRepository<Produk, String>{
}
