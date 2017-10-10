package org.qydata.main.datatest;

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
import org.qydata.po.Req;
import org.qydata.util.OperatorList;
import org.qydata.util.POIUtil;
import org.qydata.util.WriteTxt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonhn on 2017/9/20.
 */
public class Mobile2f {

    private static String readFileName = "E:\\数据测试\\20170920\\123456.xlsx";
    private static String readFileName_1 = "E:\\数据测试\\20170920\\123456_111.xlsx";
    private static String resultFileName = "E:\\数据测试\\20170920\\测试泰岳移动2要素20170920_result.txt";


    public static void main(String[] args) throws InterruptedException {

        List<Req> reqList = null;
        try {
            reqList = Mobile2f.readExcel(new File(readFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < reqList.size() ; i++) {
            Req req = reqList.get(i);
            int operator = OperatorList.isOperator(req.mobile);
            Thread.sleep(500);
            if (operator == 1){
                String resultMessage = null;
                try {
                    JSONObject jsonObject = Mobile2f.mobile2f(req.mobile,req.realName,102);
                    resultMessage = Mobile2f.parseReturn(jsonObject);
                } catch (IOException e) {
                    e.printStackTrace();
                    resultMessage = "异常";
                }
                String fileContent = "mobile:"+req.mobile+",realName:"+req.realName+",运营商:移动"+",结果:"+resultMessage;
                try {
                    WriteTxt.writeTxt(resultFileName,fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (operator == 2){

                String fileContent = "mobile:"+req.mobile+",realName:"+req.realName+",运营商:联通";
                try {
                    WriteTxt.writeTxt(resultFileName,fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (operator == 3){
                String fileContent = "mobile:"+req.mobile+",realName:"+req.realName+",运营商:电信";
                try {
                    WriteTxt.writeTxt(resultFileName,fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (operator == 0){
                String resultMessage = "未知";
                String fileContent = "mobile:"+req.mobile+",realName:"+req.realName+",运营商:未知"+",结果:"+resultMessage;
                try {
                    WriteTxt.writeTxt(resultFileName,fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static JSONObject mobile2f(String mobile, String realName,Integer aid) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(GlobalStaticConstants.REQUEST_PREFIX_API + "/mobile/verify/2f-name");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        Req req = new Req();
        req.authId = GlobalStaticConstants.AUTHID_03;
        req.reqId = Long.toString(System.currentTimeMillis()).substring(1);
        req.ts = System.currentTimeMillis();
        req.sign = DigestUtils.md5Hex(req.authId + GlobalStaticConstants.PASSWORD_03 + req.reqId + Long.toString(req.ts)).toUpperCase();
        req.omitLocal = true;
        req.aid = aid;
        req.mobile = mobile;
        req.realName = realName;
        request.setEntity(new StringEntity(new Gson().toJson(req), Charsets.UTF_8));
        CloseableHttpResponse execute = httpClient.execute(request);
        String result = EntityUtils.toString(execute.getEntity());
        System.out.println(result);
        JSONObject resultJo = JSONObject.fromObject(result);
        return resultJo;
    }

    public static List<Req> readExcel(File file) throws IOException {
        List<String[]> resultList =  POIUtil.readExcel(file);
        List<Req> reqList = new ArrayList<Req>();
        if (resultList != null){
            for (String [] strs : resultList) {
                String [] str = strs;
                Req req = new Req();
                req.mobile = str[0];
                req.realName = str[1];
                reqList.add(req);
            }
        }
        return reqList;
    }

    public static String parseReturn(JSONObject jsonObject){
        String resultMessage = null;
        if (jsonObject == null){
            return "";
        }
        JSONObject jo = jsonObject.getJSONObject("result");
        if (jo == null || jsonObject.get("result") instanceof net.sf.json.JSONNull){
            return "无记录";
        }
        String result = jo.getString("resultCode");
        if ("-1".equals(result)){
            resultMessage = "无记录";
            return  resultMessage;
        } else if ("1".equals(result)){
            resultMessage = "匹配";
            return  resultMessage;
        } else if ("4".equals(result)){
            resultMessage = "不匹配";
            return  resultMessage;
        }
        return "";
    }

}
