package com.tyss.main.command;

import lombok.Data;
/**
 * This class is used for taking login form data from login form page
 * @author TYSS
 *
 */
@Data
public class UserCommand {
    private String username;
    private String password;
}
