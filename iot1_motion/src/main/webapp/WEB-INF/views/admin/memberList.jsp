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
<link
	href="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />	
	<style>
	a {
		color: gray;
	}
	a:HOVER {
		color: skyblue;
		text-decoration: none;
	}
	.glyphicon-th-list, .glyphicon-th-large {
	    font-size: 30px;
	}
	</style>
<script
	src="<%=application.getContextPath()%>/resources/jquery/jquery-3.2.1.min.js"
	type="text/javascript"></script>
<script
	src="<%=application.getContextPath()%>/resources/bootstrap-3.3.7/js/bootstrap.min.js"
	type="text/javascript"></script>
	<script>
		function handleBtnMemberUpdate(mid, mlevel) {
			check = confirm("수정하시겠습니까?");
			if (check) { 
				location.href="memberGradeUpdate?mid="+mid+"&mlevel="+mlevel;
			 }	
		}
		function handleBtnMemberDelete(mid) {
			check = confirm("강제추방 하시겠습니까?");
			if (check) { 
				location.href="memberDelete?mid="+mid;
			 }	
		}
	</script>
</head>
<body>
<form>
	<div style="width: 1000px; margin: auto; margin-top: 50px; text-align: center">
		<c:if test="${member.mlevel >= 4}">
			<div class="form-group">
			<h4>회원 목록</h4>				
				<input type="hidden"  name="type" value="1"/>
				<table class="table table-bordered table-hover" style="width: 1000px; text-align: center; border: 0px;">
					<tr class="info">
						<td style="width: 15%; border-left: 0px; border-right: 0px">&nbsp;</td>
						<td style="width: 15%; border-left: 0px; border-right: 0px">아이디</td>
						<td style="width: 15%; border-left: 0px; border-right: 0px">이름</td>
						<td style="width: 15%; border-left: 0px; border-right: 0px">이메일</td>
						<td style="width: 10%; border-left: 0px; border-right: 0px">등급</td>
						<td style="width: 30%; border-left: 0px; border-right: 0px">등급수정</td>
					</tr>
					<c:forEach var="m" items="${list}">
						<tr>
							<td style=" border-left: 0px; border-right: 0px; vertical-align:middle; height: 55px; ">
								<img src="http://graph.facebook.com/${m.mid }/picture" class="img-circle"  style="height: 35px;" alt=""/>
							</td>
							<td style=" border-left: 0px; border-right: 0px; vertical-align:middle;">${m.mid}</td>
							<td style=" border-left: 0px; border-right: 0px; vertical-align:middle;">${m.mname}</td>
							<td style=" border-left: 0px; border-right: 0px; vertical-align:middle;">${m.memail}</td>
							<td style=" border-left: 0px; border-right: 0px; vertical-align:middle;">${m.mlevel}</td>
							<td style=" border-left: 0px; border-right: 0px; vertical-align:middle;">
								<c:if test="${m.mlevel <= member.mlevel || m.mlevel == null}">
									<select id="mlevel${m.mid}" style="background-color: white; width:80px; height: 28px" >
										<c:forEach var="n" begin="1" end="5" step="1" >
											<c:if test="${m.mlevel == n  && member.mlevel >= n}">
												<option value="${n}" selected>${n}</option>
											</c:if>
											<c:if test="${m.mlevel != n  && member.mlevel >= n}">
												<option value="${n}">${n}</option>
											</c:if>
										</c:forEach>		
									</select>								
									<input type="button" class="btn btn-info btn-sm"  value="수정" onclick="handleBtnMemberUpdate('${m.mid}', $('#mlevel${m.mid}').val())"/>&nbsp;
									<input type="button" class="btn btn-danger btn-sm"  value="추방" onclick="handleBtnMemberDelete('${m.mid}')"/>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>		
				<div  class="input-group"  style="margin-top: 20px; width: 1000px">
					<a href="memberList?pageNo=1">◀</a>&nbsp;
					<c:if test="${groupNo>1}">
						<a href="memberList?pageNo=${startPageNo-1}">◁</a>&nbsp;
					</c:if>
					<c:forEach var="i" begin="${startPageNo}" end="${endPageNo}">&nbsp;		
						<a href="memberList?pageNo=${i}" 
							<c:if test="${pageNo==i}">style="font-weight: bold; color: red"</c:if> >${i}</a>&nbsp;
					</c:forEach>
					<c:if test="${groupNo<totalGroupNo}">
						<a href="memberList?pageNo=${endPageNo+1}">▷</a>&nbsp;
					</c:if>
					<a href="memberList?pageNo=${totalPageNo}">▶</a>
				</div>	
			</div>	
		</c:if>
	</div>
	</form>
</body>
</html>