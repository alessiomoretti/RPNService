package it.alessiomoretti.RPN;

import java.util.Stack;

public class RPNParser {

    // ASCII bytes encoded math operators
    public static final byte SUM = (int) '+';
    public static final byte SUB = (int) '-';
    public static final byte MUL = (int) '*';
    public static final byte DIV = (int) '/';

    private String[] rawInput;
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
     * @return boolean
     */
    public static boolean isNumeric(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /**
     * This support utility can be used to check for illegal formats
     * in the input string.
     *
     * @param input String
     * @return boolean
     */
    public static boolean isLegalFormat(String input) {

        // if it is an empty string it cannot be processed
        if (input.length() == 0) return false;

        // support variables to pre-parse the string
        int elements = 0;
        boolean foundOperator = false;

        // check if any non numeric operand is provided
        for (String elem : input.split(" ")) {
            elements++;
            if (elem.lastIndexOf(SUM) != -1 || elem.lastIndexOf(SUB) != -1 ||
                elem.lastIndexOf(DIV) != -1 || elem.lastIndexOf(MUL) != -1) {
                foundOperator = true;
                continue;
            }
            if (!isNumeric(elem)) return false;
        }

        //check if any operator is provided
        return elements == 1 || foundOperator;
    }

    /**
     * This support utility can be used to check for anomalies in the input operands
     * and operators sequence.
     *
     * @param input array of String (the elements)
     * @return boolean
     */
    public static boolean isMalformed(String[] input) {
        // following simple rules adopted to check for illegal RPN task

        // 1. if the input is only one-element-long it must be a numeric value
        if (input.length == 1) return !isNumeric(input[0]);

        // 2. if the input is two-elements-long it cannot be processed
        if (input.length == 2) return true;

        // 3. if it is a zero-element-long task it cannot be processed
        if (input.length == 0) return true;

        // 4. check on operands order
        if (input.length > 2) {
            // 4.1 fails if the last element is numeric
            if (isNumeric(input[input.length -1])) return true;
            // 4.2 fails if the first two elements are not numeric
            if (!isNumeric(input[0]) && !isNumeric(input[1])) return true;
        }

        return false;
    }
}
