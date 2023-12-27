package cs3500.reversi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

/**
 * A basic model for the game of Reversi.
 * Has two players, uses a hexagon of pointy-top hexagons, and follows normal Reversi rules.
 */
public class BasicReversiModel extends AbstractReversiModel {

  /**
   * Creates a model for the game of Reversi in a given state defined by board and turn.
   * Size must be odd, or the shape does not work.
   *
   * @param size  the size of the board (number of rows, width of longest row)
   * @param board the state of the board
   * @param turn  whose turn it currently is
   */
  public BasicReversiModel(int size, Map<CustomPoint2D, PlayerTile> board, PlayerTile turn) {
    super(size, board, turn);
  }

  /**
   * Creates a model for the game of Reversi in the default starting state.
   * Size must be odd, or the shape does not work.
   *
   * @param size the size of the board (number of rows, width of longest row)
   */
  public BasicReversiModel(int size) {
    super(size);
  }

  /**
   * Verifies the inputted size is a valid size.
   */
  @Override
  protected boolean isValidSize(int size) {
    return (size % 2 == 1 && size >= 5);
  }

  @Override
  public void placeInitialTiles() {
    for (int neighbor_q = -1; neighbor_q < 2; neighbor_q++) {
      for (int neighbor_r = -1; neighbor_r < 2; neighbor_r++) {
        if (neighbor_q != neighbor_r) {
          this.board.put(new AxialCustomPoint(neighbor_q, neighbor_r), this.getTurn());
          this.pass(this.getTurn());
        }
      }
      this.pass(this.getTurn());
    }
  }

  @Override
  public int getRowWidth(int row) {
    if (validCoordinates(new AxialCustomPoint(0, row))) {
      return this.size - abs(row);
    } else {
      throw new IllegalArgumentException("Tried to get width of non-existent row.");
    }
  }

  @Override
  public List<CustomPoint2D> getValidTiles() {
    List<CustomPoint2D> toReturn = new ArrayList<>();
    int widthNeg = (this.getBoardSize() / -2);
    int widthPos = (this.getBoardSize() / 2);
    int addToQ = 0;
    for (int r = widthNeg; r < 0; r++) {
      for (int q = addToQ; q < this.getRowWidth(r) + addToQ; q++) {
        toReturn.add(new AxialCustomPoint(q, r));
      }
      addToQ -= 1;
    }

    for (int r = 0; r <= widthPos; r++) {
      for (int q = widthNeg; q <= widthPos - r; q++) {
        toReturn.add(new AxialCustomPoint(q, r));
      }
    }
    return toReturn;
  }

  /**
   * Returns whether the coordinates are valid.
   *
   * @param point the point to check for validity
   * @return true if valid, false if not
   */
  @Override
  protected boolean validCoordinates(CustomPoint2D point) {
    return abs(point.getDim1()) <= (this.getBoardSize() - 1) / 2  // relies on odd size invariant
            && abs(point.getDim2()) <= (this.getBoardSize() - 1) / 2;
  }

  /**
   * Returns whether the given coordinates are valid neighbors.
   * @param neighborQ the q-axis coordinate of the neighbor
   * @param neighborR the r-axis coordinate of the neighbor
   * @return true if valid, false if not
   */
  @Override
  protected boolean isValidNeighbor(int neighborQ, int neighborR) {
    return neighborQ != neighborR;
  }

  @Override
  public BoardShape getBoardShape() {
    return BoardShape.HEXAGON;
  }

}
