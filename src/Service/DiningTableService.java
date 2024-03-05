package Service;

import DAO.DiningTableDAO;
import Domain.DiningTable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lty
 */
public class DiningTableService {
    private final DiningTableDAO dao = new DiningTableDAO();
    private String sql;

    public List<DiningTable> getAllTable(){
        sql = "select id, state from diningTable";
        return dao.queryMulti(sql, DiningTable.class);
    }

    public List<Integer> getOrdered(String name){
        sql = "select id from diningTable where orderName = ?";
        List<DiningTable> diningTables = dao.queryMulti(sql, DiningTable.class, name);
        List<Integer> tables = new ArrayList<>();
        for(DiningTable t: diningTables){
            tables.add(t.getId());
        }
        return tables;
    }

    public String checkTable(int id){
        sql = "select state from diningTable where id = ?";
        return (String) dao.queryScalar(sql, id);
    }

    public void alterTable(int id, String state, String name, String tel){
        sql = "update diningTable set state = ?, orderName = ?, orderTel = ? where id = ?";
        dao.dml(sql, state, name, tel, id);
    }
}
