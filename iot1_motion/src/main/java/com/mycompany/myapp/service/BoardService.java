package com.mycompany.myapp.service;

import java.util.List;

import com.mycompany.myapp.dto.Board;
import com.mycompany.myapp.dto.BoardComment;

public interface BoardService {
	// Board
	public void boardWrite(Board Board);	
	public List<Board> boardListPage(int pageNo, int rowsPerPage);
	public int boardTotalRows();
	public Board getBoard(int bno);
	public void getBoardHit(int bno, String mid);
	public Board getBoardImg(int bno);
	public String boardCheckBpassword(int bno, String bpassword);
	public void boardDelete(int bno);
	public void boardUpdate(Board Board);
	public Board getBoardLike(int bno, String mid);
	
	// search
	public List<Board> boardSearchListPage(int pageNo, int rowsPerPage, String category, String bsearch);
	public int boardSearchTotalRows(String category, String bsearch);

	// BoardComment
	public void boardCommentWrite(BoardComment comment);
	public List<BoardComment>boardCommentList(int bcno);
	public String boardCommentCheckBpassword(int bcno, String bcpassword);
	public void boardCommentDelete(int bcno);
}
