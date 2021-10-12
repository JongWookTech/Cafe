package dto;

import java.util.Date;

public class BoardDTO {

static Date now = new Date();

public int notice_num;
public String member_id;
public String notice_pw;
public String notice_name;
public String contents;

public int getNoticenum() {
    return notice_num;
 }

 public void setNoticenum(int noticenum) {
    this.notice_num = noticenum;
 }

 public String getNoticename() {
    return notice_name;
 }

 public void setNoticename(String noticename) {
    this.notice_name = noticename;
 }

 public String getNoticeContents() {
    return contents;
 }

 public void setNoticeContents(String contents) {
    this.contents = contents;
 }


public BoardDTO(String member_id, String notice_pw, String notice_name, String contents) {
	super();
	System.out.println(now);
	this.notice_num = 0;
	this.member_id = member_id;
	this.notice_pw = notice_pw;
	this.notice_name = notice_name;
	this.contents = contents;

}

public BoardDTO(String member_id) {
	this.member_id = member_id;
}

@Override
	public String toString() {
		return notice_num+"\t"+member_id+"\t"+notice_pw+"\t"+notice_name+"\t"+contents;
	}
 
}
