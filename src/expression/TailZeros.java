package expression;

import java.math.BigInteger;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;

public class TailZeros extends AbstractUnaryOperator {
    private static final IntUnaryOperator OPERATOR = (int x) -> {
        int ctr = 0;
        for (int i = 1; i != 0 && (x & i) == 0; i <<= 1) {
            ctr++;
        }
        return ctr;
    };
    private static final Function<BigInteger, BigInteger> BIGINT_OPERATOR = (BigInteger x) -> BigInteger
            .valueOf(OPERATOR.applyAsInt(x.intValue()));
    private static final String OPERATOR_STRING = "t0";

    public TailZeros(CommonExpression child) {
        super(child);
    }

    @Override
    protected IntUnaryOperator getIntOperator() {
        return OPERATOR;
    }

    @Override
    protected Function<BigInteger, BigInteger> getBigIntegerOperator() {
        return BIGINT_OPERATOR;
    }

    @Override
    protected String getOperatorString() {
        return OPERATOR_STRING;
    }
}
