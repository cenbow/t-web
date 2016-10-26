<%@ page language="java" contentType="text/html; charset=UTF-8"
         trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp" %>
<!--弹层显示购买信息start-->
<div class="alProd"></div>
<div class="alProdInfo">
    <a href="javascript:;" class="close_btn">关闭</a>
    <div class="commodity_info clr">
          <span class="img_photo">
            <img src="${fn:replace(productDetail.basic.allPics[0],'-10-10','-600-758') }"/>
          </span>
        <div class="right_cont">
            <h3>${productDetail.basic.brand.nameEN } ${productDetail.basic.productName }</h3>
            <p>
                <b class="not_promotion"></b>
            </p>
            <!--<div class="choice_product"><span></span><span></span></div>-->
        </div>
    </div>
    <div class="color_info">
        <h3>${productDetail.basic.firstPropName }</h3>
        <ul>
            <c:forEach var="first" items="${productDetail.basic.firstProps}" varStatus="status">
                <c:choose>
                    <c:when test="${first.isSecondProp =='0'}">
                        <c:choose>
                            <c:when test="${first.secondProps[0].count*1<1 }">
                                <li id="first_${status.index }" isSecondProp="${first.isSecondProp }" class="soldOut">
                            </c:when>
                            <c:otherwise>
                                <li id="first_${status.index }" isSecondProp="${first.isSecondProp }" class="buySold">
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <li id="first_${status.index }" isSecondProp="${first.isSecondProp }" class="buySold">
                    </c:otherwise>
                </c:choose>
                ${first.firstProp }
                </li>
            </c:forEach>

        </ul>
    </div>
    <c:forEach var="first" items="${productDetail.basic.firstProps}" varStatus="status">
    <c:choose>
    <c:when test="${first.isSecondProp=='1'}"><%--是否有第二属性 --%>
    <c:choose>
    <c:when test="${status.index==0 }">
    <div class="size_info" id="second_first_${status.index }">
        </c:when>
        <c:otherwise>
        <div class="size_info" id="second_first_${status.index }" style="display:none">
            </c:otherwise>
            </c:choose>
            <h3>${productDetail.basic.secondPropName }</h3>
            <ul>
                <c:forEach var="second" items="${first.secondProps}" varStatus="secondStatus">
                    <c:choose>
                        <c:when test="${second.count*1>0 }">
                            <li class="buySold">
                        </c:when>
                        <c:otherwise>
                            <li class="soldOut">
                        </c:otherwise>
                    </c:choose>
                    ${second.secondProp }
                    <input type="hidden" id="isExchange" name="isExchange" value="${second.isExchange }"/>
                    <input type="hidden" id="marketPrice" name="marketPrice" value="${second.taxStandardPrice }"/>
                    <input type="hidden" id="isPromotion" name="isPromotion" value="${second.isPromotion }"/>
                    <input type="hidden" id="sku" name="sku" value="${second.sku }"/>
                    <input type="hidden" id="count" name="count" value="${second.count }"/>
                    <c:choose>
                        <c:when test="${second.isPromotion == '1'}">
                            <input type="hidden" id="salePrice" name="salePrice" value="${second.taxPromotionPrice }"/>
                        </c:when>
                        <c:otherwise>
                            <%-- <c:choose>
                                <c:when test="${second.isSupportDiscount==1 }">
                                    <c:choose>
                                        <c:when test="${userLv == '0002'}">
                                            <input type="hidden" id="salePrice" name="salePrice"
                                                   value="${second.goldPrice }"/>
                                        </c:when>
                                        <c:when test="${userLv == '0003'}">
                                            <input type="hidden" id="salePrice" name="salePrice"
                                                   value="${second.platinumPrice }"/>
                                        </c:when>
                                        <c:when test="${userLv == '0004'}">
                                            <input type="hidden" id="salePrice" name="salePrice"
                                                   value="${second.diamondPrice }"/>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="hidden" id="salePrice" name="salePrice"
                                                   value="${second.limitedPrice }"/>
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                <c:otherwise> --%>
                                    <input type="hidden" id="salePrice" name="salePrice"
                                           value="${second.taxStandardPrice }"/>
