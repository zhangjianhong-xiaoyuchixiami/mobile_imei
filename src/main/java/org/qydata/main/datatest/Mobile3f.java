package org.qydata.main.datatest;

import net.sf.json.JSONObject;
import org.qydata.po.Req;
import org.qydata.util.OperatorList;
import org.qydata.util.ReadExcel;
import org.qydata.util.RequestData;
import org.qydata.util.WriteTxt;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by jonhn on 2017/8/15.
 */
public class Mobile3f {

    private static String readFileName = "E:\\数据测试\\20171009\\1.xlsx";
    private static String resultFileName = "E:\\数据测试\\20171009\\1.txt";

    public static void main(String[] args) throws IOException {
        List<Req> reqList = ReadExcel.readExcel(new File(readFileName),1,2,3);
        for (Req req : reqList) {
            int operator = OperatorList.isOperator(req.mobile);
            if (operator == 1){
                JSONObject jsonObject = RequestData.mobileProductApi("/mobile/verify/3f",req.mobile,req.realName,req.idNo,90);
                String resultMessage = Mobile3f.parseReturn(jsonObject);
                String fileContent = req.mobile+","+req.realName+","+req.idNo+",移动"+","+resultMessage;
                WriteTxt.writeTxt(resultFileName,fileContent);
            }
            if (operator == 2){
                JSONObject jsonObject =  RequestData.mobileProductApi("/mobile/verify/3f",req.mobile,req.realName,req.idNo,82);
                String resultMessage = Mobile3f.parseReturn(jsonObject);
                String fileContent = req.mobile+","+req.realName+","+req.idNo+",联通"+","+resultMessage;
                WriteTxt.writeTxt(resultFileName,fileContent);
            }
            if (operator == 3){
                JSONObject jsonObject =  RequestData.mobileProductApi("/mobile/verify/3f",req.mobile,req.realName,req.idNo,104);
                String resultMessage = Mobile3f.parseReturn(jsonObject);
                String fileContent = req.mobile+","+req.realName+","+req.idNo+",电信"+","+resultMessage;
                WriteTxt.writeTxt(resultFileName,fileContent);
            }
            if (operator == 0){
                String resultMessage = "NULL";
                String fileContent = req.mobile+","+req.realName+","+req.idNo+",其他"+","+resultMessage;
                WriteTxt.writeTxt(resultFileName,fileContent);
            }
        }
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
        String code = jsonObject.getString("code");
        if (code == null || jsonObject.get("code") instanceof net.sf.json.JSONNull || "".equals(code)){
            return "";
        }
        if ("0".equals(code)) {
            JSONObject jo = jsonObject.getJSONObject("result");
            if (jo == null){
                return "";
            }
            String result = jo.getString("resultCode");
            if ("-1".equals(result)){
                return  "无记录";
            } else if ("1".equals(result)){
                return  "全匹配";
            } else if ("2".equals(result)){
                return  "部分匹配";
            } else if ("3".equals(result)){
                return  "无匹配";
            } else if ("4".equals(result)){
                return  "不匹配";
            }
        }
        return code;
    }

}
