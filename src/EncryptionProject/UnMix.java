package EncryptionProject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*****************************************************************
 Class which unmixes the message scrammbled by the mix class

 @author Jake Geers
 @version 4/3/2016
 *****************************************************************/
public class UnMix implements IUnMix {

    /** Linked list of characters representing a message (string) */
    private EncryptionProject.LinkedList message;

    /** Array list whos elements are commands to be reversed */
    private ArrayList<String> executedCmds;

    /** Scanner class implementation */
    Scanner scanner;


    /*****************************************************************
     Constructor which initalizes message and execuded commands

     @throws IOException
     @throws RuntimeException
     *****************************************************************/
    public UnMix() {
        message = new EncryptionProject.LinkedList();
        executedCmds = new ArrayList();
        scanner = new Scanner(System.in);
    }

    /*****************************************************************
     A  method that loads the text file that contains the commands
     which were executed in the mix class. I choose to unparamaterize
     for user benifit.

     @throws IOException
     @throws FileNotFoundException
     *****************************************************************/
    public void load() {
        String encryptedFile = "encryptedFile.txt";
        Scanner inFS = null; //creating another Scanner
        FileInputStream fileByteStream = null;
        try { //attempting to find and read the file
            fileByteStream = new FileInputStream(encryptedFile);
            inFS = new Scanner(fileByteStream);
            inFS.useDelimiter("[,\r\n]+"); //spliting by lines
            while (inFS.hasNext()) {
                executedCmds.add(inFS.nextLine()); //ading to array
            }
            fileByteStream.close();
        } catch (FileNotFoundException error1) {
            System.out.println("File not found ");
        } catch (IOException error2) {
            System.out.println("Oops!  Something went wrong.");
        }
    }

    /*****************************************************************
     Called by the main method this method calls decrpt and interacts
     with the user.

     @throws RuntimeException
     *****************************************************************/
    public void decrypt() {
        System.out.println("Please enter encrypted message: ");
        setMessage(scanner.nextLine()); //setting msg to user input
        scanner.close();

        UnMixMessage(executedCmds, message); //executes unmix

        //Lastly prints decrypted msg
        System.out.println("Decrypted Msg: " + message.display());
    }

    /*****************************************************************
     Similar to proccess command, this method reads the executed
     command list backwards and preforms the inverse operation

     @param executedCommands instanciated by load method
     @param msg the linked list representing the encrypted msg
     @throws RuntimeException
     @return decrypted message
     *****************************************************************/
    @Override
    public String UnMixMessage(ArrayList executedCommands,
                               EncryptionProject.LinkedList msg)
    {
        //creating an array for each command section
        for (int i = executedCommands.size(); i > 0; i--) {
            String[] cmd = executedCommands.get(i - 1)
                    .toString().split("\\s+");


            switch (cmd[0]) {
                case "a": //undo insert
                    int loc = Integer.parseInt(cmd[2]); //casting
                    msg.removeAt(cmd[1], loc);
                    break;
                case "r": //undo remove
                    loc = Integer.parseInt(cmd[2]);
                    loc--;
                    if (loc == -1) { //determining where to add
                        msg.addAtTop(cmd[1]);
                    } else {
                        msg.insertAfter(loc, cmd[1].charAt(0));
                    }
                    break;
                case "p": //undo paste
                    //creating a new string array split to account for
                    //spaces inbetween letters
                    String[] str = executedCommands.get(i - 1)
                            .toString().split(":");
                    undoPaste(str[1], str[2]);
                    break;
                default:
                    System.out.println("Error 8863682: Contact Admin");
            }
        }
        return null;
    }

    /*****************************************************************
     Setting message to a linked list of chars

     @param message a string from user input
     @throws IndexOutOfBoundsException
     *****************************************************************/
    public void setMessage(String message) {
        for (int i = 0; i < message.length(); i++) {
            this.message.add(message.charAt(i)); //separating into char
        }
    }

    /*****************************************************************
     Returns the linked list representing the message

     @throws NullPointerException
     @return Linked list represeting the message
     *****************************************************************/
    public LinkedList getMessage() {
        return message;
    }

    /*****************************************************************
     Getter for the executed comand list. Called in the UnMixMsg method

     @throws NullPointerException
     @return String array list of executed commands
     *****************************************************************/
    public ArrayList<String> getExecutedCmds() {
        return executedCmds;
    }

    /*****************************************************************
     Method called by the unmixmessage method. Responsible for
     reversing paste command. String str param is unceccesary

     @param str extra
     @param location at which to remove char
     @throws NullPointerException
     *****************************************************************/
    public void undoPaste(String str, String location) {
        int loc = Integer.parseInt(location);
        for (int i = 0; i < str.length(); i++) {
            message.removeAt(null, loc); //removes char at loc
        }

    }

    /*****************************************************************
     Main Method sets up instance of unMix and loads file and decrypts

     @param args
     @throws RuntimeException
     *****************************************************************/
    public static void main(String[] args) {
        UnMix m = new UnMix();
        m.load(); //loads list of executed commands
        m.decrypt(); //front end
    }
}
