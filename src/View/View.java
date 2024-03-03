package View;

import java.util.Scanner;
import Utils.Utils;

/**
 * @author lty
 * 主界面
 */
public class View {
    private boolean isLoop = true;
    private String input;
    private String userId;
    private String pwd;
    private Scanner scanf = new Scanner(System.in);

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
                    loginMenu();
                    break;
                case "2":
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

    private void loginMenu() throws Exception {
        System.out.print("请输入员工号：");
        userId = Utils.readString(100);
        System.out.print("请输入密  码：");
        pwd = Utils.readString(100);
        secondMenu();
        this.isLoop = false;
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
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "9":
                    isLoop_1 = false;
                    break;
                default:
                    System.out.println("输入有误，请重新输入！");
                    break;
            }
        }
    }



    public static void main(String[] args) throws Exception {
        new View().mainMenu();
    }
}
