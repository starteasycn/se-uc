package cn.starteasy.uaa.service.impl;

import cn.starteasy.uaa.service.EmployeeService;
import cn.starteasy.uaa.domain.Employee;
import cn.starteasy.uaa.repository.EmployeeRepository;
import cn.starteasy.uaa.service.dto.EmployeeDTO;
import cn.starteasy.uaa.service.mapper.EmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Employee.
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService{

    private final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    
    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private EmployeeMapper employeeMapper;

    /**
     * Save a employee.
     *
     * @param employeeDTO the entity to save
     * @return the persisted entity
     */
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.debug("Request to save Employee : {}", employeeDTO);
        Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
        employee = employeeRepository.save(employee);
        EmployeeDTO result = employeeMapper.employeeToEmployeeDTO(employee);
        return result;
    }

    /**
     *  Get all the employees.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        Page<Employee> result = employeeRepository.findAll(pageable);
        return result.map(employee -> employeeMapper.employeeToEmployeeDTO(employee));
    }

    /**
     *  Get one employee by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public EmployeeDTO findOne(Long id) {
        log.debug("Request to get Employee : {}", id);
        Employee employee = employeeRepository.findOne(id);
        EmployeeDTO employeeDTO = employeeMapper.employeeToEmployeeDTO(employee);
        return employeeDTO;
    }

    /**
     *  Delete the  employee by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.delete(id);
    }
}
