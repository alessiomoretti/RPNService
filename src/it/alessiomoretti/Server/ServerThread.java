package it.alessiomoretti.Server;

import it.alessiomoretti.RPN.RPNCalculator;
import it.alessiomoretti.RPN.RPNParser;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerThread implements Runnable {

    private InputStream input = null;
    private OutputStream output = null;
    private Socket socket = null;

    /**
     * This class can be used to run a thread per request to the RPN service
     * @param socket which is opened by the server binding
     * @throws IOException
     */
    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        this.input = socket.getInputStream();
        this.output = socket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.input, StandardCharsets.US_ASCII));
            PrintStream printer = new PrintStream(this.output);

            // accepting new chars from the socket
            int inputChar;
            StringBuilder inputBuffer = new StringBuilder();
            while ((inputChar = reader.read()) != -1) {
                char c = (char) inputChar;
                // when ASCII linefeed is received the input is assumed complete
                if (c == '\n') break;
                inputBuffer.append(c);
            }

            // removing the carriage return if any ('\r')
            if (inputBuffer.indexOf("\r") != -1)
                inputBuffer.deleteCharAt(inputBuffer.indexOf("\r"));

            // building up the input string to pass to the RPNParser
            String inputString = inputBuffer.toString();

            // preparing output result
            String outputString;

            // check for legal format
            if (RPNParser.isLegalFormat(inputString)) {

                // setting up parser
                RPNParser parser = new RPNParser(inputString);
                // performing computation routine
                Integer outputResult = RPNCalculator.computeByStack(new RPNParser(inputString));
                outputString = (outputResult != null) ? outputResult.toString() : "ERROR illegal task submitted";

            } else { outputString = "ERROR illegal format for this task"; }

            // sending on output buffer the result as ASCII encoded text
            printer.write((outputString + "\n").getBytes(StandardCharsets.US_ASCII));

            this.input.close();
            this.output.close();
            this.socket.close();

            // console
            System.out.println(inputString + " => " + outputString);

        } catch (IOException e) {
            // printing out on stderr the exception stacktrace
            e.printStackTrace();
        }

    }
}
