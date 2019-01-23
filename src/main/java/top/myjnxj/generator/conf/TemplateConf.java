package top.myjnxj.generator.conf;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TemplateConf
 * @Description TODO
 * @Author JXNJ
 * @Date 2019/1/22 20:31
 * @Version 1.0
 **/
@Component
@Data
@ConfigurationProperties(prefix = "template")
public class TemplateConf {
    private  Map<String, String> conf;

    public void replace(String packageName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(packageName.replace(".", File.separator));

        Map<String, String> newConf = new HashMap<>();

        for (Map.Entry<String, String> entry : conf.entrySet()) {
            newConf.put(entry.getKey(), StringUtils.replace(entry.getValue(), "packageName", stringBuilder.toString()).replace(".",File.separator));
        }
        conf = newConf;
    }
}
