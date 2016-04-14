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
     @param // TODO: 4/11/2016
     @throws // TODO: 4/11/2016
     *****************************************************************/
    public UnMix() {
        message = new EncryptionProject.LinkedList();
        executedCmds = new ArrayList();
        scanner = new Scanner(System.in);
    }

    /*****************************************************************
     A  method that loads the text file that contains the commands
     which were executed in the mix class
     @throws // TODO: 4/11/2016
     *****************************************************************/
    public void load() {
        String encryptedFile = "encryptedFile.txt";
        Scanner inFS = null;
        FileInputStream fileByteStream = null;
        try {
            fileByteStream = new FileInputStream(encryptedFile);
            inFS = new Scanner(fileByteStream);
            inFS.useDelimiter("[,\r\n]+");
            while (inFS.hasNext()) {
                executedCmds.add(inFS.nextLine());
            }
            fileByteStream.close();
        } catch (FileNotFoundException error1) {
            System.out.println("File not found ");
        } catch (IOException error2) {
            System.out.println("Oops!  Something went wrong.");
        }

    }

    public void decrypt() {
        System.out.println("Please enter encrypted message: ");
        setMessage(scanner.nextLine());
        scanner.close();
        UnMixMessage(executedCmds, message);
        System.out.println("Decrypted Msg: " + message.display());
    }

    @Override
    public String UnMixMessage(ArrayList executedCommands, EncryptionProject.LinkedList msg) {
        for (int i = executedCommands.size(); i > 0 ; i--) {
            String [] cmd = executedCommands.get(i-1).toString().split("\\s+");

            switch (cmd[0]){
                case "a": //undo insert // TODO: 4/14/2016 account for -1
                    int loc = Integer.parseInt(cmd[2]);
                    msg.removeAt(cmd[1], loc);
                    break;
                case "r": //undo remove
                    loc = Integer.parseInt(cmd[2]);
                    loc --;
                    if (loc == -1){
                        msg.addAtTop(cmd[1]);
                    } else {
                        msg.insertAfter(loc, cmd[1].charAt(0));
                    }
                    break;
                case "p": //undo paste
                      String[] str = executedCommands.get(i-1).toString().split(":");
                      undoPaste(str[1], str[2]);
                    break;
                case "z":



                    break;

                default:
                    System.out.println("idk yet");
            }
        }

        return null;
    }

    public void setMessage(String message) {
        for (int i = 0; i < message.length(); i++) {
            this.message.add(message.charAt(i)); //separating into char
        }
    }

    public static void main(String[] args) {
        UnMix m = new UnMix();
        m.load();
        m.decrypt();

    }

    public LinkedList getMessage() {
        return message;
    }

    public ArrayList<String> getExecutedCmds() {
        return executedCmds;
    }

    public void undoPaste(String str, String location){
        int loc = Integer.parseInt(location);
        //loc++;
        System.out.println("String lnght: " + str.length());
        for (int i = 0; i <str.length() ; i++) {
            message.removeAt(null, loc);
        }

    }
}
