package com.mycompany.api.preparedstatement;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * 使用预编译的statement对象实现用户登
 */

public class PSUserLogin {

  private static final String URL = "jdbc:mysql://localhost:3306/main?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
  private static final String USERNAME = "root";
  private static final String PASSWORD = "123456";

  public static void main(String[] args) throws ClassNotFoundException, SQLException, UnsupportedEncodingException {
    // 1.收集用户信息
    Scanner sc = new Scanner(System.in);
    // 设置输出流的编码格式
    PrintStream out = new PrintStream(System.out, true, "GBK");
    out.println("请输入用户名：");
    String username = sc.nextLine();
    out.println("请输入用户密码：");
    String password = sc.nextLine();

    sc.close();

    // 2.调用方法登录
    boolean flag = login(username, password);

    // 3.判断是否登录成功
    if (flag) {
      out.println("登录成功！");
    } else {
      out.println("登录失败！");
    }
    out.close();

  }

  public static boolean login(String uname, String pswd) throws ClassNotFoundException, SQLException {
    // 注册驱动
    Class.forName("com.mysql.cj.jdbc.Driver");
    // 连接数据库
    Connection ct = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    // 应用预编译的statement对象，使用?占位符作为变量参数
    String sql = "select * from user where username = ? and password = ?";
    PreparedStatement ps = ct.prepareStatement(sql);
    // 设置占位符参数的值
    ps.setString(1, uname);
    ps.setString(2, pswd);
    // 执行sql语句（此时不需要传入sql语句，因为前面已经设置了占位符）
    ResultSet rs = ps.executeQuery();

    // 处理结果集
    return rs.next();
  }
}
