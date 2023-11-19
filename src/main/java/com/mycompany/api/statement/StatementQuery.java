package com.mycompany.api.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Author 月花
 *         Description: 用于测试statement查询语句方式
 */
/*
 * TODO:
 * 1. 注册驱动
 * 2. 获取连接
 * 3. 获取statement对象
 * 4. 执行sql语句
 * 5. 处理结果集
 * 6. 释放资源
 */
public class StatementQuery {
  private static final String URL = "jdbc:mysql://localhost:3306/main?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
  private static final String USER = "root";
  private static final String PASSWORD = "123456";

  public static void main(String[] args) throws SQLException {
    /**
     * 1.注册驱动
     * 对于驱动版本为8+的：Driver来自于com.mysql.cj.jdbc.Driver
     * 对于驱动版本为5+的：Driver来自于com.mysql.jdbc.Driver
     */
    // 反射注册驱动（原理是Driver类中有一个静态代码块会自动注册驱动，加载类时会执行静态代码块内容）
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // 2.获取连接
    Connection ct = DriverManager.getConnection(URL, USER, PASSWORD);
    // 3.获取statement对象
    Statement sm = ct.createStatement();
    // 4.执行sql语句
    String querySql = "select * from user;";
    ResultSet resultSet = sm.executeQuery(querySql);
    // 5.处理结果集
    // next方法可以将光标移动到下一行，如果有数据返回true，否则返回false
    while (resultSet.next()) {
      // 通过getString并传入对应列名或对应列的下标来获取数据（下标从1开始）
      String uname = resultSet.getString("username");
      String pwd = resultSet.getString(5); // 这是password的下标位置
      System.out.println(uname + " -- " + pwd);
    }
    // 6.释放资源
    // 6.1.先释放resultSet
    resultSet.close();
    // 6.2.再释放statement
    sm.close();
    // 6.3.最后释放connection
    ct.close();
  }
}
