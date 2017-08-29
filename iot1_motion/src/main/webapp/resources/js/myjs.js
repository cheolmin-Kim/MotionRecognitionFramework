function leave() {
	if (confirm("정말 탈퇴하시겠습니까?") == true) { // 확인
		$.ajax({ 
			url:"leave",
			method:"post",
			data:{"mid":"${member.mid}"},
			success:function(data){
				
					location.href="/"
				
			}
		});
	} else { 
		return;
	}
}
