package com.virgilribeyre.montyhall.game;

import java.util.ArrayList;
import java.util.Collections;

public class Game extends Thread {

  private int iterate;
  private boolean isChange;
  private ArrayList<TOKEN> doors;
  private int index;
  private int revealedIndex;
  private Result result;

  private enum TOKEN {
    WIN, LOOSE
  }

  /**
   * Default constructor for Game
   * 
   * @param iterate  number of time the game is played
   * @param isChange does the player change his mind mid-game ?
   */
  public Game(int iterate, boolean isChange) throws NumberFormatException {
    if (iterate <= 0) {
      throw new NumberFormatException("Must be a not null Integer");
    }
    this.iterate = iterate;
    this.isChange = isChange;
    result = new Result(iterate);
  }

  public Result runResult() {
    run();
    return result;
  }

  /**
   * Run the game
   */
  public void run() {
    game();
  }

  /**
   * Start the game.
   */
  private void game() {
    for (int i = 0; i < iterate; i++) {
      initialize();
      choose();
      reveal();
      if (isChange) {
        change();
      }
      if (isWon()) {
        result.addSuccess();
      } else {
        result.addFail();
      }
    }
  }

  /**
   * Initialize the game.
   */
  private void initialize() {
    doors = new ArrayList<TOKEN>();
    // fill the list
    doors.add(TOKEN.WIN);
    doors.add(TOKEN.LOOSE);
    doors.add(TOKEN.LOOSE);

    // shuffle the list
    Collections.shuffle(doors);
  }

  /**
   * Set the choice of the player
   */
  private void choose() {
    index = random();
  }

  /**
   * Get a random integer between 0 and doors.size()
   */
  private int random() {
    return (int) (Math.random() * doors.size());
  }

  /**
   * Reveal a door which is not the one with the winToken and not the one choosed
   * by the player
   */
  private void reveal() {
    boolean stop = false;
    do {
      revealedIndex = random();
      stop = (revealedIndex != index) && (revealedIndex != doors.indexOf(TOKEN.WIN));
    } while (!stop);
  }

  /**
   * Switch the choice of the player
   */
  private void change() {
    boolean stop = false;
    int lastIndex = index;
    do {
      index = random();
      stop = (index != revealedIndex) && (index != lastIndex);
    } while (!stop);
  }

  /**
   * Check if the game is won
   */
  private boolean isWon() {
    return doors.get(index).equals(TOKEN.WIN);
  }
}
