package top.myjnxj.generator.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.myjnxj.generator.bo.Generator;
import top.myjnxj.generator.conf.GeneratorConf;
import top.myjnxj.generator.entity.Table;
import top.myjnxj.generator.entity.TableColumn;
import top.myjnxj.generator.mapper.GeneratorMapper;
import top.myjnxj.generator.service.GeneratorService;
import top.myjnxj.generator.util.GeneratorUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipOutputStream;

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
    GeneratorMapper generatorMapper;
    @Autowired
    GeneratorConf generatorConf;

    /**
     * 1.获取表
     * 2.遍历表，获取属性
     * 3.生成代码-Velocity
     * @param generator
     */
    @Override
    public byte[] generator(Generator generator) throws IOException {

        List<Table> tables=null;
        synchronized (this){
            //TODO 连接数据库
            //通过数据库名获取表以及表信息
           tables= generatorMapper.listTable(generator.getDataBase());

           if(tables!=null&&!tables.isEmpty()){
               for (Table table:tables){
                   log.info("{}",table.toString());
                   List<TableColumn>tableColumns=generatorMapper.listTableColumn(table);
                   GeneratorUtils.dataTypeToJavaType(tableColumns,generatorConf);
                   table.setTableColumns(tableColumns);
               }
           }
       }



        return  GeneratorUtils.generator(tables,generator);
    }


}
