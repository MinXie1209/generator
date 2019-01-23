package top.myjnxj.generator.constant;

import java.io.File;

/**
 * @ClassName VelocityConf
 * @Description TODO
 * @Author JXNJ
 * @Date 2019/1/17 11:44
 * @Version 1.0
 **/
public class VelocityConf {
    public static final String TEMPLATE = "template";

    public static final String CONTROLLER_VM = TEMPLATE + File.separator + "Controller.java.vm";
    public static final String SERVICE_VM = TEMPLATE + File.separator + "Service.java.vm";
    public static final String SERVICEIMPL_VM = TEMPLATE + File.separator + "ServiceImpl.java.vm";
    public static final String MAPPER_VM = TEMPLATE + File.separator + "Mapper.java.vm";
    public static final String PO_VM = TEMPLATE + File.separator + "PO.java.vm";
    public static final String PROVIDER_VM = TEMPLATE + File.separator + "Provider.java.vm";

    public static final String APPLICATION_YML_VM = TEMPLATE + File.separator + "application.yml.vm";
    public static final String POM_XML_VM = TEMPLATE + File.separator + "pom.xml.vm";
    public static final String APPLICATION_VM = TEMPLATE + File.separator + "Application.java.vm";
    public static final String BANNER_VM = TEMPLATE + File.separator + "banner.txt.vm";

    public static final String PAGE_VM = TEMPLATE + File.separator + "Page.java.vm";
    public static final String SWAGGER2_VM = TEMPLATE + File.separator + "Swagger2.java.vm";
    public static final String RESULTENUM_VM = TEMPLATE + File.separator + "ResultEnum.java.vm";
    public static final String EXCEPTIONCONTROLLERADVICE_VM = TEMPLATE + File.separator + "ExceptionControllerAdvice.java.vm";
    public static final String RESULTEXCEPTION_VM = TEMPLATE + File.separator + "ResultException.java.vm";
    public static final String RESULTUTILS_VM = TEMPLATE + File.separator + "ResultUtils.java.vm";
    public static final String TOKENUTILS_VM = TEMPLATE + File.separator + "TokenUtils.java.vm";
    public static final String RESULT_VM = TEMPLATE + File.separator + "Result.java.vm";
}
