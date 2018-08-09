/**
 * A Player who modifies the map Manages an inventory of Blocks Maintains a
 * position in the map (by maintaining the current tile that the Builder is on)
 *
 */
public class Builder {

    /**
     * Create a builder. <br/>
     * Set the name of the Builder (such that getName() == name) and the current
     * tile to startingTile (such that getCurrentTile() == startingTile).
     * 
     * @param name         name of the builder (returned by getName())- cannot be
     *                     null
     * @param startingTile the tile the builder starts in - cannot be null
     */
    public Builder(java.lang.String name, Tile startingTile) {
    }

    /**
     * Create a builder <br/>
     * Set the name of the Builder (such that getName() == name) and the current
     * tile to startingTile (such that getCurrentTile() == startingTile). <br/>
     * Copy the starting inventory into the builder's inventory, such that the
     * contents of getInventory() are identical to startingInventory.
     * 
     * @param name              name of the builder (returned by getName()) - cannot
     *                          be null
     * @param startingTile      the tile the builder starts in - cannot be null
     * @param startingInventory the starting inventory (blocks) - cannot be null
     * @throws InvalidBlockException if for any Block (block) in startingInventory,
     *                               block.isCarryable() == false
     */
    public Builder(java.lang.String name, Tile startingTile, java.util.List<Block> startingInventory)
            throws InvalidBlockException {
    }

    /**
     * Get the Builder's name
     * 
     * @return the Builder's name
     */
    public java.lang.String getName() {
    }

    /**
     * Get the current tile that the builder is on
     * 
     * @return the current tile
     */
    public Tile getCurrentTile() {
    }

    /**
     * What is in the Builder's inventory
     * 
     * @return blocks in the inventory
     */
    public java.util.List<Block> getInventory() {
    }

    /**
     * Drop a block from inventory on the top of the current tile <br/>
     * Blocks can only be dropped on tiles with less than 8 blocks, or tiles with
     * less than 3 blocks if a GroundBlock. <br/>
     * Note: the current tile is that given by getCurrentTile() and the index should
     * refer to an item in the list returned by getInventory() <br/>
     * Handle the following cases:
     * <ol>
     * <li>If the inventoryIndex is &lt; 0 or ≥ the inventory size, throw an
     * InvalidBlockException.</li>
     * <li>If there are more than 8 blocks on the current tile, throw a
     * TooHighException.</li>
     * <li>If there are more than 3 blocks on the current tile, and the inventory
     * block is a GroundBlock, throw a TooHighException</li>
     * </ol>
     * Hint: call Tile.placeBlock, after checking the inventory
     * 
     * @param inventoryIndex the index in the inventory to place
     * @throws InvalidBlockException if the inventoryIndex is out of the inventory
     *                               range
     * @throws TooHighException      if there are 8 blocks on the current tile
     *                               already, or if the block is an instance of
     *                               GroundBlock and there are already 3 blocks on
     *                               the current tile.
     */
    public void dropFromInventory(int inventoryIndex) throws InvalidBlockException, TooHighException {
    }

    /**
     * Attempt to dig in the current tile and add tile to the inventory <br/>
     * If the top block (given by getCurrentTile().getTopBlock()) is diggable,
     * remove the top block of the tile and destroy it, or add it to the end of the
     * inventory (given by getInventory()). <br/>
     * Handle the following cases:
     * <ol>
     * <li>If there are no blocks on the current tile, throw a TooLowException</li>
     * <li>If the top block is not diggable, throw a InvalidBlockException</li>
     * <li>If the top block is not carryable, remove the block, but do not add it to
     * the inventory.</li>
     * </ol>
     * Hint: call Tile.dig()
     * 
     * @throws TooLowException       if there are no blocks on the current tile.
     * @throws InvalidBlockException if the top block is not diggable
     */
    public void digOnCurrentTile() throws TooLowException, InvalidBlockException {
    }

    /**
     * Check if the Builder can enter a tile from the current tile. <br/>
     * Returns true if:
     * <ol>
     * <li>the tiles are connected via an exit (i.e. there is an exit from the
     * current tile to the new tile), and</li>
     * <li>the height of the new tile (number of blocks) is the same or different by
     * 1 from the current tile (i.e. abs(current tile height - new tile) &lt;= 1)
     * </li>
     * </ol>
     * If newTile is null return false.
     * 
     * @param newTile the tile to test if we can enter
     * @return true if the tile can be entered
     */
    public boolean canEnter(Tile newTile) {
    }

    /**
     * move the builder to a new tile. <br/>
     * If canEnter(newTile) == true then change the builders current tile to be
     * newTile. (i.e. getCurrentTile() == newTile) <br/>
     * If canEnter(newTile) == false then throw a NoExitException.
     * 
     * @param newTile the tile to move to
     * @throws NoExitException if canEnter(newTile) == false
     */
    public void moveTo(Tile newTile) throws NoExitException {
    }

}