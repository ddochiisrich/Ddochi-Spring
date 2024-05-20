$(function() {
	
	// 회원 수정 폼
	$("#memberUpdateForm").on("submit", function(){
		
		// 비밀번호가 체크되었는지 확인
		if(!$("#btnPassCheck").attr("disabled")) {
			alert("기존 비밀번호를 확인해주세요.");
			return false;
		}
		return joinFormCheck();
		
	})
	
	// 회원 정보 수정 폼에서 비밀번호 확인 버튼이 클릭되면
	$("#btnPassCheck").click(function(){
		let oldPass = $("#oldPass").val();
		let oldId = $("#id").val();
		
		if($.trim(oldPass).length == 0) {
			alert("기존 비밀번호를 입력해주세요");
			return false;
		}
		
		let data = "id=" + oldId + "&pass=" + oldPass;
		console.log("data : " + data );
		
		// ajax 구현 방법
		// XMLHttpRequest 객체
		// jQuery의 ajax 지원 메서드 $.get(). $.post(), $.ajax();
		// ES6+ Fetch API
		$.ajax({
			url : "passCheck.ajax", 		// 요청주소
			type : "get", 				// 요청박싱 폼 method
			data : data, 				// 서버로 보내는 데이터
			dataType : "json", 			//응답으로 받을 결과 데이터형식
			success : function(resData){// ajax가 성공되고 응답 데이터를 dataType에 맞게 파싱이 완료면 호출되는 콜백
				console.log(resData);
				if(resData.result){// 비밀번호가 맞으면
					// 비밀번호 확인 버튼을 비활성화 - disabled
					alert("비밀번호가 확인 되었다!");
					$("#btnPassCheck").attr("disabled", true);
					$("#oldPass").attr("readonly", true);
					$("#pass1").focus();
					
				} else {// 비밀번호가 틀리면 
					alert("기존 비밀번호가 틀립니다.");
					$("#oldPass").val().focus();
				}
			},
			error:function(){
			// ajax 작업 중에 오류가 발생되면 호출되는 콜백
				console.log("error");
				
			}
		});

	})
	
	// 회원 가입 폼
	$("#joinForm").on("submit", function(){
		
		
		let isIdCheck = $("#isIdCheck").val();
		
		if(isIdCheck == "false") {
			alert("아이디 중복검사 하고오너라!!!")
			return false;
		}
		
		return joinFormCheck();
	})
	
	// 이메일 도메인 셀렉트 박스가 선택하면
	$("#selectDomain").on("change", function(){
		let str = $(this).val();
		
		if(str == '직접입력') {
			$("#emailDomain").val("");
			$("#emailDomain").focus("");
			$("#emailDomain").attr("readonly", false);
		}else if(str == '네이버') {
			$("#emailDomain").val("naver.com");
			$("#emailDomain").attr("readonly", true);
		}else if(str == '다음') {
			$("#emailDomain").val("daum.net");
			$("#emailDomain").attr("readonly", true);
		}else if(str == '한메일') {
			$("#emailDomain").val("hanmail.com");
			$("#emailDomain").attr("readonly", true);
		}else if(str == '구글') {
			$("#emailDomain").val("google.com");
			$("#emailDomain").attr("readonly", true);
		}
	})
	
	$("#btnZipcode").click(findZipcode);
	
	// 아이디 중복 폼이 서브밋 될때
	$("#idCheckForm").on("submit", function(){
		
		let id = $("#checkId").val();
		if(id.length == 0) {
			alert("아이디를 입력해라!!!");
			return false;
		}
		if(id.length < 5) {
			alert("아이디를 5글자 이상 입력해라!!!!!");
			return false;
		}
		
	})
	
	// id를 아이디로 사용하기
	$("#btnIdCheckClose").on("click", function(){
		
		let id = $(this).attr("data-id-value");
		opener.document.joinForm.id.value=id;
		opener.document.joinForm.isIdCheck.value=true;
		window.close();
		
	})
	
	
	$("#btnOverlapId").on("click", function(){
		let id = $("#id").val();
		if(id.length == 0) {
			alert("아이디를 입력하거라");
			return false;
		}
		if(id.length < 5) {
			alert("아이디는 5글자 이상이어야한다 이놈아!");
			return false;
		}
		
		let url = "overlapIdCheck?id=" + id;
		
		window.open(url, "_blank", "toolbar=no, location=no, status=no, menubar=no, width=500, height=400");
	})

	$("#id").on("keyup", function(){
		let regExp = /[^A-Za-z0-9]/gi;
		if(regExp.test($(this).val())){ // 영문 대소문자, 숫자가 아니면
			alert("아이디는 영문 대소문자와 숫자만 가능합니다.");
			$(this).val($(this).val().replace(regExp,""));			
		}
	})

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
		let pass= $("#userPass").val();

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
	
	function joinFormCheck() {
		
			let name = $("#name").val();
			let id = $("#id").val();
			let pass1 = $("#pass1").val();
			let pass2 = $("#pass2").val();
			let zipcode = $("#zipcode").val();
			let address1 = $("#address1").val();
			let emailId = $("#emailId").val();
			let emailDomain = $("#emailDomain").val();
			let mobile2 = $("#mobile2").val();
			let mobile3 = $("#mobile3").val();

			if( name.length == 0 ) {
				alert("이름 입력해야지!");
				return false;
			}
			if( id.length == 0 ) {
				alert("아이디 입력해야지!");
				return false;
			}
			if( id.length < 5 ) {
				alert("아이디는 5글자 이상 입력해야한다 이놈아!");
				return false;
			}
			if( pass1.length == 0 ) {
				alert("비밀번호 입력해야지!");
				return false;
			}
			if( pass2.length == 0 ) {
				alert("비밀번호 확인 입력해야지!");
				return false;
			}
			if( pass1 != pass2 ) {
				alert("비밀번호랑 비밀번호 확인이 다르잖아!!!");
				return false;
			}
			if( zipcode.length == 0 ) {
				alert("우편번호 입력해야지!");
				return false;
			}
			if( address1.length == 0 ) {
				alert("주소 입력해야지!");
				return false;
			}
			if( emailId.length == 0 ) {
				alert("이메일 입력해야지!");
				return false;
			}
			if( emailDomain.length == 0 ) {
				alert("이메일 도메인을 입력해야지!");
				return false;
			}
			if( mobile2.length == 0 || mobile3.length == 0) {
				alert("휴대폰번호 정확히 입력해야지!");
				return false;
			}
	}
	
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
	
	
	function findZipcode() {
        new daum.Postcode({
        	
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                var themeObj = {
                		   bgColor: "#162525", //바탕 배경색
                		   searchBgColor: "#162525", //검색창 배경색
                		   contentBgColor: "#162525", //본문 배경색(검색결과,결과없음,첫화면,검색서제스트)
                		   pageBgColor: "#162525", //페이지 배경색
                		   textColor: "#FFFFFF", //기본 글자색
                		   queryTextColor: "#FFFFFF", //검색창 글자색
                		   //postcodeTextColor: "", //우편번호 글자색
                		   //emphTextColor: "", //강조 글자색
                		   outlineColor: "#444444" //테두리
                		};
                
                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                	addr = data.roadAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    addr += extraAddr;   
                } 

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
               $("#zipcode").val(data.zonecode);
               $("#address1").val(addr);
                // 커서를 상세주소 필드로 이동한다.
                $("#address2").focus();
            }
        }).open();
    }

})