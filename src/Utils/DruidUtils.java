package Utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author lty
 * Druid工具类，方便直接获取数据库连接和断开连接
 */
public class DruidUtils {
    private static final DataSource ds;

    static {
        Properties info = new Properties();
        try {
            info.load(new FileInputStream("src\\druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(info);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        Connection connection;
        try {
            connection = ds.getConnection();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return connection;
    }

    // 连接池中close方法不是真正断掉连接，而是把使用的Connection对象放回连接池
    public static void close(ResultSet resultSet, Statement statement, Connection connection){
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
