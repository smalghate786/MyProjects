package com.tyss.main.service;

import java.io.BufferedReader;
import java.io.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyss.main.command.UserCommand;
import com.tyss.main.util.EmailUtils;

@Service
public class LoginServiceImpl implements LoginService {
	@Autowired
	private EmailUtils emailUtils;
	/**
	 * This method is used to reg email body from a file
	 * 
	 * @param userCmd
	 * @return String
	 * @throws Exception
	 */
	public String getEmailBodyContent(UserCommand userCmd, String fileName) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		StringBuffer body = new StringBuffer();
		String line = br.readLine();
		while (line != null) {
			if (line != null && !"".equals(line) && !"<br/>".equals(line)) {
				// process
				if (line.contains("USER_NAME")) {
					line = line.replace("USER_NAME", userCmd.getUsername());
				}
				if (line.contains("APP_URL")) {
					line = line.replace("APP_URL", "<a href='http://localhost:7070/ytss/'>IES URL</a>");
				}
				if (line.contains("APP_USER_PWD")) {
					line = line.replace("APP_USER_PWD", userCmd.getPassword());
				}
				// Adding processed line to SB body
				body.append(line);
			}
			// read next line
			line = br.readLine();
		}
		// closing br
		br.close();
		return body.toString();
	}

	@Override
	public String sendMail(UserCommand userCmd) {
		String fileName = null, mailSub = null, mailBody = null, password = null,msg=null;
		try {
			// get file name
			fileName = "Account_Active_Email_Template.txt";
			// get mail subject
			mailSub = "Your Account Activated";
			// decrypt the password
			password =userCmd.getPassword();
			// set decrypted password to accModel object password field
			userCmd.setPassword(password);
			// get email body
			mailBody = getEmailBodyContent(userCmd, fileName);
			// send email to activate registered cw/admin
			emailUtils.sendEmail("dibakarmaji101995@gmail.com", mailSub, mailBody);
			//set msg
			msg="Mail send is succeded";
			//return msg
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			return "Mail send is faild";
			
		}
	}
}
