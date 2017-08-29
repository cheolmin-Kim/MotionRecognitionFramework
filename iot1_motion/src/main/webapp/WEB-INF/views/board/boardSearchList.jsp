<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
		text-decoration: none;
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
	function handleBtnSearch() {
		if($("#bsearch").val()=="") {
			$("#bsearch").attr("placeholder", "검색어를 입력하세요");
			$("#bsearch").focus();
			return ;
		}
		var category = $("#category").val();
		var bsearch = $("#bsearch").val();
		location.href="boardSearchList?category="+category+"&bsearch="+bsearch;
	}
	function handleLoginCheck() {
		if($("#bcwriter").val()==""){
			alert("로그인 후 이용하세요~");				
			return;
		} 
		location.href="boardWrite";
	}
	function handleBtnSearchCancel() {
		location.href="boardList";
	}
	</script>
</head>
<body>
	<jsp:include page="../home.jsp"/>
	<div style="width: 1000px; margin: auto; margin-top: 50px; text-align: center">
		<h4>게시물 목록</h4>
		<div style="width: 1000px; margin: auto; text-align: right"></div>
		<input type="hidden"  name="type" value="1"/>
		<table class="table table-bordered table-hover" 
			style="width: 1000px; text-align: center; border: 0px;">
			<tr class="info">
				<td style="width: 6%;  border-left: 0px; border-right: 0px">번호</td>
				<td style="width: 60%;  border-left: 0px; border-right: 0px">제목</td>
				<td style="width: 13%;  border-left: 0px; border-right: 0px">글쓴이</td>
				<td style="width: 9%;  border-left: 0px; border-right: 0px">날짜</td>
				<td style="width: 6%;  border-left: 0px; border-right: 0px">조회수</td>
				<td style="width: 6%;  border-left: 0px; border-right: 0px">추천수</td>
			</tr>
			<c:forEach var="b" items="${list}">
				<tr>
					<td style=" border-left: 0px; border-right: 0px">${b.bno}</td>
					<td style="text-align: left;  border-left: 0px; border-right: 0px">
						<a href="hitcount?bno=${b.bno}&pageNo=${pageNo}&mid=${member.mid}" style="color: gray; text-decoration: none">
							${b.btitle}&nbsp;
							<c:if test="${b.boriginalfilename != null}" >
								<span class="glyphicon glyphicon-picture" aria-hidden="true"  style="color: #BDBDBD"></span>&nbsp;
							</c:if>							
							<c:set var="now" value="<%= new java.util.Date() %>"/> 
							<fmt:formatDate var="today" value="${now}" pattern="yyyy-MM-dd"/>
							<fmt:formatDate var="bdate" value="${b.bdate}" pattern="yyyy-MM-dd"/>
							<c:if test="${bdate == today}"><em style="color: red">new</em>&nbsp;</c:if>
							<c:if test="${b.count > 0}" >
								<span class="glyphicon glyphicon-comment" aria-hidden="true"  style="color: #BDBDBD"></span>&nbsp; ${b.count}
							</c:if>
							<c:if test="${b.blikecount >= 10}">
								<span class="glyphicon glyphicon-star" aria-hidden="true" style="color: orange"></span>&nbsp;
							</c:if>
						</a>
					</td>					
					<td style="border-left: 0px; border-right: 0px">${b.bwriter}</td>
					<td style="border-left: 0px; border-right: 0px">${bdate}</td>
					<td style="border-left: 0px; border-right: 0px">${b.bhitcount}</td>
					<td style="border-left: 0px; border-right: 0px">${b.blikecount}</td>
				</tr>
			</c:forEach>
		</table>
		
		<div  class="input-group"  style="margin-top: 20px; width: 1000px">
			<a href="boardSearchList?pageNo=1&category=${category}&bsearch=${bsearch}">◀</a>&nbsp;
			<c:if test="${groupNo>1}">
				<a href="boardSearchList?pageNo=${startPageNo-1}&category=${category}&bsearch=${bsearch}">◁</a>&nbsp;
			</c:if>
			<c:forEach var="i" begin="${startPageNo}" end="${endPageNo}">			
				&nbsp;
				<a href="boardSearchList?pageNo=${i}&category=${category}&bsearch=${bsearch}"
					<c:if test="${pageNo==i}">style="font-weight: bold; color: red"</c:if>>${i}</a>
				&nbsp;
			</c:forEach>

			<c:if test="${groupNo<totalGroupNo}">
				<a href="boardSearchList?pageNo=${endPageNo+1}&category=${category}&bsearch=${bsearch}">▷</a>&nbsp;
			</c:if>
			<a href="boardSearchList?pageNo=${totalPageNo}&category=${category}&bsearch=${bsearch}">▶</a>
		</div>		
		
		<div  class="input-group"  style="margin-top: 20px; width: 1000px">
			<select id="category" style="background-color: white; width:100px; height: 33px">
				<option value="title">제목</option>
				<option value="content">내용</option>
				<option value="titlecontent">제목+내용</option>
				<option value="writer">작성자</option>			
			</select>&nbsp;	
			<input type="text" name="bsearch" id="bsearch" placeholder="검색"	style="background-color: white; width:200px; height: 33px">&nbsp;
			<input type="button"  class="btn btn-primary"  onclick="handleBtnSearch()" value="검색"/>&nbsp;		
			<input type="button"  class="btn btn-info"  onclick="handleBtnSearchCancel()" value="검색초기화"/>		
		</div>
		
		<div style="margin-top: 10px; width: 700; text-align: right;">
			<input type="button" class="btn btn-success" value="글쓰기"  onclick="handleLoginCheck()" />			
			<input type="hidden" class="form-control"  name="bcwriter"  id="bcwriter" value="${member.memail }"/>
		</div>
	</div>
</body>
</html>