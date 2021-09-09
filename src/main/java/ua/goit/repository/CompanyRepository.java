package ua.goit.repository;

import ua.goit.model.Company;

public class CompanyRepository extends BaseRepositoryImpl<Long, Company>{
    public CompanyRepository(Class<Company> modelClass) {
        super(modelClass);
    }
}
