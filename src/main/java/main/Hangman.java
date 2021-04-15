
package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

class Hangman {
    
    public static final int MAX_TRIALS = 10;
    public Set<String> usedWordsSet = new HashSet<>();
    public List<String> wordsList = new ArrayList<>();
    public int remainingTrials;
    public int score;
    public ScoreDBStub scoreDbStub = new ScoreDBStub();
    public ScoreDBFake scoreDbFake = new ScoreDBFake();
    public ScoreDBSpy scoreDbSpy = new ScoreDBSpy();
    public MockScoreDB mockScoreDB;
    
    Hangman(){
        
    }

    Hangman(MockScoreDB mockScoreDB) {
        this.mockScoreDB = mockScoreDB;
    }
    
    int countAlphabet(String word, char alphabet) {
        int result = 0;
        for(char c:word.toCharArray()){
            if(c == alphabet) result++;
        }
        return result;
    }

    String fetchWord(int requestedLength) {
        remainingTrials = MAX_TRIALS;
        for(String result:wordsList){
            if(result.length() != requestedLength) {}
            else if(usedWordsSet.add(result)) return result;
        }
        return null;
    }
    
    void loadWords(){
        String nextWord = null;
        try(BufferedReader br = new BufferedReader(new FileReader("WordSource.txt"))){
            while((nextWord = br.readLine()) != null){
                wordsList.add(nextWord);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Hangman.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Hangman.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String fetchClue(String word) {
        StringBuilder clue = new StringBuilder();
        for(int i=0;i<word.length();i++){
            clue.append("-");
        }
        return clue.toString();
    }

    String fetchClue(String word, String clue, char guess) {
        remainingTrials--;
        if(guess >= 'A' && guess <= 'Z' ) guess += 32;
        if(guess < 'a' || guess > 'z') throw new IllegalArgumentException("Invalid character.");
        StringBuilder newClue = new StringBuilder();
        for(int i=0;i<word.length();i++){
            if(guess == word.charAt(i) && guess != clue.charAt(i)){
                newClue.append(guess);
                score += (double) MAX_TRIALS / word.length();
            }else{
                newClue.append(clue.charAt(i));
            }
        }
        return newClue.toString();
    }
    
    //Test Doubles
    
    //Dummy object
    boolean saveScoreDummy(WordScore wordScore){
        
        return true;
    }
    
    //Stub
    boolean saveScoreStub(WordScore wordScore){
        return scoreDbStub.writeScoreDB(wordScore);
    }
    
    //Fake object
    boolean saveScoreFake(WordScore wordScore){
        return scoreDbFake.writeScoreDB(wordScore);
    }
    
    double readScoreFake(String word){
        return scoreDbFake.readScoreDB(word);
    }
    
    boolean saveScoreSpy(WordScore wordScore){
        return scoreDbSpy.writeScoreDB(wordScore);
    }

    boolean saveWordScore(String word, double score) {
        return mockScoreDB.writeScoreDB(word, score);
    }
}
