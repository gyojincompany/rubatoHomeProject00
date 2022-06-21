package com.gyojincompany.rubatohome.dto;

public class FBoardDto {
	
	private int fbnum;//게시판 번호
	private String fbid;//게시판에 글쓴이 아이디
	private String fbname;//게시판 글쓴이 이름
	private String fbcontent;//게시판에 글쓴 내용
	private String fbtitle;//게시판 제목
	private String fbdate;//게시판에 글쓴 날짜
	private String fbhit;//조회수
	
	public FBoardDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FBoardDto(int fbnum, String fbid, String fbname, String fbcontent, String fbtitle, String fbdate,
			String fbhit) {
		super();
		this.fbnum = fbnum;
		this.fbid = fbid;
		this.fbname = fbname;
		this.fbcontent = fbcontent;
		this.fbtitle = fbtitle;
		this.fbdate = fbdate;
		this.fbhit = fbhit;
	}

	public int getFbnum() {
		return fbnum;
	}

	public void setFbnum(int fbnum) {
		this.fbnum = fbnum;
	}

	public String getFbid() {
		return fbid;
	}

	public void setFbid(String fbid) {
		this.fbid = fbid;
	}

	public String getFbname() {
		return fbname;
	}

	public void setFbname(String fbname) {
		this.fbname = fbname;
	}

	public String getFbcontent() {
		return fbcontent;
	}

	public void setFbcontent(String fbcontent) {
		this.fbcontent = fbcontent;
	}

	public String getFbtitle() {
		return fbtitle;
	}

	public void setFbtitle(String fbtitle) {
		this.fbtitle = fbtitle;
	}

	public String getFbdate() {
		return fbdate;
	}

	public void setFbdate(String fbdate) {
		this.fbdate = fbdate;
	}

	public String getFbhit() {
		return fbhit;
	}

	public void setFbhit(String fbhit) {
		this.fbhit = fbhit;
	}
	
	
	
	

}
