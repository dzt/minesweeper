public class Box {

    private String type;
    private boolean isMine;
    private boolean isFlagged;
    private int position;
    private int boardSize;

    public Box (String type, int position, int boardSize) {

        this.type = type;
        this.position = position;
        this.boardSize = boardSize;

        if (this.type == "X") {
            this.isMine = true;
        }

        this.isFlagged = false;
    }

    public String getType() {
        return type;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlag() {
        isFlagged = true;
    }

    public int getPosition() {
        return position;
    }

    public int[] getSurroundingPositions() {
        int[] arr = new int[8];
        return arr;
    }

}
