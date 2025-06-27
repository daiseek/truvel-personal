package alt_t.truvel.auth.emailVerification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailSenderService	 {

    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);                          // 수신자
        message.setSubject(subject);                // 제목
        message.setText(text);                      // 본문 (인증코드 포함)
        message.setFrom("noreply@truvel.com");      // 발신자 (Gmail 주소 그대로 써도 됨)

        mailSender.send(message);
    }
}