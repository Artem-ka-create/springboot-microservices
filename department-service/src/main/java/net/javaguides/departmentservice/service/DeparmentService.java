package net.javaguides.departmentservice.service;


import net.javaguides.departmentservice.dto.DepartmentDto;

public interface DeparmentService {

    DepartmentDto saveDepartment(DepartmentDto departmentDto);

    DepartmentDto getDepartmentByCode(String code);

}
