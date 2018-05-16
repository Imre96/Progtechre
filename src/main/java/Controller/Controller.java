package Controller;
import Model.SaveHandler;
import Model.hexGame;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class Controller implements EventHandler<Event> {
    @FXML
    ArrayList<Button> btnList;
    @FXML
    ChoiceBox<String> ChBox;
    @FXML
    TextField TField;
    @FXML
    Label ScoreLabel;
    @FXML
    Button SAVE;
    @FXML
    Button LOAD;

    Stage LoadPopup =new Stage();
    Stage SavePopup = new Stage() ;
     hexGame Current= new hexGame();

    public void startGame(){
        Current.init();
        refreshCells();

    }

    public void loadGame() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/Loadbox.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        LoadPopup=new Stage();
        LoadPopup.initModality(Modality.APPLICATION_MODAL);
        LoadPopup.setTitle("ABC");
        LoadPopup.setScene(new Scene(root1));
        LoadPopup.show();
        //refreshCells();
    }

    public void getList() {
        File folder = new File("Save");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            //System.out.printf(listOfFiles[i].getName()+"\n");
            ChBox.getItems().addAll(listOfFiles[i].getName());
        }
        ChBox.getSelectionModel().selectFirst();
    }

    public void saveGame()throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/Savebox.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        SavePopup = new Stage();
        SavePopup.initModality(Modality.APPLICATION_MODAL);
        SavePopup.setTitle("Savebox");
        SavePopup.setScene(new Scene(root1));
        SavePopup.show();

    }

    public void SaveGame() {
        Current.save(TField.getText());
        SAVE.getScene().getWindow().hide();
    }

    public void handle(Event event) {
        String id = ((Button)event.getSource()).getId();
        //((Button) event.getSource()).setText(id);
        int i=IdtoInt(id.charAt(0));
        int j=IdtoInt(id.charAt(2));

        int[] e={i,j};
        if(Current.getLine().isEmpty()){
            Current.Add(e);
            ((Button)event.getSource()).setStyle("-fx-background-color: #110011;-fx-text-fill: blue;-fx-background-radius: 20;");
        }else if (isvalid(e)){
            Current.Add(e);
            ((Button)event.getSource()).setStyle("-fx-background-color: #110011;-fx-text-fill: blue;-fx-background-radius: 20;");
        }
        //refreshCells();
    }

    private boolean isvalid(int a[]){
        int [] e=Current.getLine().get(Current.getLine().size()-1);
        boolean good=false;
        if (Math.abs(e[0]-a[0])<=1 && Math.abs(e[1]-a[1])<=1)
            if(Current.getCell(e[0],e[1])==Current.getCell(a[0],a[1])) {
                good = true;
            }
            else if (Current.getLine().size()>=2&& Current.getCell(e[0],e[1])*2==Current.getCell(a[0],a[1])){
                good=true;
            }
            return good;
    }

    String CellText(int i,int j){
        if (Current.getCell(i,j)<1000)
            return String.valueOf(Current.getCell(i,j));
        else return String.valueOf(Current.getCell(i,j)/1000)+"K";

    }

    public void LoadGame(){
        Current.load("Save/"+ChBox.getValue());
        LOAD.getScene().getWindow().hide();
//       refreshCells();
    }

    public void refreshCells() {
        btnList.iterator().forEachRemaining(button -> {
            String id = button.getId();
            int i=IdtoInt(id.charAt(0));
            int j=IdtoInt(id.charAt(2));
            //System.out.printf(CellText(i,j)+" ");
            button.setText(CellText(i,j));
            button.setStyle(generateCSS(i,j));
            });
        ScoreLabel.setText("Score: "+Current.getScore());
    }

    public void Finnish() {
        //refreshCells();
        Current.finish();
        refreshCells();
    }

    int IdtoInt(char a){
        switch (a){
            case 'a': return 0;
            case 'b': return 1;
            case 'c': return 2;
            case 'd': return 3;
            case 'e': return 4;
            case 'f': return 5;
            default:return 6;
        }
    }

    private  String generateCSS( int i,int j){
        String CSS = "";
        int []e={i,j};
        if (!Current.getLine().contains(e)) {
            CSS += "-fx-background-color: #" + makeCode(i, j) + ";";
            CSS += "-fx-text-fill: red;";
        }
        else {
            CSS += "-fx-background-color: #110000;";
            CSS += "-fx-text-fill: blue;";
        }
        CSS +="-fx-background-radius: 20;";
        CSS +="-fx-font-size: " + 12 + "px;";
        return CSS;
    }

    private String makeCode(int i,int j){
        int S = Current.getCell(i,j);
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
}
