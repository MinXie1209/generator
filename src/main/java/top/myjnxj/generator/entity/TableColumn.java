package top.myjnxj.generator.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName TableColumn
 * @Description 封装数据表的列
 * @Author JXNJ
 * @Date 2019/1/16 17:23
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableColumn {
    /**
     * 列名
     */
    private String columnName;
    /**
     * Java成员变量名
     */
    private String javaColumnName;
    /**
     * get方法
     */
    private String methodName;
    /**
     * 数据库的列类型
     */
    private String dataType;
    /**
     * 列说明信息
     */
    private String columnComment;
    /**
     * 键：PRI-主键
     */
    private String columnKey;
    /**
     * java类型
     */
    private String javaType;
    public TableColumn(String columnName,String dataType,String columnComment,String columnKey){
        this.columnName=columnName;
        this.dataType=dataType;
        this.columnComment=columnComment;
        this.columnKey=columnKey;
    }
}
