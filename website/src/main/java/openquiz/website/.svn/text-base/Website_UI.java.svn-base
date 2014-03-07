package openquiz.website;

import java.io.Serializable;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.vaadin.navigator.Navigator;
import org.vaadin.navigator.Navigator.NavigableApplication;

import openquiz.website.util.Messages;
import openquiz.website.windows.MainWebSiteWindow;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.HttpServletRequestListener;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.*;

public class Website_UI extends Application implements Serializable , HttpServletRequestListener, NavigableApplication {
	private static final long serialVersionUID = 6057250810716363098L;
	
	private Locale currentCulture;
	private transient HttpServletResponse response;
	private transient HttpServletRequest request;

	public Website_UI(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	@Override
    public void init() {
		// Get the locale value from cookies
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("locale")) {
					currentCulture = new Locale(cookies[i].getValue());
				}
			}
		}
		if (currentCulture == null)
			currentCulture = Messages.DEFAULT_LOCALE;
		setCurrentLocale(currentCulture);
		
		setMainWindow(createNewWindow());
    }
	
	@Override
    public Window getWindow(String name) {
        // Use navigator to manage multiple browser windows
        return Navigator.getWindow(this, name, super.getWindow(name));
    }

	public Window createNewWindow() {
        return new MainWebSiteWindow();
	}
	
	public HttpSession getSession() {
		WebApplicationContext ctx = ((WebApplicationContext) getContext());
		return ctx.getHttpSession();
	}

	@Override
	public void onRequestStart(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public void onRequestEnd(HttpServletRequest request,
			HttpServletResponse response) {
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	public void setCurrentLocale(Locale locale) {
		
		setLocale(locale);
		Cookie[] cookies = request.getCookies();
		Cookie cookie = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("locale")) {
					cookie = cookies[i];
				}
			}
			if (cookie != null) {
				cookie.setValue(locale.toString());
				response.addCookie(cookie);
			} else {
				cookie = new Cookie("locale", locale.toString());
				response.addCookie(cookie);
			}
		}
		Messages.setLanguage(locale);
	}
}