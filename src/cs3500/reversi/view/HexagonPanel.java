package cs3500.reversi.view;

import cs3500.reversi.model.ReadOnlyReversiModel;

/**
 * Class representing a graphical representation of the board of the given model using a JPanel.
 */
class HexagonPanel extends AbstractPanel {

  /**
   * Constructs a panel that represents the board of this model.
   *
   * @param model the model to represent
   */
  public HexagonPanel(ReadOnlyReversiModel model) {
    super(model);
  }


}