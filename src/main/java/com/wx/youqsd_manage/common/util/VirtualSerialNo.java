package com.wx.youqsd_manage.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wx.youqsd_manage.common.response.SerialNoVO;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @ClassName VirtualSerialNo
 * @Description TODO
 * @Author zhangcs
 * @Date 2024/8/4 16:02
 * @Version 1.0
 */
public class VirtualSerialNo {

    public static <T> void generate(List<T> result) {
        generate(result, 1);
    }

    public static <T> IPage<T> generate(IPage<T> page) {
        generate(page.getRecords(), (int) ((page.getCurrent()-1) * page.getSize() + 1));
        return page;
    }

    private static <T> List<T> generate(List<T> result, Integer start) {
        if (! CollectionUtils.isEmpty(result)) {
            if (result.get(0) instanceof SerialNoVO) {
                for (Object vo : result)
                    ((SerialNoVO) vo).setSerialNo(start ++);
            }
        }
        return result;
    }
}
