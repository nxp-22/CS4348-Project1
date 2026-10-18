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

        // pipes to communicate with both processes
        PrintWriter toLog = new PrintWriter(logProc.getOutputStream(), true);
        PrintWriter toEnc = new PrintWriter(encProc.getOutputStream(), true);

        // read responses from the encryption process
        BufferedReader fromEnc = new BufferedReader(
            new InputStreamReader(encProc.getInputStream()));

        // reads input from the user
        Scanner input = new Scanner(System.in);

        // stores previous messages and results
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
                toLog.println("COMMAND QUIT");
                toLog.println("RESPONSE Quitting");
                break;

            } else if (cmd.equals("password")) {

                // use an old string or enter a new password
                String password = getText(input, history, "password");

                if (password == null) {
                    toLog.println("COMMAND PASSWORD");
                    toLog.println("RESPONSE ERROR Invalid password");
                    continue;
                }

                // send password to encryption program
                toEnc.println("PASS " + password);
                String passReply = fromEnc.readLine();

                if (passReply == null) {
                    System.out.println("ERROR Encryption process stopped");
                    toLog.println("COMMAND PASSWORD");
                    toLog.println("RESPONSE ERROR Encryption process stopped");
                    break;
                }

                System.out.println(passReply);
                toLog.println("COMMAND PASSWORD");
                toLog.println("RESPONSE " + passReply);

            } else if (cmd.equals("encrypt") || cmd.equals("decrypt")) {

                // use an old string or enter a new message
                String message = getText(input, history, "message");

                if (message == null) {
                    toLog.println("COMMAND " + cmd.toUpperCase());
                    toLog.println("RESPONSE ERROR Invalid input");
                    continue;
                }

                // send command and message to encryption program
                toEnc.println(cmd.toUpperCase() + " " + message);
                String reply = fromEnc.readLine();

                if (reply == null) {
                    System.out.println("ERROR Encryption process stopped");
                    toLog.println("COMMAND " + cmd.toUpperCase());
                    toLog.println("RESPONSE ERROR Encryption process stopped");
                    break;
                }

                System.out.println(reply);
                toLog.println("COMMAND " + cmd.toUpperCase());
                toLog.println("RESPONSE " + reply);

                // save the encrypted or decrypted result
                if (reply.startsWith("RESULT ")) {
                    history.add(reply.substring(7));
                }

            } else if (cmd.equals("history")) {

                // show all previous strings
                showHistory(history);
                toLog.println("COMMAND HISTORY");
                toLog.println("RESPONSE History displayed");

            } else {
                System.out.println("ERROR Unknown command");
                toLog.println("COMMAND " + cmd.toUpperCase());
                toLog.println("RESPONSE ERROR Unknown command");
            }
        }

        // tells processes to stop
        toEnc.println("QUIT");
        toLog.println("EXIT Driver finished.");
        toLog.println("QUIT");

        // closes pipes
        toLog.close();
        toEnc.close();
        fromEnc.close();

        // wait for them to finish
        encProc.waitFor();
        logProc.waitFor();

        input.close();
    }

    // lets user pick a string from history or enter a new one
    private static String getText(Scanner input, ArrayList<String> history, String type) {

        while (true) {
            System.out.print("Use history? (y/n): ");

            if (!input.hasNextLine()) {
                return null;
            }

            String choice = input.nextLine().trim().toLowerCase();

            if (choice.equals("y")) {

                if (history.isEmpty()) {
                    System.out.println("History is empty. Enter a new " + type + ".");
                    break;
                }

                while (true) {
                    showHistory(history);
                    System.out.println("0. Enter new " + type);
                    System.out.print("Choose a number: ");

                    if (!input.hasNextLine()) {
                        return null;
                    }

                    String number = input.nextLine().trim();

                    if (number.equals("0")) {
                        break;
                    }

                    try {
                        int index = Integer.parseInt(number) - 1;

                        if (index >= 0 && index < history.size()) {
                            return history.get(index);
                        }

                    } catch (NumberFormatException e) {
                        // invalid number, ask again
                    }

                    System.out.println("ERROR Invalid selection");
                }

                break;

            } else if (choice.equals("n")) {
                break;

            } else {
                System.out.println("Please enter y or n.");
            }
        }

        // ask for a new password or message
        System.out.print("Enter " + type + ": ");

        if (!input.hasNextLine()) {
            return null;
        }

        String message = input.nextLine().trim().toUpperCase();

        // check if the input only has letters
        if (!message.matches("[A-Z]+")) {

            if (type.equals("password")) {
                System.out.println("ERROR Invalid password");
            } else {
                System.out.println("ERROR Invalid input");
            }

            return null;
        }

        // new passwords are never saved in history
        if (type.equals("message")) {
            history.add(message);
        }

        return message;
    }

    // displays history as a numbered list
    private static void showHistory(ArrayList<String> history) {

        if (history.isEmpty()) {
            System.out.println("History is empty.");
        } else {

            for (int i = 0; i < history.size(); i++) {
                System.out.println((i + 1) + ". " + history.get(i));
            }
        }
    }
}
