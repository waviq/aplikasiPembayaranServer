package com.mastahcode.pembayaran.controllers;


import com.mastahcode.pembayaran.Entity.User;
import com.mastahcode.pembayaran.services.PembayaranService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by waviq on 3/09/2016.
 * controller untuk update token
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired private PembayaranService pembayaranService;

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public void updateToken(@RequestBody Map<String, String> data){
        pembayaranService.updateToken(data.get("email"), data.get("token"));
    }
}
