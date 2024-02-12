package com.example.code.services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void enviarEmail(String destino, String token) throws Exception{

          //cria uma mensagem de email
         MimeMessage message = javaMailSender.createMimeMessage();

         //apartir deste objeto eu consigo definir o destinatario, remetente e outros
        //vai receber o objeto mensagem e o true para suportar conteudo html
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        //remetente
        messageHelper.setFrom("CodeRise via api <coderise829@gmail.com>");
        //destinatario
        messageHelper.setTo(destino);
        //assunto do email
        messageHelper.setSubject("Redefinição De Senha");
        String resetLink = "http://127.0.0.1:5503/atualizarSenha.html?token=" + token + "&email=" + destino;


        String text = "<html>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "<h1>Recuperação de senha</h1>" +
                "<p>Olá, tudo bem?</p>" +
                "<p>Você solicitou a redefinição de senha do e-mail cadastrado em sistema coderise. Clique no link abaixo para prosseguir:</p>" +
                "<p style='background-color: #f64abf; padding: 10px; color: white; border-radius: 5px; display: inline-block;'><a href='" + resetLink + "' style='color: white; text-decoration: none;'>Redefinir senha</a></p>" +
                "<p>Obrigado pela sua participação e atenção em testar o projeto. Se houver alguma dúvida, sinta-se à vontade para me contatar.</p>" +
                "<p>Atenciosamente,</p>" +
                "<p>CodeRise.</p>" +
                "<span style='background-color: #0e76a8 ; padding: 10px; color: white; border-radius: 5px; display: inline-block;'><a href='https://www.linkedin.com/in/hilizangela-tallia-de-sousa-dos-reis-ab483429b/' style='color: white; text-decoration: none;'>LinkedIn</a></span>  " +
                "<span style='background-color: #171515; padding: 10px; color: white; border-radius: 5px; display: inline-block;'><a href='https://github.com/Tallia-Sousa' style='color: white; text-decoration: none;'>GitHub</a></span>" +
                "</body>" +
                "</html>";

        messageHelper.setText(text, true);

        messageHelper.setText(text,true);

        javaMailSender.send(message);

    }
}
