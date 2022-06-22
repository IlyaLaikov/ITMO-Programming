package expression;

import java.math.BigInteger;

public class Const implements CommonExpression {
    private final Object value;
    private final String toString;
    private final int hashCode;

    public Const(int value) {
        this.value = value;
        this.toString = Integer.toString((Integer) value);
        this.hashCode = value;
    }

    public Const(BigInteger value) {
        this.value = value;
        this.toString = value.toString();
        this.hashCode = value.hashCode();
    }

    @Override
    public int evaluate(int var) {
        return (Integer) value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (Integer) value;
    }

    @Override
    public BigInteger evaluate(BigInteger var) {
        return (BigInteger) value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Const) {
            Const c = (Const) obj;
            return value.equals(c.value);
        }
        return false;
    }

    @Override
    public String toString() {
        return toString;
    }

    @Override
    public String toMiniString() {
        return toString;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}
