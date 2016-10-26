package com.shangpin.web.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.NestedServletException;

import com.shangpin.web.constant.ErrorConstant;

import lombok.extern.slf4j.Slf4j;


/**
 * 全局异常处理逻辑
 */
@ControllerAdvice
@Slf4j
public class WebExceptionConfiguration {
    private static final String EXCEPTION_VIEW = "error/exception";

    /**
     * 处理业务异常
     *
     * @param e
     * @return
     * @throws Exception
     *//*
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView  handleBusinessException(AbstractBusinessException e) throws Exception {
        logger.info(e.getMessage());
        ModelAndView model = new ModelAndView();
        model.addObject(SessionConstant.ERROR_MSG, e.getMessage());
        model.setViewName("exception");
        return model;
    }*/

    /**
     * 处理系统异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView exception(Throwable e) throws Exception {
        log.error(e.getMessage(), e);
        ModelAndView model = new ModelAndView();
        model.addObject(ErrorConstant.ERROR_MSG, e);
        model.addObject(ErrorConstant.ERROR_CLASS, e.getStackTrace()[0]);
        model.setViewName(EXCEPTION_VIEW);
        return model;
    }
    /**
     * facade请求后台dubbo服务超时
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView nestedServletException(NestedServletException e) throws Exception{
    	log.error(e.getMessage(), e);
        ModelAndView model = new ModelAndView();
        model.addObject(ErrorConstant.ERROR_MSG, "RPC调用超时");
        model.setViewName(EXCEPTION_VIEW);
        return model;
    }
    /**
     * 请求参数不对,缺少请求参数
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView nestedServletException(MissingServletRequestParameterException e) throws Exception{
    	log.error(e.getMessage(), e);
    	ModelAndView model = new ModelAndView();
    	model.addObject(ErrorConstant.ERROR_MSG, "缺少请求参数");
    	model.setViewName(EXCEPTION_VIEW);
    	return model;
    }
    /**
     * rest服务不可用
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.OK)
    public ModelAndView resourceAccessException(ResourceAccessException e) throws Exception{
    	log.error(e.getMessage(), e);
    	ModelAndView model = new ModelAndView();
        model.addObject(ErrorConstant.ERROR_MSG, "REST调用超时");
        model.setViewName(EXCEPTION_VIEW);
        return model;
    }
    
}
