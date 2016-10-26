package com.shangpin.web.controller.order;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.shangpin.biz.bo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.shangpin.biz.bo.Logistics;
import com.shangpin.biz.bo.OrderLogistics;
import com.shangpin.biz.service.SPBizLogisticeService;
import com.shangpin.biz.service.SPBizOrderService;
import com.shangpin.biz.utils.ClientUtil;
import com.shangpin.biz.utils.StringUtil;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;

/**
 * @ClassName: LogisticeController
 * @Description: 物流跟踪
 * @author liling
 * @date 2014年11月29日
 * @version 1.0
 */
@Controller
@RequestMapping("/logistice")
public class LogisticeController extends BaseController {

	public static Logger logger = LoggerFactory.getLogger(LogisticeController.class);


	private static final String LIST = "logistice/list";
	@Autowired
	private SPBizLogisticeService logisticeService;


	/**
	 *
	 * @Title: list
	 * @Description:物流跟踪列表页
	 * @param
	 * @return String
	 * @throws
	 * @Create By liushaoqing
	 * @Create Date 2016年08月11日
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String logisticList(@RequestParam("orderId") String orderId,
							   @RequestParam(value = "index", required = false,defaultValue = "0") String index,
							   @RequestParam(value = "ticketNo", required = false,defaultValue = "") String ticketNo,
							   @RequestParam(value = "postArea", defaultValue = "1", required = false) String postArea,
									 HttpServletRequest request, Model model) {
		// 兼容客户端请求
		String userId = request.getParameter("userId");
		User user = getSessionUser(request);
		if (!StringUtil.isNotEmpty(userId)) {
			userId = user.getUserId();
		}
		if(!StringUtil.isNotEmpty(postArea)||postArea.equals("null")){
			postArea="1";
		}
		logger.info("logistic userId:"+userId);
		try {
			OrderLogistics orderLogistics = logisticeService.getLogisticV3(orderId, userId);
			List<Logistics> list = orderLogistics.getList();
			if (list != null && list.size() > 0) {

				if (StringUtil.isNotEmpty(ticketNo)) {
					for (int i = 0; i < list.size(); i++) {
						String tickNoTemp = list.get(i).getTicketno();
						if (tickNoTemp.equals(ticketNo)) {
							orderLogistics.setLogistics(list.get(i));
							orderLogistics.setIndex(i);
							break;
						}
				}
				} else {
					if(StringUtil.isNotEmpty(index)){
						for (int i = 0; i < list.size(); i++) {
							if (Integer.valueOf(index)==i) {
								orderLogistics.setLogistics(list.get(i));
								orderLogistics.setIndex(i);
								break;
							}
						}
					}
				}
			}
			model.addAttribute("orderId", orderId);
			model.addAttribute("orderLogistics", orderLogistics);
			model.addAttribute("postArea", postArea);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LIST;
	}

}
