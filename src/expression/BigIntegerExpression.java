package expression;

import java.math.BigInteger;

@FunctionalInterface
public interface BigIntegerExpression extends ToMiniString {
    BigInteger evaluate(BigInteger x);
}