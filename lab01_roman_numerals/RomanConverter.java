package lab01_roman_numerals;

import static shared.TextHelpers.*;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class RomanConverter {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        println(
                text("Enter a number to convert. Type "), text("exit").bold(), text(" to exit.")
        );

        while (true) {
            try {
                print(text("> ").blue());
                var input = scanner.nextLine().trim();
                if (input.equals("exit")) {
                    break;
                }
                if (input.isEmpty()) {
                    continue;
                }

                // detect if input is a roman numeral
                if (input.charAt(0) >= 'A' && input.charAt(0) <= 'Z' || input.charAt(0) >= 'a' && input.charAt(0) <= 'z') {
                    var arabic = fromRoman(input);
                    println(text(arabic).green());
                } else {
                    var roman = toRoman(Integer.parseInt(input));
                    println(text(roman).green());
                }
            } catch (NumberFormatException e) {
                println(text("Invalid input.").red());
            } catch (IllegalArgumentException e) {
                println(text(e.getMessage()).red());
            } catch (NoSuchElementException e) {
                break;
            }
        }
    }

    public static final Pattern VALID_NUMERALS = Pattern.compile(
            "^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$", Pattern.CASE_INSENSITIVE
    );

    private static String toRoman(int arabic) throws IllegalArgumentException {
        if (arabic < 1 || arabic > 3999) {
            throw new IllegalArgumentException("Invalid input");
        }

        String roman = "";

        int p1 = arabic % 10;
        int p2 = (arabic / 10) % 10;
        int p3 = (arabic / 100) % 10;
        int p4 = (arabic / 1000) % 10;

        roman += "M".repeat(p4);

        if (p3 == 9) {
            roman += "CM";
        } else if (p3 == 4) {
            roman += "CD";
        } else {
            roman += "D".repeat(p3 / 5);
            roman += "C".repeat(p3 % 5);
        }

        if (p2 == 9) {
            roman += "XC";
        } else if (p2 == 4) {
            roman += "XL";
        } else {
            roman += "L".repeat(p2 / 5);
            roman += "X".repeat(p2 % 5);
        }

        if (p1 == 9) {
            roman += "IX";
        } else if (p1 == 4) {
            roman += "IV";
        } else {
            roman += "V".repeat(p1 / 5);
            roman += "I".repeat(p1 % 5);
        }

        return roman;
    }

    private static int fromRoman(String roman) throws IllegalArgumentException {
        if (roman == null || roman.isEmpty()) {
            throw new IllegalArgumentException("Empty string");
        }

        if (!VALID_NUMERALS.matcher(roman).matches()) {
            throw new IllegalArgumentException("Invalid roman numeral");
        }

        var value = 0;
        var lastChar = ' ';

        for (var ch : roman.toCharArray()) {
            switch (ch) {
                case 'I':
                    value += 1;
                    break;
                case 'V':
                    value += lastChar == 'I' ? 3 : 5;
                    break;
                case 'X':
                    value += lastChar == 'I' ? 8 : 10;
                    break;
                case 'L':
                    value += lastChar == 'X' ? 30 : 50;
                    break;
                case 'C':
                    value += lastChar == 'X' ? 80 : 100;
                    break;
                case 'D':
                    value += lastChar == 'C' ? 300 : 500;
                    break;
                case 'M':
                    value += lastChar == 'C' ? 800 : 1000;
                    break;
            }
            lastChar = ch;
        }

        return value;
    }
}
