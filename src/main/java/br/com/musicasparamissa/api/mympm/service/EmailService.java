package br.com.musicasparamissa.api.mympm.service;

import br.com.musicasparamissa.api.mympm.entity.LogEmail;
import br.com.musicasparamissa.api.mympm.entity.Usuario;
import br.com.musicasparamissa.api.mympm.repository.LogEmailRepository;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service("MyMpMEmailService")
public class EmailService {

	@Value("${mpm_api.apps.mympm.sendgrid_api_key}")
	private String sendgridApiKey;

	@Autowired
	private LogEmailRepository logEmailRepository;

	public void send(String addressTo, String subject, String body) {

		//Send e-mail
		Email from = new Email("contato@musicasparamissa.com.br", "Equipe Músicas para Missa");
		Email to = new Email(addressTo);
		Content content = new Content("text/html", body);
		Mail mail = new Mail(from, subject, to, content);
		SendGrid sg = new SendGrid(sendgridApiKey);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println("E-mail response: " + response.getStatusCode());
		} catch (IOException ex) {
			ex.printStackTrace();
			return;
		}

		//Log e-mail
		LogEmail logEmail = new LogEmail();
		logEmail.setData(new Date());
		logEmail.setTitle(subject);
		logEmail.setEmail(addressTo);
		logEmailRepository.save(logEmail);

	}

}
