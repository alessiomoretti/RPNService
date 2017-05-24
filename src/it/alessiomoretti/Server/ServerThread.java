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
        Reader reader = new InputStreamReader(this.input, StandardCharsets.US_ASCII);

        try {
            // accepting new chars from the socket
            int inputChar;
            StringBuffer inputBuffer = new StringBuffer();
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
            // computing the result
            Integer outputResult = RPNCalculator.computeByStack(new RPNParser(inputString));

            // TODO Error messages
            this.output.write((outputResult.toString() + "\n").getBytes(StandardCharsets.US_ASCII));
            this.socket.close();

        } catch (IOException e) {
            // printing out on stderr the exception stacktrace
            e.printStackTrace();
        }

    }
}
