package org.example.service;

import org.example.model.AccountHistory;
import org.example.model.User;
import org.example.repository.AccountHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
public class AccountHistoryService {
    private final AccountHistoryRepository accountHistoryRepository;

    @Autowired
    public AccountHistoryService(AccountHistoryRepository accountHistoryRepository){
        this.accountHistoryRepository = accountHistoryRepository;
    }

    public boolean makeNewEntry(User oldUser, User newUser){
        AccountHistory newEntry = new AccountHistory();
        newEntry.setOld_email(oldUser.getEmail());
        newEntry.setOld_firstname(oldUser.getFirstname());
        newEntry.setOld_lastname(oldUser.getLastname());
        newEntry.setOld_password(oldUser.getPassword());
        newEntry.setOld_role(oldUser.getRole());
        newEntry.setNew_email(newUser.getEmail());
        newEntry.setNew_firstname(newUser.getFirstname());
        newEntry.setNew_lastname(newUser.getLastname());
        newEntry.setNew_password(newUser.getPassword());
        newEntry.setNew_role(newUser.getRole());
        newEntry.setDate_modified(LocalDate.now());
        this.accountHistoryRepository.save(newEntry);
        return true;
    }
}
