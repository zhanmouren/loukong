package com.koron.inwlms.bean.DTO.common;

import org.springframework.stereotype.Component;

import com.koron.util.Constant;


/**
 * 文件配置信息
 * @author csh
 *
 */
@Component
public class FileConfigInfo {

	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public FileConfigInfo() {
		String os = System.getProperty("os.name");  
		if(os.toLowerCase().startsWith("win")){  
			//获取在window服务器下的文件存储路径
			this.path = Constant.WIN_FILE_PATH;
		}else {
			//获取在Linux服务器下的文件存储路径
			this.path =  Constant.LINUX_FILE_PATH;
		}
	}
}
		
