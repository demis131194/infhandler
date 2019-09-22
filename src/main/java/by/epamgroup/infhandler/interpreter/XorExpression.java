package by.epamgroup.infhandler.interpreter;

public class XorExpression implements Expression {
    private Expression right;

    public XorExpression(Expression right) {
        this.right = right;
    }

    @Override
    public int interpret() {
        return ~right.interpret();
    }
}
