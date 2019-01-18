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
    public static final String TEMPLATE="template";

    public static final String CONTROLLER_VM=TEMPLATE+File.separator+"Controller.java.vm";
    public static final String CAP_CONTROLLER="Controller";
    public static final String LOW_CONTROLLER="controller";

    public static final String SERVICE_VM=TEMPLATE+File.separator+"Service.java.vm";
    public static final String CAP_SERVICE="Service";
    public static final String LOW_SERVICE="service";

    public static final String SERVICEIMPL_VM=TEMPLATE+File.separator+"ServiceImpl.java.vm";
    public static final String CAP_SERVICEIMPL="ServiceImpl";
    public static final String LOW_SERVICEIMPL="service"+ File.separator+"impl";

    public static final String MAPPER_VM=TEMPLATE+File.separator+"Mapper.java.vm";
    public static final String CAP_MAPPER="Mapper";
    public static final String LOW_MAPPER="mapper";

    public static final String PO_VM=TEMPLATE+File.separator+"PO.java.vm";
   // public static final String CAP_PO="po";
    public static final String LOW_PO="entity";

    public static final String APPLICATION_YML_VM = TEMPLATE+File.separator+"application.yml.vm";
    public static final String APPLICATION_YML = "application.yml";


    public static final String POM_XML_VM = TEMPLATE+File.separator+"pom.xml.vm";
    public static final String POM_XML = "pom.xml";

    public static final String APPLICATION_VM = TEMPLATE+File.separator+"Application.java.vm";
    public static final String APPLICATION = "Application.java";
    public static final String BANNER_VM = TEMPLATE+File.separator+"banner.txt.vm";
    public static final String BANNER = "banner.txt";

}
