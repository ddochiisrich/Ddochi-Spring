$(function(){
	// 댓글 쓰기 폼이 서브밋 될 때
	$("#replyWriteForm").on("submit", function(e){
		//댓글은 5글자 이상
		if($("#replyContent").val().length < 5) {
			alert("댓글은 5자 이상이어야 합니다.");
			return false;
		}

		let params = $(this).serialize();
		console.log("params " + params);

		$.ajax({
			url : "replyWrite.ajax",
			type : "post",
			data : params,
			dataType : "json",
			success : function(resData){
				console.log(resData);

				// 기존 댓글을 삭제
				$("#replyList").empty();

				$.each(resData, function(i, v){
					let date = new Date(v.regDate);

					let strDate = date.getFullYear() + "-" 
							+ ((date.getMonth() + 1) < 10 
									? "0" + (date.getMonth() + 1) : "0" + (date.getMonth() + 1)) + "-"
									+ (date.getDate() < 10
											? "0" + date.getDate() : date.getDate()) + " " 
											+ (date.getHours() < 10 
													? "0" + date.getHours() : date.getHours()) + ":" 
													+ (date.getMinutes() < 10 
															? "0" + date.getMinutes() : date.getMinutes()) + ":" 
															+ (date.getSeconds() < 10
																	? "0" + date.getSeconds() : date.getSeconds());

					let result = 

							'<div class="row replyRow border border-top-0">'
							+'<div class="col">'
							+   '<div class="row p-2 border-bottom align-items-center">'
							+   '<div class="col-4">'
							+   '      <span>⭐️'+ v.replyWriter +'</span>'
							+   '   </div>'
							+   '   <div class="col-8 text-end">'
							+   '      <span class="me-3">'
							+      strDate 
							+   '      </span>'
							+   '      <button class="modifyReply btn btn-outline-success btn-sm" data-no="' + v.no + '"> <i class="bi bi-journal-text">수정</i></button>'                       
							+   '      <button class="deleteReply btn btn-outline-warning btn-sm" data-no="' + v.no + '"><i class="bi bi-trash">삭제</i></button>'
							+   '      <button class="deleteReply btn btn-outline-danger btn-sm" data-no="' + v.no + '"><i class="bi bi-telephone">신고</i></button>'
							+   '      </div>'
							+   '   </div>'
							+   '   <div class="row ">'
							+   '      <div class="col p-3 ">'
							+   '         <pre>'+v.replyContent+'</pre>'
							+   '         </div>'
							+   '      </div>'
							+   '   </div>'
							+   '   </div>'

							$("#replyList").append(result);

				});

				$("#replyForm").slideUp(300).add("#replyContent").val("");

			},
			error : function(xhr, status){
				console.log("error : " + status);
			}
		});	
		//최종적으로 폼 전송을 취소한다.
		return false;
	});

	// 댓글쓰기 버튼이 클릭되면
	$("#replyWrite").on("click", function(){
		if($("#replyForm").is(":visible")) { // 댓글쓰기 폼이 화면에 보일때

			//현재 댓글 쓰기 위치에 있는지
			let $prev = $("#replyTitle").prev();
			if(!$next.is("#replyForm")) {
				$("#replyForm").slideUp(300);
			}

			setTimeout(function(e){
				$("#replyForm").insertBefore("#replyTitle").slideDown(300);	
			}, 300);

		}else {
			$("#replyForm").removeClass("d-none").css("display", "none").insertBefore("#replyTitle").slideDown(300);
		}

		$("#replyForm").find("form").attr("id", "replyWriteForm").removeAttr("data-no");
		$("#replyWriteButton").val("댓글쓰기");
		$("#replyContent").val("");
	})

	// 수정 버튼이 클릭되면
	$(document).on("click", ".modifyReply", function(e){
		let $replyRow = $(this).parents(".replyRow");

		if($("#replyForm").is(":visible")) {
			let $next = $replyRow.next();
			if(! $next.is("#replyForm")) {
				$("#replyForm").slideUp(300);
			}

			setTimeout(function(){
				$("#replyForm").insertAfter($replyRow).slideDown(300);
			}, 300);
		}else {
			$("#replyForm").removeClass("d-none").css("display", "none").insertAfter($replyRow).slideDown(300);

		}

		$("#replyForm").find("form").attr({"id": "replyUpdateForm", "data-no": $(this).attr("data-no")});
		$("#replyWriteButton").val("댓글수정");

		let reply = $(this).parent().parent().next().find("pre").text();
		$("#replyContent").val($.trim(reply));

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