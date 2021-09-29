package indi.csteam.mathxpro.generate;

import java.util.ArrayList;

/**
 * @author Derek
 * @version 1.00
 * @Description TODO
 * @ClassName Eauqtions.java
 * @date 16:45 2021/9/27
 */
public class Equations {
    private final int max;
    private final int min;
    private final int count;
    private final boolean hasBracket;
    private final int OPE_NUM = 3;
    private final String[] ope = {"+","-","×","÷"};

    public Equations(int n,int ma,int mi,boolean h){
        count = n;// 算式个数
        max = ma;
        min = mi;
        hasBracket = h;
    }

    /**
     * 生成相应个数的算式，并一起作为List返回
     * @return 包含相应个数算式的List
     */
    public ArrayList<String> getEquations(){
        int operatorNum;// 运算符号个数

        ArrayList<String> equations = new ArrayList<>();// 保存生成的算式
        int i = 0;

        /* 不含括号 */
        while(i < count && !hasBracket){
            operatorNum = (int)(Math.random() * OPE_NUM + 1);

            String te = tempEqu(operatorNum);
            /* 已经有重复的算式 */
            if(Check.contain(equations, te)){
                continue;
            }
            equations.add(te);
            i++;
        }

        /* 含有括号 */
        while(i < count){
            operatorNum = (int)(Math.random() * OPE_NUM + 1);

            /* 只有一个运算符，无需添加括号 */
            if(operatorNum == 1){
                String temp = tempEqu(operatorNum);
                /* 已经有重复的算式 */
                if(Check.contain(equations, temp)){
                    continue;
                }
                equations.add(temp);
            }
            /* 两个运算符 */
            if(operatorNum == 2){
                String temp = insertBrackets(tempEqu(operatorNum));
                /* 已经有重复的算式 */
                if(Check.contain(equations, temp)){
                    continue;
                }
                equations.add(temp);
            }
            /* 三个运算符 */
            if(operatorNum == 3){
                String temp = insertBrackets(tempEqu(operatorNum));
                /* 已经有重复的算式 */
                if(Check.contain(equations, temp)){
                    continue;
                }
                equations.add(temp);
            }

            i++;
        }

        return equations;
    }

    /**
     * 生成一个符合数据要求且不含括号的中间式
     * @param opeNum 运算符个数
     * @return 不含括号的中间式
     */
    public String tempEqu(int opeNum){
        int[] operator = new int[opeNum],digit = new int[opeNum+1];// 运算数据与符号
        /* 先生成一个算式，判断合法性，直到生成合法算式 */
        StringBuilder str;
        until:
        do{
            for (int j = 0; j < opeNum; j++){
                operator[j] = (int)(Math.random() * 4);
            }
            for (int j = 0; j < opeNum +1; j++){
                digit[j] = (int)(Math.random() * (max-min+1) + min);
            }

            str = new StringBuilder(String.valueOf(digit[0]));
            for (int j = 0; j < opeNum; j++) {
                str.append(ope[operator[j]]).append(digit[j+1]);
                if(!(Information.validEquation(str.toString())
                        && Information.validAnswer(max,min, ComputeRPN.getAnswer(str.toString())))){
                    continue until;
                }
            }
        }while(!(Information.validEquation(str.toString())
                && Information.validAnswer(max,min, ComputeRPN.getAnswer(str.toString()))));

        return str.toString();
    }

    /**
     * 对不含括号的临时式进行插入括号操作，控制括号内必须含有加减
     * @param tempEqu 不含括号的临时式
     * @return 含有括号的算式
     */
    public String insertBrackets(String tempEqu){
        /* 临时的（位置，更新数组下标 */
        int tempBracket = 0, k = 0;
        /* 是否出现过加减，是否出现过乘除，是否添加过临时（ */
        boolean flag0 = false, flag1 = false, hasTB = false;
        /* 括号的开始位置与结束位置 */
        int[] begin = new int[OPE_NUM], end = new int[OPE_NUM];
        char[] charTemp = tempEqu.toCharArray();

        for (int j = 0; j < charTemp.length; j++) {
            /* 不是数字，并且是乘除 */
            if(!Character.isDigit(charTemp[j]) && Information.priority(charTemp[j]) == 1){
                /* 第一次出现乘除，并且之前出现过加减 */
                if(flag0 && !flag1){
                    end[0] = j + 1;//begin[0]初始化已赋值为0
                    k++;

                    tempBracket = j + 1 + k*2;
                    hasTB = true;
                    flag0 = false;//之后重新判断是否出现过加减
                    flag1 = true;
                }
                /* 在乘除后没有添加过临时（ */
                if(!hasTB){
                    tempBracket = j + 1 + k*2;
                    hasTB = true;
                    flag0 = false;
                    flag1 = true;
                }
                /* 添加过临时（，并且出现过加减 */
                else if(flag0){
                    /* 先记录下括号位置 */
                    begin[k] = tempBracket;
                    end[k] = j + 1 + k*2;
                    k++;

                    /* 再在当前乘除之后，添加新的临时（ */
                    tempBracket = j + 1 + k*2;
                }
            }
            else if(!Character.isDigit(charTemp[j])){
                flag0 = true;
            }
        }
        /* 添加过临时（，并且出现过加减，并且到了数组末尾 */
        if(hasTB && flag0){
            begin[k] = tempBracket;
            end[k] = charTemp.length + 1 + k*2;
            k++;
        }

        StringBuilder result = new StringBuilder(tempEqu);
        for (int i = 0; i < k; i++) {
            /* 随机是否插入括号 */
            int hasBracket = (int)(Math.random() * 2);
            if(hasBracket == 1){
                result.insert(begin[i], "(");
                result.insert(end[i], ")");
            }
            /* 不插入括号，则之后的括号位置前移2位 */
            else{
                for (int j = i + 1; j < k; j++) {
                    begin[j] -= 2;
                    end[j] -= 2;
                }
            }
        }

        return result.toString();
    }
}
