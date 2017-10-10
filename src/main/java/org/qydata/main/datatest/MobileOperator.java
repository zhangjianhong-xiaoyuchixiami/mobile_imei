package org.qydata.main.datatest;

import org.qydata.po.Req;
import org.qydata.util.OperatorList;
import org.qydata.util.POIUtil;
import org.qydata.util.WriteTxt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonhn on 2017/9/22.
 */
public class MobileOperator {

    private static String readFileName = "E:\\数据测试\\20171009\\1.xlsx";
    private static String readFileName_1 = "E:\\数据测试\\20170922\\1融聚状态.xlsx";
    private static String resultFileName = "E:\\数据测试\\20171009\\1.txt";


    public static void main(String[] args) throws InterruptedException, IOException {

        List<Req> reqList = MobileOperator.readExcel(new File(readFileName));
        if (reqList != null) {
            for (Req req : reqList) {
                int operator = OperatorList.isOperator(req.mobile);
                if (operator == 1) {
                    String fileContent = req.mobile + ",移动" + ",NULL";
                    WriteTxt.writeTxt(resultFileName, fileContent);
                }
                if (operator == 2){
                    String fileContent = req.mobile + ",联通" + ",NULL";
                    WriteTxt.writeTxt(resultFileName, fileContent);
                }
                if (operator == 3){
                    String fileContent = req.mobile + ",电信" + ",NULL";
                    WriteTxt.writeTxt(resultFileName, fileContent);
                }
                if (operator == 0){
                    String fileContent = req.mobile + ",其他" + ",NULL";
                    WriteTxt.writeTxt(resultFileName, fileContent);
                }
            }
        }
    }



    public static List<Req> readExcel(File file) throws IOException {
        List<String[]> resultList =  POIUtil.readExcel(file);
        List<Req> reqList = new ArrayList<Req>();
        if (resultList != null){
            for (String [] strs : resultList) {
                String [] str = strs;
                Req req = new Req();
                req.mobile = str[2];
                reqList.add(req);
            }
        }
        return reqList;
    }


}
