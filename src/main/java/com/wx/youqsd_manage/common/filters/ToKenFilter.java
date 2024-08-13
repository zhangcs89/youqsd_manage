package com.wx.youqsd_manage.common.filters;

import cn.hutool.json.JSONObject;
import com.wx.youqsd_manage.common.exception.DefineException;
import com.wx.youqsd_manage.common.exception.ErrcodeStatus;
import com.wx.youqsd_manage.common.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author zhangchunsheng
 * @version 1.0.0
 * @className ToKenFilter
 * @date 2024/8/13   14:43
 * @description TODO
 */
@Component
public class ToKenFilter implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 2. 解码JWT令牌
        try {
            // 1. 从HTTP请求头部获取JWT令牌
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)){
                throw new DefineException(ErrcodeStatus.SC_UNAUTHORIZED);
            }
//            // 请替换下面的方法和密钥为你实际使用的JWT库和密钥
//            Claims claims = JwtUtils.analysisJWT(jwtToken);
//
//            // 3. 验证JWT令牌
//            // 3.1 签名验证已经在解码中完成
//            // 3.2 过期验证
//            Date expirationDate = claims.getExpiration();
//            Date now = new Date();
//            if (expirationDate.before(now)) {
//                // 令牌已过期，发送错误响应
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return false;
//            }

            // 3.3 权限验证，根据需要执行

        } catch (DefineException e) {
            // 令牌无效或解码失败，发送错误响应
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", e.getCode());
            jsonObject.put("msg", e.getMessage());
            response.getWriter().print(jsonObject);
            response.getWriter().close();
            return false;
        }

        // 验证通过，返回true，请求继续到达控制器方法
        return true;
    }

}