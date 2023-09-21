package lab02_school_inheritance;

import java.util.UUID;

public class Student extends SchoolPerson {
    public String major;

    public Student(String name, int age, School school, String major) {
        super(name, age, school);
        this.major = major;
    }

    public Student(String name, int age, UUID schoolId, String major) {
        super(name, age, schoolId);
        this.major = major;
    }

    public String toString() {
        return String.format("%s, %d years old, studies %s at %s", name, age, major, school.shortName);
    }
}
