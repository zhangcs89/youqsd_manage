package com.wx.youqsd_manage.vo.resp;

import com.wx.youqsd_manage.entity.ShopInfo;
import lombok.Data;

/**
 * @ClassName ShopInfoPageResp
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 15:55
 * @Version 1.0
 */
@Data
public class ShopInfoPageResp extends ShopInfo {

    private boolean isReg;

    public boolean isReg() {
        return false;
    }
}
