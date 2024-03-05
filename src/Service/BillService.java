package Service;

import DAO.BillDAO;
import Domain.Bill;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lty
 */
public class BillService {
    private final BillDAO dao = new BillDAO();
    private String sql;

    public void setBill(String name, int foodsId, int foodsNum, int tableId){
        sql = "insert into bill value(NULL, ?, ?, ?, ?, now())";
        double p = new MenuService().getPrice(foodsId);
        dao.dml(sql, foodsId, foodsNum, foodsNum * p, tableId);
        new DiningTableService().alterTable(tableId, "就餐中", name, "");
    }

    public List<Bill> getBillByUser(String name){
        List<Integer> tableId = new DiningTableService().getOrdered(name);
        sql = "select * from bill where tableId = ?";
        List<Bill> all = new ArrayList<>();
        for(int t: tableId) {
           List<Bill> tmp = dao.queryMulti(sql, Bill.class, t);
           all.addAll(tmp);
        }
        return all;
    }

    public void dropBill(int tableId){
        sql = "delete from bill where tableId = ?";
        dao.dml(sql, tableId);
        new DiningTableService().alterTable(tableId, "空", "", "");
    }
}
