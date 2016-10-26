/**
 * Created by wenyu on 2016/9/8.
 */
(function() {
    var quark_id;
    var W, J, N, c_s,c_s2;
    var com = {};
    var tpm = "";
    function sp_g_c_i() {
        var tempc = {};
        tempc.os = N.platform;
        tempc.app = N.appName;
        tempc.ver = N.appVersion;
        tempc.agent = N.userAgent;
        tempc.ec = N.cookieEnabled;
        tempc.ej = N.javaEnabled();
        tempc.fv = sp_g_fv();
        tempc.lg = sp_g_lg();
        tempc.screen = sp_g_screen();
        return tempc
    }
    function sp_g_fv() {
        var f = "";
        if (N.plugins && N.plugins.length) {
            for (var ii = 0; ii < N.plugins.length; ii++) {
                if (N.plugins[ii].name.indexOf("Shockwave Flash") != -1) {
                    f = N.plugins[ii].description.split("Shockwave Flash ")[1];
                    break
                }
            }
        } else {
            if (W.ActiveXObject) {
                for (var ii = 10; ii >= 2; ii--) {
                    try {
                        var fl = eval("new ActiveXObject('ShockwaveFlash.ShockwaveFlash." + ii + "');");
                        if (fl) {
                            f = ii + ".0";
                            break
                        }
                    } catch(e) {}
                }
            }
        }
        return f
    }
    function sp_g_lg() {
        var l = "";
        if (N.language) {
            l = N.language.toLowerCase()
        } else {
            if (N.browserLanguage) {
                l = N.browserLanguage.toLowerCase()
            }
        }
        return l
    }
    function sp_g_screen() {
        var screen = self.screen;
        return temps = {
            bit: screen.colorDepth,
            px: screen.pixelDepth,
            height: screen.height,
            width: screen.width,
            availHeight: screen.availHeight,
            availWidth: screen.availWidth
        }
    }
    function sp_g_u_i() {
        var u = {};
        var uv_str = sp_get_cookie("quark_uv");
        uv_str = decodeURI(uv_str);
        var uv_id = "";
        var uv_new = 0;
        if (uv_str == "") {
            uv_new = 1;
            var rand1 = parseInt(Math.random() * 4000000000);
            var rand2 = parseInt(Math.random() * 4000000000);
            uv_id = String(rand1) + String(rand2);
            var value = uv_id + "|" + quark_id;
            sp_set_cookie("quark_uv", value, 1)
        } else {
            var arr = uv_str.split("|");
            uv_id = arr[0];
            var uids_str = arr[1];
            if (uids_str != quark_id) {
                uv_new = 1;
                var value = uv_id + "|" + quark_id;
                sp_set_cookie("quark_uv", value, 1)
            }
        }
        u.uv_id = uv_id;
        u.uv_new = uv_new;
        var uid_str = sp_get_cookie("behaviorflag");
        u.u_id = uid_str;
        return u
    }
    function sp_g_url() {
        return J.URL
    }
    function sp_g_title() {
        return J.title
    }
    function sp_g_charset() {
        return J.characterSet || J.charset
    }
    function sp_g_dm() {
        var host = J.location.host;
        var d = host.replace(/^www\./, "");
        var ss = d.split(".");
        var l = ss.length;
        if (l == 3) {
            if (sp_c_ctry_top_domain(ss[1]) && sp_c_ctry_domain(ss[2])) {} else {
                d = ss[1] + "." + ss[2]
            }
        } else {
            if (l >= 3) {
                var ip_pat = "^[0-9]*.[0-9]*.[0-9]*.[0-9]*$";
                if (host.match(ip_pat)) {
                    return d
                }
                var ip_pat_port = "^[0-9]*.[0-9]*.[0-9]*.[0-9]*(:[0-9]*)?$";
                if (host.match(ip_pat_port)) {
                    return host.match("^[0-9]*.[0-9]*.[0-9]*.[0-9]*")
                }
                if (sp_c_ctry_top_domain(ss[l - 2]) && sp_c_ctry_domain(ss[l - 1])) {
                    d = ss[l - 3] + "." + ss[l - 2] + "." + ss[l - 1]
                } else {
                    d = ss[l - 2] + "." + ss[l - 1]
                }
            }
        }
        return d
    }
    function sp_c_ctry_top_domain(str) {
        var pattern = "/^aero$|^cat$|^coop$|^int$|^museum$|^pro$|^travel$|^xxx$|^com$|^net$|^gov$|^org$|^mil$|^edu$|^biz$|^info$|^name$|^ac$|^mil$|^co$|^ed$|^gv$|^nt$|^bj$|^hz$|^sh$|^tj$|^cq$|^he$|^nm$|^ln$|^jl$|^hl$|^js$|^zj$|^ah$|^hb$|^hn$|^gd$|^gx$|^hi$|^sc$|^gz$|^yn$|^xz$|^sn$|^gs$|^qh$|^nx$|^xj$|^tw$|^hk$|^mo$|^fj$|^ha$|^jx$|^sd$|^sx$/i";
        if (str.match(pattern)) {
            return 1
        }
        return 0
    }
    function sp_c_ctry_domain(str) {
        var pattern = "/^ac$|^ad$|^ae$|^af$|^ag$|^ai$|^al$|^am$|^an$|^ao$|^aq$|^ar$|^as$|^at$|^au$|^aw$|^az$|^ba$|^bb$|^bd$|^be$|^bf$|^bg$|^bh$|^bi$|^bj$|^bm$|^bo$|^br$|^bs$|^bt$|^bv$|^bw$|^by$|^bz$|^ca$|^cc$|^cd$|^cf$|^cg$|^ch$|^ci$|^ck$|^cl$|^cm$|^cn$|^co$|^cr$|^cs$|^cu$|^cv$|^cx$|^cy$|^cz$|^de$|^dj$|^dk$|^dm$|^do$|^dz$|^ec$|^ee$|^eg$|^eh$|^er$|^es$|^et$|^eu$|^fi$|^fj$|^fk$|^fm$|^fo$|^fr$|^ly$|^hk$|^hm$|^hn$|^hr$|^ht$|^hu$|^id$|^ie$|^il$|^im$|^in$|^io$|^ir$|^is$|^it$|^je$|^jm$|^jo$|^jp$|^ke$|^kg$|^kh$|^ki$|^km$|^kn$|^kp$|^kr$|^kw$|^ky$|^kz$|^la$|^lb$|^lc$|^li$|^lk$|^lr$|^ls$|^lt$|^lu$|^lv$|^ly$|^ga$|^gb$|^gd$|^ge$|^gf$|^gg$|^gh$|^gi$|^gl$|^gm$|^gn$|^gp$|^gq$|^gr$|^gs$|^gt$|^gu$|^gw$|^gy$|^ma$|^mc$|^md$|^mg$|^mh$|^mk$|^ml$|^mm$|^mn$|^mo$|^mp$|^mq$|^mr$|^ms$|^mt$|^mu$|^mv$|^mw$|^mx$|^my$|^mz$|^na$|^nc$|^ne$|^nf$|^ng$|^ni$|^nl$|^no$|^np$|^nr$|^nu$|^nz$|^om$|^re$|^ro$|^ru$|^rw$|^pa$|^pe$|^pf$|^pg$|^ph$|^pk$|^pl$|^pm$|^pr$|^ps$|^pt$|^pw$|^py$|^qa$|^wf$|^ws$|^sa$|^sb$|^sc$|^sd$|^se$|^sg$|^sh$|^si$|^sj$|^sk$|^sl$|^sm$|^sn$|^so$|^sr$|^st$|^su$|^sv$|^sy$|^sz$|^tc$|^td$|^tf$|^th$|^tg$|^tj$|^tk$|^tm$|^tn$|^to$|^tp$|^tr$|^tt$|^tv$|^tw$|^tz$|^ua$|^ug$|^uk$|^um$|^us$|^uy$|^uz$|^va$|^vc$|^ve$|^vg$|^vi$|^vn$|^vu$|^ye$|^yt$|^yu$|^za$|^zm$|^zr$|^zw$/i";
        if (str.match(pattern)) {
            return 1
        }
        return 0
    }
    function sp_g_bd() {
        return "";
    }
    function _isApp(){
        var isapp = "";
        try{

            isapp = document.getElementById("_isapp");
            if (isapp)
                return isapp.value;
        }catch(e){
            console.log(e.message);
        }

        return "";
    }

    function getImei(){
        var imei = "";
        try{

            imei = document.getElementById("imei");
            if (imei)
                return imei.value;
        }catch(e){
            console.log(e.message);
        }

        return "";
    }
    function sp_get_cookie(name) {
        var mn = name + "=";
        var b, e;
        var co = J.cookie;
        if (mn == "=") {
            return co
        }
        b = co.indexOf(mn);
        if (b < 0) {
            return ""
        }
        e = co.indexOf(";", b + name.length);
        if (e < 0) {
            return co.substring(b + name.length + 1)
        } else {
            return co.substring(b + name.length + 1, e)
        }
    }
    function sp_set_cookie(name, val, cotp) {
        var date = new Date;
        var year = date.getFullYear();
        var hour = date.getHours();
        var cookie = "";
        if (cotp == 0) {
            cookie = name + "=" + val + ";"
        } else {
            if (cotp == 1) {
                year = year + 10;
                date.setYear(year);
                cookie = name + "=" + val + ";expires=" + date.toGMTString() + ";"
            } else {
                if (cotp == 2) {
                    hour = hour + 1;
                    date.setHours(hour);
                    cookie = name + "=" + val + ";expires=" + date.toGMTString() + ";"
                }
            }
        }
        var d = sp_g_dm();
        if (d != "") {
            cookie += "domain=" + d + ";"
        }
        cookie += "path=" + "/;";
        J.cookie = cookie
    }
    function sp_get_stm() {
        var date = new Date();
        var yy = date.getFullYear();
        var mm = date.getMonth();
        var dd = date.getDate();
        var hh = date.getHours();
        var ii = date.getMinutes();
        var ss = date.getSeconds();
        var ms = date.getMilliseconds();
        var tm = "";
        tm = yy + "-" + mm + "-" + dd + " " + hh + ":" + ii + ":" + ss + "." + ms;
        return tm
    }
    function quark_encode(str) {
        var e = "",
            i = 0;
        for (i = 0; i < str.length; i++) {
            if (str.charAt(i) == "#") {
                e = e + "%23"
            } else {
                if (str.charCodeAt(i) >= 0 && str.charCodeAt(i) <= 255) {
                    e = e + encodeURIComponent(str.charAt(i))
                } else {
                    e = e + str.charAt(i)
                }
            }
        }
        return e
    }
    function init() {
        quark_id = "quark_2.0.0";
        c_s = "http://quark.shangpin.com/quark/img/sp.png";
        c_s2 = "http://logtrack.shangpin.com/c.gif";
        W = window;
        J = document;
        N = navigator;
        com.c = sp_g_c_i();
        com.u = sp_g_u_i();
        com.url = sp_g_url();
        com.title = sp_g_title();
        com.charset = sp_g_charset();
        com.dm = sp_g_dm();
        J.onclick = c_p;
        v_p()
    }
    function v_p() {
        var p = {};
        p.quark_id = quark_id;
        p.bt = "visit";
        p.tm = new Date().getTime();
        p.com = com;
        p.ref = J.referrer;
        p.bd = sp_g_bd();
        p.isApp=_isApp();
        p.imei = getImei();
        pc(p)
    }
    function c_p(ev) {
        var c = {};
        c.quark_id = quark_id;
        c.bt = "click";
        c.tm = new Date().getTime();
        c.com = com;
        c.isApp=_isApp();
        c.imei = getImei();
        c.bd = sp_g_bd();
        ev = ev || W.event;
        var target = ev.target || ev.srcElement;
        c.ev = {};
        c.ev.et = ev.type;
        c.ev.cm = ev.button;
        var tag = {};
        tag.tagName = target.nodeName;
        tag.id = target.id;
        tag.type = target.type;
        tag.src = target.src;
        tag.href = target.href;
        tag.ctag = target.getAttribute("ctag");
        /* 冗余处理ctag */
        if(!tag.ctag){
            var tag_p = target.parentNode;
            if(tag_p){
                if(!tag_p.getAttribute("ctag")){
                    var tag_pp = tag_p.parentNode;
                    if(tag_pp){
                        if(!tag_pp.getAttribute("ctag")) {
                            var tag_ppp = tag_pp.parentNode;
                            if(tag_ppp && tag_ppp.getAttribute("ctag")){
                                tag.ctag = tag_ppp.getAttribute("ctag");
                            }
                        }else{
                            tag.ctag = tag_pp.getAttribute("ctag");
                        }
                    }
                }else{
                    tag.ctag = tag_p.getAttribute("ctag");
                }
            }
        }

        c.ev.tag = tag;
        var point = {
            x: 0,
            y: 0
        };
        if (ev.pageX || ev.pageY) {
            point.x = ev.pageX;
            point.y = ev.pageY
        } else {
            point.x = ev.clientX + J.body.scrollLeft - J.body.clientLeft;
            point.y = ev.clientY + J.documentElement.scrollTop
        }
        c.ev.point = point;
        pc(c)
    }
    function pc(p) {
        ir(p);
    }
    function ir(p) {
        //�ϱ�c_s
        var img = document.getElementById("quark-image");
        var dest = c_s + "?" + bp(p)+"&time="+Math.random();;
        if (img) {
            img.src = dest
        } else {
            img = '<img style="display: none" id="quark-image" border="0" width="1" height="1" src=\'' + dest + "' />";
            document.write(img)
        }
        //�ϱ�c_s2
        var img2 = document.getElementById("quark-image2");
        var dest2 = c_s2 + "?" + bp(p)+"&time="+Math.random();
        if (img2) {
            img2.src = dest2
        } else {
            img2 = '<img style="display: none" id="quark-image2" border="0" width="1" height="1" src=\'' + dest2 + "' />";
            document.write(img2)
        }
    }

    function bp(p) {
        if (p) {
            if (tpm == '') {
                tpm = "quark_id=" + p.quark_id + "&os=" + p.com.c.os + "&app=" + p.com.c.app + "&ver=" + quark_encode(p.com.c.ver) + "&ag=" + quark_encode(p.com.c.agent) + "&ec=" + p.com.c.ec + "&ej=" + p.com.c.ej + "&fv=" + quark_encode(p.com.c.fv) + "&lg=" + quark_encode(p.com.c.lg) + "&bit=" + p.com.c.screen.bit + "&px=" + p.com.c.screen.px + "&ht=" + p.com.c.screen.height + "&wt=" + p.com.c.screen.width + "&aht=" + p.com.c.screen.availHeight + "&awt=" + p.com.c.screen.availWidth + "&uvid=" + p.com.u.uv_id + "&uv_new=" + p.com.u.uv_new + "&uid=" + p.com.u.u_id + "&url=" + escape(p.com.url) + "&tl=" + escape(p.com.title) + "&cs=" + p.com.charset + "&dm=" + p.com.dm+"&isapp="+p.isApp+"&imei="+p.imei;
            }

            if (p.bt == 'click') {
                return tpm + "&bt=" + p.bt + "&tm=" + p.tm + "&et=" + p.ev.et + "&cm=" + p.ev.cm + "&ttn=" + p.ev.tag.tagName + "&tid=" + p.ev.tag.id + "&ttp=" + p.ev.tag.type + "&tsr=" + escape(p.ev.tag.src) + "&thf=" + escape(p.ev.tag.href) + "&ptx=" + p.ev.point.x + "&pty=" + p.ev.point.y +"&ctag="+p.ev.tag.ctag
            } else {
                return tpm + "&bt=" + p.bt + "&tm=" + p.tm + "&ref=" + escape(p.ref) + "&bd=" + p.bd
            }
        }
    }
    init()
} ());

