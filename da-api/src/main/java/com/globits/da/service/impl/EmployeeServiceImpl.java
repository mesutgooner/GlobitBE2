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

import javax.mail.Message;
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
        ResponseMessage responseMessage = new ResponseMessage();
        if (dto!=null){
            boolean update = false;
            Employee result = null;
            if (dto.getId()!=null){
                result = employeeRepository.getOne(dto.getId());
            }
            if (result!=null)
                update = true;
            responseMessage = validate(dto,update);
            if (responseMessage.getMsg().isEmpty() || responseMessage.getMsg().equals("") || !StringUtils.hasText(responseMessage.getMsg())){
                result = new Employee(dto);
                employeeRepository.save(result);
                return new EmployeeDTO(result);
            }
        }
        return responseMessage;
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
        String[] cols = {"STT","T??n","M??","Email","SDT","Tu???i"};
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

    private ResponseMessage validate(EmployeeDTO dto, boolean isUpdate){
        ResponseMessage msg= new ResponseMessage();
        msg.setMsg("");
        //validate code
        if (dto.getCode()==null && !StringUtils.hasText(dto.getCode())){
            msg.setMsg(msg.getMsg()+"Code kh??ng ???????c b??? tr???ng\n");
        } else {
            if (dto.getCode().length()<6 || dto.getCode().length()>10){
                msg.setMsg(msg.getMsg()+"Code ph???i d??i t???i thi???u 6 k?? t??? v?? t???i ??a 10 k?? t???\n");
            }
            if (StringUtils.containsWhitespace(dto.getCode()))
                msg.setMsg(msg.getMsg()+"Code kh??ng ???????c c?? kho???ng tr???ng\n");
            if (!employeeRepository.findAllByCode(dto.getCode()).isEmpty() && isUpdate==false)
                msg.setMsg(msg.getMsg()+"Code ???? t???n t???i\n");
        }
        //validate name
        if (dto.getName()==null || !StringUtils.hasText(dto.getName())){
            msg.setMsg(msg.getMsg()+"T??n kh??ng ???????c b??? tr???ng\n");
        }
        //validate email
        if (dto.getEmail()==null || !StringUtils.hasText(dto.getEmail())){
            msg.setMsg(msg.getMsg()+"Email kh??ng ???????c b??? tr???ng\n");
        } else {
            try{
                InternetAddress address = new InternetAddress(dto.getEmail());
                address.validate();
            } catch (AddressException e) {
                msg.setMsg(msg.getMsg()+"Email ch??a ????ng ?????nh d???ng\n");
                e.printStackTrace();
            }
        }
        //validate phone
        if (dto.getPhone()==null || !StringUtils.hasText(dto.getPhone())){
            msg.setMsg(msg.getMsg()+"SDT kh??ng ???????c b??? tr???ng\n");
        } else {
            if (!dto.getPhone().matches("[0-9]+")){
                msg.setMsg(msg.getMsg()+"SDT ch??? ???????c ph??p ??i???n s???\n");
            }
            if (dto.getPhone().length()!=10){
                msg.setMsg(msg.getMsg()+"SDT ph???i c?? ????? 10 ch??? s???\n");
            }
        }
        //validate age
        if (dto.getAge()==null)
            msg.setMsg(msg.getMsg()+"Tu???i kh??ng ???????c b??? tr???ng\n");
        else if (dto.getAge()<=0)
            msg.setMsg(msg.getMsg()+"Tu???i kh??ng h???p l???");
        return msg;
    }
}
