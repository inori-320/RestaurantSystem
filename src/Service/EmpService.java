package Service;

import DAO.EmpDAO;
import Domain.Employee;

/**
 * @author lty
 * 完成对emp表的各种操作（通过调用EmpDAO）
 */
public class EmpService {
    private final EmpDAO empDAO = new EmpDAO();

    public boolean registerEmp(String empId, String name, String pwd, String job){
        String sql = "insert into emp values (NULL, ?, MD5(?), ?, ?)";
        try{
            empDAO.dml(sql, empId, pwd, name, job);
            return true;
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public Employee getEmp(String empId, String pwd){
        String sql = "select * from emp where empId = ? and pwd = md5(?)";
        return empDAO.querySingle(sql, Employee.class, empId, pwd);
    }
}
