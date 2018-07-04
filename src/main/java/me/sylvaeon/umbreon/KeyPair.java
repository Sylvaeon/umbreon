package me.sylvaeon.umbreon;

public class KeyPair {
	private Object r, c;

	public KeyPair(Object r, Object c) {
		this.r = r;
		this.c = c;
	}

	public KeyPair(String string) {
		String[] split = string.split(",");
		try {
			this.r = Integer.parseInt(split[0]);
		} catch (Exception e) {
			this.r = split[0];
		}
		try {
			this.c = Integer.parseInt(split[1]);
		} catch (Exception e) {
			this.c = split[0];
		}
	}

	@Override
	public String toString() {
		return r + "," + c;
	}

	public Object getR() {
		return r;
	}

	public void setR(Object r) {
		this.r = r;
	}

	public Object getC() {
		return c;
	}

	public void setC(Object c) {
		this.c = c;
	}
}
