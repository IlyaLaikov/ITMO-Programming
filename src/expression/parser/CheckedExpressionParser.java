package expression.parser;

import expression.*;
import expression.exceptions.ParserException;

import java.util.Map;

public class CheckedExpressionParser implements CheckedParser {
    @Override
    public TripleExpression parse(final String source) {
        try {
            return parse(new StringSource(source));
        } catch (IllegalArgumentException exc) {
            throw new ParserException(exc.getMessage(), exc);
        }
    }

    public static TripleExpression parse(final CharSource source) {
        return new InnerExpressionParser(source).parseExpression();
    }

    private static class InnerExpressionParser extends BaseParser {
        private int scopeBalance = 0;

        public InnerExpressionParser(final CharSource source) {
            super(source);
        }

        public CommonExpression parseExpression() {
            CommonExpression result = parseSubexpression();
            if (eof() && scopeBalance == 0) {
                return result;
            }
            throw error("End of Expression expected");
        }

        class OperatorBank {
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
                        return new CheckedAdd(a, b);
                    case "-":
                        return new CheckedSubtract(a, b);
                    case "*":
                        return new CheckedMultiply(a, b);
                    case "/":
                        return new CheckedDivide(a, b);
                    case "min":
                        return new Min(a, b);
                    case "max":
                        return new Max(a, b);
                    case "**":
                        return new CheckedPow(a, b);
                    case "//":
                        return new CheckedLog(a, b);
                    default:
                        throw error("Unsupported operator: " + operator);
                }
            }
        }

        boolean afterScope = false;

        private CommonExpression parseSubexpression () {
            OperatorBank bank = new OperatorBank(parseAtom());

            while (!eof()) {
                String operator = takeOperator();
                if (operator.equals(")")) {
                    afterScope = true;
                    if (scopeBalance > 0) {
                        --scopeBalance;
                    } else {
                        throw error("Wrong scope sequence");
                    }
                    return bank.getExpr();
                } else {
                    afterScope = false;
                    bank.add(operator, parseAtom());
                }
            }

            return bank.getExpr();
        }

        private CommonExpression parseAtom () {
            skipWhitespaces();
            if (take('(')) {
                ++scopeBalance;
                return parseSubexpression();
            } else if (take('-')) {
                if (between('1', '9')) {
                    return parseNumber(true);
                } else {
                    return new CheckedNegate(
                            parseAtom()
                    );
                }
            } else if (take('l')) {
                expect('0');
                return new LeadZeros(
                        parseAtom()
                );
            } else if (take('t')) {
                expect('0');
                return new TailZeros(
                        parseAtom()
                );
            } else if (take('a')) {
                expect("bs");
                if (!test('(')) {
                    expectWhitespace();
                }
                return new CheckedAbs(
                        parseAtom()
                );
            } else if (between('0', '9')) {
                return parseNumber(false);
            } else if (between('x', 'z')) { // or "if (take('x') || take('y') || take('z'))"
                return new Variable(
                        // parse variable
                        String.valueOf(take())
                );
            }
            throw error("Number or unary operator expected");
        }

        private static final int COUNT_OF_PRIORITY_LEVELS = 4;
        private static final Map<String, Integer> PRIORITY = Map.ofEntries(
                Map.entry("min", 3),
                Map.entry("max", 3),
                Map.entry("+", 2),
                Map.entry("-", 2),
                Map.entry("*", 1),
                Map.entry("/", 1),
                Map.entry("**", 0),
                Map.entry("//", 0)
        );

        private String takeOperator () {
            boolean ws = skipWhitespaces();
            if (test('+') || test('-')) {
                return String.valueOf(take());
            } else if (take('*')) {
                if (take('*')) {
                    return "**";
                }
                return "*";
            } else if (take('/')) {
                if (take('/')) {
                    return "//";
                }
                return "/";
            } else if (take(')')) {
                return ")";
            } else if (take('m')) {
                if (afterScope || ws) {
                    if (take('i')) {
                        expect("n");
                        return "min";
                    } else if (take('a')) {
                        expect("x");
                        return "max";
                    } else {
                        throw error("'i' or 'a' expected");
                    }
                } else {
                    throw error("Missing whitespace before min or max");
                }
            } else {
                throw error("Not found binary operator: " + take());
            }
        }

        private CommonExpression parseNumber (boolean minus){
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
