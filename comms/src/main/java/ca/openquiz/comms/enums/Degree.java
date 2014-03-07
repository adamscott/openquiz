package ca.openquiz.comms.enums;

import java.util.ArrayList;
import java.util.List;

public enum Degree {
	very_easy,
	easy,
	normal,
	hard,
	very_hard;
	
	public static Degree findByName(String name){
	    for(Degree d : values()){
	        if( d.name().equals(name)){
	            return d;
	        }
	    }
	    return null;
	}
	
	public static String[] valuesName(){
		String[] valuesName = new String[5]; 
		int i = 0;
		for(Degree d : values()){
			valuesName[i++] = toString(d);
		}
		return valuesName;
	}
	
	public static Degree findByStringName(String deg)
	{
		Degree value;
		
		if(deg.equals("Très Facile"))
			value = Degree.very_easy;
		
		else if(deg.equals("Facile"))
			value = Degree.easy;
		
		else if(deg.equals("Normal"))
			value = Degree.normal;
		
		else if(deg.equals("Difficile"))
			value = Degree.hard;
		
		else if(deg.equals("Très Difficile"))
			value = Degree.very_hard;
		
		else
			value = null;
		
		return value;
	}
	
	public static String toString(Degree degree)
	{
		String value;
		switch (degree) {
		case very_easy:
			value = "Très Facile";
			break;
		case easy:
			value = "Facile";
			break;
		case normal:
			value = "Normal";
			break;
		case hard:
			value = "Difficile";
			break;
		case very_hard:
			value = "Très Difficile";
			break;
		default:
			value = "";
			break;
		}
		
		return value;
	}
}
