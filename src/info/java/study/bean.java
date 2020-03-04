package info.java.study;
import java.util.Date;

public class bean {
	private String thread_no;
	private String thread_title;
	
	public String getthread_no() {
		return thread_no;
	}
	public void setthread_no(String thread_no) {
		this.thread_no = thread_no;
	}
	
	public String getthread_title() {
		return thread_title;
	}
	public void setthread_title(String thread_title) {
		this.thread_title = thread_title;
	}
	
	public bean(String thread_no, String thread_title) {
		this.thread_no = thread_no;
		this.thread_title = thread_title;
	}

}