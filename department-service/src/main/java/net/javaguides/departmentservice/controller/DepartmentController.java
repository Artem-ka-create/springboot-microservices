package net.javaguides.departmentservice.controller;

import net.javaguides.departmentservice.dto.DepartmentDto;
import net.javaguides.departmentservice.service.DeparmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/departments")
public class DepartmentController {

    private DeparmentService departmentService;

    public DepartmentController(DeparmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    private ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto){
        return new ResponseEntity<>(departmentService.saveDepartment(departmentDto),HttpStatus.CREATED);
    }
    @GetMapping("{department-code}")
    private  ResponseEntity<DepartmentDto> getDepartmentByCode(@PathVariable(name="department-code") String code){
        return ResponseEntity.ok(departmentService.getDepartmentByCode(code));
    }
}
