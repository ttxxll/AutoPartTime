package auto.controller;

import auto.common.BaseResult;
import auto.controller.request.ProductCardInviteLinkPageReq;
import auto.service.ProductCardInviteLinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author taoxinglong
 * @description TODO
 * @date 2024-08-30 15:43
 */
@Slf4j
@RestController
@RequestMapping("/card/invite/link")
public class ProductCardInviteLinkController {

    @Autowired
    private ProductCardInviteLinkService productCardInviteLinkService;

    /**
     * 分页接口
     * @return
     */
    @PostMapping("/page")
    public BaseResult page(@RequestBody ProductCardInviteLinkPageReq pageReq) {
        try {
            Map<String, Object> pageResult = productCardInviteLinkService.page(pageReq);
            return BaseResult.buildSuccess(pageResult);
        } catch (Exception e) {
            log.error("ProductCardInviteLinkController page error!", e);
            return BaseResult.buildError(e.getMessage());
        }
    }

    @PostMapping("/upload")
    public BaseResult upload(@RequestParam("file") MultipartFile file) {
        try {
            BaseResult result = productCardInviteLinkService.upload(file);
            return result;
        } catch (Exception e) {
            log.error("ProductCardInviteLinkController upload error!", e);
            return BaseResult.buildError(e.getMessage());
        }
    }

    @PostMapping("/export")
    public void export(@RequestBody ProductCardInviteLinkPageReq pageReq, HttpServletResponse response) {
        try {
            productCardInviteLinkService.export(pageReq, response);
        } catch (Exception e) {
            log.error("ProductCardInviteLinkController export error!", e);
        }
    }

}
