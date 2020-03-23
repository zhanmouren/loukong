package com.koron.beetl;

import java.io.IOException;

import org.beetl.core.Resource;
import org.beetl.core.ResourceLoader;
import org.beetl.core.exception.BeetlException;
import org.beetl.core.exception.ErrorInfo;

public class CustomerException extends BeetlException {

	public CustomerException(String detailCode) {
		super(detailCode);
	}

	public String getDescription() {
		ErrorInfo error = new ErrorInfo(this);
		int line = error.getErrorTokenLine();
		StringBuilder sb = new StringBuilder(">>").append(error.getType()).append(":").append(error.getErrorTokenText()).append(" Line:").append(line);
		ResourceLoader resLoader = gt.getResourceLoader();
		// 潜在问题，此时可能得到是一个新的模板，不过可能性很小，忽略！
		String content = null;
		try {
			Resource res = resLoader.getResource(resourceId);
			// 显示前后三行的内容
			int[] range = getRange(line);
			content = res.getContent(range[0], range[1]);
			if (content != null) {
				String[] strs = content.split(cr);
				int lineNumber = range[0];
				for (int i = 0; i < strs.length; i++) {
					sb.append(lineNumber).append("|").append(strs[i]).append("\n");
					lineNumber++;
				}
			}
		} catch (IOException e) {
		}
		return sb.toString();
	}

	private int[] getRange(int line) {
		int startLine = Math.max(line - 3, 1);
		int endLine = startLine + 6;
		return new int[] { startLine, endLine };
	}
}
