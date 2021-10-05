package indi.csteam.mathxpro.generate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Derek
 * @version 1.00
 * @Description to generate files of Exercises and Answers
 * @ClassName GetFiles.java
 * @date 16:28 2021/9/27
 */
public class GetFiles {
    public static void getExercises(ArrayList<String> equationList){
        int k = 1;
        try {
            BufferedWriter bw1 = new BufferedWriter(new FileWriter(".\\Exercises.txt"));
            for (String s: equationList) {
                bw1.write(k + ". " + s + "=");
                bw1.newLine();
                bw1.flush();
                k++;
            }
            bw1.close();
            System.out.println("已在当前文件目录下生成题目。");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void getAnswers(ArrayList<String> equationList){
        int k = 1;
        try {
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(".\\Answers.txt"));
            for (String s: equationList) {
                bw2.write(k + ". " + ComputeRPN.getAnswer(s));
                bw2.newLine();
                bw2.flush();
                k++;
            }
            bw2.close();
            System.out.println("已在当前文件目录下生成答案。");
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
