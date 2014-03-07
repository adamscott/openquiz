package ca.openquiz.comms.enums;

import java.util.ArrayList;
import java.util.List;

public enum CategoryType {
	Language,
	History,
	Math,
	Art_Music,
	Science,
	Geography,
	HumanSciences,
	Entertainment,
	Sport,
	Literature,
	Other,
	CorrectWord,
	Unknown,
	BeforeAfter;
	
	public static CategoryType findByName(String name){
	    for(CategoryType d : values()){
	        if( d.name().equals(name)){
	            return d;
	        }
	    }
	    return null;
	}
	
	public static String[] valuesName(){
		String[] valuesName = new String[13]; 
		int i = 0;
		for(CategoryType d : values()){
			valuesName[i++] = toString(d);
		}
		return valuesName;
	}
	
	public static CategoryType findByStringName(String type)
	{
		CategoryType value;
		
		if(type.equals("Langues"))
			value = CategoryType.Language;
		
		else if(type.equals("Histoire"))
			value = CategoryType.History;
		
		else if(type.equals("Mathématiques"))
			value = CategoryType.Math;
		
		else if(type.equals("Sciences"))
			value = CategoryType.Science;
		
		else if(type.equals("Arts & Musique"))
			value = CategoryType.Art_Music;
		
		else if(type.equals("Géographie"))
			value = CategoryType.Geography;
		
		else if(type.equals("Sciences Humaines"))
			value = CategoryType.HumanSciences;
		
		else if(type.equals("Sports"))
			value = CategoryType.Sport;
		
		else if(type.equals("Littérature"))
			value = CategoryType.Literature;
		
		else if(type.equals("Divertissement"))
			value = CategoryType.Entertainment;
		
		else if(type.equals("Autre"))
			value = CategoryType.Other;
		
		else if(type.equals("Mot Juste"))
			value = CategoryType.CorrectWord;
		
		else if(type.equals("Inconnu"))
			value = CategoryType.Unknown;
		
		else
			value = CategoryType.Other;
		
		return value;
	}
	
	public static String toString(CategoryType type)
	{
		String value;
		switch (type) {
		case Language:
			value = "Langues";
			break;
		case History:
			value = "Histoire";
			break;
		case Math:
			value = "Mathématiques";
			break;
		case Art_Music:
			value = "Arts & Musique";
			break;
		case Science:
			value = "Sciences";
			break;
		case Geography:
			value = "Géographie";
			break;
		case HumanSciences:
			value = "Sciences Humaines";
			break;
		case Entertainment:
			value = "Divertissement";
			break;
		case Sport:
			value = "Sports";
			break;
		case Literature:
			value = "Littérature";
			break;
		case Other:
			value = "Autre";
			break;
		case CorrectWord:
			value = "Mot Juste";
			break;
		case Unknown:
			value = "Inconnu";
			break;
		default:
			value = "";
			break;
		}
		
		return value;
	}
}
