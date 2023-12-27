package cs3500.reversi.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

/**
 * Class representing tests for the SquareReversiModel.
 */
public class SquareReversiModelTest {
  ReversiModel model;

  @Before
  public void setUp() {
    this.model = new SquareReversiModel(6);
  }

  @Test
  public void testBoardThrowsExceptionWithBadSize() {
    Assert.assertThrows("Index cannot be below 0", IllegalArgumentException.class, () ->
            new SquareReversiModel(-1));
    Assert.assertThrows("Too small index", IllegalArgumentException.class, () ->
            new SquareReversiModel(2));
    Assert.assertThrows("Odd index", IllegalArgumentException.class, () ->
            new SquareReversiModel(11));
  }

  @Test
  public void testPlaceTileThrowsProperExceptions() {
    // Throws for location not even on board
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.placeTile(new AxialCustomPoint(-1, 0), PlayerTile.FIRST));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.placeTile(new AxialCustomPoint(0, -1), PlayerTile.FIRST));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.placeTile(new AxialCustomPoint(9, 0), PlayerTile.FIRST));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.placeTile(new AxialCustomPoint(0, 9), PlayerTile.FIRST));

    // Throws for location already has a tile
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model.placeTile(new AxialCustomPoint(2, 2), PlayerTile.FIRST));

    // Throws for logically invalid move
    Assert.assertThrows(IllegalStateException.class, () -> // Next to your own
            this.model.placeTile(new AxialCustomPoint(1, 2), PlayerTile.FIRST));
    Assert.assertThrows(IllegalStateException.class, () -> // Next to nothing
            this.model.placeTile(new AxialCustomPoint(4, 0), PlayerTile.FIRST));
    Assert.assertThrows(IllegalStateException.class, () -> // Next opposite, but empty tile in run
            this.model.placeTile(new AxialCustomPoint(4, 1), PlayerTile.FIRST));

    // Throws for wrong turn, but valid move
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model.placeTile(new AxialCustomPoint(3, 1), PlayerTile.SECOND));
    this.model.pass(PlayerTile.FIRST);
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model.placeTile(new AxialCustomPoint(4, 1), PlayerTile.FIRST));
  }

  @Test
  public void testPlaceTileWorks() {
    // Basic placement
    this.model.placeTile(new AxialCustomPoint(3, 1), PlayerTile.FIRST);
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(3, 1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(3, 2)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(3, 3)));
    // Flips multiple tiles in a line, as well as placing on border
    this.model.placeTile(new AxialCustomPoint(4, 1), PlayerTile.SECOND);
    Assert.assertEquals(PlayerTile.SECOND, this.model.getTileAt(new AxialCustomPoint(3, 2)));
    Assert.assertEquals(PlayerTile.SECOND, this.model.getTileAt(new AxialCustomPoint(2, 3)));
    Assert.assertEquals(PlayerTile.SECOND, this.model.getTileAt(new AxialCustomPoint(4, 1)));
    // Flips tiles even if multiple different directions
    this.model.placeTile(new AxialCustomPoint(5, 1), PlayerTile.FIRST);
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(3, 1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(4, 1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(5, 1)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(2, 2)));
    Assert.assertEquals(PlayerTile.FIRST, this.model.getTileAt(new AxialCustomPoint(3, 3)));
  }

  @Test
  public void testPassWorks() {
    Assert.assertThrows(IllegalStateException.class, () -> // wrong turn
            this.model.pass(PlayerTile.SECOND));
    this.model.pass(PlayerTile.FIRST);
    Assert.assertThrows(IllegalStateException.class, () -> // wrong turn
            this.model.pass(PlayerTile.FIRST));
    // After passing, now second player can play a valid move
    this.model.placeTile(new AxialCustomPoint(3, 4), PlayerTile.SECOND);
    Assert.assertTrue(true);
  }

  @Test
  public void testGetTileAtThrowsProperExceptions() {
    // Throws for location not even on board
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getTileAt(new AxialCustomPoint(-1, 0)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getTileAt(new AxialCustomPoint(0, -1)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getTileAt(new AxialCustomPoint(9, 0)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.getTileAt(new AxialCustomPoint(0, 9)));

    // Throws for location does not have a tile
    Assert.assertThrows(IllegalStateException.class, () ->
            this.model.getTileAt(new AxialCustomPoint(0, 0)));
  }

  @Test
  public void testPlayerScoreWhoIsWinning() {
    this.model.placeTile(new AxialCustomPoint(3, 1), PlayerTile.FIRST);
    Assert.assertEquals(4, this.model.playerScore(PlayerTile.FIRST));
    Assert.assertEquals(1, this.model.playerScore(PlayerTile.SECOND));
    Assert.assertEquals(PlayerTile.FIRST, this.model.whoIsWinning());
    this.model.placeTile(new AxialCustomPoint(4, 1), PlayerTile.SECOND);
    Assert.assertEquals(3, this.model.playerScore(PlayerTile.FIRST));
    Assert.assertEquals(3, this.model.playerScore(PlayerTile.SECOND));
    Assert.assertEquals(PlayerTile.SECOND, this.model.whoIsWinning());
    this.model.placeTile(new AxialCustomPoint(5, 1), PlayerTile.FIRST);
    Assert.assertEquals(5, this.model.playerScore(PlayerTile.FIRST));
    Assert.assertEquals(2, this.model.playerScore(PlayerTile.SECOND));
    Assert.assertEquals(PlayerTile.FIRST, this.model.whoIsWinning());
    this.model.placeTile(new AxialCustomPoint(5, 0), PlayerTile.SECOND);
    Assert.assertEquals(4, this.model.playerScore(PlayerTile.FIRST));
    Assert.assertEquals(4, this.model.playerScore(PlayerTile.SECOND));
    Assert.assertEquals(PlayerTile.SECOND, this.model.whoIsWinning());
    this.model.placeTile(new AxialCustomPoint(4, 2), PlayerTile.FIRST);
    Assert.assertEquals(6, this.model.playerScore(PlayerTile.FIRST));
    Assert.assertEquals(3, this.model.playerScore(PlayerTile.SECOND));
    Assert.assertEquals(PlayerTile.FIRST, this.model.whoIsWinning());
    this.model.placeTile(new AxialCustomPoint(4, 3), PlayerTile.SECOND);
    Assert.assertEquals(4, this.model.playerScore(PlayerTile.FIRST));
    Assert.assertEquals(6, this.model.playerScore(PlayerTile.SECOND));
    Assert.assertEquals(PlayerTile.SECOND, this.model.whoIsWinning());
  }

  @Test
  public void testIsSpotEmpty() {
    // Throws for location not even on board
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.isSpotEmpty(new AxialCustomPoint(-1, 0)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.isSpotEmpty(new AxialCustomPoint(0, -1)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.isSpotEmpty(new AxialCustomPoint(9, 0)));
    Assert.assertThrows(IllegalArgumentException.class, () ->
            this.model.isSpotEmpty(new AxialCustomPoint(0, 9)));

    Assert.assertFalse(this.model.isSpotEmpty(new AxialCustomPoint(2, 2)));
    Assert.assertTrue(this.model.isSpotEmpty(new AxialCustomPoint(0, 0)));
    Assert.assertTrue(this.model.isSpotEmpty(new AxialCustomPoint(1, 0)));
  }

  @Test
  public void testGetRowWidth() {
    Assert.assertEquals(6, this.model.getRowWidth(0));
    Assert.assertEquals(6, this.model.getRowWidth(1));
    Assert.assertEquals(6, this.model.getRowWidth(2));
    Assert.assertEquals(6, this.model.getRowWidth(3));
    Assert.assertEquals(6, this.model.getRowWidth(4));
    Assert.assertEquals(6, this.model.getRowWidth(5));
  }

  @Test
  public void testPlayerMoves() {
    this.model = new SquareReversiModel(4);
    Assert.assertEquals(Set.of(new AxialCustomPoint(1, 3), new AxialCustomPoint(0, 2),
            new AxialCustomPoint(3, 1), new AxialCustomPoint(2, 0)),
            this.model.playerMoves(PlayerTile.FIRST));
    this.model.placeTile(new AxialCustomPoint(2, 0), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(3, 0), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(3, 1), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(3, 2), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(2, 3), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(1, 3), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(0, 1), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(0, 2), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(0, 3), PlayerTile.FIRST);
    this.model.placeTile(new AxialCustomPoint(0, 0), PlayerTile.SECOND);
    this.model.placeTile(new AxialCustomPoint(1, 0), PlayerTile.FIRST);
    this.model.pass(PlayerTile.SECOND); // out of moves for second player
    this.model.placeTile(new AxialCustomPoint(3, 3), PlayerTile.FIRST);
    Assert.assertEquals(Set.of(), this.model.playerMoves(PlayerTile.FIRST));
    Assert.assertEquals(Set.of(), this.model.playerMoves(PlayerTile.SECOND));

  }
}
