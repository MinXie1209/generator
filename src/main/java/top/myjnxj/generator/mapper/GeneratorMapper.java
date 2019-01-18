package top.myjnxj.generator.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;
import top.myjnxj.generator.entity.Table;
import top.myjnxj.generator.entity.TableColumn;

import java.util.List;

/**
 * @InterfaceName GeneratorMapper
 * @Description TODO
 * @Author JXNJ
 * @Date 2019/1/16 17:18
 * @Version 1.0
 **/
@Mapper
public interface GeneratorMapper {
    @Select("SELECT table_name tableName FROM information_schema.tables WHERE table_schema='wechat' AND table_type='base table'")
    List<Table> listTable(String dataBase);
    @Select("SELECT column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey FROM information_schema.columns" +
            " WHERE table_name = '${table.tableName}' AND table_schema = (SELECT DATABASE()) ORDER BY ordinal_position")
    List<TableColumn> listTableColumn(@Param("table") Table table);
}
