package lab02_school_inheritance;

import java.util.HashMap;
import java.util.UUID;

public class School {
    public UUID id;
    public String name;
    public String shortName;
    public String address;
    public HashMap<UUID, SchoolPerson> people = new HashMap<>();

    public School(String name, String shortName, String address) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.shortName = shortName;
        this.address = address;

        SchoolRegistry.addSchool(this);
    }

    public String toString() {
        var s = new StringBuilder();
        s.append(String.format("%s\n%s\n\nPeople:\n", name, address));

        for (SchoolPerson person : people.values()) {
            s.append(indent(person.toString(), 2));
        }


        return s.toString();
    }

    public String indent(String s, int indent) {
        var lines = s.split("\n");
        var sb = new StringBuilder();
        for (String line : lines) {
            sb.append(" ".repeat(indent));
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }
}
