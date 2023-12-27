package cs3500.reversi.view;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Optional;

import cs3500.reversi.model.CustomPoint2D;

/**
 * Represents a decorator for a panel.
 */
public interface Panel {

  void drawBoard(Graphics g);

  Optional<CustomPoint2D> getSelectedPoint();

  /**
   * Selects the tile on this panel at logicalP. If logicalP is not on the board, deselects.
   *
   * @param logicalP the logical coordinates of the tile to select
   */
  void selectTile(Point2D logicalP);

  /**
   * Adds a mouse listener that will call selectTile on the given listener when a click happens.
   *
   * @param features listener to call selectTile on when mouse click
   */
  void addFeatures(ViewFeatures features);

  int getValueAtSelectedPoint();

  double getScaledXForSelectedPoint();

  double getScaledYForSelectedPoint();

  AffineTransform transformPhysicalToLogical();
}
