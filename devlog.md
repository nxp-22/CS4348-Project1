# 2026-10-16 16:30

## Thoughts So Far
I looked over all the project instructions and started to familiarize myself with the project.I need 3 programs, logger, encryption program, and driver program. The logger records actvitiy with timestamps.The driver communicates with other program through pipes. The encryption program use a Vigenère cipher.

## Plan for This Session
- Creating the repository
- Setting up the initial project structure
- Creating the main program files

## Session Reflection
I created the project repository and the initial files needed for the project.

# 2026-10-17 19:25

## Thoughts So Far
In this session I worked on Logger.java. This file reads messages from standard input and saves them to a log file with timestamps. I used PrintWriter and BufferedReader and added a loop that stops when QUIT is entered.

I also tested this program, during which I noticed extra lines in my log file from an earlier test. I learned that FileWriter with true adds new messages instead of overwriting the file. The new messages were formatted correctly.

## Plan for This Session
- work on the logger.java file
- test the file


# 2026-10-17 20:00

## Thoughts So Far
I worked on Encryption.java and implemented the PASS and ENCRYPT commands. I used  Vigenère cipher to encrypt messages. I also added error handling when no password is set or when invalid characters are entered.

I  tested this program as well. I learned that FileWriter with true adds new messages instead of overwriting the file. 

## Plan for This Session
- work on the Encryption.java file
- test the file

# 2026-10-17 20:40

## Thoughts So Far
I added the DECRYPT command to Encryption.java. It uses the same idea but subtracts the key values instead of adding them.

I tested it using OIWWC with the password HELLO and successfully got HELLO back. Next, I plan to work on Driver.java to connect the programs using processes and pipes.

## Plan for This Session
- add a command to the Encryption.java file
- test the new command

# 2026-10-17 21:01

## Thoughts So Far
I worked on Driver.java. I also used ProcessBuilder to start Logger and Encryption as separate processes. I used PrintWriter to send commands and BufferedReader to receive responses through pipes.

I tested the Driver with the password HELLO and confirmed that encryption returned OIWWC and checked the activity.log and verified that the Logger.

## Plan for This Session
- worked on the  driver.java file
- test the file using the word HELLO

# 2026-10-17 21:21

## Thoughts So Far
I updated Driver.java. I added an interactive menu for password, encrypt, decrypt, history, and quit using an ArrayList to store the strings and encryption/decryption results so they can be reused later.
I also added options for selecting strings from history and entering new text. 

## Plan for This Session
- updaed the  driver.java file
- test the file using the command words









