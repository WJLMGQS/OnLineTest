	var dataTable ;
	function initPaperTableByAjax(){
		if(dataTable!=null){
			dataTable.fnClearTable();//清空数据表
		}
		dataTable = $("#eachRow").dataTable( {
			"bProcessing": true,
			"bServerSide": false,		//因为不是服务端指定的数据，所以设置为false，不然会出现NAN
			"sAjaxSource": "/OnLineTest/teacher/ajax/subject2papers?opvalue="+$("#subjectId").val()+"&totalMark="+$("#totalMark").val()+"&finishTime="+$("#finishTime").val(),
			"bDestroy":true,
			"aaSorting": [[ 9, "asc" ]],	//初始化的时候排序的标准,这里对时间升序是过去的比较大(靠口)
			"aoColumns": [  				//对应从json中返回的数据列
			    { "mData": null},
				{ "mData": "teacher.userId"},
				{ "mData": "name"},
				{ "mData": "bulidType"},
				{ "mData": "useType"},
				{ "mData": "teacher.subject.code"},
				{ "mData": "difficulty"},
				{ "mData": "createTime"},
				{ "mData": "updateTime"},
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
				{ "bSearchable": true, "bVisible": false, "aTargets": [9]},
				//指定列的样式
				{ "sClass": "center","aTargets": [0,1,2,3,4,5,6,7,8]}
			]
		});
	}
	
	/**
		@param node nRow:表示创建后的tr对象(jquery对象)
		@param array aData : 表示一行Html记录，是一个jquery对象用对象的属性直接访问
		@param int iDisplayIndex: 显示的行号
		@see 
			2：添加复选框
			3:将难度显示数字替换为文字（1：简单  0：一般  -1：困难）
			4:绑定事件为每一行,并更改手势
	*/
	function fnRowCallback( nRow, aData, iDisplayIndex){
			//添加复选框
			$('td:eq(0)',nRow).html('<input type="checkbox" name="options" value="'+aData['id']+'"/>');
	}