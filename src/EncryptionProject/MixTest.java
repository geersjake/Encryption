package EncryptionProject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/*****************************************************************
 Test Class which tests the vairous methods of the mix class

 @author Jake Geers
 @version 4/3/2016
 *****************************************************************/
public class MixTest extends Mix {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    /*****************************************************************
     tests that the process command method correctly processes the
     command
     @param
     @throws
     @return
     *****************************************************************/
    @Test
    public void testProcessCommand() {
        Mix message = new Mix();
        message.setInitialMessage ("hello");
        message.processCommand("a a 0");
       // assertEquals("Tahis is a secret message", userMessage);
    }


}