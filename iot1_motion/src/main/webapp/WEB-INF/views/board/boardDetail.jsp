<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="../home.jsp" flush="true"/>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<meta name="viewport"
		content="width=device-width, initial-scale=1, user-scalable=no">
	<title>JSP Page</title>
	<link	href="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/css/bootstrap.min.css"
			rel="stylesheet" type="text/css" />
	<script	src="<%=application.getContextPath()%>/resources/jquery/jquery-3.2.1.min.js"
				type="text/javascript"></script>
	<script	src="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/js/bootstrap.min.js"
				type="text/javascript"></script>			
	<script>
		function handleBtnUpdate() {
			var bpassword = $("#bpassword").val();
			if( bpassword =="") {
				$("#bpassword").attr("placeholder", "비밀번호를 입력하셔야 합니다.");
				$("#bpassword").focus();
				return ;
			}
			$.ajax({
				url: "boardCheckBpassword",
				method: "post",
				data: {"bno":"${board.bno}", "bpassword":bpassword},
				success: function(data) {
					if(data.result =="success") {
						location.href="boardUpdate?bno=${board.bno}&pageNo=${pageNo}&mid=${member.mid}";
					} else {
						$("#bpassword").val("");
						$("#bpassword").attr("placeholder", "비밀번호를 입력하셔야 합니다.");
						$("#bpassword").focus();
					}
				}
			});
		}
		function handleBtnDelete() {
			var bpassword = $("#bpassword").val();
			if( bpassword =="") {
				$("#bpassword").attr("placeholder", "비밀번호를 입력하셔야 합니다.");
				$("#bpassword").focus();
				return ;
			}
			$.ajax({	
				url: "boardCheckBpassword",
				method: "post",
				data: {"bno":"${board.bno}", "bpassword":bpassword},
				success: function(data) {
					if(data.result =="success") {
						console.log("success");
						check = confirm("삭제하시겠습니까?");
						if (check) { 
							location.href="boardDelete?bno=${board.bno}";
						 }
					} else {
						$("#bpassword").val("");
						$("#bpassword").attr("placeholder", "비밀번호를 입력하셔야 합니다.");
						$("#bpassword").focus();
					}
				}
			});
		}
		function handleBtnDeleteAdmin() {			
			check = confirm("삭제하시겠습니까?");
			if (check) { 
				location.href="boardDelete?bno=${board.bno}";
			 }
		}
		function handleBtnLike() {
			console.log($("#mid").val());
			if($("#mid").val()==""){		
				alert("로그인 후 이용 가능합니다");
				$("#list").focus();
				return;
			}
			location.href="boardLike?bno=${board.bno}&pageNo=${pageNo}&mid=${member.mid}";		
		}
		
		function handleBtnComment(){
			if( $("#bcpassword").val() == "") {
				$("#bcpassword").attr("placeholder", "비밀번호");
				$("#bcpassword").focus();
				return ;
			} else if( $("#bccomment").val() == "") {
				$("#bccomment").val("");
				$("#bccomment").attr("placeholder", "내용을 입력하세요!");
				$("#bccomment").focus();
				return ;
			} 
			return $("#form1").submit();
		}
		
		function handleLoginCheck() {
			if($("#mid").val()==""){
				alert("로그인 후 이용하세요~");		
				$("#list").focus();
				return ;
			} 
		}
		
		/* 덧글 수정/삭제 */	
		function handleBtnCommentUD(bcno, select) {
			var bcpassword = $("#bcpassword").val();
			if( bcpassword =="") {
				$("#bcpassword").attr("placeholder", "비밀번호를 입력하셔야 합니다.");
				$("#bcpassword").focus();
				return ;
			}
			$.ajax({	
				url: "boardCommentCheckBpassword",
				method: "post",
				data: {"bcno":bcno, "bcpassword":bcpassword},
				success: function(data) {
					if(data.result =="success") {
						console.log("success");
						check = confirm("삭제하시겠습니까?");
						if (check && select == 'delete') { 
							console.log("success");
							location.href="boardCommentDelete?bno=${board.bno}&pageNo=${pageNo}&bcno=" + bcno;
						 } else if(check && select == 'update') { 							 
							location.href="boardCommentUpdate?bno=${board.bno}&pageNo=${pageNo}&bcno=" + bcno;
						 } 	console.log("success2");
					} else {
						console.log("2");
						$("#bcpassword").val("");
						$("#bcpassword").attr("placeholder", "비밀번호를 입력하셔야 합니다.");
						$("#bcpassword").focus();
					}
				}
			});
		}
		function handleBtnCommentDeleteAdmin(bcno, select) {
			check = confirm("삭제하시겠습니까?");
			if (check && select == 'delete') { 
				console.log("success");
				location.href="boardCommentDelete?bno=${board.bno}&pageNo=${pageNo}&bcno=" + bcno;
			 } 
		}
	</script>
