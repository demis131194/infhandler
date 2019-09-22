package by.epamgroup.infhandler.interpreter;

public class XorExpression implements Expression {
    private Expression left;
    private Expression right;

    public XorExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public int interpret() {
        return left.interpret() ^ right.interpret();
    }
}
