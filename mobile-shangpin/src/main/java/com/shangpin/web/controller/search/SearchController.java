package com.shangpin.web.controller.search;

import com.google.gson.reflect.TypeToken;
import com.shangpin.biz.bo.*;
import com.shangpin.biz.bo.base.ResultObjOne;
import com.shangpin.biz.bo.base.ResultSuggestion;
import com.shangpin.biz.service.SPBizSearchService;
import com.shangpin.biz.service.SPBizStarPacketService;
import com.shangpin.utils.JsonUtil;
import com.shangpin.utils.StringUtil;
import com.shangpin.web.constant.Constants;
import com.shangpin.web.controller.BaseController;
import com.shangpin.web.utils.SearchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/search")
public class SearchController extends BaseController{
	 private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	private static final String FLAGSHOP_SEARCH = "flagshop/search";
	
	private static final String SEARCH_INDEX = "/search/index";
	
	private static final String SEARCH_LIST ="/search/search_list";
	
	private static String defaultCategoryNo;
	private static String defaultCategoryName;
	
	
	 private static Map<String, Object> searchFacet = new HashMap<String, Object>();
	@Autowired
	SPBizSearchService searchService;
	
	@Autowired
	private SPBizSearchService spBizSearchService;
	@Autowired
	private SPBizStarPacketService spBizStarPacketService;

	@RequestMapping(value = "/flagshopProduct/list", method = { RequestMethod.GET, RequestMethod.POST })
	
