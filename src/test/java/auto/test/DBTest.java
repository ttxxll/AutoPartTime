package auto.test;

import auto.AutoPartTimeMain;
import auto.dao.ProductCardInviteLinkDao;
import auto.model.ProductCardInviteLink;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

/**
 * @author taoxinglong
 * @description TODO
 * @date 2024-08-29 23:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoPartTimeMain.class)
@Slf4j
public class DBTest {

    @Autowired
    private ProductCardInviteLinkDao productCardInviteLinkDao;

    @Test
    public void testDB() {
        List<ProductCardInviteLink> list =  productCardInviteLinkDao.selectList(
                new QueryWrapper<ProductCardInviteLink>().eq("id", "adasdasdas"));
        log.info(JSONObject.toJSONString(list));
    }

    @Test
    public void testInsertDB() {
        for (int i = 0; i < 1000; i++) {
            ProductCardInviteLink inviteLink = new ProductCardInviteLink();
            inviteLink.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            inviteLink.setStatus(1);
            int insert = productCardInviteLinkDao.insert(inviteLink);
        }
    }

}
