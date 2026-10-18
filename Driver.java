
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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

        // reads input from the user
        Scanner input = new Scanner(System.in);

        // stores prev messages 
        ArrayList<String> history = new ArrayList<>();

        // log when the driver starts
        toLog.println("START Driver started.");

        
        // shows the menu until user quits
        while (true) {
            System.out.println("\nCommands: password, encrypt, decrypt, history, quit");
            System.out.print("Enter command: ");
            if (!input.hasNextLine()) {
                break;
            }
            String cmd = input.nextLine().trim().toLowerCase();
            if (cmd.equals("quit")) {
                break;
            }
            System.out.println("Command selected: " + cmd);
        }


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
