package cn.congee.api.interceptor;

import cn.congee.api.annotation.RequestLimit;
import cn.congee.api.common.JsonResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 拦截器
 *
 * @Author: yang
 * @Date: 2020-12-14 12:08
 */
@Component
public class RequestLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            RequestLimit requestLimit = hm.getMethodAnnotation(RequestLimit.class);
            if(null == requestLimit){
                return true;
            }
            long times = requestLimit.time();
            int counts = requestLimit.count();
            String ip=request.getRemoteAddr();
            String key = request.getServletPath() + ":" + ip ;
            Integer count = (Integer) redisTemplate.opsForValue().get(key);

            if (null == count || -1 == count) {
                redisTemplate.opsForValue().set(key, 1, times, TimeUnit.SECONDS);
                return true;
            }

            if (count < counts) {
                count = count+1;
                redisTemplate.opsForValue().set(key, count,0);
                return true;
            }

            if (count >= counts) {
//                response 返回 json 请求过于频繁请稍后再试
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                JsonResult result = new JsonResult<>();
                result.setCode(9999);
                result.setMessage("操作过于频繁");
                Object obj = JSONObject.toJSON(result);
                response.getWriter().write(JSONObject.toJSONString(obj));
                return false;
            }
        }
        return true;
    }

}
