package lab02_school_inheritance;


public class Person {
    public String name;
    public int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return String.format("%s, %d years old", name, age);
    }
}

