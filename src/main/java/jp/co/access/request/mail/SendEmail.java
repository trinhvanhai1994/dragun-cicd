package jp.co.access.request.mail;

import lombok.Data;

import java.util.List;

/**
 * SendEmail
 */
@Data
public class SendEmail {

    private String to;
    private String subject;
    private String body;
    private List<String> tos;
    private List<String> ccs;

    public SendEmail(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public SendEmail(List<String> tos, String subject, String body, List<String> ccs) {
        this.tos = tos;
        this.subject = subject;
        this.body = body;
        this.ccs = ccs;
    }

    public SendEmail(List<String> tos, String subject, String body) {
        this.tos = tos;
        this.subject = subject;
        this.body = body;
    }

    public SendEmail(List<String> tos, List<String> ccs, String subject, String body) {
        this.tos = tos;
        this.ccs = ccs;
        this.subject = subject;
        this.body = body;
    }
}
