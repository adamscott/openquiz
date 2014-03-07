package ca.openquiz.comms.enums;

public enum QuestionType {
	Anagram,
	Association,
	General,
	Identification,
	Intru,
	Media;
	
	public static QuestionType findByName(String name){
	    for(QuestionType t : values()){
	        if( t.name().equals(name)){
	            return t;
	        }
	    }
	    return null;
	}
}
