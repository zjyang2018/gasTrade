package  com.zach.gasTrade.common;

public class PageResult extends DataResult {
	public PageResult() {
		super();
	}

	/**
	 * 总记录数
	 */
	private long allCount;
	/**
	 * 总页数
	 */
	private int pageCount;
	/**
	 * 页码
	 */
	private int pageNum;
	/**
	 * 每页条数
	 */
	private int pageSize;

	public PageResult(Integer code) {
		super(code);
	}

	public PageResult(Integer code, String msg) {
		super(code, msg);
	}

	public long getAllCount() {
		return allCount;
	}

	public void setAllCount(long allCount) {
		this.allCount = allCount;
	}

	public int getPageCount() {
		if(allCount%pageSize==0) {
			return (int) (allCount/pageSize);
		}else {
			return (int) (allCount/pageSize+1);
		}
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public static PageResult initResult() {

		return new PageResult(0, "success!");
	}
}
