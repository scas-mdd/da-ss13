package scas.domain.persistence;

import java.util.List;
/**
 * Services for the Employee JavaBean 
 * @author Stefan
 * Cretated 15 May 2013
 * Last update 15 May 2013
 * v1.0 
 */

import scas.domain.employee.Employee;

public interface EmployeeService {

	public Employee createEmployee(long id, String firstName, String lastName, String dob, int salary);
	
	public List<Employee> getEmployeesList();
	
	public Employee getEmployeeById(long id);
	
	public void storeEmployee(Employee employee);
	
	
}
