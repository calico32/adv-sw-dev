package lab02_school_inheritance;

import java.util.ArrayList;
import java.util.UUID;

public class Teacher extends FacultyMember {
    public ArrayList<String> courses = new ArrayList<>();

    public Teacher(String name, int age, School school, String department) {
        super(name, age, school, department);
    }

    public Teacher(String name, int age, School school, String department, ArrayList<String> courses) {
        super(name, age, school, department);
        this.courses = courses;
    }

    public Teacher(String name, int age, UUID schoolId, String department) {
        super(name, age, schoolId, department);
    }

    public Teacher(String name, int age, UUID schoolId, String department, ArrayList<String> courses) {
        super(name, age, schoolId, department);
        this.courses = courses;
    }

    public String toString() {
        return String.format("%s, teaches:\n%s", super.toString(), coursesToString());
    }

    public void addCourse(String code, String name) {
        courses.add(String.format("%s: %s", code, name));
    }

    public String coursesToString() {
        var s = new StringBuilder();
        for (String course : courses) {
            s.append(String.format("  %s\n", course));
        }
        return !s.isEmpty() ? s.substring(0, s.length() - 1) : "";
    }
}
