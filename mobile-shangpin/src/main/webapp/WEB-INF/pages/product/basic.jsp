<%@ page language="java" contentType="text/html; charset=UTF-8"
	trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>
<!--该商品已下架start-->
    <div class="goods-sold-out">
          <p>该商品已下架</p>
    </div>
<!--该商品已下架end-->
 <c:set var="firstP" value="${productDetail.basic.defaultIndex.firstPropIndex}"/>
 <c:set var="secondP" value="${productDetail.basic.defaultIndex.secondPropIndex}"/>
 <div class="detailed_information">
 	<div class="product_show">
        <div class="show_case">
           <i class="item-activity">
                 <img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/e.gif" lazy="${productDetail.promoLogo}" />
           </i>
            <a href="javascript:;" class="photo_details">
            <!--<em class="new_tag"><img src="/images/e.gif" lazy="/images/new_tag.png" /></em>-->
            <img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/e.gif" lazy="${fn:replace(productDetail.basic.allPics[0],'-10-10','-600-758')}" />
            </a>
              <a href="#" class="case_img"><em></em><b>${fn:length(productDetail.basic.allPics)}</b></a>
        </div>
        <section>
          <header>
            <span>${productDetail.basic.brand.nameEN }</span>
<%--               ${productDetail.basic.prefix }--%>
               <em>${productDetail.basic.productName }</em>
<%--               ${productDetail.basic.suffix }--%>
			  <p>${productDetail.basic.advertWord }</p>
			  <%--去掉促销语--%>
