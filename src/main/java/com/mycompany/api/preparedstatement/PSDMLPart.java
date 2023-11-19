package com.mycompany.api.preparedstatement;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.io.PrintStream;
import java.sql.Connection;

/**
 * 测试PS的DML操作（增删改）
 */

public class PSDMLPart {

  private static final String URL = "jdbc:mysql://localhost:3306/main?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "123456";

  // 设置输出流的编码格式
  static PrintStream out;
  static Connection ct;

  static {
    try {
      // 注册驱动
      Class.forName("com.mysql.cj.jdbc.Driver");
      // 连接数据库
      ct = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      // 设置输出流的编码格式
      out = new PrintStream(System.out, true, "GBK");
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    try {
      // 测试插入用户数据
      insert();
      // 测试更新用户数据
      update();
      // 测试删除用户数据
      delete();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      // 释放资源
      ct.close();
    }
  }

  // 插入用户数据
  public static void insert() throws SQLException {
    // 创建ps
    // 创建sql语句
    String sql = "insert into user (username,password,updatedAt,avatar,nickname) values(?,?,?,?,?)";
    // 创建ps对象
    PreparedStatement ps = ct.prepareStatement(sql);

    // 设置占位符参数的值
    LocalDateTime time = LocalDateTime.now();
    ps.setString(1, "zhangsan");
    ps.setString(2, "123456");
    ps.setString(3, time.toString());
    ps.setString(4,
        "https://sears-2000.oss-cn-shanghai.aliyuncs.com/wechat_app/wecaht_public/register-user-avatar-1682235934293.png");
    ps.setString(5, "zhangsan");
    // 执行sql
    int rows = ps.executeUpdate();

    // 判断是否插入成功
    if (rows > 0) {
      out.println("插入用户成功！");
    } else {
      out.println("插入用户失败！");
    }

    // 释放资源
    ps.close();
  }

  // 更新用户数据
  public static void update() throws SQLException {
    // 创建ps
    // 创建sql语句
    String sql = "update user set password = ? where username = ?";
    // 创建ps对象
    PreparedStatement ps = ct.prepareStatement(sql);

    // 设置占位符参数的值
    ps.setString(1, "654321");
    ps.setString(2, "zhangsan");

    // 执行sql
    int rows = ps.executeUpdate();

    // 判断是否更新成功
    if (rows > 0) {
      out.println("更新用户成功！");
    } else {
      out.println("更新用户失败！");
    }

    // 释放资源
    ps.close();
  }

  // 删除用户数据
  public static void delete() throws SQLException {
    // 创建ps
    // 创建sql语句
    String sql = "delete from user where username = ?";
    // 创建ps对象
    PreparedStatement ps = ct.prepareStatement(sql);

    // 设置占位符参数的值
    ps.setString(1, "zhangsan");

    // 执行sql
    int rows = ps.executeUpdate();

    // 判断是否更新成功
    if (rows > 0) {
      out.println("删除用户成功！");
    } else {
      out.println("删除用户失败！");
    }

    // 释放资源
    ps.close();
  }
}