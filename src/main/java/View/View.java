package View;

import Controller.Controller;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class responsible for the graphical interface
 */
public class View {
    /**
     * the root container for the view
     */
    private BorderPane view;
    /**
     * the buttons representing the invidual cells in the game
     */
    private Button[][] Btrix = new Button[5][6];
    private Button SaveB, LoadB, NewB, FinishB, Save = new Button(), Load= new Button();


    GridPane center=new GridPane();
    VBox menu =new VBox();
    Controller cmd;
    HBox fejlec;
    StackPane lablec;
    TextField Input = new TextField();
    Stage savewindow;
    Stage loadwindow;
    ChoiceBox<String> choiceBox= new ChoiceBox<>();
    Label scorelabel = new Label("Score:");

    public BorderPane getView() {
        return view;
    }

    public View(Controller cmd) {
        this.cmd = cmd;
        createAndConfigurePane();
        File folder = new File("Save");
        if (!folder.exists())
            folder.mkdir();
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++)
            choiceBox.getItems().addAll(listOfFiles[i].getName());
        choiceBox.getSelectionModel().selectFirst();

        AssignCMD();

    }

    private void createAndConfigurePane() {
        view = new BorderPane();
        center = new GridPane();
        menu = new VBox();
        fejlec = new HBox();
        lablec = new StackPane();
        view.setCenter(center);
        view.setTop(fejlec);
        view.setBottom(lablec);
        view.setLeft(menu);

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 6; j++) {
                Btrix[i][j] = new Button(CellText(i,j));
                Btrix[i][j].setMinWidth(40);
                Btrix[i][j].setMinHeight(40);
                Btrix[i][j].setStyle(generateCSS(i,j));
                center.add(Btrix[i][j], i, j);
            }
        SaveB = new Button("Save");
        SaveB.setOnAction(e -> savedisplay());
        LoadB = new Button("Load");
        LoadB.setOnAction(e -> Loaddisplay());
        NewB = new Button("New");
        FinishB = new Button("Finish");

        lablec.getChildren().addAll(FinishB);
        fejlec.getChildren().addAll(scorelabel);

        menu.getChildren().addAll(NewB, SaveB, LoadB);

    }

    void savedisplay() {
        savewindow = new Stage();

        //Block events to other windows
        savewindow.initModality(Modality.APPLICATION_MODAL);
        savewindow.setTitle("SaveBox");
        savewindow.setMinWidth(250);

        Label label = new Label();
        label.setText("Kérem adja meg a mentendő fájl nevét.");

        //TextField Input = new TextField();
        DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
        Date date = new Date();
        Input.setText(dateFormat.format(date));

        Save.setText("Save");

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, Input, Save);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
       savewindow.setScene(scene);
        savewindow.showAndWait();
    }

    public void Loaddisplay() {
        loadwindow = new Stage();
        loadwindow.initModality(Modality.APPLICATION_MODAL);
        loadwindow.setTitle("LoadBox");
        loadwindow.setMinWidth(250);

        Label label = new Label();
        label.setText("Kérlek válaszd ki a betöltendő mentést.");

        //ChoiceBox<String> choiceBox = new ChoiceBox<>();

        File folder = new File("Save");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++)
            choiceBox.getItems().addAll(listOfFiles[i].getName());
        choiceBox.getSelectionModel().selectFirst();



        Load.setText("Load");


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, choiceBox, Load);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        loadwindow.setScene(scene);
        loadwindow.showAndWait();
    }

    private  String generateCSS( int i,int j){
        String CSS = "";
        int []e={i,j};
        if (!cmd.getLine().contains(e)) {
            CSS += "-fx-background-color: #" + makeCode(i, j) + ";";
            CSS += "-fx-text-fill: red;";
        }
        else {
            CSS += "-fx-background-color: #110000;";
            CSS += "-fx-text-fill: blue;;";
        }
        CSS +="-fx-background-radius: 20;";


        if (i%2==0)
            CSS +="-fx-translate-y: 20;";
        return CSS;
    }

    String CellText(int i,int j){
        if (cmd.getcell(i,j)<1000)
            return String.valueOf(cmd.getcell(i,j));
        else return String.valueOf(cmd.getcell(i,j)/1000)+"K";

    }

    private String makeCode(int i,int j){
        int S = cmd.getcell(i,j);
        switch (S){
            case 2:return "ffff00";

            case 4:return "eeee00";

            case 8:return "dddd00";

            case 16:return "cccc00";

            case 32:return "bbbb00";

            case 64:return "aaaa00";

            case 128:return "999900";

            case 256:return "888800";

            case 512:return "777700";

            case 1024:return "666600";

            case 2048:return "555500";

            case 4096:return "444400";

            case 8192:return "333300";

            case 16384:return "222200";

            default:return "111100";

        }
    }

    void updateDessign(){
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 6; j++) {
                Btrix[i][j].setStyle(generateCSS(i,j));
                Btrix[i][j].setText(CellText(i,j));
            }
        ArrayList<int[]> lista = cmd.getLine();
        for (int i=0; i<lista.size();i++){
           int[] e=lista.get(i);
            Btrix[e[0]][e[1]].setStyle(Selected(e[0],e[1]));
        }

        scorelabel.setText("Score: "+cmd.getScore());

    }

    String Selected(int i, int j){
        String CSS = "";
        int []e={i,j};

            CSS += "-fx-background-color: #110000;";
            CSS += "-fx-text-fill: blue;;";

        CSS +="-fx-background-radius: 20;";
        if (i%2==0)
            CSS +="-fx-translate-y: 20;";
        return CSS;
    }

    void AssignCMD(){
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 6; j++) {
                int FinalI =i, FinalJ=j;
                Btrix[i][j].setOnAction(e -> {cmd.CellClicked(FinalI, FinalJ);
                updateDessign();
                });
            }
        NewB.setOnAction(e->{cmd.init();updateDessign();});
        Save.setOnAction(e->{cmd.Save(Input);savewindow.close();});
        Load.setOnAction(e->{cmd.Load(choiceBox);updateDessign();loadwindow.close();});
        FinishB.setOnAction(e->{cmd.finish();updateDessign();});


    }


}
