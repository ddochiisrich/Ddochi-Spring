$(function() {
	
	$("#btnOverlapId").on("click", function(){
		let id = $("#id").val();
		if(id.length == 0) {
			alert("아이디를 입력하거라");
			return false;
		}
		if(id.length < 5) {
			alert("아이디는 5글자 이상이어야한다 이놈아!");
		}
		
		let url = "overlabIdCheck?id=" + id;
		
		window.open(url, "toolbar=no, location=no, status=no, menubar=no, width=400, height=300");
	});

	$("#id").on("keyup", function(){
		let regExp = /[^A-Za-z0-9]/gi;
		if(regExp.test($(this).val())){ // 영문 대소문자, 숫자가 아니면
			alert("아이디는 영문 대소문자와 숫자만 가능합니다.");
			$(this).val($(this).val().replace(regExp,""));			
		}
	});

	$("#pass1").on("keyup", inputCharReplace);
	$("#pass2").on("keyup", inputCharReplace);
	$("#emailId").on("keyup", inputEmailDomainReplace);
	$("#emailDomain").on("keyup", inputEmailDomainReplace);

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
	
	function inputCharReplace() {
		let regExp = /[^A-Za-z0-9]/g;
		if(regExp.test($(this).val())){ // 영문 대소문자, 숫자가 아니면
			alert("영문 대소문자와 숫자만 가능합니다.");
			$(this).val($(this).val().replace(regExp,""));			
		}
	}

	function inputEmailDomainReplace() {
		let regExp = /[^A-Za-z0-9\.]/gi;
		if(regExp.test($(this).val())){ // 영문 대소문자, 숫자가 아니면
			alert("이메일 도메인은 영문 소문자, 숫자, 점(.)만 가능합니다.");
			$(this).val($(this).val().replace(regExp,""));			
		}
	}	

})