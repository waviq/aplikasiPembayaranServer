package com.mastahcode.pembayaran;

import com.mastahcode.pembayaran.services.FcmService;
import com.mastahcode.pembayaran.services.PembayaranService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by waviq on 2/09/2016.
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(PembayaranServerApplication.class)
public class FcmServiceTest {

    @Autowired private  FcmService fcm;
    @Test
    public void testKirimFcm() throws Exception{
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("action","test waviq");
        fcm.kirimGcmMessage("ckx1iEEz-CM:APA91bHNXLt4w6Ofq9Skh_qApKvKmumS20KO8MxETeUikr_XvrOG1BWikc-Uprsf_vgsHwMcWiTOYFMvUOP7XZMoaucr9Bf983Jxyhz02PmTk88A4j_ct43FUb8C7UGIfQKl573KZG5f", data);

        Thread.sleep(10*1000);
    }
}

