package com.staffinity.recruiting.internalSearch.infrastructure.adapters.employees;

import java.util.Collections;
import java.util.List;

public class NoOpEmployeeClient implements EmployeeClient {

    @Override
    public List<Employee> getEmployees() {
        return Collections.emptyList();
    }
}
