package ca.openquiz.comms.enums;

public enum QuestionTarget {
	Collectif,
	Individual;
	
	public static QuestionTarget findByName(String name){
	    for(QuestionTarget t : values()){
	        if( t.name().equals(name)){
	            return t;
	        }
	    }
	    return null;
	}
}