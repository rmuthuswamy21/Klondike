package cs3500.reversi.view;


import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

import cs3500.reversi.model.BoardShape;
import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Class representing a graphical view of the Reversi Model.
 */
public class ReversiGraphicsView extends JFrame implements IView {
  // INVARIANT: the given panel will always extend JPanel and will thus always be a component
  private Panel hex;

  private boolean hints;

  private final ReadOnlyReversiModel m;  // Only used to recreate hex

  private ViewFeatures features;

  /**
   * Constructs a graphical view using the model, with a default size of (1000, 1000).
   * @param model the model to base the view on
   */
  public ReversiGraphicsView(ReadOnlyReversiModel model) {
    super();
    this.setTitle("Reversi!");
    this.setSize(700, 700);
    if (model.getBoardShape() == BoardShape.HEXAGON) {
      this.hex = new HexagonPanel(model);
    }
    else {
      this.hex = new SquarePanel(model);
    }
    this.add((Component) this.hex);
    this.m = model;
    this.hints = false;
  }

  @Override
  public void addFeatures(ViewFeatures features) {
    this.features = features;
    // Add mouse listener for selecting
    hex.addFeatures(this.features);
    // Add key listener for passing and placing a tile
    this.addKeyListener(new KeyListener() {
      @Override
      public void keyTyped(KeyEvent e) {
        // don't want to do anything, but must be implemented
      }

      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_SPACE) {
          features.pass();
        } else if (e.getKeyCode() == 'P') {
          features.placeTile(hex.getSelectedPoint());
        } else if (e.getKeyCode() == 'H') {
          features.switchHints();
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // don't want to do anything, but must be implemented
      }
    });
  }

  @Override
  public void showMessage(String s) {
    JOptionPane.showMessageDialog(this, s);
  }

  @Override
  public void selectTile(Point2D logicalP) {
    hex.selectTile(logicalP);
  }

  @Override
  public void display(boolean b) {
    this.setVisible(b);
  }

  @Override
  public void update() {
    this.repaint();
  }

  @Override
  public void switchHints() {
    if (this.hints) {
      System.out.println("Switching hints off");
      this.remove((Component) this.hex);
      this.hex = new HexagonPanel(this.m);
      hex.addFeatures(features);
      this.add((Component) this.hex);
    }
    else {
      System.out.println("Switching hints on");
      this.remove((Component) this.hex);
      this.hex = new HintPanelDecorator(this.hex);
      hex.addFeatures(features);
      this.add((Component) this.hex);
    }
    this.revalidate();
    this.repaint();
    this.hints = !this.hints;
  }
}
