package auto.model;

import lombok.Data;

/**
 * @author taoxinglong
 * @description TODO
 * @date 2024-08-29 17:01
 */
@Data
public class Event {

    private String event;
    private String robot_wxid;
    private String robot_name;
    private String from_wxid;
    private String from_name;
    private String final_from_wxid;
    private String final_from_name;
    private String to_wxid;
    private String msgid;
    private String msg;
    private String group_wxid;
    private String cardPwd;
    private String inviteLink;

}
