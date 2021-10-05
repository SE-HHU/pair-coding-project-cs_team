package indi.csteam.mathxpro.generate;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Derek
 * @version 1.00
 * @Description for running
 * @ClassName Main.java
 * @date 14:11 2021/9/27
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("输入生成题目个数：");
        int n;
        while((n = in.nextInt())<=0){
            System.out.println("请重新输入有效题目个数：");
        }

        System.out.println("请输入数据范围：");
        System.out.print("max=");
        int max = in.nextInt();
        System.out.print("min=");
        int min = in.nextInt();

        System.out.print("是否含括号(yes/no)：");
        String hasBracket = in.next();

        Equations e = new Equations(n,max,min, hasBracket.equals("yes"));
        ArrayList<String> equationList = e.getEquations();

        GetFiles.getExercises(equationList);
        GetFiles.getAnswers(equationList);
    }
}
