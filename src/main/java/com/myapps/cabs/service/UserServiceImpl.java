package com.myapps.cabs.service;

import com.myapps.cabs.exception.UserException;
import com.myapps.cabs.model.Address;
import com.myapps.cabs.model.Organization;
import com.myapps.cabs.model.User;
import com.myapps.cabs.model.UserAddress;
import com.myapps.cabs.repository.AddressRepository;
import com.myapps.cabs.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.random.RandomGenerator;

import static com.myapps.cabs.constants.Constants.INVALID_USER;

@Service
public class UserServiceImpl implements UserService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public UserServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    private final Map<String, String> otpValidation = new HashMap<>();

    @Value("${geocoding.api.key}")
    private String geocodingApiKey;

    @Override
    public boolean authenticateUser(User user) {
        try {
            isValidEmail(user.getUserEmail(), user.getAccessCode());
            String kerberosId = getUser(user.getUserEmail(), user.getAccessCode());
            user.setKerberosId(kerberosId);
            user.setId(UUID.randomUUID().toString());
            sendAuthenticationMail(user.getUserEmail());

            userRepository.save(user);
            return true;
        } catch (UserException exception) {
            throw new UserException(INVALID_USER, exception);
        }
    }

    @Override
    public boolean processOtp(String otp, String email) {
        if (otpValidation.containsKey(email)) {

            for (Map.Entry<String, String> value : otpValidation.entrySet()) {
                if (value.getKey().equalsIgnoreCase(email)) {
                    return value.getValue().equalsIgnoreCase(otp);
                }
            }
        }
        return false;
    }

    @Override
    public boolean saveUserLocation(UserAddress address) {
        try {
            String kerberosId = address.getKerberosId();
            User userObj = userRepository.findByKerberosId(kerberosId);
            if (!address.getAddresses().isEmpty()) {
                address.setKerberosId(userObj.getKerberosId());
                for (Address adr : address.getAddresses()) {
                    adr.setType(adr.getType().toUpperCase());
                    adr.setCoordinates(getAddressCoordinates(adr.getDescription()));
                }
            }
            addressRepository.save(address);
            return true;
        } catch (UserException | IOException exp) {
            throw new UserException("", exp.getCause());
        }
    }

    private String getAddressCoordinates(String description) throws IOException {
        String location = URLEncoder.encode(description, StandardCharsets.UTF_8);
        URL url = new URL("https://api.geoapify.com/v1/geocode/search?name=" + location + "&format=json&apiKey=" + geocodingApiKey);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("Accept", "application/json");
        String value = http.getResponseMessage();
        http.disconnect();
        return value;
    }

    private Boolean isValidEmail(String userEmail, String accessCode) {
        return userEmail.contains(Organization.valueOf(accessCode).getValidStr());
    }

    private String getUser(String email, String accessCode) {

        String[] string = email.split("@");
        String firstName = string[0].split("\\.")[0].substring(0, 3);
        String lastName = string[0].split("\\.")[1];

        return firstName + lastName;
    }

    private Boolean sendAuthenticationMail(String userEmail) {
        try {
            String OTP = generateToken();
            otpValidation.put(userEmail, OTP);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");
            mimeMessageHelper.setFrom("vasanmit@gmail.com");
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject("Cabs Verification Code");
            mimeMessageHelper.setText("Your Cabs Verification Code is - " + OTP);

            javaMailSender.send(mimeMessage);
            return true;
        } catch (MessagingException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    private String generateToken() {

        StringBuilder token = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int str = RandomGenerator.getDefault().nextInt(9);
            token.append(str);
        }
        return token.toString();
    }

}
