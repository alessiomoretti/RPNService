package it.alessiomoretti;

import it.alessiomoretti.RPN.RPNCalculator;
import it.alessiomoretti.RPN.RPNParser;
import it.alessiomoretti.Server.ServerThread;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        // TODO parametric port and exception handling
        ServerSocket s = new ServerSocket(1234);
        while(true) {
            ServerThread thread = new ServerThread(s.accept());
            thread.run();
        }
    }
}
