package com.itu.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.log4j.Logger;

import com.google.protobuf.MessageOrBuilder;

public class ClassDeepCopy {
	static Logger logger = Log4jUtil.getLogger(ClassDeepCopy.class);

	/**
	 * copy bean to google protobuff.
	 * 
	 * @param bean
	 *            the bean to be copied
	 * @param proto
	 *            the proto to be filled in
	 * @param exceptions
	 * @return true copy right false copy failure.
	 */
	public static <T extends MessageOrBuilder> boolean CopyBeanToProto(Object bean, T proto, String... exceptions) {
		Class<? extends Object> c1 = bean.getClass();
		Class<? extends Object> c2 = proto.getClass();
		MutableBoolean flag = new MutableBoolean(true);

		// loop methods witch starts with "get"
		Arrays.stream(c1.getDeclaredMethods()).filter(x -> x.getName().startsWith("get")).forEach(x -> {
			String getMsgName = x.getName();
			logger.debug(String.format("getMsdName:%s", getMsgName));

			// if method included in exceptions, ignoring it.
				String mMethod = getMsgName.substring(3);
				logger.debug(String.format("field Name:%s", mMethod));
				if (Arrays.stream(exceptions).anyMatch(y -> y.toLowerCase().equals(mMethod.toLowerCase()))) {
					logger.info(String.format("Field %s is aborted..\n", mMethod));
					return;
				}

				// init set method, it's tail must the same as get method.
				String setMsgName = "set" + mMethod;
				logger.debug(String.format("setMsgName :%s", setMsgName));

				Optional<Method> opMethod = Arrays.stream(c2.getMethods()).filter(y -> y.getName().equals(setMsgName)).findFirst();
				Method m2;
				try {
					if (opMethod.isPresent()) {
						m2 = opMethod.get();
						Object value = x.invoke(bean);
						if (value != null) {
							m2.invoke(proto, value);
							logger.debug(String.format("field %s set correct, valeu is %s", mMethod, value.toString()));
						} else {
							logger.info(String.format("field %s is null!", mMethod));
						}
					} else {
						logger.debug(String.format("setMsgName :%s is not present in dest", setMsgName));
					}
				} catch (Exception e) {
					logger.debug(String.format("method %s error: %s", mMethod, e.getMessage()));
					logger.error("failed", e);
					e.printStackTrace();
					flag.setFalse();
				}

				logger.debug("\n");
			});
		return flag.getValue();
		// return false;
	}

	/**
	 * copy gootle proto buffer to bean.
	 * 
	 * @param proto
	 *            the proto copied from
	 * @param bean
	 *            the bean copied to
	 * @param exceptions
	 * @return true copy right false copy failure.
	 */
	public static <T extends MessageOrBuilder> boolean CopyProtoToBean(T proto, Object bean, String... exceptions) {
		Class<? extends Object> beanClass = bean.getClass();
		Class<? extends Object> protoClass = proto.getClass();
		MutableBoolean flag = new MutableBoolean(true);

		// loop methods witch starts with "get"
		Arrays.stream(beanClass.getDeclaredMethods()).filter(x -> x.getName().startsWith("set")).forEach(x -> {
			String setMethodName = x.getName();
			logger.debug(String.format("setMethodName:%s", setMethodName));

			// if method included in exceptions, ignoring it.
				String mMethod = setMethodName.substring(3);
				logger.debug(String.format("field Name:%s", mMethod));
				if (Arrays.stream(exceptions).anyMatch(y -> y.toLowerCase().equals(mMethod.toLowerCase()))) {
					logger.info(String.format("Field %s is aborted..\n", mMethod));
					return;
				}

				// init set method, it's tail must the same as get method.
				String getMethodName = "get" + mMethod;
				logger.debug(String.format("getMethodName :%s", getMethodName));

				Optional<Method> opMethod = Arrays.stream(protoClass.getMethods()).filter(y -> y.getName().equals(getMethodName)).findFirst();
				Method m2;
				try {
					if (opMethod.isPresent()) {
						m2 = opMethod.get();
						Object value = m2.invoke(proto);
						if (value != null) {
							x.invoke(bean, value);
							logger.debug(String.format("field %s set correct, valeu is %s", mMethod, value.toString()));
						} else {
							logger.info(String.format("field %s is null!", mMethod));
						}
					} else {
						logger.debug(String.format("getMethodName :%s is not present in dest", getMethodName));
					}
				} catch (Exception e) {
					logger.debug(String.format("method %s error: %s", mMethod, e.getMessage()));
					logger.error("failed", e);
					e.printStackTrace();
					flag.setFalse();
				}

				logger.debug("\n");
			});
		return flag.getValue();
		// return false;
	}

