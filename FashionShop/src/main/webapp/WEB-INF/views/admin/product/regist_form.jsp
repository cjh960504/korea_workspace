<%@page import="com.koreait.fashionshop.model.domain.TopCategory"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
List<TopCategory> topList = (List) request.getAttribute("topList");
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="../inc/header.jsp"%>
<style>
input[type=text], select, textarea {
	width: 100%;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	margin-top: 6px;
	margin-bottom: 16px;
	resize: vertical;
}

input[type=button] {
	background-color: #4CAF50;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

input[type=button]:hover {
	background-color: #45a049;
}

.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}

/*드래그 관련*/
#dragArea {
	width: 100%;
	height: 300px;
	overflow: scroll;
	border:1px solid #ccc;
	float:left;
}

.dragBorder {
	background: #ffffff;
}

.box > img{
	width:100%;
}
.box{
	width:100px;
	float:left;
	padding:5px;
}
.close{
	color:#ccc;
	cursor:pointer;
}
</style>
<script>
	var uploadFiles=[]; //이미지 미리보기 목록
	var psizeArray=[];//유저가 선택한 사이즈를 담는 배열
	
	$(function() {
		CKEDITOR.replace('detail');
	
		//상위 카테고리 선택하면..
		$($("select")[0]).change(function() {
			//비동기 방식으로 서버에 요청하되 순수 ajax보다는 jquery ajax를 이용하자!
			getSubList(this);
		});

		/*드래그 관련 이벤트*/
		$("#dragArea").on("dragenter", function(e) {//드래그로 영역에 진입했을때..
			//$(this).append("dragenter<br>");
			$(this).addClass("dragBorder");
		});
		$("#dragArea").on("dragover", function(e) {//드래그 영역 위에 있을 때..
			e.preventDefault(); //여타 다른 이벤트를 비활성화시키자..	
			//$(this).append("dragover<br>");
		});
		$("#dragArea").on("drop", function(e) {//드래그 영역 위에서 이미지를 떨구면..
			e.preventDefault(); //여타 다른 이벤트를 비활성화시키자..
			//$(this).append("drop<br>");
			
			//자바스크립트로 드래그된 이미지 정보를 구해와서, div영역에 미리보로기 효과..
			var fileList = e.originalEvent.dataTransfer.files;//드래그한 파일들에 대한 배열 얻기!!
			
			//배열안에 들어있는 이미지들에 대한 미리보기 처리..
			for(var i=0;i<fileList.length;i++){
				uploadFiles.push(fileList[i]);//fileList안의 요소들을 일반배열에 옮겨심기 
				//왜심있나? 배열이 지원하는 여러 메서드들을 활용하기 위해 (fileList는 FileList의 형태로 필요한 메서드를 지원하지 않음)
				preview(uploadFiles[i], i);//파일 요소 하나를 넘기기
			}
		});
		$("#dragArea").on("dragleave", function(e) {//드래그로 영역에서 빠져나가면..
			//$(this).append("dragleave<br>");
			$(this).removeClass();
		});
		
		//이미지 삭제 이벤트 처리
		$("#dragArea").on("click", ".close", function(e){
			console.log(e);
			
			//대상 요소 배열에서 삭제 
			//삭제 전에 uploadFiles라는 배열에 들어있는 file의 index를 구하자!!
			var f = uploadFiles[e.target.id];
			//console.log(f);
			var index = uploadFiles.indexOf(f);
			console.log(index);
			uploadFiles.splice(index, 1);
			
			//내가 추가한거
			$(".box").remove();
			for(var i=0;i<uploadFiles.length;i++){
				preview(uploadFiles[i], i);
			}
			//대상요소삭제
			//class인 close를 감싸고 있는 부모인 box를 지운다!(시각적 삭제)
			$(e.target).parent().remove();
		})
		
		//체크박스 이벤트 구현
		$("input[type='checkbox']").on("click", function (e) {
			var ch = e.target;//이벤트를 일으킨 주체 컴포넌트 즉 체크박스
			//alert($(ch).val);
			console.log(ch.name);
		});
	});

	//업로드 이미지 미리보기
	function preview(file, index){
		//js로 이미지 미리보기를 구현하려면, 파일리더를 이용하면 된다. FileReader
		var reader = new FileReader(); //아직은 읽을 대상 파일이 결정되지 않음..
		//파일을 읽어들이면, 이벤트 발생시킴
		reader.onload=function(e){
			//console.log(reader.result);
			var tag ="<div class=\"box\">";
			tag+="<div class=\"close\" id=\""+index+"\">X</div>"; 
			tag+="<img src=\""+reader.result+"\">";
			tag+="</div>";
			$("#dragArea").append(tag);
			
		};
		
		//위의 이벤트를 발동시킴!
		reader.readAsDataURL(file);//지정한 파일을 읽는다(매개변수로는 파일이 와야함)
	}
	
	//비동기 방식으로 하위카테고리 요청하기!!
	//data=파라미터, 모든게Json형식
	function getSubList(obj) {
		$.ajax({
			url : "/admin/product/sublist",
			type : "get",
			data : {
				topcategory_id : $(obj).val()
			},
			success : function(result) {
				//서버가 이미 json으로 전송해주었으므로 별도의 Parsing이 필요없다~!
				//alert(result);
				$($("select")[1]).empty();
				$($("select")[1]).append("<option>하위카테고리 선택</option>");
				for (var i = 0; i < result.length; i++) {
					var subCategory = result[i]; //subcategory 1건에 대한 json객체얻기!!
					$($("select")[1]).append(
							"<option value=\""+subCategory.subcategory_id+"\">"
									+ subCategory.name + "</option>");
				}
			}
		});
	}
	//사이즈 선택 시 배열 재구성
	function setPsizeArray() {
		
	}
	
	//상품등록
	function regist() {
		//$("textarea").val($("CKEditior").instances.detail.getData());
		//비동기 전송 시 기존의 form을 이용할 수 있을까? yes!!!!!
		var formData = new FormData($("form")[0]);//<form>태그와는 다름.. 전송할 때 파라미터들을 담을 수 있지만 이 자체가 폼태그가 아니다!!
		
		//미리보기했던 이미지들은 파일컴포넌트화 되어있지 않기 때문에, 전송데이터에서 빠져있다..
		//따라서 formData전송 전에, 동적으로 파일컴포넌트화시켜 formData에 추가하자!!!
		//java에서의 improved for문과 동일한 역할(주로 컬렉션에서 객체를 꺼낼 때 사용..)
		//formData.append(name값, 업로드할 파일 객체, 파일명);
		$.each(uploadFiles, function(i, file){
			//미리보기만 가능했던 이미지들을 전송할 수 있는 형태로 만듬
			formData.append("addImg", file, file.name);//<input type="file" name="addImg"> 동일
			console.log(file.name);
		});
		
		//폼데이터에 에디터의 값 추가하기!!
		formData.append("detail", CKEDITOR.instances["detail"].getData());
		
		/* 비동기 업로드*/
		$.ajax({
			url:"/admin/product/regist",
			data:formData,
			contentType:false,/*false일 경우 multipart/form-data로 지정한 효과!*/
			processData:false,/*false일 경우 query-string으로 전송하지 않음, 문자열만 보내는게 아니라 이미지도 껴있기 떄문에 stream방식*/
			type:"post",
			success:function(result){
				alert(result);
			}
		});
		
		/* 동기방식 업로드
		$("form").attr({
			action : "/admin/product/regist",
			method : "post",
			enctype : "multipart/form-data"
		});
		$("form").submit();
		*/
	}
