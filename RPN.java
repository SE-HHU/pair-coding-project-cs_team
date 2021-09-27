import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Derek
 * @version 1.00
 * @Description 转化为逆波兰表达式
 * @ClassName RPN.java
 * @date 12:52 2021/9/27
 */
public class RPN {
    public Stack<String> transformToRPN(String equation){
        Stack<String> operator = new Stack<>();
        Stack<String> reversePolish = new Stack<>();
        ArrayList<String> stringArray = toStringArray(equation);

        for (String i : stringArray) {
            if(isDigit(i)){
                reversePolish.push(i);
            }
            else if(operator.empty() || i.equals("(") || operator.peek().equals("(")){
                operator.push(i);
            }
            else if(i.equals(")")){
                while(!operator.peek().equals("(")){
                    reversePolish.push(operator.pop());
                }
                operator.pop();//将"("出栈
            }
            else if(priority(i) > priority(operator.peek())){
                operator.push(i);
            }
            else if(priority(i) <= priority(operator.peek())){
                while(!operator.empty()
                        && !operator.peek().equals("(")
                        && priority(i) <= priority(operator.peek())){
                    reversePolish.push(operator.pop());
                }
                operator.push(i);
            }
        }

        while(!operator.empty()){
            reversePolish.push(operator.pop());
        }

        return reversePolish;
    }

    /**
     * 将算式中的数字和运算符分开存放为数组
     * @param equation 算式的字符串表达
     * @return 转化后的算式
     */
    public ArrayList<String> toStringArray(String equation){
        ArrayList<String> newString = new ArrayList<>();
        char[] charOfEquation = equation.toCharArray();
        StringBuilder digit = new StringBuilder();
        for (char c : charOfEquation) {
            if (Character.isDigit(c)) {
                digit.append(c);
            } else if (digit.length() != 0) {
                newString.add(digit.toString());
                digit = new StringBuilder();
                newString.add(Character.toString(c));
            } else {
                newString.add(Character.toString(c));
            }
        }
        if(digit.length() != 0){
            newString.add(digit.toString());
        }

        return newString;
    }

    public boolean isDigit(String num){
        boolean flag = true;
        char[] charOfNum = num.toCharArray();
        for (char c : charOfNum) {
            if(!Character.isDigit(c)){
                flag = false;
            }
        }

        return flag;
    }

    public int priority(String operator){
        return switch (operator) {
            case "+", "-" -> 0;
            case "×", "÷", "*", "/" -> 1;
            default -> -1;
        };
    }
}
