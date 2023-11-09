package co.edu.escuelaing.cvds.lab7.service;

import co.edu.escuelaing.cvds.lab7.model.Employee;
import co.edu.escuelaing.cvds.lab7.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public void createEmployee(){
        Employee employee = new Employee();
        employeeRepository.save(employee);
    }

    public List<Employee> getEmployees(){
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(long id){
        return employeeRepository.findById(id);
    }
    public  Employee saveEmployee(Employee e){
        return employeeRepository.save(e);
    }
    public boolean deleteEmployee(long id){
        try{
            Optional<Employee> employee= this.getEmployeeById(id);
            employeeRepository.delete(employee.get());
            return true;
        }catch (Exception e){
            return false;
        }
    }
//    public void fillEmployees(){
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        try {
//            InputStream inputStream = getClass().getResourceAsStream("/data.json");
//            List<Employee> employees = objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
//            for (Employee employee : employees) {
//                this.saveEmployee(employee);
//            }
//        } catch (IOException e) {
//            System.out.println("ERRROOOOOOOOOOOOOOOOOOOOOOOOOrrrrrrrrr");
//            e.printStackTrace();
//
//        }
//
//    }
    public void fillEmployees(){
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            URL url = new URL("https://my.api.mockaroo.com/employee.json?key=15cfd9f0"); // Reemplaza con la URL real
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            InputStream inputStream = connection.getInputStream();

            List<Employee> employees = objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
            for (Employee employee : employees) {
                this.saveEmployee(employee);
            }
            connection.disconnect();
        } catch (IOException e) {
            System.out.println("ERRROOOOOOOOOOOOOOOOOOOOOOOOOrrrrrrrrr");
            e.printStackTrace();

        }

    }
}