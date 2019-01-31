package top.myjnxj.generator.service;

import top.myjnxj.generator.bo.Generator;

/**
 * @InterfaceName GeneratorService
 * @Description TODO
 * @Author JXNJ
 * @Date 2019/1/16 15:40
 * @Version 1.0
 **/
public interface GeneratorService {
    /**
     * 根据配置生成代码.zip
     * @param generator
     */
    byte[] generator(Generator generator) throws Exception;
}
