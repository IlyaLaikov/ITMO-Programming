package expression;

import java.math.BigInteger;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;

public abstract class AbstractUnaryOperator implements CommonExpression {
    protected final CommonExpression child;

    private final int hashCode;
    private String toString;
    private String toMiniString;

    protected AbstractUnaryOperator(CommonExpression child) {
        this.child = child;
        this.hashCode = Objects.hash(child, getClass());
    }

    protected abstract IntUnaryOperator getIntOperator();

    protected abstract Function<BigInteger, BigInteger> getBigIntegerOperator();

    protected abstract String getOperatorString();

    @Override
    public int evaluate(int x) {
        return getIntOperator().applyAsInt(
                child.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return getIntOperator().applyAsInt(
                child.evaluate(x, y, z));
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        return getBigIntegerOperator().apply(
                child.evaluate(x));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractUnaryOperator) {
            AbstractUnaryOperator op = (AbstractUnaryOperator) obj;
            return child.equals(op.child) && op.getClass().equals(getClass());
        }
        return false;
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = getOperatorString() + "(" + child + ")";
        }
        return toString;
    }

    @Override
    public String toMiniString() {
        if (toMiniString == null) {
            toMiniString = getOperatorString() + operandWrapper(child, child instanceof AbstractBinaryOperator);
        }
        return toMiniString;
    }

    private String operandWrapper(Expression expr, boolean inBrackets) {
        return (inBrackets ? "(" : " ") + expr.toMiniString() + (inBrackets ? ")" : "");
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
