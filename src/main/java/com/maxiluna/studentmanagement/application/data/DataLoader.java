package com.maxiluna.studentmanagement.application.data;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    @Autowired
    private DataService dataService;

    @PostConstruct
    public void loadData() {
        dataService.loadData();
    }
}

