package top.myjnxj.generator.service;

/**
 * @InterfaceName MailService
 * @Description 邮件服务
 * @Author JXNJ
 * @Date 2019/1/21 14:00
 * @Version 1.0
 **/
public interface MailService {
    /**
     * 发送邮件
     * @param to 邮箱目的地址
     * @param subject 主题
     * @param content 内容
     */
    void sendMail(String to, String subject, String content);
}
