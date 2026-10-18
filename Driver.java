
import java.io.*;
public class Driver {

    public static void main(String[] args) throws IOException, InterruptedException {

        // first check if the log file name is given
        if (args.length != 1) {
            System.out.println("Usage: java Driver <logfile>");
            return;
        }

        // then start the logger and encryption processes
        Process logProc = new ProcessBuilder("java", "Logger", args[0]).start();
        Process encProc = new ProcessBuilder("java", "Encryption").start();

        
        PrintWriter toLog = new PrintWriter(logProc.getOutputStream(), true);
        PrintWriter toEnc = new PrintWriter(encProc.getOutputStream(), true);

        // read the responses from the encryption process
        BufferedReader fromEnc = new BufferedReader(
            new InputStreamReader(encProc.getInputStream()));

        // log when the driver starts
        toLog.println("START Driver started.");

        // sends the password to  encryption program
        toEnc.println("PASS HELLO");
        String passReply = fromEnc.readLine();

        System.out.println("Password response: " + passReply);
        toLog.println("COMMAND Password set");
        toLog.println("RESPONSE " + passReply);

        toEnc.println("ENCRYPT HELLO");
        String encReply = fromEnc.readLine();

        System.out.println("Encryption response: " + encReply);
        toLog.println("COMMAND ENCRYPT");
        toLog.println("RESPONSE " + encReply);

        // tells processes to stop
        toEnc.println("QUIT");
        toLog.println("EXIT Driver finished.");
        toLog.println("QUIT");

        // closes  pipes
        toLog.close();
        toEnc.close();
        fromEnc.close();

        // wait for them to finish
        encProc.waitFor();
        logProc.waitFor();
    }
}
