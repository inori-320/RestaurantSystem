package View;

import java.util.*;

import Domain.Bill;
import Domain.DiningTable;
import Domain.Employee;
import Domain.Menu;
import Service.BillService;
import Service.DiningTableService;
import Service.EmpService;
import Service.MenuService;
import Utils.Utils;

/**
 * @author lty
 * 主界面
 */
public class View {
    private boolean isLoop = true;
    private String input;
    private String empId;
    private String pwd;
    Employee employee = null;
    private final EmpService empService = new EmpService();
    private final DiningTableService diningTableService = new DiningTableService();
    private final MenuService menuService = new MenuService();
    private final BillService billService = new BillService();

    private void mainMenu(){
        while(isLoop) {
            System.out.println("===============欢迎来到餐厅服务系统================");
            System.out.println("\t \t 1.餐厅会员注册");
            System.out.println("\t \t 2.餐厅会员登录");
            System.out.println("\t \t 9.退出系统");
            System.out.println("================================================");
            System.out.print("请输入您想选择的功能（1、2、9）：");
            input = Utils.readString(1);
            switch(input){
                case "1":
                    register();
                    break;
                case "2":
                    login();
                    break;
                case "9":
                    System.out.println("正在退出系统......");
                    this.isLoop = false;
                    break;
                default:
                    System.out.println("输入有误，请重新输入。");
                    break;
            }
        }
    }

    private void register(){
        System.out.print("请输入账号：");
        empId = Utils.readString(100);
        System.out.print("请输入用户名：");
        String name = Utils.readString(100);
        System.out.print("请输入密码：");
        pwd = Utils.readString(100);
        System.out.print("请输入职位（顾客请填写会员）：");
        String job = Utils.readString(100);
        if(empService.registerEmp(empId, name, pwd, job)){
            System.out.println("注册成功！");
        } else {
            System.out.println("注册失败，账号已存在！");
        }
    }

    private void login(){
        System.out.print("请输入员工号：");
        empId = Utils.readString(100);
        System.out.print("请输入密  码：");
        pwd = Utils.readString(100);
        employee = empService.getEmp(empId, pwd);
        if(employee != null) {
            System.out.println(employee.getName() + "  登陆成功！");
            secondMenu();
            this.isLoop = false;
        } else {
            System.out.println("登录失败，用户名或密码错误！");
        }
    }

    private void secondMenu(){
        boolean isLoop_1 = true;
        while(isLoop_1) {
            System.out.println("================餐厅服务系统主界面=================");
            System.out.println("\t \t 1.显示餐桌状态");
            System.out.println("\t \t 2.预订餐桌");
            System.out.println("\t \t 3.查看所有菜品");
            System.out.println("\t \t 4.点餐");
            System.out.println("\t \t 5.查看账单");
            System.out.println("\t \t 6.结账");
            System.out.println("\t \t 9.退出系统");
            System.out.println("================================================");
            System.out.println("如果要使用多张餐桌请预订，不预定默认只使用一张餐桌！");
            System.out.print("请输入您想选择的功能（1、2、3、4、5、6、9）：");
            input = Utils.readString(1);
            switch(input){
                case "1":
                    showTables();
                    break;
                case "2":
                    orderTable();
                    break;
                case "3":
                    showAllFoods();
                    break;
                case "4":
                    orderFoods();
                    break;
                case "5":
                    showPrice();
                    break;
                case "6":
                    payFoods();
                    break;
                case "9":
                    if(billService.getBillByUser(employee.getName()).isEmpty()) {
                        System.out.println("欢迎下次光临...");
                        isLoop_1 = false;
                    } else {
                        System.out.println("您还没有付钱！请先结账后再退出。");
                    }
                    break;
                default:
                    System.out.println("输入有误，请重新输入！");
                    break;
            }
        }
    }

    private void showTables(){
        System.out.println("\n餐桌编号\t\t餐桌状态");
        List<DiningTable> tables = diningTableService.getAllTable();
        for(DiningTable d: tables){
            System.out.println(d);
        }
    }

