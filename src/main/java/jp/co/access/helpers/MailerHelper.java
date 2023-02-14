package jp.co.access.helpers;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import jp.co.access.request.mail.SendEmail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;

import static jp.co.access.utils.StringConstant.MAIL_ENDPOINT;
import static jp.co.access.utils.StringConstant.MAIL_TYPE;

/**
 * MailerHelper
 */
@Component
public class MailerHelper {

    @Value("${mail.noreply.address}")
    private String fromEmailAddress;

    private final SendGrid sendGrid;

    public MailerHelper(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    /**
     * Send email type html using SendGrid
     *
     * @param item item
     * @throws IOException
     */
    @Async
    public void sendEmailViaSendGrid(SendEmail item) throws IOException {
        Email from = new Email(fromEmailAddress);
        Email to = new Email(item.getTo());
        Content content = new Content(MAIL_TYPE, item.getBody());
        Mail mail = new Mail(from, item.getSubject(), to, content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint(MAIL_ENDPOINT);
        request.setBody(mail.build());

        sendGrid.api(request);
    }

    /**
     * Send bulk email type html using SendGrid
     *
     * @param item item
     * @throws IOException
     */
    public void sendBulkEmailViaSendGrid(SendEmail item) throws IOException {
        if (CollectionUtils.isEmpty(item.getTos())) {
            return;
        }

        Mail mail = new Mail();
        // from
        Email from = new Email(fromEmailAddress);
        mail.setFrom(from);

        // bulk
        Personalization personalization = new Personalization();
        item.getTos().forEach(mailItem -> {
            personalization.addTo(new Email(mailItem));
        });
        mail.addPersonalization(personalization);

        // Subject
        mail.setSubject(item.getSubject());

        // Content
        Content content = new Content(MAIL_TYPE, item.getBody());
        mail.addContent(content);

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint(MAIL_ENDPOINT);
        request.setBody(mail.build());

        sendGrid.api(request);
    }
}
