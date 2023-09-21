package lab02_school_inheritance;

import java.util.UUID;

public class FacultyMember extends SchoolPerson {
    public String department;

    public FacultyMember(String name, int age, School school, String department) {
        super(name, age, school);
        this.department = department;
    }

    public FacultyMember(String name, int age, UUID schoolId, String department) {
        super(name, age, schoolId);
        this.department = department;
    }

    public String toString() {
        return String.format("%s, %d years old, works at %s in %s", name, age, school.shortName, department);
    }
}
