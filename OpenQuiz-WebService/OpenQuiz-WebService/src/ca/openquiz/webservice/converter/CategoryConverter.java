package ca.openquiz.webservice.converter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.Category;

import com.google.appengine.api.datastore.Key;

public class CategoryConverter extends XmlAdapter<Category, Key> {

	@Override
	public Category marshal(Key val) throws Exception {
		return DBManager.getCategory(val);
	}

	@Override
	public Key unmarshal(Category val) throws Exception {
		return val.getKey();
	}
	

}
