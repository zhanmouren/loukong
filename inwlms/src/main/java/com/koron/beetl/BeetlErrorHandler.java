package com.koron.beetl;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.beetl.core.ErrorHandler;
import org.beetl.core.Resource;
import org.beetl.core.ResourceLoader;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.ErrorInfo;

public class BeetlErrorHandler implements ErrorHandler {
	private boolean throwException;
	private BeetlException ex = null;
	
	public BeetlErrorHandler() {
		this(true);
	}
	public BeetlErrorHandler(boolean throwException) {
		this.throwException = throwException;
	}
	@Override
	public void processExcption(BeetlException ex, Writer writer) {
		processExcption(ex, writer, !throwException);
	}

	public void processExcption(BeetlException ex, Writer writer, boolean print) {
		this.ex = ex;
		ErrorInfo error = new ErrorInfo(ex);
		if (error.getErrorCode().equals(BeetlException.CLIENT_IO_ERROR_ERROR)) {
			// 不输出详细提示信息
			if (!ex.gt.getConf().isIgnoreClientIOError()) {
				println(writer, "Client IO Exception:" + getResourceName(ex.resourceId) + ":" + error.getMsg());
				if (ex.getCause() != null) {
					this.printThrowable(writer, ex.getCause());
				}
				return;
			}
		}

		int line = error.getErrorTokenLine();
		StringBuilder sb = new StringBuilder(">>").append(this.getDateTime()).append(":").append(error.getType()).append(":").append(error.getErrorTokenText())
				.append(" Line:").append(line).append(" Resource:").append(getResourceName(ex.resourceId));
		if (error.getErrorCode().equals(BeetlException.TEMPLATE_LOAD_ERROR)) {
			if (error.getMsg() != null)
				sb.append(error.getMsg());
			println(writer, sb.toString());
			return;
		}

		println(writer, sb.toString());
		if (ex.getMessage() != null) {
			println(writer, ex.getMessage());
		}

		ResourceLoader resLoader = ex.gt.getResourceLoader();
		// 潜在问题，此时可能得到是一个新的模板，不过可能性很小，忽略！
		String content = null;
		try {
			Resource res = resLoader.getResource(ex.resourceId);
			// 显示前后三行的内容
			int[] range = this.getRange(line);
			content = res.getContent(range[0], range[1]);
			if (content != null) {
				String[] strs = content.split(ex.cr);
				int lineNumber = range[0];
				for (int i = 0; i < strs.length; i++) {
					sb.append("\n").append(lineNumber).append("|").append(strs[i]);
					print(writer, "" + lineNumber);
					print(writer, "|");
					println(writer, strs[i]);
					lineNumber++;
				}
			}
		} catch (IOException e) {
		}

		if (error.hasCallStack()) {
			println(writer, "  ========================");
			println(writer, "  Call Stack:");
			for (int i = 0; i < error.getResourceCallStack().size(); i++) {
				println(writer, "  " + error.getResourceCallStack().get(i) + " Line：" + error.getTokenCallStack().get(i).line);
			}
		}
		printCause(error, writer);
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {

		}
		if (!print)
			throw new RuntimeException("Exception" + sb.toString());
	}

	protected void printCause(ErrorInfo error, Writer writer) {
		Throwable t = error.getCause();
		if (t != null) {
			printThrowable(writer, t);
		}

	}

	protected String getResourceName(String resourceId) {
		return resourceId;
	}

	protected void println(Writer w, String msg) {
		try {
			w.write(msg+"\n");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	protected void print(Writer w, String msg) {
		try {
			w.write(msg);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	protected void printThrowable(Writer w, Throwable t) {
		t.printStackTrace();
	}

	protected int[] getRange(int line) {
		int startLine = Math.max(line - 3, 1);
		int endLine = startLine + 6;
		return new int[] { startLine, endLine };
	}

	protected String getDateTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		return sdf.format(date);
	}

	/**
	 * @return the ex
	 */
	public BeetlException getEx() {
		return ex;
	}
}
