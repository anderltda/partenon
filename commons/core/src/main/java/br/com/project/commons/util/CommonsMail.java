package br.com.project.commons.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public class CommonsMail {

	public CommonsMail() throws EmailException, MalformedURLException {
		//enviaEmailSimples();
		//enviaEmailComAnexo();
		//enviaEmailFormatoHtml();
	}

	/**
	 * envia email simples(somente texto)
	 * @throws EmailException
	 */
	public void enviaEmailSimples() throws EmailException {

		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
		email.addTo("anderltda@gmail.com", "Siga"); //destinatário
		email.setFrom("anderltda@gmail.com", "Eu"); // remetente
		email.setSubject("Teste -> Email simples"); // assunto do e-mail
		email.setMsg("Teste de Email utilizando commons-email"); //conteudo do e-mail
		email.setAuthentication("teste", "xxxxx");
		email.setSmtpPort(465);
		email.setSSL(true);
		email.setTLS(true);
		email.send();	
	}


	/**
	 * envia email com arquivo anexo
	 * @throws EmailException
	 */
	public void enviaEmailComAnexo() throws EmailException{

		// cria o anexo 1.
		EmailAttachment anexo1 = new EmailAttachment();
		anexo1.setPath("teste/teste.txt"); //caminho do arquivo (RAIZ_PROJETO/teste/teste.txt)
		anexo1.setDisposition(EmailAttachment.ATTACHMENT);
		anexo1.setDescription("Exemplo de arquivo anexo");
		anexo1.setName("teste.txt");		

		// cria o anexo 2.
		EmailAttachment anexo2 = new EmailAttachment();
		anexo2.setPath("teste/teste2.jsp"); //caminho do arquivo (RAIZ_PROJETO/teste/teste2.jsp)
		anexo2.setDisposition(EmailAttachment.ATTACHMENT);
		anexo2.setDescription("Exemplo de arquivo anexo");
		anexo2.setName("teste2.jsp");		

		// configura o email
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
		email.addTo("anderltda@gmail.com", "Siga"); //destinatário
		email.setFrom("anderltda@gmail.com", "Eu"); // remetente
		email.setSubject("Teste -> Email com anexos"); // assunto do e-mail
		email.setMsg("Teste de Email utilizando commons-email"); //conteudo do e-mail
		email.setAuthentication("teste", "xxxxx");
		email.setSmtpPort(465);
		email.setSSL(true);
		email.setTLS(true);

		// adiciona arquivo(s) anexo(s)
		email.attach(anexo1);
		email.attach(anexo2);
		// envia o email
		email.send();
	}


	/**
	 * Envia email no formato HTML
	 * @throws EmailException 
	 * @throws MalformedURLException 
	 */
	public static void enviaEmailFormatoHtml(List<String> destinatarios, String subject, String message, List<String> ccs, List<String> bccs) throws Exception {

		HtmlEmail email = new HtmlEmail();

		// adiciona uma imagem ao corpo da mensagem e retorna seu id
		URL url = new URL("http://www.apache.org/images/asf_logo_wide.gif");
		String cid = email.embed(url, "Apache logo");	

		// configura a mensagem para o formato HTML
		email.setHtmlMsg("<html>Logo do Apache - <img ></html>");

		// configure uma mensagem alternativa caso o servidor não suporte HTML
		email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML");

		email.setHostName("smtp.gmail.com"); // o servidor SMTP para envio do e-mail
		
		for (String mail : destinatarios) {
			email.addTo(mail); //destinatário			
		}		
		
		if(SetUtil.nonEmpty(ccs)) {
			for (String mail : ccs) {
				email.addCc(mail); //cc			
			}
		}

		if(SetUtil.nonEmpty(bccs)) {
			for (String mail : bccs) {
				email.addBcc(mail); //bcc			
			}		
		}		

		email.setFrom("sistema@phisolucoes.com.br", "Phi-Soluções"); // remetente
		email.setSubject(subject); // assunto do e-mail
		email.setMsg(message); //conteudo do e-mail
		email.setAuthentication("anderltda", "90a20a30");
		email.setSmtpPort(465);
		email.setSSL(true);
		email.setTLS(true);
		
		// envia email
		email.send();
	}


	/**
	 * @param args
	 * @throws EmailException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws Exception {
		//CommonsMail.enviaEmailFormatoHtml("anderltda@yahoo.com.br", "Segue a senha para acesso");

		// instância um objeto da classe Random usando o construtor padrão
		Random gerador = new Random(); //imprime sequência de 10 números inteiros aleatórios
		System.out.println(gerador.nextInt(19700621)); 



	}

}
