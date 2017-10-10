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
 * Created by jonhn on 2017/9/22.
 */
public class MobileStatus {

    private static String readFileName = "E:\\数据测试\\20171009\\2.xlsx";
    private static String readFileName_1 = "E:\\数据测试\\20170922\\1融聚状态.xlsx";
    private static String resultFileName = "E:\\数据测试\\20171009\\2-2.txt";


    public static void main(String[] args) throws InterruptedException, IOException {

        List<Req> reqList = ReadExcel.readExcel(new File(readFileName),1,null,null);
        if (reqList != null) {
            for (Req req : reqList) {
                int operator = OperatorList.isOperator(req.mobile);
                if (operator == 1) {
                    JSONObject jsonObject = RequestData.mobileProductApi("/mobile/query/status",req.mobile,null,null,22);
                    String resultMessage = MobileStatus.parseReturn(jsonObject);
                    String fileContent = req.mobile + ",移动" + "," + resultMessage;
                    WriteTxt.writeTxt(resultFileName, fileContent);
                }
                if (operator == 2){
                    JSONObject jsonObject = RequestData.mobileProductApi("/mobile/query/status",req.mobile,null,null,5);
                    String resultMessage = MobileStatus.parseReturn(jsonObject);
                    String fileContent = req.mobile + ",联通" + "," + resultMessage;
                    WriteTxt.writeTxt(resultFileName, fileContent);
                }
                if (operator == 3){
                    JSONObject jsonObject = RequestData.mobileProductApi("/mobile/query/status",req.mobile,null,null,18);
                    String resultMessage = MobileStatus.parseReturn(jsonObject);
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
            String result = jo.getString("status");
            if ("-2".equals(result)) {
                return "异常";
            } else if ("-1".equals(result)) {
                return "不在网";
            } else if ("0".equals(result)) {
                return "未知";
            } else if ("1".equals(result)) {
                return "未启用";
            } else if ("2".equals(result)) {
                return "正常";
            } else if ("3".equals(result)) {
                return "欠费停机";
            } else if ("4".equals(result)) {
                return "其它停机";
            } else if ("5".equals(result)) {
                return "已销号";
            } else if ("6".equals(result)) {
                return "在网但不可用";
            } else if ("7".equals(result)) {
                return "停机";
            }
        }
        return code;
    }

}
