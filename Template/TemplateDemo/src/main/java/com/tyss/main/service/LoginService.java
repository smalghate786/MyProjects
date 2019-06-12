package com.tyss.main.service;

import com.tyss.main.command.UserCommand;

public interface LoginService {
    public String sendMail(UserCommand userCmd);
    public String getEmailBodyContent(UserCommand userCmd, String fileName) throws Exception;
}
