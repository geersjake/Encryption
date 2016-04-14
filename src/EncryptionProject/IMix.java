package EncryptionProject;

/*****************************************************************
 Interface for mix class used to create usable methods

 @author Jake Geers
 @version 4/3/2016
 *****************************************************************/
public interface IMix {

    /** set the original message into the linked list of characters */
    void setInitialMessage(String message);

    /** processes the given mix command and returns the current message after processing the mix command */
    String processCommand(String command);

}
