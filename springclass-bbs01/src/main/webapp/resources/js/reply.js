$(function(){
	
	// 삭제
		$(document).on("click", ".deleteReply", function(){
			$("#global-content > div").append($("#replyForm").slideUp(300));
			$("#replyContent").val("");
			
			var no = $(this).attr("data-no");
			var writer = $(this).parent().prev().find("span").text();
			var bbsNo = $("#replyForm input[name=bbsNo]").val();
			var params = "no=" + no + "&bbsNo=" + bbsNo;
			console.log(params);
			
			var result = confirm(writer + "님이 작성한 " + no + "번 댓글을 삭제하시겠습니까 ?")
					
				if(result) {
					$.ajax({
						url: "replyDelete.ajax",
						type : "post",
						data : params,
						dataType : "json",
						success : function(resData){
							console.log(resData)
							
							$("#replyList").empty();
							$.each(resData, function(i, v){
								
								var date = new Date(v.regDate);
								var strDate = date.getFullYear() + "-" + ((date.getMonth() + 1 < 10)
										? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "-"
										+ (date.getDate() < 10 ? "0" + date.getDate() :
										date.getDate()) + " "
										+ (date.getHours() < 10 ? "0" + date.getHours() :
										date.getHours()) + ":"
										+ (date.getMinutes() < 10 ? "0" + date.getMinutes() :
										date.getMinutes()) + ":"
										+ (date.getSeconds() < 10 ? "0" + date.getSeconds() :
										date.getSeconds());
								
								var result = 
										`<div class="row replyRow border border-top-0">
			                    <div class="col">
			                        <!--  댓글 작성자 수정 삭제 신고 버튼 영역 -->
			                        <div class="row p-2 border-bottom align-items-center">
			                            <div class="col-4">
			                                <span>${v.replyWriter}</span>
			                            </div>
			                            <div class="col-8 text-end">
			                                <span class="me-3">${strDate}</span>
			                                <button class="modifyReply btn btn-outline-success btn-sm" data-no="${v.no}"><i class="bi bi-journals"></i> 수정</button>
			                                <button class="deleteReply btn btn-outline-warning btn-sm" data-no="${v.no}"><i class="bi bi-trash3"></i> 삭제</button>
			                                <button class="reportReply btn btn-outline-danger btn-sm" data-no="${v.no}"><i class="bi bi-fire"></i> 신고</button>
			                            </div>
			                        </div>
			                        <!-- 댓글 내용 영역 -->
			                        <div class="row">
			                            <div class="col p-3">
			                                <pre>${v.replyContent}</pre>
			                            </div>
			                        </div>
			                    </div>
			                </div>`;
										$("#replyList").append(result);
								
							}) // end each
						},
						error : function (xhr, status, error){
							console.log("ajax 실패 : " + status + "-" + xhr.status);
						}
					});
				}
					return false;
		})
	
	
	// 수정
	$(document).on("click", ".modifyReply", function(){
		console.log($("#replyForm").css("display"));
		console.log($("#replyForm").is(":visible"));
		
		// 수정 버튼이 클릭된 최상의 부모를 구한다.
		console.log($(this).parents(".replyRow"));
		var $replyRow = $(this).parents(".replyRow");
		
		// 댓글쓰기 폼이 화면에 보이는 상태라면
		if($("#replyForm").is(":visible")) {
			var $next = $replyRow.next();
			if(! $next.is("#replyForm")) {
				$("#replyForm").slideUp(300);
			}
			setTimeout(function(){
				$("#replyForm").insertAfter($replyRow).slideDown(300);
			}, 300);
		} else {
			$("#replyForm").removeClass("d-none").css("display", "none").insertAfter($replyRow).slideDown(300);
		}
		$("#replyForm").find("form").attr({"id": "replyUpdateForm", "data-no":$(this).attr("data-no")});
		console.log($(this).attr("data-no"));
		$("#replyWriteButton").val("댓글수정");
		
		var reply = $(this).parent().parent().next().find("pre").text();
		$("#replyContent").val($.trim(reply));
	});
	
	// 댓글 수정폼이 submit 될 때
	$(document).on("submit", "#replyUpdateForm", function(){
		if($("#replyContent").val().length <= 5 ) {
			alert("댓글은 5글자 이상 입력해야 합니다.");
			return false;
		}
		$("#global-content > div").append($("#replyForm").slideUp(300));
		
		var params = $(this).serialize() + "&no=" + $(this).attr("data-no");
		console.log(params);
		
		$.ajax({
			url: "replyUpdate.ajax",
			type : "post",
			data : params,
			dataType : "json",
			success : function(resData){
				console.log(resData)
				
				$("#replyList").empty();
				$.each(resData, function(i, v){
					
					var date = new Date(v.regDate);
					var strDate = date.getFullYear() + "-" + ((date.getMonth() + 1 < 10)
							? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "-"
							+ (date.getDate() < 10 ? "0" + date.getDate() :
							date.getDate()) + " "
							+ (date.getHours() < 10 ? "0" + date.getHours() :
							date.getHours()) + ":"
							+ (date.getMinutes() < 10 ? "0" + date.getMinutes() :
							date.getMinutes()) + ":"
							+ (date.getSeconds() < 10 ? "0" + date.getSeconds() :
							date.getSeconds());
					
					var result = 
							`<div class="row replyRow border border-top-0">
                    <div class="col">
                        <!--  댓글 작성자 수정 삭제 신고 버튼 영역 -->
                        <div class="row p-2 border-bottom align-items-center">
                            <div class="col-4">
                                <span>${v.replyWriter}</span>
                            </div>
                            <div class="col-8 text-end">
                                <span class="me-3">${strDate}</span>
                                <button class="modifyReply btn btn-outline-success btn-sm" data-no="${v.no}"><i class="bi bi-journals"></i> 수정</button>
                                <button class="deleteReply btn btn-outline-warning btn-sm" data-no="${v.no}"><i class="bi bi-trash3"></i> 삭제</button>
                                <button class="reportReply btn btn-outline-danger btn-sm" data-no="${v.no}"><i class="bi bi-fire"></i> 신고</button>
                            </div>
                        </div>
                        <!-- 댓글 내용 영역 -->
                        <div class="row">
                            <div class="col p-3">
                                <pre>${v.replyContent}</pre>
                            </div>
                        </div>
                    </div>
                </div>`;
							$("#replyList").append(result);
					
				}) // end each
				$("#replyContent").val("");
			},
			error : function (xhr, status, error){
				console.log("ajax 실패 : " + status + "-" + xhr.status);
			}
				
		})
		return false;
	})
	
	// 댓글 쓰기가 클릭되었을 때 이벤트 처리
	$("#replyWrite").on("click", function(){
		
		// 화면에 보이는 상태인지 체크
		console.log($("#replyForm").css("display"));
		console.log($("#replyForm").is(":visible"));
		
		//댓글 쓰기 폼이 화면에 보이는 상태라면
		if($("#replyForm").is(":visible")) {
			
			var $prev = $("#replyTitle").prev();
			if(! $prev.is("#replyForm")) {
				$("#replyForm").slideUp(300);
			}
			setTimeout(function(){
				$("#replyForm").insertBefore("#replyTitle").slideDown(300);
			}, 300);
		} else {
			$("#replyForm").removeClass("d-none").css("display", "none").insertBefore("#replyTitle").slideDown(300);
		}
		
		$("#replyForm").find("form").attr("id", "replyWriteForm").removeAttr("data-no");
		$("#replyContent").val("");
		$("#replyWriteButton").val("댓글쓰기");
	});
	
	// 댓글 쓰기폼이 서브밋 될 때
	$(document).on("submit", "#replyWriteForm", function(e){
		if($("#replyContent").val().length < 5 ) {
			alert("댓글은 5자 이상 입력해야합니다.")
			return false;
		}
		
		var params = $(this).serialize();
		console.log(params);
		
		$.ajax({
			url : "replyWrite.ajax",
			type : "post",
			data : params,
			dataType : "json",
			success : function(resData){
				console.log(resData);
				
				$("#replyList").empty();
				$.each(resData, function(i, v){
					
					var date = new Date(v.regDate);
					var strDate = date.getFullYear() + "-" + ((date.getMonth() + 1 < 10)
							? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)) + "-"
							+ (date.getDate() < 10 ? "0" + date.getDate() :
							date.getDate()) + " "
							+ (date.getHours() < 10 ? "0" + date.getHours() :
							date.getHours()) + ":"
							+ (date.getMinutes() < 10 ? "0" + date.getMinutes() :
							date.getMinutes()) + ":"
							+ (date.getSeconds() < 10 ? "0" + date.getSeconds() :
							date.getSeconds());
					
					var result = 
							`<div class="row replyRow border border-top-0">
                    <div class="col">
                        <!--  댓글 작성자 수정 삭제 신고 버튼 영역 -->
                        <div class="row p-2 border-bottom align-items-center">
                            <div class="col-4">
                                <span>${v.replyWriter}</span>
                            </div>
                            <div class="col-8 text-end">
                                <span class="me-3">${strDate}</span>
                                <button class="modifyReply btn btn-outline-success btn-sm" data-no="${v.no}"><i class="bi bi-journals"></i> 수정</button>
                                <button class="deleteReply btn btn-outline-warning btn-sm" data-no="${v.no}"><i class="bi bi-trash3"></i> 삭제</button>
                                <button class="reportReply btn btn-outline-danger btn-sm" data-no="${v.no}"><i class="bi bi-fire"></i> 신고</button>
                            </div>
                        </div>
                        <!-- 댓글 내용 영역 -->
                        <div class="row">
                            <div class="col p-3">
                                <pre>${v.replyContent}</pre>
                            </div>
                        </div>
                    </div>
                </div>`;
							$("#replyList").append(result);
							$("#replyList").removeClass("text-center");
							$("#replyList").removeClass("p-5");
					
				}) // end each
				
				// 댓글쓰기가 완료되면 폼을 숨긴다
				$("#replyForm").slideUp(300).add("#replyContent").val("");
			},
			error : function(xhr, status){
				console.log("error : " + status);
			}
		})
		return false;
	})
	
	// 추천 / 땡큐가 클릭되면
	$(".btnCommend").click(function() {
		
		let com = $(this).attr("id");
		console.log("com : " + com);
		
		$.ajax({
			url:"recommend.ajax",
			type:"post", // recommend=thank&no=10
			data:{recommend : com, no : $("#no").val()},
			dataType : "json",
			success : function(data){
				console.log(data);
				$("#commend > .recommend").text(" (" + data.recommend + ")");
				$("#thank > .recommend").text(" (" + data.thank + ")");
				
				let msg = com =='commend' ? '추천이' : '땡큐가';
				alert(msg + " 반영되었습니다.")
			},
			error : function(xhr, status, error){
				console.log("error : " + xhr.statusText + ", " + status + ", " + error);
			}
		})
	})
	
})