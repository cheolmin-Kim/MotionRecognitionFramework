package com.mycompany.myapp.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mycompany.myapp.dto.Board;
import com.mycompany.myapp.dto.BoardComment;
import com.mycompany.myapp.dto.Hitcount;
import com.mycompany.myapp.dto.Likecount;
import com.mycompany.myapp.dto.Member;

@Component
public class BoardDaoImpl implements BoardDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardDaoImpl.class);

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/*
	 * Board
	 */
	@Override
	public int boardInsert(Board board) {
		sqlSessionTemplate.insert("board.insert", board);
		int bno = board.getBno();
		LOGGER.info(bno + "");
		return bno;
	}

	@Override
	public List<Board> boardSelectPage(int pageNo, int rowsPerPage) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("endNum", pageNo * rowsPerPage);
		map.put("startNum", (pageNo - 1) * rowsPerPage);
		List<Board> list = sqlSessionTemplate.selectList("board.selectPage", map);
		return list;
	}

	@Override
	public int boardCountAll() {
		int count = sqlSessionTemplate.selectOne("board.countAll");
		return count;
	}

	@Override
	public Board boardSelectByBno(int bno) {
		Board board = sqlSessionTemplate.selectOne("board.selectByBno", bno);
		return board;
	}

	@Override
	public void boardUpdateBhitcount(int bno, String mid, int bhitcount) {
		Map<String, String> map = new HashMap<>();
		map.put("bno", String.valueOf(bno));
		map.put("mid", mid);
		map.put("bhitcount", String.valueOf(bhitcount + 1));

		int result = sqlSessionTemplate.update("board.updateBhitcount", map);
		if (result == 1) {
			Hitcount hitcount = new Hitcount();
			hitcount.setBno(bno);
			hitcount.setMid(mid);
			// hitcount.setMid(mname);
			sqlSessionTemplate.insert("hitcount.insert", hitcount);
		}
	}

	@Override
	public void boardDelete(int bno) {
		sqlSessionTemplate.delete("board.delete", bno);
	}

	@Override
	public void boardUpdate(Board board) {
		sqlSessionTemplate.update("board.update", board);
	}

	@Override
	public List<Board> boardSearchBySearch(int pageNo, int rowsPerPage, String category, String bsearch) {
		Map<String, String> map = new HashMap<>();
		map.put("endNum", String.valueOf(pageNo * rowsPerPage));
		map.put("startNum", String.valueOf((pageNo - 1) * rowsPerPage));
		map.put("bsearch", bsearch);

		List<Board> list = null;
		if (category.equals("title")) {
			list = sqlSessionTemplate.selectList("board.searchtitle", map);
		} else if (category.equals("content")) {
			list = sqlSessionTemplate.selectList("board.searchcontent", map);
		} else if (category.equals("titlecontent")) {
			list = sqlSessionTemplate.selectList("board.searchtitlecontent", map);
		} else if (category.equals("writer")) {
			list = sqlSessionTemplate.selectList("board.searchwriter", map);
		}
		return list;
	}

	@Override
	public int boardCountAll(String category, String bsearch) {
		int count = 0;
		Map<String, String> map = new HashMap<>();
		map.put("bsearch", bsearch);

		if (category.equals("title")) {
			count = sqlSessionTemplate.selectOne("board.searchtitlecount", map);
		} else if (category.equals("content")) {
			count = sqlSessionTemplate.selectOne("board.searchcontentcount", map);
		} else if (category.equals("titlecontent")) {
			count = sqlSessionTemplate.selectOne("board.searchtitlecontentcount", map);
		} else if (category.equals("writer")) {
			count = sqlSessionTemplate.selectOne("board.searchwritercount", map);
		}
		return count;
	}

	@Override
	public void boardUpdateBlikecount(int bno, String mid, int blikecount) {
		Map<String, String> map = new HashMap<>();
		map.put("bno", String.valueOf(bno));
		map.put("blikecount", String.valueOf(blikecount + 1));
		map.put("mid", mid);
		int result = sqlSessionTemplate.update("board.updateBlikecount", map);

		Likecount likecount = new Likecount();
		likecount.setBno(bno);
		likecount.setMid(mid);
		if (result == 1) {
			sqlSessionTemplate.insert("likecount.insert", likecount);
		} else {
			sqlSessionTemplate.delete("likecount.delete", likecount);
			map.put("blikecount", String.valueOf(blikecount - 1));
			sqlSessionTemplate.update("board.updateBlikecount", map);
		}
	}

	/*
	 * BoardComment
	 */

	@Override
	public int boardCommentInsert(BoardComment comment) {
		sqlSessionTemplate.insert("boardcomment.insert", comment);
		int bcno = comment.getBcno();
		LOGGER.info(bcno + "");
		return bcno;
	}

	@Override
	public List<BoardComment> boardCommentList(int bno) {
		List<BoardComment> list = sqlSessionTemplate.selectList("boardcomment.selectcommentlist", bno);
		return list;
	}

	@Override
	public BoardComment boardSelectByBcno(int bcno) {
		BoardComment boardComment = sqlSessionTemplate.selectOne("boardcomment.selectByBcno", bcno);
		return boardComment;
	}

	@Override
	public void boardCommentDelete(int bcno) {
		sqlSessionTemplate.delete("boardcomment.delete", bcno);
	}

}
