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
import org.qydata.po.Req;
import org.qydata.util.POIUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonhn on 2017/8/15.
 */
public class MobileDurationTest {

    private static String authId = "qydata03";
    private static String authPass = "a54cc70444ea4618ad8d586194ba1572";

    private static String readFileName = "E:\\数据测试\\移动在网时长测试样本.xlsx";
    private static String resultFileName = "E:\\数据测试\\移动在网时长测试样本.xlsx_result_0816.txt";

    public static void main(String[] args){

        List<Req> reqList = null;
        try {
            reqList = MobileDurationTest.readExcel(new File(readFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < reqList.size() ; i++) {
            Req req = reqList.get(i);
            int operator = Mobile3fTest.isOperator(req.mobile);
            if (operator == 1){
                String resultMessage = null;
                try {
                    JSONObject jsonObject = MobileDurationTest.mobileDuration(req.mobile,83);
                    resultMessage = MobileDurationTest.parseReturn(jsonObject);
                } catch (IOException e) {
                    e.printStackTrace();
                    resultMessage = "异常";
                }
                String fileContent = "mobile:"+req.mobile+",运营商:移动"+",结果:"+resultMessage;
                try {
                    Mobile3fTest.writeTxt(resultFileName,fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (operator == 2){
                String resultMessage = "";
                // JSONObject jsonObject =  Mobile3fTest.mobile3f(req.mobile,req.realName,req.idNo,70);
                String fileContent = "mobile:"+req.mobile+",运营商:联通"+",结果:"+resultMessage;
                try {
                    Mobile3fTest.writeTxt(resultFileName,fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (operator == 3){
                String resultMessage = "";
                //JSONObject jsonObject =  Mobile3fTest.mobile3f(req.mobile,req.realName,req.idNo,70);
                String fileContent = "mobile:"+req.mobile+",运营商:电信"+",结果:"+resultMessage;
                try {
                    Mobile3fTest.writeTxt(resultFileName,fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (operator == 0){
                String resultMessage = "未知";
                String fileContent = "mobile:"+req.mobile+",运营商:未知"+",结果:"+resultMessage;
                try {
                    Mobile3fTest.writeTxt(resultFileName,fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 请求在网时长核验接口
     * @param mobile
     * @param aid
     * @return
     * @throws IOException
     */
    public static JSONObject mobileDuration(String mobile,Integer aid) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("https://api.qydata.org:9000/mobile/query/duration");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        Req req = new Req();
        req.authId = authId;
        req.reqId = Long.toString(System.currentTimeMillis()).substring(1);
        req.ts = System.currentTimeMillis();
        req.sign = DigestUtils.md5Hex(req.authId + authPass + req.reqId + Long.toString(req.ts)).toUpperCase();

        req.omitLocal = true;
        req.aid = aid;

        req.mobile = mobile;

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
    public static List<Req> readExcel(File file) throws IOException {
        List<String[]> resultList =  POIUtil.readExcel(file);
        List<Req> reqList = new ArrayList<Req>();
        if (resultList != null){
            for (String [] strs : resultList) {
                String [] str = strs;
                Req req = new Req();
                req.mobile = str[1];
                reqList.add(req);
            }
        }
        return reqList;
    }

    /**
     * 解析返回结果
     * @param jsonObject
     * @return
     */
    public static String parseReturn(JSONObject jsonObject){
        if (jsonObject == null){
            return "";
        }
        JSONObject jo = jsonObject.getJSONObject("result");
        if (jo == null){
            return "";
        }
        String result = jo.getString("rangeStart");
        String result2 = jo.getString("rangeEnd");
        return result + "&" + result2;
    }

}