	public static boolean CopyObjects(Object source, Object dest, String... exceptions) {
		Class<? extends Object> c1 = source.getClass();
		Class<? extends Object> c2 = dest.getClass();
		MutableBoolean flag = new MutableBoolean(true);

		if (dest instanceof MessageOrBuilder) {
			logger.info("dest is message builder");
		} else if (source instanceof MessageOrBuilder) {
			logger.info("source is message builder");
		} else {
			logger.info("neither is message builder");
		}
		// loop methods witch starts with "get"
		Arrays.stream(c1.getDeclaredMethods()).filter(x -> x.getName().startsWith("get")).forEach(x -> {
			String getMsgName = x.getName();
			logger.debug(String.format("getMsdName:%s", getMsgName));

			// if method included in exceptions, ignoring it.
				String mMethod = getMsgName.substring(3);
				logger.debug(String.format("field Name:%s", mMethod));
				if (Arrays.stream(exceptions).anyMatch(y -> y.toLowerCase().equals(mMethod.toLowerCase()))) {
					logger.info(String.format("Field %s is aborted..\n", mMethod));
					return;
				}

				// init set method, it's tail must the same as get method.
				String setMsgName = "set" + mMethod;
				logger.debug(String.format("setMsgName :%s", setMsgName));

				Class<?> type;
				if (dest instanceof MessageOrBuilder) {
					type = getProtoType(x.getReturnType());
				} else if (source instanceof MessageOrBuilder) {
					type = getBeanType(x.getReturnType());
				} else {
					type = x.getReturnType();
				}
				try {
					Method m = c2.getMethod(setMsgName, type);
					Object value = x.invoke(source);
					if (value != null)
						m.invoke(dest, value);
					else
						logger.debug(String.format("%s is null!", mMethod));
				} catch (Exception e) {
					logger.debug(String.format("method %s error: %s", mMethod, e.getMessage()));
					logger.error("failed", e);
					e.printStackTrace();
					flag.setFalse();
				}

				logger.debug("\n");
			});
		return flag.getValue();
		// return false;
	}

	/**
	 * change Integer type to Primitive type.
	 * 
	 * @param type
	 * @return
	 */
	public static Class<?> getProtoType(Class<?> type) {
		Class<?> newtype;
		if (type.equals(Integer.class)) {
			newtype = Integer.TYPE;
		} else if (type.equals(Double.class)) {
			newtype = Double.TYPE;
		} else if (type.equals(Boolean.class)) {
			newtype = Boolean.TYPE;
		} else if (type.equals(Byte.class)) {
			newtype = Byte.TYPE;
		} else if (type.equals(Short.class)) {
			newtype = Short.TYPE;
		} else if (type.equals(Long.class)) {
			newtype = Long.TYPE;
		} else if (type.equals(Float.class)) {
			newtype = Float.TYPE;
		} else if (type.equals(Character.class)) {
			newtype = Character.TYPE;
		} else {
			newtype = type;
		}
		return newtype;
	}

	public static Class<?> getBeanType(Class<?> type) {
		Class<?> newtype;
		if (type.equals(Integer.TYPE)) {
			newtype = Integer.class;
		} else if (type.equals(Double.TYPE)) {
			newtype = Double.class;
		} else if (type.equals(Boolean.TYPE)) {
			newtype = Boolean.class;
		} else if (type.equals(Byte.TYPE)) {
			newtype = Byte.class;
		} else if (type.equals(Short.TYPE)) {
			newtype = Short.class;
		} else if (type.equals(Long.TYPE)) {
			newtype = Long.class;
		} else if (type.equals(Float.TYPE)) {
			newtype = Float.class;
		} else if (type.equals(Character.TYPE)) {
			newtype = Character.class;
		} else {
			newtype = type;
		}
		return newtype;
	}
}