	public String flagshopBrandProduct(SearchConditions searchConditions, Model model, HttpServletRequest request){
		User user = getSessionUser(request);
		String userLv = StringUtils.isEmpty(user) ? null : user.getLv();
		String start = ( StringUtils.isEmpty(searchConditions.getStart() )) ? "1" : searchConditions.getStart();
		SearchProductResult searchResult = searchService.searchProductList(searchConditions.getKeyword(),null, start, Constants.PRODUCT_LIST_END, searchConditions.getTagId(),searchConditions.getBrandNo(), searchConditions.getPrice(), searchConditions.getColor(), searchConditions.getSize(), searchConditions.getCategoryNo(), searchConditions.getOrder(), userLv,SearchType.PRODUCT, searchConditions.getPostArea());
		int hasMore = SearchUtil.hasMore(Integer.parseInt(searchResult.getCount()),Integer.parseInt(start), Integer.parseInt(Constants.PRODUCT_LIST_END));
		model.addAttribute("keyword", searchConditions.getKeyword());
		model.addAttribute("searchResult", searchResult);
		model.addAttribute("start", start);
		
		model.addAttribute("hasMore", hasMore);
		model.addAttribute("userLv", userLv);
		return FLAGSHOP_SEARCH;
	}
	@RequestMapping(value = "/flagshopProduct/list/more", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getflagshopProductMore(SearchConditions searchConditions, HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		User user = getSessionUser(request);
		String userLv = StringUtils.isEmpty(user) ? null : user.getLv();
		String start = searchConditions.getStart() ;
		SearchProductResult searchResult = searchService.searchProductList(searchConditions.getKeyword(),null, start, Constants.PRODUCT_LIST_END,searchConditions.getTagId(), searchConditions.getBrandNo(), searchConditions.getPrice(), searchConditions.getColor(), searchConditions.getSize(), searchConditions.getCategoryNo(), searchConditions.getOrder(), userLv,SearchType.PRODUCT, searchConditions.getPostArea());
		int hasMore = SearchUtil.hasMore(Integer.parseInt(searchResult.getCount()),Integer.parseInt(start), Integer.parseInt(Constants.PRODUCT_LIST_END));
		
		map.put("hasMore", hasMore);
		map.put("searchResult", searchResult);
		map.put("userLv", userLv);
		return map;
	}
	/**
	 * 进入搜索页初始化值
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "index")
	public String index(Model model,HttpServletRequest request)throws Exception{
		request.setCharacterEncoding("utf-8");
		SearchKeyword searchKeyword = spBizSearchService.getSearchKeyword();
		ArrayList<Object> searkey=new ArrayList<>();
		ArrayList<Object> searkeys=new ArrayList<>();
		Cookie cookies[] = ((HttpServletRequest) request).getCookies();
		 if (cookies!=null) {
			 for (Cookie uaasCookie : cookies) {
				 if (uaasCookie.getName().contains("searKey")) {
					 searkey.add(URLDecoder.decode(uaasCookie.getValue()));
				 }
			 }
		}
		 //取最新添加的10条
		 for(int i = searkey.size(); (i>=searkey.size()-10 && i>0); i--){
			 searkeys.add((String)searkey.get(i-1));
		 }
		model.addAttribute("searkeys", searkeys);
		model.addAttribute("searchKeyword", searchKeyword);
		return SEARCH_INDEX;
	}
	
	@RequestMapping(value = "suggestion")
	@ResponseBody
	public Map<String, Object> suggestion(String keyword, Model model,HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		ResultSuggestion resultSuggestion = spBizSearchService.suggestion(keyword);
		if(resultSuggestion!=null &&resultSuggestion.getSuggestions().size()>0){
			map.put("code", "0");
			map.put("searchKeyword", resultSuggestion);
		}else {
			map.put("code", "1");
		}
		return map;
	}
	   
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String product(SearchConditions searchConditions,Model model, HttpServletRequest request,HttpServletResponse response)throws Exception {
        	if (StringUtils.isEmpty(searchConditions.getKeyword().trim())) {
        		 return SEARCH_LIST;
			}
        	/*return 
        	String msg="抱歉！搜索数据不可以为空，请重新输入";*/
    		//红包
    		String link=isRedlink(searchConditions.getKeyword());
    		if (StringUtil.isNotEmpty(link)) {
				 response.sendRedirect(link);
				 return null;
			}    		
    		//保存cookes
    		saveSearchkey(response,searchConditions.getKeyword(),request);
    		
            String userLv = this.getSessionUser(request) == null ? null : this.getSessionUser(request).getLv();
            logger.debug("user level is :" + userLv);
            searchConditions.setUserLv(userLv);
            searchConditions.setNum(Constants.PRODUCT_LIST_END);
            searchConditions.setStart(StringUtils.isEmpty(searchConditions.getStart()) ? "1" : searchConditions.getStart());
            SearchResult searchResult = spBizSearchService.searchList(searchConditions);
            //int hasPageNum = SearchUtil.hasPageNum(Integer.parseInt(searchResult.getCount()),Integer.parseInt(Constants.PRODUCT_LIST_END));
            List<SearchCategory> categoryList = searchResult.getCategoryList();
            if(categoryList!=null&&categoryList.size()>0){
                defaultCategoryNo = categoryList.get(0).getId();
                defaultCategoryName = categoryList.get(0).getName();
            }
            searchFacet.put("color", searchResult.getColorList());
            searchFacet.put("size", searchResult.getSizeList());
            searchFacet.put("brand", searchResult.getBrandList());
            searchFacet.put("category", searchResult.getCategoryList());
            
            model.addAttribute(
                    "hasMore",
                    SearchUtil.hasMore(Integer.parseInt(searchResult.getCount()),
                            Integer.parseInt(Constants.PRODUCT_LIST_END)));
            model.addAttribute("totalPage", SearchUtil.totalPage(Integer.parseInt(searchResult.getCount()), Integer.parseInt(Constants.PRODUCT_LIST_END)));
            model.addAttribute("searchFacet", searchFacet);
            model.addAttribute("userLv", userLv);
            if(searchResult.getParentCategory() != null){
                model.addAttribute("parentCategoryNo", searchResult.getParentCategory().getId());
                model.addAttribute("parentCategoryName",searchResult.getParentCategory().getName());
            }
            model.addAttribute("searchConditions", searchConditions);
            model.addAttribute("searchResult", searchResult);
            return SEARCH_LIST;
    }
    
