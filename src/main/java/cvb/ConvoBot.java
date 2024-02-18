package cvb;

import cvb.commands.Command;
import cvb.commands.CommandParser;
import cvb.exceptions.ConvoBotException;
import cvb.exceptions.ExitException;
import cvb.gui.Main;
import cvb.utils.ResponseConstructor;
import cvb.utils.Storage;
import cvb.utils.TaskList;
import javafx.application.Application;

/**
 * The main class representing the ConvoBot application.
 */
public class ConvoBot {

    private final TaskList tasks;
    private final ResponseConstructor rc = new ResponseConstructor();

    /**
     * Constructor for the ConvoBot class.
     *
     * @param filePath The file path for task data storage.
     */
    public ConvoBot(String filePath) {
        tasks = new TaskList(new Storage(filePath));
    }

    /**
     * Retrieves a response from ConvoBot based on the provided input.
     *
     * @param userInput The input string representing the user's message.
     * @return A string containing ConvoBot's response.
     * @throws ExitException If the user enters an exit command, this exception is thrown
     *                       to indicate that the application should exit.
     */
    public String getResponse(String userInput) throws ExitException {
        Command c;
        try {
            c = CommandParser.parse(userInput);
            if (c.isExit()) {
                throw new ExitException();
            }
        } catch (ConvoBotException e) {
            rc.showError(e.getMessage());
            return rc.getResponse();
        }

        assert c != null;

        try {
            c.execute(tasks, rc);
        } catch (ConvoBotException e) {
            rc.showError(e.getMessage());
        }
        return rc.getResponse();
    }

    /**
     * Main entry point for the ConvoBot application.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
