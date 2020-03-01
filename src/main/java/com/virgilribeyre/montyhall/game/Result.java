package com.virgilribeyre.montyhall.game;

public class Result {

  private int success;
  private int fails;
  private int iterate;

  /**
   * Default Result constructor
   * 
   * @param iterate number of time the game is runned
   */
  public Result(int iterate) {
    this.iterate = iterate;
    success = 0;
    fails = 0;
  }

  /**
   * Add a success to the result
   */
  public void addSuccess() {
    success++;
  }

  /**
   * Add a fail to the result
   */
  public void addFail() {
    fails++;
  }

  /**
   * Default public getter of success
   *
   * @return int the number of Succes
   */
  public int getSuccess() {
    return success;
  }

  /**
   * Default public getter of fails
   * 
   * @return
   */
  public int getFails() {
    return fails;
  }

  /**
   * Calculate the success rate
   *
   * @return float the success rate
   */
  public float getSuccessRate() {
    return ((float) success / (float) iterate) * 100;
  }

  /**
   * Calculate the fail rate
   * 
   * @return float the fail rate
   */
  public float getFailRate() {
    return ((float) fails / (float) iterate) * 100;
  }
}
