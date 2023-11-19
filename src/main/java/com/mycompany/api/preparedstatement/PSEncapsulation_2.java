package com.mycompany.api.preparedstatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.ResultSetMetaData;

public class PSEncapsulation_2 {

  private static final String URL = "jdbc:mysql://localhost:3306/main?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "123456";

  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    // 封装请求到的ResultSet对象，一般情况封装为List<Map>，HashMap的key/value对应列名/列值，list的每个元素对应一行数据
    Class.forName("com.mysql.cj.jdbc.Driver");

    // 连接数据库
    Connection ct = DriverManager.getConnection(URL, USERNAME, PASSWORD);

    // 创建sql并执行
    String sql = "select id,username,password,nickname from user";
    PreparedStatement ps = ct.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();

    // 封装结果集
    // 定义一个MapList
    List<Map> list = new ArrayList<>();

    // 获取结果集的元数据
    ResultSetMetaData rsmd = rs.getMetaData();
    // 获取结果集的列数
    int columnCount = rsmd.getColumnCount();
    // 遍历结果集
    while (rs.next()) {
      // 定义一个Map
      Map<String, Object> map = new HashMap<>();
      // 遍历每一列
      for (int i = 1; i <= columnCount; i++) {
        // 获取列名
        String columnLabel = rsmd.getColumnLabel(i);
        // 获取列值
        Object columnValue = rs.getObject(columnLabel);
        // 将列名和列值封装到Map中
        map.put(columnLabel, columnValue);
      }
      // 将每一行的数据封装到List中
      list.add(map);
    }

    System.out.println(list);
    // 释放资源
    rs.close();
    ps.close();
    ct.close();
  }
}
