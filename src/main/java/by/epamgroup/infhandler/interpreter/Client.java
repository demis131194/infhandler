package by.epamgroup.infhandler.interpreter;

import by.epamgroup.infhandler.exception.IllegalExpressionException;
import by.epamgroup.infhandler.util.Cursor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {
    private static Logger logger = LogManager.getLogger();
    private static int countOperation;
    private static final String NUMBER_REGEX = "-?[\\d]+";

    private Client() {
    }

    public static Expression evaluate(String expression) throws IllegalExpressionException {
        logger.trace("In evaluate.");
        logger.info("Evaluated expression: " + expression);
        expression = expression.replaceAll(">>", ">");
        expression = expression.replaceAll("<<", "<");
        Expression resultExpression = calculate(expression);
        logger.info("Result of evaluate = " + resultExpression.interpret() + ", countOperation = " + countOperation);
        countOperation = 0;
        return resultExpression;
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
        countOperation++;
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
        int rightCursor = cursor.getRightCursor();
        int operatorCursor = cursor.getOperatorCursor();

        char rightChar = expression.charAt(rightCursor);

        if (Character.isDigit(rightChar)) {

            try {
                while (Character.isDigit(rightChar) || rightChar == '-') {
                    rightChar = expression.charAt(++rightCursor);
                }

                cursor.setRightCursor(rightCursor);
                rightCursor--;
            } catch (Exception e) {
                rightCursor--;
                cursor.setRightCursor(rightCursor + 1);
            }

            int number = Integer.parseInt(expression.substring(operatorCursor + 1, rightCursor + 1));
            return new NumberExpression(number);

        } else if (rightChar == '(') {
            int breaksCount = 1;

            while (breaksCount > 0) {
                rightChar = expression.charAt(++rightCursor);

                if (rightChar == ')') {
                    breaksCount--;
                } else if (rightChar == '(') {
                    breaksCount++;
                }

            }
            cursor.setRightCursor(rightCursor + 1);
            String newExpression = expression.substring(operatorCursor + 2, rightCursor);
            return calculate(newExpression);
        }

        throw new IllegalExpressionException("Wrong expression!");
    }

    private static Expression getLeftExpression(String expression, Cursor cursor) throws IllegalExpressionException {
        int leftCursor = cursor.getLeftCursor();
        int operatorCursor = cursor.getOperatorCursor();

        char leftChar = expression.charAt(leftCursor);

        if (Character.isDigit(leftChar)) {

            try {
                while (Character.isDigit(leftChar) || leftChar == '-') {
                    leftChar = expression.charAt(--leftCursor);
                }

                cursor.setLeftCursor(leftCursor + 1);
                leftCursor++;

            } catch (Exception e) {
                leftCursor++;
                cursor.setLeftCursor(leftCursor);
            }

            int number = Integer.parseInt(expression.substring(leftCursor, operatorCursor));
            return new NumberExpression(number);

        } else if (leftChar == ')') {
            int breaksCount = 1;

            while (breaksCount > 0) {
                leftChar = expression.charAt(--leftCursor);

                if (leftChar == ')') {
                    breaksCount++;
                } else if (leftChar == '(') {
                    breaksCount--;
                }

            }
            cursor.setLeftCursor(leftCursor);
            leftCursor++;
            String newExpression = expression.substring(leftCursor, operatorCursor - 1);
            return calculate(newExpression);
        }

        throw new IllegalExpressionException("Wrong expression!");
    }
}
