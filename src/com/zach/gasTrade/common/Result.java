package  com.zach.gasTrade.common;

public class Result {
	public static final int OK = 0;
	public static final int FAILURE = -1;

	private Integer code;
	private String msg;

	public Result() {
		this.code = Integer.valueOf(OK);
	}

	public Result(Integer code) {
		this.code = code;
	}

	public Result(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Result{" + "code='" + code + '\'' + ", msg='" + msg + '\''
				+ '}';
	}

	public static Result initResult() {

		return new Result(0, "success!");
	}

}
