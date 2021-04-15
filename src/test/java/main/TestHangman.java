
package main;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestHangman {
    static Random random;
    static Hangman hangman;
    int requestedLength;
    
    @BeforeAll
    static void setupClass(){
        random = new Random();
        hangman = new Hangman();
        hangman.loadWords();
    }
    
    @BeforeEach
    void setupTest(){
        requestedLength = random.nextInt(6)+5;
        hangman.score = 0;
    }
    
    @AfterEach
    void tearDownTest(){
        //
    }
    
    @AfterAll
    static void tearDownClass(){
        //
    }
    
    @Test
    void test_alphabetCountInAWord(){
        String word = "pizza";
        char alphabet = 'z';
        int count = hangman.countAlphabet(word,alphabet);
        assertEquals(2, count);
    }
    
    @Test
    void test_lengthOfFetchedWordRandom(){
        String word = hangman.fetchWord(requestedLength);
        assertTrue(requestedLength == word.length());
    }
    
    @Test
    void test_uniquenessOfFetchedWord(){
        Set<String> usedWordsSet = new HashSet<>();
        int round = 0;
        String word = null;
        while(round < 100){
            requestedLength = random.nextInt(6) + 5;
            word = hangman.fetchWord(requestedLength);
            round++;
            assertTrue(usedWordsSet.add(word));
        }
    }
    
    @Test
    void test_fetchClueBeforeAnyGuess(){
        String clue = hangman.fetchClue("pizza");
        assertEquals("-----", clue);
    }
    
    @Test
    void test_fetchClueAfterCorrectGuess(){
        String clue = hangman.fetchClue("pizza");
        String newClue = hangman.fetchClue("pizza",clue,'a');
        assertEquals("----a", newClue);
    }
    
    @Test
    void test_fetchClueAfterIncorrectGuess(){
        String clue = hangman.fetchClue("pizza");
        String newClue = hangman.fetchClue("pizza",clue,'x');
        assertEquals("-----", newClue);
    }
    
    @Test
    void test_whenInvalidGuessThenFetchClueThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> hangman.fetchClue("pizza","-----",'1'));
    }
    
    @Test
    void test_whenInvalidGuessThenFetchClueThrowsExceptionWithMessage(){
        Exception e = assertThrows(IllegalArgumentException.class, () -> hangman.fetchClue("pizza","-----",'1'));
        assertEquals("Invalid character.", e.getMessage());
    }
    
    @Test
    void test_remainingTrialsBeforeAnyGuess(){
        hangman.fetchWord(requestedLength);
        assertEquals(Hangman.MAX_TRIALS, hangman.remainingTrials);
    }
    
    @Test
    void test_remainingTrialsAfterOneGuess(){
        hangman.fetchWord(requestedLength);
        hangman.fetchClue("pizza","-----",'a');
        assertEquals(Hangman.MAX_TRIALS - 1, hangman.remainingTrials);
    }
    
    @Test
    void test_scoreBeforeAnyGuess(){
        hangman.fetchWord(requestedLength);
        assertEquals(0,hangman.score);
    }
    
    @Test
    void test_scoreAfterCorrectGuess(){
        String word = "pizza";
        String clue = "-----";
        char guess = 'a';
        hangman.fetchClue(word, clue, guess);
        assertEquals((double)Hangman.MAX_TRIALS / word.length(),hangman.score);
    }
    
    @Test
    void test_scoreAfterIncorrectGuess(){
        String word = "pizza";
        String clue = "-----";
        char guess = 'x';
        hangman.fetchClue(word, clue, guess);
        assertEquals(0,hangman.score);
    }
    
    @Test
    void test_saveScoreDummy(){
        assertTrue(hangman.saveScoreDummy(new WordScore("apple", 10)));
    }
    
    @Test
    void test_saveScoreStub(){
        assertTrue(hangman.saveScoreStub(new WordScore("apple", 10)));
    }
    
    @Test
    void test_writeScoreDBFake(){
        WordScore wordScore = new WordScore("apple", 10);
        hangman.saveScoreFake(wordScore);
        assertTrue(hangman.scoreDbFake.readScoreDB("apple") == 10);
    }
    
    @Test
    void test_saveScoreFake(){
        assertTrue(hangman.saveScoreFake(new WordScore("apple", 10)));
    }
    
    @Test
    void test_readScoreFake(){
        hangman.saveScoreFake(new WordScore("apple", 10));
        double score = hangman.readScoreFake("apple");
        assertEquals(10,(double)score);
    }
    
    @Test
    void test_writeScoreDbSpyCalled(){
        hangman.scoreDbSpy.writeScoreDbCalled = false;
        WordScore wordScore = new WordScore("apple", 10);
        hangman.saveScoreSpy(wordScore);
        assertTrue(hangman.scoreDbSpy.writeScoreDbCalled);
    }
    
    @Test
    void test_saveScoreUsingMockDb(){
        MockScoreDB mockScoreDB = mock(MockScoreDB.class);
        Hangman hangman = new Hangman(mockScoreDB);
        when(mockScoreDB.writeScoreDB("apple", 10)).thenReturn(true);
        
        assertTrue(hangman.saveWordScore("apple",10));
    }
}
