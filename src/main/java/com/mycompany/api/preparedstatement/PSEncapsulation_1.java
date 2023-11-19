package com.mycompany.api.preparedstatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PSEncapsulation_1 {

  private static final String URL = "jdbc:mysql://localhost:3306/main?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "123456";

  public static void main(String[] args) throws ClassNotFoundException, SQLException {

    // 封装请求到的ResultSet对象，一般情况封装为List<Map>，Map的key/value对应列名/列值，list的每个元素对应一行数据

    Class.forName("com.mysql.cj.jdbc.Driver");

    // 连接数据库
    Connection ct = DriverManager.getConnection(URL, USERNAME, PASSWORD);

    // 创建sql
    String sql = "select id,username,password,nickname from user";
    PreparedStatement ps = ct.prepareStatement(sql);

    ResultSet rs = ps.executeQuery();
    // 封装结果集
    // 1.定义一个MapList
    List<Map> list = new ArrayList<>();
    // 2.遍历结果集
    while (rs.next()) {
      // 3.获取每一行的数据
      int id = rs.getInt("id");
      String username = rs.getString("username");
      String password = rs.getString("password");
      String nickname = rs.getString("nickname");
      // 4.将每一行的数据封装到Map中
      Map<String, Object> map = Map.of("id", id, "username", username, "password", password, "nickname", nickname);
      // 5.将每一行的数据封装到List中
      list.add(map);
    }

    System.out.println(list);

    // 释放资源
    rs.close();
    ps.close();
    ct.close();
  }
}
