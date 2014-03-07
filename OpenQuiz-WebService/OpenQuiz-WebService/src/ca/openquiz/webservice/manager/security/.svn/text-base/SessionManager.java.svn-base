package ca.openquiz.webservice.manager.security;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Key;

import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.Session;

public class SessionManager {

	private static int maxSessionQtyByUser = 5;

	public static Session getLatestSession(Key userId) {

		Session latestSession = null;
		List<Session> sessionsList = DBManager.searchSessions(userId);

		if (!sessionsList.isEmpty()){
			//clean old sessions
			for(Session s : sessionsList) {
				if (latestSession == null) {
					latestSession = s;
				}
				else if(latestSession.getCreateTime().before(s.getCreateTime())) {
					latestSession = s;
				}
			}
		}

		return latestSession;
	}

	public static Session createNewSession(Key userId){
		Session currentSession = new Session(userId);
		DBManager.save(currentSession);
		cleanUserSessions(userId);
		return getLatestSession(userId);
	}

	public static void cleanUserSessions(Key userId){
		List<Session> sessionsList = DBManager.searchSessions(userId);

		if (!sessionsList.isEmpty()){
			//clean old sessions
			for(Session s : sessionsList) {
				if (!s.isActive()) {
					s.delete();
				}
			}
		}

		sessionsList = DBManager.searchSessions(userId);

		if(sessionsList.size() >= maxSessionQtyByUser) {
			Collections.sort(sessionsList, new Comparator<Session>() {
				public int compare(Session s1, Session s2) {
					return s2.getCreateTime().compareTo(s1.getCreateTime());
				}
			});
			
			for(int i = 0; i < sessionsList.size(); i++){
				if(i >= maxSessionQtyByUser)
				sessionsList.get(i).delete();;
			}
		}
	}
	
	public static void extendSessionEndTime(Session s){
		//extend session endtime if required
		Date now = new Date();
		if(s != null && s.isExpired()) {
			s.setEndTime(new Date(now.getTime() + 60*60*1000));
			DBManager.save(s);
		}
	}

	public static void closeUserSession(Key userKey, String sessionKey) {
		List<Session> sessionsList = DBManager.searchSessions(userKey);
		Session session = DBManager.getSession(sessionKey);
		if (!sessionsList.isEmpty() && session != null) {
			//clean all sessions
			for(Session s : sessionsList) {
				if(s.getKey().equals(session.getKey())) {
					session.delete();
				}
			}
		}
	}
}
