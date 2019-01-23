package top.myjnxj.generator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.myjnxj.generator.bo.Generator;
import top.myjnxj.generator.conf.GeneratorConf;
import top.myjnxj.generator.conf.TemplateConf;
import top.myjnxj.generator.entity.Table;

import top.myjnxj.generator.entity.TableColumn;
import top.myjnxj.generator.service.GeneratorService;
import top.myjnxj.generator.util.DAOUtils;
import top.myjnxj.generator.util.GeneratorUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GeneratorServiceImpl
 * @Description TODO
 * @Author JXNJ
 * @Date 2019/1/16 15:49
 * @Version 1.0
 **/
@Service
@Slf4j
public class GeneratorServiceImpl implements GeneratorService {
    @Autowired
    GeneratorConf generatorConf;
    @Autowired
    TemplateConf templateConf;

    /**
     * 1.获取表
     * 2.遍历表，获取属性
     * 3.生成代码-Velocity
     * @param generator
     */
    @Override
    public byte[] generator(Generator generator) throws IOException {

        List<Table> tables=new ArrayList<>();
        synchronized (this){
            //TODO 连接数据库
            DAOUtils.queryTableAndTableColumns(generator,tables);
            templateConf.replace(generator.getPackageName());
           for (Table table:tables){
               GeneratorUtils.dataTypeToJavaType(table.getTableColumns(),generatorConf);
               //log.info("{}",table.getTableName());
               for (TableColumn tableColumn:table.getTableColumns()){
                 //  log.info("{}",tableColumn.getColumnName());
               }
           }
       }



        return  GeneratorUtils.generator(tables,generator,templateConf);
    }


}
