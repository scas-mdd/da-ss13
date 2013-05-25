package scas.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import scas.domain.Employee;


@Stateless
public class EmployeeEJB implements EmployeeEJBRemote,Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "my-pu")
	private EntityManager entityManager;
	
	@Override
	public Employee createEmployee(String firstName, String lastName, String dob, int salary) {
		Employee e = new Employee();
		e.setFirstName(firstName);
		e.setLastName(lastName);
		e.setSalary(salary);
		e.setDob(dob);
		return e;
	}

	@Override
	public List<Employee> getEmployeesList() {
		Query query = entityManager.createNativeQuery("select * from employee", Employee.class);
		List<Employee> list = query.getResultList();
		return list;
	}

	@Override
	public Employee getEmployeeById(int id) {
		// TODO Auto-generated method stub
		Employee employee = entityManager.find(Employee.class, id);
		return employee;
	}

	@Override
	public void saveEmployee(Employee employee) {
		if(employee.getId() == -1){
			entityManager.persist(employee);
		}
		else
			entityManager.merge(employee);
	}
	public void updateEmployee(Employee employee){
		entityManager.merge(employee);
	}
	@Override
	public void deleteEmployee(Employee employee) {
		entityManager.createNativeQuery("delete from employee where id="+employee.getId(), Employee.class).executeUpdate();
		//entityManager.remove(employee);
	}

	
}
