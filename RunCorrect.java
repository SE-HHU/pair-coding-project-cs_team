package indi.csteam.mathxpro.generate;

/**
 * @author Derek
 * @version 1.00
 * @Description to run correct
 * @ClassName RunCorrect.java
 * @date 19:07 2021/10/6
 */
public class RunCorrect {
    public static void main(String[] args) {
        String exerciseName = ".\\Exercises.txt";
        String answerName = ".\\Answers.txt";
        Correct.readFile(exerciseName, answerName);
    }
}
