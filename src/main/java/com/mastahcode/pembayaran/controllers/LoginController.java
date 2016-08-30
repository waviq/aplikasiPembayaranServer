package com.mastahcode.pembayaran.controllers;

import com.mastahcode.pembayaran.dto.LoginRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object>login(@RequestBody @Valid LoginRequest login){
        String validUsername = "waviq";
        String validPassword = "waviq";

        Map<String, Object> hasil = new HashMap<String, Object>();

        if (validUsername.equals(login.getUsername()) && validPassword.equals(login.getPassword())){
            hasil.put("sukses", true);
        }else {
            hasil.put("sukses", false);
        }

        return hasil;


    }
}