</head>
<body>
	<div style="max-width: 1000px; margin: auto; margin-top: 50px">	
	<h4><a href="boardList?pageNo=${pageNo}" class="btn btn-default"  id="list">목록</a> ${board.btitle}</h4>
	<c:if test="${member.mlevel == 5}">
		<div class="form-group" align="right">
			<input type="button" class="btn btn-warning btn-sm" value="게시글 삭제(관리자)"    onclick="handleBtnDeleteAdmin()"/>	
		</div>	
	</c:if>
	<hr />
	<form method="post"  id="form1" action="boardCommentWrite" style="padding: 0px 20px"
		enctype="multipart/form-data">
		
		<button type="button" class="btn btn-default" name="bno" disabled="disabled" style="width: 100px; height: 33px;">${board.bno}</button>
		<button type="button" class="btn btn-default" disabled="disabled" style="width:40px; background-color: #CEF6EC"><span class="glyphicon glyphicon-user" style="color: #FAAC58"></span></button>
		<button type="button" class="btn btn-default" name="bwriter"  disabled="disabled" style="width: 250px; height: 33px;">${board.bwriter}</button>
		<button type="button" class="btn btn-default" disabled="disabled" style="width:40px; background-color: #CEF6EC"><span class="glyphicon glyphicon-heart" style="color: #FAAC58"></span></button>
		<button type="button" class="btn btn-default" name="blikecount"  disabled="disabled" style="width: 100px; height: 33px;">${board.blikecount}</button>
		<button type="button" class="btn btn-default" disabled="disabled" style="width:40px; background-color: #CEF6EC"><span class="glyphicon glyphicon-eye-open" style="color: #FAAC58"></span></button>
		<button type="button" class="btn btn-default" name="bhitcount"  disabled="disabled" style="width: 100px; height: 33px;">${board.bhitcount}</button>
		<button type="button" class="btn btn-default" disabled="disabled" style="width:40px; background-color: #CEF6EC"><span class="glyphicon glyphicon-time" style="color: #FAAC58"></span></button>
		<fmt:formatDate var="bdate" value="${board.bdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		<button type="button" class="btn btn-default" name="bdate"  disabled="disabled" style="width: 210px; height: 33px;">${bdate}</button>
		&nbsp;<br/>&nbsp;
		<!-- <hr/> -->
		<div class="form-group" style="text-align: center;">
			<c:if test='${board.boriginalfilename != "" && board.boriginalfilename != null}'>
				<img src="boardImage?bno=${board.bno }" style="max-width: 980px;"/>
			</c:if>
		</div>
		<div class="form-group" style="text-align: center;">			
			<p class="text-justify" >${board.bcontent}</p>
		</div>
		
		<!-- Like Count -->
		<div class="form-group" style="text-align: center;">
			<div class="btn btn-danger" style="border-radius: 50px; width: 100px; height: 100px; line-height: 30px; " onclick="handleBtnLike()">
				${board.blikecount}<br/>
				<span class="glyphicon glyphicon-heart" aria-hidden="true"  style="color: yellow; font-size: 45px"></span>
			</div>			
		</div>
		<hr/>
		<div class="form-group" align="right">
			<c:if test="${member.mid == board.mid}" >
				<input type="password" id="bpassword" placeholder="비밀번호"	name="bpassword"  style="width: 150px; height: 33px;"  maxlength="10"/>
				<input type="button" class="btn btn-info" value="수정"  onclick="handleBtnUpdate()" />
				<input type="button" class="btn btn-danger" value="삭제"    onclick="handleBtnDelete()"/>	
			</c:if>		
			<a href="boardList?pageNo=${pageNo}" class="btn btn-primary"  id="list">목록</a>
		</div>		
		<hr/>
		<!-- 댓글 리스트 -->
		<table class="table table-bordered table-striped table-hover ">
			<c:forEach var="comment" items="${list}" varStatus="status">
				<fmt:formatDate var="bcdateDay" value="${comment.bcdate}" pattern="yyyy-MM-dd"/>
				<fmt:formatDate var="bcdateTime" value="${comment.bcdate}" pattern="HH:mm:ss"/>	
					<c:if test="${comment.mid == member.mid}">
						<tr >				
							<td style="width: 25%; ">
								<img src="http://graph.facebook.com/${member.mid }/picture" class="img-circle"/>&nbsp; ${comment.bcwriter}
							</td>
							<td style="width: 60%; border-right: 0px"><p>${comment.bccomment}</p></td>
							<td style="width: 5%; border-left: 0px" >
								<input type="hidden" class="form-control"  name="bcno" id="bcno" value="${comment.bcno }"/>
								<input type="button" class="btn btn-danger btn-xs" value="삭제"  onclick="handleBtnCommentUD(${comment.bcno },'delete')"/>
								<c:if test="${member.mlevel == 5}">
									<div class="form-group" align="right">
										<input type="button" class="btn btn-warning btn-xs" 
										value="삭제(관리자)"  
										style="float: left"  
										onclick="handleBtnCommentDeleteAdmin(${comment.bcno },'delete')"/>	
									</div>	
								</c:if>
							</td>
							<td style="width: 10%; text-align: right">${bcdateDay}<br/>${bcdateTime}</td>
						</tr>		
					</c:if>
					<c:if test="${comment.mid != member.mid}">
						<tr >		
							<td style="width: 25%; ">
							<img src="http://graph.facebook.com/${comment.mid }/picture" class="img-circle"/>&nbsp; ${comment.bcwriter}
							</td>
							<td style="width: 65%" colspan=2><p>${comment.bccomment}</p>
								<input type="hidden" class="form-control"  name="bcno"  id="bcno" value="${comment.bcno }"/>
								<c:if test="${member.mlevel == 5}">
									<div class="form-group" align="right">
										<input type="button" class="btn btn-warning btn-xs" value="삭제(관리자)"    onclick="handleBtnCommentDeleteAdmin(${comment.bcno },'delete')"/>	
									</div>	
								</c:if>								
							</td>							
							<td style="width: 10%; text-align: right">${bcdateDay}<br/>${bcdateTime}</td>
						</tr>		
					</c:if>						
			</c:forEach>
		</table>
		<!-- 댓글 -->
		<div class="form-group">
			<div class="input-group">
				<span class="input-group-addon"> <span
					class="glyphicon glyphicon-pencil"></span>
				</span>
				<textArea rows="5" cols="30" class="form-control" placeholder="내용" 
					name="bccomment" id="bccomment" onfocus="handleLoginCheck()" ></textArea>
				<!-- 세션 아이디 -->
				<input type="hidden" class="form-control"  name="mid"  id="mid" value="${member.mid }"/>
				<input type="hidden" class="form-control"  name="bcwriter"  id="bcwriter" value="${member.mname }"/>
				<input type="hidden" class="form-control"  name="pageNo"  id="pageNo" value="${pageNo}"/>							
				<input type="hidden" class="form-control"  name="bno"  id="bno" value="${board.bno}"/>							
			</div>
		</div>
		<c:if test="${member.mid != null}" >		
		<div class="form-group" align="right">
			<input type="password" id="bcpassword" placeholder="비밀번호"	name="bcpassword"  style="width: 150px; height: 33px;"  maxlength="10"/>
			<input type="button" class="btn btn-success" value="등록"  onclick="handleBtnComment()" />		
		</div>
		</c:if>
	</form>
	</div>
</body>
</html>