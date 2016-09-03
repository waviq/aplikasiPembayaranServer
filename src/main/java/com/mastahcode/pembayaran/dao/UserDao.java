package com.mastahcode.pembayaran.dao;

import com.mastahcode.pembayaran.Entity.User;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by waviq on 3/09/2016.
 *
 */
public interface UserDao extends PagingAndSortingRepository<User, String> {
    public User findByEmail(String email);
}
