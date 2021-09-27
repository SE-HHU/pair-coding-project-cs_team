/**
 * @author Derek
 * @version 1.00
 * @Description TODO
 * @ClassName Information.java
 * @date 15:44 2021/9/27
 */
public class Information {
    public static boolean isDigit(String num){
        char[] charOfNum = num.toCharArray();
//        if(Fraction.isFraction(num)){
//            return true;
//        }
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
            default -> -1;
        };
    }
}
