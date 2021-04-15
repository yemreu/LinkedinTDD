package main;

public class ObjectUnderTest {
    
    ExternalDBStub externalDBStub = new ExternalDBStub();
    
    int totalValues(){
        return externalDBStub.getValue("abc") + externalDBStub.getValue("def") + externalDBStub.getValue("xyz");
    }
}
