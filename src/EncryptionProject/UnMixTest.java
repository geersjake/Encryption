package EncryptionProject;

import org.junit.Test;

/*****************************************************************
 Junit4 test class to test the various methods of the Unmix class

 @author Jake Geers
 @version 4/3/2016
 *****************************************************************/
public class UnMixTest extends UnMix {

    Mix mix;
    UnMix unMix;


    @org.junit.Before
    public void setUp() throws Exception {
        mix = new Mix();
        unMix = new UnMix();


    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @Test
    /*****************************************************************
     Tests that the orginal message matches the unMixed one
     @param
     @throws
     @return
     *****************************************************************/
    public void testUndoProcessCommand() throws Exception {


        /*Mix message = new Mix();
        message.setInitialMessage ("This is a secret message");
        String userMessage = message.processCommand("a a 0");
        message.processCommand("s testIt");

        UnMix unMessage = new UnMix();
        String original = unMessage.UnMixUsingFile ("testIt", userMessage);
        assertEquals(original, "This is a secret message");*/

    }

 /*   @Test
    public void testUndoRemoveAtTailAndTop() throws Exception {
        mix.setInitialMessage("Abba");
        mix.processCommand("r a");
        mix.processCommand("s");

        unMix.setMessage(mix.getMessage());
        unMix.load();
        unMix.UnMixMessage(unMix.getExecutedCmds(), unMix.getMessage());

        assertEquals("Abba", );
    }*/
}