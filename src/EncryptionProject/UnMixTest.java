package EncryptionProject;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
    public void testUndoProcessCommand() throws Exception {

        unMix.setMessage("zthis ishis a scrt");
        unMix.load();
        unMix.UnMixMessage(unMix.getExecutedCmds(), unMix.getMessage());
        assertEquals("this is a secret", unMix.getMessage().display());

    }

    @Test
    public void testUndoRemoveAtTailAndTop() throws Exception {
        mix.setInitialMessage("Abba");
        mix.processCommand("r a");
        mix.processCommand("s");

        unMix.setMessage(mix.getMessage());
        unMix.load();
        unMix.UnMixMessage(unMix.getExecutedCmds(), unMix.getMessage());

        assertEquals("Abba",unMix.getMessage().display() );
    }

    @Test
    public void testDecodingMultiple2(){
        Mix message = new Mix();
        message.setInitialMessage("The world is on fire");
        message.processCommand("r p");
        message.processCommand("c 4 7 2");
        message.processCommand("p 6 2");
        message.processCommand("p 6 2");
        message.processCommand("r e");
        message.processCommand("a j 8");
        message.processCommand("a f 10");
        message.processCommand("a s 4");
        message.processCommand("a v 1");
        message.processCommand("a 1ss 3");
        message.processCommand("a : 9");
        message.processCommand("c 4 8 3");
        message.processCommand("s");

        String mixedMsg = message.getMessage();
        System.out.println(mixedMsg);

        unMix.load();
        unMix.setMessage(mixedMsg);
        unMix.UnMixMessage(unMix.getExecutedCmds(), unMix.getMessage());
        assertEquals("The world is on fire",
                unMix.getMessage().display());
    }
}