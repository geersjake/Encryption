package EncryptionProject;

import java.util.ArrayList;

/*****************************************************************
 Interface for unmix class which provides skelton methods to be
 used in unmix

 @author Jake Geers
 @version 4/3/2016
 *****************************************************************/
public interface IUnMix {

    /** Us the parameter mixedMessage as the string point, that is,
     *  the mix message from the previous step.  The second
     *  parameter is the file where the list of undo commands is found.
     *  The returned string should be the original message. */
    String UnMixMessage (ArrayList executedCmds, LinkedList msg);

}
