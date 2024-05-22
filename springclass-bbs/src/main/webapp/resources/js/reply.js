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
					
					let strDate = date.getFullYear() + "-"+ (date.getMonth()+1< 10 ? "0" : "" +"-") + date.getDate() + " " + date.getHours() + ":" + date.getMinites()+ ":" + date.getSeconds(); 
					
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
		                        +   '      <button class="modifyReply btn btn-outline-success btn-sm"><i class="bi bi-journal-text">수정</i></button>'
		                        +   '      <button class="deleteReply btn btn-outline-warning btn-sm"><i class="bi bi-trash">삭제</i></button>'
		                        +   '      <button class="deleteReply btn btn-outline-danger btn-sm"><i class="bi bi-telephone">신고</i></button>'
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
		$("#replyForm").removeClass("d-none").css("display", "none").insertBefore("#replyTitle").slideDown(300);
	})
	
	// 수정 버튼이 클릭되면
	$(".modifyReply").on("click", function(e){
		let $replyRow = $(this).parents(".replyRow");
		
		$("#replyForm").removeClass("d-none").css("display", "none").insertAfter($replyRow).slideDown(300);
		
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