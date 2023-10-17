package net.javaguides.departmentservice.service.impl;

import net.javaguides.departmentservice.dto.DepartmentDto;
import net.javaguides.departmentservice.entity.Department;
import net.javaguides.departmentservice.repository.DepartmentRepository;
import net.javaguides.departmentservice.service.DeparmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DeparmentService {

    private DepartmentRepository  departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {

        Department department = mapToEntity(departmentDto);

        return mapToDto(departmentRepository.save(department));
    }

    @Override
    public DepartmentDto getDepartmentByCode(String code) {

        Department department = departmentRepository.findByDepartmentCode(code);
        return mapToDto(department);
    }

    private DepartmentDto mapToDto(Department department){
//        DepartmentDto departmentDto = mapper.map(department,DepartmentDto.class);
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setDepartmentName(department.getDepartmentName());
        departmentDto.setDepartmentCode(department.getDepartmentCode());
        departmentDto.setDepartmentDescription(department.getDepartmentDescription());

        return departmentDto;
    }
    private Department mapToEntity(DepartmentDto departmentDto){
//        Department department = mapper.map(departmentDto,Department.class);
        Department department = new Department();
        department.setId(departmentDto.getId());
        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setDepartmentCode(departmentDto.getDepartmentCode());
        department.setDepartmentDescription(departmentDto.getDepartmentDescription());
        return department;
    }



}
