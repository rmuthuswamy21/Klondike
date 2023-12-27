package cs3500.reversi.view;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Optional;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import cs3500.reversi.model.CustomPoint2D;

/**
 * Represents a decorator for a panel.
 */
public abstract class PanelDecorator extends JPanel implements Panel {
  private final Panel p;

  PanelDecorator(Panel p) {
    this.p = p;
  }

  @Override
  protected void paintComponent(Graphics g) {
    p.drawBoard(g);
  }

  @Override
  public void drawBoard(Graphics g) {
    p.drawBoard(g);
  }

  @Override
  public Optional<CustomPoint2D> getSelectedPoint() {
    return p.getSelectedPoint();
  }

  @Override
  public int getValueAtSelectedPoint() {
    return p.getValueAtSelectedPoint();
  }

  @Override
  public double getScaledXForSelectedPoint() {
    return p.getScaledXForSelectedPoint();
  }

  @Override
  public double getScaledYForSelectedPoint() {
    return p.getScaledYForSelectedPoint();
  }

  @Override
  public void selectTile(Point2D logicalP) {
    p.selectTile(logicalP);
  }

  @Override
  public void addFeatures(ViewFeatures features) {
    p.addFeatures(features);
    this.addMouseListener(new MouseInputAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        Point2D physicalP = e.getPoint();
        Point2D logicalP = p.transformPhysicalToLogical().transform(physicalP, null);
        features.selectTile(logicalP);
      }
    });
  }

  @Override
  public AffineTransform transformPhysicalToLogical() {
    return p.transformPhysicalToLogical();
  }
}
