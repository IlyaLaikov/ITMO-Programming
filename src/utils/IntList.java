package utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

class IntList implements Cloneable, RandomAccess {
    private int[] data;
    private int size;

    private static final int RECAPACITY_FACTOR = 2;
    private static final int DEFAULT_CAPACITY = 16;

    public IntList() {
        this(IntList.DEFAULT_CAPACITY);
    }

    public IntList(Collection<? extends Integer> c) {
        this(Math.min(IntList.DEFAULT_CAPACITY, c.size()));
        int i = 0;
        for (Integer integer : c) {
            this.data[i++] = integer;
        }
    }

    public IntList(int initialCapacity) {
        this.data = new int[initialCapacity];
        this.size = 0;
    }

    public IntList(IntList intList) {
        this.data = intList.data.clone();
        this.size = intList.size;
    }

    public boolean add(int value) {
        this.add(this.size, value);
        return true;
    }

    public void add(int index, int value) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        if (this.size == this.data.length) {
            this.data = Arrays.copyOf(this.data, this.data.length * IntList.RECAPACITY_FACTOR);
        }
        System.arraycopy(this.data, index, this.data, index + 1, this.size - index);
        this.data[index] = value;
        this.size++;
    }

    public boolean addAll(Collection<? extends Integer> c) {
        for (Integer integer : c) {
            this.add(integer.intValue());
        }

        return c.size() != 0;
    }

    public boolean addAll(int index, Collection<? extends Integer> collection) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        if (collection.size() > this.data.length - this.size) {
            this.data = Arrays.copyOf(this.data, (this.data.length + collection.size()) * 2);
        }
        System.arraycopy(this.data, index, this.data, index + collection.size(), this.size - index);
        for (Integer integer : collection) {
            this.data[index++] = integer.intValue();
        }
        this.size += collection.size();
        return true;
    }

    public void clear() {
        this.size = 0;
    }

    public boolean contains(Object obj) {
        return this.indexOf(obj) != -1;
    }

    public boolean containsAll(Collection<?> c) {
        for (Object obj : c) {
            if (this.indexOf(obj) != -1) {
                return false;
            }
        }
        return true;
    }

    public int get(int index) {
        if (index >= this.data.length) {
            throw new IndexOutOfBoundsException();
        }
        return this.data[index];
    }

    public int indexOf(Object o) {
        if (o instanceof Integer) {
            Integer obj = (Integer) o;
            for (int i = 0; i < this.size; ++i) {
                if (this.data[i] == obj) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int lastIndexOf(Object o) {
        if (o instanceof Integer) {
            Integer obj = (Integer) o;
            for (int i = this.size - 1; i >= 0; --i) {
                if (this.data[i] == obj) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean remove(Object o) {
        if (o instanceof Integer) {
            int index = this.indexOf(o);
            System.arraycopy(this.data, index + 1, this.data, index, this.size - index);
            return true;
        }
        this.capacityFit();
        return false;
    }

    public int remove(int index) {
        if (index >= this.data.length) {
            throw new IndexOutOfBoundsException();
        }
        int value = this.data[index];
        System.arraycopy(this.data, index + 1, this.data, index, this.size - index);
        this.capacityFit();
        return value;
    }

    public boolean removeAll(Collection<?> c) {
        boolean removed = false;
        for (Object obj : c) {
            int index = this.indexOf(obj);
            if (index != -1) {
                this.remove(index);
                removed = true;
            }
        }
        return removed;
    }

    public void removeRange(int fromIndex, int toIndex) {
        if (fromIndex > this.size || toIndex > this.size) {
            throw new IndexOutOfBoundsException();
        }
        if (toIndex < fromIndex) {
            throw new IndexOutOfBoundsException();
        }
        System.arraycopy(this.data, toIndex, this.data, fromIndex, this.size - toIndex);
        this.capacityFit();
    }

    public boolean retainAll(Collection<?> c) {
        boolean removed = false;
        for (int i = 0; i < this.size; ++i) {
            if (!c.contains(this.data[i])) {
                this.remove(i);
                removed = true;
            }
        }
        return removed;
    }

    public int set(int index, int value) {
        if (index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        int previous = this.data[index];
        this.data[index] = value;
        return previous;
    }

    public int size() {
        return this.size;
    }

    public IntList subList(int fromIndex, int toIndex) {
        IntList subList = new IntList();
        subList.data = Arrays.copyOfRange(this.data, fromIndex, toIndex);
        subList.size = toIndex - fromIndex;
        return subList;
    }

    public int[] toArray() {
        return Arrays.copyOf(this.data, this.size);
    }

    @Override
    public IntList clone() {
        IntList clone = new IntList();
        clone.data = this.data.clone();
        clone.size = this.size;
        return clone;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.toArray());
    }

    private void capacityFit() {
        if (this.data.length < IntList.DEFAULT_CAPACITY * IntList.RECAPACITY_FACTOR) {
            return;
        }
        if (this.size * IntList.RECAPACITY_FACTOR < this.data.length / IntList.RECAPACITY_FACTOR) {
            this.data = Arrays.copyOf(this.data, this.data.length / IntList.RECAPACITY_FACTOR);
        }
    }
}
