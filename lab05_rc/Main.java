package lab05_rc;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

import static lab05_rc.Util.formatDouble;

public class Main {
    public static void main(String[] args) {
        try {
            var params = RCParams.load("rc.in");
            System.out.println("Loaded parameters from rc.in");
            System.out.printf("B: %s V\tR: %s Ω\tC: %s µF\tT₀: %s µs\tT₁: %s µs\n", formatDouble(params.b), formatDouble(params.resistance), formatDouble(params.capacitance), formatDouble(params.startTime), formatDouble(params.endTime));
            var rcs = new VoltageInstant[100];
            for (var i = 0; i < 100; i++) {
                var t = i * (params.endTime - params.startTime) / 100;
                var v = params.b * (1 - Math.exp(-t / (params.resistance * params.capacitance)));
                rcs[i] = new VoltageInstant(t, v);
            }
            var collection = new VoltageInstantCollection(rcs);
            collection.save("rc.out");
            System.out.println("Wrote data points to rc.out");
            collection = VoltageInstantCollection.load("rc.out");
            var riseTime = collection.riseTime(params.b);
            System.out.printf("Rise time: %sµs\n", formatDouble(riseTime));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }
}

class RCParams {
    public double b;
    public double resistance;
    public double capacitance;
    public double startTime;
    public double endTime;

    public RCParams(double b, double resistance, double capacitance, double startTime, double endTime) {
        this.b = b;
        this.resistance = resistance;
        this.capacitance = capacitance;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static RCParams load(String filename) throws IllegalArgumentException, FileNotFoundException {
        var file = new File(filename);
        var scanner = new Scanner(file);
        var b = Double.POSITIVE_INFINITY;
        var resistance = Double.POSITIVE_INFINITY;
        var capacitance = Double.POSITIVE_INFINITY;
        var startTime = Double.POSITIVE_INFINITY;
        var endTime = Double.POSITIVE_INFINITY;
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            var parts = line.split(" ");
            for (var part : parts) {
                var kv = part.split("=");
                switch (kv[0]) {
                    case "b" -> b = Double.parseDouble(kv[1]);
                    case "r" -> resistance = Double.parseDouble(kv[1]);
                    case "c" -> capacitance = Double.parseDouble(kv[1]);
                    case "t0" -> startTime = Double.parseDouble(kv[1]);
                    case "t1" -> endTime = Double.parseDouble(kv[1]);
                }
            }
        }

        if (b == Double.POSITIVE_INFINITY || resistance == Double.POSITIVE_INFINITY || capacitance == Double.POSITIVE_INFINITY || startTime == Double.POSITIVE_INFINITY || endTime == Double.POSITIVE_INFINITY) {
            throw new IllegalArgumentException("Invalid input");
        }

        return new RCParams(b, resistance, capacitance, startTime, endTime);
    }
}

class VoltageInstant {
    public double time;
    public double voltage;

    public VoltageInstant(double time, double voltage) {
        this.time = time;
        this.voltage = voltage;
    }
}

@SuppressWarnings("ResultOfMethodCallIgnored")
class VoltageInstantCollection {
    public VoltageInstant[] items;

    public VoltageInstantCollection(VoltageInstant[] items) {
        this.items = items;
    }

    public void save(String filename) throws IOException {
        var file = new File(filename);
        file.createNewFile();
        var writer = new BufferedWriter(new FileWriter(file));
        for (var rc : items) {
            writer.write(String.format("%s %s\n", formatDouble(rc.time), formatDouble(rc.voltage)));
        }
        writer.close();
    }

    public static VoltageInstantCollection load(String filename) throws FileNotFoundException {
        var file = new File(filename);
        var scanner = new Scanner(file);
        var rcs = new VoltageInstant[100];
        var i = 0;
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            var parts = line.split(" ");
            rcs[i] = new VoltageInstant(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]));
            i++;
        }
        return new VoltageInstantCollection(rcs);
    }

    public double riseTime(double b) {
        var t0 = 0.06 * b;
        var t1 = 0.95 * b;
        var t0Index = 0;
        var t1Index = 0;
        for (var i = 0; i < items.length; i++) {
            if (items[i].voltage > t0) {
                t0Index = i;
                break;
            }
        }
        for (var i = 0; i < items.length; i++) {
            if (items[i].voltage > t1) {
                t1Index = i;
                break;
            }
        }
        return items[t1Index].time - items[t0Index].time;
    }
}

class Util {
    public static String formatDouble(double d) {
        // no more than 5 decimal places
        return new BigDecimal(d).setScale(5, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }
}