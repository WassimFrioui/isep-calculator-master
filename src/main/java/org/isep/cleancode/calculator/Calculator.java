package org.isep.cleancode.calculator;

public class Calculator {
    public double evaluateMathExpression(String expression) {

        String[] parts = expression.replaceAll("(?<=[-+*/()])|(?=[-+*/()])", " ").trim().split("\\s+");
        parts = evaluateOperators(parts, "*", "/");
        parts = evaluateOperators(parts, "+", "-");
        return Double.parseDouble(parts[0]);

    }

    private String[] evaluateOperators(String[] parts, String first_operation, String second_operation) {
        for (int i = 0; i < parts.length; i++) {
            if (parts[i].equals(first_operation) || parts[i].equals(second_operation)) {
                double left = Double.parseDouble(parts[i - 1]);
                double right = Double.parseDouble(parts[i + 1]);
                double result;
                switch (parts[i]) {
                    case "*":
                        result = left * right;
                        break;
                    case "/":
                        result = left / right;
                        break;
                    case "+":
                        result = left + right;
                        break;
                    case "-":
                        result = left - right;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected operator: " + parts[i]);
                }

                parts = replaceParts(parts, i - 1, i + 1, String.valueOf(result));
                return evaluateOperators(parts, first_operation, second_operation); 
            }
        }
        return parts;
    }

   
    private String[] replaceParts(String[] parts, int from_Index, int to_Index, String new_Value) {
        String[] result = new String[parts.length - (to_Index - from_Index)];
        int default_index = 0;
        for (int i = 0; i < parts.length; i++) {
            if (i < from_Index || i > to_Index) {
                result[default_index++] = parts[i];
            } else if (i == from_Index) {
                result[default_index++] = new_Value;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        String expression = "5 + 2";
        double result = calculator.evaluateMathExpression(expression);
        System.out.println("Result: " + result);  

    }
}
