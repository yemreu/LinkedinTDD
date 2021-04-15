package main;

public class ObjectUnderTestMock {
    ExternalDBMock mockObject;
    
    public ObjectUnderTestMock(ExternalDBMock externalDBMock){
        this.mockObject = externalDBMock;
    }
    
    public int getTotalValues(){
        return mockObject.getValue("abc") + mockObject.getValue("def") + mockObject.getValue("xyz");
    }
}
