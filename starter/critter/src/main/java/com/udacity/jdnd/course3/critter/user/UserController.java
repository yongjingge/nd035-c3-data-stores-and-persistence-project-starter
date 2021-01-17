package com.udacity.jdnd.course3.critter.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    CustomerService customerService;
    
    @Autowired
    EmployeeService employeeService;

    // ----------------- CUSTOMER -----------------
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = convertCustomerDTOToEntity(customerDTO);
        return convertEntityToCustomerDTO(customerService.saveCustomer(customer));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return convertEntitiesToCustomerDTOs(customerService.getAllCustomers());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return convertEntityToCustomerDTO(customerService.getCustomerByPetId(petId));
    }

    public static CustomerDTO convertEntityToCustomerDTO (Customer Customer) {
        CustomerDTO CustomerDTO = new CustomerDTO();
        BeanUtils.copyProperties(Customer, CustomerDTO);
        return CustomerDTO;
    }

    public static Customer convertCustomerDTOToEntity (CustomerDTO CustomerDTO) {
        Customer Customer = new Customer();
        BeanUtils.copyProperties(CustomerDTO, Customer);
        return Customer;
    }

    public static List<CustomerDTO> convertEntitiesToCustomerDTOs (List<Customer> Customers) {
        List<CustomerDTO> res = new ArrayList<>();
        for (Customer singleCustomer : Customers) {
            res.add(convertEntityToCustomerDTO(singleCustomer));
        }
        return res;
    }

    // ----------------- EMPLOYEE -----------------
    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertEmployeeDTOToEntity(employeeDTO);
        return convertEntityToEmployeeDTO(employeeService.saveEmployee(employee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertEntityToEmployeeDTO(employeeService.getEmployeeByEmployeeId(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailabilityForEmployee(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        // need to separate employeeRequestDTO into fields
        LocalDate date = employeeDTO.getDate();
        Set<EmployeeSkill> skills = employeeDTO.getSkills();
        List<Employee> resEntities = employeeService.findEmployeesForService(skills, date.getDayOfWeek());
        return convertEntitiesToDTOs(resEntities);
    }

    public static EmployeeDTO convertEntityToEmployeeDTO (Employee Employee) {
        EmployeeDTO EmployeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(Employee, EmployeeDTO);
        return EmployeeDTO;
    }

    public static Employee convertEmployeeDTOToEntity (EmployeeDTO EmployeeDTO) {
        Employee Employee = new Employee();
        BeanUtils.copyProperties(EmployeeDTO, Employee);
        return Employee;
    }

    public static List<EmployeeDTO> convertEntitiesToDTOs (List<Employee> Employees) {
        List<EmployeeDTO> res = new ArrayList<>();
        for (Employee singleEmployee : Employees) {
            res.add(convertEntityToEmployeeDTO(singleEmployee));
        }
        return res;
    }

}
