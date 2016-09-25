package seedu.addressbook.commands;

import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList.DuplicatePersonException;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;
import seedu.addressbook.history.History;
import seedu.addressbook.history.PreviousCommand;

public class UndoCommand extends Command {
    
    public static final String COMMAND_WORD = "undo";

    @Override
    public CommandResult execute(History history) {
        if (history.isEarliest()) {
            return new CommandResult("Nothing to undo.");
        } else {
            PreviousCommand prevCmd = history.revertStep();
            switch (prevCmd.getCommandName()) {
            case "add":
                for (Person person : prevCmd.getPersonsAffected()) {
                    try {
                        addressBook.removePerson(person);
                    } catch (PersonNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                return new CommandResult("Undid last command:\n\tadd " + prevCmd.getPersonsAffected().get(0).toString());
                
            case "delete":
                for (Person person : prevCmd.getPersonsAffected()) {
                    try {
                        addressBook.addPerson(person);
                    } catch (DuplicatePersonException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }           
                return new CommandResult("Undid last command:\n\tdelete " + prevCmd.getPersonsAffected().get(0).toString());
                
            case "clear":
                for (Person person : prevCmd.getPersonsAffected()){
                    try {
                        addressBook.addPerson(person);
                    } catch (DuplicatePersonException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                return new CommandResult("Undid last command:\n\tclear");
                
            case "edit":
                try {
                    addressBook.removePerson(prevCmd.getPersonsAffected().get(1));
                    addressBook.addPerson(prevCmd.getPersonsAffected().get(0));
                } catch (PersonNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (DuplicatePersonException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            return new CommandResult("Undid last command:\n\tedit\n\t\t" + prevCmd.getPersonsAffected().get(1).toString() + "\n\treverted back to\n\t\t" + prevCmd.getPersonsAffected().get(0));
                    

            default:
                return new CommandResult("Nothing to undo");
            }
            
            
        }
    }

}
