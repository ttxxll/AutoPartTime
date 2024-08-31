package auto.model;

import com.alibaba.excel.annotation.ExcelProperty;
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

    @ExcelProperty("卡密")
    private String id;

    @ExcelProperty("邀请链接")
    private String inviteLink;

    // 状态：0无效，1有效
    @ExcelProperty("状态")
    private Integer status;

    @ExcelProperty("业务")
    private String bizCode;

    @ExcelProperty("更新时间")
    private Date updateTime;

    @ExcelProperty("创建时间")
    private Date createTime;
}
