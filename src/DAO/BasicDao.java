package DAO;

import Utils.DruidUtils;
import Utils.Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.util.List;

/**
 * @author lty
 */
public class BasicDao<T> {
    private QueryRunner queryRunner = new QueryRunner();

    public int dml(String sql, Object... params){
        Connection connection = null;
        int affectedRows = 0;
        try {
            connection = DruidUtils.getConnection();
            affectedRows = queryRunner.update(connection, sql, params);
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            DruidUtils.close(null, null, connection);
        }
        return affectedRows;
    }
    public List<T> queryMulti(String sql, Class<T> cls,Object... params){
        Connection connection = null;
        List<T> queryGet;
        try{
            connection = DruidUtils.getConnection();
            queryGet = queryRunner.query(connection, sql, new BeanListHandler<>(cls),params);
        } catch (Exception e){
            throw new RuntimeException(e);
        } finally {
            DruidUtils.close(null, null, connection);
        }
        return queryGet;
    }

    public T querySingle(String sql, Class<T> cls, Object... params){
        Connection connection;
        T queryGet;
        try{
            connection = DruidUtils.getConnection();
            queryGet = queryRunner.query(connection, sql, new BeanHandler<>(cls), params);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        return queryGet;
    }
    public Object queryScalar(String sql, Object... params){
        Connection connection;
        Object queryGet;
        try{
            connection = DruidUtils.getConnection();
            queryGet = queryRunner.query(connection, sql, new ScalarHandler<>(), params);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        return queryGet;
    }
}
