package seedu.addressbook.history;

import java.util.Stack;

public class History {

    private Stack<PreviousCommand> previousCommands;
    private Stack<PreviousCommand> redoCommands;
    
    public History(){
        previousCommands = new Stack<PreviousCommand>();
        redoCommands = new Stack<PreviousCommand>();
    }
    
    public void update(PreviousCommand prevCmd){
        previousCommands.push(prevCmd);
    }
    
    public boolean isEarliest(){
        return previousCommands.isEmpty();
    }
    
    public boolean isLatest(){
        return redoCommands.isEmpty();
    }
    
    public PreviousCommand revertStep(){
        return redoCommands.push(previousCommands.pop());
    }
    
    public PreviousCommand redoStep(){
        return previousCommands.push(redoCommands.pop());
    }
    
    public void resetRedo(){
       redoCommands = new Stack<PreviousCommand>();
    }
}
