// Copyright 2008 Google Inc. All Rights Reserved.
package ca.openquiz.webservice.manager;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;
 
public final class PMF {
    private static final PersistenceManagerFactory pmfInstance =
        JDOHelper.getPersistenceManagerFactory("transactions-optional");
 
    private PMF() {}
 
    public static PersistenceManagerFactory get() {
        return pmfInstance;
    }
}