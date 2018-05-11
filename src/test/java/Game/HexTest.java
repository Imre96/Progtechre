package Game;

import Model.hexGame;
import Game.SaveHandler;
import org.junit.Test;

import javax.imageio.IIOException;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class HexTest {
    Model.hexGame hx = new hexGame();

    @Test
    public void settergetter(){
        hx.init();
        hx.setCell(1,1,0);
        assertEquals(0,hx.getCell(1,1),"the method should Set and get the correct value");
        int sav = hx.getCell(1,0);
        hx.gravitate();
        assertEquals(sav,hx.getCell(1,1),"the cell above the zero value shoud fall in its place");
        assertEquals(0,hx.getCell(1,0),"Where the cell has fallen sould be now zero");
        hx.fillUp();
        assertNotEquals(0,hx.getCell(1,0),"The Cell sould no longer be zero");
        assertEquals(0,hx.getScore(),"before actual moves the Score should be zero");
        hx.setScore(1000);
        assertEquals(1000,hx.getScore(),"the Setter should set the value");
        hx.addScore(500);
        assertEquals(1500,hx.getScore(),"the addScore should add to the existing score ");
    }
    @Test
    public void files(){
        try {
            hx.save("testsave");
            File testfile = new File("Save/testsave.xml");
            assertTrue(testfile.exists());
            hexGame other = new hexGame();
            other.load(testfile.getCanonicalPath());
            assertEquals(hx.getScore(), other.getScore());
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 6; j++) {
                    assertEquals(hx.getCell(i, j), other.getCell(i, j));
                }
            testfile.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
