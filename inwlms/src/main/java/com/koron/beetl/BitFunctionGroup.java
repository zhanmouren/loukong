package com.koron.beetl;
/**
 * 位运算方法集
 * @author swan
 *
 */
public class BitFunctionGroup {

	/**
	 * 并且
	 * @param a
	 * @param b
	 * @return
	 */
	public long and(long a, long b) {
		return a & b;
	}

	/**
	 * 或
	 * @param a
	 * @param b
	 * @return
	 */
	public long or(long a, long b) {
		return a | b;
	}

	/**
	 * 异或
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public long xor(long a, long b) {
		return a ^ b;
	}

	/**
	 * 取反
	 * 
	 * @param a
	 * @return
	 */
	public long nor(long a) {
		return ~a;
	}

	/**
	 * 左移
	 * 
	 * @param a
	 * @param offset
	 * @return
	 */
	public long shl(long a, int offset) {
		return a << offset;
	}

	/**
	 * 无符号右移
	 * 
	 * @param a
	 * @param offset
	 * @return
	 */
	public long shr(long a, int offset) {
		return a >>> offset;
	}

	/**
	 * 带符号右移
	 * 
	 * @param a
	 * @param offset
	 * @return
	 */
	public long shr2(long a, int offset) {
		return a >> offset;
	}
	/**
	 * 字符串转成长整型,支持0x 0
	 * @param a
	 * @return
	 */
	public long decode(String a) {
		return Long.decode(a);
	}

}
