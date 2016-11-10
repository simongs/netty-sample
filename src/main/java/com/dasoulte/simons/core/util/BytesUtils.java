package com.dasoulte.simons.core.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.lang.StringUtils;

public class BytesUtils {

	public enum FillerPosition {
		LEFT, /* 좌측을 채움 */
		RIGHT /* 우측을 채움 */
	}

	public enum FillerType {
		ZERO((byte)48), /** 0으로 채움 **/
		BLANK((byte)32);
		/** 공백으로 채움 **/

		private byte byteValue;

		FillerType(byte byteValue) {
			this.byteValue = byteValue;
		}

		public byte getByteValue() {
			return byteValue;
		}
	}

	/**
	 * byte 자르기 
	 * @param strBytes  : 원본 바이트 배열
	 * @param start 	: 바이트 배열 중 자를 시작점
	 * @param size 		: 자를 문자 길이
	 * @param charset   : String으로 변경시 인코딩할 캐릭터셋
	 * @param charset
	 * @return
	 */
	public static String getStringFromReceiveByte(byte[] strBytes, int start, int size, String charset) {
		int end = start + size;

		int k = 0;
		byte[] temp = new byte[size];
		for (int i = start; i < end; i++) {
			temp[k] = strBytes[i];
			k++;
		}

		String result = StringUtils.EMPTY;
		try {
			result = new String(temp, charset);
		} catch (Exception e) {
			result = StringUtils.EMPTY;
		}
		return result;
	}

	/**
	 * byte 자르기
	 * (인코딩, 길이, 패딩 및 패딩문자 전각 고려)
	 * 
	 * @param src 실제 문자열
	 * @param charSet 문자열 인코딩할 캐릭터 셋
	 * @param size 총길이
	 * @param fillerType ZERO or BLANK
	 * @param fillerPos LEFT or RIGHT
	 * @param isRightPaddingWithFullChar TRUE or FALSE
	 * @return
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getBytes(String src, String charSet, int size, FillerType fillerType, FillerPosition fillerPos, boolean isRightPaddingWithFullChar) throws IllegalArgumentException, UnsupportedEncodingException {
		if (src == null) {
			src = "";
		}

		byte[] rv = src.getBytes(charSet);

		if (rv.length > size) {
			byte[] work = new byte[size];
			System.arraycopy(rv, 0, work, 0, work.length);
			rv = work;
		} else if (rv.length < size) {
			byte[] work = new byte[size];
			if (FillerPosition.LEFT == fillerPos) {
				System.arraycopy(rv, 0, work, size - rv.length, rv.length);
				for (int i = size - rv.length - 1; i >= 0; i--) {
					work[i] = fillerType.getByteValue();
				}
			} else {
				System.arraycopy(rv, 0, work, 0, rv.length);

				if (isRightPaddingWithFullChar) {
					byte[] charBytes = TextUtils.convertToFullChar(Character.toString((char)fillerType.getByteValue())).getBytes(charSet);

					int idx = rv.length;
					while (idx < size) {
						for (byte c : charBytes) {
							work[idx] = c;
							idx++;
						}
					}
				} else {
					for (int i = rv.length; i < size; i++) {
						work[i] = fillerType.getByteValue();
					}
				}

			}
			rv = work;
		}
		return rv;
	}

	/**
	 * byte 자르기
	 * (인코딩, 길이, 패딩 및 전각여부 미고려)
	 * 
	 * @param src 실제 문자열
	 * @param charSet 문자열 인코딩할 캐릭터 셋
	 * @param size 총길이
	 * @param fillerType ZERO or BLANK
	 * @param fillerPos LEFT or RIGHT
	 * @return
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getBytes(String src, String charSet, int size, FillerType fillerType, FillerPosition fillerPos) throws IllegalArgumentException, UnsupportedEncodingException {
		return getBytes(src, charSet, size, fillerType, fillerPos, false);
	}

	/**
	 * 기존 getBytes에서 바이트 사이즈보다 큰 한글 리턴받을 경우 정확하게 잘리지 않는 오류가 있어서 (ex 사이즈 12, 문자: PAYCO한글넘어)
	 * 사이즈보다 더 작게 trim하여 바이트 계산하도록 수정. for 송금-입금처리, 
	 * @param src 실제 문자열
	 * @param charSet 문자열 인코딩할 캐릭터 셋
	 * @param size 총길이
	 * @param fillerType ZERO or BLANK
	 * @param fillerPos LEFT or RIGHT
	 * @return
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getBytesTrim(String src, String charSet, int size, FillerType fillerType, FillerPosition fillerPos) throws IllegalArgumentException, UnsupportedEncodingException {
		if (src == null) {
			src = "";
		}
		byte[] rv = src.getBytes(charSet);
		if (rv.length > size) {
			for (int i = 0; i < src.length(); i++) {
				byte[] rvtrim = src.getBytes(charSet);
				src = src.substring(0, src.length() - 1);

				if (src.getBytes(charSet).length <= size) {
					rv = rvtrim;
					break;
				}
			}

			byte[] work = new byte[size];
			System.arraycopy(rv, 0, work, 0, work.length);
			rv = work;
		} else if (rv.length < size) {
			byte[] work = new byte[size];
			if (fillerPos == FillerPosition.LEFT) {
				System.arraycopy(rv, 0, work, size - rv.length, rv.length);
				for (int i = size - rv.length - 1; i >= 0; i--) {
					work[i] = fillerType.getByteValue();
				}
			} else {
				System.arraycopy(rv, 0, work, 0, rv.length);
				for (int i = rv.length; i < size; i++) {
					work[i] = fillerType.getByteValue();
				}
			}
			rv = work;
		}
		return rv;
	}

	/**
	 * @param strBytes 원본 바이트 배열
	 * @param size 자를 사이즈 
	 * @param start 바이트 배열 중 자를 시작점
	 * @param end 바이트 배열 중 자를 끝점
	 * @param charset - String으로 변경시 인코딩할 캐릭터셋
	 * @return
	 */
	public static String getStringFromReceiveByte(byte[] strBytes, int size, int start, int end, String charset) {
		int k = 0;
		byte[] temp = new byte[size];
		for (int i = start; i < end; i++) {
			temp[k] = strBytes[i];
			k++;
		}

		String result = StringUtils.EMPTY;
		try {
			result = new String(temp, charset);
		} catch (Exception e) {
			result = StringUtils.EMPTY;
		}
		return result;
	}

	/**
	 * 레코드 길이를 표현하기 위한 바이트 배열 유틸
	 * 
	 * @param length
	 * @return
	 */
	public static byte[] shortToBytes(short length) {
		byte[] bytes = new byte[2];
		for (int i = 0; i < 2; ++i) {
			bytes[1 - i] = (byte)(length & 0xff);
			length >>= 8;
		}
		return bytes;
	}

	public static short bytesToShort(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.allocate(2);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.put(bytes[0]);
		bb.put(bytes[1]);
		return bb.getShort(0);
	}

}
