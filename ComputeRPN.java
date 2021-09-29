package indi.csteam.mathxpro.generate;

import java.util.Stack;

/**
 * @author Derek
 * @version 1.00
 * @Description TODO
 * @ClassName ComputeRPN.java
 * @date 15:10 2021/9/27
 */
public class ComputeRPN {
    public static String getAnswer(String equation){
        ComputeRPN computer = new ComputeRPN();
        return computer.AnswerFromStack(RPN.transformToRPN(equation));
    }

    public String AnswerFromStack(Stack<String> RPNOfEqu){
        Stack<String> result = new Stack<>();
        for(String str : RPNOfEqu){
            if(Information.isDigit(str)){
                result.push(str);
            }
            else{
                String first, second, t = "1";
                second = result.pop();
                first = result.pop();
                switch (str){
                    case "+" -> t = add(first,second);
                    case "-" -> t = minus(first,second);
                    case "*", "×" -> t = multiply(first,second);
                    case "/", "÷" -> t = divide(first,second);
                }
                result.push(t);
            }
        }

        return result.pop();
    }

    //分数加法
    public String add(String a,String b){
        Fraction fa,fb,r;
        if(Fraction.isFraction(a)|| Fraction.isFraction(b)){
            fa = Fraction.transform(a);
            fb = Fraction.transform(b);
            r = new Fraction(fa.numerator*fb.denominator+fa.denominator*fb.numerator,fa.denominator*fb.denominator);

            return r.getFraction();
        }
        else return String.valueOf(Integer.parseInt(a)+Integer.parseInt(b));
    }
    //分数减法
    public String minus(String a,String b){
        Fraction fa,fb,r;
        if(Fraction.isFraction(a)|| Fraction.isFraction(b)){
            fa = Fraction.transform(a);
            fb = Fraction.transform(b);
            r = new Fraction(fa.numerator*fb.denominator-fa.denominator*fb.numerator,fa.denominator*fb.denominator);

            return r.getFraction();
        }
        else return String.valueOf(Integer.parseInt(a)-Integer.parseInt(b));
    }
    //分数乘法
    public String multiply(String a,String b){
        Fraction fa,fb,r;
        if(Fraction.isFraction(a)|| Fraction.isFraction(b)){
            fa = Fraction.transform(a);
            fb = Fraction.transform(b);
            r = new Fraction(fa.numerator*fb.numerator,fa.denominator*fb.denominator);

            return r.getFraction();
        }
        else return String.valueOf(Integer.parseInt(a)*Integer.parseInt(b));
    }
    //分数除法
    public String divide(String a,String b){
        Fraction fa,fb,r;
        if(Fraction.isFraction(a) || Fraction.isFraction(b)){
            fa = Fraction.transform(a);

            if(Fraction.isFraction(b)){
                int endIndex = b.indexOf('/');
                if(endIndex == -1){
                    endIndex = b.indexOf('÷');
                }
                //b要转化为倒数
                int bd = Integer.parseInt(b.substring(0, endIndex)),
                        bn = Integer.parseInt(b.substring(endIndex + 1));
                fb = new Fraction(bn, bd);
            }
            else fb = new Fraction(1, Integer.parseInt(b));

            r = new Fraction(fa.numerator*fb.numerator,fa.denominator*fb.denominator);

        }
        else{
            r = new Fraction(Integer.parseInt(a),Integer.parseInt(b));
        }

        return r.getFraction();
    }
}
