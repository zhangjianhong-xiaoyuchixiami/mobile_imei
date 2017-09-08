package org.qydata.po;

import lombok.Data;

/**
 * Created by jonhn on 2017/8/15.
 */
@Data
public class Resp {

    public int code;
    public String message;
    public Result result;

}
