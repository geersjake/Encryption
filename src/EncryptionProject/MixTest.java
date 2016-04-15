package EncryptionProject;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/*****************************************************************
 Test Class which tests the vairous methods of the mix class

 @author Jake Geers
 @version 4/3/2016
 *****************************************************************/
public class MixTest extends Mix {

    /** Interface rule for exceptions */
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    /** Globally used general exception */
    Exception ex;

    /** Global instnace of Mix used */
    Mix m;

    /** Global instance of Mix used throughout */
    Mix message;

    /** Random number generation */
    Random random;


    @Before
    public void setUp() throws Exception {
        m = new Mix("hello world");
        message = new Mix("this is a secret");
        random = new Random();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConstructor() {
        assertEquals("hello world", m.getMessage());

        assertEquals("this is a secret", message.getMessage());
    }

    @Test
    public void testConstructorParam() {
        String s = "hello world";
        LinkedList helloWorld = new LinkedList(Arrays.asList
                (s.split("\\S+")));
        assert (helloWorld.equals(m.getMessageAsList()));

        String s2 = "this is a secret";
        LinkedList secret = new LinkedList(Arrays.asList
                (s2.split("\\S+")));
        assert (secret.equals(message.getMessageAsList()));
    }

    @Test
    public void testInsert() {
        try {
            for (int i = 0; i < 1000; i++) {
                int rPos = random.nextInt(
                        m.getMessageAsList().getSize());
                char rChar = (char) (48 + random.nextInt(47));
                m.processCommand("a " + rChar + " " + rPos);
            }
        } catch (Exception e) {
            ex = e;
        }
        assertEquals(null, ex);
    }

    @Test
    public void testRemove() {
        int n = message.getMessageAsList().getSize();
        Mix temp = new Mix();
        String s;
        for (int i = 6; i < n; i++) {
            int rPos = random.nextInt(m.getMessageAsList().getSize());
            s = message.processCommand("r " + rPos).toString();
            temp.setInitialMessage(s);
            message = temp;
        }
    }

    @Test
    public void proccessCMD() {
        message.processCommand("r p");//bad
        message.processCommand("r e");//good

        message.processCommand("c 4 7 99999"); //bad
        message.processCommand("c 1 3 9");//good

        message.processCommand("p 6 2"); //bad
        message.processCommand("p 6 9"); //good

        message.processCommand("a z2"); //bad
        message.processCommand("a z -1"); //good

        message.processCommand("agaskl"); // moot bad
        message.processCommand("help"); //moot good

        message.processCommand("s"); //save file for use in unmixTest

        assertEquals("zthis ishis a scrt", message.getMessage());
    }

    /** Tests after this point were created by a peer */
    @Test
    public void testAddCommand() {
        Mix message = new Mix();
        message.setInitialMessage("This is a secret message");
        String userMessage = message.processCommand("a a 0");
        assertEquals("Tahis is a secret message", userMessage);
    }

    @Test
    public void testRemoveCommand() {
        Mix message = new Mix();
        message.setInitialMessage("This is a secret message");
        String userMessage = message.processCommand("r s");
        assertEquals("Thi i a ecret meage", userMessage);
    }

    @Test
    public void testProcessCommandR1() {
        Mix message = new Mix();
        message.setInitialMessage("This is a secret message");
        String userMessage = message.processCommand("r e");
        assertEquals("This is a scrt mssag", userMessage);

    }

    @Test
    public void testProcessCommandR2() {
        Mix message = new Mix();
        message.setInitialMessage("This is a secret message");
        String userMessage = message.processCommand("r T");
        assertEquals("his is a secret message", userMessage);

    }

    @Test
    public void testProcessCommandR3() {
        Mix message = new Mix();
        message.setInitialMessage("This is a secret message");
        String userMessage = message.processCommand("r s");
        assertEquals("Thi i a ecret meage", userMessage);

    }

    @Test
    public void testProcessCommandP1() {
        Mix message = new Mix();
        message.setInitialMessage("This is a secret message");
        message.processCommand("c 1 5 1");
        String userMessage = message.processCommand("p 2 1");
        assertEquals("Thihis is is a secret message", userMessage);

    }

    @Test
    public void testProcessCommandP2() {
        Mix message = new Mix();
        message.setInitialMessage("This is a secret message");
        message.processCommand("c 1 5 999");
        String userMessage = message.processCommand("p 2 999");
        assertEquals("Thihis is is a secret message", userMessage);

    }

}