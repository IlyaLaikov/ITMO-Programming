package expression.parser;

import expression.*;

import java.util.Map;

public class ExpressionParser implements Parser {
    @Override
    public TripleExpression parse(final String source) {
        return parse(new StringSource(source));
    }

    public static TripleExpression parse(final CharSource source) {
        return new InnerExpressionParser(source).parseExpression();
    }

    private static class InnerExpressionParser extends BaseParser {
        public InnerExpressionParser(final CharSource source) {
            super(source);
        }

        public CommonExpression parseExpression() {
            CommonExpression result = parseSubexpression();
            if (eof()) {
                return result;
            }
            throw error("End of Expression expected");
        }

        private static class OperatorBank {
            private final CommonExpression[] exprList = new CommonExpression[COUNT_OF_PRIORITY_LEVELS + 1];
            private final String[] operatorList = new String[COUNT_OF_PRIORITY_LEVELS];
            private int size = 0;

            public OperatorBank(CommonExpression firstExpr) {
                exprList[0] = firstExpr;
            }

            public void add(String op, CommonExpression expr) {
                while (size > 0 && PRIORITY.get(op) >= PRIORITY.get(operatorList[size - 1])) {
                    mergeLast();
                }
                operatorList[size] = op;
                exprList[size + 1] = expr;
                ++size;
            }

            public CommonExpression getExpr() {
                while (size > 0) {
                    mergeLast();
                }
                return exprList[0];
            }

            private void mergeLast() {
                --size;
                exprList[size] = makeExpression(operatorList[size], exprList[size], exprList[size + 1]);
            }

            private CommonExpression makeExpression(String operator, CommonExpression a, CommonExpression b) {
                switch (operator) {
                    case "+":
                        return new Add(a, b);
                    case "-":
                        return new Subtract(a, b);
                    case "*":
                        return new Multiply(a, b);
                    case "/":
                        return new Divide(a, b);
                    case "min":
                        return new Min(a, b);
                    case "max":
                        return new Max(a, b);
                    default:
                        throw new IllegalArgumentException("Unsupported operator: " + operator);
                }
            }
        }

        private CommonExpression parseSubexpression() {
            OperatorBank bank = new OperatorBank(parseAtom());

            skipWhitespaces();
            while (!take(')') && !eof()) {
                bank.add(takeOperator(), parseAtom());
                skipWhitespaces();
            }

            return bank.getExpr();
        }

        private CommonExpression parseAtom() {
            skipWhitespaces();
            if (take('(')) {
                return parseSubexpression();
            } else if (take('-')) {
                if (between('1', '9')) {
                    return parseNumber(true);
                } else {
                    return new Negate(
                            parseAtom());
                }
            } else if (take('l')) {
                expect('0');
                return new LeadZeros(
                        parseAtom());
            } else if (take('t')) {
                expect('0');
                return new TailZeros(
                        parseAtom());
            } else if (take('a')) {
                expect("bs");
                return new Abs(
                        parseAtom());
            } else if (between('0', '9')) {
                return parseNumber(false);
            } else if (between('x', 'z')) { // or "if (take('x') || take('y') || take('z'))"
                return new Variable(
                        // parse variable
                        String.valueOf(take()));
            }
            throw error("Undefined state");
        }

        private static final int COUNT_OF_PRIORITY_LEVELS = 3;
        private static final Map<String, Integer> PRIORITY = Map.ofEntries(
                Map.entry("min", 3),
                Map.entry("max", 3),
                Map.entry("+", 2),
                Map.entry("-", 2),
                Map.entry("*", 1),
                Map.entry("/", 1));

        private String takeOperator() {
            if (test('+') || test('-') || test('*') || test('/')) {
                return String.valueOf(take());
            } else if (take('m')) {
                if (take('i')) {
                    expect("n");
                    return "min";
                } else if (take('a')) {
                    expect("x");
                    return "max";
                } else {
                    throw error("'i' of 'a' expected");
                }
            } else {
                throw error("Not found binary operator");
            }
        }

        private CommonExpression parseNumber(boolean minus) {
            StringBuilder sb = new StringBuilder();
            if (minus) {
                sb.append('-');
            }

            takeInteger(sb);

            return new Const(Integer.parseInt(sb.toString()));
        }

        private void takeInteger(final StringBuilder sb) {
            if (take('0')) {
                sb.append('0');
            } else if (between('1', '9')) {
                takeDigits(sb);
            } else {
                throw error("Invalid number");
            }
        }

        private void takeDigits(final StringBuilder sb) {
            while (between('0', '9')) {
                sb.append(take());
            }
        }
    }
}
