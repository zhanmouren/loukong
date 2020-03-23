package com.koron.common.web.util;

import org.swan.bean.MessageBean;

import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.koron.ebs.mybatis.SessionFactory;
import org.koron.ebs.mybatis.TaskAnnotation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koron.common.bean.StaffBean;
import com.koron.common.web.mapper.StaffMapper;
import com.koron.common.web.service.VariableService;
import com.koron.ebs.permission.ResourceLoader;
import com.koron.ebs.permission.StaffAccount;
import com.koron.ebs.permission.mybatis.SPILoader;
import com.koron.util.Constant;

/**
 * 周新宇原创. 方志文抄袭。 云之家登录使用
 * 
 * @author swan
 *
 */
public class YzjSSO {
	/**
	 * 存储ticket和用户的映射
	 */
	private static final HashMap<String, StaffAccount> map = new HashMap<>();
	/**
	 * 日志信息
	 */
	private final static Logger logger = Logger.getLogger(YzjSSO.class);

	private static String get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String body = null;
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(get);
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity);
		} catch (Exception e) {
			logger.error("http connection error.", e);
		} finally {
			try {
				response.close();
			} catch (Exception e) {
			}
		}
		return body;
	}

	/**
	 * 取云之家token
	 * 
	 * @param appid
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	private static String getAccess_token(String appid, String secret) throws Exception {
		// https://xuntong.gdhwater.com/openauth2/api/token?grant_type=client_credential&appid=10150&secret=gdhwatersafe
		if (appid == null) {
			logger.error("Please set variable of yunzhijia.config.appid");
			throw new Exception("Haven't yunzhijia.config.secret appid.");
		}
		if (secret == null || secret.isEmpty()) {
			logger.error("Please set variable of yunzhijia.config.secret");
			throw new Exception("Haven't yunzhijia.config.secret variable.");
		}
		StringBuffer action = new StringBuffer();
		String yunzjurl = VariableService.getVariable("yunzhijia", "config", "url");
		if (yunzjurl == null || yunzjurl.isEmpty()) {
			logger.error("Please set variable of yunzhijia.config.url");
			throw new Exception("Haven't yunzhijia.config.url variable.");
		}
		action.append(yunzjurl + "/openauth2/api/token?grant_type=client_credential&").append("appid=" + appid) // 设置服务号的appid
				.append("&secret=" + secret); // 设置服务号的密匙

		String token = get(action.toString());
		if (token == null || token.length() == 0) {
			throw new Exception("取云之家Access_token返回空字符");
		}
		HashMap<String, Object> map = new Gson().fromJson(token, new TypeToken<HashMap<String, Object>>() {
		}.getType());
		return (String) map.get("access_token");

	}

	/**
	 * 取云之家用户信息
	 * 
	 * @param ticket
	 * @param token
	 * @return
	 * @throws Exception
	 */
	private static String getUser(String ticket, String token) throws Exception {
		// 域名
		String yunzjurl = VariableService.getVariable("yunzhijia", "config", "url");
		// https://xuntong.gdhwater.com/openauth2/api/getcontext?ticket=29a61f388e035bb55da6d85a57b72257&access_token=2423cbbe47b5d914eafc4f2d85de70de
		StringBuffer action = new StringBuffer();
		action.append(yunzjurl + "/openauth2/api/getcontext?").append("ticket=" + ticket).append("&access_token=" + token);
		String user = get(action.toString());
		if (user == null || user.length() == 0) {
			throw new Exception("取云之家Access_token返回空字符");
		}
		try {
			HashMap<String, Object> map = new Gson().fromJson(user, new TypeToken<HashMap<String, Object>>() {
			}.getType());
			return (String) map.get("mobile");
		} catch (Exception e) {
			throw new Exception(user);
		}
	}

	/**
	 * 通过云之家的ticket 取用户的账号并登陆
	 * 
	 * @param factory
	 * @param account
	 * @param password
	 * @return
	 */
	@TaskAnnotation("login")
	public static final MessageBean<Void> login(SessionFactory factory, String ticket) {
		try {
			String account = "";// 当ticket为空时，表示这是从pc直接进入，为了测试方便，给一个默认帐号。
			logger.info("登陆人ticket=" + ticket);
			if (ticket != null && ticket.length() > 0) {
				StaffAccount tmpAccount = map.get(ticket);
				if(tmpAccount != null) {
					return MessageBean.create(Constant.MESSAGE_INT_SUCCESS, ticket, void.class);
				}
				logger.info("开始登陆云之家");
				String access_token = getAccess_token(VariableService.getVariable("yunzhijia", "config", "appid"),
						VariableService.getVariable("yunzhijia", "config", "secret"));
				logger.info("云之家access_token=" + access_token);
				account = getUser(ticket, access_token);
				logger.info("云之家mobile=" + account);
			} else {
				return MessageBean.create(Constant.MESSAGE_INT_NOMODULE, "请从云之家登陆本系统", Void.class);
			}
			StaffMapper mapper = factory.getMapper(StaffMapper.class);
			StaffAccount staff = new SPILoader().get(account, StaffAccount.class, ResourceLoader.ENTITY_ACCOUNT_INT);
			StaffBean bean = mapper.getStaffByLoginId(account);
			if (staff == null || bean == null) {
				return MessageBean.create(Constant.MESSAGE_INT_NOMODULE, "用户账号" + account + "不存在", Void.class);
			}
			staff.setStaff(bean);
			map.put(ticket, staff);
			return MessageBean.create(Constant.MESSAGE_INT_SUCCESS, ticket, Void.class);
		} catch (Exception e) {
			logger.error("手机登陆失败", e);
			return MessageBean.create(Constant.MESSAGE_INT_NOMODULE, e.getMessage(), Void.class);
		}
	}

	/**
	 * 根据ticket获取相应得账号
	 * 
	 * @param key
	 * @return
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	public static StaffAccount getUser(String ticket) {
		if(ticket == null || ticket.isEmpty())
			return null;
		Object o = map.get(ticket);
		if(o == null) {
			try(SessionFactory factory = new SessionFactory()){
				factory.runTask(YzjSSO.class, "login",MessageBean.class, ticket);
			}
		}
		return map.get(ticket);
	}
}