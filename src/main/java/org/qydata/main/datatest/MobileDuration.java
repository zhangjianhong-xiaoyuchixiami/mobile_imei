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
public class MobileDuration {



    private static String readFileName = "E:\\数据测试\\20171009\\2.xlsx";
    private static String readFileName_1 = "E:\\数据测试\\20170922\\1融聚状态.xlsx";
    private static String resultFileName = "E:\\数据测试\\20171009\\2-3.txt";

    public static void main(String[] args) throws InterruptedException, IOException {

        List<Req> reqList = ReadExcel.readExcel(new File(readFileName),1,null,null);
        if (reqList != null) {
            for (Req req : reqList) {
                int operator = OperatorList.isOperator(req.mobile);
                if (operator == 1) {
                    JSONObject jsonObject = RequestData.mobileProductApi("/mobile/query/duration",req.mobile,null,null,null);
                    String resultMessage = MobileDuration.parseReturn(jsonObject);
                    String fileContent = req.mobile + ",移动" + "," + resultMessage;
                    WriteTxt.writeTxt(resultFileName, fileContent);
                }
                if (operator == 2){
                    JSONObject jsonObject = RequestData.mobileProductApi("/mobile/query/duration",req.mobile,null,null,null);
                    String resultMessage = MobileDuration.parseReturn(jsonObject);
                    String fileContent = req.mobile + ",联通" + "," + resultMessage;
                    WriteTxt.writeTxt(resultFileName, fileContent);
                }
                if (operator == 3){
                    JSONObject jsonObject = RequestData.mobileProductApi("/mobile/query/duration",req.mobile,null,null,null);
                    String resultMessage = MobileDuration.parseReturn(jsonObject);
                    String fileContent = req.mobile + ",电信" + "," + resultMessage;
                    WriteTxt.writeTxt(resultFileName, fileContent);
                }
                if (operator == 0){
                    String fileContent = req.mobile + ",其他" + ",NULL";
                    WriteTxt.writeTxt(resultFileName, fileContent);
                }
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
            if (jo == null) {
                return "";
            }
            String result = jo.getString("rangeStart");
            String result2 = jo.getString("rangeEnd");
            return result + "," + result2;
        }
        return code;
    }

}
