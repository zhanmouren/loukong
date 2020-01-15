package com.koron.common.web.mapper;

/**<pre>
 * 用来处理层级关系的对象.
 * 通过四个属性seq  parentMask mask childMask来进行判断。类似IP地址掩码。
 * 下级编码的全0和全1被排除，不参作为编码.
 * 例如: 全个属性分别为：0001,1000,0000,0000 4 2 3 
 * 表示上级有四位，父级的seq为 0001,0000,0000,0000 本级为2，即0001,10XX,XXXX,XXXX为下级编码.
 * 直接下级为 0001,10XX,X000,0000.
 * 直接下级编码有三个，即可以有 001 010 011 100 101 110六个. 000 111不能作为编码。
 * 根据需要可以直接使用 {@link Integer} {@link Long} {@link ByteArray} {@link IntArray} {@link LongArray}.
 * </pre>
 * @author swan
 *
 * @param <T>
 */
public interface TreeBean<T> {
	/**
	 * 用来存储当前层别标志的数值.
	 * 可以为整数，长型，byte数组，整数数组,长型数组
	 * @return
	 */
	public T getSeq();
	/**
	 * 父级掩码长度
	 * @return
	 */
	public int getParentMask();
	/**
	 * 自身掩码长度
	 * @return
	 */
	public int getMask();
	/**
	 * 直属下级掩码长度
	 * @return
	 */
	public int getChildMask();
	
	
	
	public static abstract class AbstractLevelBean<T> implements TreeBean<T>
	{
		private int parentMask;
		private int mask;
		private int childMask;
		@Override
		public int getParentMask() {
			return parentMask;
		}

		@Override
		public int getMask() {
			return mask;
		}

		@Override
		public int getChildMask() {
			return childMask;
		}

		/**
		 * @param parentMask the parentMask to set
		 */
		public AbstractLevelBean<T> setParentMask(int parentMask) {
			this.parentMask = parentMask;
			return this;
		}

		/**
		 * @param childMask the childMask to set
		 */
		public AbstractLevelBean<T> setChildMask(int childMask) {
			this.childMask = childMask;
			return this;
		}

		/**
		 * @param mask the mask to set
		 */
		public AbstractLevelBean<T> setMask(int mask) {
			this.mask = mask;
			return this;
		}
		
	}
	/**<pre>
	 * 使用整数来存储层别的数值.
	 * 有效倍数为31.
	 * </pre>
	 * @author swan
	 *
	 */
	public static class Integer extends AbstractLevelBean<java.lang.Integer>
	{
		private int seq;
		@Override
		public java.lang.Integer getSeq() {
			return seq;
		}
		public Integer setSeq(int seq){
			this.seq = seq;
			return this;
		}
	}
	/**<pre>
	 * 使用长型来存储层别的数值.
	 * 有效位数为63
	 * </pre>
	 * @author swan
	 *
	 */
	public static class Long extends AbstractLevelBean<java.lang.Long>
	{
		private long seq;
		@Override
		public java.lang.Long getSeq() {
			return seq;
		}
		public Long setSeq(java.lang.Long seq){
			this.seq = seq;
			return this;
		}
	}
	/**
	 * 使用byte数组来存储层别的数值
	 * @author swan
	 *
	 */
	public static class ByteArray extends AbstractLevelBean<byte[]>
	{
		private byte[] seq;
		@Override
		public byte[] getSeq() {
			return seq;
		}
		public ByteArray setSeq(byte[] seq){
			this.seq = seq;
			return this;
		}
	}
	/**
	 * 使用整型数组来存储层别的数值
	 * @author swan
	 *
	 */
	public static class IntArray extends AbstractLevelBean<int[]>
	{
		private int[] seq;
		@Override
		public int[] getSeq() {
			return seq;
		}
		public IntArray setSeq(int[] seq){
			this.seq = seq;
			return this;
		}
	}
	/**
	 * 使用长型数组来存储层别的数值
	 * @author swan
	 *
	 */
	public static class LongArray extends AbstractLevelBean<long[]>
	{
		private long[] seq;
		@Override
		public long[] getSeq() {
			return seq;
		}
		public LongArray setSeq(long[] seq){
			this.seq = seq;
			return this;
		}
	}
	/**
	 * 使用字符串来存储层别的数值
	 * @author swan
	 *
	 */
	public static class StringTree extends AbstractLevelBean<String>
	{
		private String seq;
		@Override
		public String getSeq() {
			return seq;
		}
		public StringTree setSeq(String seq){
			this.seq = seq;
			return this;
		}
	}
}