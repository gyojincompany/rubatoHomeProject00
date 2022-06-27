package com.gyojincompany.rubatohome.dao;

import java.util.ArrayList;

import com.gyojincompany.rubatohome.dto.FBoardDto;
import com.gyojincompany.rubatohome.dto.FileDto;
import com.gyojincompany.rubatohome.dto.ReplyDto;

public interface IDao {
	
	//freeboard 용 dao
	public void fbwriteDao(String fbname, String fbtitle, String fbcontent, String fbid);//자유게시판 글쓰기
	public ArrayList<FBoardDto> fblistDao();//자유게시판 리스트 가져오기
	public int fblistcountDao();//자유게시판 리스트 글 개수 가져오기
	public void fbhitDao(String fbnum);//자유게시판 조회수 증가
	public FBoardDto fbviewDao(String fbnum);//자유게시판 글 보기
	public ArrayList<FBoardDto> fbTitleSearchlistDao(String keyword);//자유게시판 제목에서 검색한 결과 리스트 가져오기
	public ArrayList<FBoardDto> fbContentSearchlistDao(String keyword);//자유게시판 내용에서 검색한 결과 리스트 가져오기
	public ArrayList<FBoardDto> fbWriterSearchlistDao(String keyword);//자유게시판 글쓴이에서 검색한 결과 리스트 가져오기
	public void fbdeleteDao(String fbnum);//자유게시판 글 삭제
	
	public void fbfileInsertDao(int bno, String fileName, String fileOriName, String fileUrl, String fileExtension);
	//파일 원본이름, 변경된이름, 파일이 첨부된 게시글 번호, 파일 경로를 DB에 저장
	public FileDto fbfileInfoDao(String bno);//게시글 번호로 검색하여 해당 게시글에 첨부된 파일의 모든 정보 불러오기
	
	//member 용 dao
	public void memberjoinDao(String mid, String mpw, String mname, String memail);//회원 가입 하기
	public int checkIdDao(String mid);//회원 아이디 존재 여부 확인
	public int checkIdPwDao(String mid, String mpw);//로그인 시 회원 아이디와 비번 일치여부 체크
	
	//reply 용 dao
	public void rbwrite(int rborifbnum, String rbid, String rbcontent);
	public ArrayList<ReplyDto> rblist(String fbnum);//덧글이 달린 원글의 게시판 번호를 인수로 호출
	public void rbreplycount(String fbnum);//실행시 freeboard의 덧글수 필드(fbreplycount)의 값이 1씩 증가 
	
	
	
	
	
}
