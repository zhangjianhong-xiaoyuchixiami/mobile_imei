package org.qydata.main;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

/**
 * Created by jonhn on 2017/3/8.
 */
public class MobileDivOperatorAndMobileMD5 {


    @Test
    public void contextLoads() {
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(1);
        set.add(1);
        set.add(1);
        set.add(1);
        set.add(1);
        set.add(1);
        set.add(1);
        set.add(1);
        Assert.assertEquals(1, set.size());
        set.add(2);
        Assert.assertEquals(2, set.size());

    }

}
