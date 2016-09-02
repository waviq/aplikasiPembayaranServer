package com.mastahcode.pembayaran.services;




import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by waviq on 2/09/2016.
 * <p/>
 * Service untuk Fcm
 */
public class FcmService {

    private static final String API_KEY = "AIzaSyBvYI9lh-oe17uQAhJWhkdXiSqL8u-F324";
    private static final String FCM_SERVER = "https://fcm.googleapis.com/fcm/send";


    private RestTemplate restTemplate = new RestTemplate();

    //constructor
    public FcmService() {
        //inisialisasi rest template supaya setiap kirim request
        //include API key di Header

        restTemplate.setInterceptors(Arrays.<ClientHttpRequestInterceptor>asList(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                request.getHeaders().add("Authorization", "key=" + API_KEY);
                return execution.execute(request, body);
            }
        }));
    }

    public void kirimGcmMessage(String tujuan, Map<String, Object> data) {
        Map<String, Object> fcmRequest = new HashMap<String, Object>();
        fcmRequest.put("to", tujuan);
        fcmRequest.put("data", data);
        Map<String, Object> hasil = restTemplate.postForObject(FCM_SERVER, fcmRequest, Map.class);
        //LOG.debug("GCM : Starting GCM Sender");

    }

}
