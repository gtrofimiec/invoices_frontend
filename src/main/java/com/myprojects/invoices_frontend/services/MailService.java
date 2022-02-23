package com.myprojects.invoices_frontend.services;

import com.myprojects.invoices_frontend.clients.MailClient;
import com.myprojects.invoices_frontend.domain.dtos.MailDto;
import com.myprojects.invoices_frontend.layout.dialogboxes.ShowNotification;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private static MailService mailService;
    private static MailClient mailClient;

    public MailService(MailClient mailClient) {
        MailService.mailClient = mailClient;
    }

    public static MailService getInstance() {
        if(mailService == null) {
            mailService = new MailService(mailClient);
        }
        return mailService;
    }

    public void sendMail(MailDto mailDto) {
        ShowNotification isMailSent = new ShowNotification();
        isMailSent.setDuration(5000);
        if(mailClient.sendMail(mailDto)) {
            isMailSent.setMessage("E-mail z fakturą został wysłany");
        } else {
            isMailSent.setMessage("Podczas wysyłania maila wystapił błąd." +
                    " Sprawdź czy podano poprawny adres e-mail");
        }
        isMailSent.show();
    }
}
