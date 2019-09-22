package by.epamgroup.infhandler.interpreter;

public class NotExpression implements Expression {
    private Expression expression;

    public NotExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public int interpret() {
        return ~expression.interpret();
    }
}
