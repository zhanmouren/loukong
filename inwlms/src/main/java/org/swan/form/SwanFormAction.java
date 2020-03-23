package org.swan.form;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.*;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.koron.util.Tools;

@Controller
public class SwanFormAction {
	/**
	 * xml转html
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
//	@RequestMapping("/form/{key}.htm")
//	@ResponseBody
//	public String xmlToHtml(@PathVariable("key") String key) {
//		String data = "";
//		HashMap<String, Object> datas = new HashMap<>();
//		// 实例化 DocumentBuilderFactory 对象
//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		try {
//			// 得到 DocumentBuilder 对象
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			// 加载 test.xml，并将其转换为 Document 对象
//			Document doc = db.parse(Tools.getWebFile("template"+File.separatorChar+key.replace('.', '/') + ".xml").openStream());
//			// 实例 DOMSource 对象
//			DOMSource source = new DOMSource(doc);
//			// 加载 xsl 文件
//			StreamSource ss = new StreamSource(getClass().getClassLoader().getResourceAsStream("form.xslt"));
//			// 实例化 TransformerFactory 对象
//			TransformerFactory tff = TransformerFactory.newInstance();
//			Transformer tf = tff.newTransformer(ss);
//			
//			StringWriter sw = new StringWriter();
//			Result resulted = new StreamResult(sw);
//			// 设定字符编码方式
//			tf.setOutputProperty("encoding", "utf-8");
//			tf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//			// 将定义好的 .xsl 格式 转换
//			tf.transform(source, resulted);
//			String str = null;
//			str = sw.toString();
//			ArrayList<String> resultFile = new ArrayList<String>();
//			// 判断html有哪些控件，获取相应js
//			XPath xpath = XPathFactory.newInstance().newXPath();
//			// 判断map的元素在doc中是否存在
//			for (String urlKey : SwanFormMapper.keyToUrl.keySet()) {
//				XPathExpression express = xpath.compile("//" + urlKey);
//				NodeList list = (NodeList) express.evaluate(doc, XPathConstants.NODESET);
//				// 存在，传值
//				if (list != null && list.getLength() > 0) {
//					resultFile.addAll(SwanFormMapper.keyToUrl.get(urlKey));
//				}
//			}
//			XPathExpression express = xpath.compile("/widge/css");
//			NodeList list = (NodeList) express.evaluate(doc, XPathConstants.NODESET);
//			// 存在，传值
//			if (list != null && list.getLength() > 0) {
//				for (int i = 0; i < list.getLength(); i++) {
//					resultFile.add(((Element)list.item(i)).getAttribute("src"));
//				}
//			}
//			express = xpath.compile("/widge/script");
//			list = (NodeList) express.evaluate(doc, XPathConstants.NODESET);
//			// 存在，传值
//			if (list != null && list.getLength() > 0) {
//				System.out.println("found script");
//				for (int i = 0; i < list.getLength(); i++) {
//					resultFile.add(((Element)list.item(i)).getAttribute("src"));
//				}
//			}
//			System.out.println(resultFile);
//			datas.put("html", str);
//			datas.put("jsAndCss", resultFile);
//			data = new ObjectMapper().writeValueAsString(datas);
//		} catch (IllegalArgumentException | XPathExpressionException | ParserConfigurationException | SAXException | IOException
//				| TransformerFactoryConfigurationError | TransformerException ex) {
//			ex.printStackTrace();
//		}
//		return data;
//	}
}
