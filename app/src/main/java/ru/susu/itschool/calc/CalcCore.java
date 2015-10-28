package ru.susu.itschool.calc;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by Александр on 27.10.2015.
 */
public class CalcCore {


    public String getOPN(String inputString) throws EmptyStackException {
        LinkedList<String> opn = new LinkedList<>();
        Stack<String> stack = new Stack<>();
        ArrayList<String> symbols = new ArrayList<>();

        boolean isPrewNum = false;
        String[] params = inputString.split("");
        for (int i = 0; params.length > i; i++) {
            if (isNumber(params[i])) {
                if (isPrewNum) {
                    symbols.set(symbols.size() - 1, symbols.get(symbols.size() - 1) + params[i]);
                } else {
                    symbols.add(params[i]);
                }
                isPrewNum = true;
            } else {
                symbols.add(params[i]);
                isPrewNum = false;
            }
        }

        for (String currentSymbol : symbols) {

            // число сразу в выходную строку
            if (isNumber(currentSymbol)) {
                opn.add(currentSymbol + " ");
                continue;
            }

            // скобку сохраним в стеке операций
            if (currentSymbol.equals("(") || stack.empty()) {
                stack.push(currentSymbol);
                continue;
            }

            if (isOperator(currentSymbol)) {
                /**
                 * если верхний в стеке оператор имеет больший
                 * приоритет, чем приоритет текущего оператора, то
                 * извлекаем символы из стека в выходную строку
                 * до тех пор, пока выполняется это условие
                 */
                while (!stack.isEmpty()
                        && priorityOfOperation(stack.peek()) >= priorityOfOperation(currentSymbol)) {
                    opn.add(stack.pop() + " ");
                }
                stack.push(currentSymbol);
                continue;
            }

            /**
             * если закрывающая скобка, то извлекаем символы из
             * стека операций в выходную строку до тех пор,
             * пока не встретим открывающую скобку.
             */
            if (currentSymbol.equals(")")) {
                while (!stack.peek().equals("(")) {
                    opn.add(stack.pop() + " ");
                }
                stack.pop(); // выталкиваем саму скобку.
                continue;
            }
        }

        /**
         * отложенные в стеке операторы добавляем
         * в выходную строку.
         */
        while (!stack.empty()) {
            opn.add(stack.pop() + " ");
        }

        // крепим вместе последовательность - и на выход
        StringBuilder sb = new StringBuilder();
        for (String s : opn)
            sb.append(s);

        return sb.toString();
    }

    private boolean isNumber(String currentSymbol) {
        try {
            Double.parseDouble(currentSymbol);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isOperator(String c) {
        return c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/")
                || c.equals("^");
    }

    private int priorityOfOperation(String temp) {
        switch (temp) {
            case "^":
                return 3;
            case "/":
            case "*":
                return 2;
            case "+":
            case "-":
                return 1;
            default:
                return 0;
        }
    }

    public double calculateResult(String OPN) {
        Stack<String> stack = new Stack<>();

        for (String currentSymbol : OPN.split(" ")) {
            if (isNumber(currentSymbol)) {
                stack.push(currentSymbol);
                continue;
            }

            if (isOperator(currentSymbol)) {
                double result = 0;
                double first = Double.parseDouble(stack.pop());
                double second = Double.parseDouble(stack.pop());

                switch (currentSymbol) {
                    case "^":
                        result = Math.pow(second, first);
                        break;
                    case "/":
                        result = second / first;
                        break;
                    case "*":
                        result = second * first;
                        break;
                    case "+":
                        result = second + first;
                        break;
                    case "-":
                        result = second - first;
                        break;
                }
                stack.push(String.valueOf(result));
            }
        }
        return Double.parseDouble(stack.pop());
    }
}
