package org.qydata.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonhn on 2017/8/15.
 */
public class OperatorListTest {

    @Test
    public void contextLoads(){
       // Assert.assertEquals(19,OperatorList.setChinaMobile.size());
       // Assert.assertEquals(11,OperatorList.setChinaUnicom.size());
       // Assert.assertEquals(8,OperatorList.setChinaTelecom.size());
        //Assert.assertEquals(true,StringUtils.equals("100","100"));

//        HashSet<Integer> set = new HashSet<Integer>();
//        set.add(3000);
//        set.add(2500);
//        set.add(2000);
//        set.add(1500);
//        set.add(1000);
//        if (set.contains(2000)){
//            set.remove(2000);
//        }
//        Assert.assertEquals(4, set.size());

        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(0);
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(1);
        System.out.println(list.containsAll(list1));
    }

}