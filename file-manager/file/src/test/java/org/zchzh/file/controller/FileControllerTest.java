package org.zchzh.file.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileControllerTest {

    private final RestTemplate restTemplate = new RestTemplate();

    @LocalServerPort
    private int port;

    private static final String URL = "http://127.0.0.1:";

    @Test
    void downloadChunk() {
        // 添加header
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Range","bytes=0-");
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(httpEntity);

        File file = restTemplate.execute(URL + port + "/file/download/chunk?id=" + "2", HttpMethod.GET, requestCallback, clientHttpResponse -> {
            File ret = new File("D://testdata/testtesttest123");
            if (!ret.exists()) {
                ret.createNewFile();
            }
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            HttpHeaders headers = clientHttpResponse.getHeaders();
            log.info("Content-Range: {}", headers.get("Content-Range"));
            return ret;
        });
        Assertions.assertNotNull(file);
        log.info("file name :{} , file size : {}", file.getName(), file.length());
    }
}