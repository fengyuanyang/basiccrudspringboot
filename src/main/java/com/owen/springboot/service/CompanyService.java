package com.owen.springboot.service;

import com.owen.springboot.entity.Company;
import com.owen.springboot.respository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    public CompanyService(@Autowired CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Iterable<Company> findAll() {
        return companyRepository.findAll();
    }

    public Iterable<Company> save(Iterable<Company> entities) {
        return companyRepository.saveAll(entities);
    }

    public Optional<Company> find(Long id) {
        return companyRepository.findById(id);
    }

    public Company update(Company company) {
        return companyRepository.save(company);
    }

    public void delete(Long id) {
        companyRepository.deleteById(id);
    }
}
