package indi.csteam.mathxpro.generate;

import java.io.*;

/**
 * @author Derek
 * @version 1.00
 * @Description to deal with given files, and record information of them
 * @ClassName ReadFile.java
 * @date 20:13 2021/9/28
 */
public class Correct {
    /**
     * 规定：算式中不能包含空格
     * 读入相应文件，对内容进行需要的处理
     * @param exerciseName 算式题目文件
     * @param answerName 算式答案文件
     */
    public static void readFile(String exerciseName, String answerName){
        try {
            FileReader exercise = new FileReader(exerciseName);
            FileReader answer = new FileReader(answerName);
            LineNumberReader lnrOfExercise = new LineNumberReader(exercise);
            LineNumberReader lnrOfAnswer = new LineNumberReader(answer);

            /* 检查答案对错部分 */
            StringBuilder correct = new StringBuilder("Correct: (");//记录正确题目
            StringBuilder wrong = new StringBuilder("Wrong: (");//记录错误题目
            int sumOfCorrect = 0, sumOfWrong = 0;

            String equ = lnrOfExercise.readLine();
            String ans = lnrOfAnswer.readLine();
            while(equ != null && ans!= null){
                /* 答案错误 */
                if(!ComputeRPN.getAnswer(readEquation(equ)).equals(readAnswer(ans))){
                    wrong.append(lnrOfExercise.getLineNumber()).append(",");
                    sumOfWrong++;
                }
                /* 答案正确 */
                else{
                    correct.append(lnrOfExercise.getLineNumber()).append(",");
                    sumOfCorrect++;
                }
                equ = lnrOfExercise.readLine();
                ans = lnrOfAnswer.readLine();
            }
            correct = new StringBuilder(correct.substring(0, correct.length() - 1));
            wrong = new StringBuilder(wrong.substring(0, wrong.length() - 1));
            /* 补全信息 */
            correct.append(")");
            correct.insert(9, sumOfCorrect + " ");//冒号空格后，括号前
            wrong.append(")");
            wrong.insert(7, sumOfWrong + " ");//冒号空格后，括号前

            /* 将信息写入Grade.txt */
            BufferedWriter bw = new BufferedWriter(new FileWriter(".\\Grade.txt"));
            bw.write(correct.toString());
            bw.newLine();
            bw.write(wrong.toString());
            bw.newLine();

            /* 关闭流 */
            bw.flush();
            bw.close();
            lnrOfExercise.close();
            lnrOfAnswer.close();
        }
        catch (IOException e){
            System.out.println("Error in file!");
            System.out.println(e.getMessage());
        }

        try{
            FileReader exercise = new FileReader(exerciseName);
            LineNumberReader lnrOfExercise0 = new LineNumberReader(exercise);

            /* 复制一份题目文件 */
            File copied = new File(".", "CopiedGrade.txt");
            copy(new File(exerciseName), copied);


            /* 检查题目重复部分 */
            int k = 0;
            int sumOfRepeated = 0;
            String[] details = new String[200];
            String equ0 = lnrOfExercise0.readLine();
            while(equ0 != null){
                LineNumberReader lnrOfExercise1 = new LineNumberReader(new FileReader(copied));
                String equ1 = lnrOfExercise1.readLine();
                while(equ1 != null){
                    /* 只对当前题目之后的题目做查重 */
                    if(lnrOfExercise0.getLineNumber() < lnrOfExercise1.getLineNumber()){
                        if(Check.isRepeated(readEquation(equ0), readEquation(equ1))){
                            sumOfRepeated++;
                            details[k] = "(" + (k + 1) + ") " + lnrOfExercise0.getLineNumber() + ","+readEquation(equ0)
                                    + " Repeat " + lnrOfExercise1.getLineNumber() + "," + readEquation(equ1);
                            k++;
                        }
                    }
                    equ1 = lnrOfExercise1.readLine();
                }

                equ0 = lnrOfExercise0.readLine();
            }

            /* 将信息写入Grade.txt */
            BufferedWriter bw = new BufferedWriter(new FileWriter(".\\Grade.txt", true));//在后面续写
            bw.write("Repeat:");
            bw.write(String.valueOf(sumOfRepeated));
            bw.newLine();
            /* 当没有重复题目才写入相关信息 */
            if(sumOfRepeated != 0){
                bw.write("RepeatDetail:");
                bw.newLine();
                for (int i = 0; details[i] != null; i++) {
                    bw.write(details[i]);
                    bw.newLine();
                }
            }

            /* 关闭流 */
            bw.flush();
            bw.close();
            lnrOfExercise0.close();
        }
        catch (IOException e){
            System.out.println("Error in file!");
            System.out.println(e.getMessage());
        }
    }

    /**
     * 从文件的一行中截取算式
     * @param line 文件读取的一行字符串
     * @return 截取出的算式
     */
    public static String readEquation(String line){
        char[] cLine = line.toCharArray();
        /* 算式开始处，算式结束处 */
        int begin = 0, end = -1;
        for (int i = 0; i < cLine.length; i++) {
            /* 出现了非算式中的符号 */
            if(!Character.isDigit(cLine[i])
                    && Information.priority(cLine[i]) == -1
                    && cLine[i] != '='){
                begin = i + 1;
            }
            else if(cLine[i] == '='){
                end = i;
                break;
            }
        }
        if(end > begin){
            return line.substring(begin, end);
        }
        else{
            return line.substring(begin);
        }
    }

    /**
     * 从文件的一行中截取答案
     * @param line 文件读取的一行字符串
     * @return 截取出的答案
     */
    public static String readAnswer(String line){
        char[] cLine = line.toCharArray();
        /* 算式开始处 */
        int begin = 0;
        for (int i = 0; i < cLine.length; i++) {
            /* 出现了非算式中的符号 */
            if(!Character.isDigit(cLine[i])
                    && Information.priority(cLine[i]) == -1
                    && cLine[i] != '='){
                begin = i + 1;
            }
            else if(cLine[i] == '='){
                begin = i + 1;
                break;
            }
        }
        return line.substring(begin);
    }

    public static void copy(File fileName0, File fileName1) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName0));
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName1));

        String line = br.readLine();
        while(line != null){
            bw.write(line);
            bw.newLine();

            line = br.readLine();
        }

        br.close();
        bw.close();
    }
}