<%--              <c:choose>
                <c:when test="${productDetail.notice.salesPromotionNew.type  ne null}">
                 <c:set var="model" value="${productDetail.notice.salesPromotionNew}"/>
	             <c:choose>
	               <c:when test="${model.refContent eq ''}">
	                 <p> ${model.name }</p>
	               </c:when>
	               <c:otherwise>
	                  <%@ include file="/WEB-INF/pages/product/model_rule.jsp"%>
                      <p> ${model.name }</p>
	                  </a>
	               </c:otherwise>
	             </c:choose>
                </c:when>
                <c:otherwise>
                  <p> ${productDetail.basic.advertWord }</p>
                </c:otherwise>
              </c:choose>--%>
          </header>
          <article>
          <c:if test="${fn:length(productDetail.flagList)>0 }">
          <div class="logo_box">
	     	<c:forEach var="flag" items="${productDetail.flagList}">
		     	<c:choose>
		     	   <c:when test="${flag eq '新品' && productDetail.basic.firstProps[firstP].secondProps[secondP].isPromotion=='1'}">
		     	   </c:when>
		     	   <c:otherwise>
		     	      <i>${flag}</i>
		     	   </c:otherwise>
		       </c:choose> 
	        </c:forEach>
          </div>
          </c:if>
      <div>
     <p class="not_promotion" id="forSalsPrice" >
     <c:set var="color" value="${productDetail.basic.firstProps[firstP].secondProps[secondP].priceTag[0].color eq ''? '#000' : productDetail.basic.firstProps[firstP].secondProps[secondP].priceTag[0].color}"/>
     <span style='color:${color}' >${productDetail.basic.firstProps[firstP].secondProps[secondP].priceTag[0].priceTitle}</span>
	 <c:choose>
		<c:when test="${productDetail.basic.firstProps[firstP].secondProps[secondP].isPromotion=='0'}">
		    <%--<em style='color:${color}'>				 --%>
						<%--&yen;${productDetail.basic.firstProps[firstP].secondProps[secondP].taxStandardPrice }--%>
						<input  type="hidden" id ="lowestInfo" name="lowestInfo" sku="${productDetail.basic.firstProps[firstP].secondProps[secondP].sku}" isPromotion="${productDetail.basic.firstProps[firstP].secondProps[secondP].isPromotion}"  lowestPrice="${productDetail.basic.firstProps[firstP].secondProps[secondP].taxStandardPrice}" isExchange="${productDetail.basic.firstProps[firstP].secondProps[secondP].isExchange }" marketPrice="${productDetail.basic.firstProps[firstP].secondProps[secondP].taxStandardPrice }"/>		
			<%--</em>--%>
		</c:when>
		<c:otherwise>
		 <%--<em style='color:${color}'>--%>
			 <%--&yen;${productDetail.basic.firstProps[firstP].secondProps[secondP].taxPromotionPrice }--%>
		 <%--</em>--%>
		 <input type="hidden" id ="lowestInfo" name="lowestInfo" sku="${productDetail.basic.firstProps[firstP].secondProps[secondP].sku}" isPromotion="${productDetail.basic.firstProps[firstP].secondProps[secondP].isPromotion}"  lowestPrice="${productDetail.basic.firstProps[firstP].secondProps[secondP].taxPromotionPrice}" isExchange="${productDetail.basic.firstProps[firstP].secondProps[secondP].isExchange }" marketPrice="${productDetail.basic.firstProps[firstP].secondProps[secondP].taxStandardPrice }"/>
		</c:otherwise>

	</c:choose>
	 <%--<c:forEach var="secondProps"  items="${productDetail.basic.firstProps[firstP].secondProps}" varStatus="status">--%>
		 <c:set var="productPrice" value="${productDetail.basic.firstProps[firstP].secondProps[secondP].productPrice}"/>
		 <span <%--style="display:${status.index +1 ==secondP ? 'block' : 'none'}" id="price_${status.index+1}"--%>>
			 <em style='color:${productPrice.color}'>
				 ${productPrice.priceDesc}
			 </em>
			<em class="${productPrice.compareHasLine eq '1' ? 'promotion-price-hasline' : 'promotion-price'}" style="color:${productPrice.compareColor}">${productPrice.compareDesc}</em>
		 </span>
	 <%--</c:forEach>--%>

	 <%--<c:if test="${productDetail.basic.firstProps[firstP].secondProps[secondP].priceTag[1].isShow ==1}">--%>
	  <%--<em style='color:${productDetail.basic.firstProps[firstP].secondProps[secondP].priceTag[1].color eq ""?"#000": productDetail.basic.firstProps[firstP].secondProps[secondP].priceTag[1].color }'>--%>
                <%--${productDetail.basic.firstProps[firstP].secondProps[secondP].priceTag[1].priceTitle} --%>
                <%--<c:choose>--%>
                 <%--<c:when test="${'1' eq productDetail.basic.firstProps[firstP].secondProps[secondP].priceTag[1].isCut}">--%>
                    <%--<m style="text-decoration:line-through"> ${productDetail.basic.firstProps[firstP].secondProps[secondP].priceTag[1].priceDesc}</m>--%>
                 <%--</c:when>--%>
                 <%--<c:otherwise>--%>
                    <%--${productDetail.basic.firstProps[firstP].secondProps[secondP].priceTag[1].priceDesc}--%>
                 <%--</c:otherwise>--%>
                <%--</c:choose>--%>
	  <%--</em>--%>
	 <%--</c:if>--%>
   	</p>
   		<!--  <ul>
      		<li id="forSaleDesc"></li>
	        <c:if test="${productDetail.basic.isPackage=='1'}">
	      	  <li class="blue">包邮</li>
	        </c:if>
       	</ul>  -->
    </div>
    </article> 
   </section>
       <c:choose>
        <c:when test="${(productDetail.basic.sales ne -1) && (productDetail.basic.commentCount ne -1) && (productDetail.basic.collections ne -1)}">
	        <ul class="record_num top_line">
		          <li class="sales"><i></i>销量 <b> ${productDetail.basic.sales} 件</b></li>
		          <li class="comments"><i></i>评论 <b>${productDetail.basic.commentCount}条</b></li>
		          <li class="like"><i></i>喜欢 <b>${productDetail.basic.collections} 人</b></li>  
		    </ul>
        </c:when>
       </c:choose>
   </div>

   <!--促销信息-->
  <%--  <c:forEach var="prom" items="${productDetail.promotion }">
	 <div class="product_promotional">
	      <a href="${ctx}/subject/product/list_${prom.activityId}">
	        <i><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/e.gif" lazy="${cdn:pic(pageContext.request)}/styles/shangpin/images/product/promotional_icon.png" /></i>
	        ${prom.desc }
	      </a>
	 </div>
   </c:forEach> --%>
    <c:if test="${fn:length(productDetail.promotionNew)>0 }">
       <c:forEach var="model" items="${productDetail.promotionNew }"> 
		<div class="product_promotional">
	       <c:choose>
	               <c:when test="${model.refContent eq ''}">
	                 	<i><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/e.gif" lazy="${cdn:pic(pageContext.request)}/styles/shangpin/images/product/promotional_icon.png" /></i>
	                   ${model.name }
	               </c:when>
	               <c:otherwise>
	                   <%@ include file="/WEB-INF/pages/product/model_rule.jsp"%>
				        <i><img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/e.gif" lazy="${cdn:pic(pageContext.request)}/styles/shangpin/images/product/promotional_icon.png" /></i>
				        ${model.name }
				        </a>
	               </c:otherwise>
	       </c:choose>
		</div>
	  </c:forEach>
    </c:if>
 <!--配送、支付、分期、承诺-->
       <div class="product_introduce">
       	<%-- <div class="row_box">
           	<em>配送：</em>${productDetail.notice.dispatching }。
           </div> --%>
           <div class="row_box">
           	<em>支付：</em>
               <ul class="payment_method">
                   <li class="alipay"></li>
                   <li class="wechatpay"></li>
                   <li class="unionpay"></li>
                   <c:if test="${productDetail.notice.codFlag eq '1' }" >
                     <li class="topay"></li>
                   </c:if>
           	</ul>
           </div>
           <c:if test="${productDetail.notice.acceptance ne ''}">
           <div class="row_box">
           	<em>承诺：</em>${productDetail.notice.acceptance }
           </div>
           </c:if>
       </div>
       
       <!--促销信息-->
	<div class="product_promotional product_sizes">
		<c:choose>
      		<c:when test="${productDetail.basic.isSoldOut=='1' }">
      		 <a href="javascript:;" class="select_bar" selectSoldOut="1"> 
      		</c:when>
      		<c:otherwise>
      		  <a href="javascript:;" class="select_bar" selectSoldOut="0">  
      		</c:otherwise>
	   </c:choose>
	   <c:if test="${productDetail.basic.firstPropName != ''}">
	   	 <span>颜 色：</span>
		 <c:forEach var="first" items="${productDetail.basic.firstProps}" varStatus="status" >
	              ${first.firstProp }
	              <c:if test="${ (status.index+1)<fn:length(productDetail.basic.firstProps)}">、</c:if>
	     </c:forEach> <br /> 
	    </c:if>
	    <c:if test="${productDetail.basic.firstProps[0].isSecondProp=='1'}">
          <span> ${productDetail.basic.secondPropName }：</span>
		<%--是否有第二属性 --%>
		   <c:forEach var="second" items="${productDetail.basic.firstProps[0].secondProps}" varStatus="status">
		      ${second.secondProp }
		      <c:if test="${ (status.index+1)<fn:length(productDetail.basic.firstProps[0].secondProps)}">、</c:if>
           </c:forEach>
		</c:if>
		</a>
	</div>