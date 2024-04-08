package GUI.Utility;


import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.apikeys.model.CreateApiKeyRequest;
import com.resend.services.apikeys.model.CreateApiKeyResponse;
import com.resend.services.emails.model.Attachment;
import com.resend.services.emails.model.SendEmailRequest;
import com.resend.services.emails.model.SendEmailResponse;
import com.resend.services.emails.model.Tag;

import java.util.Map;

public class EmailService {


    public static void main(String[] args) {
        Resend resend = new Resend("re_WpJpf4JZ_CAVKMDbNz8SSeGmdCfr5gk2u");

        Attachment att = Attachment.builder()
                .fileName("invoice.pdf")
                .content("invoiceBuffer")
                .build();

        Tag tag = Tag.builder()
                .name("category")
                .value("confirm_email")
                .build();

        SendEmailRequest sendEmailRequest = SendEmailRequest.builder()
                .from("Acme <onboarding@resend.dev>")
                .to("jesnie02@easv365.dk")
                //.attachments(att)
                .text("hello world")
                .subject("it works!")
                .headers(Map.of(
                        "X-Entity-Ref-ID", "123456789"
                ))
                .tags(tag)
                .build();

        try {
            SendEmailResponse data = resend.emails().send(sendEmailRequest);
        } catch (ResendException e) {
            throw new RuntimeException(e);
        }

    }


    public static void sendEmail() throws ResendException {
        Resend resend = new Resend("re_WpJpf4JZ_CAVKMDbNz8SSeGmdCfr5gk2u");
        SendEmailRequest firstEmailRequest = SendEmailRequest.builder()
                .from("Acme <onboarding@resend.dev>")
                .to("jesnie02@easv365.dk")
                .subject("gurli gris")

                .html("<h1>it works!</h1>")
                .build();
        try {
            SendEmailResponse data = resend.emails().send(firstEmailRequest);
        } catch (ResendException e) {
            throw new RuntimeException(e);
        }

    }

}
