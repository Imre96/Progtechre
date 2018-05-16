package Model;

public interface hexGameInterface {
    int[][] getMatrix();

    int getScore();

    void addScore(int n);

    void setMatrix(int[][] matrix);

    void setScore(int n);

    int getCell(int i, int j);

    void setCell(int i, int j, int n);

    void init();

    void fillUp();

    void gravitate();

    void save(String name);

    void load(String name);
}
