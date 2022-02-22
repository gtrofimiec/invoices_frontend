package com.myprojects.invoices_frontend.domain.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.File;

public class MailDto {

    @JsonProperty("mailTo")
    private String mailTo;
    @JsonProperty("subject")
    private String subject;
    @JsonProperty("message")
    private String message;
    @JsonProperty("toCC")
    private String toCc;
    @JsonProperty("attachmentName")
    private String attachmentName;
    @JsonProperty("invoice")
    private File invoice;

    public MailDto() {
    }

    public MailDto(String mailTo, String subject, String message, String toCc, String attachmentName, File invoice) {
        this.mailTo = mailTo;
        this.subject = subject;
        this.message = message;
        this.toCc = toCc;
        this.attachmentName = attachmentName;
        this.invoice = invoice;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailTo() {
        return mailTo;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToCc(String toCc) {
        this.toCc = toCc;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public void setInvoice(File invoice) {
        this.invoice = invoice;
    }
}