	@RequestMapping(value = "list", method = RequestMethod.POST)
    public String product(SearchConditions searchConditions, String parentCategoryNo,String parentCategoryName, Model model,
            HttpServletRequest request) {
        //SearchUtil.SystemConditions(searchConditions);
        String userLv = this.getSessionUser(request) == null ? null : this.getSessionUser(request).getLv();
        logger.debug("user level is :" + userLv);
        searchConditions.setUserLv(userLv);
        searchConditions.setNum(Constants.PRODUCT_LIST_END);
        SearchResult searchResult = spBizSearchService.searchList(searchConditions);
        //判断商品显示的价格
//        List<Product> products = searchResult.getProductList();
//        if(null != products){
//            for(Product product : products){
//                if("0002".equals(userLv)){
//                    product.setStrongPrice(Integer.parseInt(product.getGoldPrice()));
//                }else if("0003".equals(userLv)){
//                    product.setStrongPrice(Integer.parseInt(product.getPlatinumPrice()));
//                }else if("0004".equals(userLv)){
//                    product.setStrongPrice(Integer.parseInt(product.getDiamondPrice()));
//                }else {
//                    product.setStrongPrice(Integer.parseInt(product.getLimitedPrice()));
//                }
//                product.setDelPrice(Integer.parseInt(product.getMarketPrice()));
//            }
//        }
        //List<Category> categoryList = searchResult.getCategoryList();
        model.addAttribute("searchResult", searchResult);
        model.addAttribute(
                "hasMore",
                SearchUtil.hasMore(Integer.parseInt(searchResult.getCount()),
                        Integer.parseInt(Constants.PRODUCT_LIST_END)));
        model.addAttribute("totalPage", SearchUtil.totalPage(Integer.parseInt(searchResult.getCount()), Integer.parseInt(Constants.PRODUCT_LIST_END)));
        model.addAttribute("start", searchConditions.getStart());
        model.addAttribute("searchFacet", searchFacet);
        model.addAttribute("searchConditions", searchConditions);
        model.addAttribute("categoryNo", searchConditions.getCategoryNo());
        model.addAttribute("categoryName",searchConditions.getCategoryName());
        model.addAttribute("userLv", userLv);
        model.addAttribute("gender", searchConditions.getGender());
        model.addAttribute("parentCategoryNo", parentCategoryNo);
        model.addAttribute("parentCategoryName", parentCategoryName);
        model.addAttribute("queryConditions", SearchUtil.initQueryConditions(searchConditions));
        model.addAttribute("defaultCategoryNo",defaultCategoryNo);
        model.addAttribute("defaultCategoryName",defaultCategoryName);
        if("5".equals(searchConditions.getOrder())){
            model.addAttribute("isHot", "0");
        }else {
            model.addAttribute("isHot", "1");
        }
        return SEARCH_LIST;
    }
	
	
	

