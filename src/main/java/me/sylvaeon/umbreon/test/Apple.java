package me.sylvaeon.umbreon.test;

public class Apple extends Fruit {
	public String type;
	protected long length;
	private boolean round;

	public Apple(String name, String type) {
		super(name);
		this.type = type;
	}
}
