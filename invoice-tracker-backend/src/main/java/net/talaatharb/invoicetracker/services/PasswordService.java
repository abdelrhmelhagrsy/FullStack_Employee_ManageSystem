package net.talaatharb.invoicetracker.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.bytebuddy.utility.RandomString;
import net.talaatharb.invoicetracker.exceptions.UserException;
import net.talaatharb.invoicetracker.models.ResetTokenEntity;
import net.talaatharb.invoicetracker.models.User;
import net.talaatharb.invoicetracker.repositories.ResetTokenRepository;
import net.talaatharb.invoicetracker.repositories.UserRepository;
import net.talaatharb.invoicetracker.utils.RegexHelper;

@Service
public class PasswordService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ResetTokenRepository resetTokenRepo;
    @Autowired
    private MailService mailService;

    @Value("${APPLICATION_URL:'http://localhost:3000'}")
    private String appUrl;
    
    private static final int FIVE_MINUTES = 5 * 60 * 1000;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void deleteUserToken(Long uid){
        resetTokenRepo.deleteById(uid);
    }

    @Transactional
    public void sendResetLink(String email) {
        // email validation
        if(email == null || !RegexHelper.testWithPattern(RegexHelper.EMAIL_PATTERN, email)){
            return;
//            throw new UserException("Something went wrong");
        }

        String token = RandomString.make(45);
        String resetLink = appUrl + "/reset-password/" + token;

        String mailSubject = "Reset password request";
        String mailBody = "<h2>Reset Password Request</h2>" +
                          "<p>Please visit <a href=\"" + resetLink + "\"><bold>this link</bold> </a> to reset your password </p>";

        Optional<User> userReturnedOptional = userRepo.findByEmail(email);
        if(userReturnedOptional.isEmpty()) {
            return;
//            throw new UserException("No user found with email " + email);
        }

        User userReturned = userReturnedOptional.get();
        ResetTokenEntity resetToken = new ResetTokenEntity();
        resetToken.setResetToken(token);
        resetToken.setExpTimeStamp(System.currentTimeMillis() + FIVE_MINUTES);
        resetToken.setUser(userReturned);

        if(userReturned.getResetToken() == null){
            userReturned.setResetToken(resetToken);
        }else{
            userReturned.getResetToken().setExpTimeStamp(System.currentTimeMillis() + FIVE_MINUTES);
            userReturned.getResetToken().setResetToken(token);
        }
        mailService.sendMail(email, mailSubject, mailBody);
    }

    @Transactional
    public void resetPassword(String resetToken, String newPassword) {

        UserException somethingWentWrong = new UserException("something went wrong");

        // token and password validation
        if(isInvalidTokenAndPassword(resetToken, newPassword)){
            throw somethingWentWrong;
        }

        Optional<ResetTokenEntity> resetTokenReturnedOptional = resetTokenRepo.findByResetToken(resetToken);

        if(resetTokenReturnedOptional.isEmpty()) {
            throw somethingWentWrong;
        }

        ResetTokenEntity resetTokenReturned = resetTokenReturnedOptional.get();

        if(System.currentTimeMillis() > resetTokenReturned.getExpTimeStamp()){
            throw new UserException("Enter Your Email Again");
        }

        User userReturned = resetTokenReturned.getUser();

        // using Password encoder bean to hash the new password
        String hashedPassword = passwordEncoder.encode(newPassword);

        userReturned.setPassword(hashedPassword);
        userReturned.setResetToken(null);
        userRepo.save(userReturned);
    }

	private boolean isInvalidTokenAndPassword(String resetToken, String newPassword) {
		return resetToken == null || !RegexHelper.testWithPattern(RegexHelper.NO_SPECIAL_CHARS_PATTERN,resetToken) || newPassword == null || !RegexHelper.testWithPattern(RegexHelper.PASSWORD_PATTERN, newPassword);
	}
}
