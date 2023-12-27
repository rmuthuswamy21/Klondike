package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A square model for the game of Reversi, following normal reversi rules.
 */
public class SquareReversiModel extends AbstractReversiModel {

  /**
   * Creates a model for the game of Reversi in a given state defined by board and turn.
   * Size must be odd, or the shape does not work.
   *
   * @param size  the size of the board (number of rows, width of longest row)
   * @param board the state of the board
   * @param turn  whose turn it currently is
   */
  public SquareReversiModel(int size, Map<CustomPoint2D, PlayerTile> board, PlayerTile turn) {
    super(size, board, turn); // verifies that size is an even number
  }


  /**
   * Creates a model for the game of Reversi in the default starting state.
   * Size must be odd, or the shape does not work.
   *
   * @param size the size of the board (number of rows, width of longest row)
   */
  public SquareReversiModel(int size) {
    super(size);
  }

  @Override
  protected boolean isValidSize(int size) {
    return (size % 2 == 0 && size >= 4);
  }

  @Override
  public void placeInitialTiles() {
    this.board.put(new SquareCustomPoint(size / 2 - 1, size / 2 - 1), PlayerTile.FIRST);
    this.board.put(new SquareCustomPoint(size / 2 - 1, size / 2), PlayerTile.SECOND);
    this.board.put(new SquareCustomPoint(size / 2, size / 2 - 1), PlayerTile.SECOND);
    this.board.put(new SquareCustomPoint(size / 2, size / 2), PlayerTile.FIRST);
  }

  @Override
  public boolean validCoordinates(CustomPoint2D point) {
    return (point.getDim1() >= 0 && point.getDim1() < this.size
            && point.getDim2() >= 0 && point.getDim2() < this.size);
  }


  @Override
  public List<CustomPoint2D> getValidTiles() {
    ArrayList<CustomPoint2D> validTiles = new ArrayList<>();
    for (int i = 0; i < this.size; i++) {
      for (int j = 0; j < this.getRowWidth(i); j++) {
        validTiles.add(new SquareCustomPoint(j, i));
      }
    }
    return validTiles;
  }

  @Override
  public int getRowWidth(int row) {
    if (row < 0 || row >= this.size) {
      throw new IllegalArgumentException("Row out of bounds");
    }
    return this.size;
  }

  @Override
  protected boolean isValidNeighbor(int neighborQ, int neighborR) {
    System.out.println("Was valid neighbor.");
    return (neighborQ != 0 || neighborR != 0);
  }

  @Override
  public BoardShape getBoardShape() {
    return BoardShape.SQUARE;
  }
}
