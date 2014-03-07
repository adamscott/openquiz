package openquiz.website.util;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import openquiz.website.Website_UI;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.GAEApplicationServlet;

public class WebsiteServlet extends GAEApplicationServlet implements Serializable{
	private static final long serialVersionUID = 2243541609997529913L;

	transient HttpServletRequest request;
	transient HttpServletResponse response;
	transient Website_UI application;
	
	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException {
		this.request = request;
		
		this.response = response;
		if(application != null ){
			application.setServletRequest(request);
			application.setServletResponse(response);
		}
		super.service(request, response);
	};

	@Override
	protected Class<? extends Application> getApplicationClass() {
		return Website_UI.class;
	}

	@Override
	protected Application getNewApplication(HttpServletRequest request)
			throws ServletException {
		application = new Website_UI(this.request, this.response);
		return application;
	}
}
