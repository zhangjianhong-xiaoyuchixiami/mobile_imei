package org.qydata.po;

import lombok.Data;

/**
 * Created by jonhn on 2017/8/15.
 */
@Data
public class Result {

    public int resultCode;
    public String [] unmatched;
    public int operator;
    public int l;

}
