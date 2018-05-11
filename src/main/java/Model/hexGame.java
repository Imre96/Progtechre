package Model;

import Game.SaveHandler;

import static java.lang.Math.pow;

/**
 * Class representing the current gamestate.
 *
 */
public class hexGame implements hexGameInterface {
    /**
     * Matrix representing thevalues of invidual Cells.
     */
   // private IntegerProperty matrix[][]= new IntegerProperty()[5][6];
    private int matrix[][]= new int[5][6];
    /**
     *The Score the player gathered during the game.
     */
    private int score;
    /**
     *Returns the gameboard.
     *
     * @return the gameboard
     */
    @Override
    public int[][] getMatrix() {
        return matrix;
    }
    /**
     *Returns the Score.
     *
     * @return the score
     */
    @Override
    public int getScore() {
        return score;
    }
    /**
     *Increases score by n amount.
     *
     * @param n the gameboard
     */
    @Override
    public void addScore(int n){
        score+=n;
    }
    /**
     *Sets the Gameboard.
     *
     * @param matrix 5x6 int array to assign as the gameboard
     */
    @Override
    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }
    /**
     *Sets the Score.
     *
     * @param n the value to set Score as
     */
    @Override
    public void setScore(int n) {
        this.score = n;
    }
    /**
     *Returns the value of invidual cells..
     *
     * @return the @int value of the Cell
     * @param i column coordinate
     * @param j row coordinate
     */
    @Override
    public int getCell(int i, int j){
        return matrix[i][j];
    }
    /**
     *Sets the value of invidual cells..
     *
     * @param i column coordinate
     * @param j row coordinate
     * @param n value to assign
     */
    @Override
    public void setCell(int i, int j, int n){
        matrix[i][j]=n;
    }

    /**
     * Constuctor for creating {@code hexGame} object
     */
    @Override
    public void hexGame(){
        for (int i=0 ;i<6; i++){
            for (int j=0;j<5;j++){
                matrix[i][j]=0;
            }
        }
        score=0;
    }

    /**
     * Fills up the gameboard with Randomized Values and sets Score to 0
     */
    @Override
    public void init(){
        for (int i=0 ;i<5; i++){
            for (int j=0;j<6;j++){
                matrix[i][j]=(int)pow(2,(int)(Math.random()*3+1));
            }
        }
        //score=0;
    }

    /**
     * Fills up the empty cells on the board with ramdomly generated value.
     */
    @Override
    public void fillUp(){
        for (int i=0 ;i<5; i++){
            for (int j=0;j<6;j++){
                if (matrix[i][j]==0)
                    matrix[i][j]=(int)pow(2,(int)(Math.random()*3+1));
            }
        }
    }

    /**
     * Makes the cells fall down.
     */
    @Override
    public void gravitate(){
        for (int i=0; i<5; i++){
            boolean volteses=true;
                while (volteses){
                    volteses=false;
                    for (int j=5;j>0;j--)
                        if (matrix[i][j]==0 && matrix[i][j-1]!=0){
                        matrix[i][j]=matrix[i][j-1];
                        matrix[i][j-1]=0;
                        volteses=true;
                    }
                }
        }
    }

    /**
     * Saves the current state of the game to an XML file
     * @param name The name of the file to save in
     */
    @Override
    public void save(String name) {
        SaveHandler SH = new SaveHandler();
        SH.save(this, name);
    }

    /**
     * Restores the state of the game from an xml file
     * @param name name of the xml file
     */
    @Override
    public void load(String name) {
        SaveHandler SH = new SaveHandler();
        hexGame tmp = SH.load(name);
        this.matrix=tmp.getMatrix();
        this.score=tmp.getScore();
    }



}
