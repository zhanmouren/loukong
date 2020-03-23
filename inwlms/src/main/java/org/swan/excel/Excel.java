package org.swan.excel;

import org.swan.bean.MessageBean;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.FileResourceLoader;
import org.springframework.http.*;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koron.common.beetl.BeetlErrorHandler;
import com.koron.common.beetl.BeetlTool;
import com.koron.util.Constant;
import com.koron.util.Tools;
/**
 * 完成Excel的导出.
 * 
 * @author swan 
 *
 */
public class Excel {
	
	public static final HttpEntity<?> createFile(String fileName,File file, String template, Map<String, Object> map) {
		return createFile(fileName,file,template,map);
	}
	/**
	 * 根据数据和模板文件生成HttpEntity对象
	 * @param fileName 生成文件的文件名
	 * @param file excel模板文件
	 * @param template 数据转换模板文件
	 * @param map 数据，在数据模板里通过key访问对应的数据
	 * @return
	 */
	public static final HttpEntity<?> createFile(boolean isRead,String fileName,InputStream file, String template, Map<String, Object> map) {
		MessageBean<byte[]> ret = Excel.createFile(isRead,file,template, map);
		if(ret.getCode() == 0)
		{
			HttpHeaders header = new HttpHeaders();
			try {
				header.add("Content-Disposition", "attachment ; filename="+new String(fileName.getBytes("utf8"),"ISO8859-1")+".xlsx");
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}
			header.add("Content-Type", "application/octet-stream");
			ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(ret.getData(),header, HttpStatus.OK);
			return entity;
		}else
		{
			ResponseEntity<String> entity = new ResponseEntity<String>(ret.toJson(), HttpStatus.OK);
			return entity;
		}
	}
	
