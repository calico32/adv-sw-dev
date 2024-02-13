package lab04_file_io;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
    public static final String INPUT_FILE = "points.in";
    public static final String OUTPUT_FILE = "points.out";

    public static void main(String[] args) {
        PointCollection input;
        try {
            input = PointCollection.load(INPUT_FILE);
            System.out.println("Points loaded from " + INPUT_FILE);
        } catch (IOException e) {
            System.out.println("Could not load points from file");
            input = PointCollection.prompt();
            try {
                input.save(INPUT_FILE);
                System.out.println("Points saved to " + INPUT_FILE);
            } catch (IOException e2) {
                System.out.println("Could not save points to file");
            }
        }

        var output = new DoubleCollection();
        while (input.hasNext()) {
            var a = input.next();
            var b = input.next();
            if (b == null) {
                System.out.println("Warning: odd number of points, ignoring last point");
                break;
            }

            var dx = a.x - b.x;
            var dy = a.y - b.y;
            var dz = a.z - b.z;
            var distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

            System.out.printf("Distance between %s and %s is %f\n", a, b, distance);
            output.add(distance);
        }

        try {
            output.save(OUTPUT_FILE);
            System.out.println("Distances saved to " + OUTPUT_FILE);
        } catch (IOException e) {
            System.out.println("Could not save distances to file");
        }
    }
}

class Point {
    public double x;
    public double y;
    public double z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return String.format("(%f, %f)", x, y);
    }
}

abstract class Collection<T> {
    protected ArrayList<T> items;
    protected int current;

    public Collection(List<T> items) {
        this.items = new ArrayList<>(items);
        current = 0;
    }

    public Collection() {
        this.items = new ArrayList<>();
        current = 0;
    }

    public T next() {
        if (current >= items.size()) {
            return null;
        }
        return items.get(current++);
    }

    public boolean hasNext() {
        return current < items.size();
    }

    public void add(T item) {
        items.add(item);
    }

    public abstract void save(String fileName) throws IOException;
}

class PointCollection extends Collection<Point> {
    public PointCollection(List<Point> items) {
        super(items);
    }

    public void save(String fileName) throws IOException {
        var file = new File(fileName);
        file.createNewFile();

        var writer = new BufferedWriter(new FileWriter(file));
        for (var point : items) {
            var x = new BigDecimal(point.x).stripTrailingZeros().toPlainString();
            var y = new BigDecimal(point.y).stripTrailingZeros().toPlainString();
            var z = new BigDecimal(point.z).stripTrailingZeros().toPlainString();
            writer.write(String.format("(%s, %s, %s)\n", x, y, z));
        }

        writer.close();
    }

    public static PointCollection load(String fileName) throws IOException {
        var file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        var reader = new BufferedReader(new FileReader(file));
        var points = new ArrayList<Point>();
        String line;
        while ((line = reader.readLine()) != null) {
            var pattern = Pattern.compile("\\((\\d+(?:\\.\\d+)?), (\\d+(?:\\.\\d+)?), (\\d+(?:\\.\\d+)?)\\)");
            var matcher = pattern.matcher(line);
            if (matcher.find()) {
                var x = Double.parseDouble(matcher.group(1));
                var y = Double.parseDouble(matcher.group(2));
                var z = Double.parseDouble(matcher.group(3));
                points.add(new Point(x, y, z));
            } else {
                throw new IOException("Invalid file format");
            }
        }

        reader.close();

        return new PointCollection(points);
    }

    public static PointCollection prompt() {
        var points = new ArrayList<Point>();
        var scanner = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Enter a point (x, y, z), or press enter to continue: ");
            try {
                var input = scanner.readLine();
                if (input.isBlank()) {
                    break;
                }
                var parts = input.split(",");
                if (parts.length != 3) {
                    throw new IOException("Invalid input");
                }
                var x = Double.parseDouble(parts[0]);
                var y = Double.parseDouble(parts[1]);
                var z = Double.parseDouble(parts[2]);
                points.add(new Point(x, y, z));
            } catch (IOException e) {
                System.out.println("Invalid input");
            }
        }
        return new PointCollection(points);
    }
}



class DoubleCollection extends Collection<Double> {
    public DoubleCollection(List<Double> items) {
        super(items);
    }

    public DoubleCollection() {
        super();
    }

    public void save(String fileName) throws IOException {
        var file = new File(fileName);
        file.createNewFile();

        var writer = new BufferedWriter(new FileWriter(file));
        for (var f : items) {
            writer.write(String.format("%f\n", f));
        }

        writer.close();
    }

    public static DoubleCollection load(String fileName) throws IOException {
        var file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        var reader = new BufferedReader(new FileReader(file));
        var doubles = new ArrayList<Double>();
        String line;
        while ((line = reader.readLine()) != null) {
            doubles.add(Double.parseDouble(line));
        }

        reader.close();

        return new DoubleCollection(doubles);
    }
}