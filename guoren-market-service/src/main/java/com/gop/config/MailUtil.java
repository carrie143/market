///*
// * 文件名：MailUtil.java 版权：Copyright by www.huawei.com 描述：用户反馈信息 修改人：tjy 修改时间：2016年1月4日 跟踪单号： 修改单号：
// * 修改内容：
// */
//
//package com.gop.config;
//
//
//import java.util.Properties;
//import java.util.ResourceBundle;
//
//import javax.mail.Authenticator;
//import javax.mail.Message.RecipientType;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//
///**
// * 用户反馈发送邮件
// * 
// * @author lwq
// */
//public class MailUtil
//{
//
//    public static void send(Integer uid, String context)
//        throws MessagingException
//    {
//        // 配置发送邮件的环境属性
//        final Properties props = new Properties();
//
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("mail");
//        String Mail_Smtp_Starttls_Enable = resourceBundle.getString("Mail_Smtp_Starttls_Enable");
//        String Mail_Smtp_Auth = resourceBundle.getString("Mail_Smtp_Auth");
//        String Mail_Smtp_Host = resourceBundle.getString("Mail_Smtp_Host");
//        String Mail_From_User = resourceBundle.getString("Mail_From_User");
//        String Mail_Password = resourceBundle.getString("Mail_Password");
//        String Mail_To_User = resourceBundle.getString("Mail_To_User");
//
//        props.put("mail.smtp.starttls.enable", Mail_Smtp_Starttls_Enable);
//        // 表示SMTP发送邮件，需要进行身份验证
//        props.put("mail.smtp.auth", Mail_Smtp_Auth);
//        props.put("mail.smtp.host", Mail_Smtp_Host);
//        // 发件人的账号
//        props.put("mail.user", Mail_From_User);
//        // 访问SMTP服务时需要提供的密码
//        props.put("mail.password", Mail_Password);
//
//        // 构建授权信息，用于进行SMTP进行身份验证
//        Authenticator authenticator = new Authenticator()
//        {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication()
//            {
//                // 用户名、密码
//                String userName = props.getProperty("mail.user");
//                String password = props.getProperty("mail.password");
//                return new PasswordAuthentication(userName, password);
//            }
//        };
//        // 使用环境属性和授权信息，创建邮件会话
//        Session mailSession = Session.getInstance(props, authenticator);
//        // mailSession.setDebug(true);
//        // 创建邮件消息
//        MimeMessage message = new MimeMessage(mailSession);
//        // 设置发件人
//        InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
//        message.setFrom(form);
//
//        // 设置收件人
//        InternetAddress to = new InternetAddress(Mail_To_User);
//        message.setRecipient(RecipientType.TO, to);
//
//        // 设置抄送
//        // InternetAddress cc = new InternetAddress("zhengminghai@new4g.cn");
//        // message.setRecipient(RecipientType.CC, cc);
//
//        // 设置密送，其他的收件人不能看到密送的邮件地址
//        // InternetAddress bcc = new InternetAddress("aaaaa@163.com");
//        // message.setRecipient(RecipientType.CC, bcc);
//
//        // 设置邮件标题
//        message.setSubject("用户反馈邮件");
//
//        String str = "用户编号(" + uid + ")反馈的的内容如下：\n" + context;
//
//        // 设置邮件的内容体
//        message.setContent(str, "text/html;charset=UTF-8");
//
//        System.out.println("开始发送邮件...start");
//        // 发送邮件
//        Transport.send(message);
//
//        System.out.println("邮件发送成功...success");
//    }
//}