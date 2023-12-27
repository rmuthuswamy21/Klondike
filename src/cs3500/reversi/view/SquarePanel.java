package cs3500.reversi.view;

import java.awt.geom.Path2D;

import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Class representing a graphical representation of the board of the given model using a JPanel.
 */
public class SquarePanel extends AbstractPanel {

  /**
   * Constructs a panel that represents the board of this model.
   *
   * @param model the model to represent
   */
  public SquarePanel(ReadOnlyReversiModel model) {
    super(model);
  }

  @Override
  protected Path2D drawShape(double x, double y, double scaledSize, CustomPoint2D point) {
    double[] xPoints = new double[4];
    double[] yPoints = new double[4];

    for (int i = 0; i < 4; i++) {
      double angle = Math.PI * i / 2 + Math.PI / 4; // Calculating angle for each point
      xPoints[i] = x + (scaledSize * Math.cos(angle)); // Calculating x-coordinate
      yPoints[i] = y + (scaledSize * Math.sin(angle)); // Calculating y-coordinate
    }


    Path2D square = new Path2D.Double();
    for (int i = 0; i < 4; i++) {
      if (i == 0) {
        square.moveTo(xPoints[i], yPoints[i]);
      } else {
        square.lineTo(xPoints[i], yPoints[i]);
      }
    }
    square.closePath();
    return square;
  }

  @Override
  protected double getScaledYFromR(int r) {
    return this.scaleAttrPhysicalToLogical(1.5 * r * size + buffer);
  }

  @Override
  protected double getScaledXFromQ(int q, int r) {
    return this.scaleAttrPhysicalToLogical(1.5 * q * size + buffer);
  }

}
