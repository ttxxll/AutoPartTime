package auto.common.constants;

public interface SmsConstants {

    public static final String AUTHORIZATION = "Authorization";

    // 固定是APPCODE
    public static final String APPCODE = "APPCODE";

    /**
     * 调用地址，模板url
     */
    public static final String TEMPLATE_URL = "https://jmsms.market.alicloudapi.com/sms/send?mobile=%s&templateId=%s&value=%s";
}
