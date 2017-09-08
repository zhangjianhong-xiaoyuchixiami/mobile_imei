package org.qydata.po;

import lombok.Data;

/**
 * Created by jonhn on 2017/8/15.
 */
@Data
public class Req {

    public String authId;
    public long ts;
    public String reqId;
    public String sign;
    public boolean omitLocal;
    public int aid;

    public String mobile;
    public String realName;
    public String idNo;
    public int l;

}
