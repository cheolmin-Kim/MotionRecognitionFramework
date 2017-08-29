<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../home.jsp"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">
<title>JSP Page</title>
<link
	href="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script
	src="<%=application.getContextPath()%>/resources/jquery/jquery-3.2.1.min.js"
	type="text/javascript"></script>
<script
	src="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/js/bootstrap.min.js"
	type="text/javascript"></script>
	<script>
		function fileChange() {
			if($("#battach")[0].files.length != 0) {
				var originalfilename = $("#battach")[0].files[0].name;
				$("#spanFileName").text(originalfilename);
			}
		}
		function fileDelete() {
			$("#spanFileName").text("");
			$("#boriginalfilename").val("");
			$("#bsavedfilename").val("");
			$("#bfilecontent").val("");
		}
		function btnCancel() {
			location.href="boardDetail?bno=${board.bno}&pageNo=${pageNo}";
		} 
	</script>
</head>
<body>
	<div style="max-width: 1000px; margin: auto;  margin-top: 50px">
	<h4>게시물 등록</h4>
	<hr />
	<form method="post" action="boardUpdate" style="padding: 0px 20px" 
		enctype="multipart/form-data">
		<input type="hidden" name="bno" value="${board.bno}" />
		<div class="form-group">
			<div class="input-group">
				<span class="input-group-addon"> <span
					class="glyphicon glyphicon-pencil"></span>
				</span> <input type="text" class="form-control" placeholder="제목"
					name="btitle" value="${board.btitle}" maxlength="30"/>
			</div>
		</div>
		<div class="form-group">
			<div class="input-group">
				<span class="input-group-addon"> <span
					class="glyphicon glyphicon-user"></span>
				</span> <input type="text" class="form-control" placeholder="작성자"
					name="bwriter" value="${board.bwriter}"  maxlength="8"  readonly/>
			</div>
		</div>
		<div class="form-group">
			<div class="input-group">
				<span class="input-group-addon"> <span
					class="glyphicon glyphicon-lock"></span>
				</span> <input type="password" class="form-control" placeholder="비밀번호"
					name="bpassword" value="${board.bpassword}"  maxlength="10"/>
			</div>
		</div>
		<div class="form-group">
			<div class="input-group">
				<span class="input-group-addon"> <span
					class="glyphicon glyphicon-pencil"></span>
				</span>
				<textArea rows="10" cols="30" class="form-control" placeholder="내용" 
					name="bcontent">${board.bcontent} </textArea>${board.battach }
			</div>
		</div>
		<div class="form-group">
			<div class="input-group" style="height: 47px;">
				<span class="input-group-addon"> <span
					class="glyphicon glyphicon-camera">
					</span>
				</span> 
				<div class="form-control"  style="height: 47px;">
					<input type="hidden" class="btn btn-default"  name="boriginalfilename"  id="boriginalfilename"  value="${board.boriginalfilename }" />					
					<input type="hidden" class="btn btn-default"  name="bsavedfilename" id="bsavedfilename" value="${board.bsavedfilename }" />					
					<input type="hidden" class="btn btn-default"  name="bfilecontent"  id="bfilecontent" value="${board.bfilecontent }" />					
									
					<span id="spanFileName">${board.boriginalfilename}</span>
					<label for="battach" class="btn btn-default">변경</label>		
					<input type="button" value="삭제" class="btn btn-default" onclick="fileDelete()"  />		
					<input id="battach" type="file" style="visibility: hidden;" name="battach" onchange="fileChange()" multiple value="${board.battach }" />		
				</div>
			</div>
		</div>
		<div align="right">
		<input type="hidden" class="form-control"  name="pageNo"  id="pageNo" value="${pageNo}"/>		
		<input type="button" class="btn btn-success" value="취소" onclick="btnCancel()"/>
		<input type="submit" class="btn btn-info" value="등록" />
		</div>
	</form>
	</div>
</body>
</html>