    private void orderTable(){
        System.out.print("请选择您要预订的餐桌号(0退出)：");
        int id = Utils.readInt();
        if(id > 0){
            String ret = diningTableService.checkTable(id);
            if(ret == null) {
                System.out.println("预订失败！餐桌号超过最大数量限制！请按照已有的餐桌号进行选择！");
            } else if(ret.equals("被预定")) {
                System.out.println("预订失败！餐桌已有人预订！");
            } else if(ret.equals("就餐中")){
                System.out.println("预订失败！餐桌已有人在就餐！");
            } else {
                System.out.println("是否确认预订(y/n):");
                String entry = Utils.readString(1).toLowerCase();
                switch (entry){
                    case "y":
                        System.out.println("预订人电话：");
                        String tel = Utils.readString(20);
                        System.out.println("预订成功！");
                        diningTableService.alterTable(id,"被预定", employee.getName(), tel);
                        break;
                    case "n":
                        System.out.println("取消预订");
                        break;
                    default:
                        System.out.println("输入有误，请确认！");
                }
            }
        } else {
            System.out.println("退出预订！");
        }
    }

    private void showAllFoods(){
        List<Menu> foods = menuService.getAllFoods();
        System.out.println("菜品编号\t\t菜品名\t\t类型\t\t\t价格");
        for(Menu m: foods){
            System.out.println(m);
        }
    }

    private void orderFoods(){
        List<Integer> tables = diningTableService.getOrdered(employee.getName());
        if(!tables.isEmpty()){
            System.out.println("您已预订或正在使用桌号！请点餐");
        } else {
            System.out.println("请选择点餐的桌号:");
            int tableId = Utils.readInt();
            if(diningTableService.checkTable(tableId) != null && !Objects.equals(diningTableService.checkTable(tableId), "被预定")
                    && !Objects.equals(diningTableService.checkTable(tableId), "就餐中")) {
                tables.add(tableId);
            } else if (diningTableService.checkTable(tableId) == null){
                System.out.println("该餐桌号不存在！！");
                return;
            } else {
                System.out.println("该餐桌已有人预订或使用！");
                return;
            }
        }
        System.out.print("请选择需要点的菜品编号:");
        int foodsId = Utils.readInt();
        if(menuService.getMenuById(foodsId) == null) {
            System.out.println("菜品不存在！");
            return;
        }
        System.out.print("请选择需要点的菜品数量:");
        int foodsNum = Utils.readInt();
        System.out.println("是否确认点菜(y/n):");
        String entry = Utils.readString(1).toLowerCase();
        switch (entry){
            case "y":
                System.out.println("预订成功！");
                for (Integer table : tables) {
                    billService.setBill(employee.getName(), foodsId, foodsNum, table);
                }
                break;
            case "n":
                System.out.println("取消点菜");
                break;
            default:
                System.out.println("输入有误，请确认！");
        }
    }

    private void showPrice(){
        List<Bill> allBill = billService.getBillByUser(employee.getName());
        System.out.println("编号\t\t\t菜品号\t\t\t数量\t\t\t金额\t\t\t桌号\t\t\t日期");
        for(Bill b: allBill){
            System.out.println(b);
        }
    }
    private int getPrice(List<Bill> bills, int tableId){
        int price = 0;
        for(Bill b: bills){
            if(b.getTableId() == tableId) price += b.getPrice();
        }
        return price;
    }

    private HashSet<Integer> getOrderedTable(List<Bill> bills){
        HashSet<Integer> tables = new HashSet<>();
        for(Bill b: bills){
            tables.add(b.getTableId());
        }
        return tables;
    }

    private void payFoods(){
        List<Bill> allBill = billService.getBillByUser(employee.getName());
        Set<Integer> tables = getOrderedTable(allBill);
        if(tables.isEmpty()){
            System.out.println("您现在无需结账！");
            return;
        }
        System.out.print("请选择您要结账的餐桌号：");
        int tableId = Utils.readInt();
        if(!tables.contains(tableId)){
            System.out.println("餐桌号输入错误！请确认");
            return;
        }
        System.out.print("需要支付的金额为：" + getPrice(allBill, tableId) + "\n");
        System.out.print("是否确认支付(y/n):");
        String entry = Utils.readString(1).toLowerCase();
        switch (entry){
            case "y":
                System.out.println("支付成功！");
                billService.dropBill(tableId);
                break;
            case "n":
                System.out.println("取消支付");
                break;
            default:
                System.out.println("输入有误，请确认！");
        }
    }

    public static void main(String[] args){
        new View().mainMenu();
    }
}