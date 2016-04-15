package EncryptionProject;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
// TODO: 4/14/2016 linked list of linked list
// TODO: 4/14/2016 java doc
// TODO: 4/14/2016 error message test
// TODO: 4/14/2016 junit

/*****************************************************************
 Class used to mix/encrypt message implements IMix.

 @author Jake Geers
 @version 4/3/2016
 *****************************************************************/
public class Mix implements IMix {

    /** Linked list of chars representing message */
    private EncryptionProject.LinkedList message;

    /** Scanner to read console input */
    private Scanner scanner;

    /** Array list retaining procced commands */
    private ArrayList<String> execudedCmds;

    /** Array list contaiing clipBoards */
    private ArrayList<LinkedList> clipBoards;


    /*****************************************************************
     Mix Constructor for instantiating global variables

     @throws // TODO: 4/11/2016
     *****************************************************************/
    public Mix() {
        message = new EncryptionProject.LinkedList();
        scanner = new Scanner(System.in);
        execudedCmds = new ArrayList<>();
        clipBoards = new ArrayList<>();

    }

    /*****************************************************************
     Implementation of setInitalMessage, sets the message to be mixed
     based on user input

     @param message the string to become the initial message
     *****************************************************************/
    @Override
    public void setInitialMessage(String message) {
        for (int i = 0; i < message.length(); i++) {
            this.message.add(message.charAt(i)); //separating into char
        }
        if (message.length() == 0){
            System.out.print("Please enter a message:");
            setInitialMessage(scanner.nextLine());
        }
    }

    /*****************************************************************
     Implementation of processCommand. Executes the given command

     @param command the user defined command
     @return the message after executing the command
     @throws RuntimeException
     @throws IOException
     *****************************************************************/
    @Override
    public String processCommand(String command) {
        String[] cmd = command.split("\\s+"); //separating input

        switch (cmd[0]) {
            case "q": //quit
                //accessing system clipboard
                StringSelection selection;
                selection = new StringSelection(message.display());
                Clipboard clipboard = Toolkit.getDefaultToolkit()
                        .getSystemClipboard();
                clipboard.setContents(selection, selection);

                //printing final message
                System.out.println("Quitting. Final Message is:\n" +
                        message.display());
                System.exit(0);
                break;
            case "help": //help
                System.out.println("List of available commands are:");
                System.out.printf("\tCopy: c start end clipBoardNum\n");
                System.out.printf("\tPaste: p afterNum clipBoardNum\n");
                System.out.printf("\tInsert: a char afterNum\n");
                System.out.printf("\tRemove all: r char\n");
                System.out.printf("\tSave: s\n");
                System.out.printf("\tQuit: q\n");
                break;
            case "a": //insert // TODO: 4/11/2016  letter first then num
                if (cmd.length != 3) { //ensuring proper length
                    System.out.println("Insert has three param");
                    break;
                }
                if (cmd[1].toString().length() > 1){ //checking char
                    System.out.println("Insert one char only");
                    break;
                }
                //checking valid size
                if (cmd[2].toString().length() > message.getSize()){
                    System.out.println("Position is not valid");
                    break;
                }

                try { //ensuring position is a int
                    int index = Integer.parseInt(cmd[2]);
                    if (index >= message.getSize() || index < -1){
                        System.out.println(index +" is not a valid position");
                        break;
                    }
                    if (index == -1) { //-1 to insert at top
                        message.addAtTop(cmd[1].charAt(0));
                    }
                    else if (index < -1){
                        System.out.println("Improper bounds");
                        break;
                    }
                    else {
                        message.insertAfter(index, cmd[1].charAt(0));
                    }
                    execudedCmds.add(command); //adding cmd to list
                }
                catch (NumberFormatException e) {
                    System.out.println("Position must be an int");
                    break;
                }
                break;
            case "r": //remove
                if (cmd.length != 2) { //ensuring proper length
                    System.out.println("Remove has 2 param");
                    break;
                }
                if (cmd[1].toString().length() > 1){ //checking char
                    System.out.println("Remove one char only");
                    break;
                }

                char str = cmd[1].charAt(0); //casting char to remove
                int i = message.getSize(); //counter

                while (i > 0) {
                    i--;
                    //conditional checking for equivalence at each
                    //position. If eqiv then adds position to
                    //list of commands
                    if (message.get(i).getData().equals(cmd[1].charAt(0)))
                        execudedCmds.add("r " + cmd[1].charAt(0) +
                                " " + ((i)));

                }
                //removing all occurences
                message.removeAll(str);
                break;
            case "p": //paste
                if (cmd.length != 3) { //ensuring proper length
                    System.out.println("Paste has three param");
                    break;
                }
                if (paste(cmd[1], cmd[2])) { //checking paste condition
                int clipNum = Integer.parseInt(cmd[2]); //casting
                //adding to command list
                    execudedCmds.add("p :" +
                            clipBoards.get(clipNum).display() +
                            ":" + cmd[1]);
                }
                break;
            case "c": //copy
                try {
                    int start = Integer.parseInt(cmd[1].trim());
                    int end = Integer.parseInt(cmd[2].trim());

                    //ensuring bounds are valid
                    if (end < start || end > message.getSize() - 1) {
                        System.out.println("Improper End Bound");
                        break;
                    }
                    if (start < 0 || start > end) {
                        System.out.println("Improper Starting Bound");
                        break;
                    }
                    //calling copy() if bounds are valid
                    copy(cmd[1], cmd[2], cmd[3]);

                } catch (RuntimeException e) {
                    System.out.println("Please format cmd properly");
                }
                break;
            case "s": //save
                System.out.println("File Saved");
                save(); //method to write cmd list
                break;
            default: //default for any unrecognized commands
                System.out.println("Please enter a valid command");
                break;
        }
        return message.display();
    }

