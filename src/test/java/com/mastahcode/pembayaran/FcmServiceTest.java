package com.mastahcode.pembayaran;

import com.mastahcode.pembayaran.services.FcmService;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by waviq on 2/09/2016.
 */


public class FcmServiceTest {

    @Test
    public void testKirimFcm(){
        FcmService fcm = new FcmService();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("action","test");
        fcm.kirimGcmMessage("ckx1iEEz-CM:APA91bHNXLt4w6Ofq9Skh_qApKvKmumS20KO8MxETeUikr_XvrOG1BWikc-Uprsf_vgsHwMcWiTOYFMvUOP7XZMoaucr9Bf983Jxyhz02PmTk88A4j_ct43FUb8C7UGIfQKl573KZG5f\n", data);
    }
}

