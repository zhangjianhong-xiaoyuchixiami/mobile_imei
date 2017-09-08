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
import org.qydata.util.OperatorList;
import org.qydata.util.POIUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonhn on 2017/8/15.
 */
public class Mobile3fTest {

    private static String authId = "qydata03";
    private static String authPass = "a54cc70444ea4618ad8d586194ba1572";

    private static String readFileName = "E:\\数据测试\\手机号问题 _2.xlsx";
    private static String resultFileName = "E:\\数据测试\\手机号问题result_0818.txt";

    public static void main(String[] args){

        List<Req> reqList = null;
        try {
            reqList = Mobile3fTest.readExcel(new File(readFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < reqList.size() ; i++) {
            Req req = reqList.get(i);
            int operator = Mobile3fTest.isOperator(req.mobile);
            if (operator == 1){
                String resultMessage = null;
                try {
                    JSONObject jsonObject = Mobile3fTest.mobile3f(req.mobile,req.realName,req.idNo,90);
                    resultMessage = Mobile3fTest.parseReturn(jsonObject);
                } catch (IOException e) {
                    e.printStackTrace();
                    resultMessage = "异常";
                }
                String fileContent = "mobile:"+req.mobile+",realName:"+req.realName+",idNo:"+req.idNo+",运营商:移动"+",结果:"+resultMessage;
                try {
                    Mobile3fTest.writeTxt(resultFileName,fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (operator == 2){
                String resultMessage = "";
                try {
                    JSONObject jsonObject =  Mobile3fTest.mobile3f(req.mobile,req.realName,req.idNo,82);
                    resultMessage = Mobile3fTest.parseReturn(jsonObject);
                } catch (IOException e) {
                    e.printStackTrace();
                    resultMessage = "异常";
                }
                String fileContent = "mobile:"+req.mobile+",realName:"+req.realName+",idNo:"+req.idNo+",运营商:联通"+",结果:"+resultMessage;
                try {
                    Mobile3fTest.writeTxt(resultFileName,fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (operator == 3){
                String resultMessage = "";
                try {
                    JSONObject jsonObject =  Mobile3fTest.mobile3f(req.mobile,req.realName,req.idNo,28);
                    resultMessage = Mobile3fTest.parseReturn(jsonObject);
                } catch (IOException e) {
                    e.printStackTrace();
                    resultMessage = "异常";
                }
                String fileContent = "mobile:"+req.mobile+",realName:"+req.realName+",idNo:"+req.idNo+",运营商:电信"+",结果:"+resultMessage;
                try {
                    Mobile3fTest.writeTxt(resultFileName,fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (operator == 0){
                String resultMessage = "未知";
                String fileContent = "mobile:"+req.mobile+",realName:"+req.realName+",idNo:"+req.idNo+",运营商:未知"+",结果:"+resultMessage;
                try {
                    Mobile3fTest.writeTxt(resultFileName,fileContent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 请求三要素核验接口
     * @param mobile
     * @param realName
     * @param idNo
     * @param aid
     * @return
     * @throws IOException
     */
    public static JSONObject mobile3f(String mobile,String realName,String idNo, Integer aid) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("https://api.qydata.org:9000/mobile/verify/3f");
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
        Req req = new Req();
        req.authId = authId;
        req.reqId = Long.toString(System.currentTimeMillis()).substring(1);
        req.ts = System.currentTimeMillis();
        req.sign = DigestUtils.md5Hex(req.authId + authPass + req.reqId + Long.toString(req.ts)).toUpperCase();

        req.omitLocal = true;
        req.aid = aid;

        req.mobile = mobile;
        req.realName = realName;
        req.idNo = idNo;

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
                req.mobile = str[0];
                req.realName = str[1];
                req.idNo = str[2];
                reqList.add(req);
            }
        }
        return reqList;
    }

    /**
     * 判断运营商
     * @param mobile
     * @return
     */
    public static int isOperator(String mobile){

        String param = mobile.substring(0,3);
        if ( OperatorList.setChinaMobile.contains(param)){
            return 1;
        }else if (OperatorList.setChinaUnicom.contains(param)){
            return 2;
        }else if (OperatorList.setChinaTelecom.contains(param)){
            return 3;
        }
        return 0;
    }

    /**
     *写文件
     */
    public static void writeTxt(String fileName,String fileContent) throws IOException {
        File file = new File(fileName);
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(fileContent + "\r\n");
        bw.close();
    }

    /**
     * 解析返回结果
     * @param jsonObject
     * @return
     */
    public static String parseReturn(JSONObject jsonObject){
        String resultMessage = null;
        if (jsonObject == null){
            return "";
        }
        JSONObject jo = jsonObject.getJSONObject("result");
        if (jo == null){
            return "";
        }
        String result = jo.getString("resultCode");
        if ("-1".equals(result)){
            resultMessage = "无记录";
            return  resultMessage;
        } else if ("1".equals(result)){
            resultMessage = "全匹配";
            return  resultMessage;
        } else if ("2".equals(result)){
            resultMessage = "部分匹配";
            return  resultMessage;
        } else if ("3".equals(result)){
            resultMessage = "无匹配";
            return  resultMessage;
        } else if ("4".equals(result)){
            resultMessage = "不匹配";
            return  resultMessage;
        }
        return "";
    }

}
