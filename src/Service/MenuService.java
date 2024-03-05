package Service;

import DAO.MenuDAO;
import Domain.Menu;

import java.util.List;

/**
 * @author lty
 */
public class MenuService {
    private MenuDAO dao = new MenuDAO();
    private String sql;

    public List<Menu> getAllFoods(){
        sql = "select * from menu";
        return dao.queryMulti(sql, Menu.class);
    }

    public Menu getMenuById(int id){
        sql = "select * from menu where id = ?";
        return dao.querySingle(sql, Menu.class, id);
    }

    public double getPrice(int id){
        return (Double)dao.queryScalar("select price from menu where id = ?", id);
    }
}
