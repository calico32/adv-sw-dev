package lab02_school_inheritance;

import java.util.UUID;

public class Administrator extends FacultyMember {
    public String title;

    public Administrator(String name, int age, School school, String title) {
        super(name, age, school, "Administration");
        this.title = title;
    }

    public Administrator(String name, int age, UUID schoolId, String title) {
        super(name, age, schoolId, "Administration");
        this.title = title;
    }

    public String toString() {
        return String.format("%s as %s", super.toString(), title);
    }
}
