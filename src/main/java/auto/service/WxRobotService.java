package auto.service;

import auto.common.BaseResult;
import auto.common.constants.CardLinkStatus;
import auto.common.constants.EventType;
import auto.common.constants.WxID;
import auto.config.SmsConfig;
import auto.dao.ProductCardInviteLinkDao;
import auto.model.Event;
import auto.model.ProductCardInviteLink;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WxRobotService {

    private final static String URL_SEND = "http://officially-talented-kitten.ngrok-free.app/send";

    private final RateLimiter rateLimiter = RateLimiter.create(50);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SmsService smsService;

    @Autowired
    private SmsConfig smsConfig;

    @Autowired
    private ProductCardInviteLinkDao productCardInviteLinkDao;

    public BaseResult sendTextMsg(Event event) {

        try {
            log.info("sendTextMsg start : {}", JSONObject.toJSONString(event));
            boolean acquire = rateLimiter.tryAcquire();
            if (!acquire) {
                log.error("sendTextMsg 流量触发阈值！");
                Map<String, String> alertBody = buildMsgBody(EventType.SendTextMsg, WxID.YYL_ROBOT, WxID.TXL, "流量触发阈值！");
                restTemplate.exchange(URL_SEND, HttpMethod.POST, new HttpEntity<>(alertBody), String.class);
                return BaseResult.buildError("流量触发阈值，请检查重试！");
            }

            ProductCardInviteLink inviteLink = productCardInviteLinkDao.selectOne(new QueryWrapper<ProductCardInviteLink>().eq("id", event.getCardPwd()));
            if (inviteLink == null) {
                return BaseResult.buildError("卡密不存在！");
            }
            if (inviteLink.getStatus().equals(CardLinkStatus.invalid.getStatus())) {
                return BaseResult.buildError("卡密已使用！");
            }

            // 发给做单人
            Map<String, String> body = buildMsgBody(event.getEvent(), event.getRobot_wxid(), event.getTo_wxid(), event.getMsg());
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(body);
            ResponseEntity<String> response = restTemplate.exchange(URL_SEND, HttpMethod.POST, entity, String.class);
            if (response.getStatusCodeValue() != 200) {
                log.error("sendTextMsg failed!: {}", JSONObject.toJSONString(response.getBody()));
                // 再发送一次
//                response = restTemplate.exchange(URL_SEND, HttpMethod.POST, entity, String.class);
//                if (response.getStatusCodeValue() != 200) {
//                    // 还是不行 短信告警
//                    smsService.send("18375324908", smsConfig.getTemplateId(),"000000");
//                    return BaseResult.buildError("Wx消息发送失败！");
//                }
            }
            log.info("sendTextMsg success! param = {}, res = {}", JSONObject.toJSONString(event), JSONObject.toJSONString(response.getBody()));

            int update = productCardInviteLinkDao.update(null, new UpdateWrapper<ProductCardInviteLink>().
                    eq("id", event.getCardPwd()).
                    eq("status", CardLinkStatus.valid.getStatus()).
                    set("status", CardLinkStatus.invalid.getStatus()).
                    set("invite_link", event.getInviteLink()).
                    set("update_time", new Date()));
            // 更新失败：WX提醒
            if (update != 1) {
                Map<String, String> alertBody = buildMsgBody(EventType.SendTextMsg, WxID.YYL_ROBOT, WxID.TXL, "数据库更新失败！表：product_card_invite_link，id：" + event.getCardPwd());
                restTemplate.exchange(URL_SEND, HttpMethod.POST, new HttpEntity<>(alertBody), String.class);
                log.error("update productCardInviteLink fail! inviteLink : {}", event.getCardPwd());
            }

            // 更新成功 结束
            log.info("update productCardInviteLink success! event : {}", JSONObject.toJSONString(event));
            return BaseResult.buildSuccess();
        } catch (Exception e) {
            log.error("sendTextMsg occur error! param : {}, e : ", JSONObject.toJSONString(event), e);
            return BaseResult.buildError(e.getMessage());
        }
    }

    private Map<String, String> buildMsgBody(String event, String from, String to, String msg) {
        Map<String, String> body = new HashMap<>();
        body.put("event", event);
        body.put("to_wxid", to);
        body.put("msg", msg);
        body.put("robot_wxid", from);
        return body;
    }

}
