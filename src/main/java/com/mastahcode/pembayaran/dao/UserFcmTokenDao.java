package com.mastahcode.pembayaran.dao;

import com.mastahcode.pembayaran.Entity.User;
import com.mastahcode.pembayaran.Entity.UserFcmToken;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by waviq on 3/09/2016.
 *
 */
public interface UserFcmTokenDao extends PagingAndSortingRepository<UserFcmToken, String> {
    @Modifying@Query("delete UserFcmToken ut where ut.user = :user")
    public void deleteAllUserToken(@Param("user") User u);
}
