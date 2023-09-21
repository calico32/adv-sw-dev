package lab02_school_inheritance;

public class Main {
    public static void main(String[] args) {
        School school = new School("University of Chicago", "UChicago", "Hyde Park, Chicago, IL");
        Student student = new Student("John Doe", 20, school, "Economics");
        Teacher teacher = new Teacher("Jane Doe", 30, school, "Economics");
        teacher.addCourse("E101", "Introduction to Economics");
        teacher.addCourse("E201", "Intermediate Economics");
        teacher.addCourse("E301", "Advanced Economics");
        Administrator administrator = new Administrator("Jack Doe", 40, school, "Dean");

        System.out.println(school);
    }
}
