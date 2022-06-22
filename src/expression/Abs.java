package expression;

import java.math.BigInteger;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;

public class Abs extends AbstractUnaryOperator {
    private static final IntUnaryOperator OPERATOR = (int x) -> (x < 0 ? -x : x);
    private static final String OPERATOR_STRING = "abs";

    public Abs(CommonExpression child) {
        super(child);
    }

    @Override
    protected IntUnaryOperator getIntOperator() {
        return OPERATOR;
    }

    @Override
    protected Function<BigInteger, BigInteger> getBigIntegerOperator() {
        return BigInteger::abs;
    }

    @Override
    protected String getOperatorString() {
        return OPERATOR_STRING;
    }
}
