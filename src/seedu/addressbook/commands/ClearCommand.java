package seedu.addressbook.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.history.History;
import seedu.addressbook.history.PreviousCommand;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Clears address book permanently.\n\t"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";

    public ClearCommand() {}


    @Override
    public CommandResult execute(History history) {
        List<Person> affectedPersons = new ArrayList<Person>();
        for (ReadOnlyPerson person : addressBook.getAllPersons().immutableListView()){
            affectedPersons.add((Person) person);
        }
        history.update(new PreviousCommand(COMMAND_WORD, affectedPersons));
        addressBook.clear();       
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
