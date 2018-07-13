package me.sylvaeon.umbreon;

import org.jetbrains.annotations.NotNull;

public final class Counter extends Number implements Comparable<Counter> {
	private static final long serialVersionUID = 1L;

	public long count;

	public Counter() {
		this.count = 0;
	}

	public Counter(long count) {
		this.count = count;
	}

	public void add() {
		count++;
	}

	public void subtract() {
		count--;
	}

	public void add(long i) {
		count += i;
	}

	public void subtract(long i) {
		count -= i;
	}

	public void add(Counter i) {
		count += i.count;
	}

	public void subtract(Counter i) {
		count -= i.count;
	}

	public boolean positive() {
		return count > 0;
	}

	@Override
	public int compareTo(@NotNull Counter o) {
		return Math.toIntExact(count - o.count);
	}

	@Override
	public String toString() {
		return count + "";
	}
	
	@Override
	public byte byteValue() {
		return new Long(count).byteValue();
	}
	
	@Override
	public short shortValue() {
		return new Long(count).shortValue();
	}
	
	@Override
	public int intValue() {
		return new Long(count).intValue();
	}
	
	@Override
	public long longValue() {
		return count;
	}
	
	@Override
	public float floatValue() {
		return new Long(count).floatValue();
	}
	
	@Override
	public double doubleValue() {
		return new Long(count).doubleValue();
	}
}
