package lab02_school_inheritance;

import java.util.UUID;

public class SchoolPerson extends Person {
    public UUID id;
    public School school;

    public SchoolPerson(String name, int age, School school) {
        super(name, age);
        this.id = UUID.randomUUID();
        this.school = school;
        this.school.people.put(this.id, this);
    }

    public SchoolPerson(String name, int age, UUID schoolId) {
        super(name, age);
        this.id = UUID.randomUUID();
        this.school = SchoolRegistry.getSchool(schoolId);
        this.school.people.put(this.id, this);
    }

    public String toString() {
        return String.format("%s, %d years old, %s", name, age, school.shortName);
    }
}
