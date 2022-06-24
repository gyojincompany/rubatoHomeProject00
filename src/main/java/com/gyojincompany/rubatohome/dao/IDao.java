package com.gyojincompany.rubatohome.dao;

import java.util.ArrayList;

import com.gyojincompany.rubatohome.dto.FBoardDto;

public interface IDao {
	
	//freeboard 용 dao
	public void fbwriteDao(String fbname, String fbtitle, String fbcontent, String fbid);//자유게시판 글쓰기
	public ArrayList<FBoardDto> fblistDao();//자유게시판 리스트 가져오기
	public int fblistcountDao();//자유게시판 리스트 글 개수 가져오기
	public void fbhitDao(String fbnum);//자유게시판 조회수 증가
	public FBoardDto fbviewDao(String fbnum);//자유게시판 글 보기
	
	
	//member 용 dao
	
}
