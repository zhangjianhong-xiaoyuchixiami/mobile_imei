package org.qydata.util;

import org.qydata.po.Req;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonhn on 2017/10/10.
 */
public class ReadExcel {

    /**
     * 封装请求数据
     * @param file
     * @return
     * @throws IOException
     */
    public static List<Req> readExcel(File file,Integer mobileLine,Integer realNameLine,Integer idNoLine) throws IOException {
        List<String[]> resultList =  POIUtil.readExcel(file);
        List<Req> reqList = new ArrayList<Req>();
        if (resultList != null){
            for (String [] strs : resultList) {
                String [] str = strs;
                Req req = new Req();
                if (mobileLine != null){
                    req.mobile = str[mobileLine];
                }
                if (realNameLine != null){
                    req.realName = str[realNameLine];
                }
                if (idNoLine != null){
                    req.idNo = str[idNoLine];
                }
                reqList.add(req);
            }
        }
        return reqList;
    }

}
