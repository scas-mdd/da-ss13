package scas.domain.persistence;

import java.util.ArrayList;
import java.util.List;

import scas.domain.employee.Employee;


public class EmployeeServiceImpl implements EmployeeService{
	
	List<Employee> employees;
	public EmployeeServiceImpl(){
		employees = new ArrayList<Employee>();
	}
	@Override
	public Employee createEmployee(long id, String firstName, String lastName, String dob, int salary) {
		// TODO Auto-generated method stub
		Employee e = new Employee();
		e.setDob(dob);
		e.setId(id);
		e.setFirstName(firstName);
		e.setLastName(lastName);
		e.setSalary(salary);
		return e;
	}

	@Override
	public List<Employee> getEmployeesList() {
		// TODO Auto-generated method stub
		return employees;
	}

	@Override
	public Employee getEmployeeById(long id) {
		// TODO Auto-generated method stub
		Employee employee = null;
		for(Employee e : employees){
			if (e.getId() == id)
				employee = e;
		}
		return employee;
	}

	@Override
	public void storeEmployee(Employee employee) {
		// TODO Auto-generated method stub
		employees.add(employee);
	}
	
	

}
