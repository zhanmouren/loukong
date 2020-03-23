package com.koron.beetl;

import org.swan.form.SwanFormMapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.*;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class FormTemplate implements Function {
	@SuppressWarnings("unchecked")
	@Override
	public Object call(Object[] arg, Context ctx) {
		StringBuffer sb = new StringBuffer();
		if (arg == null || arg.length == 0)
			return "";
		try {
			BeetlTool tool = BeetlTool.getIntance();
			for (Entry<String,Object> entry : ctx.globalVar.entrySet()) {
				tool.put(entry.getKey(),entry.getValue());
			}
			tool.getCfg().setPlaceholderStart("{{");
			tool.getCfg().setPlaceholderEnd("}}");
			tool.getCfg().setStatementStart("<!--# ");
			tool.getCfg().setStatementEnd("#-->");
			CharArrayWriter bos = new CharArrayWriter();
			tool.renderTo("/template/" + arg[0] + ".xml", new ClasspathResourceLoader("/static/"), bos);
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new CharArrayReader(bos.toCharArray())));			DOMSource source = new DOMSource(doc);
			// 加载 xsl 文件
			StreamSource xslt = new StreamSource(getClass().getClassLoader().getResourceAsStream("form.xslt"));
			// 实例化 TransformerFactory 对象
			Transformer tf = TransformerFactory.newInstance().newTransformer(xslt);
			StringWriter sw = new StringWriter();
			Result result = new StreamResult(sw);
			tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			tf.transform(source, result);
			// 判断html有哪些控件，获取相应js
			XPath xpath = XPathFactory.newInstance().newXPath();
			// 判断map的元素在doc中是否存在
			StringBuilder resultFile = new StringBuilder();
			for (String urlKey : SwanFormMapper.keyToUrl.keySet()) {
				XPathExpression express = xpath.compile("//" + urlKey);
				NodeList list = (NodeList) express.evaluate(doc, XPathConstants.NODESET);
				// 存在，传值
				if (list != null && list.getLength() > 0) {
					for (String str : SwanFormMapper.keyToUrl.get(urlKey)) {
						resultFile.append(str+",");
					}
				}
			}
			if(ctx.getGlobal("flag") == null)
				ctx.set("flag", new HashMap<String,String>());
			((Map<String, String>) ctx.getGlobal("flag")).put("FORM_APPENDIX", resultFile.toString());
			String str = sw.toString().trim();
			if(str.startsWith("<div>")) str = str.substring("<div>".length());
			if(str.endsWith("</div>")) str = str.substring(0,str.length()-"</div>".length());
			return str;
		} catch (SAXException | IOException | ParserConfigurationException | TransformerFactoryConfigurationError | TransformerException | XPathExpressionException ex) {
			ex.printStackTrace();
		}
		return sb.toString();
	}
}
