package it.alessiomoretti;

import it.alessiomoretti.Server.ServerThread;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    private static Integer PORT = 1234; // default port

    public static void main(String[] args) throws IOException {

        // if '--port=<portnumber>' is specified via CLI
        for (String arg : args) {
            if (arg.contains("--port=")) {
                try {
                    PORT = Integer.valueOf(arg.split("--port=")[1]);
                } catch (ValueException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid port number: exiting now! \n");
                    return;
                }
            }
        }

        // running server
        try {
            ServerSocket s = new ServerSocket(PORT);
            System.out.println("Server running on port: " + String.valueOf(PORT));
            for(;;) {
                ServerThread thread = new ServerThread(s.accept());
                thread.run();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
