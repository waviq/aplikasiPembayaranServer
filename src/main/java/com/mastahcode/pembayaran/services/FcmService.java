package com.mastahcode.pembayaran.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastahcode.pembayaran.Entity.AntrianFcm;
import com.mastahcode.pembayaran.Entity.StatusAntrian;
import com.mastahcode.pembayaran.dao.AntrianFcmDao;
import com.mastahcode.pembayaran.exception.PendaftaranFcmTopicGagalException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by waviq on 2/09/2016.
 * <p/>
 * Service untuk Fcm
 */

@Service
@Transactional
public class FcmService {

    private static final String API_KEY = "AIzaSyBvYI9lh-oe17uQAhJWhkdXiSqL8u-F324";
    private static final String FCM_SERVER = "https://fcm.googleapis.com/fcm/send";
    private static final Logger LOGGER = LoggerFactory.getLogger(FcmService.class);


    private RestTemplate restTemplate = new RestTemplate();

    //digunakan untuk convert objek ke json
    private ObjectMapper objectMapper = new ObjectMapper();

    //simpen
    @Autowired
    private AntrianFcmDao antrianFcmDao;

    //constructor
    public FcmService() {
        //inisialisasi rest template supaya setiap kirim request
        //include API key di Header
        restTemplate.setInterceptors(Arrays.<ClientHttpRequestInterceptor>asList(new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                request.getHeaders().add("Authorization", "key=" +API_KEY);
                return execution.execute(request, body);
            }
        }));

    }

    public void daftarkanTokenKeTopic(String token, String topic) throws PendaftaranFcmTopicGagalException{

        String urlRegistrasiTopic = "https://iid.googleapis.com/iid/v1/"+token+"/rel/topics/"+topic;
        ResponseEntity<Void> hasil = restTemplate.postForEntity(urlRegistrasiTopic, null, Void.class);
        LOGGER.debug("FCM : Pendaftaran token {} ke topic {} : {}", token, topic, hasil.getStatusCode().toString());

        if (!HttpStatus.OK.equals(hasil.getStatusCode())){
            throw new PendaftaranFcmTopicGagalException("Error "+hasil.getStatusCode().toString());
        }
    }

    public void kirimGcmMessage(String tujuan, Map<String, Object> data) {
        try {
            AntrianFcm a = new AntrianFcm();
            a.setTujuan("ckx1iEEz-CM:APA91bHNXLt4w6Ofq9Skh_qApKvKmumS20KO8MxETeUikr_XvrOG1BWikc-Uprsf_vgsHwMcWiTOYFMvUOP7XZMoaucr9Bf983Jxyhz02PmTk88A4j_ct43FUb8C7UGIfQKl573KZG5f");
            a.setData(objectMapper.writeValueAsString(data));
            antrianFcmDao.save(a);
        } catch (JsonProcessingException e) {
            LOGGER.warn("FCM gagal insert ke antrian : {}", e.getMessage());
        }


    }

    //scaduler untuk memproses antrian FCM
    @Scheduled(fixedDelay = 5000)
    public void prosesAntrianFcm() {

        //ambil berdasarkan status dlo, halaman 0, yg d ambil 1 record aja
        PageRequest pr = new PageRequest(0, 1);
        //Query
        Page<AntrianFcm> antrianTeratas = antrianFcmDao.findByStatusOrderByWaktuKirimAsc(StatusAntrian.BARU, pr);

        LOGGER.debug("FCM :memproses{} antrian dari {}",
                antrianTeratas.getNumberOfElements(),
                antrianTeratas.getTotalElements()); //Log status
        //jika antrian kosong, ya udh biarin aja
        if (antrianTeratas.getNumberOfElements() < 1) {
            return;
        }
        AntrianFcm a = antrianTeratas.getContent().get(0);
        prosesPengiriman(a);

        //kita save apapun statusnya dari proses pengiriman
        antrianFcmDao.save(a);

    }

    private void prosesPengiriman(AntrianFcm a) {

        try {
            Map<String, Object> fcmRequest = new HashMap<String, Object>();
            fcmRequest.put("to", a.getTujuan());
            fcmRequest.put("data", objectMapper.readValue(a.getData(), Map.class));
            Map<String, Object> hasil = restTemplate.postForObject(FCM_SERVER, fcmRequest, Map.class);

            LOGGER.debug("FCM sukses : [{}]", hasil.get("success"));

            //jika sukses set status menjadi terkirim
            a.setStatus(StatusAntrian.TERKIRIM);
            a.setWaktuKirim(new Date());
        } catch (IOException e) {
            LOGGER.warn("FCM gagal: {}", e.getMessage());

            //jika gagal set
            a.setStatus(StatusAntrian.GAGAL_KIRIM);
            a.setWaktuKirim(new Date());
            a.setKeterangan(e.getMessage());
        }

    }

}
