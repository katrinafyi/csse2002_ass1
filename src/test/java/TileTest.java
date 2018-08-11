import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * TileTest
 */
public class TileTest {

    @Test
    public void testConstructor1() {
        Tile t = new Tile();
        List<Block> b = t.getBlocks();
        assertEquals("Not 3 initial blocks.", 3, b.size());
        // We can't use assertEquals() with a List<Block> because .equals()
        // for Block compares references, not types.
        assertTrue("First block not soil.", b.get(0) instanceof SoilBlock);
        assertTrue("Second block not soil.", b.get(1) instanceof SoilBlock);
        assertTrue("Third block not grass.", b.get(2) instanceof GrassBlock);
        assertEquals("Initial exits exist.", 0, t.getExits().size());
    }

    @Test
    public void testConstructor2() {
        List<Block> startingBlocks = new ArrayList<Block>();
        startingBlocks.add(new GrassBlock());
        startingBlocks.add(new GrassBlock());
        startingBlocks.add(new GrassBlock());

        boolean thrown = false;
        Tile t;
        try {
            t = new Tile(startingBlocks);
        } catch (TooHighException e) {
            thrown = true;
        }
        assertFalse("3 grass blocks threw.", thrown);

        startingBlocks.add(new WoodBlock());
        try {
            t = new Tile(startingBlocks);
        } catch (TooHighException e) {
            thrown = true;
        }
        assertFalse("3 grass blocks, 1 wood threw.", thrown);

        startingBlocks.remove(startingBlocks.size()-1);
        startingBlocks.add(new GrassBlock());
        try {
            t = new Tile(startingBlocks);
        } catch (TooHighException e) {
            thrown = true;
        }
        assertTrue("4 grass blocks, didn't throw.", thrown);
        thrown = false;

        startingBlocks.remove(startingBlocks.size()-1);
        for (int i = 0; i < 5; i++) {
            startingBlocks.add(new WoodBlock());
        }
        try {
            t = new Tile(startingBlocks);
        } catch (TooHighException e) {
            thrown = true;
        }
        assertFalse("3 grass blocks, 5 wood blocks threw.", thrown);

        startingBlocks.add(new WoodBlock());
        try {
            t = new Tile(startingBlocks);
        } catch (TooHighException e) {
            thrown = true;
        }
        assertTrue("3 grass blocks, 6 wood blocks, didn't throw.", thrown);
    }

    @Test
    public void testExits() throws NoExitException {
        Tile t = new Tile();

        boolean thrown = false;
        try {
            t.addExit(null, t);
        } catch (NoExitException e) {
            thrown = true;
        }
        assertTrue("Null exit name should throw but didn't.", thrown);
        thrown = false;

        try {
            t.addExit("exit name", null);
        } catch (NoExitException e) {
            thrown = true;
        }
        assertTrue("Null target tile should throw but didn't.", thrown);
        thrown = false;

        Tile other = new Tile();
        t.addExit("name", other);
        assertTrue("New exit not stored.", t.getExits().containsKey("name"));
        assertEquals("New exit is the wrong tile.", other, t.getExits().get("name"));

        Tile newTarget = new Tile();
        t.addExit("name", newTarget);
        assertEquals("Target with same name not overwritten.",
                     newTarget, t.getExits().get("name"));
    }

    @Test
    public void testGetBlocks() throws TooHighException {
        List<Block> b = Arrays.asList((Block)new SoilBlock(), new WoodBlock());
        Tile t = new Tile(b);
        assertEquals("Incorrect blocks.", b, t.getBlocks());
    }

    @Test
    public void testGetExits() throws NoExitException {
        Tile t = new Tile();
        t.addExit("up", t);
        t.addExit("down", t);

        Map<String, Tile> m = new HashMap<>();
        m.put("down", t);
        m.put("up", t);

        assertEquals("Incorrect exits.", m, t.getExits());
    }

    @Test
    public void testGetTopBlock() throws TooHighException, TooLowException {
        Tile t = new Tile(new ArrayList<Block>());

        boolean thrown = false;
        try {
            t.getTopBlock();
        } catch (TooLowException e) {
            thrown = true;
        }
        assertTrue("Get top block of empty tile didn't throw.", thrown);
        thrown = false;

        Block top = new WoodBlock();
        t = new Tile(Arrays.asList((Block)new SoilBlock(), top));
        assertEquals("Incorrect top block.", top, t.getTopBlock());
    }

    @Test
    public void testRemoveTopBlock() throws TooLowException, TooHighException {
        Tile t = new Tile(new ArrayList<Block>());

        boolean thrown = false;
        try {
            t.removeTopBlock();
        } catch (TooLowException e) {
            thrown = true;
        }
        assertTrue("Remove top block of empty tile didn't throw.", thrown);
        thrown = false;

        List<Block> blockList = new ArrayList<Block>();
        blockList.add(new SoilBlock());
        blockList.add(new WoodBlock());

        Block bottomBlock = blockList.get(0);
        t = new Tile(blockList);
        t.removeTopBlock();
        assertEquals(
            "Top block incorrectly removed.",
            Arrays.asList(bottomBlock),
            t.getBlocks()
        );
    }

    @Test
    public void testRemoveExit() throws NoExitException {
        Tile t = new Tile();
        t.addExit("up", t);
        t.addExit("right", t);

        boolean thrown = false;
        try {
            t.removeExit(null);
        } catch (NoExitException e) {
            thrown = true;
        }
        assertTrue("Removing null exit didn't throw.", thrown);
        thrown = false;

        try {
            t.removeExit("down");
        } catch (NoExitException e) {
            thrown = true;
        }
        assertTrue("Removing non-existent exit didn't throw.", thrown);
        thrown = false;

        Map<String, Tile> expected = new HashMap<String,Tile>();
        expected.put("right", t);

        t.removeExit("up");
        assertEquals("Exit not removed correctly.", expected, t.getExits());
    }

    @Test
    public void testDig()
            throws TooLowException, InvalidBlockException, TooHighException {
        Tile t = new Tile(new ArrayList<Block>());
        boolean thrown = false;
        try {
            t.dig();
        } catch (TooLowException e) {
            thrown = true;
        }
        assertTrue("Digging empty tile didn't throw.", thrown);
        thrown = false;

        t = new Tile(Arrays.asList((Block)new StoneBlock()));
        try {
            t.dig();
        } catch (InvalidBlockException e) {
            thrown = true;
        }
        assertTrue("Digging undiggable block didn't throw.", thrown);
        thrown = false;

        List<Block> blocks = Arrays.asList(
            (Block)new WoodBlock(), new SoilBlock());
        t = new Tile(blocks);
        Block b = t.dig();
        assertEquals("Digging removed incorrect block.", blocks.get(1), b);
        assertEquals(
            "Digging resulted in the incorrect blocks.",
            Arrays.asList(blocks.get(0)), t.getBlocks());
    }

    @Test
    public void testPlaceBlock() throws TooHighException, InvalidBlockException {
        Tile t = new Tile();
        try {
            t.placeBlock(null);
            fail("Placing null block didn't throw.");
        } catch (InvalidBlockException e) {}

        try {
            t.placeBlock(new SoilBlock());
            fail("Placing ground block above height 3 didn't throw.");
        } catch (TooHighException e) {}


    }

    @Test
    public void testMoveBlock()
            throws TooHighException, NoExitException, InvalidBlockException {
        Tile t = new Tile();

    }
}