package by.epamgroup.infhandler.interpreter;

import by.epamgroup.infhandler.util.Cursor;

public class Client {

    public Expression evaluate(String expression) {
        expression = expression.replaceAll(">>", ">");
        expression = expression.replaceAll("<<", "<");

        while (!expression.matches("-?[\\d]+")) {

            if (expression.contains("~")) {
                expression = executeOperation(expression, Operation.NOT);
            }
            if (expression.contains(">")) {
                expression = rightShiftBitOperation(expression);
            }
            if (expression.contains("<")) {
                expression = leftShiftBitOperation(expression);
            }
            if (expression.contains("&")) {
                expression = andOperation(expression);
            }

        }

        return new NumberExpression(Integer.parseInt(expression));
    }

    private String executeOperation(String expression, Operation operation) {
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
                 resultExpression = new NotExpression(rightExpression);
                break;
            case LEFT_SHIFT:
                resultExpression = new LeftBitShiftExpression(leftExpression, rightExpression);
                break;
            case RIGHT_SHIFT:
                resultExpression = new RightBitShiftExpression(leftExpression, rightExpression);
                break;
            case AND:
                resultExpression = new AndExpression(leftExpression, rightExpression);
                break;
        }

        int result = resultExpression.interpret();
        String replacedPartExpression = expression.substring(cursor.getLeftCursor(), cursor.getRightCursor());
        return expression.replace(replacedPartExpression, String.valueOf(result));
    }

    private String andOperation(String expression) {
        int operatorCursor = expression.indexOf('&');
        Cursor cursor = new Cursor(operatorCursor - 1, operatorCursor + 1, operatorCursor);

        Expression leftExpression = getLeftExpression(expression, cursor);
        Expression rightExpression = getRightExpression(expression, cursor);

        Expression resultExpression = new LeftBitShiftExpression(leftExpression, rightExpression);
        int result = resultExpression.interpret();
        String replacedPartExpression = expression.substring(cursor.getLeftCursor(), cursor.getRightCursor());
        return expression.replace(replacedPartExpression, String.valueOf(result));
    }

    private String notOperation(String expression) {
        int operatorCursor = expression.indexOf('~');
        Cursor cursor = new Cursor(operatorCursor, operatorCursor + 1, operatorCursor);

        Expression rightExpression = getRightExpression(expression, cursor);

        Expression resultExpression = new NotExpression(rightExpression);
        int result = resultExpression.interpret();
        String replacedPartExpression = expression.substring(cursor.getLeftCursor(), cursor.getRightCursor());
        return expression.replace(replacedPartExpression, String.valueOf(result));
    }

    private String leftShiftBitOperation(String expression) {
        int operatorCursor = expression.indexOf('<');
        Cursor cursor = new Cursor(operatorCursor - 1, operatorCursor + 1, operatorCursor);

        Expression leftExpression = getLeftExpression(expression, cursor);
        Expression rightExpression = getRightExpression(expression, cursor);

        Expression resultExpression = new LeftBitShiftExpression(leftExpression, rightExpression);
        int result = resultExpression.interpret();
        String replacedPartExpression = expression.substring(cursor.getLeftCursor(), cursor.getRightCursor());
        return expression.replace(replacedPartExpression, String.valueOf(result));
    }


    private String rightShiftBitOperation(String expression) {
        int operatorCursor = expression.indexOf('>');
        Cursor cursor = new Cursor(operatorCursor - 1, operatorCursor + 1, operatorCursor);

        Expression leftExpression = getLeftExpression(expression, cursor);
        Expression rightExpression = getRightExpression(expression, cursor);

        Expression resultExpression = new RightBitShiftExpression(leftExpression, rightExpression);
        int result = resultExpression.interpret();
        String replacedPartExpression = expression.substring(cursor.getLeftCursor(), cursor.getRightCursor());
        return expression.replace(replacedPartExpression, String.valueOf(result));
    }



    private Expression getRightExpression(String expression, Cursor cursor) {
        int rightCursor = cursor.getRightCursor();
        int operatorCursor = cursor.getOperatorCursor();

        char rightChar = expression.charAt(rightCursor);

        if (Character.isDigit(rightChar)) {

            try {
                while (Character.isDigit(rightChar)) {
                    rightChar = expression.charAt(++rightCursor);
                }

                if (rightChar == ')') {
                    cursor.setRightCursor(rightCursor + 1);
                } else {
                    cursor.setRightCursor(rightCursor);
                }

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
            return evaluate(newExpression);
        }

        throw new IllegalArgumentException("Wrong expression!");
    }

    private Expression getLeftExpression(String expression, Cursor cursor) {
        int leftCursor = cursor.getLeftCursor();
        int operatorCursor = cursor.getOperatorCursor();

        char leftChar = expression.charAt(leftCursor);

        if (Character.isDigit(leftChar)) {

            try {
                while (Character.isDigit(leftChar)) {
                    leftChar = expression.charAt(--leftCursor);
                }
                if (leftChar == '(') {
                    cursor.setLeftCursor(leftCursor);
                } else {
                    cursor.setLeftCursor(leftCursor + 1);
                }

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
            return evaluate(newExpression);
        }

        throw new IllegalArgumentException("Wrong expression!");
    }
}
