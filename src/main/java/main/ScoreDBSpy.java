package main;

//Spy class.
//Records output for a component outside the object under test.
public class ScoreDBSpy {
    boolean writeScoreDbCalled;
    
    public boolean writeScoreDB(WordScore wordScore){
        writeScoreDbCalled = true;
        return true;
    }
}