</script>
</head>
<body>
	<%@ include file="../inc/main_navi.jsp"%>

	<h3>Contact Form</h3>


	<div class="container">
		<form>

			<select>
				<option value=0>상위카테고리 선택</option>
				<%
				for (TopCategory topCategory : topList) {
				%>
				<option value="<%=topCategory.getTopcategory_id()%>"><%=topCategory.getName()%></option>
				<%
				}
				%>
			</select> <select name="subcategory_id">
				<option>하위카테고리 선택</option>
			</select> <input type="text" name="product_name" placeholder="상품명"> <input
				type="text" name="price" placeholder="가격"> <input
				type="text" name="brand" placeholder="브랜드">
			<!-- 파일 최대 4개까지 지원  -->
			<p>
				대표이미지 : <input type="file" name="repImg">
			</p>

			<div id="dragArea"></div>

			<!-- 지원 사이즈 선택 -->
			<p>
				 XS<input type="checkbox" name="psize[0].fit" value="XS"> 
				 S<input type="checkbox" name="psize[1].fit" value="S"> 
				 M<input type="checkbox" name="psize[2].fit" value="M">
				 L<input type="checkbox" name="psize[3].fit" value="L">
				 XL<input type="checkbox" name="psize[4].fit" value="XL">
				 XXL<input type="checkbox" name="psize[5].fit" value="XXL">
			</p>
			<p>
				<input type="color" name="color[0].picker" value="#ccfefe">
				<input type="color" name="color[1].picker" value="#ccc">
				<input type="color" name="color[2].picker" value="#ffffff">
				<input type="color" name="color[3].picker" value="#00ff00">
				<input type="color" name="color[4].picker" value="#fdfdfd">
				<input type="color" name="color[5].picker" value="#ff0000">
			</p>
			<textarea name="detail" id="detail" placeholder="상세정보"
				style="height: 200px"></textarea>
			<input type="button" value="글등록" onClick="regist()"> <input
				type="button" value="목록보기"
				onClick="location.href='/client/notice/list'">
		</form>
	</div>
</body>
</html>