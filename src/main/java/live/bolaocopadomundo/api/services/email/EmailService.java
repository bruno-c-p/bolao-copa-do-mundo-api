package live.bolaocopadomundo.api.services.email;

import live.bolaocopadomundo.api.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${mail.from}")
    private String emailFrom;

    @Autowired
    private JavaMailSender emailSender;

    public void sendMailMessage(final SimpleMailMessage simpleMailMessage) {
        this.emailSender.send(simpleMailMessage);
    }

    public void sendConfirmationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(user.getEmail());
        message.setSubject("Bolão Copa do Mundo 2022 - Confirmação de Email");
        message.setText("Olá, "
                + user.getNickname() + "!\n\n"
                + "Seu código de ativação é: " + user.getActivationCode() + "\n\n"
                + "Atenciosamente,\n" + "Bolão Copa do Mundo 2022");

        this.sendMailMessage(message);
    }
}