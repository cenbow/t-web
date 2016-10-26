<%@ page language="java" contentType="text/html; charset=UTF-8" trimDirectiveWhitespaces="true"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.inc.jsp"%>



<rapid:override name="custum">
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/user/login.css${ver}" rel="stylesheet" />
    <link href="${cdn:css(pageContext.request)}/styles/shangpin/css/page/user/tel2_sign.css${ver}" rel="stylesheet" />
    <script type="text/javascript" charset="utf-8">
        loader = SP.core
                .install("${cdn:js(pageContext.request)}/styles/shangpin/js/jquery.min.js${ver}")
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/lazyload.js${ver}")
                .excute()
                <%--.using("${cdn:js(pageContext.request)}/styles/shangpin/js/config.sim.js${ver}")--%>
                .using("${cdn:js(pageContext.request)}/styles/shangpin/js/user/login.js${ver}")
                .excute()
    </script>
</rapid:override >


<%-- 浏览器标题 --%>
<rapid:override name="title">
    手机注册
</rapid:override>

<%-- 页标题 --%>
<rapid:override name="page_title">
    手机注册
</rapid:override>
<rapid:override name="content">
    <div class="tabs_box">
        <div class="tabs_Cell">
            <form name="login" id="J_mobiForm2" method="post" action="${ctx}/registe/mobile">
                <fieldset>
                    <p class="c-form-search">
                        <label for="mobi" class="mobi"></label>
                        <input readonly="readonly" onfocus="this.blur()" type="text" id="J_MobiName" name="mobi" placeholder="手机号" onkeyup='this.value=this.value.replace(/\D/gi,"")' maxlength="11" value ="${mobi}"/>
                        <%--<button type="button"></button>--%>
                    </p>
                    <div class="verification_box">
                        <p class="c-form-search verification">
                            <label for="mobiCode" class="mobiCode"></label>
                            <input type="text" id="J_MobiCode" name="mobiCode" placeholder="输入验证码" maxlength="6" />
                            <button type="button"></button>
                        </p>
                        <a href="javascript:;" class="getCode" id="passwordGetCode" data-waiting="秒">获取验证码</a>
                    </div>
                    <div class="verification_box">
                        <p class="c-form-search verification" style="width: 90%">
                            <label for="MobiPwd" class="MobiPwd"></label>
                            <input type="text" id="J_MobiPwd" name="password" placeholder="请设置6-20位字母或数字" maxlength="20"  onpaste="return false" ondragenter="return false" oncontextmenu="return false;" style="ime-mode:disabled" />
                            <button type="button"></button>
                        </p>
                        <span class="pwd_eyes"></span>
                    </div>
                    <a class="pwd_tips" href="javascript:;">密码由6-20位数字组成，区分大小写</a>
                    <!--<div class="fillGender" id="mobiGender"><a href="#" class="cur" name="0"><em>女士<i><img src="/images/2016Mapp/cur_icon.png" width="12" height="12"></i></em></a><a href="#" name="1"><em>男士<i><img src="/images/2016Mapp/cur_icon.png" width="12" height="12"></i></em></a><input type="hidden" id="sexVal01" value="0" /></div>-->
                    <p class="login_errorMsg mobiMsg">&nbsp;${msg}</p>
                    <input type="botton" class="login_btn" value="注 册" readonly="readonly" ctag='{"id":"H2"}'/>
                    <p class="agreement"><span></span>我已阅读并同意<a  href="javascript:;" class="sp_info" >《尚品用户协议》</a></p>
                </fieldset>
            </form>
        </div>
    </div>
    <div class="pop_overlay">
        <div class="pop_container">
            <p><h2>隐私声明 Privacy Statement</h2></p>
            <p>欢迎访问尚品网(shangpin.com)或尚品奥莱网(aolai.com)！我们以本隐私声明对访问者隐私保护的许诺。以下文字公开本网站尚品网(shangpin.com)或尚品奥莱网(aolai.com)对信息收集和使用的情况。本站的隐私声明正在不断改进中，随着本网站服务范围的扩大，我们会随时更新我们的隐私声明。我们欢迎您随时会来查看本申明。请向 service@shangpin.com反馈您的意见。</p>
            <p>在同意尚品网(shangpin.com)或尚品奥莱网(aolai.com)服务协议（"协议"）之时，你已经同意我们按照本隐私声明来使用和披露您的个人信息。本隐私声明的全部条款属于该协议的一部份。</p>
            <p><strong>未成年人的特别注意事项</strong></p>
            <p>如果您未满18周岁，您无权独自使用公司服务，因此我们希望您不要向我们提供任何个人信息。如果您未满18周岁，您只能在父母或监护人的陪同下才可以使用公司服务。</p>
            <p><strong>用户名和密码</strong></p>
            <p>当您打算注册成用户后，我们要求您选择一个用户名和密码。您只能通过您的密码来使用您的帐号。如果您泄漏了密码，您可能丢失了您的个人识别信息，并且有可能导致对您不利的司法行为。因此不管任何原因使您的密码安全受到危及，您应该立即通过service@shangpin.com和我们取得联系。</p>
            <p><strong>注册信息</strong></p>
            <p>当您在注册为用户时，我们要求您填写一张注册表。注册表要求提供您的电子邮件地址和性别。您还有权选择来填写附加信息。这些信息可能包括您公司所在的省份和城市，时区和邮政编码，传真号码，主页和您的职务。我们使用注册信息来获得用户的统计资料。我们将会用这些统计数据来给我们的用户分类，以便有针对性地向我们的用户提供新的服务。我们会通过您的邮件地址来通知您这些新的服务。</p>
            <p><strong>您的交易行为</strong></p>
            <p>我们跟踪IP地址仅仅只是为了安全的必要。如果我们没有发现任何安全问题，我们会及时删除我们收集到的IP地址。我们还跟踪全天的页面访问数据。全天页面访问数据被用来反映网站的流量，一是我们可以为未来的发展制定计划。</p>
            <p><strong>广告</strong></p>
            <p>我们会对个人身份数据进行匿名的综合统计，并为销售和奖励的需要将该综合统计向广告主披露。</p>
            <p><strong>第三方</strong></p>
            <p>我们不会向任何第三方提供，出售，出租，分享和交易用户的个人信息，除非第三方和尚品网(shangpin.com)或尚品奥莱网(aolai.com)一起为网站和用户提供服务并且在该服务结束后已将被禁止访问包括其以前能够访问的所有这些资料。当我们被法律强制或依照政府要求提供您的信息时我们将善意地披露您的资料。</p>
            <p><strong>信息的存储和交换</strong></p>
            <p>用户信息和资料被收集和存储在放置于中国的服务器上。只有为了做备份的需要时，我们才可能需要将您的资料传送到别国的服务器上。</p>
            <p><strong>外部链接</strong></p>
            <p>本站含有到其他网站的链接。尚品网(shangpin.com)或尚品奥莱网(aolai.com)对那些网站的隐私保护措施不负任何责任。我们可能在任何需要的时候增加商业伙伴或共用品牌的网站，但是提供给他们的将仅仅是综合信息，我们将不会公开您的身份。</p>
            <p><strong>安全</strong></p>
            <p>我们网站有相应的安全措施来确保我们掌握的信息不丢失，不被滥用和变造。这些安全措施包括向其它服务器备份数据和对用户密码加密。尽管我们有这些安全措施，但请注意在因特网上不存在"完善的安全措施"。</p>
            <p><strong>修改您的资料</strong></p>
            <p>您可以随时在尚品网(shangpin.com)或尚品奥莱网(aolai.com)的网站修改或者更新你的个人信息和密码（在成功登录之后）。</p>
            <p><strong>联系我们</strong></p>
            <p>如果你对本隐私声明或尚品网(shangpin.com)或尚品奥莱网(aolai.com)的隐私保护措施以及您在使用中的问题有任何意见和建议请和我们联系：service@shangpin.com。</p>
            <p><h2>服务条款 Service</h2></p>
            <p><strong>一、尚品网(shangpin.com)或尚品奥莱网(aolai.com)服务条款的确认和接纳</strong></p>
            <p>尚品网(shangpin.com)或尚品奥莱网(aolai.com)的各项电子服务的所有权和运作权归尚品网(shangpin.com)或尚品奥莱网(aolai.com)。尚品网(shangpin.com)或尚品奥莱网(aolai.com)提供的服务将完全按照其发布的服务条款和操作规则严格执行。用户必须完全同意所有服务条款并完成注册程序，才能成为shangpin.com的正式用户。用户确认：本协议条款是处理双方权利义务的当然约定依据，除非违反国家强制性法律，否则始终有效。在下订单的同时，您也同时承认了您拥有购买这些产品的权利能力和行为能力，并且将您对您在订单中提供的所有信息的真实性负责。</p>
            <p><strong>二、服务简介</strong></p>
            <p>尚品网(shangpin.com)或尚品奥莱网(aolai.com)运用自己的操作系统通过国际互联网络为用户提供网络服务。同时，用户必须对信息的真实性负责。</p>
            <p>(1)自行配备上网的所需设备，包括个人电脑、调制解调器或其他必备上网装置；</p>
            <p>(2)自行负担个人上网所支付的与此服务有关的电话费用、网络费用。</p>
            <p>尚品网(shangpin.com)或尚品奥莱网(aolai.com)不公开用户的姓名、地址、电话号码、电子邮箱和笔名， 除以下情况外：</p>
            <p>(1)用户授权尚品网(shangpin.com)或尚品奥莱网(aolai.com)透露这些信息；</p>
            <p>(2)相应的法律及程序要求尚品网(shangpin.com)或尚品奥莱网(aolai.com)提供用户的个人资料。</p>
            <p>如果用户提供的资料包含有不正确的信息，尚品网(shangpin.com)或尚品奥莱网(aolai.com)保留结束用户使用网络服务资格的权利。</p>
            <p><strong>三、价格和数量</strong></p>
            <p>尚品网(shangpin.com)或尚品奥莱网(aolai.com)将尽最大努力保证您所购商品与网站上公布的价格一致，但价目表和声明并不构成要约。尚品网(shangpin.com)或尚品奥莱网(aolai.com)有权在发现了其网站上显现的产品及订单的明显错误或缺货的情况下，单方面撤回任何承诺。同时，尚品网(shangpin.com)或尚品奥莱网(aolai.com)保留对产品订购的数量的限制权。</p>
            <p>产品的价格和可获性都在尚品网(shangpin.com)或尚品奥莱网(aolai.com)上指明。这类信息将随时更改且不发任何通知。如果发生了意外情况，在确认了您的订单后，由于运输、货币汇率变动、供应商违约等可能的原因或由于网站的错误或由于第三方支付合作方的错误等造成商品价格变化，您有权取消您的订单，并希望您能及时通过电子邮件或电话通知尚品网(shangpin.com)或尚品奥莱网(aolai.com)客户服务部。</p>
            <p>如果发生了意外情况，在确认了您的订单后，由于运输、货币汇率变动、供应商违约以及其他可能的原因或由于网站的错误或由于第三方支付合作方的错误等造成商品不可获得，尚品网(shangpin.com)或尚品奥莱网(aolai.com)有权单方面撤回任何承诺并且不承担交易货款以外的任何赔偿责任。</p>
            <p>您所订购的商品，如果发生缺货，您有权取消订单。</p>
            <p><strong>四、送货及费用</strong></p>
            <p>尚品网(shangpin.com)或尚品奥莱网(aolai.com)将会把产品送到您所指定的送货地址。所有在尚品网(shangpin.com)或尚品奥莱网(aolai.com)上列出的送货时间为参考时间，参考时间的计算是根据库存状况、正常的处理过程和送货时间、送货地点的基础上估计得出的。送货费用根据您选择的配送方式的不同而异。</p>
            <p>请清楚准确地填写您的真实姓名、送货地址及联系方式。因如下情况造成订单延迟或无法配送等，尚品网(shangpin.com)或尚品奥莱网(aolai.com)将不承担责任：</p>
            <p>(1)客户提供错误信息和不详细的地址；</p>
            <p>(2)货物送达无人签收，由此造成的重复配送所产生的费用及相关的后果；</p>
            <p>(3)不可抗力，例如：自然灾害、交通戒严、突发战争等。</p>
            <p><strong>五、退换货</strong></p>
            <p>尚品网(shangpin.com)或尚品奥莱网(aolai.com)提供的商品退换货标准及操作流程详见尚品奥莱帮助中心。</p>
            <p><strong>六、服务条款的修改</strong></p>
            <p>尚品网(shangpin.com)或尚品奥莱网(aolai.com)将可能不定期的修改本用户协议的有关条款而不需提前告知用户或征得用户许可；一旦条款及服务内容产生变动，尚品网(shangpin.com)或尚品奥莱网(aolai.com)将会在有关页面上进行提示。如果不同意尚品网(shangpin.com)或尚品奥莱网(aolai.com)对条款内容所做的修改，用户可以主动取消获得的网络服务。如果用户继续使用尚品网(shangpin.com)或尚品奥莱网(aolai.com)的服务，则视为接受服务条款的变动。尚品网(shangpin.com)或尚品奥莱网(aolai.com)保留随时修改或中断服务而不需告知用户的权利。尚品网(shangpin.com)或尚品奥莱网(aolai.com)行使修改或中断服务的权利，不需对用户或第三方负责。</p>
            <p><strong>七、用户隐私制度</strong></p>
            <p>尊重用户个人隐私是尚品网(shangpin.com)或尚品奥莱网(aolai.com)的一项基本政策。所以，作为对以上第二点人注册资料分析的补充，尚品网(shangpin.com)或尚品奥莱网(aolai.com)一定不会在未经合法用户授权时公开或透露其注册资料及保存在尚品网(shangpin.com)或尚品奥莱网(aolai.com)中的非公开内容，除非有法律许可要求或尚品网(shangpin.com)或尚品奥莱网(aolai.com)在诚信的基础上认为透露这些信件在以下四种情况是必要的：</p>
            <p>(1)遵守有关法律规定，遵从尚品网(shangpin.com)或尚品奥莱网(aolai.com)合法服务程序；</p>
            <p>(2)保持维护尚品网(shangpin.com)或尚品奥莱网(aolai.com)的商标所有权；</p>
            <p>(3)在紧急情况下竭力维护用户个人和社会大众的隐私安全；</p>
            <p>(4)符合其他相关的要求。</p>
            <p>我们的用户隐私制度将不断改进，随着本网站服务范围的扩大，我们会随时更新我们的隐私声明。“用户隐私声明”是本用户条款的不可分割部分。</p>
            <p><strong>八、用户的帐号，密码和安全性</strong></p>
            <p>用户一旦注册成功，成为尚品网(shangpin.com)或尚品奥莱网(aolai.com)的合法用户，将得到一个密码和用户名。您可随时根据指示改变您的密码。用户将对用户名和密码安全负全部责任。另外，每个用户都要对以其用户名进行的所有活动和事件负全责。用户若发现任何非法使用用户帐号或存在安全漏洞的情况，请立即通告尚品网(shangpin.com)或尚品奥莱网(aolai.com)。</p>
            <p><strong>九、拒绝提供担保</strong></p>
            <p>用户个人对网络服务的使用承担风险。尚品网(shangpin.com)或尚品奥莱网(aolai.com)对此不作任何类型的担保，不论是明确的或隐含的，但是不对商业性的隐含担保、特定目的和不违反规定的适当担保作限制。尚品网(shangpin.com)或尚品奥莱网(aolai.com)不担保服务一定能满足用户的要求，也不担保服务不会受中断，对服务的及时性，安全性，出错发生都不作担保。尚品网(shangpin.com)或尚品奥莱网(aolai.com)对在尚品网(shangpin.com)或尚品奥莱网(aolai.com)上得到的任何商品购物服务或交易进程，不作担保。</p>
            <p><strong>十、对用户信息的存储和限制</strong></p>
            <p>尚品网(shangpin.com)或尚品奥莱网(aolai.com)不对用户所发布信息的删除或储存失败负责。尚品网(shangpin.com)或尚品奥莱网(aolai.com)有判定用户的行为是否符合尚品网(shangpin.com)或尚品奥莱网(aolai.com)服务条款的要求和精神的保留权利，如果用户违背了服务条款的规定，尚品网(shangpin.com)或尚品奥莱网(aolai.com)有中断对其提供网络服务的权利。</p>
            <p><strong>十一、用户管理</strong></p>
            <p>用户单独承担发布内容的责任。用户对服务的使用是根据所有适用于尚品网(shangpin.com)或尚品奥莱网(aolai.com)的国家法律、地方法律和国际法律标准的。用户必须遵循：</p>
            <p>(1)从中国境内向外传输技术性资料时必须符合中国有关法规；</p>
            <p>(2)使用网络服务不作非法用途；</p>
            <p>(3)不干扰或混乱网络服务；</p>
            <p>(4)遵守所有使用网络服务的网络协议、规定、程序和惯例。</p>
            <p>用户须承诺不传输任何非法的、骚扰性的、中伤他人的、辱骂性的、恐怖性的、伤害性的、庸俗的，淫秽等信息资料。另外，用户也不能传输和教唆他人构成犯罪行为的资料；不能传输助长国内不利条件和涉及国家安全的资料；不能传输任何不符合当地法规、国家法律和国际法律的资料。未经许可而非法进入其它电脑系统是被禁止的。</p>
            <p>若用户的行为不符合以上提到的服务条款，尚品网(shangpin.com)或尚品奥莱网(aolai.com)将作出独立判断立即取消用户服务帐号。用户需对自己在网上的行为承担法律责任。用户若在尚品网(shangpin.com)或尚品奥莱网(aolai.com)上散布和传播反动、色情或其他违反国家法律的信息，尚品网(shangpin.com)或尚品奥莱网(aolai.com)的系统记录有可能作为用户违反法律的证据，并应司法机关的要求而据实提交给司法机关。</p>
            <p><strong>十二、保障用户</strong></p>
            <p>用户同意保障和维护尚品网(shangpin.com)或尚品奥莱网(aolai.com)全体成员的利益，负责支付由用户使用超出业务范围引起的律师费用，违反服务条款的损害补偿费用等。</p>
            <p><strong>十三、结束服务</strong></p>
            <p>用户或尚品网(shangpin.com)或尚品奥莱网(aolai.com)可随时根据实际情况中断一项或多项网络服务。尚品网(shangpin.com)或尚品奥莱网(aolai.com)不需对任何个人或第三方负责而随时中断服务。用户对后来的条款修改有异议，或对尚品网(shangpin.com)或尚品奥莱网(aolai.com)的服务不满，可以行使如下权利：</p>
            <p>(1)停止使用尚品网(shangpin.com)或尚品奥莱网(aolai.com)的网络服务；</p>
            <p>(2)通告尚品网(shangpin.com)或尚品奥莱网(aolai.com)停止对该用户的服务。</p>
            <p>结束用户服务后，用户使用网络服务的权利马上中止。从那时起，用户有权利，尚品网(shangpin.com)或尚品奥莱网(aolai.com)也没有义务传送任何未处理的信息或未完成的服务给用户或第三方。</p>
            <p><strong>十四、通告</strong></p>
            <p>所有发给用户的通告都可通过页面公告或电子邮件或常规的信件传送。用户协议条款的修改、服务变更、或其它重要事件的通告都会以此形式进行。</p>
            <p><strong>十五、参与广告策划</strong></p>
            <p>用户在他们发表的信息中加入宣传资料或参与广告策划，在尚品网(shangpin.com)或尚品奥莱网(aolai.com)的免费服务上展示他们的产品，任何这类促销方法，包括运输货物、付款、服务、商业条件、担保及与广告有关的描述都只是在相应的用户和广告销售商之间发生。尚品网(shangpin.com)或尚品奥莱网(aolai.com)不承担任何责任，尚品网(shangpin.com)或尚品奥莱网(aolai.com)没有义务为这类广告销售负任何一部分的责任。</p>
            <p><strong>十六、网络服务内容的所有权</strong></p>
            <p>尚品网(shangpin.com)或尚品奥莱网(aolai.com)定义的网络服务内容包括：文字、软件、声音、图片、录像、图表、广告中的全部内容；电子邮件的全部内容；尚品网(shangpin.com)或尚品奥莱网(aolai.com)为用户提供的其他信息。所有这些内容受版权、商标、标签和其它财产所有权法律的保护。所以，用户只能在尚品网(shangpin.com)或尚品奥莱网(aolai.com)和广告商授权下才能使用这些内容，而不能擅自复制、再造这些内容、或创造与内容有关的派生产品。尚品网(shangpin.com)或尚品奥莱网(aolai.com)所有的文章版权归原文作者和尚品网(shangpin.com)或尚品奥莱网(aolai.com)共同所有，任何人需要转载尚品网(shangpin.com)或尚品奥莱网(aolai.com)的文章，必须征得原文作者或尚品网(shangpin.com)或尚品奥莱网(aolai.com)授权。</p>
            <p><strong>十七、责任限制</strong></p>
            <p>如因不可抗力的原因使尚品网(shangpin.com)或尚品奥莱网(aolai.com)销售系统崩溃或无法正常使用导致网上交易无法完成或丢失有关的信息、记录等，尚品网(shangpin.com)或尚品奥莱网(aolai.com)不承担责任。但是尚品网(shangpin.com)或尚品奥莱网(aolai.com)会尽可能合理地协助处理善后事宜，并努力使客户免受经济损失。</p>
            <p><strong>十八、法律管辖和适用</strong></p>
            <p>本协议的订立、执行和解释及争议的解决均应适用中国法律。</p>
            <p>如发生尚品网(shangpin.com)或尚品奥莱网(aolai.com)服务条款与中国法律相抵触时，则这些条款将完全按法律规定重新解释，而其它合法条款则依旧保持对用户产生法律效力和影响。</p>
            <p>本协议的规定是可分割的，如本协议任何规定被裁定为无效或不可执行，该规定可被删除而其余条款应予以执行。</p>
            <p>如双方就本协议内容或其执行发生任何争议，双方应尽力友好协商解决；协商不成时，任何一方均可向尚品网(shangpin.com)或尚品奥莱网(aolai.com)所在地的人民法院提起诉讼。</p>
            <p><h2>商业条款 Commercial Terms</h2></p>
            <p>尚品网(shangpin.com)或尚品奥莱网(aolai.com)保留取消任何被我们的合作银行或第三方在线支付平台质疑身份真实性的订单的权利。尚品网(shangpin.com)或尚品奥莱网(aolai.com)保留在任何时间拒绝履行任何有疑问或被怀疑有信用欺诈嫌疑的订单。</p>
            <p>尚品网(shangpin.com)或尚品奥莱网(aolai.com)以服务品牌厂商、服务全球精明时尚消费者为使命。根据我们与合作品牌的协议，我们创造和推广的服务仅提供给最终端消费者。</p>
            <p>在尚品网(shangpin.com)或尚品奥莱网(aolai.com)网站上公布出售的商品，意在销售给最终端消费者。尚品网(shangpin.com)或尚品奥莱网(aolai.com)这样定义"最终端消费者"，即"个人消费者，购买尚品网(shangpin.com)或尚品奥莱网(aolai.com)销售的商品作为自己个人使用，而不是以此从事任何形式的再销售和分销"。</p>
            <p>因此，尚品网(shangpin.com)或尚品奥莱网(aolai.com)提示任何不在"最终端消费者"定义范围内的用户，放弃以各种手段直接或间接从尚品网(shangpin.com)或尚品奥莱网(aolai.com)购买商品并从事再销售的企图。</p>
            <p>根据如上所述的商业条款，尚品网(shangpin.com)或尚品奥莱网(aolai.com)保留取消任何违背上述商业条款和"最终端消费者"定义的订单的权利。</p>
            <p>尚品网社区条款 Community Terms</p>
            <p>本社区命名为"尚品社区"。</p>
            <p>本社区所有权、经营权均属尚品网。</p>
            <p>本社区的管理维护由尚品网社区部门负责</p>
            <p><strong>社区用户</strong></p>
            <p>在尚品网注册的用户，可成为尚品社区注册用户（以下简称"社区用户"），拥有社区用户基本权限。</p>
            <p>本社区用户的个人资料受到保护，不接受任何个人或单位的查询请求，公安机关和司法部门或根据国家相关法律规定提供除外。</p>
            <p>本社区用户有义务遵守国家法律法规及本社区各项规章制度。</p>
            <p>本社区用户有义务遵守网络礼仪。</p>
            <p>社区用户享有言论自由权利，但社区用户不得在本社区发表包含以下内容的言论：</p>
            <p>（一）煽动抗拒、破坏宪法和法律、行政法规实施的；</p>
            <p>（二）煽动颠覆国家政权，推翻社会主义制度的；</p>
            <p>（三）煽动分裂国家、破坏国家统一的；</p>
            <p>（四）煽动民族仇恨、民族歧视，破坏民族团结的；</p>
            <p>（五）任何包含对种族、性别、宗教、地域内容等歧视的；</p>
            <p>（六）捏造或者歪曲事实，散布谣言，扰乱社会秩序的；</p>
            <p>（七）宣扬封建迷信、淫秽、色情、赌博、暴力、凶杀、恐怖、教唆犯罪的；</p>
            <p>（八）公然侮辱他人或者捏造事实诽谤他人的，或者进行其他恶意攻击的；</p>
            <p>（九）损害国家机关信誉的；</p>
            <p>（十）其他违反宪法和法律行政法规的。 </p>
            <p><strong>版权声明</strong></p>
            <p>本社区用户发表的留言仅代表作者本人观点，与尚品网立场无关。作者文责自负。 </p>
            <p>尚品网有权将在本社区发表的非规范留言进行处理，如删除、修改等。</p>
            <p>未经尚品网同意，禁止在本社区内发布任何形式的广告。</p>
            <p><strong>处罚原则</strong></p>
            <p>本社区用户不得在本社区进行任何违反国家法律法规及社区各项规章制度的活动，不得在本社区进行任何破坏公共安全的活动，不得在本社区进行任何非法商业活动，不得在本社区进行任何破坏公共秩序的活动，如有违反，社区管理人员将依据有关规定进行处罚。</p>
            <p>本社区用户不得利用社区BUG进行任何活动，如有违反，社区有权作出关闭其部分权限、暂停帐号使用直至删除帐号处理，同时社区保留追究责任人法律及经济责任的权利。</p>
            <p><strong>责任声明</strong></p>
            <p>本社区用户因为违反本社区规定而触犯中华人民共和国法律的，责任自负，尚品网不承担任何责任。</p>
        </div>
        <em class="close_pop"></em>
    </div>
</rapid:override>

<rapid:override name="footer">

</rapid:override>
<%@ include file="/WEB-INF/pages/common/common.jsp" %>





