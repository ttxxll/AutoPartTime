package auto.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author taoxinglong
 * @description TODO
 * @date 2024-08-30 12:35
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sms")
public class SmsConfig {

    // 短信正文ID（模板ID）
    private String templateId;

    private String appCode;
}
