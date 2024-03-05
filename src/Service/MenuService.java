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
}
