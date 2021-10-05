package indi.csteam.mathxpro.generate;

/**
 * @author Derek
 * @version 1.00
 * @Description to get needed information
 * @ClassName Information.java
 * @date 15:44 2021/9/27
 */
public class Information {
    public static boolean isDigit(String num){
        char[] charOfNum = num.toCharArray();
        for (char c : charOfNum) {
            if(!Character.isDigit(c)){
                return false;
            }
        }

        return true;
    }

    public static int priority(String operator){
        return switch (operator) {
            case "+", "-" -> 0;
            case "×", "÷", "*", "/" -> 1;
            case "(", ")" -> 2;
            default -> -1;
        };
    }

    public static int priority(char operator){
        return switch (operator) {
            case '+', '-' -> 0;
            case '×', '÷', '*', '/' -> 1;
            case '(', ')' -> 2;
            default -> -1;
        };
    }

    public static boolean validEquation(String tempEqu){
        /* 算式出现异常 */
        try{
            ComputeRPN.getAnswer(tempEqu);
        }
        catch (Exception e){
            return false;
        }

        return true;
    }

    /**
     * 前提说明：此方法与getAnswer()配合使用
     * @param max，数据最大值
     * @param min 数据最小值
     * @param answer 算式答案
     * @return 答案是否符合要求
     */
    public static boolean validAnswer(int max, int min, String answer){
        if(answer.equals("No Meaning!")){
            return false;
        }
        /* 结果为负 */
        else if(answer.charAt(0) == '-'){
            return false;
        }
        /* 结果为假分数或数据不在范围内 */
        else if(Fraction.isFraction(answer)){
            Fraction f = Fraction.transform(answer);
            return !f.isFalseFraction()
                    && f.numerator >= min && f.numerator <= max
                    && f.denominator <= max;
        }
        else{
            int s = Integer.parseInt(answer);
            return s >= min && s <= max;
        }
    }
}