<%-- 
                                </c:otherwise>
                            </c:choose> --%>
                        </c:otherwise>
                    </c:choose>
                    </li>
                </c:forEach>
            </ul>
        </div>
        </c:when>
        <c:otherwise>
            <div id="second_first_${status.index }">
                <input type="hidden" id="isExchange" name="isExchange" value="${first.secondProps[0].isExchange }"/>
                <input type="hidden" id="marketPrice" name="marketPrice" value="${first.secondProps[0].taxStandardPrice }"/>
                <input type="hidden" id="isPromotion" name="isPromotion" value="${first.secondProps[0].isPromotion }"/>
                <input type="hidden" id="sku" name="sku" value="${first.secondProps[0].sku }"/>
                <input type="hidden" id="count" name="count" value="${first.secondProps[0].count }"/>

                <c:choose>
                    <c:when test="${first.secondProps[0].isPromotion == '1'}">
                        <input type="hidden" id="salePrice" name="salePrice"
                               value="${first.secondProps[0].taxPromotionPrice }"/>
                        <span type="hidden" id="lowestInfo" name="lowestInfo"
                              isPromotion="${first.secondProps[0].isPromotion}" sku="${first.secondProps[0].sku}"
                              lowestPrice="${first.secondProps[0].taxPromotionPrice}"
                              isExchange="${first.secondProps[0].isExchange }"
                              marketPrice="${first.secondProps[0].taxStandardPrice }"></span>
                    </c:when>
                    <c:otherwise>
                       <%--  <c:choose>
                            <c:when test="${first.secondProps[0].isSupportDiscount==1 }">
                                <c:choose>
                                    <c:when test="${userLv == '0002'}">
                                        <input type="hidden" id="salePrice" name="salePrice"
                                               value="${first.secondProps[0].goldPrice }"/>
                                    </c:when>
                                    <c:when test="${userLv == '0003'}">
                                        <input type="hidden" id="salePrice" name="salePrice"
                                               value="${first.secondProps[0].platinumPrice }"/>
                                    </c:when>
                                    <c:when test="${userLv == '0004'}">
                                        <input type="hidden" id="salePrice" name="salePrice"
                                               value="${first.secondProps[0].diamondPrice }"/>

                                    </c:when>
                                    <c:otherwise>

                                        <input type="hidden" id="salePrice" name="salePrice"
                                               value="${first.secondProps[0].limitedPrice }"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise> --%>
                                <input type="hidden" id="salePrice" name="salePrice"
                                       value="${first.secondProps[0].taxStandardPrice }"/>
                           <%--  </c:otherwise>
                        </c:choose> --%>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:otherwise>
        </c:choose>

        </c:forEach>
        <div class="num_info">
            <h3 class="f_left">购买数量</h3>
            <div class="amount_change">
                <a class="amount_cut" href="javascript:;">-</a>
                <input class="amount_val" name="" value="1" type="text">
                <a class="amount_add" href="javascript:;">+</a>
            </div>
        </div>

        <div class="submit">
            <a href="javascript:;" class="submit_btn">确定</a>
        </div>
        <input type="hidden" id="buySku" name="buySku" value=""/>
        <input type="hidden" id="buyCount" name="buyCount" value="1"/>
        <input type="hidden" id="productNo" name="productNo" value="${productNo}"/>
        <input type="hidden" id="collectId" name="collectId" value="${productDetail.basic.collect.id }"/>
        <input type="hidden" id="topicId" name="topicId" value="${topicId}"/>
        <input type="hidden" id="brandName" name="brandName" value="${productDetail.basic.brand.nameEN }"/>
        <input type="hidden" id="firstPropName" name="firstPropName" value="${productDetail.basic.firstPropName }"/>
        <input type="hidden" id="secondPropName" name="secondPropName" value="${productDetail.basic.secondPropName}"/>
        <input type="hidden" id="postArea" name="postAreaName" value="${productDetail.postArea}"/>
    </div>
    <!--弹层显示购买信息end-->
    <div class="footerBtm">
        <div class="footerBtmFixed">
            <div>
                <a href="#" onclick="NTKF.im_openInPageChat('kf_9986_1355827406710')">
                    <em>
                        <img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/product/service.png" width="25"
                             height="25"/></em>
                    <i>客服</i>
                </a>
          <span>
          <%--<a href="${ctx }/cart/list">
            <em id="forCartCount" style="display:none">1</em>
           <img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/product/pic_shopping.png" width="25" height="25" />
         </a>--%>

          <c:choose>
              <c:when test="${productDetail.basic.collect.isCollected=='1'}">
                  <a class="collection_btn cur" href="javascript:;">已加入愿望清单</a>
              </c:when>
              <c:otherwise>
                  <a class="collection_btn" href="javascript:;">加入愿望清单</a>
              </c:otherwise>
          </c:choose>

    <%--        <c:choose>
		      		<c:when test="${productDetail.basic.isSoldOut=='1' }">
		      		 <a href="#" class="buy_btn" buySoldOut="1">立即购买</a>
		      		</c:when>
		      		<c:otherwise>
		      		<a href="#" class="buy_btn" buySoldOut="0">立即购买</a>
		      		</c:otherwise>
			</c:choose>
        </div>
      </div>
    </div>
    <!--页面底部end-->
    </c:when>
    <c:otherwise>--%>

              <!--国内页面底部start-->
    <%--<div class="footerBtm">
      <div class="footerBtmFixed">
        <div>
          <span>
          <a href="${ctx }/cart/list">
            <em id="forCartCount" style="display:none">1</em>
           <img src="${cdn:pic(pageContext.request)}/styles/shangpin/images/detail/pic_shopping.png" width="25" height="25" />
          <a href="javascript:;"><i class="collection_btn"></i></a>
         </a>         
          </span>  --%>
          
         <%-- <c:choose>
		      		<c:when test="${productDetail.basic.isSoldOut=='1' }">
		      		 <a href="#" class="buy_btn" buySoldOut="1">立即购买</a>
		      		</c:when>
		      		<c:otherwise>
		      		<a href="#" class="buy_btn" buySoldOut="0">立即购买</a>
		      		</c:otherwise>
			    </c:choose>--%>
	      <c:choose>
              <c:when test="${checkWX }">
                  <c:choose>
                      <c:when test="${cookie['ch'].value eq '102'||ch eq '102'}">
                      </c:when>
                      <c:otherwise>
                          <c:choose>
                              <c:when test="${productDetail.basic.isSoldOut=='1' }">
                                  <a href="javascript:;" class="add_btn" cartSoldOut="1">加入购物袋</a>
                              </c:when>
                              <c:otherwise>
                                  <a href="javascript:;" class="add_btn" cartSoldOut="0">加入购物袋</a>
                              </c:otherwise>
                          </c:choose>

                      </c:otherwise>
                  </c:choose>
              </c:when>
              <c:otherwise>
                  <c:choose>
                      <c:when test="${productDetail.basic.isSoldOut=='1' }">
                          <a href="javascript:;" class="add_btn" cartSoldOut="1">加入购物袋</a>
                      </c:when>
                      <c:otherwise>
                          <a href="javascript:;" class="add_btn" cartSoldOut="0">加入购物袋</a>
                      </c:otherwise>
                  </c:choose>
              </c:otherwise>
          </c:choose>
			</span>
            </div>
        </div>
    </div>
    <!--页面底部end-->