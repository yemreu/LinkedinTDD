
package main;

//Proxy for the real db.
//Feeds desired input to the tests. Doesn't reflect real behavior.
public class ScoreDBStub {
    
    public boolean writeScoreDB(WordScore wordScore){
        return true;
    }
}
