import java.util.Scanner;

/**
 * The {@code ConsoleIO} class provides a simple interface for handling
 * console-based input and output operations.
 * <p>
 * It wraps a {@link Scanner} object to read user input and provides utility
 * methods for displaying prompts and retrieving user responses as strings or
 * integers.
 * </p>
 */
public class ConsoleIO
{

    private final Scanner INPUT;


    /**
     * Default constructor that initializes the scanner to read from standard
     * input ({@code System.in}).
     */
    public ConsoleIO()
    {
        INPUT = new Scanner(System.in);
    }

    /**
     * Constructs a {@code ConsoleIO} object using a provided {@link Scanner}
     * instance.
     *
     * @param console the scanner to use for input
     */
    public ConsoleIO(Scanner console)
    {
        INPUT = console;
    }

    /**
     * Closes the underlying scanner.
     * <p>
     * Once closed, no further input can be read using this object.
     * </p>
     */
    public void close()
    {
        INPUT.close();
    }

    /**
     * Displays a message to the console.
     *
     * @param prompt the message to display
     */
    public void display(String prompt)
    {
        System.out.println(prompt);
    }

    /**
     * Prompts the user for input and attempts to parse the response as an
     * integer.
     *
     * @param prompt the message displayed to the user
     * @return the integer value entered by the user
     * @throws NumberFormatException if the input cannot be parsed as an
     *                               integer
     */
    public int getIntInput(String prompt) throws NumberFormatException
    {
        return Integer.parseInt((getStringInput(prompt)));
    }

    /**
     * Prompts the user for input and returns the entered value as a string.
     *
     * @param prompt the message displayed to the user
     * @return the user's input as a string
     */
    public String getStringInput(String prompt)
    {
        System.out.println(prompt);
        return INPUT.nextLine();
    }

    /**
     * Prints a blank line to the console.
     * <p>
     * This method is used to improve readability of console output by inserting
     * spacing between sections of text.
     * </p>
     */
    public void lineBreak()
    {
        System.out.println();
    }
}
