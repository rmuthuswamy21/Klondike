package cs3500.reversi.model;

import java.util.Objects;

/**
 * Represents a point in a square grid.
 */
public class SquareCustomPoint implements CustomPoint2D {
  private final int x;
  private final int y;

  public SquareCustomPoint(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Returns the q coordinate of this point.
   *
   * @return the q coordinate
   */
  public final int getDim1() {
    return this.x;
  }

  /**
   * Returns the r coordinate of this point.
   *
   * @return the r coordinate
   */
  public final int getDim2() {
    return this.y;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof CustomPoint2D) {
      CustomPoint2D other = (CustomPoint2D) o;
      return this.x == other.getDim1() && this.y == other.getDim2();
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

  @Override
  public String toString() {
    return this.getDim1() + ", " + this.getDim2();
  }
}
