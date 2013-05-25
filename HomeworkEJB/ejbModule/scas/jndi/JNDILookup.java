package scas.jndi;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JNDILookup {
	 
	private static Context initialContext;
	 
	    private static final String PKG_INTERFACES = "org.jboss.ejb.client.naming";
	 
	    public static Context getInitialContext() throws NamingException {
	        if (initialContext == null) {
	            Properties properties = new Properties();
	            properties.put(Context.URL_PKG_PREFIXES, PKG_INTERFACES);
	            //properties.put("jboss.naming.client.ejb.context", true);
	            /*properties.put(Context.SECURITY_PRINCIPAL, "root");
	            properties.put(Context.SECURITY_CREDENTIALS, "stefan");
	            properties.put("remote.connection.default.username", "root");
	        	properties.put("remote.connection.default.password", "stefan");*/
	            initialContext = new InitialContext(properties);
	        }
	        return initialContext;
	    }
}	
