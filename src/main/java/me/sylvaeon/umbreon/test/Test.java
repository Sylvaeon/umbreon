package me.sylvaeon.umbreon.test;

public class Test {
	public static void main(String[] args) {
		Car car = new Car(3);
		int people = car.getPeople();
		car.setPeople(5);
		System.out.println(people);
		System.out.println(car.getPeople());
	}
}
