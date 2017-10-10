package org.qydata.main;

import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.qydata.constants.GlobalStaticConstants;

import java.io.IOException;

/**
 * Created by jonhn on 2017/9/12.
 */
public class MD5ToImei {

    public static void main(String[] args) throws IOException {
        String q [] = {"55C18606CBF541A983F1B6BE1D941872"};
        String sid = Long.toString(System.currentTimeMillis());
        JSONObject jsonObject = mobileToImei(q,sid);
        System.out.println(jsonObject.toString());
    }

    public static JSONObject mobileToImei(String [] q, String sid) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(GlobalStaticConstants.REQUEST_PREFIX + "/mobile/query/md5-no");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        MobileToImei.Req req = new MobileToImei.Req();
        req.authId = GlobalStaticConstants.AUTHID;
        req.reqId = Long.toString(System.currentTimeMillis()).substring(1);
        req.ts = System.currentTimeMillis();
        req.sign = DigestUtils.md5Hex(req.authId + GlobalStaticConstants.PASSWORD + req.reqId + Long.toString(req.ts)).toUpperCase();
        req.q = q;
        req.sid = sid;
        request.setEntity(new StringEntity(new Gson().toJson(req), Charsets.UTF_8));
        CloseableHttpResponse execute = httpClient.execute(request);
        String result = EntityUtils.toString(execute.getEntity());
        System.out.println(result);
        JSONObject resultJo = JSONObject.fromObject(result);
        return resultJo;
    }

}
