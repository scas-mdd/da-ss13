package scas.ejb;

import java.util.List;

import javax.ejb.Remote;

import scas.domain.Employee;


@Remote
public interface EmployeeEJBRemote {

	public Employee createEmployee(String firstName, String lastName, String dob, int salary);
	
	public List<Employee> getEmployeesList();
	
	public Employee getEmployeeById(int id);
	
	public void saveEmployee(Employee employee);
	
	public void updateEmployee(Employee employee);
	
	public void deleteEmployee(Employee employee);
}
