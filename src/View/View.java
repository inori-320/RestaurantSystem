package View;

import java.util.List;
import java.util.Scanner;

import DAO.DiningTableDAO;
import Domain.DiningTable;
import Domain.Employee;
import Service.DiningTableService;
import Service.EmpService;
import Utils.Utils;

/**
 * @author lty
 * 主界面
 */
public class View {
    private boolean isLoop = true;
    private String input;
    private String empId;
    private String name;
    private String pwd;
    private String job;
    private boolean isPay = true;
    Employee employee = null;
    private final EmpService empService = new EmpService();
    private final DiningTableService diningTableService = new DiningTableService();
    private final Scanner scanf = new Scanner(System.in);

    private void mainMenu() throws Exception {
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

    private void register() throws Exception {
        System.out.print("请输入账号：");
        empId = Utils.readString(100);
        System.out.print("请输入用户名：");
        name = Utils.readString(100);
        System.out.print("请输入密码：");
        pwd = Utils.readString(100);
        System.out.print("请输入职位（顾客请填写会员）：");
        job = Utils.readString(100);
        if(empService.registerEmp(empId, name, pwd, job)){
            System.out.println("注册成功！");
        } else {
            System.out.println("注册失败，账号已存在！");
        }
    }

    private void login() throws Exception {
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

    private void secondMenu() throws Exception {
        boolean isLoop_1 = true;
        while(isLoop_1) {
            System.out.println("================网络通信系统主界面=================");
            System.out.println("\t \t 1.显示餐桌状态");
            System.out.println("\t \t 2.预订餐桌");
            System.out.println("\t \t 3.查看所有菜品");
            System.out.println("\t \t 4.点餐");
            System.out.println("\t \t 5.查看账单");
            System.out.println("\t \t 6.结账");
            System.out.println("\t \t 9.退出系统");
            System.out.println("================================================");
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
                    isPay = false;
                    break;
                case "5":
                    showPrice();
                case "6":
                    payFoods();
                    isPay = true;
                case "9":
                    if(isPay) {
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
                System.out.print("预订失败！餐桌号超过最大数量限制！请按照已有的餐桌号进行选择！");
            } else if(ret.equals("满")) {
                System.out.print("预订失败！餐桌已有人预订！");
            } else {
                System.out.println("是否确认预订(y/n):");
                String entry = Utils.readString(1).toLowerCase();
                switch (entry){
                    case "y":
                        System.out.println("预订人电话：");
                        String tel = Utils.readString(20);
                        System.out.println("预订成功！");
                        diningTableService.alterTable(id, employee.getName(), tel);
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

    }

    private void orderFoods(){

    }

    private void showPrice(){

    }

    private void payFoods(){

    }

    public static void main(String[] args) throws Exception {
        new View().mainMenu();
    }
}
