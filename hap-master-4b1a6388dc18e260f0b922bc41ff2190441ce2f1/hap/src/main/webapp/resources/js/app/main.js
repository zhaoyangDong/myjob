var tab = null;
var tabStrip = null;
var accordion = null;
var tree = null;
var tabIds = [];
$(function ()
{
	// 布局
	$("#layout1").ligerLayout({ leftWidth: 190, height: '100%',heightDiff:-34,space:4, onHeightChanged: f_heightChanged });

	var height = $(".l-layout-center").height();

	// TabStrip
	$("#tabstrip").css("height", height);
	tabStrip = $("#tabstrip").kendoTabStrip({
		animation: {
            open: {
            	duration: 200,
                effects: "fadeIn"
            }
		},
		collapsible: true,
		navigatable: false,
		select: function(element) {
			var that = this;
			tabStrip.deactivateTab(tabStrip.tabGroup.children(".k-state-active"));
			that.activateTab(element.item);
			return that;
		}
	}).data("kendoTabStrip");
	tabStrip.activateTab(tabStrip.tabGroup.children("li")[0]);
	tabIds = ["home"];
	
	// ContextMenu
	var contextMenu = [ {
		text : '关闭当前页',
		encoded : false
	}, {
		text : "关闭其他",
		encoded : false
	}, {
		text : "关闭所有",
		encoded : false
	}, {
		text : "刷新",
		encoded : false
	} ];
	$("#tabstrip-context-menu").kendoContextMenu({
        target: ".k-tabstrip-items .k-item",
        dataSource:contextMenu,
        select: function(e) {
        	var text = $(e.item).find('span')[0].innerHTML;
        	if (text === "关闭当前页" && $(e.target).find('a')[0].getAttribute("tabid") != "home") {
        		f_removeCurrentTab($(e.target).find('a')[0].getAttribute("tabid"));
        	} else if (text === "关闭其他") {
        		f_removeOtherTab($(e.target).find('a')[0].getAttribute("tabid"));
        	} else if (text === "关闭所有") {
        		f_removeAllTab();
        	} else if (text === "刷新") {
        		f_refrshTab($(e.target).find('a')[0].getAttribute("tabid"));
        	}
        }
    })

	// 面板
	$("#accordion1").ligerAccordion({
		height: height - 31, speed: null
	});
	
	$(".l-link").hover(function ()
	{
		$(this).addClass("l-link-over");
	}, function ()
	{
		$(this).removeClass("l-link-over");
	});

	accordion = liger.get("accordion1");
	$("#pageloading").hide();
	
	function createMenu(html,datas){
	    for(var i=0;i<datas.length;i++){
	        var data = datas[i];
            html.push('<li>');
            if(data.children){
                html.push('<a href="javascript:;">');
                html.push('<i class="'+(data.icon||'')+'"></i>');
                html.push('<span class="title">');
                html.push(data.text);
                html.push('</span><span class="arrow "></span></a><ul class="sub-menu">');
                createMenu(html,data.children);
                html.push('</ul>')
            } else if(data.url){
                html.push('<a id="link'+data.id+'" href="javascript:f_addTab(\''+data.functionCode+'\',\''+data.text+'\', \''+data.url+'\')">');
                html.push('<i class="'+(data.icon||'')+'"></i>');
                html.push(data.text);
                html.push('</a>')
            }
            html.push('</li>')
        }
	}
	// menu
    $.ajax({
        type: 'GET',
        url:_basePath + '/sys/function/menus',
        contentType: "application/json; charset=utf-8",
        success: function (datas) {
            var html=[];
            datas = [].concat(datas);
            createMenu(html,datas);
            $(".page-sidebar-menu").append(html.join(''));
        }
    });
	
});
function f_heightChanged(options)
{

	var _height = $("#tabstrip").css("height");
	_height = _height.substring(0, _height.length-2);
	$("#tabstrip").css("height", Number(_height) + options.diff);
	if (accordion && options.middleHeight - 24 > 0)
		accordion.setHeight(options.middleHeight - 24);
}
function f_addTab(tabid, text, url)
{
	var index = tabIds.indexOf(tabid);
	tabStrip = $("#tabstrip").kendoTabStrip().data("kendoTabStrip");
	setTimeout(function() {
		if (index < 0) {
			tabIds.push(tabid);
			tabStrip.append({
				id : tabid,
				text : f_getTabTitle(text, index, tabid),
				encoded : false,
				content : "<iframe frameborder='0' src='" + url + "' name='"
						+ tabid + "' id='" + tabid + "'></iframe>"
			})
			tabStrip.select(tabStrip.tabGroup.children("li").length - 1);
		} else {
			tabStrip.select(index);
		}
		$("#tabstrip-context-menu").data("kendoContextMenu").setOptions({target: ".k-tabstrip-items .k-item"});
		$('.page-sidebar-menu').find('a.active').removeClass('active');
		$('#link'+tabid).addClass('active');
	}, 100);
	
}
function f_removeTab(tabId){
	setTimeout(function() {
		var index = tabIds.indexOf(tabId);
		$("#tabstrip").kendoTabStrip().data("kendoTabStrip")
			.remove(index).select(index - 1);
		tabIds.splice(index, 1);
	},100);
}
function f_removeCurrentTab(tabId){
	f_removeTab(tabId);
}
function f_removeOtherTab(tabId){
	setTimeout(function() {
		if (tabIds.length > 2) {
			var index = tabIds.indexOf(tabId)
				tabStrip = $("#tabstrip").kendoTabStrip().data("kendoTabStrip");
			if (index === tabIds.length -1) {
				tabStrip.remove(tabStrip.tabGroup.children("li").slice(1,index));
			} else {
				tabStrip.remove(tabStrip.tabGroup.children("li").slice(index + 1));
				tabStrip.remove(tabStrip.tabGroup.children("li").slice(1,index));
			}
			tabStrip.select(1);
			var _tabIds = ["home"];
			_tabIds.push(tabIds[index]);
			tabIds = _tabIds;
		}
	},100);
}
function f_removeAllTab(){
	setTimeout(function() {
		if (tabIds.length >= 2) {
			tabStrip = $("#tabstrip").kendoTabStrip().data("kendoTabStrip");
			tabStrip.remove(tabStrip.tabGroup.children("li").slice(1));
			tabStrip.select(0);
			tabIds.splice(1, tabIds.length-1);
		}
	},100);
}
function f_refrshTab(tabId){
	setTimeout(function() {
		$("#" + tabId).attr('src', $("#" + tabId).attr('src'));
	},100);
}
function f_getTabTitle(text, index, tabid) {
	return '<a tabid="' + tabid + '">'
			+ text + '&nbsp;&nbsp;&nbsp;&nbsp;<span class="l-tab-links-item-close" onclick="f_removeTab(\''
			+ tabid + '\')"></span></a>';
}