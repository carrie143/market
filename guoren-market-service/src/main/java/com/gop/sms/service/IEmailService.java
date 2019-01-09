package com.gop.sms.service;

import com.gop.sms.dto.EmailDto;

/**
 * @author yujianjian
 * @since 2017-09-11 下午2:32
 */
public interface IEmailService {


    /**
     * 发送简单邮件
     */
    boolean sendSimpleMail(EmailDto emailDto);

    /**
     * 发送带附件的邮件
     */
    boolean sendAttachmentsMail(EmailDto emailDto);

}