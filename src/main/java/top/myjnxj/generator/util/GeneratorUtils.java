package top.myjnxj.generator.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Component;
import top.myjnxj.generator.bo.Generator;
import top.myjnxj.generator.conf.GeneratorConf;
import top.myjnxj.generator.constant.VelocityConf;
import top.myjnxj.generator.entity.PrimaryKey;
import top.myjnxj.generator.entity.Table;
import top.myjnxj.generator.entity.TableColumn;
import top.myjnxj.generator.entity.TemplateAttribute;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @ClassName GeneratorUtils
 * @Description TODO
 * @Author JXNJ
 * @Date 2019/1/16 17:38
 * @Version 1.0
 **/
@Component
@Slf4j
public class GeneratorUtils {
    //工程目录
    private static final String ROOT = "springbootjnxj";
    //resources目录
    private static final String RESOUCRCES = ROOT + File.separator + "src" + File.separator + "main" + File.separator + "resources";
    //代码目录
    private static final String JAVA_ROOT = ROOT + File.separator + "src" + File.separator + "main" + File.separator + "java";

    /**
     * 生成相关的zip文件
     *
     * @param tables
     */
    public static byte[] generator(List<Table> tables, Generator generator) throws IOException {
        //字节数组输出流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
        //velocity配置
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.init();


        List<String> templates = getTemplates();
        for (Table table : tables) {
            String className = tableNameToClassName(table.getTableName());
            TemplateAttribute templateAttribute = new TemplateAttribute(generator.getPackageName(), className, tableColumnToJavaAttribute(table.getTableName()));

            //给模板注入属性
            Map<String, String> attributes = new HashMap<>();
            getAttributes(attributes, templateAttribute);

            VelocityContext context = new VelocityContext(attributes);
            PrimaryKey primaryKey=null;
            for (TableColumn tableColumn : table.getTableColumns()) {
                tableColumn.setJavaColumnName(tableColumnToJavaAttribute(tableColumn.getColumnName()));
                tableColumn.setMethodName(tableColumnToMethodName(tableColumn.getColumnName()));
                if ("PRI".equals(tableColumn.getColumnKey())){
                    /*table.setPk(tableColumn.getColumnName());
                    table.setJavaPk(tableColumnToJavaAttribute(table.getPk()));
                    table.setPkMethodName(tableColumnToMethodName(tableColumn.getColumnName()));*/
                    primaryKey=new PrimaryKey(tableColumn.getColumnName(),tableColumnToJavaAttribute(tableColumn.getColumnName()),tableColumnToMethodName(tableColumn.getColumnName()),tableColumn.getJavaType());
                }
            }
            context.put("tableColumns", table.getTableColumns());
           /* context.put("pk",table.getPk());
            context.put("javaPk",table.getJavaPk());
            context.put("pkMethodName",table.getPkMethodName());*/
            context.put("primaryKey",primaryKey);
            if (templates != null && !templates.isEmpty()) {
                for (String templateStr : templates) {

                    Template template = ve.getTemplate(templateStr, "UTF-8");

                    StringWriter stringWriter = new StringWriter();
                    template.merge(context, stringWriter);
                    try {
                        String fileName = getFileName(generator.getPackageName(), className, templateStr);
                        log.info("fileName:{}", fileName);
                        writeTemplate(zipOutputStream, fileName, stringWriter);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // log.info("template:{},{}", template.getName(), stringWriter.toString());
                }
            }

        }
        //生成与表无关的模板
        List<String> staticTemplates = getStaticTemplates();
        VelocityContext context = new VelocityContext();
        //给模板注入属性
        context.put("generator", generator);
        for (String staticTemplate : staticTemplates) {
            Template template = ve.getTemplate(staticTemplate, "UTF-8");

            StringWriter stringWriter = new StringWriter();
            template.merge(context, stringWriter);
            String fileName = getFileName(staticTemplate,generator.getPackageName());
            log.info("fileName:{}", fileName);
            writeTemplate(zipOutputStream, fileName, stringWriter);
        }

        //这里要注意一下流关闭的顺序，先关闭ZipOutputStream，否则会出现不可预料末端的压缩文件的错误
        IOUtils.closeQuietly(zipOutputStream);
        byte[] result = outputStream.toByteArray();
        IOUtils.closeQuietly(outputStream);
        return result;

    }

    private static String tableColumnToMethodName(String javaColumnName) {
        StringBuilder stringBuilder=new StringBuilder("get");
        stringBuilder.append(tableNameToClassName(javaColumnName));
        return stringBuilder.append("()").toString();
    }

    /**
     * 获取文件路径
     *
     * @param staticTemplate
     * @param packageName
     * @return
     */
    private static String getFileName(String staticTemplate, String packageName) {
        StringBuffer stringBuffer = new StringBuffer();
        if (VelocityConf.APPLICATION_YML_VM.equals(staticTemplate)) {
            return stringBuffer.append(RESOUCRCES + File.separator + VelocityConf.APPLICATION_YML).toString();
        } else if (VelocityConf.POM_XML_VM.equals(staticTemplate)) {
            return stringBuffer.append(ROOT + File.separator + VelocityConf.POM_XML).toString();
        } else if (VelocityConf.APPLICATION_VM.equals(staticTemplate)) {
            stringBuffer.append(JAVA_ROOT + File.separator);
            stringBuffer.append(packageName.replace(".",File.separator));
            stringBuffer.append(File.separator);
            return stringBuffer.append(VelocityConf.APPLICATION).toString();
        }else if (VelocityConf.BANNER_VM.equals(staticTemplate)) {
            return stringBuffer.append(RESOUCRCES + File.separator + VelocityConf.BANNER).toString();
        }else{
            return "";
        }

    }

    private static void writeTemplate(ZipOutputStream zipOutputStream, String fileName, StringWriter stringWriter) throws IOException {
        zipOutputStream.putNextEntry(new ZipEntry(fileName));
        zipOutputStream.write(stringWriter.toString().getBytes("UTF-8"));
        zipOutputStream.closeEntry();
        stringWriter.close();
    }

    /**
     * 获取不与表有关的模板
     *
     * @return
     */
    private static List<String> getStaticTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add(VelocityConf.APPLICATION_YML_VM);
        templates.add(VelocityConf.POM_XML_VM);
        templates.add(VelocityConf.APPLICATION_VM);
        templates.add(VelocityConf.BANNER_VM);

        return templates;
    }

