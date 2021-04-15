package main;

public class ExternalDBStub {
    
    int getValue(String key){
        switch(key){
            case "abc":return 9;
            case "def":return 7;
            case "xyz":return 10;
            default:return 0;
        }
    }
}
