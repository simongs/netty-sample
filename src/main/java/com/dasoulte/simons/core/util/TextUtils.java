package com.dasoulte.simons.core.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextUtils {
	/**
	 * 반각문자를 전각문자로 치환하여 리턴
	 * 
	 * @param halfChar
	 * @return
	 */
	public static String convertToFullChar(String halfChar) {
		if (halfChar == null) {
			return null;
		}

		StringBuffer strBuf = new StringBuffer();

		char c = 0;

		for (int i = 0; i < halfChar.length(); i++) {
			c = halfChar.charAt(i);
			if (c >= 0x21 && c <= 0x7e) {
				// 영문 알파벳 이거나 특수 문자
				c += 0xfee0;
			} else if (c == 0x20) {
				// 공백
				c = 0x3000;
			}
			strBuf.append(c);
		}
		return strBuf.toString();
	}

	/**
	 * 전각문자를 반각문자로 치환하여 리턴
	 * 
	 * @param fullChar
	 * @return
	 */
	public static String convertToHalfChar(String fullChar) {
		if (fullChar == null) {
			return null;
		}

		StringBuffer strBuf = new StringBuffer();

		char c = 0;

		for (int i = 0; i < fullChar.length(); i++) {
			c = fullChar.charAt(i);

			if (c >= '！' && c <= '～') {
				//영문이거나 특수 문자 일경우.
				c -= 0xfee0;
			} else if (c == '　') {
				// 공백
				c = 0x20;
			}
			strBuf.append(c);
		}
		return strBuf.toString();
	}
}
