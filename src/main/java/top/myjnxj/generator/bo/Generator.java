package top.myjnxj.generator.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Generator
 * @Description 封装自动生成代码的参数
 * @Author JXNJ
 * @Date 2019/1/16 15:22
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Generator {
    /**
     * 包名，前缀
     */
    private String packageName;
    /**
     * 数据库的url
     */
    private String url;
    /**
     * 数据库名
     */
    private String dataBase;
    /**
     * 数据库的用户名
     */
    private  String userName;
    /**
     * 数据库的密码
     */
    private String password;
}
