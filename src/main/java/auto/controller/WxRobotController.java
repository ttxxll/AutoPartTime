package auto.controller;

import auto.common.BaseResult;
import auto.model.Event;
import auto.service.WxRobotService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/wx")
@CrossOrigin(origins = "http://122.51.106.147")
public class WxRobotController {

    @Autowired
    private WxRobotService wxRobotService;

    @PostMapping(path = "/send/callback")
    public BaseResult sendCallback(HttpServletRequest request, @RequestBody String body){
        try {
            Event event = JSONObject.parseObject(body, Event.class);
            log.info("event : {}", JSONObject.toJSONString(event));
            return BaseResult.buildSuccess();
        } catch (Exception e) {
            return BaseResult.buildError(e.getMessage());
        }
    }

    @PostMapping(path = "/send/text/msg")
    public BaseResult sendTextMsg(HttpServletRequest request, @RequestBody Event event){
        try {
            if (event == null || StringUtils.isAnyBlank(event.getEvent(), event.getRobot_wxid(), event.getTo_wxid(), event.getMsg(), event.getCardPwd(), event.getInviteLink())) {
                return BaseResult.buildError("参数异常");
            }
            return wxRobotService.sendTextMsg(event);
        } catch (Exception e) {
            return BaseResult.buildError(e.getMessage());
        }
    }

}
