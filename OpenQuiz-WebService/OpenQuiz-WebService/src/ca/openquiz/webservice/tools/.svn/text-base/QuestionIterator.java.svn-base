package ca.openquiz.webservice.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query.Filter;

import ca.openquiz.webservice.enums.QuestionType;
import ca.openquiz.webservice.model.Question;
import ca.openquiz.webservice.parameter.SearchQuestionParam;

public class QuestionIterator implements Iterator<Question> {

	private static final String myPackage = "ca.openquiz.webservice.model.";
	@SuppressWarnings("rawtypes")
	private Class questionClass = null;
	private QuestionType type = QuestionType.General;
	private List<Question> questions;
	private Cursor cursor = null;
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	private int fetchSize = 100;
	private Filter filter = null;
	private int offset = 0;

	public QuestionIterator(SearchQuestionParam param){
		
		try {
			String str = myPackage + "Question" + this.type;
			questionClass = Class.forName(str);
		} catch (ClassNotFoundException e) {
			ExceptionLogger.getLogger().warning(e.toString());
		}

		questions = new ArrayList<Question>();

		if(param.getOffset() >= 0){
			this.offset = param.getOffset();
		}
		
		if(param.getResultsByPage() >= 20){
			fetchSize = param.getResultsByPage();
		}

		// Conditions de la recherche
		List<Filter> andFilters = new ArrayList<Filter>();
		
		if (param.getAuthor() != null) {
			andFilters.add(new FilterPredicate("author", FilterOperator.EQUAL, param.getAuthor()));
		}
		if (param.getCategory() != null) {
			andFilters.add(new FilterPredicate("category", FilterOperator.EQUAL, param.getCategory()));
		}
		if (param.getLanguage() != null) {
			andFilters.add(new FilterPredicate("language", FilterOperator.EQUAL, param.getLanguage().name()));
		}
		if (param.getDegree() != null) {
			andFilters.add(new FilterPredicate("degree", FilterOperator.EQUAL, param.getDegree().name()));
		}
		if (param.getAvailableDate() != null) {
			andFilters.add(new FilterPredicate("availableDate", FilterOperator.LESS_THAN_OR_EQUAL, param.getAvailableDate()));
		}
		andFilters.add(new FilterPredicate("isReported", FilterOperator.EQUAL, param.isReported()));
		
		if(andFilters.size() > 1){
			andFilters.add(CompositeFilterOperator.and(andFilters));
		}
		else if(!andFilters.isEmpty()){
			this.filter = andFilters.get(0);
		}

	}

	private void fetch(){
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(this.fetchSize);
		if(this.cursor != null){
			fetchOptions.startCursor(this.cursor);
		}
		else if(offset >= 0){
			fetchOptions.offset(this.offset);
			this.offset = -1;
		}
		
		Query query = new Query("Question" + type.name());
		query.setFilter(this.filter);

		PreparedQuery pq = datastore.prepare(query);

		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		for(Entity e : results){

			Question q;
			try {
				q = (Question) questionClass.newInstance();
				q.setPropertiesFromEntity(e);
				questions.add(q);
			} catch (Exception e1) {
				ExceptionLogger.getLogger().warning(e1.toString());
			}
		}

		this.cursor = results.getCursor();
	}

	@Override
	public boolean hasNext() {
		if(questions.isEmpty()){
			fetch();
			if(questions.isEmpty() || this.cursor == null){
				return false;
			}
		}

		return true;
	}

	@Override
	public Question next() {
		Question returnValue = questions.get(0);
		questions.remove(0);
		return returnValue;
	}

	@Override
	public void remove() {
		questions.remove(0);
	}

	public Cursor getCursor() {
		return cursor;
	}

}