	/**
	 * 根据模板文件生成Excel文件. 生成文件的格式为xlsx
	 * 
	 * @param file 模板文件.如果为空,文件为新文件
	 * @param template Beetl解析模板.根目录为WEB下的excel目录
	 * @param map 数据
	 * @return
	 */
	private static final MessageBean<byte[]> createFile(boolean isRead,InputStream file, String template, Map<String, Object> map) {
		BeetlTool tool = BeetlTool.getIntance();
		for (Entry<String, Object> entry : map.entrySet()) {
			tool.put(entry.getKey(), entry.getValue());
		}
		tool.getCfg().setPlaceholderStart("#{");
		BeetlErrorHandler handler = new BeetlErrorHandler(false);
		tool.setErrorHandler(handler);
		try {
			PipedWriter writer = new PipedWriter();
			PipedReader reader = new PipedReader(writer);
			new Thread(() ->tool.renderTo(template, new ClasspathResourceLoader("/static"), writer)).start();
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true); 
			List<CellBean> data = mapper.readValue(reader, new TypeReference<List<CellBean>>() {
			});
			return createFile(isRead,file, data.toArray(new CellBean[0]));
		} catch (IOException ex) {
			if (handler.getEx() != null) {// beetl解析出错
				StringWriter writer = new StringWriter();
				handler.processExcption(handler.getEx(), writer,true);
				MessageBean<byte[]> msg = MessageBean.create(Constant.MESSAGE_INT_FILE_TRANSFER_ERROR, writer.toString(), byte[].class);
				return msg;
			}
			ex.printStackTrace();
			MessageBean<byte[]> msg = MessageBean.create(Constant.MESSAGE_INT_FILE_TRANSFER_ERROR, ex.getMessage(), byte[].class);
			return msg;
		}
	}

	/**
	 * 根据模板文件生成Excel文件. 生成文件的格式为xlsx
	 * 
	 * @param file 模板文件.如果为空,文件为
	 * @param beans 需要添加的数据
	 * @return
	 */
	private static final MessageBean<byte[]> createFile(InputStream file, CellBean... beans) {
		XSSFWorkbook wb = null;
		try {
			if (file != null) {
				File tmp = File.createTempFile("baseform", "xlsx");
				Files.copy(file, tmp.toPath(),StandardCopyOption.REPLACE_EXISTING);
				wb = (XSSFWorkbook) WorkbookFactory.create(tmp);
			} else
				wb = new XSSFWorkbook();
			XSSFSheet sheet = null;
			for (CellBean bean1 : beans) {
				if (bean1.getType() == 7) {
					sheet = wb.getSheet(bean1.getValue());
					if (sheet == null)
						sheet = wb.createSheet(bean1.getValue());
					continue;
				}
				if (sheet == null)
					sheet = wb.createSheet();
				Excel.add(bean1, sheet, wb);
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			wb.write(os);
			wb.close();
			MessageBean<byte[]> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, "success", byte[].class);
			msg.setData(os.toByteArray());
			return msg;
		} catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
			e.printStackTrace();
			MessageBean<byte[]> msg = MessageBean.create(Constant.MESSAGE_INT_FILE_TRANSFER_ERROR, e.getMessage(), byte[].class);
			return msg;
		}
	}
	private static final MessageBean<byte[]> createFile(boolean isRead,InputStream file, CellBean... beans) {
		XSSFWorkbook wb = null;
		try {
			if (file != null) {
				File tmp = File.createTempFile("baseform", "xlsx");
				Files.copy(file, tmp.toPath(),StandardCopyOption.REPLACE_EXISTING);
				wb = (XSSFWorkbook) WorkbookFactory.create(tmp);
			} else
				wb = new XSSFWorkbook();
			XSSFSheet sheet = null;
			for (CellBean bean1 : beans) {
				if (bean1.getType() == 7) {
					sheet = wb.getSheet(bean1.getValue());
					if (sheet == null)
						sheet = wb.createSheet(bean1.getValue());
					continue;
				}
				if (sheet == null)
					sheet = wb.createSheet();
				if(isRead){
					sheet.protectSheet(UUID.randomUUID().toString());
					sheet.lockSelectLockedCells(true);
					sheet.lockSelectUnlockedCells(true);
				}
				Excel.add(bean1, sheet, wb);
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			wb.write(os);			
			wb.close();
			MessageBean<byte[]> msg = MessageBean.create(Constant.MESSAGE_INT_SUCCESS, "success", byte[].class);
			msg.setData(os.toByteArray());
			return msg;
		} catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
			e.printStackTrace();
			MessageBean<byte[]> msg = MessageBean.create(Constant.MESSAGE_INT_FILE_TRANSFER_ERROR, e.getMessage(), byte[].class);
			return msg;
		}
	}
	/**
	 * 添加单元个的功能
	 * 
	 * @param bean 数据单元格
	 * @param sheet 表单
	 * @param wb 文件
	 */
	private static final void add(CellBean bean, XSSFSheet sheet, XSSFWorkbook wb) {
		if (bean == null)
			return;
		if (bean.getDrow() > 0 || bean.getDcol() > 0) {
			sheet.addMergedRegion(new CellRangeAddress(bean.getRow(), bean.getRow() + bean.getDrow(), bean.getCol(), bean.getCol() + bean.getDcol()));
		}
		XSSFRow row = sheet.getRow(bean.getRow());
		if (row == null)
			row = sheet.createRow(bean.getRow());
		XSSFCell cell = row.getCell(bean.getCol());
		if (cell == null)
			cell = row.createCell(bean.getCol());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			switch (bean.getType()) {
			case CellBean.TYPE_DOUBLE:
				cell.setCellValue(Double.parseDouble(bean.getValue()));
				break;
			case CellBean.TYPE_DATE:
				cell.setCellValue(df.parse(bean.getValue()));
				break;
			case CellBean.TYPE_BOOLEAN:
				cell.setCellValue(bean.getValue().equalsIgnoreCase("true"));
				break;
			default:
				cell.setCellValue(bean.getValue());
				break;
			}
		} catch (NumberFormatException | ParseException e) {
			e.printStackTrace();
		}
		if (bean.getFormat() != null && !bean.getFormat().isEmpty()) {
			XSSFCellStyle style = wb.createCellStyle();
			XSSFDataFormat format = wb.createDataFormat();
			style.setDataFormat(format.getFormat(bean.getFormat()));
			cell.setCellStyle(style);
		}
	}

	/**
	 * 往EXCEL里添加的单元格
	 * 
	 * @author swan
	 *
	 */
	public static class CellBean {
		/**
		 * 文本类型
		 */
		public static final int TYPE_STRING = 1;
		/**
		 * 数字类型
		 */
		public static final int TYPE_DOUBLE = 2;
		/**
		 * 逻辑型
		 */
		public static final int TYPE_BOOLEAN = 3;
		/**
		 * 日期型
		 */
		public static final int TYPE_DATE = 5;
		/**
		 * 富文本型
		 */
		public static final int TYPE_RICHTEXT = 6;
		/**
		 * 表页
		 */
		public static final int TYPE_SHEET = 7;
		/**
		 * 年月日格式
		 */
		public static final String FORMAT_SHORT_DATE = "yyyy-mm-dd";
		/**
		 * 时分秒格式
		 */
		public static final String FORMAT_SHORT_TIME = "hh:mm:ss";
		/**
		 * 时间日期格式
		 */
		public static final String FORMAT_SHORT_DATETIME = "yyyy-mm-dd hh:mm:ss";
		/**
		 * 逗号格式
		 */
		public static final String FORMAT_SHORT_DECIMAL = "#,##0";
		/**
		 * 两位小数点
		 */
		public static final String FORMAT_SHORT_DECIMAL_TWO = "#,##0.00";
		/**
		 * 值
		 */
		private String value;
		/**
		 * 单元格类型
		 */
		private int type = TYPE_STRING;
		/**
		 * 数据格式
		 */
		private String format;
		/**
		 * 列数
		 */
		private int col;
		/**
		 * 行数
		 */
		private int row;
		/**
		 * 合并列数
		 */
		private int drow = 0;
		/**
		 * 合并行数
		 */
		private int dcol = 0;

		/**
		 * @return 获取值
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @param 设置值
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * @return 获取单元格类型
		 */
		public int getType() {
			return type;
		}

		/**
		 * @param 设置单元格类型
		 */
		public void setType(int type) {
			this.type = type;
		}

		/**
		 * @return 获取数据格式
		 */
		public String getFormat() {
			return format;
		}

		/**
		 * @param 设置数据格式
		 */
		public void setFormat(String format) {
			this.format = format;
		}

		/**
		 * @return 获取列数
		 */
		public int getCol() {
			return col;
		}

		/**
		 * @param 设置列数
		 */
		public void setCol(int col) {
			this.col = col;
		}

		/**
		 * @return 获取行数
		 */
		public int getRow() {
			return row;
		}

		/**
		 * @param 设置行数
		 */
		public void setRow(int row) {
			this.row = row;
		}

		/**
		 * @return 获取合并列数
		 */
		public int getDrow() {
			return drow;
		}

		/**
		 * @param 设置合并列数
		 */
		public void setDrow(int drow) {
			this.drow = drow;
		}

		/**
		 * @return 获取合并y行数
		 */
		public int getDcol() {
			return dcol;
		}

		/**
		 * @param 设置合并y行数
		 */
		public void setDcol(int dcol) {
			this.dcol = dcol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "CellBean [value=" + value + ", type=" + type + ", format=" + format + ", col=" + col + ", row=" + row + ", drow=" + drow + ", dcol=" + dcol
					+ "]";
		}
	}
	public static void main(String[] args) {
		String str = "<xsl:if test=\"@v-if\"><xsl:attribute name=\"v-if\"><xsl:value-of select=\"@v-if\"/></xsl:attribute></xsl:if>\r\n";
		String arr[]=new String[] {"v-else","v-else-if","v-bind","v-for","v-model","v-once","v-html","v-show","v-onclick","v-ondbclick","v-oncontextmenu","v-onwheel","v-onmouseenter","v-onmouseover","v-onmousemove","v-onmousedown","v-onmouseup","v-onmouseleave","v-onmouseout","v-onkeydown","v-onkeypress","v-onkeyup","v-onsubmit","v-onreset","v-onfocus","v-onblur"};
		for (String string : arr) {
			System.out.print(str.replaceAll("v-if", string));
		}
	}
}
