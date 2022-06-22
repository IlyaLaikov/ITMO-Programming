package expression;

import java.math.BigInteger;
import java.util.function.BiFunction;
import java.util.function.IntBinaryOperator;

public class Multiply extends AbstractBinaryOperator {
    private static final IntBinaryOperator OPERATOR = (int x, int y) -> (x * y);
    private static final String OPERATOR_STRING = "*";
    private static final int PRIORITY = 1;
    private static final boolean IS_SYMMETRICAL = true;

    public Multiply(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    protected IntBinaryOperator getIntOperator() {
        return OPERATOR;
    }

    @Override
    protected BiFunction<BigInteger, BigInteger, BigInteger> getBigIntegerOperator() {
        return BigInteger::multiply;
    }

    @Override
    protected String getOperatorString() {
        return OPERATOR_STRING;
    }

    @Override
    protected int getPriority() {
        return PRIORITY;
    }

    @Override
    protected boolean isSymmetrical() {
        return IS_SYMMETRICAL;
    }
}