    @RequestMapping(value = "/list/more", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMore(SearchConditions searchConditions, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = getSessionUser(request);
        String userLv = StringUtils.isEmpty(user) ? null : user.getLv();
        logger.debug("user level is :" + userLv);
        searchConditions.setUserLv(userLv);
        searchConditions.setNum(Constants.PRODUCT_LIST_END);
        SearchResult searchResult = spBizSearchService.searchList(searchConditions);
        int totalPage = SearchUtil.totalPage(Integer.parseInt(searchResult.getCount()),
                Integer.parseInt(Constants.PRODUCT_LIST_END));
        if (totalPage > searchConditions.getPageNo()) {
            map.put("hasMore", 1);
        } else {
            map.put("hasMore", 0);
        }
        map.put("searchResult", searchResult);
        map.put("userLv", userLv);
        return map;
    }
	
	
	
	@RequestMapping(value = "/select", method = RequestMethod.POST)
    @ResponseBody
    public SearchResult getConditions(SearchConditions searchConditions, HttpServletRequest request){
		SearchResult searchResult = spBizSearchService.searchList(searchConditions);
        return searchResult;
    }
	
	/**
	 * 保存搜索过的的关键字
	 * 
	 * @param response
	 * @param keyword
	 * @param request
	 */
	private void saveSearchkey(HttpServletResponse response, String keyword,
			HttpServletRequest request) throws Exception {
		// 如果是热门搜索不保存cookie
		SearchKeyword searchKeyword = spBizSearchService.getSearchKeyword();
		if (searchKeyword != null && searchKeyword.getList().size() > 0) {
			for (RefContent re : searchKeyword.getList()) {
				if (re.getRefContent().equals(keyword)) {
					return;
				}
			}
		}

		Cookie cookies[] = ((HttpServletRequest) request).getCookies();
		if (cookies != null) {
			for (Cookie uaasCookie : cookies) {
				if (uaasCookie.getName().equals(keyword)) {
					return;
				}
			}
		}
		String value = URLEncoder.encode(keyword, "utf-8");
		Cookie cookieToken = new Cookie("searKey" + value, value);
		cookieToken.setPath("/");
		cookieToken.setMaxAge(7200);
		response.addCookie(cookieToken);
	}
	
	/**
	 * 清空cookes
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/clearCookie", method = RequestMethod.POST)
    @ResponseBody
    public void clearCookie(HttpServletRequest request, HttpServletResponse response){
		Cookie cookies[] = request.getCookies();  
	      if (cookies != null)  
	      {  
	          for (int i = 0; i < cookies.length; i++)  
	          {  
	              if (cookies[i].getName().contains("searKey"))  
	              {  
	                  Cookie cookie = new Cookie(cookies[i].getName(),cookies[i].getValue());
	                  cookie.setPath("/");
	                  cookie.setMaxAge(0);  
	                  response.addCookie(cookie);  
	              }  
	          }  
	      }
    }


	/**
	 * 是否是红包口令.如果是跳转红包页面
	 *
	 * @param keyword
	 * @return
	 */
	private String isRedlink(String keyword) {
		Boolean result = spBizStarPacketService.checkisred(keyword);
		if (result) {
			Red red = new Red();
			String link = "";
			red = spBizStarPacketService.findRedList(keyword);
			if (!"0".equals(red.getType()) && red.getCommenBean() != null) {
				String type = red.getCommenBean().getType();
				String refContent = red.getCommenBean().getRefContent();
				String name = red.getCommenBean().getName();
				switch (Integer.parseInt(red.getCommenBean().getType())) {
					case 1:
						link += "/subject/product/list?topicId=" + refContent
								+ "&postArea=0'";
						break;
					case 2:
						link += "/category/product/list?categoryNo=" + refContent
								+ "&postArea=0";
						break;
					case 3:
						link += "/brand/product/list?brandNo=" + refContent
								+ "&postArea=0&WWWWWWWWW";
						break;
					case 4:
						link += "/product/detail?productNo=" + refContent;
						break;
					case 5:
						link += refContent;
						break;
					case 6:
						link += typeCategoryLink(type, refContent, name);
						break;
					case 9:
						link += "lable/product/list?tagId=" + refContent + "";
						break;
					default:
						link += "/search/list?keyword=" + refContent;
						break;
				}
				return link;

			}
		}
		return null;
	}



	private String typeCategoryLink(String type, String refContent, String name) {
		String link = null;
		switch (Integer.parseInt(type)) {
		case 1:
			link += "/coupon/list";
			break;
		case 3:
			link += "/order/list-1'";
			break;
		case 4:
			link += "/collect/product/list?pageIndex=1&pageSize=20&shopType=1";
			break;
		case 2:
			link += "/giftCard/productList";
			break;
		default:
			link += "/search/list?keyword=" + refContent;
			break;
		}
		return link;
	}


}
