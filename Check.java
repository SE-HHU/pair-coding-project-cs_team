package indi.csteam.mathxpro.generate;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Derek
 * @version 1.00
 * @Description to check for repeated equation
 * @ClassName Check.java
 * @date 20:10 2021/9/27
 */
public class Check {
    /**
     * 判断是否算式List中是否含有此算式
     * @param equList 已有的算式，ArrayList
     * @param equ 需判断的算式，String
     * @return 判断结果，boolean
     */
    public static boolean contain(ArrayList<String> equList, String equ){
        for (String str : equList) {
            if(Check.isRepeated(equ, str)){
                return true;
            }
        }

        return false;
    }

    /**
     * 规定：只有运算顺序一致才判定为重复，如"(1+2)*(2+2)", "(2+2)*(2+1)"为不同算式
     * 判断两个算式（中序表达式）是否重复
     * @param equ1 一个算式，String
     * @param equ2 另一个算式，String
     * @return 判断结果，boolean
     */
    public static boolean isRepeated(String equ1, String equ2){
        Stack<String> equation1 = transform(RPN.transformToRPN(equ1));
        Stack<String> equation2 = transform(RPN.transformToRPN(equ2));

        for (int i = 0; i < equation1.size(); i++) {
            /* 出现不相同的栈顶元素 */
            if(!equation1.peek().equals(equation2.peek())){
                /* 乘除可能的不同形式 */
                if(((equation1.peek().equals("*") || equation1.peek().equals("×"))
                        && (equation2.peek().equals("*") || equation2.peek().equals("×")))
                        || ((equation1.peek().equals("/") || equation1.peek().equals("÷"))
                        && (equation2.peek().equals("/") || equation2.peek().equals("÷")))){
                    equation1.pop();
                    equation2.pop();
                    continue;
                }
                return false;
            }
            /* 栈顶元素相同，将相同的栈顶出栈 */
            else{
                equation1.pop();
                equation2.pop();
            }
        }
        return true;
    }

    /**
     * 将逆波兰式按优先级降序的方式重新排列，并且加和乘（满足交换律）的两个被操作数按升序的方式排列
     * @param RPNOfEquation 任意逆波兰式，Stack
     * @return 转换后的新逆波兰式，Stack
     */
    public static Stack<String> transform(Stack<String> RPNOfEquation){
        Stack<String> temp = new Stack<>();
        Stack<String> result = new Stack<>();
        boolean flag = false;//是否第一次取出过元素
        int numOfDigit = 0;//临时栈中数字个数
        int numOfTarget = 0;//下一步要取的数据个数
        for(String str : RPNOfEquation){
            /* str是数据（整数） */
            if(Information.isDigit(str)){
                temp.push(str);
                numOfDigit++;
                if(numOfTarget < 2){
                    numOfTarget++;
                }
            }
            /* str是运算符 */
            else if(!flag || numOfTarget == 2){
                String first, second, t;
                second = temp.pop();
                first = temp.pop();

                /* 满足交换律的运算符 */
                if((str.equals("+") || str.equals("*") || str.equals("×")) && max(first, second).equals(first)){
                    /* 使first小于second */
                    t = first;
                    first = second;
                    second = t;
                }
                result.push(first);
                result.push(second);
                result.push(str);
                numOfDigit -= 2;
                numOfTarget = 0;

                flag = true;
            }
            else if(numOfTarget == 1){
                result.push(temp.pop());
                result.push(str);
                numOfDigit -= 1;
                numOfTarget = 0;
            }
            else if(numOfTarget == 0 && numOfDigit > 0){
                result.push(temp.pop());
                result.push(str);
                numOfDigit -= 1;
            }
        }

        return result;
    }

    /**
     * 取两个整数较大值
     * @param num1 一个整数，String
     * @param num2 另一个整数，String
     * @return 较大值，String
     */
    public static String max(String num1, String num2){
        /* 确保是整数 */
        if(Information.isDigit(num1) && Information.isDigit(num2)){
            int m = Integer.max(Integer.parseInt(num1), Integer.parseInt(num2));
            return String.valueOf(m);
        }
        else{
            return "error";
        }
    }
}
