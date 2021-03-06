package by.epamgroup.infhandler.interpreter;

public class Cursor {
    private int leftCursor;
    private int rightCursor;
    private int operatorCursor;

    public Cursor(int leftCursor, int rightCursor, int operatorCursor) {
        this.leftCursor = leftCursor;
        this.rightCursor = rightCursor;
        this.operatorCursor = operatorCursor;
    }

    public int getLeftCursor() {
        return leftCursor;
    }

    public void setLeftCursor(int leftCursor) {
        this.leftCursor = leftCursor;
    }

    public int getRightCursor() {
        return rightCursor;
    }

    public void setRightCursor(int rightCursor) {
        this.rightCursor = rightCursor;
    }

    public int getOperatorCursor() {
        return operatorCursor;
    }

    public void decrementLeftCursor() {
        leftCursor--;
    }

    public void incrementLeftCursor() {
        leftCursor++;
    }

    public void decrementRightCursor() {
        rightCursor--;
    }

    public void incrementRightCursor() {
        rightCursor++;
    }
}
