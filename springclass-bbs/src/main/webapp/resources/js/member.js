$(function() {
	
	// 모달 로그인 폼
		$("#modalLoginForm").submit(function(){
			
			let id = $("#userId").val();
			let pass= $("#pass").vall();
			
			if(id.length <= 0) {
				alert("아이디를 입력해 주세요.");
				$("#userId").focus();
				return false;
			}
			
			if(pass.length <= 0) {
				alert("비밀번호를 입력해 주세요.");
				$("#pass").focus();
				return false;
			}
		})
	
	// 로그인 폼
	$("#loginForm").submit(function(){
		
		let id = $("#userId").val();
		let pass= $("#userPass").vall();
		
		if(id.length <= 0) {
			alert("아이디를 입력해 주세요.");
			$("#userId").focus();
			return false;
		}
		
		if(pass.length <= 0) {
			alert("비밀번호를 입력해 주세요.");
			$("#userPass").focus();
			return false;
		}
	})
	
})