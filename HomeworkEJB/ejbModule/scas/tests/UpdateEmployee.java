package scas.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import scas.client.EJBApplicationClient;
import scas.domain.Employee;
import scas.ejb.EmployeeEJBRemote;

public class UpdateEmployee {

	EJBApplicationClient client = new EJBApplicationClient();
	EmployeeEJBRemote bean = client.getBean();
	Employee employee;
	
	@Before 
	public void before(){
		employee = bean.getEmployeeById(1);
	}
	@Test
	public void testTrue() {
		employee.setFirstName("Test");
		bean.saveEmployee(employee);
		assertEquals("Test", bean.getEmployeeById(1).getFirstName());
		
	}

}
