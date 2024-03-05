package Service;

import DAO.DiningTableDAO;
import Domain.DiningTable;

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

    public String checkTable(int id){
        sql = "select state from diningTable where id = ?";
        return (String) dao.queryScalar(sql, id);
    }

    public void alterTable(int id, String name, String tel){
        sql = "update diningTable set state = '满', orderName = ?, orderTel = ? where id = ?";
        dao.dml(sql, name, tel, id);
    }
}
