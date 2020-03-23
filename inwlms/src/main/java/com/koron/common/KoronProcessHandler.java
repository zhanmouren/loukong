package com.koron.common;

import java.text.DecimalFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.koron.ebs.util.Utils;
import org.koron.ebs.util.field.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koron.beetl.BeetlTool;
import com.koron.beetl.HttpFormat;
import com.koron.common.bean.DefineFieldBean;
import com.koron.util.Constant;
import com.koron.util.Tools;

public class KoronProcessHandler implements FieldBeanProcessHandler {
	public static final int FLAG_CONDITION = 8;
	public static final int FLAG_REQUIRED = 1;
	public static final Logger logger = Logger.getLogger(KoronProcessHandler.class);
	private LinkedHashMap<String, ItemFunction> map = new LinkedHashMap<String, KoronProcessHandler.ItemFunction>();
	@SuppressWarnings("unused")
	private FieldBeanProcessor processor = null;

	public KoronProcessHandler(final FieldBeanProcessor processor) {
		this.processor = processor;
		put("flag", new ItemFunction() {
			@Override
			public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
				if ((((DefineFieldBean) bean).getFlag() & FLAG_REQUIRED) != 0)
					tag.attr("required", " ");
				if ((bean.getType() & FieldBean.TEXTFIELD_DATETIME) != 0) {
					if ((bean.getType() & FieldBean.TEXTFIELD_DATETIME_DATE) != 0) {
						bean.setClassName(bean.getClassName() + " datepicker");
					} else if ((bean.getType() & FieldBean.TEXTFIELD_DATETIME_TIME) != 0) {
						bean.setClassName(bean.getClassName() + " timepicker");
					} else {
						bean.setClassName(bean.getClassName() + " datetimepicker");
					}
					processor.fireListener(new FieldParseEvent(bean, FieldParseEvent.HAS_DATE));
				} else if ((bean.getType() & FieldBean.IMAGE) != 0)
					processor.fireListener(new FieldParseEvent(bean, FieldParseEvent.HAS_PIC));

				String val = ((DefineFieldBean) bean).getDefaultValue();
				String defaultValue = Utils.isNull(val, "");
				if (defaultValue != null)
					tag.attr("value", defaultValue);
				String placeHolder = bean.getDesc();
				if (placeHolder != null) {
					tag.attr("placeholder", placeHolder);
				}
				if ((bean.getType() & FieldBean.TEXTFIELD_PASSWORD) != 0) {
					tag.attr("type", "password");
				}
				if ((bean.getType() & FieldBean.TEXTFIELD_HIDDEN) != 0) {
					tag.attr("type", "hidden");
				}
				return tag;
			}
		});
		put("name", new ItemFunction() {
			@Override
			public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
				tag.attr("name", String.valueOf(value));
				if (((FieldBean.CHECKBOX + FieldBean.RADIO + FieldBean.IMAGE + FieldBean.LABEL) & bean.getType()) == 0) {
					tag.attr("id", String.valueOf(value));
				}
				return tag;
			}
		});
		put("desc", new ItemFunction() {
			@Override
			public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
				if ((bean.getType() & FieldBean.TEXTFIELD) == FieldBean.TEXTFIELD || (bean.getType() & FieldBean.TEXTAREA) == FieldBean.TEXTAREA) {
					tag.attr("placeholder", value.toString());
				}
				return tag;
			}
		});
		put("className", new ItemFunction() {
			@Override
			public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
				if ((bean.getType() & FieldBean.IMAGE) == 0) {
					tag.addClass(String.valueOf(value));
				} else {
					tag.find("input", false).addClass(String.valueOf(value));
				}
				return tag;
			}
		});

		put("validate", new ItemFunction() {
			@Override
			public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
				String strMap = String.valueOf(value);
				if (strMap.equals("null") || strMap.isEmpty())
					return tag;
				Map<String, Object> tmp = null;
				tmp = new Gson().fromJson(strMap, new TypeToken<Map<String, Object>>() {
				}.getType());
				for (Entry<String, Object> entry : tmp.entrySet()) {
					if (entry.getKey().equals("class"))
						tag.addClass(String.valueOf(entry.getValue()));
					else
						tag.attr(entry.getKey(), String.valueOf(entry.getValue()));
				}
				return tag;
			}
		});

		put("param_attr", new ItemFunction() {
			@Override
			public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
				Map<String, Object> tmp = null;
				if (value instanceof Map)
					tmp = (Map) value;
				else if (value instanceof String)
					tmp = new Gson().fromJson(String.valueOf(value), new TypeToken<Map<String, Object>>() {
					}.getType());
				else
					return tag;
				for (Entry<String, Object> entry : tmp.entrySet()) {
					if (entry.getKey().equals("class"))
						tag.addClass(String.valueOf(entry.getValue()));
					else
						tag.attr(entry.getKey(), String.valueOf(entry.getValue()));
				}
				return tag;
			}
		});
		put("param_css", new ItemFunction() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
				Map<String, Object> tmp = null;
				if (value instanceof Map)
					tmp = (Map) value;
				else if (value instanceof String)
					tmp = new Gson().fromJson(String.valueOf(value), new TypeToken<Map<String, Object>>() {
					}.getType());
				else
					return tag;
				for (Entry<String, Object> entry : tmp.entrySet()) {
					tag.css(entry.getKey(), String.valueOf(entry.getValue()));
				}
				return tag;
			}
		});
		put("param_url", new ItemFunction() {
			@Override
			public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
				HtmlTag ret = tag;
				String url = String.valueOf(value);
				if ((bean.getType() & (FieldBean.RADIO + FieldBean.CHECKBOX + FieldBean.SELECT + FieldBean.LABEL)) != 0)// 如果是单选，复选，下拉框
				{
					if ((bean.getType() & FieldBean.AJAXDATA) != 0)// 动态数据
					{
						LinkedHashMap<String, String> enumData = Tools.getDyn(url);
						if (tag.getTag().equals("select"))// 下拉框，往下面加option
						{
							for (Entry<String, String> entry : enumData.entrySet()) {
								HtmlTag option = new HtmlTag("option").attr("value", entry.getKey()).append(new HtmlTag("", entry.getValue()));
								if (String.valueOf(entry.getKey()).equals(data) || entry.getValue().equals(data))
									option.attr("selected", "selected");
								tag.append(option);
							}
						} else if (tag.getTag().equals("input")) {
							ret = new HtmlTag("");
							for (Entry<String, String> entry : enumData.entrySet()) {
								HtmlTag tmp = new HtmlTag(tag.getTag(), tag.getContent());
								tmp.setList(tag.getList());
								tmp.setAttrs(tag.getAttrs());
								tmp.attr("value", entry.getKey());
								if (String.valueOf(entry.getKey()).equals(data) || entry.getValue().equals(data))
									tmp.attr("checked", "checked");
								ret.append(new HtmlTag("label").append(tmp).append(new HtmlTag("", entry.getValue())));

							}
						} else if ((bean.getType() & FieldBean.LABEL) != 0) {
							tag.append(new HtmlTag("", enumData.get(data)));
						}
					}
					tag = ret;
				}
				return tag;
			}
		});

		put("param_data", new ItemFunction() {
			@Override
			public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
				HtmlTag ret = tag;
				if ((bean.getType() & (FieldBean.RADIO + FieldBean.CHECKBOX + FieldBean.SELECT + FieldBean.LABEL)) != 0)// 如果是单选，复选，下拉框
				{
					if ((bean.getType() & FieldBean.STATICDATA) != 0)// 静态数据
					{
						LinkedHashMap<String, String> enumData = new Gson().fromJson(String.valueOf(value), new TypeToken<LinkedHashMap<String, String>>() {
						}.getType());
						if (tag.getTag().equals("select"))// 下拉框，往下面加option
						{
							for (Entry<String, String> entry : enumData.entrySet()) {
								HtmlTag option = new HtmlTag("option").attr("value", entry.getKey()).append(new HtmlTag("", entry.getValue()));
								if (String.valueOf(entry.getKey()).equals(data) || entry.getValue().equals(data))
									option.attr("selected", "selected");
								tag.append(option);
							}
						} else if (tag.getTag().equals("input")) {
							ret = new HtmlTag("");
							for (Entry<String, String> entry : enumData.entrySet()) {
								HtmlTag tmp = new HtmlTag(tag.getTag(), tag.getContent());
								tmp.setList(tag.getList());
								tmp.setAttrs(tag.getAttrs());
								tmp.attr("value", entry.getKey());
								if (String.valueOf(entry.getKey()).equals(data) || entry.getValue().equals(data))
									tmp.attr("checked", "checked");
								ret.append(new HtmlTag("label").append(tmp).append(new HtmlTag("", entry.getValue())));
							}
						} else if ((bean.getType() & FieldBean.LABEL) != 0) {
							tag.append(new HtmlTag("", enumData.get(data)));
						}

					}
				}
				return ret;
			}
		});
		put("param_enum", new ItemFunction() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
				HtmlTag ret = tag;
				if (data == null)
					return ret;
				String[] defaultValues = data.toString().split(",");
				if ((bean.getType() & (FieldBean.RADIO + FieldBean.CHECKBOX + FieldBean.SELECT + FieldBean.LABEL)) != 0)// 如果是单选，复选，下拉框
				{
					String enumKey = bean.getParam("enum");
					String regx = "(?:[^(]*)(?:\\((.*)\\))?";
					Pattern p = Pattern.compile(regx);
					Matcher m = p.matcher(enumKey);
					Object[] param = null;
					if (m.find() && m.groupCount() > 0) {
						String args = m.group(1);
						if (args != null && !args.trim().isEmpty()) {
							String[] arg = args.split("\\s*,\\s*");
							param = new Object[arg.length];
							for (int i = 0; i < arg.length; i++) {
								param[i] = processor.getValue(arg[i]);
							}
						}
					}
					if (enumKey.indexOf("(") != -1)
						enumKey = enumKey.substring(0, enumKey.indexOf("("));

					EnumElement element = Tools.getEnumByKey(enumKey, param);
					if (element != null) {
						HashMap<Object, String> enumData = element.getItem();
						if (tag.getTag().equals("select"))// 下拉框，往下面加option
						{
							for (Entry<Object, String> entry : enumData.entrySet()) {
								HtmlTag option = new HtmlTag("option").attr("value", String.valueOf(entry.getKey())).append(new HtmlTag("", entry.getValue()));
								if (String.valueOf(entry.getKey()).equals(data) || entry.getValue().equals(data))
									option.attr("selected", "selected");
								tag.append(option);
							}
						} else if (tag.getTag().equals("input")) {
							ret = new HtmlTag("");
							for (Entry<Object, String> entry : enumData.entrySet()) {
								HtmlTag tmp = new HtmlTag(tag.getTag(), tag.getContent());
								LinkedList<HtmlTag> tmpList = new LinkedList<HtmlTag>();
								tmpList.addAll(tag.getList());
								tmp.setList(tmpList);
								TreeMap<String, String> mapTmp = new TreeMap<String, String>();
								mapTmp.putAll(tag.getAttrs());
								tmp.setAttrs(mapTmp);
								tmp.attr("value", String.valueOf(entry.getKey()));
								if (element != null && element.isBit())
									tmp.attr("isbit", "isbit");
								for (String string : defaultValues) {
									if (entry.getKey().equals(string) || entry.getValue().equals(string)) {
										tmp.attr("checked", "checked");
									} else if (entry.getKey() instanceof Integer) {
										int item = (Integer) entry.getKey();
										int tmpData = 0;
										if (string != null && !string.isEmpty())
											tmpData = Integer.parseInt(string);
										if ((item & tmpData) != 0)
											tmp.attr("checked", "checked");
									} else if (entry.getKey() instanceof Long) {
										long item = (Long) entry.getKey();
										long tmpData = 0l;
										if (string != null && !string.isEmpty())
											tmpData = Long.parseLong(string);
										if ((item & tmpData) != 0)
											tmp.attr("checked", "checked");
									}
								}
								ret.append(new HtmlTag("label").append(tmp).append(new HtmlTag("", entry.getValue())));
							}
						} else if ((bean.getType() & FieldBean.LABEL) != 0) {
							if (element != null && data != null && !data.toString().isEmpty())
								tag.append(new HtmlTag("", element.getValue(data, ",")));
						}
					}
				}
				return ret;
			}
		});

		put("param_option", new ItemFunction() {
			@Override
			public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
				if ((bean.getType() & (FieldBean.RADIO + FieldBean.CHECKBOX + FieldBean.SELECT)) != 0)// 如果是单选，复选，下拉框
				{
					HashMap<String, String> map2 = new Gson().fromJson(bean.getParam("option"), HashMap.class);
					if (tag.getTag().equals("select")) {
						HtmlTag option = new HtmlTag("option").attr("value", map2.get("key")).append(new HtmlTag("", map2.get("value")));
						tag.prepend(option);
					} else// 输入框
					{
						HtmlTag option = tag.getList().getFirst();
						option.getList().get(1).setContent(map2.get("value"));
						option.getList().getFirst().attr("value", map2.get("key"));
						tag.prepend(option);
					}
				}
				return tag;
			}
		});

		put("caption", new ItemFunction() {
			@Override
			public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
				DefineFieldBean defineBean = (DefineFieldBean) bean;
				if ((bean.getType() & FieldBean.LABEL + FieldBean.COMBIN) != 0)
					return tag;
				if ((defineBean.getFlag() & FLAG_CONDITION) == 0) {
					if ((bean.getType() & FieldBean.IMAGE) == 0) {// 如果是图片
						HtmlTag font = new HtmlTag("font");
						font.append(new HtmlTag("", bean.getCaption())).append(tag);
						tag = font;
					} else {
						String defaultValue = Utils.isNull(value, String.valueOf(data), "");
						String src = defaultValue;
						if (defaultValue.equals("")) {
							src = "images/example.png";
						}
						HtmlTag img = new HtmlTag("img").attr("src", src);
						Map<String, Object> tmp = null;
						if (bean.getParam("pic_css") != null && !bean.getParam("pic_css").trim().isEmpty()) {
							tmp = new Gson().fromJson(bean.getParam("pic_css"), new TypeToken<Map<String, Object>>() {
							}.getType());
							for (Entry<String, Object> entry : tmp.entrySet()) {
								img.css(entry.getKey(), String.valueOf(entry.getValue()));
							}
						}
						if (bean.getParam("pic_attr") != null && !bean.getParam("pic_attr").trim().isEmpty()) {
							tmp = new Gson().fromJson(bean.getParam("pic_attr"), new TypeToken<Map<String, Object>>() {
							}.getType());

							for (Entry<String, Object> entry : tmp.entrySet()) {
								img.attr(entry.getKey(), String.valueOf(entry.getValue()));
							}
						}
						tag = img;
					}
				} else {
					// 针对图片类型进行特殊处理
					if ((bean.getType() & bean.IMAGE) != 0) {
						String defaultValue = Utils.isNull(value, String.valueOf(data), "");
						String src = defaultValue;
						if (defaultValue.equals("")) {
							src = "/images/example.png";
						}
						HtmlTag img = new HtmlTag("img").attr("src", src);
						Map<String, Object> tmp = null;
						if (bean.getParam("pic_css") != null && !bean.getParam("pic_css").trim().isEmpty()) {
							tmp = new Gson().fromJson(bean.getParam("pic_css"), new TypeToken<Map<String, Object>>() {
							}.getType());

							for (Entry<String, Object> entry : tmp.entrySet()) {
								img.css(entry.getKey(), String.valueOf(entry.getValue()));
							}
						}
						if (bean.getParam("pic_attr") != null && !bean.getParam("pic_attr").trim().isEmpty()) {
							tmp = new Gson().fromJson(bean.getParam("pic_attr"), new TypeToken<Map<String, Object>>() {
							}.getType());

							for (Entry<String, Object> entry : tmp.entrySet()) {
								img.attr(entry.getKey(), String.valueOf(entry.getValue()));
							}
						}

						img.attr("onclick", "addPic(this,document.getElementById('" + bean.getName() + "'));");
						tag = new HtmlTag("").append(img).append(tag);
					}

					HtmlTag dt = new HtmlTag("dt").append(new HtmlTag("", bean.getCaption()));
					if ((defineBean.getFlag() & 1) != 0) {
						// 必填
						dt.addClass("must");
					}
					// 编辑框
					HtmlTag dd = new HtmlTag("dd").append(tag);

					// 文本框后面附加的单位
					String measure = bean.getParam("measure");
					measure = measure == null ? "" : measure;
					int fixed = 2;
					if (measure.indexOf(',') != -1) {
						fixed = Integer.parseInt(measure.substring(measure.indexOf(',') + 1));
						measure = measure.substring(0, measure.indexOf(','));
					}

					if (measure != null && !measure.isEmpty() && (bean.getType() & FieldBean.TEXTFIELD) != 0)// 如果是文本框
					{
						tag.attr("fixed", "" + fixed);
						EnumElement element = Tools.getEnumByKey(measure);
						HashMap<Object, String> enumData = element.getItem();
						HtmlTag tg = null;
						if (enumData.size() == 0) {
							return tag;
						} else if (enumData.size() == 1) {
							for (Entry<Object, String> ent : enumData.entrySet()) {
								tg = new HtmlTag("label").append(new HtmlTag("", "(" + ent.getValue() + ")"));
								tag.attr("_measure", ent.getKey().toString());
							}
						} else {
							tg = new HtmlTag("select").attr("for", bean.getName()).addClass("_assist");
							int i = 0;
							for (Entry<Object, String> entry : enumData.entrySet()) {
								if (i++ == 0)
									tag.attr("_measure", entry.getKey().toString());
								HtmlTag option = new HtmlTag("option").attr("value", entry.getKey().toString()).append(new HtmlTag("", entry.getValue()));
								tg.append(option);
							}
							processor.fireListener(new FieldParseEvent(bean, Constant.HAS_ASSIST));
						}
						dt.append(tg);
					}

					if (defineBean.getLength() != null && defineBean.getLength() > 0) {
						dd.css("width", defineBean.getLength() + "px");
					}
					HtmlTag dl = new HtmlTag("dl").append(dt).append(dd).addClass("dataitem");
					if (bean.getParam("outerClass") != null) {
						dl.addClass(bean.getParam("outerClass"));
					}
					dd = new HtmlTag("dd").css("width", "auto").append(new HtmlTag("label").attr("class", "error").attr("for", bean.getName()));
					dl.append(dd);
					tag = dl;
				}
				return tag;
			}
		});
		put("value", new ItemFunction() {// 四类复选（下拉框，单选框，复选框，枚举型文本）的值已设置，此处不进行
					@Override
					public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
						if ((bean.getType() & FieldBean.TEXTFIELD) != 0) {
							HtmlTag tmp = tag.find("input", false);
							if (tmp.getList().size() > 0) {
								HtmlTag inp = tmp.getList().get(0);
								if (inp.attr("_measure") != null && !inp.attr("_measure").isEmpty() && !value.toString().trim().isEmpty()) {
									double a = Double.parseDouble(value.toString());
									double b = Double.parseDouble(inp.attr("_measure"));

									DecimalFormat df = new DecimalFormat();
									try {
										int fixed = Integer.parseInt(inp.attr("fixed"));
										char[] c = new char[fixed + 2];
										Arrays.fill(c, '#');
										c[1] = '.';
										df = new DecimalFormat(new String(c));
									} catch (NumberFormatException e) {
										e.printStackTrace();
									}
									inp.attr("value", df.format((a / b)));
								} else
									inp.attr("value", String.valueOf(new HttpFormat().format(String.valueOf(value),null)));
							}
						} else if ((bean.getType() & FieldBean.TEXTAREA) != 0) {
							HtmlTag tmp = tag.find("textarea", false);
							if (tmp.getList().size() > 0) {
								tmp.getList().get(0).append(new HtmlTag("", String.valueOf(value)));
							}
						} else if ((bean.getType() & FieldBean.LABEL) != 0 && bean.getParam("enum") == null) {
							HtmlTag tmp = tag.find("span", false);
							Double appendix = null;
							int fixed = 2;
							if (bean.getParam("measure") != null) {
								String measure = bean.getParam("measure");
								if (measure.indexOf(',') != -1) {
									fixed = Integer.parseInt(measure.substring(measure.indexOf(',') + 1));
									measure = measure.substring(0, measure.indexOf(','));
								}
								EnumElement<Object> element = Tools.getEnumByKey(measure);
								if (element.getItem() != null && element.getItem().size() != 0) {
									for (Entry<Object, String> ent : element.getItem().entrySet()) {
										Object o = ent.getKey();
										if (o instanceof Integer)
											appendix = ((Integer) o).doubleValue();
										else if (o instanceof Double)
											appendix = ((Double) o).doubleValue();
										break;
									}
								}
							}

							if (tmp.getList().size() > 0) {
								String caption = String.valueOf(value);
								if (value == null)
									caption = null;
								if (appendix != null && caption != null && !caption.trim().isEmpty()) {
									try {
										char[] c = new char[fixed + 2];
										Arrays.fill(c, '#');
										c[1] = '.';
										DecimalFormat df = new DecimalFormat(new String(c));
										caption = df.format(Double.parseDouble(caption) / appendix);
									} catch (NumberFormatException e) {
										e.printStackTrace();
									}
								}
								tmp.getList().get(0).append(new HtmlTag("", caption));
							}
						} else if ((bean.getType() & FieldBean.IMAGE) != 0) {
							tag.find("input", false).attr("value", String.valueOf(value));
						}
						return tag;
					}
				});
		put("param_wrap", new ItemFunction() {
			@Override
			public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
				HtmlTag tmp = new HtmlTag(String.valueOf(value)).append(tag);
				return tmp;
			}
		});

		put("param_reparse", new ItemFunction() {
			@Override
			public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
				try {
					tag.parse(String.valueOf(value));
				} catch (TagCmdParserException e) {
					e.printStackTrace();
				}
				return tag;
			}
		});

		put("param__PATTERN", new ItemFunction() {
			@Override
			public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
				BeetlTool tool = BeetlTool.getIntance();
				tool.put("bean", bean);
				tool.put("value", data);
				String tmp = tool.render(String.valueOf(value));
				tag = new HtmlTag("", tmp);
				return tag;
			}
		});
	}

	@Override
	public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data) {
		HtmlTag ret = map.get(key).handle(key, value, bean, tag, data);
		return ret;
	}

	@Override
	public Set<String> keys() {
		return map.keySet();
	}

	/**
	 * @param key
	 * @param value
	 * @return
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	public ItemFunction put(String key, ItemFunction value) {
		return map.put(key, value);
	}

	interface ItemFunction {
		public HtmlTag handle(String key, Object value, FieldBean bean, HtmlTag tag, Object data);
	}
}