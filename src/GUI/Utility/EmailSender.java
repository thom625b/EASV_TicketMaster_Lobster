package GUI.Utility;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.Attachment;
import com.resend.services.emails.model.SendEmailRequest;
import com.resend.services.emails.model.SendEmailResponse;
import com.resend.services.emails.model.Tag;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Properties;



public class EmailSender {


    private static final String PROP_FILE = "config/config.settings";
    private final Resend resend;

    public EmailSender() throws IOException {
        Properties properties = loadProperties();
        String apiKey = properties.getProperty("api.key");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalStateException("API key is not specified in the properties file.");
        }
        this.resend = new Resend(apiKey);
    }


    private static Properties loadProperties() {
        try (FileInputStream fis = new FileInputStream(PROP_FILE)) {
            Properties properties = new Properties();
            properties.load(fis);
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load properties file: " + PROP_FILE, e);
        }
    }

    public boolean sendEmail(String recipient, String subject, String content, Attachment attachment) throws ResendException {
        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .from("EASV Event <EASV@leet.dk>")
                .to(recipient)
                .subject(subject)
                .html(content)
                .addAttachment(attachment)
                .build();


        try {
            SendEmailResponse data = resend.emails().send(sendEmailRequest);
            return true;
        } catch (ResendException e) {
            throw new ResendException("Error occurred trying to send email:\n" + e);
        }
    }


    public boolean sendTicket(String recipient, String subject, String content, Path pdfPath) throws IOException, ResendException {
        byte[] pdfBytes = Files.readAllBytes(pdfPath);
        String base64Pdf = Base64.getEncoder().encodeToString(pdfBytes);

        Attachment attachment = Attachment.builder()
                .fileName(pdfPath.getFileName().toString())
                .content(base64Pdf)
                .build();

        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .from("EASV Event <EASV@leet.dk>")
                .to(recipient)
                .subject(subject)
                .text(content)
                .attachments(List.of(attachment))
                .build();

        SendEmailResponse data = resend.emails().send(sendEmailRequest);

        return true;
    }



}
