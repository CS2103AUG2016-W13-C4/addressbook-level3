package seedu.addressbook.commands;

import seedu.addressbook.data.person.*;
import seedu.addressbook.history.History;

import java.util.List;

/**
 * Sorts the address book in alphabetical order and lists it.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" 
            + "Sorts the address book in alphabetical order and lists it.";

    @Override
    public CommandResult execute(History history) {
        UniquePersonList allPersons = addressBook.getAllPersonsOriginal();
        allPersons.sort();
        List<ReadOnlyPerson> allPersonsSorted = allPersons.immutableListView();
        return new CommandResult(getMessageForPersonListShownSummary(allPersonsSorted), allPersonsSorted);
    }
    
}
