package openquiz.website.util; 

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class Misc implements Serializable{
	private static final long serialVersionUID = -7947988418576420451L;

	public static <T> Collection<T> nullSafe(Collection<T> c) {
        return (c == null) ? Collections.<T>emptyList() : c;
    }
	
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public static String formatDate(Date date){
		return dateFormat.format(date);
	}
}