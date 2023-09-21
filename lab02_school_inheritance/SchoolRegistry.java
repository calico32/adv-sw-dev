package lab02_school_inheritance;

import java.util.HashMap;
import java.util.UUID;

public class SchoolRegistry {
    private static final HashMap<UUID, School> schools = new HashMap<>();

    public static void addSchool(School school) {
        schools.put(school.id, school);
    }

    public static School getSchool(UUID id) {
        return schools.get(id);
    }

    public static void printSchools() {
        System.out.println("Schools:");
        for (School school : schools.values()) {
        }
    }
}
