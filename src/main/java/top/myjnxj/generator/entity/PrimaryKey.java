package top.myjnxj.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName PrimaryKey
 * @Description 封装数据库表的主键
 * @Author JXNJ
 * @Date 2019/1/18 19:54
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrimaryKey {
    private String pk;
    private String javaPk;
    private String pkMethodName;
    private String javaType;
}
