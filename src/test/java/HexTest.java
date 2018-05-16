import Model.hexGame;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class HexTest {

    @Test
    public void settergetter(){
        hexGame hx = new hexGame();

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
            hexGame hx = new hexGame();

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
            assertFalse(testfile.exists());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void moves(){
        hexGame hx = new hexGame();

        hx.init();
        for (int i=0;i<5;i++) {
            int[]e = {i,0};
            hx.setCell(i, 0, (int) Math.pow(2, i+1));
            hx.Add(e);
            hx.setCell(i,1,(int)Math.pow(2, i+1));
            e[1]++;
            hx.Add(e);
        }
        ArrayList<int[]>mockline = new ArrayList<>();
        for (int i=0;i<5;i++){
            int[] e={i,0};
            mockline.add(e);
            e[1]++;
            mockline.add(e);
         }
        for (int i=0;i<mockline.size();i++) {
        int[] a,b;
        a=hx.getLine().get(i);
        b=mockline.get(i);
        assertEquals(a[0],b[0]);
        assertEquals(a[1],b[1]);
        }
        hx.finish();
        assertEquals(hx.getCell(4,1),32);
        assertEquals(hx.getScore(),62);
    }

    @Test
    public void Equtest(){
        hexGame hx = new hexGame();
        hx.init();
        hx.setCell(0,0,2);
        hx.setCell(0,1,2);
        int[] e={0,0};
        hx.Add(e);
        e[1]++;
        hx.Add(e);

        hx.finish();

        System.out.println(hx.getScore());

        //assertEquals(4,hx.getCell(e[0],e[1]));
        //assertEquals(4,hx.getScore());

    }
    @Test
    public void Overtest(){
        hexGame hx = new hexGame();

        hx.init();
        hx.setCell(0,0,2);
        hx.setCell(0,1,2);
        hx.setCell(0,2,2);
        int[] e={0,0};
        hx.Add(e);
        e[1]++;
        hx.Add(e);
        e[1]++;
        hx.Add(e);
        hx.finish();
        // assertEquals(4,hx.getCell(e[0],e[1]));
        //assertEquals(6,hx.getScore());

    }

    @Test
    public void doubletest(){
        hexGame hx = new hexGame();
        hx.init();
        hx.setCell(0,0,4);
        hx.setCell(0,1,4);
        hx.setCell(0,2,4);
        hx.setCell(0,3,4);
        int[] e= {0,0};
        hx.Add(e);
        e[1]++;
        hx.Add(e);
        e[1]++;
        hx.Add(e);
        e[1]++;
        hx.Add(e);
        hx.finish();
        int cell=hx.getCell(0,3);
        //assertEquals(16,cell);
        //assertEquals(16,hx.getScore());

    }



}
