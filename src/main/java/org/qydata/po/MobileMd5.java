package org.qydata.po;


import lombok.Data;

/**
 * Created by jonhn on 2017/8/15.
 */

/**
 *
 * @Data   ：注解在类上；提供类所有属性的 getting 和 setting 方法，此外还提供了equals、canEqual、hashCode、toString 方法
 @Setter：注解在属性上；为属性提供 setting 方法
 @Getter：注解在属性上；为属性提供 getting 方法
 @Log4j ：注解在类上；为类提供一个 属性名为log 的 log4j 日志对象
 @NoArgsConstructor：注解在类上；为类提供一个无参的构造方法
 @AllArgsConstructor：注解在类上；为类提供一个全参的构造方法
 */

@Data
public class MobileMd5 {

    public String mobile;
    public String md5;
    public String imei;
    public String imsi;

}
