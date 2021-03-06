package com.spark.sql

import java.sql.{Connection, DriverManager}

/**
  * Created by ibf on 10/19.
  */
object JDBCSparkThriftServerDemo {
  def main(args: Array[String]): Unit = {
    // 一、添加驱动
    val driver = "org.apache.hive.jdbc.HiveDriver"
    Class.forName(driver)

    var connection: Connection = null

    try {
      // 二、获取connection连接
      val url = "jdbc:hive2://bigdata.server1:10000"
      val username = " "
      val password = " "
      connection = DriverManager.getConnection(url, username, password)

      // 三、获取Statement对象
      // 3.1 切换database
      //connection.prepareStatement("use common").execute()
      // 3.2 语句执行
      val sql = "select eid,ename,salary from test.emp where salary > ?"
      val pstmt = connection.prepareStatement(sql)
      pstmt.setInt(1, 1000)

      // 四、sql执行
      val rs = pstmt.executeQuery()

      // 五、结果获取
      while (rs.next()) {
        val empno = rs.getInt(1)
        val ename = rs.getString("ename")
        val sal = rs.getDouble("salary")

        println(s"${empno}:${ename}:${sal}")
      }
    } finally {
      // 六、关闭连接
      if (connection != null) {
        connection.close()
      }
    }
  }
}
