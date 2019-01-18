package top.myjnxj.generator.conf;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName GeneratorConf
 * @Description TODO
 * @Author JXNJ
 * @Date 2019/1/16 20:06
 * @Version 1.0
 **/
@Component
@Data
@ConfigurationProperties(prefix = "generator")
public class GeneratorConf {

    private Map<String,String> conf;
}
