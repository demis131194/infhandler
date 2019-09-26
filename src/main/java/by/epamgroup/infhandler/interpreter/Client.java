package by.epamgroup.infhandler.interpreter;

import by.epamgroup.infhandler.exception.IllegalExpressionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {
    private static Logger logger = LogManager.getLogger();
    private static final String NUMBER_REGEX = "-?[\\d]+";

    private Client() {
    }

    public static Expression evaluate(String expression) throws IllegalExpressionException {
        logger.trace("In evaluate.");
        logger.info("Evaluated expression: " + expression);
        expression = expression.replaceAll(">>", ">");
        expression = expression.replaceAll("<<", "<");
        return calculate(expression);
    }

    private static Expression calculate(String expression) throws IllegalExpressionException {

        while (!expression.matches(NUMBER_REGEX)) {

            while (expression.contains(Operation.NOT.getOperation())) {
                expression = executeOperation(expression, Operation.NOT);
            }
            while (expression.contains(Operation.RIGHT_SHIFT.getOperation())) {
                expression = executeOperation(expression, Operation.RIGHT_SHIFT);
            }
            while (expression.contains(Operation.LEFT_SHIFT.getOperation())) {
                expression = executeOperation(expression, Operation.LEFT_SHIFT);
            }
            while (expression.contains(Operation.AND.getOperation())) {
                expression = executeOperation(expression, Operation.AND);
            }
            while (expression.contains(Operation.XOR.getOperation())) {
                expression = executeOperation(expression, Operation.XOR);
            }
            while (expression.contains(Operation.OR.getOperation())) {
                expression = executeOperation(expression, Operation.OR);
            }

        }

        return new NumberExpression(Integer.parseInt(expression));
    }

    private static String executeOperation(String expression, Operation operation) throws IllegalExpressionException {
        int operatorCursor = expression.indexOf(operation.getOperation());
        Cursor cursor;
        Expression leftExpression;
        Expression rightExpression;

        if (operation == Operation.NOT) {
            cursor = new Cursor(operatorCursor, operatorCursor + 1, operatorCursor);
            leftExpression = null;
        } else {
            cursor = new Cursor(operatorCursor - 1, operatorCursor + 1, operatorCursor);
            leftExpression = getLeftExpression(expression, cursor);
        }

        rightExpression = getRightExpression(expression, cursor);

        Expression resultExpression = null;

        switch (operation) {
            case NOT:
                 resultExpression = () -> ~rightExpression.interpret();
                break;
            case LEFT_SHIFT:
                resultExpression = () -> leftExpression.interpret() << rightExpression.interpret();
                break;
            case RIGHT_SHIFT:
                resultExpression = () -> leftExpression.interpret() >> rightExpression.interpret();
                break;
            case AND:
                resultExpression = () -> leftExpression.interpret() & rightExpression.interpret();
                break;
            case XOR:
                resultExpression = () -> leftExpression.interpret() ^ rightExpression.interpret();
                break;
            case OR:
                resultExpression = () -> leftExpression.interpret() | rightExpression.interpret();
                break;
        }

        int result = resultExpression.interpret();
        String replacedPartExpression = expression.substring(cursor.getLeftCursor(), cursor.getRightCursor());
        return expression.replace(replacedPartExpression, String.valueOf(result));
    }

    private static Expression getRightExpression(String expression, Cursor cursor) throws IllegalExpressionException {
        Expression rightExpression;
        char rightChar = expression.charAt(cursor.getRightCursor());

        if (Character.isDigit(rightChar)) {

            int number = getRightNumberFromExpression(expression, cursor);
            rightExpression =  new NumberExpression(number);

        } else if (rightChar == '(') {
            String newExpression = getRightExpressionFromBounds(expression, cursor);
            rightExpression =  calculate(newExpression);
        } else {
            throw new IllegalExpressionException("Wrong expression!");
        }

        return rightExpression;
    }

    private static String getRightExpressionFromBounds(String expression, Cursor cursor) {
        char rightChar;
        int breaksCount = 1;

        while (breaksCount > 0) {
            cursor.incrementRightCursor();
            rightChar = expression.charAt(cursor.getRightCursor());

            if (rightChar == ')') {
                breaksCount--;
            } else if (rightChar == '(') {
                breaksCount++;
            }

        }
        cursor.incrementRightCursor();
        return expression.substring(cursor.getOperatorCursor() + 2, cursor.getRightCursor() - 1);
    }

    private static int getRightNumberFromExpression(String expression, Cursor cursor) {
        char rightChar = expression.charAt(cursor.getRightCursor());
        try {
            while (Character.isDigit(rightChar) || rightChar == '-') {
                cursor.incrementRightCursor();
                rightChar = expression.charAt(cursor.getRightCursor());
            }

        } catch (IndexOutOfBoundsException ignored) {
        }

        return Integer.parseInt(expression.substring(cursor.getOperatorCursor() + 1, cursor.getRightCursor()));
    }

    private static Expression getLeftExpression(String expression, Cursor cursor) throws IllegalExpressionException {
        Expression resultExpression;

        char leftChar = expression.charAt(cursor.getLeftCursor());

        if (Character.isDigit(leftChar)) {
            int number = getLeftNumberFromExpression(expression, cursor);
            resultExpression = new NumberExpression(number);

        } else if (leftChar == ')') {
            String newExpression = getLeftExpressionInBounds(expression, cursor);
            resultExpression =  calculate(newExpression);

        } else {
            throw new IllegalExpressionException("Wrong expression!");
        }

        return resultExpression;
    }

    private static String getLeftExpressionInBounds(String expression, Cursor cursor) {
        char leftChar;
        int breaksCount = 1;

        while (breaksCount > 0) {
            cursor.decrementLeftCursor();
            leftChar = expression.charAt(cursor.getLeftCursor());

            if (leftChar == ')') {
                breaksCount++;
            } else if (leftChar == '(') {
                breaksCount--;
            }

        }
        return expression.substring(cursor.getLeftCursor() + 1, cursor.getOperatorCursor() - 1);
    }

    private static int getLeftNumberFromExpression(String expression, Cursor cursor) {
        char leftChar = expression.charAt(cursor.getLeftCursor());

        try {
            while (Character.isDigit(leftChar) || leftChar == '-') {
                cursor.decrementLeftCursor();
                leftChar = expression.charAt(cursor.getLeftCursor());
            }
            cursor.incrementLeftCursor();
        } catch (IndexOutOfBoundsException e) {
            cursor.incrementLeftCursor();
        }

        return Integer.parseInt(expression.substring(cursor.getLeftCursor(), cursor.getOperatorCursor()));
    }
}
