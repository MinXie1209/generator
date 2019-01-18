package top.myjnxj.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @ClassName Table
 * @Description 封装数据表
 * @Author JXNJ
 * @Date 2019/1/16 17:21
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Table {
    private String tableName;
    private List<TableColumn> tableColumns;
    private String pk;
    private String javaPk;
    public Table(String tableName){
        this.tableName=tableName;
    }
}
