	var dataTable ;
	function initSinglesTableByAjax(id){
		if(dataTable!=null){
			dataTable.fnClearTable();//清空数据表
		}
		dataTable = $("#eachRow").dataTable( {
			"bProcessing": true,
			"bServerSide": false,		//因为不是服务端指定的数据，所以设置为false，不然会出现NAN
			"sAjaxSource": "/OnLineTest/teacher/ajax/knowledge2singles?opvalue="+id,
			"bDestroy":true,
			"aaSorting": [[ 3, "asc" ]],	//初始化的时候排序的标准,这里对时间升序是过去的比较大(靠口)
			"aoColumns": [  				//对应从json中返回的数据列
				{ "mData": null},
				{ "mData": "content"},
				{ "mData": "difficulty"},
				{ "mData": "createTime"},
				{ "mData": "id"}
			],
			"sScrollX" : "100%",//'disabled' or  '100%' 类似的字符串 是否开启水平滚动，以及指定滚动区域大小 
			"sScrollXInner" : "100%",//当x-scrolling,x方向滚动条启动时，sScrollXInner迫使DataTable使用更宽的
			"oLanguage" : {
				"bPaginate": true, //开关，是否显示分页器
				"sProcessing" : "正在加载中......",
				"sLengthMenu" : "每页显示 _MENU_ 条记录",
				"sZeroRecords" : "对不起，查询不到相关数据！",
				"sEmptyTable" : "表中无数据存在！",
				"sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
				"sInfoFiltered" : "数据表中共为 _MAX_ 条记录",
				"sSearch" : "搜索",
				"oPaginate" : {
					"sFirst" : "首页",
					"sPrevious" : "上一页",
					"sNext" : "下一页",
					"sLast" : "末页"
				}
			},
			"fnRowCallback":fnRowCallback,
			"aoColumnDefs": [
				//隐藏试题号列
				{ "bSearchable": true, "bVisible": false, "aTargets": [4]},
				//指定列的样式
				{ "sClass": "center","aTargets": [0,1,2,3]}
			]
		});
		clearSingleTemplateDIV();
	}
	
	/**
		@param node nRow:表示创建后的tr对象(jquery对象)
		@param array aData : 表示一行Html记录，是一个jquery对象用对象的属性直接访问aData['content'|'id'|'diffculty']
		@param int iDisplayIndex: 显示的行号
		@see 
			1:获取每行的content（去除前后显示25个字符,字符串截取区间[0,25]）内容，做拼接操作提供缩显操作，
			2：添加复选框
			3:将难度显示数字替换为文字（1：简单  0：一般  -1：困难）
			4:绑定事件为每一行,并更改手势
	*/
	var strPeel = "••••••";
	function fnRowCallback( nRow, aData, iDisplayIndex){
			//更改文本域内容
			var hiddenContent = $.trim(aData['content'])
			if($.trim(hiddenContent).length>35){
				hiddenContent = hiddenContent.substring(0,35)+strPeel;
			}
			$('td:eq(1)',nRow).html(hiddenContent);
			//添加复选框
			$('td:eq(0)',nRow).html('<input type="checkbox" id="options" title="'+aData['id']+'"/>');
			//为tr添加click事件,并更改手势
			$(nRow).css("cursor","pointer").click( function () { 
				var bId = $("#bId").val();
				if(aData['id']!=bId){//同一行点击2次只加载一次
					loadSingleTemplateDIV(aData['id']);
				}
			});
	}

	//加载的试题信息
	function loadSingleTemplateDIV(questionID){
		var opvalue = questionID;
		// 设置与服务端进行交互的URL(包括中文处理),每次更改了知识点后就更新分页下标（由服务端更新）/teacher/ajax/knowledge2singles
		var url = "/OnLineTest/teacher/ajax/singleId2Singe?opvalue=" + encodeURI(encodeURI(opvalue));
		url = covertURL(url);
		// 与服务端进行交互,并显示数据
		$.getJSON(url, null, function(data) {
			$("#bId").val(questionID);
			$("#content").html(data.content);
			$("#SA").val(data.SA);
			$("#SB").val(data.SB);
			$("#SC").val(data.SC);
			$("#SD").val(data.SD);
			$("#result").val(data.result);
			$("#difficulty").val(data.difficulty);	
			//$("#difficulty").val(data.difficulty);
			$("#analyse").val(data.analyse);
			$("#createTime").val(data.createTime);
			var imgUrl = "/OnLineTest/questionBankImage/"+data.image+"?random=" + (new Date()).getTime();
			$("#questionImage").attr("src",imgUrl);
			$("#floatImage").attr("href",imgUrl);
			$("#floatImage").attr("title","单选试题图片");
		});
	}
	//初始化或还原显示的试题信息
	function clearSingleTemplateDIV(){
		$("#bId").val("0");//这里填0，为了和第一点击时的Id对比,记录试题的编号
		$("#content").html("此处尚未加载内容");
		$("#SA").val("此处尚未加载内容");
		$("#SB").val("此处尚未加载内容");
		$("#SC").val("此处尚未加载内容");
		$("#SD").val("此处尚未加载内容");
		$("#result").val("未知");
		$("#difficulty").val("未知");
		$("#analyse").val("此处尚未加载内容");
		$("#createTime").val("未知");
		$("#questionImage").attr("src","/OnLineTest/questionBankImage/defaultQuestBank.jpg");//表示此处暂无图片
		$("#floatImage").attr("href","/OnLineTest/questionBankImage/defaultQuestBank.jpg");
		$("#floatImage").attr("title","单选试题图片");
	}
