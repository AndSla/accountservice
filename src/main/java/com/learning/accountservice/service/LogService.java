package com.learning.accountservice.service;

import com.learning.accountservice.model.LogEvent;
import com.learning.accountservice.repository.LogEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Autowired
    LogEventRepository logEventRepository;

    public LogService() {
    }

    public void log(String msg, String subject, String object, String path) {
        LogEvent logEvent = new LogEvent();
        logEvent.setAction(msg);
        logEvent.setSubject(subject);
        logEvent.setObject(object);
        logEvent.setPath(path);
        logEventRepository.save(logEvent);
    }

    public LogEventRepository getLogEventRepository() {
        return logEventRepository;
    }

}
