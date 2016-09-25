package seedu.addressbook.history;

import java.util.Stack;

public class History {

    private Stack<PreviousCommand> previousCommands;
    
    public History(){
        previousCommands = new Stack<PreviousCommand>();
    }
    
    public void update(PreviousCommand prevCmd){
        previousCommands.push(prevCmd);
    }
    
    public boolean isEarliest(){
        return previousCommands.isEmpty();
    }
    
    public PreviousCommand revertStep(){
        return previousCommands.pop();
    }
}
