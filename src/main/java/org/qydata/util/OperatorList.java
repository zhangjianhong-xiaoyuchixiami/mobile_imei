package org.qydata.util;

import java.util.HashSet;

/**
 * Created by jonhn on 2017/8/15.
 */
public class OperatorList {

    //移动
    public final static HashSet<String> setChinaMobile = new HashSet<String>();
    //联通
    public final static HashSet<String> setChinaUnicom = new HashSet<String>();
    //电信
    public final static HashSet<String> setChinaTelecom = new HashSet<String>();

    static {

        //移动
        setChinaMobile.add("134");
        setChinaMobile.add("135");
        setChinaMobile.add("136");
        setChinaMobile.add("137");
        setChinaMobile.add("138");
        setChinaMobile.add("139");
        setChinaMobile.add("147");
        setChinaMobile.add("150");
        setChinaMobile.add("151");
        setChinaMobile.add("152");
        setChinaMobile.add("157");
        setChinaMobile.add("158");
        setChinaMobile.add("159");
        setChinaMobile.add("178");
        setChinaMobile.add("182");
        setChinaMobile.add("183");
        setChinaMobile.add("184");
        setChinaMobile.add("187");
        setChinaMobile.add("188");

        //联通
        setChinaUnicom.add("130");
        setChinaUnicom.add("131");
        setChinaUnicom.add("132");
        setChinaUnicom.add("145");
        setChinaUnicom.add("155");
        setChinaUnicom.add("156");
        setChinaUnicom.add("171");
        setChinaUnicom.add("175");
        setChinaUnicom.add("176");
        setChinaUnicom.add("185");
        setChinaUnicom.add("186");

        //电信
        setChinaTelecom.add("133");
        setChinaTelecom.add("153");
        setChinaTelecom.add("173");
        setChinaTelecom.add("177");
        setChinaTelecom.add("180");
        setChinaTelecom.add("181");
        setChinaTelecom.add("189");
        setChinaTelecom.add("170");

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

}
