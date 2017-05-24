package it.alessiomoretti.RPN;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Stack;

public class RPNParser {

    // ASCII bytes encoded math operators
    public static final byte SUM = (int) '+';
    public static final byte SUB = (int) '-';
    public static final byte MUL = (int) '*';
    public static final byte DIV = (int) '/';

    private  String[] rawInput;
    private Stack<Integer> inputStack;

    public String[] getRawInput() {
        return rawInput;
    }

    public Stack<Integer> getInputStack() {
        return inputStack;
    }


    /**
     * This is an helper class to help the user parse correctly the
     * input string for the RPN task.
     *
     * @param input String
     */
    public RPNParser(String input) {
        this.rawInput = input.split(" ");
        this.inputStack = new Stack<>();
    }

    /**
     * This is a simple utiity to find out if a given string is numeric
     * or not (for the RPN task, to decide whether the string is to be stacked
     * or it is an operator).
     *
     * @param s String, the element to be processed
     * @return boolean, true or false
     */
    public static boolean isNumeric(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }


    /**
     * This utility can be used to check for anomalies in the input string.
     *
     * @param input String
     * @return boolean, true or false
     */
    public static boolean isMalformed(String[] input) {

        if (input.length == 1)
            return !isNumeric(input[0]);

        if (input.length == 0) return true;

        if (isNumeric(input[input.length - 1])) return true;

        if (input.length >= 2) if (!isNumeric(input[0]) && !isNumeric(input[1])) return true;

        return false;
    }
}
