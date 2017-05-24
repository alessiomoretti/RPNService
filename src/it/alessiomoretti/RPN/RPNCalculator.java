package it.alessiomoretti.RPN;


import java.awt.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RPNCalculator {

    /**
     * Assuming (as problem statement) operands are integer not-negative values,
     * we will use a stack to approach the Reverse Polish Notation task.
     *
     * @param parser RPNParser instance
     * @return integer
     */
    public static Integer computeByStack(RPNParser parser) {

        // if an illegal task was entered, a negative value is returned
        if (RPNParser.isMalformed(parser.getRawInput()))
            return -1;

        for (String element : parser.getRawInput()) {

            if (RPNParser.isNumeric(element)) {
                // if it is numeric, we perform conversion and stack it up...
                parser.getInputStack().push(Integer.valueOf(element));
            } else {
                // ...else we select the last two integers from the stack and
                // stack the result itself!

                // if it is an illegal operators sequence (e.g. '*+' instead of "* +")
                if (element.getBytes().length > 1) return -1;
                // assuming if no numeric value, it is a single-byte-operator...
                byte operator = element.getBytes()[0];

                // support variables for non-commutative operators
                Integer elem2;
                Integer elem1;

                // ...selecting the appropriate operator according to byte comparison
                switch (operator) {
                    case RPNParser.SUM:
                        parser.getInputStack().push(parser.getInputStack().pop() + parser.getInputStack().pop());
                        break;
                    case RPNParser.MUL:
                        parser.getInputStack().push(parser.getInputStack().pop() * parser.getInputStack().pop());
                        break;
                    case RPNParser.SUB:
                        elem2 = parser.getInputStack().pop();
                        elem1 = parser.getInputStack().pop();
                        parser.getInputStack().push(elem1 - elem2);
                        break;
                    case RPNParser.DIV:
                        elem2 = parser.getInputStack().pop();
                        elem1 = parser.getInputStack().pop();
                        parser.getInputStack().push(elem1 / elem2);
                        break;
                }
            }
        }

        // returning the last element (the result) in the stack
        return parser.getInputStack().pop();

    }
}
