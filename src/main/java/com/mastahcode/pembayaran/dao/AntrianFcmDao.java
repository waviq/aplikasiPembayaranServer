package com.mastahcode.pembayaran.dao;

import com.mastahcode.pembayaran.Entity.AntrianFcm;
import com.mastahcode.pembayaran.Entity.StatusAntrian;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by waviq on 3/09/2016.
 *
 */
public interface AntrianFcmDao extends PagingAndSortingRepository <AntrianFcm, String>{
    public Page<AntrianFcm> findByStatusOrderByWaktuKirimAsc(StatusAntrian statusAntrian, Pageable p);
}
