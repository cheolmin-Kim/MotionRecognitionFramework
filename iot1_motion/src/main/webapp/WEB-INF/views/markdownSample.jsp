<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<title>JSP Page</title>
		<link href="<%= application.getContextPath() %>/resources/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
		<script src="<%= application.getContextPath() %>/resources/jquery/jquery-3.2.1.min.js" type="text/javascript"></script>
		<script src="<%= application.getContextPath() %>/resources/bootstrap-3.3.7/js/bootstrap.min.js" type="text/javascript"></script>
	</head>
<body>

<jsp:include page="home.jsp"></jsp:include>

<h2 id="motion-recognition-gyro"> <strong>Motion Recognition (Gyro)</strong></h2>

<hr>



<h2 id="what-is-this">What is this?</h2>

<blockquote>
  <p><strong>소개:</strong></p>
  
  <ul>
  <li>자이로가속도 센서를 이용하여 모션인식을 진행</li>
  <li>X, Y, Z의 각 축의 회전 각을 입력하면 모션을 만들거나 기존의 만들어진 모션을 활용 가능</li>
  <li>기타 다른 센서를 조합하여 모션과 같이 활용 가능</li>
  </ul>
</blockquote>

<p><br><br></p>



<h3 id="자이로-부분">자이로 부분</h3>

<p><img src="http://cfile7.uf.tistory.com/image/2445F4395733FF872C8DD9" alt="enter image description here" title=""></p>

<p><br><br></p>



<h3 id="다른-센서">다른 센서</h3>

<p><br></p>



<h3 id="거리-센서">거리 센서</h3>



<h4 id="초음파">초음파</h4>

<p><img src="http://cfile28.uf.tistory.com/image/2621BB4456B4C33C02C628" alt="enter image description here" title=""></p>



<h4 id="적외선">적외선</h4>

<p><img src="https://kocoafab.cc/data/140818041514.jpg" alt="enter image description here" title=""> <br>
<br></p>



<h5 id="센서를-통하여-얻은-거리를-이용하여-다음-두가지-메소드를-활용-가능">센서를 통하여 얻은 거리를 이용하여 다음 두가지 메소드를 활용 가능</h5>



<pre class="prettyprint"><code class="language-java hljs ">eventHandle(<span class="hljs-keyword">double</span> distance, <span class="hljs-keyword">double</span> fixPoint);

holdOn(<span class="hljs-keyword">double</span> distance, <span class="hljs-keyword">double</span> min, <span class="hljs-keyword">double</span> max);
</code></pre>

<p><br></p>



<h3 id="버튼">버튼</h3>

<p><br></p>



<h4 id="일반버튼">일반버튼</h4>

<p><img src="http://www.gameplusedu.com/shop/data/editor/3d8490bfad84fde4.JPG" alt="enter image description here" title=""></p>



<h4 id="터치버튼">터치버튼</h4>

<p><img src="https://erelement.com/images/large/sensors/touch-buton_LRG.jpg" alt="enter image description here" title=""> <br>
<br></p>

<table>
<thead>
<tr>
  <th></th>
  <th>Button On</th>
  <th>Button Off</th>
</tr>
</thead>
<tbody><tr>
  <td>Status</td>
  <td>On (result of String)</td>
  <td>Off (result of String)</td>
</tr>
</tbody></table>


<p><br></p>



<h4 id="이-상태를-이용하여-if문에서-제어-가능">이 상태를 이용하여 if문에서 제어 가능</h4>



<pre class="prettyprint"><code class="language-java hljs "><span class="hljs-keyword">if</span>(ButtonStatus.equals(<span class="hljs-string">"on"</span>) {...}

<span class="hljs-keyword">else</span> <span class="hljs-keyword">if</span>(ButtonStatus.equals(<span class="hljs-string">"off"</span>) {...}</code></pre>
</body>
</html>