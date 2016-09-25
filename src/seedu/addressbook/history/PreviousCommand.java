package seedu.addressbook.history;

import java.util.List;

import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.ReadOnlyPerson;

public class PreviousCommand {

    private String commandName;
    
    private List<Person> personsAffected;
    
    public String getCommandName(){
        return commandName;
    }
    
    public List<Person> getPersonsAffected(){
        return personsAffected;
    }
    
    public PreviousCommand(String commandName, List<Person> affectedPersons){
        this.commandName = commandName;
        this.personsAffected = affectedPersons;
    }
    
}
