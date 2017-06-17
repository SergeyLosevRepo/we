package com.tsystems.javaschool.tasks.calculator;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        // TODO: Implement the logic here
        if (statement == null || statement.isEmpty()) {return null;}
        if (statement.charAt(0) == '-') statement = "0"+statement;
        String s1 = statement.replace("(-", "(0-");
        statement = eval(s1);
        if (statement == null) {
            return statement;
        }
        else {
            Double d = BigDecimal.valueOf(Double.parseDouble(statement)).setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue();
            statement = d.toString();
            if (statement.substring(statement.indexOf(".")).equals(".0")) {
                return statement.substring(0,statement.indexOf("."));
            } else
                return statement;
        }
    }

    static boolean isDelim(char c) { // тру если пробел
        return c == ' ';
    }
    static boolean isOperator(char c) { // возвращяем тру если один из символов ниже
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }
    static int priority(char op) {
        switch (op) { // при + или - возврат 1, при * / % 2 иначе -1
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }
    static void processOperator(LinkedList<Double> st, char op) {
        double r = st.removeLast(); // выдёргиваем из упорядоченного листа последний элемент
        double l = st.removeLast(); // также
        switch (op) { // выполняем действие между l и r в зависимости от оператора в кейсе и результат валим в st
            case '+':
                st.add(l + r);
                break;
            case '-':
                st.add(l - r);
                break;
            case '*':
                st.add(l * r);
                break;
            case '/':
                if (r == 0) st.add(null);
                st.add(l / r);
                break;
        }
    }

    public static String eval(String s) {
        LinkedList<Double> st = new LinkedList<Double>(); // итоговая строка
        LinkedList<Character> op = new LinkedList<Character>(); // операторы
        try {
            for (int i = 0; i < s.length(); i++) { // парсим строку
                char c = s.charAt(i);
                if (isDelim(c))
                    continue;
                if (c == '(')
                    op.add('(');
                else if (c == ')') {
                    while (op.getLast() != '(')
                        processOperator(st, op.removeLast());
                    op.removeLast();
                } else if (isOperator(c)) {
                    while (!op.isEmpty() && priority(op.getLast()) >= priority(c))
                        processOperator(st, op.removeLast());
                    op.add(c);
                } else {
                    String operand = "";
                    while (i < s.length()) {
                        if (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.') {
                            operand += s.charAt(i);
                            i++;
                        } else break;
                    }
                    --i;
                    st.add(Double.parseDouble(operand));
                }
            }
            while (!op.isEmpty())
                processOperator(st, op.removeLast());
            return st.get(0).toString(); // возврат результата
        }catch (Exception e){
            return null;
        }
    }



}
