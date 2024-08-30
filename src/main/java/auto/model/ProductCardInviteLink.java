package auto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author taoxinglong
 * @description TODO
 * @date 2024-08-29 23:48
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCardInviteLink implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String inviteLink;

    // 状态：0无效，1有效
    private Integer status;

    private String bizCode;

    private Date updateTime;

    private Date createTime;
}
