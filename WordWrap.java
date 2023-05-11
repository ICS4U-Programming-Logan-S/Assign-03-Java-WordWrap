import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
* This program takes input from a file and makes the text go to the next line
* after it has reached the index number of the integer above the input string.
*
* @author Logan S
* @version 1.0
* @since 2023-05-07
*/

public final class WordWrap {

    /**
    * For style checker.
    *
    * @exception IllegalStateException Utility class.
    * @see IllegalStateException
    */

    private WordWrap() {
        throw new IllegalStateException("Utility class");
    }

    /**
    * This is the wordWrap method.
    *
    * @param someString the string in the file.
    * @param numChar the character limit per line.
    * @return the wrapped string.
    */

    // A function that returns a wrapped version of the given string
    public static String wordWrap(String someString, int numChar) {
        final ArrayList<String> lines = new ArrayList<>();

        if (someString.length() == 0) {
            // Base case: empty string, return empty string
            return "";
        } else if (someString.length() <= numChar) {
            // Base case: string length <= numChar, add the string as a line
            lines.add(someString + "\n");
        } else if (!someString.substring(0, numChar).contains(" ")) {
            // No whitespace within numChar range, split at the numChar index
            final int currentLineLength = numChar;
            lines.add(someString.substring(0, currentLineLength) + "\n");

            final String restOfString;
            restOfString = someString.substring(currentLineLength).trim();

            if (!restOfString.isEmpty()) {
                // Recursive call on the remaining string
                lines.add(wordWrap(restOfString, numChar));
            }
        } else {
            /* Whitespace within numChar range, split at the last space
            character index */
            final int lastSpaceIndex = someString.substring(0,
                    numChar + 1).lastIndexOf(" ");
            lines.add(someString.substring(0, lastSpaceIndex) + "\n");

            final String restOfString = someString.substring(lastSpaceIndex
                    + 1).trim();
            if (!restOfString.isEmpty()) {
                // Recursive call on the remaining string
                lines.add(wordWrap(restOfString, numChar));
            }
        }

        // Concatenate the lines into a single string
        String wrappedString = "";
        for (String line : lines) {
            wrappedString += line;
        }
        return wrappedString;
    }

    /**
    * This is the main method.
    *
    * @param args Unused.
    */

    // Main function to test the wordWrap function
    public static void main(String[] args) {
        try {
            // Read the input file
            final File inputFile = new File("WordWrapInput3.txt");
            final Scanner scanner = new Scanner(inputFile);
            final File outputFile = new File("WordWrapOutput.txt");
            final FileWriter writer = new FileWriter(outputFile);

            while (scanner.hasNextLine()) {
                final int numOfChar = scanner.nextInt();
                scanner.nextLine();
                final String inputString = scanner.nextLine();

                // Call the wordWrap function on the input string
                final String wrappedString = wordWrap(inputString, numOfChar);

                // Write the output file
                try {
                    writer.write(wrappedString);
                } catch (IOException ex) {
                    System.out.println("Error: " + ex.getMessage());
                }
            }
            scanner.close();
            writer.close();
        } catch (IOException exception) {
            System.out.println("An error occurred.");
        }
    }
}
