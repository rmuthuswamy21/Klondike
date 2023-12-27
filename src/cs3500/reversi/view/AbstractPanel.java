package cs3500.reversi.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Represents an abstract decorator for a panel.
 */
public abstract class AbstractPanel extends JPanel implements Panel {

  protected final ReadOnlyReversiModel model;
  protected final Map<Shape, CustomPoint2D> points;
  protected Optional<CustomPoint2D> selectedPoint;
  protected double size;

  protected double buffer;

  /**
   * Constructs a panel that represents the board of this model.
   *
   * @param model the model to represent
   */
  public AbstractPanel(ReadOnlyReversiModel model) {
    this.model = model;
    this.points = new HashMap<>();
    this.selectedPoint = Optional.empty();
  }


  protected Dimension getPreferredLogicalSize() {
    return new Dimension(this.model.getBoardSize(), this.model.getBoardSize());
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1000, 1000);
  }

  @Override
  public Optional<CustomPoint2D> getSelectedPoint() {
    return this.selectedPoint;
  }

  /**
   * Transforms the logical coordinates to physical coordinates.
   *
   * @return
   */
  public AffineTransform transformLogicalToPhysical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = this.getPreferredLogicalSize();
    ret.scale(getWidth() / preferred.getWidth(), getHeight() / preferred.getHeight());
    return ret;
  }

  /**
   * Transforms the physical coordinates to logical coordinates.
   *
   * @return the transformation
   */
  public AffineTransform transformPhysicalToLogical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = this.getPreferredLogicalSize();
    ret.scale(preferred.getWidth() / getWidth(), preferred.getHeight() / getHeight());
    return ret;
  }

  public double scaleAttrPhysicalToLogical(double attr) {
    Dimension preferred = this.getPreferredLogicalSize();
    return attr * preferred.getWidth() / getWidth();
  }

  @Override
  protected void paintComponent(Graphics g) {
    this.drawBoard(g);
  }

  @Override
  public void drawBoard(Graphics g) {
    this.points.clear();
    super.paintComponent(g);
    this.size = (double) this.getWidth() / this.model.getBoardSize() / 2;
    this.buffer = this.getWidth() - this.model.getBoardSize() * Math.sqrt(3) * size;
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.transform(transformLogicalToPhysical());

    for (CustomPoint2D tile : model.getValidTiles()) {
      this.draw(g2d, tile);
    }
  }

  protected Path2D drawShape(double x, double y, double scaledSize, CustomPoint2D point) {

    double[] xPoints = new double[6];
    double[] yPoints = new double[6];

    for (int i = 0; i < 6; i++) {
      double angle = 2.0 * Math.PI * (i + 0.5) / 6;
      xPoints[i] = x + (scaledSize * Math.cos(angle));
      yPoints[i] = y + (scaledSize * Math.sin(angle));
    }

    Path2D hex = new Path2D.Double();
    for (int i = 0; i < 6; i++) {
      if (i == 0) {
        hex.moveTo(xPoints[i], yPoints[i]);
      } else {
        hex.lineTo(xPoints[i], yPoints[i]);
      }
    }
    hex.closePath();
    return hex;
  }

  protected void draw(Graphics2D g, CustomPoint2D point) {
    double x = getScaledXFromQ(point.getDim1(), point.getDim2());
    double y = getScaledYFromR(point.getDim2());
    double scaledSize = this.scaleAttrPhysicalToLogical(size);
    Path2D shape = this.drawShape(x, y, scaledSize, point);
    g.setStroke(new BasicStroke((float) 0.0025 * this.model.getBoardSize()));
    g.setColor(Color.BLACK);
    g.draw(shape);
    g.setColor(Color.LIGHT_GRAY);
    if (this.selectedPoint.isPresent()) {
      if (this.selectedPoint.get().equals(point)) {
        g.setColor(Color.CYAN);
      }
    }
    g.fill(shape);
    this.points.put(shape, point);

    try {
      g.setColor(this.model.getTileAt(point).getColor());
      Ellipse2D.Double circle = new Ellipse2D.Double(x - scaledSize / 2, y - scaledSize / 2,
              scaledSize, scaledSize);
      g.fill(circle);
    } catch (IllegalStateException e) {
      // do nothing - should never actually throw
    }
  }

  protected double getScaledYFromR(int r) {
    double y = (size * (this.model.getBoardSize() - 1)) / 2;
    y += (1.5 * size) * (r + this.model.getBoardSize() / 2);
    y = this.scaleAttrPhysicalToLogical(y);
    return y;
  }

  protected double getScaledXFromQ(int q, int r) {
    double x = (size * (this.model.getBoardSize() - 1)) / 2 + buffer / 2 - Math.sqrt(3) * size;
    double offset = x;
    if (r < 0) {
      offset -= (Math.sqrt(3) * size) / 2 * (r + (this.model.getBoardSize() / 2));
      x = offset + ((Math.sqrt(3) * size)
              * (q + (this.model.getRowWidth(r)) - (this.model.getBoardSize() / 2)));
    } else {
      offset += (Math.sqrt(3) * size) / 2 * (r - (this.model.getBoardSize() / 2));
      x = offset + (Math.sqrt(3) * size) * (q + this.model.getBoardSize() / 2 + 1);
    }
    return this.scaleAttrPhysicalToLogical(x);
  }


  @Override
  public void selectTile(Point2D logicalP) {
    boolean nonFound = true;
    for (Shape s : this.points.keySet()) {
      if (s.contains(logicalP)) {
        if (selectedPoint.isPresent() && this.selectedPoint.get().equals(
                this.points.get(s))) {
          this.selectedPoint = Optional.empty();
        } else {
          this.selectedPoint = Optional.of(this.points.get(s));
        }
        nonFound = false;
      }
    }
    if (nonFound) {
      this.selectedPoint = Optional.empty();
    }
    this.repaint();
  }


  @Override
  public void addFeatures(ViewFeatures features) {
    this.addMouseListener(new MouseInputAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        Point2D physicalP = e.getPoint();
        Point2D logicalP = transformPhysicalToLogical().transform(physicalP, null);
        features.selectTile(logicalP);
      }
    });
  }

  @Override
  public int getValueAtSelectedPoint() {
    if (this.selectedPoint.isPresent()) {
      try {
        return this.model.getScoreIfMovePlayed(this.selectedPoint.get(), this.model.getTurn());
      } catch (IllegalStateException e) {
        return 0;
      }
    } else {
      throw new IllegalStateException("Tried to get score for selected when nothing selected.");
    }
  }

  @Override
  public double getScaledXForSelectedPoint() {
    if (this.selectedPoint.isPresent()) {
      System.out.println(this.getPreferredLogicalSize().getWidth() / this.getWidth());
      return getScaledXFromQ(this.selectedPoint.get().getDim1(),
              this.selectedPoint.get().getDim2()) * this.getWidth() /
              this.getPreferredLogicalSize().getWidth();
    } else {
      throw new IllegalStateException("Tried to get X for selected when nothing selected.");
    }
  }

  @Override
  public double getScaledYForSelectedPoint() {
    if (this.selectedPoint.isPresent()) {
      return getScaledYFromR(this.selectedPoint.get().getDim2()) * this.getWidth()
              / this.getPreferredLogicalSize().getWidth();
    } else {
      throw new IllegalStateException("Tried to get Y for selected when nothing selected.");
    }
  }
}
