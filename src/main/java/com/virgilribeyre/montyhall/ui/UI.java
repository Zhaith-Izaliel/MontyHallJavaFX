package com.virgilribeyre.montyhall.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.virgilribeyre.montyhall.game.Game;
import com.virgilribeyre.montyhall.game.Result;

public class UI extends Application {
  private TextField input = new TextField("1000");
  private Button run = new Button("Run the simulation");
  private int iterate = 1;
  private VBox sceneWrapper;
  private Scene scene;
  private VBox lastCharts;
  private Label warning = new Label("You must set a valid integer greater than 0");

  @Override
  public void start(Stage stage) throws Exception {

    stage.setTitle("Monty Hall Simulation - CC-BY-NC-SA - Virgil Ribeyre");
    stage.initStyle(StageStyle.DECORATED);
    createScene();
    stage.setScene(scene);
    stage.setMaximized(true);
    stage.show();
  }

  public static void run(String[] args) {
    Application.launch(args);
  }

  private void createScene() {
    sceneWrapper = new VBox(8);
    sceneWrapper.getChildren().addAll(createTitle(), new Separator(Orientation.HORIZONTAL), createInputBox(),
        new Separator(Orientation.HORIZONTAL));
    scene = new Scene(sceneWrapper);
  }

  // ####################### Input Creation #######################
  private VBox createInputBox() {
    VBox box = new VBox(10);
    run.setOnAction(actionEvent -> {
      String text = input.getText();
      try {
        box.getChildren().remove(warning);
        iterate = parseInt(text);
        sceneWrapper.getChildren().remove(lastCharts);
        sceneWrapper.getChildren().add(chartSection(iterate));
      } catch (NumberFormatException nfe) {
        box.getChildren().add(warning);
      }
    });
    HBox inputBox = new HBox(2);
    inputBox.getChildren().addAll(new Label("Number of iteration: "), input);
    inputBox.setAlignment(Pos.CENTER);

    HBox runBox = new HBox(run);
    runBox.setAlignment(Pos.CENTER);

    box.getChildren().addAll(createLabelBox("Settings", (double) 26, Pos.CENTER), inputBox, runBox);
    return box;
  }

  // ####################### END OF Input Creation #######################
  // #####################################################################
  // ####################### Charts Creation #############################
  private VBox chartSection(int iterate) {
    HBox labelBox = createLabelBox("Results", (double) 26, Pos.CENTER);
    VBox returned = new VBox(8);
    HBox box = new HBox();
    box.setAlignment(Pos.CENTER);
    box.getChildren().addAll(createBoxChart(createChart(iterate, false), "When the player stays on them choice"),
        new Separator(Orientation.VERTICAL),
        createBoxChart(createChart(iterate, true), "When the player changes them mind"));
    returned.getChildren().addAll(labelBox, new Separator(Orientation.HORIZONTAL), box);

    lastCharts = returned;
    return returned;
  }

  private VBox createBoxChart(VBox chart, String title) {
    HBox labelBox = createLabelBox(title, (double) 19, Pos.CENTER);
    VBox box = new VBox(5);
    box.getChildren().addAll(labelBox, chart);

    return box;
  }

  private VBox createChart(int iterate, boolean isChange) {
    PieChart chart = new PieChart();
    Result result = new Game(iterate, isChange).runResult();
    Data slice1 = new Data("Fail rate", result.getFailRate());
    Data slice2 = new Data("Success rate", result.getSuccessRate());
    chart.getData().add(slice1);
    chart.getData().add(slice2);

    VBox box = new VBox(4);
    box.getChildren().addAll(chart, new Separator(Orientation.HORIZONTAL),
        new Label("Success rate: " + result.getSuccessRate()), new Label("Fail rate: " + result.getFailRate()),
        new Separator(Orientation.HORIZONTAL), new Label("Number of success: " + result.getSuccess()),
        new Label("Number of fails: " + result.getFails()));

    return box;
  }
  // ####################### END OF Charts Creation #######################
  // ######################################################################
  // ####################### Titles Creation ##############################

  private HBox createLabelBox(String title, Double fontSize, Pos position) {
    Label label = new Label(title);
    label.setFont(Font.font("System", FontWeight.BOLD, fontSize));
    label.setAlignment(position);
    HBox labelBox = new HBox(label);
    labelBox.setAlignment(position);

    return labelBox;
  }

  private VBox createTitle() {
    VBox title = new VBox(6);
    Label author = new Label("Author: Virgil Ribeyre");
    Label licence = new Label("Licence: Creative Commons BY-NC-SA");
    Label description = new Label(
        "This JavaFX application allows you to run a simulation of the famous Monty Hall's game "
            + "and display two pie charts based on the results for a player who changes them mind in the middle "
            + "of the game and for one who stays consistent.\nFor more informations, see the GitHub page.");

    author.setWrapText(true);
    licence.setWrapText(true);
    description.setWrapText(true);
    author.setTextAlignment(TextAlignment.LEFT);
    licence.setTextAlignment(TextAlignment.LEFT);
    description.setTextAlignment(TextAlignment.LEFT);
    description.setMaxWidth(700);

    VBox wrapperLabels = new VBox(8);
    wrapperLabels.getChildren().addAll(author, licence, description);
    wrapperLabels.setAlignment(Pos.CENTER_LEFT);

    VBox infos = new VBox(4);
    infos.setAlignment(Pos.CENTER);
    infos.setPadding(new Insets(8, 8, 8, 20));
    infos.getChildren().add(wrapperLabels);
    title.getChildren().addAll(createLabelBox("MontyHall Simulation", (double) 32, Pos.CENTER), infos,
        new Separator(Orientation.HORIZONTAL), new Separator(Orientation.HORIZONTAL));
    return title;
  }
  // ####################### END OF Titles Creation ########################

  /**
   * Parse a String as an Integer.
   * 
   * @param str the desired String
   * @return the parsed Integer
   * @throws NumberFormatException if the String is not a proper Integer
   */
  private int parseInt(String str) throws NumberFormatException {
    return Integer.parseInt(str);
  }
}
