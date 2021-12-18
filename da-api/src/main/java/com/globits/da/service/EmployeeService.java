package com.globits.da.service;

import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.EmployeeSearchDTO;

import java.io.File;
import java.util.List;

public interface EmployeeService {
    EmployeeDTO saveOrUpdate(EmployeeDTO dto);

    List<EmployeeDTO> search(EmployeeSearchDTO dto);

    void delete(Long id);

    File exportExcel();
}
