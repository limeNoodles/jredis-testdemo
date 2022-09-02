package com.lime.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenIntecptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //允许跨域的主机ip
        response.setHeader("Access-Control-Allow-Origin","*");
        //允许访问的方法
        response.setHeader("Access-Control-Allow-Method","*");
        //配置cors的缓存，多少秒之后才重发一次预检
        response.setHeader("Access-Control-Max-Age","3600");
        //设置请求头允许传递的参数
        response.setHeader("Access-Control-Allow-Headers","*");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
/*        //开始校验token
        String token=request.getHeader("token");
        boolean isOK=false;
        String Method=request.getMethod();
        String path=request.getServletPath();

        if(path.indexOf("doLogin")!=-1||Method.equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            isOK=true;
        }else {
            if(token==null||token.isEmpty()){
                throw new RuntimeException("没有登录");
            }else {
                Object obj=request.getSession().getServletContext().getAttribute(token);
                if(obj!=null){
                    isOK=true;
                }else {
                    throw new RuntimeException("登录异常");
                }
            }
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
