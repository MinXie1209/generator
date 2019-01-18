package top.myjnxj.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName TemplateAttribute
 * @Description TODO
 * @Author JXNJ
 * @Date 2019/1/17 10:40
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
public class TemplateAttribute {
    //包名
    private String packageName;
    //类名
    private String capClassName;
    //类实例名
    private String lowClassName;


}
