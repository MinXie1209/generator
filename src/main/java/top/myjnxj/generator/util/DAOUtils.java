package top.myjnxj.generator.util;


import lombok.extern.slf4j.Slf4j;
import top.myjnxj.generator.bo.Generator;
import top.myjnxj.generator.common.enums.ResultEnum;
import top.myjnxj.generator.common.exception.ResultException;
import top.myjnxj.generator.entity.Table;
import top.myjnxj.generator.entity.TableColumn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DAOUtils {
    private static final String DRVIER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String QUERY_TABLE = "SELECT table_name tableName FROM information_schema.tables WHERE table_schema=? and table_type='base table'";
    private static final String QUERY_TABLECOLUMN = "SELECT column_name columnName, data_type dataType, column_comment columnComment, column_key columnKey " +
            "FROM information_schema.columns WHERE table_name = ? AND table_schema = (SELECT DATABASE()) ORDER BY ordinal_position";

    public static void queryTableAndTableColumns(Generator generator, List<Table> tables) throws Exception {
        try {
            Class.forName(DRVIER_CLASS);
            Connection connection = DriverManager.getConnection(generator.getUrl(), generator.getUserName(), generator.getPassword());
            getTables(connection, tables, generator.getDataBase());

        } catch (ClassNotFoundException e) {
            throw  new ResultException(ResultEnum.DATABASE_ERROR);
        } catch (SQLException e) {
            throw  new ResultException(ResultEnum.DATABASE_ERROR);
        }

    }

    private static void getTables(Connection connection, List<Table> tables, String dataBase) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement=connection.prepareStatement(QUERY_TABLE);
            preparedStatement.setString(1,dataBase);
            resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
               Table table=new Table();
                //converTable(table,resultSet);
                table.setTableName(resultSet.getString("tableName"));
                tables.add(table);
            }
            for (Table table:tables){
                getTableColumns(connection,table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(preparedStatement, resultSet);
            closeConnection(connection);

        }
    }

    private static void closeConnection(Connection connection) throws SQLException {
        if(connection!=null){
            connection.close();
        }

    }

    private static void getTableColumns(Connection connection,Table table) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement=connection.prepareStatement(QUERY_TABLECOLUMN);
            preparedStatement.setString(1,table.getTableName());
            resultSet=preparedStatement.executeQuery();
            List<TableColumn> columnList=new ArrayList<>();
            while (resultSet.next()) {
                converTableColumns(columnList,resultSet);
            }
            table.setTableColumns(columnList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void converTableColumns(List<TableColumn> columnList, ResultSet resultSet)throws SQLException {
        TableColumn tableColumn=new TableColumn();
        tableColumn.setColumnName(resultSet.getString("columnName"));
        tableColumn.setMethodName("");
        tableColumn.setDataType(resultSet.getString("dataType"));
        tableColumn.setColumnComment(resultSet.getString("columnComment"));
        tableColumn.setColumnKey(resultSet.getString("columnKey"));
        tableColumn.setJavaType("");
        columnList.add(tableColumn);

    }

    private static void converTable(Table table, ResultSet resultSet)throws SQLException {
        table.setTableName(resultSet.getString("tableName"));
    }

    private static void closeQuietly(PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Generator generator = new Generator();

    }
}
