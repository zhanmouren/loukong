package com.koron.common.bean;

import java.sql.Timestamp;

public class FileBean implements IdentityBean{
	/**
	 * 不可见(私有)1
	 */
	public static final Integer  FLAG_PRIVATE =1;
	/**
	 * 可见(公开)2
	 */
	public static final Integer  FLAG_PUBLIC =2;
	
	private String filename;
	
	private Integer id;
	/**
	 * 标识资源名
	 */
	private String key;
	/**
	 * 链接地址
	 */
	private String url;
	/**
	 * 类型
	 */
	private String mimetype;
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 被引用次数
	 */
	private Integer linknum=0;
	/**
	 * 文件是否可见(1不可见，2可见)，默认可见
	 */
	private Integer flag = 2;
	/**
	 * 0 默认产品图片，1自定义图片....
	 */
	private Integer type;
	private Timestamp createtime;
	/**
	*设置
	*/
	public void setId(Integer id){
		this.id = id;
	}
	/**
	*获取
	*/
	public Integer getId(){
		return id;
	}
	/**
	*设置标识资源名
	*/
	public void setKey(String key){
		this.key = key;
	}
	/**
	*获取标识资源名
	*/
	public String getKey(){
		return key;
	}
	/**
	*设置链接地址
	*/
	public void setUrl(String url){
		this.url = url;
	}
	/**
	*获取链接地址
	*/
	public String getUrl(){
		return url;
	}
	/**
	*设置类型
	*/
	public void setMimetype(String mimetype){
		this.mimetype = mimetype;
	}
	/**
	*获取类型
	*/
	public String getMimetype(){
		return mimetype;
	}
	/**
	*设置描述
	*/
	public void setDescription(String description){
		this.description = description;
	}
	/**
	*获取描述
	*/
	public String getDescription(){
		return description;
	}
	/**
	*设置被引用次数
	*/
	public void setLinknum(Integer linknum){
		this.linknum = linknum;
	}
	/**
	*获取被引用次数
	*/
	public Integer getLinknum(){
		return linknum;
	}
	/**
	 * 获取 文件是否可见(1不可见，2可见)，默认可见
	 * @return
	 */
	public Integer getFlag() {
		return flag;
	}
	/**
	 * 设置 是否可见(1不可见，2可见)，默认可见
	 * @param flag
	 */
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	/**
	*设置0 默认产品图片，1自定义图片....
	*/
	public void setType(Integer type){
		this.type = type;
	}
	/**
	*获取0 默认产品图片，1自定义图片....
	*/
	public Integer getType(){
		return type;
	}
	/**
	*设置
	*/
	public void setCreatetime(Timestamp createtime){
		this.createtime = createtime;
	}
	/**
	*获取
	*/
	public Timestamp getCreatetime(){
		return createtime;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public static Integer getFlagPrivate() {
		return FLAG_PRIVATE;
	}
	public static Integer getFlagPublic() {
		return FLAG_PUBLIC;
	}
	
	
}