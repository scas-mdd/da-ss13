package scas.client;

import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
 
import scas.ejb.EmployeeEJBRemote;
import scas.ejb.EmployeeEJB;
import scas.jndi.JNDILookup;
import scas.domain.Employee;
 
public class EJBApplicationClient {
	private EmployeeEJBRemote bean;
	
	public static void main(String [] args){
		EmployeeEJBRemote employeeBean = doLookup();
		Employee emp = employeeBean.createEmployee("Vicenzio", "Allegrini", "26.09.1980", 3200);
		//employeeBean.saveEmployee(emp);
		List<Employee> list = employeeBean.getEmployeesList();
		for(Employee e : list)
			System.out.println(e.toString());
		//employeeBean.deleteEmployee((Employee) list.get(2));
	}
	
    public EJBApplicationClient(){
    	 bean = doLookup();
    }
    public EmployeeEJBRemote getBean(){
    	return bean;
    }
    private static EmployeeEJBRemote doLookup() {
        Context context = null;
        EmployeeEJBRemote bean = null;
        try {
            // 1. Obtaining Context
            context = JNDILookup.getInitialContext();
            // 2. Generate JNDI Lookup name
            String lookupName = getLookupName();
            // 3. Lookup and cast
            bean = (EmployeeEJBRemote) context.lookup(lookupName);
 
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return bean;
    }
 
    private static String getLookupName() {
        /*The app name is the EAR name of the deployed EJB without .ear
        suffix. Since we haven't deployed the application as a .ear, the app
        name for us will be an empty string */
        String appName = "";
 
        /* The module name is the JAR name of the deployed EJB without the
        .jar suffix.*/
        String moduleName = "HomeworkEJB";
 
        /* AS7 allows each deployment to have an (optional) distinct name.
        This can be an empty string if distinct name is not specified.*/
        String distinctName = "";
 
        // The EJB bean implementation class name
        String beanName = EmployeeEJB.class.getSimpleName();
 
        // Fully qualified remote interface name
        final String interfaceName = EmployeeEJBRemote.class.getName();
 
        // Create a look up string name
        String name = "ejb:" + appName + "/" + moduleName + "/" +
                distinctName    + "/" + beanName + "!" + interfaceName;
        System.out.println(name);
        return name;
    }
}
