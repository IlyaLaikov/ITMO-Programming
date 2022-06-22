package expression.parser;

import expression.TripleExpression;

@FunctionalInterface
public interface CheckedParser {
    TripleExpression parse(String expression) throws Exception;
}
