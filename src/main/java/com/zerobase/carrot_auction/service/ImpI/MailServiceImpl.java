package com.zerobase.carrot_auction.service.ImpI;

import com.zerobase.carrot_auction.repository.UserRepository;
import com.zerobase.carrot_auction.repository.entity.UserEntity;
import com.zerobase.carrot_auction.service.MailService;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

	private static final String emailAuthURL = "http://localhost:8080/user/verify";
	private final UserRepository userRepository;
	private final JavaMailSender javaMailSender;
	@Value("${AdminMail.id}")
	private String from;

	private MimeMessage createMessage(Long userId) throws Exception {
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("해당 유저가 존재하지 않습니다"));
		MimeMessage message = javaMailSender.createMimeMessage();

		message.addRecipients(Message.RecipientType.TO, user.getEmail());
		message.setSubject("당근옥션 가입을 축하드립니다");

		String content = "<div style ='margin:20px;'>"
			+ String.format("<h1> 안녕하세요, %s님! </h1>", user.getNickname())
			+ "<h1> 가입 완료를 위해 아래 링크를 눌러주세요</h1>"
			+ String.format("<a href=%s?id=%d&authCode=%s>이메일 인증</a>", emailAuthURL, userId,
			user.getAuthCode())
			+ "</div>";

		message.setText(content, "utf-8", "html");
		message.setFrom(from);

		return message;
	}

	@Override
	public void sendMessage(Long userId) throws Exception {
		MimeMessage message = createMessage(userId);
		try {
			javaMailSender.send(message);
		} catch (MailException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
}
