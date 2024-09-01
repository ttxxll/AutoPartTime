package auto.service;

import auto.common.BaseResult;
import auto.controller.request.ProductCardInviteLinkPageReq;
import auto.dao.ProductCardInviteLinkDao;
import auto.model.ProductCardInviteLink;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author taoxinglong
 * @description TODO
 * @date 2024-08-30 15:44
 */
@Slf4j
@Service
public class ProductCardInviteLinkService {

    @Autowired
    private ProductCardInviteLinkDao productCardInviteLinkDao;

    public Map<String, Object> page(ProductCardInviteLinkPageReq pageReq) {
        Page<ProductCardInviteLink> pageInfo = new Page<>(pageReq.getPage(), pageReq.getSize());
        QueryWrapper<ProductCardInviteLink> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(pageReq.getId())) {
            queryWrapper.eq("id", pageReq.getId());
        }
        if (StringUtils.isNotBlank(pageReq.getStatus())) {
            queryWrapper.eq("status", pageReq.getStatus());
        }
        IPage<ProductCardInviteLink> orderDOIPage = productCardInviteLinkDao.selectPage(pageInfo, queryWrapper);
        log.info("ProductCardInviteLinkService page success! list = {}, ", JSONObject.toJSONString(orderDOIPage));
        List<ProductCardInviteLink> orderDOIPageRecords = orderDOIPage.getRecords();
        Map<String, Object> pageMap = new HashMap<>(2);
        pageMap.put("total_record", orderDOIPage.getTotal());
        pageMap.put("total_page", orderDOIPage.getPages());
        pageMap.put("current_data", orderDOIPageRecords);
        return pageMap;
    }

    public BaseResult upload(MultipartFile file) {
        log.info("ProductCardInviteLinkService upload begin");
        boolean isExcel = checkExcel(file);
        if (!isExcel) {
            return BaseResult.buildError("上传文件不是excel！");
        }
        return null;
    }

    /**
     * 检查文件
     */
    public static boolean checkExcel(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if (!fileName.endsWith("xls") && !fileName.endsWith("xlsx")) {
            return false;
        }
        return true;
    }

    public void export(ProductCardInviteLinkPageReq pageReq, HttpServletResponse response) {
        QueryWrapper<ProductCardInviteLink> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(pageReq.getId())) {
            queryWrapper.eq("id", pageReq.getId());
        }
        if (StringUtils.isNotBlank(pageReq.getStatus())) {
            queryWrapper.eq("status", pageReq.getStatus());
        }
        List<ProductCardInviteLink> data = productCardInviteLinkDao.selectList(queryWrapper.orderByAsc("update_time"));
        data.forEach(obj -> obj.setId("http://122.51.106.147/?card="+obj.getId()));
        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=卡密.xlsx");

        // 使用 EasyExcel 写入数据到输出流
        try {
            EasyExcel.write(response.getOutputStream(), ProductCardInviteLink.class)
                    .sheet("卡密")
                    .doWrite(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
