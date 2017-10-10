package org.qydata.main.imei;

import com.google.gson.Gson;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 *
 */
public class Imsi2Mobile {

    public static class Req {
        public String authId;
        public long ts;
        public String reqId;
        public String sign;


        public String [] q;
        public String sid;

    }

    static String authId = "bjlskj";
    static String authPass = "d8729c7d73e047b99e6e9aa144a0ace2";

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("https://imei.qydata.org:9000/mobile/query/imsi2no");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        Imsi2Mobile.Req req = new Imsi2Mobile.Req();
        req.authId = authId;
        req.reqId = Long.toString(System.currentTimeMillis()).substring(1);
        req.ts = System.currentTimeMillis();
        req.sign = DigestUtils.md5Hex(req.authId + authPass + req.reqId + Long.toString(req.ts)).toUpperCase();
        req.q = new String[]{"460079146560619"};
        req.sid = "2047092500123";
        request.setEntity(new StringEntity(new Gson().toJson(req), Charsets.UTF_8));
        CloseableHttpResponse execute = httpClient.execute(request);
        System.out.println("httpCode="+execute.getStatusLine().getStatusCode());
        System.out.println("测试结果="+ EntityUtils.toString(execute.getEntity()));
    }
}
