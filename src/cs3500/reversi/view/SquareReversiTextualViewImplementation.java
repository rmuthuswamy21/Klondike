package cs3500.reversi.view;

import java.io.IOException;

import cs3500.reversi.model.AxialCustomPoint;
import cs3500.reversi.model.CustomPoint2D;
import cs3500.reversi.model.ReversiModel;

/**
 * A textual view for a game of reversi with a square board.
 */
public class SquareReversiTextualViewImplementation implements ReversiTextualView {

  private final ReversiModel model;
  private final Appendable builder;

  public SquareReversiTextualViewImplementation(ReversiModel model, Appendable builder) {
    this.model = model;
    this.builder = builder;
  }

  private String getTileString(AxialCustomPoint point) {
    String s;
    if (this.model.isSpotEmpty(point)) {
      s = "_ ";
    } else {
      s = (this.model.getTileAt(point).toString());
      s += " ";
    }
    return s;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    int dim2 = 0;
    for (CustomPoint2D point : model.getValidTiles()) {
      if (point.getDim2() != dim2) {
        dim2 = point.getDim2();
        stringBuilder.append("\n");
      }
      stringBuilder.append(this.getTileString(new AxialCustomPoint(point.getDim1(),
              point.getDim2())));
    }
    return stringBuilder.toString();
  }


  @Override
  public void render() throws IOException {
    this.builder.append(this.toString());
  }
}
