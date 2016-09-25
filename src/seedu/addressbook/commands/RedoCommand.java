package seedu.addressbook.commands;

import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.UniquePersonList.DuplicatePersonException;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;
import seedu.addressbook.history.History;
import seedu.addressbook.history.PreviousCommand;

public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Redo the last command that was undone from undo.\n\t" + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(History history) {
        if (history.isLatest()) {
            return new CommandResult("Nothing to redo.");
        } else {
            PreviousCommand prevCmd = history.redoStep();
            switch (prevCmd.getCommandName()) {
            case "delete":
                for (Person person : prevCmd.getPersonsAffected()) {
                    try {
                        addressBook.removePerson(person);
                    } catch (PersonNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                return new CommandResult(
                        "Redid last undo command:\n\tdelete " + prevCmd.getPersonsAffected().get(0).toString());

            case "add":
                for (Person person : prevCmd.getPersonsAffected()) {
                    try {
                        addressBook.addPerson(person);
                    } catch (DuplicatePersonException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                return new CommandResult(
                        "Redid last undo command:\n\tadd " + prevCmd.getPersonsAffected().get(0).toString());

            case "clear":
                for (Person person : prevCmd.getPersonsAffected()) {
                    addressBook.clear();
                }
                return new CommandResult("Redid last command:\n\tclear");

            case "edit":
                try {
                    addressBook.removePerson(prevCmd.getPersonsAffected().get(0));
                    addressBook.addPerson(prevCmd.getPersonsAffected().get(1));
                } catch (PersonNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (DuplicatePersonException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return new CommandResult(
                        "Redid last command:\n\tedit\n\t\t" + prevCmd.getPersonsAffected().get(0).toString()
                                + "\n\treverted back to\n\t\t" + prevCmd.getPersonsAffected().get(1));

            default:
                return new CommandResult("Nothing to undo");
            }

        }
    }

}
