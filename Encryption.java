import java.util.Scanner;

public class Encryption {
    private static String key = null;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while (input.hasNextLine()) {

            String line = input.nextLine();
            String[] words = line.split(" ", 2);
            String cmd = words[0].toUpperCase();
            String message = words.length > 1 ? words[1].toUpperCase() : "";

            if (cmd.equals("PASS")) {

                if (!message.matches("[A-Z]+")) {
                    System.out.println("ERROR Invalid password");
                } else {
                    key = message;
                    System.out.println("RESULT");
                }

            } else if (cmd.equals("ENCRYPT")) {

                if (key == null) {
                    System.out.println("ERROR Password not set");
                } else if (!message.matches("[A-Z]+")) {
                    System.out.println("ERROR Invalid input");
                } else {
                    System.out.println("RESULT " + encryptMessage(message));
                }
                } else if (cmd.equals("DECRYPT")) {
                    if (key == null) {
                        System.out.println("ERROR Password not set");
                } else if (!message.matches("[A-Z]+")) {
                    System.out.println("ERROR Invalid input");
                } else {
                    System.out.println("RESULT " + decryptMessage(message));
                }
                } else if (cmd.equals("QUIT")) {
                    break;
                    
            } else {
                System.out.println("ERROR Unknown command");
            }
        }
        input.close();
    }

    private static String encryptMessage(String message) {
        String result = "";
        for (int i = 0; i < message.length(); i++) {
            int letterNum = message.charAt(i) - 'A';
            int keyNum = key.charAt(i % key.length()) - 'A';
            int encrypted = (letterNum + keyNum) % 26;
            result += (char)(encrypted + 'A');
        }

        return result;
    }

    private static String decryptMessage(String message) {
        String result = "";

        for (int i = 0; i < message.length(); i++) {
            int letterNum = message.charAt(i) - 'A';
            int keyNum = key.charAt(i % key.length()) - 'A';
            int decrypted = (letterNum - keyNum + 26) % 26;
            result += (char)(decrypted + 'A');
        }

        return result;
    }
}
