package Controller;


import Model.hexGame;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class Controller {
    /**
     * The current game
     */
    private hexGame Current =new hexGame();
    /**
     * The currently selected Cells.
     */
    private ArrayList<int[]> line=new ArrayList<int[]>();

    /**
     * Sets up the Gameboard for the current game
     */
    public  void init(){
        Current.init();
    }
    /**
     * Returns the Score
     * @return the score
     */
    public  int getScore(){
        return Current.getScore();
    }
    /**
     * Increases the score
     * @param n the amount is increased by
     */
    private  void AddScore(int n) {
        Current.addScore(n);
    }

    /**
     * Returns invidual cells
     * @param i Collumn coordinate
     * @param j Row coordinate
     * @return the value of the cell
     */
    public int getcell(int i,int j){
        return Current.getCell(i,j);
    }

    /**
     * Handles the Clicks on the buttons representing the invidual cells
     * @param i Collumn of the clicked button
     * @param j Row of the clicked button
     */
    public void CellClicked(int i, int j){
        int[] e={i,j};
        if (!line.contains(e)){
            if (line.isEmpty()){
                line.add(e);
            }else if (isvalid(e)){
                line.add(e);
            }
        }
    }

    /**
     * Check if the clicked button is a valid move
     * @param a the column and row coordinates in {i,j} format
     * @return true if the move is valid
     */
    private boolean isvalid(int a[]){
        int [] e=line.get(line.size()-1);
        boolean good=false;
        if (Math.abs(e[0]-a[0])<=1 && Math.abs(e[1]-a[1])<=1)
            if(Current.getCell(e[0],e[1])==Current.getCell(a[0],a[1])) {
                good = true;
            }
            else if (line.size()>=2&& Current.getCell(e[0],e[1])*2==Current.getCell(a[0],a[1])){
                good=true;
            }
        return good;
    }

    /**
     * Returns a list of the so far connected cells,
     * @return Arraylist containig the indexes of connected cells
     */
    public ArrayList<int[]> getLine() {
        return line;
    }

    /**
     * Call the Saving method
     * @param input Textfield containig the name of the save
     */
    public void Save(TextField input){
       Current.save(input.getText());
    }

    /**
     * Calls Loading method
     * @param choiceBox Choicebox containing sellected Save
     */
    public void Load(ChoiceBox<String> choiceBox){
        Current.load("Save/"+choiceBox.getValue());
    }

    /**
     * Handless the end move event
     */
    public void finish(){
        int sum =0;
        for (int i=0; i<line.size();i++){
            sum +=Current.getCell(line.get(i)[0],line.get(i)[1]);
            Current.setCell(line.get(i)[0],line.get(i)[1],0);
        }
        int[] e =line.get(line.size()-1);
        Current.setCell(e[0],e[1],get2pow(sum));
        if (line.size()>1)
            AddScore(sum);
        Current.gravitate();
        Current.fillUp();
        line.clear();

    }

    /**
     * return the highest power of 2 that is smaller than n
     * @param n the score gained from the move
     * @return a power of 2
     */
    private int get2pow(int n) {
        int i=1;
        while(Math.pow(2,i)<=n) {
            i++;
        }
        if (Math.pow(2,i)==n) {
            return (int) Math.pow(2, i);
        }else return  (int) Math.pow(2, i-1);
    }
}
