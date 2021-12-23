package com.globits.da.service.impl;

import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.ResponseMessage;
import com.globits.da.dto.search.EmployeeSearchDTO;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.service.EmployeeService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public Object saveOrUpdate(EmployeeDTO dto) {
        ResponseMessage msg= new ResponseMessage();
        msg.setMsg("");
        if (dto!=null){
            //validate code
            if (dto.getCode()==null && !StringUtils.hasText(dto.getCode())){
                msg.setMsg(msg.getMsg()+"Code không được bỏ trống\n");
            } else {
                if (dto.getCode().length()<6 || dto.getCode().length()>10){
                    msg.setMsg(msg.getMsg()+"Code phải dài tối thiểu 6 kí tự và tối đa 10 kí tự\n");
                }
                if (StringUtils.containsWhitespace(dto.getCode()))
                    msg.setMsg(msg.getMsg()+"Code không được có khoảng trắng\n");
                if (!employeeRepository.findAllByCode(dto.getCode()).isEmpty())
                    msg.setMsg(msg.getMsg()+"Code đã tồn tại\n");
            }
            //validate name
            if (dto.getName()==null || !StringUtils.hasText(dto.getName())){
                msg.setMsg(msg.getMsg()+"Tên không được bỏ trống\n");
            }
            //validate email
            if (dto.getEmail()==null || !StringUtils.hasText(dto.getEmail())){
                msg.setMsg(msg.getMsg()+"Email không được bỏ trống\n");
            } else {
                try{
                    InternetAddress address = new InternetAddress(dto.getEmail());
                    address.validate();
                } catch (AddressException e) {
                    msg.setMsg(msg.getMsg()+"Email chưa đúng định dạng\n");
                    e.printStackTrace();
                }
            }
            //validate phone
            if (dto.getPhone()==null || !StringUtils.hasText(dto.getPhone())){
                msg.setMsg(msg.getMsg()+"SDT không được bỏ trống\n");
            } else {
                if (!dto.getPhone().matches("[0-9]+")){
                    msg.setMsg(msg.getMsg()+"SDT chỉ được phép điền số\n");
                }
                if (dto.getPhone().length()!=10){
                    msg.setMsg(msg.getMsg()+"SDT phải có đủ 10 chữ số\n");
                }
            }
            //validate age
            if (dto.getAge()==null)
                msg.setMsg(msg.getMsg()+"Tuổi không được bỏ trống\n");
            else if (dto.getAge()<=0)
                msg.setMsg(msg.getMsg()+"Tuổi không hợp lệ");

            //validated->save
            if (msg.getMsg().isEmpty() || msg.getMsg().equals("")){
                Employee result = null;
                if (dto.getId()!=null){
                    result = employeeRepository.getOne(dto.getId());
                }
                result = new Employee(dto);
                employeeRepository.save(result);
                return new EmployeeDTO(result);
            }
        }
        return msg;
    }

    @Override
    public List<EmployeeDTO> search(EmployeeSearchDTO dto) {
        List<Employee> entities = employeeRepository.findAll();
        List<EmployeeDTO> results = new ArrayList<>();
        if (dto!=null){
            if (dto.getKeyword()!=null && StringUtils.hasText(dto.getKeyword()))
                entities = employeeRepository.search(dto.getKeyword());
        }
        for (Employee e:entities){
            results.add(new EmployeeDTO(e));
        }
        if (results.size()>0)
            return results;
        else
            return null;
    }

    @Override
    public void delete(Long id) {
        if (employeeRepository.existsById(id))
            employeeRepository.deleteById(id);
    }

    @Override
    public File exportExcel(){
        String[] cols = {"STT","Tên","Mã","Email","SDT","Tuổi"};
        List<Employee> employees = employeeRepository.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        //format and create header
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);
        for (int i=0;i<cols.length;i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(cols[i]);
            cell.setCellStyle(headerCellStyle);
        }
        //Write data
        int rowNum = 1;
        int stt=1;
        for (Employee employee:employees){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(stt);
            row.createCell(1).setCellValue(employee.getName());
            row.createCell(2).setCellValue(employee.getCode());
            row.createCell(3).setCellValue(employee.getEmail());
            row.createCell(4).setCellValue(employee.getPhone());
            row.createCell(5).setCellValue(employee.getAge());
            stt++;
        }
        //Write file
        try {
            File file = new File("Employee.xls");
            FileOutputStream fop = new FileOutputStream(file);
            if (!file.exists()){
                file.createNewFile();
            }
            workbook.write(fop);
            fop.close();
            workbook.close();

            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
