package auto.service;

import auto.common.constants.CommonConstants;
import auto.common.constants.SmsConstants;
import auto.config.SmsConfig;
import auto.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author taoxinglong
 * @description TODO
 * @date 2024-08-30 12:35
 */
@Slf4j
@Service
public class SmsService {
    @Autowired
    private SmsConfig smsConfig;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 发送短信
     * @param mobile
     * @param templateId
     * @param content
     */
    public void send(String mobile, String templateId, String content) {
        long beginTime = CommonUtil.getCurrentTimestamp();
        String url = String.format(SmsConstants.TEMPLATE_URL, mobile, templateId, content);
        HttpHeaders headers = new HttpHeaders();
        headers.set(SmsConstants.AUTHORIZATION, SmsConstants.APPCODE + CommonConstants.BLANK +smsConfig.getAppCode());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Post调用，但是传参放在query中（url）
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        long endTime = CommonUtil.getCurrentTimestamp();
        log.info("cost = {} url = {}, body : {}", endTime-beginTime, url, response.getBody());

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("send sms success, url : {}, response body : {}", url, response.getBody());
        } else {
            log.error("send sms failed, url : {}, response body : {}", url, response.getBody());
            throw new RuntimeException("send sms failed!");
        }
    }
}
