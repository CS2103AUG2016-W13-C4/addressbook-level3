package seedu.addressbook.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;
import seedu.addressbook.history.History;
import seedu.addressbook.history.PreviousCommand;

/**
 * Edit a specific detail of a person.
 * Possible edit types: name, phone, email, address
 * The person is identified using its last displayed index from the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" 
            + "Edit properties of a specific person. \n\t"
            + "Parameters: INDEX EDITTYPE/NEWDETAIL \n\t"
            + "Example: " + COMMAND_WORD
            + " 1 p/12345678";

    private static final String PERSON_EDIT_TYPE_NAME = "n";
    private static final String PERSON_EDIT_TYPE_PHONE = "p";
    private static final String PERSON_EDIT_TYPE_EMAIL = "e";
    private static final String PERSON_EDIT_TYPE_ADDRESS = "a";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";

    private static final String MESSAGE_INVALID_PERSON_DETAIL = "New person detail is invalid!";
    
    private final String editType;
    private final String newPersonDetail;
    
    public EditCommand(int targetVisibleIndex, String editType, String newPersonDetail) {
        super(targetVisibleIndex);
        this.editType = editType;
        this.newPersonDetail = newPersonDetail;
    }
    
    @Override
    public CommandResult execute(History history) {
        try {
            List<Person> affectedPersons = new ArrayList<Person>();            
            final ReadOnlyPerson target = getTargetPerson();
            Person targetWithNewDetail = getNewPersonWithEditedDetail(target);
            affectedPersons.add((Person)target);
            affectedPersons.add(targetWithNewDetail);
            history.update(new PreviousCommand(COMMAND_WORD, affectedPersons));
           
            addressBook.removePerson(target);
            addressBook.addPerson(targetWithNewDetail);
            return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, targetWithNewDetail));

        } catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        } catch (PersonNotFoundException pnfe) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        } catch (IllegalValueException ive) {
            return new CommandResult(MESSAGE_INVALID_PERSON_DETAIL);
        }
    }
    
    /**
     * Create a new Person with a detail changed.
     *
     * @param target ReadOnlyPerson
     * @return Person with changed detail
     */
    private Person getNewPersonWithEditedDetail(ReadOnlyPerson target) 
            throws IllegalValueException {
        if (editType.equals(PERSON_EDIT_TYPE_NAME)) {
            Name newName = new Name(newPersonDetail);
            return new Person(newName, target.getPhone(), target.getEmail(),
                              target.getAddress(), target.getTags());
        } else if (editType.equals(PERSON_EDIT_TYPE_PHONE)) {
            Phone newPhone = new Phone(newPersonDetail, target.getPhone().isPrivate());
            return new Person(target.getName(), newPhone, target.getEmail(),
                              target.getAddress(), target.getTags());
        } else if (editType.equals(PERSON_EDIT_TYPE_EMAIL)) {
            Email newEmail = new Email(newPersonDetail, target.getEmail().isPrivate());
            return new Person(target.getName(), target.getPhone(), newEmail,
                              target.getAddress(), target.getTags());
        } else if (editType.equals(PERSON_EDIT_TYPE_ADDRESS)) {
            Address newAddress = new Address(newPersonDetail, target.getAddress().isPrivate());
            return new Person(target.getName(), target.getPhone(), target.getEmail(),
                              newAddress, target.getTags());
        }
        return new Person(target.getName(), target.getPhone(), target.getEmail(),
                target.getAddress(), target.getTags());
    }
    
}
