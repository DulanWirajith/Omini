package lk.dbay.service;

import lk.dbay.entity.DbayUser;

public interface SendEmailSMTP {

    void sendEmail(String emailTo, String emailSubject, String emailText) throws Exception;

    void sendRegistrationEmail(DbayUser dbayUser) throws Exception;
}
