package com.mycompany.myapp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mycompany.myapp.dao.BoardDao;
import com.mycompany.myapp.dto.Board;
import com.mycompany.myapp.dto.BoardComment;
import com.mycompany.myapp.dto.Hitcount;

@Component
public class BoardServiceImpl implements BoardService {

	@Resource(name = "boardDaoImpl")
	private BoardDao dao;

	@Override
	public void boardWrite(Board Board) {
		dao.boardInsert(Board);
	}

	@Override
	public List<Board> boardListPage(int pageNo, int rowsPerPage) {
		List<Board> list = dao.boardSelectPage(pageNo, rowsPerPage);
		return list;
	}

	@Override
	public int boardTotalRows() {
		int count = dao.boardCountAll();
		return count;
	}

	@Override
	public Board getBoard(int bno) {
		Board board = dao.boardSelectByBno(bno);
		return board;
	}
	@Override
	public void getBoardHit(int bno, String mid) {
		Board board = dao.boardSelectByBno(bno);		
		dao.boardUpdateBhitcount(bno, mid, board.getBhitcount());
	}

	@Override
	public Board getBoardImg(int bno) {
		Board board = dao.boardSelectByBno(bno);
		return board;
	}

	@Override
	public String boardCheckBpassword(int bno, String bpassword) {
		String result = "fail";
		Board board = dao.boardSelectByBno(bno);
		if (board.getBpassword().equals(bpassword)) {
			result = "success";
		}
		return result;
	}

	@Override
	public void boardDelete(int bno) {
		dao.boardDelete(bno);
	}

	@Override
	public void boardUpdate(Board Board) {
		dao.boardUpdate(Board);
	}

	@Override
	public Board getBoardLike(int bno, String mid) {
		Board board = dao.boardSelectByBno(bno);
		dao.boardUpdateBlikecount(bno, mid, board.getBlikecount());
		return board;
	}

	@Override
	public List<Board> boardSearchListPage(int pageNo, int rowsPerPage, String category, String bsearch) {
		List<Board> list = dao.boardSearchBySearch(pageNo, rowsPerPage, category, bsearch);
		return list;
	}

	@Override
	public int boardSearchTotalRows(String category, String bsearch) {
		int count = dao.boardCountAll(category, bsearch);
		return count;
	}

	@Override
	public void boardCommentWrite(BoardComment comment) {
		dao.boardCommentInsert(comment);
	}

	@Override
	public List<BoardComment> boardCommentList(int bno) {
		List<BoardComment> list = dao.boardCommentList(bno);
		return list;
	}

	@Override
	public String boardCommentCheckBpassword(int bcno, String bcpassword) {
		String result = "fail";
		BoardComment boardcomment = dao.boardSelectByBcno(bcno);
		if (boardcomment.getBcpassword().equals(bcpassword)) {
			result = "success";
		}
		return result;
	}

	@Override
	public void boardCommentDelete(int bcno) {
		dao.boardCommentDelete(bcno);
	}

}
