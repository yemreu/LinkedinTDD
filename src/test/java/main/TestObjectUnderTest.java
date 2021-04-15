package main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestObjectUnderTest {
    
    @Test
    void test(){
        ObjectUnderTest out = new ObjectUnderTest();
        assertEquals(26,out.totalValues());
    }
    
    @Test
    void testMock(){
        ExternalDBMock mockObject = mock(ExternalDBMock.class);
        ObjectUnderTestMock objectUnderTest = new ObjectUnderTestMock(mockObject);
        
        when(mockObject.getValue("abc")).thenReturn(9);
        when(mockObject.getValue("def")).thenReturn(7);
        when(mockObject.getValue("xyz")).thenReturn(10);
        
        assertEquals(26,objectUnderTest.getTotalValues());
    }
}
