package email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail
{
public boolean sendEmail(String from, String to, String username, String password, String text)
  {
    /*boolean isSSL = true;
    String host = "smtp.163.com";
    int port = 465;
    boolean isAuth = true;*/

    Properties props = new Properties();
	InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("mail.properties");
	try {
		props.load(inStream);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
  /* props.put("mail.smtp.ssl.enable", Boolean.valueOf(isSSL));
   props.put("mail.smtp.host", host);
   props.put("mail.smtp.port", Integer.valueOf(port));
   props.put("mail.smtp.auth", Boolean.valueOf(isAuth));
   props.put("mail.debug", "true");*/
    
    Session session = Session.getDefaultInstance(props,new Authenticator(){
    @Override
      protected PasswordAuthentication getPasswordAuthentication() {
    	  return new PasswordAuthentication(username,password);
      } } );
    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject("验证码");
      message.setText(text);
      Transport.send(message);
      System.out.println("发送完毕！");
      return true;
    } catch (AddressException e) {
      throw new RuntimeException("邮箱地址出错" + e.getMessage()); 
    } catch (MessagingException e) {
    throw new RuntimeException("发送邮件出错" +e.getMessage());
  }
}}