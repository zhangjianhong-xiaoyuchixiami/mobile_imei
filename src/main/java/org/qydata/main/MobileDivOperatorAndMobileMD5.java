package org.qydata.main;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.hssf.usermodel.*;

import java.io.*;
import java.util.Iterator;

/**
 * Created by jonhn on 2017/3/8.
 */
public class MobileDivOperatorAndMobileMD5 {


    public static void main(String[] args) throws Exception {
        readTable();
    }

    public static void readTable() throws Exception{
        InputStream ips=new FileInputStream("D:\\mobile\\mobile.xls");
        HSSFWorkbook wb=new HSSFWorkbook(ips);
        HSSFSheet sheet=wb.getSheetAt(0);

        for(Iterator ite = sheet.rowIterator(); ite.hasNext();){
            HSSFRow row=(HSSFRow)ite.next();
            String fileContent = "";
            for(Iterator itet=row.cellIterator();itet.hasNext();){
                HSSFCell cell=(HSSFCell)itet.next();
                switch(cell.getCellType()){
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        //得到Boolean对象的方法
                        System.out.print(cell.getBooleanCellValue()+" ");
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        //先看是否是日期格式
                        if(HSSFDateUtil.isCellDateFormatted(cell)){
                            //读取日期格式
                            System.out.print(cell.getDateCellValue()+" ");
                        }else{
                            //读取数字
                            System.out.print(cell.getNumericCellValue()+" ");
                        }
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:
                        //读取公式
                        System.out.print(cell.getCellFormula()+" ");
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        String [] strYiDong = {"134","135","136","137","138","139","147","150","151","152","157","158","159","178","182","183","184","187","188"};
                        String [] strLianTong = {"130","131","132","145","155","156","171","175","176","185","186"};
                        String [] strDianxing = {"133","153","173","177","180","181","189","170"};
//                        Map<String,String> map = new HashMap();
//                        map.put("134","2004");
//                        map.put("135","1997");
//                        map.put("136","1997");
//                        map.put("137","1997");
//                        map.put("138","1999");
//                        map.put("139","1994");
//                        map.put("147","2009");
//                        map.put("150","2007");
//                        map.put("151","2008");
//                        map.put("152","2006");
//                        map.put("157","2007");
//                        map.put("158","2007");
//                        map.put("159","2005");
//                        map.put("178","2017");
//                        map.put("182","2010");
//                        map.put("183","2011");
//                        map.put("184","2013");
//                        map.put("187","2009");
//                        map.put("188","2009");
                        String value = cell.getRichStringCellValue().toString().trim();
                        String valueParam = value.substring(0,3);
//                        String [] aaa = value.split("-");
//                        HashSet<Integer> set = new HashSet<>();
//                        int sum = 0;
//                        for (int i = 0; i < aaa.length ; i++) {
//                            set.add(Integer.parseInt(aaa[i]));
//                        }
//                        for (int a:set) {
//                            sum=sum+a;
//                        }
//                        fileContent = sum+"";
                        for (int i = 0; i < strYiDong.length ; i++) {
                            if (valueParam.contains(strYiDong[i])){
                                String mobile = value;
                               // String year = strYiDong[i];
                               // String year_2 = map.get(year);
                               // int resu = (2017 - Integer.parseInt(year_2)) - 1;
                                if (Integer.parseInt(mobile) >= 5){

                                }
                              //  fileContent = mobile +"-" +"移动" +"&" + resu ;
                                System.out.println(fileContent);
                                break;
                            }
                        }
                        for (int i = 0; i < strLianTong.length ; i++) {
                            if (valueParam.contains(strLianTong[i])){
                                String mobile = value;
                                String md5Mobile = DigestUtils.md5Hex(mobile);
                                fileContent = mobile +"-" +"联通" +"&" + md5Mobile ;
                            }
                        }
                        for (int i = 0; i < strDianxing.length ; i++) {
                            if (valueParam.contains(strDianxing[i])){
                                String mobile = value;
                                String md5Mobile = DigestUtils.md5Hex(mobile);
                                fileContent = mobile +"-" +"电信" +"&" + md5Mobile ;
                            }
                        }
                        break;
                }
            }
            File file = new File("D:\\mobile\\result_0815.txt");
            if(!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fileContent + "\r\n");
            bw.close();
        }

    }
}
