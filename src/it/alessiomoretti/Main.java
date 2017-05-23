package it.alessiomoretti;

import it.alessiomoretti.RPN.RPNCalculator;
import it.alessiomoretti.RPN.RPNParser;

public class Main {

    public static void main(String[] args) {
        System.out.println(RPNCalculator.computeByStack(new RPNParser(args[0])));
    }
}
