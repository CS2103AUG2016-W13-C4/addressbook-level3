package seedu.addressbook.commands;

import seedu.addressbook.history.History;

/**
 * Represents an incorrect command. Upon execution, produces some feedback to the user.
 */
public class IncorrectCommand extends Command{

    public final String feedbackToUser;

    public IncorrectCommand(String feedbackToUser){
        this.feedbackToUser = feedbackToUser;
    }

    @Override
    public CommandResult execute(History history) {
        return new CommandResult(feedbackToUser);
    }

}
