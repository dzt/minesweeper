public class Box {

    private String type;
    private boolean isMine;

    public Box (String type) {
        this.type = type;
        if (this.type == "X") {
            this.isMine = true;
        }
    }

    public String getType() {
        return type;
    }

    public boolean isMine() {
        return isMine;
    }

}
