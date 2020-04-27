package com.owen.springboot.controller;

import com.owen.springboot.entity.Company;
import com.owen.springboot.respository.CompanyRepository;
import com.owen.springboot.service.CompanyService;
import com.owen.springboot.util.WebUtils;
import io.swagger.annotations.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/company")
@Api(value = "Company details")
public class CompanyController {
    CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping({"/"})
    @PreAuthorize("hasRole('OPERATOR') or hasRole('MANAGER') or hasRole('ADMIN')")
    @ApiOperation(value = "get All Companies", nickname = "getCompanies", notes = "Return Companies", response = ResponseEntity.class, tags={ "company", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = ResponseEntity.class)})
    public ResponseEntity<Iterable<Company>> getCompanies(){
        return ResponseEntity.ok().body(companyService.findAll());
    }

    @GetMapping({"/{id}"})
    @PreAuthorize("hasRole('OPERATOR') or hasRole('MANAGER') or hasRole('ADMIN')")
    @ApiOperation(value = "get Company detail", nickname = "getCompany", notes = "get company detail by company id", response = ResponseEntity.class, tags={ "company", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = ResponseEntity.class)})
    public ResponseEntity<Optional<Company>> getCompany(@PathVariable Long id){
        return ResponseEntity.ok().body(companyService.find(id));
    }

    @DeleteMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @ApiOperation(value = "delete Company", nickname = "deleteCompany", notes = "delete company by company id", response = ResponseEntity.class, tags={ "company", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = ResponseEntity.class)})
    public ResponseEntity deleteCompany(@PathVariable Long id){
        try {
            companyService.delete(id);
        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @ApiOperation(value = "update Company", nickname = "updateCompany", notes = "update company detail by company id", response = ResponseEntity.class, tags={ "company", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = ResponseEntity.class)})
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @ApiParam(value = "company detail to update to",required=true) @RequestBody Company updateCompany){
        Optional<Company> optional = companyService.find(id);
        String userName = WebUtils.getUserName();
        Company company = optional.get();
        company.setUpdatedBy(userName);
        company.setUpdatedAt(new Date());
        company.setName(updateCompany.getName() == null?company.getName():updateCompany.getName());
        company.setAddress(updateCompany.getAddress() == null? company.getAddress():updateCompany.getAddress());
        return ResponseEntity.ok().body(companyService.update(company));
    }

    @PostMapping({"/"})
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    @ApiOperation(value = "add Companies", nickname = "addCompanies", notes = "add multi companies at once", response = ResponseEntity.class, tags={ "company", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = ResponseEntity.class)})
    public ResponseEntity<Iterable<Company>> addCompanies(@ApiParam(value = "companies to add",required=true) @RequestBody List<Company> companyList){
        String userName = WebUtils.getUserName();
        for (Company company:companyList) {
            company.setCreatedAt(new Date());
            company.setUpdatedAt(new Date());
            company.setCreatedBy(userName);
            company.setUpdatedBy(userName);
        }

        return ResponseEntity.ok().body(companyService.save(companyList));
    }
}
