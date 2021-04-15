package main;

import java.util.HashMap;
import java.util.Map;

//Fake object class.
//Has an implementation but is not production ready.
public class ScoreDBFake {
    Map<String,Double> wordScoreMap = new HashMap<>();
    
    public boolean writeScoreDB(WordScore wordScore){
        wordScoreMap.put(wordScore.word, wordScore.score);
        return true;
    }
    
    public double readScoreDB(String word){
        return wordScoreMap.get(word);
    }
}
