package com.mycompany.myapp.dao;

import java.util.List;

import com.mycompany.myapp.dto.Board;
import com.mycompany.myapp.dto.BoardComment;

public interface BoardDao {	
	// Board
	public int boardInsert(Board imageboard);
	public List<Board> boardSelectPage(int pageNo, int rowsPerPage);
	public int boardCountAll();
	public Board boardSelectByBno(int bno);
	public void boardUpdateBhitcount(int bno, String mid, int bhitcount);
	public void boardDelete(int bno);
	public void boardUpdate(Board Board);
	public void boardUpdateBlikecount(int bno, String mid, int blikecount);
	
	// search
	public List<Board> boardSearchBySearch(int pageNo, int rowsPerPage, String category, String bsearch);
	public int boardCountAll(String category, String bsearch);
	
	// BoardComment
	public int boardCommentInsert(BoardComment comment);
	public List<BoardComment>boardCommentList(int bcno);
	public BoardComment boardSelectByBcno(int bcno);
	public void boardCommentDelete(int bcno);	
}
