package com.gyojincompany.rubatohome.dto;

public class FileDto {
	
	private int fno;//파일의 고유번호(시퀀스)
	private int bno;//파일이 첨부된 게시판 글 번호
	private String fileName;//변경된 파일 이름
	private String fileOriName;//원래 파일 이름
	private String fileUrl;//파일의 저장 경로
	
	public int getFno() {
		return fno;
	}
	public void setFno(int fno) {
		this.fno = fno;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileOriName() {
		return fileOriName;
	}
	public void setFileOriName(String fileOriName) {
		this.fileOriName = fileOriName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	

}
