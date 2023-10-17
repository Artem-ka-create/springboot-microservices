package net.javaguides.employeeservice.service.impl;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import net.javaguides.employeeservice.dto.APIResponseDto;
import net.javaguides.employeeservice.dto.DepartmentDto;
import net.javaguides.employeeservice.dto.EmployeeDto;
import net.javaguides.employeeservice.dto.OrganizationDto;
import net.javaguides.employeeservice.entity.Employee;
import net.javaguides.employeeservice.repository.EmployeeRepository;
import net.javaguides.employeeservice.service.APIClient;
import net.javaguides.employeeservice.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private EmployeeRepository employeeRepository;
//    private RestTemplate restTemplate;
    private WebClient webClient;

//    private APIClient apiClient;



    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee employee = mapToEntity(employeeDto);

        return mapToDto(employeeRepository.save(employee));
    }

//    @CircuitBreaker(name="${spring.application.name}",fallbackMethod = "getDefaultDepartment")
    @Retry(name ="${spring.application.name}" , fallbackMethod = "getDefaultDepartment")
    @Override
    public APIResponseDto getEmployeeById(Long id) {
        LOGGER.info("inside getEmployeeById() method");
        Employee employee = employeeRepository.findById(id).orElse(new Employee());
//        ResponseEntity<DepartmentDto> responseEntity = restTemplate
//                .getForEntity("http://localhost:8080/api/departments/"
//                        + employee.getDepartmntCode(), DepartmentDto.class);


        DepartmentDto departmentDto = webClient.get().uri("http://localhost:8080/api/departments/"
                        + employee.getDepartmntCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();

        OrganizationDto organizationDto = webClient.get().uri("http://localhost:8083/api/organizations/"
                        +employee.getOrganizationCode())
                .retrieve()
                .bodyToMono(OrganizationDto.class)
                .block();

//        DepartmentDto departmentDto = apiClient.getDepartment(employee.getDepartmntCode());
////        DepartmentDto departmentDto = responseEntity.getBody();
        EmployeeDto employeeDto= mapToDto(employee);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);
        apiResponseDto.setOrganizationDto(organizationDto);

        return apiResponseDto;
    }


    private EmployeeDto mapToDto(Employee employee){
//        EmployeeDto employeeDto = mapper.map(employee,EmployeeDto.class);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setDepartmntCode(employee.getDepartmntCode());
        employeeDto.setOrganizationCode(employee.getOrganizationCode());

        return employeeDto;
    }
    private Employee mapToEntity(EmployeeDto employeeDto){
//        Employee employee = mapper.map(employeeDto,Employee.class);
        Employee employee = new  Employee();
        employee.setId(employeeDto.getId());
        employee.setEmail(employeeDto.getEmail());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setDepartmntCode(employeeDto.getDepartmntCode());
        employee.setOrganizationCode(employeeDto.getOrganizationCode());


        return employee;
    }


    public APIResponseDto getDefaultDepartment(Long id,Exception exception) {

        LOGGER.info("inside getDefaultEmployeeById() method");

        Employee employee = employeeRepository.findById(id).orElse(new Employee());

        DepartmentDto departmentDto = new DepartmentDto();

        departmentDto.setDepartmentName("R&D Department name");
        departmentDto.setDepartmentCode("RD001");
        departmentDto.setDepartmentDescription("Research and Development department");

        EmployeeDto employeeDto= mapToDto(employee);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);

        return apiResponseDto;
    }
}
