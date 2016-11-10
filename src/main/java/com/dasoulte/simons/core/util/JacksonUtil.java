package com.dasoulte.simons.core.util;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.FilterProvider;

public class JacksonUtil {
	/** The mapper. */
	private final ObjectMapper mapper;
	private final PrettyPrinter printer;

	/**
	 * Instantiates a new jackson util.
	 */
	private JacksonUtil() {
		printer = new DefaultPrettyPrinter();
		mapper = new ObjectMapper();
		mapper.enable(MapperFeature.AUTO_DETECT_GETTERS);
		mapper.enable(MapperFeature.AUTO_DETECT_IS_GETTERS);

		// ignoring unknown Properties
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		// for special charactor parsing
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

		// don't make null properties write out
		mapper.setSerializationInclusion(Include.NON_NULL);
	}

	/**
	 * Gets the single instance of JacksonUtil.
	 *
	 * @return single instance of JacksonUtil
	 */
	public static JacksonUtil getInstance() {
		return new JacksonUtil();
	}

	/**
	 * Gets the mapper.
	 *
	 * @return the mapper
	 */
	public static ObjectMapper getMapper() {
		return getInstance().mapper;
	}

	/**
	 * To json.
	 *
	 * @param object the object
	 * @return the string
	 */
	public static String toJson(Object object) {
		try {
			return getMapper().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * To json. by PrettyPrinter 
	 *
	 * @param object the object
	 * @return the string
	 */
	public static String toPrettyJson(Object object) {
		try {
			ObjectWriter writer = JacksonUtil.getMapper().writer(getInstance().printer);

			return writer.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * To json.
	 *
	 * @param object the object
	 * @return the string
	 */
	public static String toJson(FilterProvider filterProvider, Object object) {
		try {
			return getMapper().writer(filterProvider).writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * To object.
	 *
	 * @param <T> the generic type
	 * @param jsonStr the json str
	 * @param cls the cls
	 * @return the t
	 */
	public static <T> T toObject(String jsonStr, Class<T> cls) {
		try {
			return getMapper().readValue(jsonStr, cls);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T toObjectToObject(Object object, Class<T> cls) {
		return toObject(toJson(object), cls);
	}

	/**
	 * To object.
	 *
	 * @param <T> the generic type
	 * @param jsonStr the json str
	 * @param typeReference the type reference
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toObject(String jsonStr, TypeReference<?> typeReference) {
		try {
			return (T)getMapper().readValue(jsonStr, typeReference);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, Object> toMap(String jsonStr) {
		return toObject(jsonStr, new TypeReference<Map<String, Object>>() {
		});
	}

	public static HashMap<String, Object> toHashMap(String jsonStr) {
		return toObject(jsonStr, new TypeReference<HashMap<String, Object>>() {
		});
	}

	public static Map<String, Object> toMapFromObject(Object object) {
		return toObject(toJson(object), new TypeReference<Map<String, Object>>() {
		});
	}

	public static JsonNode toJsonNode(Reader reader) {
		try {
			return getMapper().readTree(reader);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the single instance of JacksonUtil.
	 *
	 * @return single instance of JacksonUtil
	 */
	public static JacksonUtil createInstance() {
		return new JacksonUtil();
	}

}
