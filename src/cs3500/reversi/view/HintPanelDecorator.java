package cs3500.reversi.view;

import java.awt.Graphics;

/**
 * Represents a decorator for a panel.
 */
public class HintPanelDecorator extends PanelDecorator {

  HintPanelDecorator(Panel p) {
    super(p);
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (super.getSelectedPoint().isPresent()) {
      System.out.println(super.getScaledXForSelectedPoint() + "    "
              + (int) super.getScaledYForSelectedPoint());
      g.drawString("" + super.getValueAtSelectedPoint(), (int) super.getScaledXForSelectedPoint(),
              (int) super.getScaledYForSelectedPoint());
    }
  }



}
