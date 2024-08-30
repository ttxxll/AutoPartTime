package auto.controller.request;

import lombok.Data;

/**
 * @author taoxinglong
 * @description TODO
 * @date 2024-08-30 15:47
 */
@Data
public class ProductCardInviteLinkPageReq {

    /**
     * 状态
     */
    private String status;

    private String id;

    /**
     * 第几页
     */
    private int page;

    /**
     * 每页多少条
     */
    private int size;
}