    /*****************************************************************
     Overrides object toString to out put a formatted string string
     which includes message and numbers

     @return result, a two line string
     @throws IndexOutOfBoundsException
     *****************************************************************/
    public String toString() {
        String index = ""; //creating parts to concat
        String result;
        String msg = "";

        for (int i = 0; i < message.getSize(); i++) {
            msg = msg + message.get(i).getData() + "   ";
            if (i < 10) { //setting number spacing based on size
                index = index + i + "   ";
            } else {
                index = index + i + "  ";
            }
        }
        result = "Message: " + "\n \t" + index + "\n \t"
                + msg;
        return result;
    }

    /*****************************************************************
     Returns the unformatted message as a string. Used in various
     methods to use message in an unformatted setting

     @return message
     *****************************************************************/
    public String getMessage() {
        return message.display();
    }

    /*****************************************************************
     Main method for encrypting the message. Promps the user and sends
     and sends commands to processCommand()

     @throws RuntimeException
     @throws IOException
     *****************************************************************/
    public void mixture() {
        System.out.printf("Enter initial message you wish" +
                " to scramble: ");
        setInitialMessage(scanner.nextLine().trim()); //setting msg
        System.out.println(toString()); //disp fmt msg and numbers
        System.out.println("Enter a command or type q to quit");
        System.out.println("To see a list of commands type help");
        System.out.print("Command:" + "\n \t");

        do { //loops as long as input is available
            processCommand(scanner.nextLine()); //executing cmd
            System.out.println(toString()); //displaying result
            System.out.printf("Command:" + "\n \t");
        } while (scanner.hasNext());
    }

    /*****************************************************************
     Method which is called by proccessCommand, pastes a clipboard's
     content after the specified location

     @param position      after which to paste
     @param pClipboardNum the clipboard id
     @return isValid command
     @throws RuntimeException
     *****************************************************************/
    public boolean paste(String position, String pClipboardNum) {
        boolean isValid = false;
        try {
            int pos = Integer.parseInt(position); //getting int pos
            int clipBoardNum = Integer.parseInt(pClipboardNum);

            //no signifcance other than to throw an exception
            clipBoards.get(clipBoardNum);

            //informing user that specified clipboard is empty
            if (clipBoards.get(clipBoardNum).get(0)==null){
                System.out.println("That clipboard is empty");
            }

            //appending message for ever char in the list
            for (int i = 0; i < clipBoards.get
                    (clipBoardNum).getSize(); i++)
            {
                if (pos == -1){ //adding to top
                    message.addAtTop(clipBoards.get(clipBoardNum)
                            .get(i).getData().toString().charAt(0));
                }else { //adding after position
                    message.insertAfter(pos, clipBoards.get(clipBoardNum)
                            .get(i).getData().toString().charAt(0));
                }
                pos++;
                isValid =true;
            }
        } catch (RuntimeException e){
            System.out.println("Format for paste cmd is incorrect");
        }

        return isValid;
    }

    /*****************************************************************
     Copies the contents of message on the specific interval to a
     clipboard in clipBoardList. Creates a new clipboard if one does
     not exist, overwrites clipboard otherwise.

     @param pFirst        first char to be copied
     @param pLast         last char to be copied
     @param pClipBoardNum clipboard identifier
     @throws IndexOutOfBoundsException
     *****************************************************************/
    public void copy(String pFirst, String pLast, String pClipBoardNum) {
        int first = Integer.parseInt(pFirst);
        int last = Integer.parseInt(pLast);
        int clipBoardNum = Integer.parseInt(pClipBoardNum);

        if (clipBoardNum > 999){
            System.out.println("Please don't be difficult");
            return;
        }
        LinkedList temp = new LinkedList();
        try { //trying to set temp to CB at loc and clearing it
            temp = clipBoards.get(clipBoardNum);
            temp.clear();
        } catch (IndexOutOfBoundsException e) {
            //if a CB does not exist, creates CB up to loc
            for (int i = clipBoards.size(); i < clipBoardNum; i++) {
                clipBoards.add(new LinkedList());
            }
        } finally {
            //lastly adds items on the specified interval to the list
            for (int i = first; i <= last; i++) {
                temp.add(message.get(i).getData());
            }
            clipBoards.add(temp);
            System.out.println("Contents of CB " + clipBoardNum + ": "
                    + clipBoards.get(clipBoardNum).display());
        }
    }

    /*****************************************************************
     Save the array of proccessed commands to a file to be used in
     the unMix class

     @throws IOException
     *****************************************************************/
    public void save() {
        PrintWriter out = null;
        String encryptedFile = "encryptedFile.txt";
        try {
            out = new PrintWriter(new BufferedWriter
                    (new FileWriter(encryptedFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < execudedCmds.size(); i++) {
            out.println(execudedCmds.get(i));
        }
        out.close();
    }

    /*****************************************************************
     Main method that creates the class object and calls mixture
     @param args
     @throws RuntimeException
     @throws IOException
     *****************************************************************/
    public static void main(String[] args) {
        Mix m = new Mix();
        m.mixture();
    }
}
