package cs3500.reversi.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import cs3500.reversi.model.AxialCustomPoint;
import cs3500.reversi.model.ReversiModel;
import cs3500.reversi.model.SquareReversiModel;

/**
 * Represents a test suit for the SquareReversi textual rendering.
 */
public class SquareReversiTextualViewTest {
  ReversiModel model;

  @Before
  public void setUp() {
    this.model = new SquareReversiModel(4);
  }

  @Test
  public void testEmptyBoard() {
    StringBuilder out = new StringBuilder();
    ReversiTextualView view = new SquareReversiTextualViewImplementation(model, out);
    try {
      view.render();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Assert.assertEquals(
            "_ _ _ _ \n" +
                    "_ X O _ \n" +
                    "_ O X _ \n" +
                    "_ _ _ _ ",
            out.toString());
  }

  @Test
  public void testBigEmptyBoard() {
    this.model = new SquareReversiModel(16);
    StringBuilder out = new StringBuilder();
    ReversiTextualView view = new SquareReversiTextualViewImplementation(model, out);
    try {
      view.render();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Assert.assertEquals(
            "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ X O _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ O X _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ ",
            out.toString());
  }



  @Test
  public void testViewAtTrivialPoint() {
    this.model = new SquareReversiModel(8);
    StringBuilder out = new StringBuilder();
    ReversiTextualView view = new SquareReversiTextualViewImplementation(model, out);
    model.placeTile(new AxialCustomPoint(4, 2), model.getTurn());
    model.placeTile(new AxialCustomPoint(5, 2), model.getTurn());
    model.placeTile(new AxialCustomPoint(6, 2), model.getTurn());
    model.placeTile(new AxialCustomPoint(3, 2), model.getTurn());
    model.placeTile(new AxialCustomPoint(2, 5), model.getTurn());
    try {
      view.render();
    } catch (IOException e) {
      e.printStackTrace();
    }
    Assert.assertEquals(
            "_ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ \n" +
                    "_ _ _ O X X X _ \n" +
                    "_ _ _ O X _ _ _ \n" +
                    "_ _ _ X X _ _ _ \n" +
                    "_ _ X _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ \n" +
                    "_ _ _ _ _ _ _ _ ",
            out.toString());
  }
}