    /**
     * 获取文件名
     *
     * @param packageName
     * @param className
     * @param templateStr
     * @return
     */
    private static String getFileName(String packageName, String className, String templateStr) {
        StringBuffer stringBuffer = new StringBuffer(JAVA_ROOT);
        stringBuffer.append(File.separator);
        stringBuffer.append(packageName.replace(".", File.separator));
        stringBuffer.append(File.separator);
        if (VelocityConf.CONTROLLER_VM.equals(templateStr)) {
            stringBuffer.append(VelocityConf.LOW_CONTROLLER);
            stringBuffer.append(File.separator);
            stringBuffer.append(className);
            stringBuffer.append(VelocityConf.CAP_CONTROLLER);
        } else if (VelocityConf.SERVICE_VM.equals(templateStr)) {
            stringBuffer.append(VelocityConf.LOW_SERVICE);
            stringBuffer.append(File.separator);
            stringBuffer.append(className);
            stringBuffer.append(VelocityConf.CAP_SERVICE);
        } else if (VelocityConf.SERVICEIMPL_VM.equals(templateStr)) {
            stringBuffer.append(VelocityConf.LOW_SERVICEIMPL);
            stringBuffer.append(File.separator);
            stringBuffer.append(className);
            stringBuffer.append(VelocityConf.CAP_SERVICEIMPL);
        } else if (VelocityConf.MAPPER_VM.equals(templateStr)) {
            stringBuffer.append(VelocityConf.LOW_MAPPER);
            stringBuffer.append(File.separator);
            stringBuffer.append(className);
            stringBuffer.append(VelocityConf.CAP_MAPPER);
        } else if (VelocityConf.PO_VM.equals(templateStr)){
            stringBuffer.append(VelocityConf.LOW_PO);
            stringBuffer.append(File.separator);
            stringBuffer.append(className);
            //stringBuffer.append(VelocityConf.CAP_PO);
        }else if (VelocityConf.PROVIDER_VM.equals(templateStr)){
            stringBuffer.append(VelocityConf.LOW_MAPPER);
            stringBuffer.append(File.separator);
            stringBuffer.append(VelocityConf.LOW_PROVIDER);
            stringBuffer.append(File.separator);
            stringBuffer.append(className);
            stringBuffer.append(VelocityConf.CAP_PROVIDER);
        }else {

        }

        return stringBuffer.append(".java").toString();
    }

    /**
     * 表名转类名
     *
     * @param tableName
     * @return
     */
    private static String tableNameToClassName(String tableName) {
        if (StringUtils.isNotBlank(tableName)){
            return WordUtils.capitalizeFully(tableName, new char[]{'_'}).replace("_", "");
        }
        return tableName;

    }

    /**
     * 列名转Java成员变量
     *
     * @param Column
     * @return
     */
    private static String tableColumnToJavaAttribute(String Column) {
        String result = WordUtils.capitalizeFully(Column, new char[]{'_'}).replace("_", "");
        return WordUtils.uncapitalize(result);
    }


    private static void getAttributes(Map<String, String> attributes, TemplateAttribute templateAttribute) {
        attributes.put("packageName", templateAttribute.getPackageName());
        attributes.put("ClassName", templateAttribute.getCapClassName());
        attributes.put("className", templateAttribute.getLowClassName());
        attributes.put("now", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
    }

    public static void main(String[] args) {
       // log.info("{}", getFileName("com.java.demo", "Account", "ServiceImpl.java.vm"));
        log.info("{}", tableNameToClassName("account_id"));
    }

    /**
     * 获取模板
     * 当前需要的模板：
     * 1.Controller
     * 2.Service接口
     * 3.ServiceImpl实现类
     * 4.Mapper接口
     *
     * @return
     */
    private static List<String> getTemplates() {
        List<String> templates = new ArrayList<>();
        templates.add(VelocityConf.CONTROLLER_VM);
        templates.add(VelocityConf.SERVICE_VM);
        templates.add(VelocityConf.SERVICEIMPL_VM);
        templates.add(VelocityConf.MAPPER_VM);
        templates.add(VelocityConf.PO_VM);
        templates.add(VelocityConf.PROVIDER_VM);


        return templates;
    }


    /**
     * 数据库字段类型转Java类型
     *
     * @param tableColumns
     */
    public static void dataTypeToJavaType(List<TableColumn> tableColumns, GeneratorConf generatorConf) {
        for (TableColumn tableColumn : tableColumns) {
            tableColumn.setJavaType(generatorConf.getConf().get(tableColumn.getDataType()));
        }
    }
}
