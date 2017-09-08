package org.qydata.main;

import org.qydata.po.MobileMd5;
import org.qydata.util.POIUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonhn on 2017/8/15.
 */
public class Test {

    public static void main(String[] args) throws IOException {

      List<String[]> resultList =  POIUtil.readExcel(new File("D:\\mobile\\mobile1.xlsx"));
      List<MobileMd5> mobileMd5List = new ArrayList<MobileMd5>();
      if (resultList != null){
          for (String [] strs : resultList) {
              String [] str = strs;
              MobileMd5 mobileMd5 = new MobileMd5();
              mobileMd5.mobile = str[0];
              mobileMd5.md5 = str[1];
              mobileMd5.imei = str[2];
              mobileMd5.imsi = str[3];
              mobileMd5List.add(mobileMd5);
          }
      }
        for (MobileMd5 mobileMd5 : mobileMd5List) {
            System.out.println(mobileMd5.toString());
        }
    }

}
