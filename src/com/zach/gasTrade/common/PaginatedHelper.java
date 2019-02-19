package  com.zach.gasTrade.common;

import java.io.Serializable;

/**
 * 分页参数读取辅助类
 *
 */
public class PaginatedHelper implements Serializable {
	// 每页记录条数
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int page = 1;
	protected int pageSize;

	// 开始位置
	protected int startIndex;
	public static final int PAGE_SIZE = 20;

	/**
	 * 获取当前页
	 * 
	 * @return
	 */
	public int getCurrentPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	// 计算查询开始位置
	private void calculatestartIndex() {
		if (this.page > 0) {
		} else {
			page = 1;
		}
		this.startIndex = (this.page - 1) * this.pageSize;
	}

	/**
	 * 取得开始位置
	 * 
	 * @return
	 */
	public int getStartIndex() {
		calculatestartIndex();
		return startIndex;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (pageSize > 0) {
			this.pageSize = pageSize;
		} else {
			this.pageSize = PAGE_SIZE;
		}
	}

}
