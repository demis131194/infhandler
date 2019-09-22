package by.epamgroup.infhandler.interpreter;

public enum Operation {
    NOT("~"),
    LEFT_SHIFT("<"),
    RIGHT_SHIFT(">"),
    AND("&"),
    XOR("^"),
    OR("|");

    private String operation;

    Operation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
