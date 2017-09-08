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
import org.qydata.util.POIUtil;
import org.qydata.util.WriteTxt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonhn on 2017/5/23.
 */
public class MobileToImei {

    public static class Req {
        public String authId;
        public long ts;
        public String reqId;
        public String sign;

        public String [] q;
        public String sid;

    }

    private static String authId = "qydata04";
    private static String authPass = "ff1625575cd54c3faad0763d98513765";

    private static String readFileName = "E:\\数据测试2\\mobileToimei.xlsx";
    private static String resultFileName = "E:\\数据测试2\\mobileToimei_0904_result.txt";

    public static void main(String[] args) throws IOException {
        String [] q = readExcel(new File(readFileName));
        JSONObject jsonObject = MobileToImei.mobileToImei(q,"20170904-qy-2");
        WriteTxt.writeTxt(resultFileName,jsonObject.toString());

    }

    /**
     *
     * @return
     * @throws IOException
     */
    public static JSONObject mobileToImei(String [] q, String sid) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("https://imei.qydata.org:9000/mobile/query/imei2no");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        Req req = new Req();
        req.authId = authId;
        req.reqId = Long.toString(System.currentTimeMillis()).substring(1);
        req.ts = System.currentTimeMillis();
        req.sign = DigestUtils.md5Hex(req.authId + authPass + req.reqId + Long.toString(req.ts)).toUpperCase();
        req.q = q;
        req.sid = sid;
        request.setEntity(new StringEntity(new Gson().toJson(req), Charsets.UTF_8));
        CloseableHttpResponse execute = httpClient.execute(request);
        String result = EntityUtils.toString(execute.getEntity());
        System.out.println(result);
        JSONObject resultJo = JSONObject.fromObject(result);
        return resultJo;
    }

    /**
     * 封装请求数据
     * @param file
     * @return
     * @throws IOException
     */
    public static String [] readExcel(File file) throws IOException {
        List<String[]> resultList =  POIUtil.readExcel(file);
        List<String> stringList = new ArrayList<String>();
        if (resultList != null){
            for (String [] strs : resultList) {
                String [] str = strs;
                stringList.add(str[0]);
            }
        }
        return (String[]) stringList.toArray(new String[stringList.size()]);
    }

}
