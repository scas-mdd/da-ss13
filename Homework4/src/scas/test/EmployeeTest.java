package scas.test;

import scas.domain.employee.Employee;
import scas.domain.persistence.EmployeeServiceImpl;

public class EmployeeTest {

	public static void main(String [] args){
		EmployeeServiceImpl manager = new EmployeeServiceImpl();
		Employee e = manager.createEmployee(1, "First", "Employee", "13th July 1990", 1000);
		manager.storeEmployee(e);
		Employee fetchedEmployee = manager.getEmployeeById(1);
		System.out.println("The name of the created employee is " + e.getFirstName() + " " + e.getLastName());
		System.out.println("The name of the fetched employee is " + fetchedEmployee.getFirstName() + " " + fetchedEmployee.getLastName());
		System.out.println(fetchedEmployee.equals(e));
	}
}
