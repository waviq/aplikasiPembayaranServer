package com.mastahcode.pembayaran.services;

import com.mastahcode.pembayaran.Entity.Produk;
import com.mastahcode.pembayaran.Entity.User;
import com.mastahcode.pembayaran.Entity.UserFcmToken;
import com.mastahcode.pembayaran.dao.ProdukDao;
import com.mastahcode.pembayaran.dao.UserDao;
import com.mastahcode.pembayaran.dao.UserFcmTokenDao;
import com.mastahcode.pembayaran.exception.PendaftaranFcmTopicGagalException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by waviq on 3/09/2016.
 *
 */

@Service @Transactional
public class PembayaranService {

    @Autowired private ProdukDao produkDao;
    @Autowired private FcmService fcmService;
    @Autowired private UserDao userDao;
    @Autowired private UserFcmTokenDao userFcmTokenDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(PembayaranService.class);

    public void simpan(Produk p){
        produkDao.save(p);

        //Bikin Datanya
        Map <String, Object> data = new HashMap<String, Object>();
        data.put("action", "update");
        data.put("content", "produk");
        fcmService.kirimGcmMessage("/topics/produk", data);

    }

    public Page<Produk> semuaProduk(Pageable page){
        return  produkDao.findAll(page);
    }

    public void updateToken(String email, String token) {

        try {

            fcmService.daftarkanTokenKeTopic(token, "produk");

            User u = userDao.findByEmail(email);
            if(u != null){
                UserFcmToken userToken = new UserFcmToken();
                userToken.setUser(u);
                userToken.setFcmToken(token);
                userFcmTokenDao.deleteAllUserToken(u);
                userFcmTokenDao.save(userToken);
            }
        }catch (PendaftaranFcmTopicGagalException e){
            LOGGER.warn(e.getMessage(), e);
        }

    }

    /*private void deleteExistingUserToken(User u){
        userFcmTokenDao.deleteAllUserToken(u);

    }*/
}


