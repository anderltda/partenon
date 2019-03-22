package br.com.project.commons.exception;

import java.lang.reflect.Constructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.project.commons.util.StringUtil;

@SuppressWarnings("unchecked")
public class ExceptionFactory {
	private static final Log log = LogFactory.getLog(ExceptionFactory.class);

	// factories - para padrão (ie, sem T)
	public static final BaseException createException(int id, Throwable t) {
		return new BaseException(id, t);
	}

	public static final BaseException createException(int id, String message) {
		return new BaseException(id, message);
	}

	public static final BaseException createException(String codigo, String message) {
		return new BaseException(codigo, message);
	}

	public static final BaseException createException(Throwable t) {
		return new BaseException(t);
	}

	public static final BaseException createException(String message, Throwable t) {
		return new BaseException(message, t);
	}

	public static final BaseException createException(int id, String message, Throwable t) {
		return new BaseException(id, message, t);
	}

	public static final BaseException createException(String codigo, String message, Throwable t) {
		return new BaseException(codigo, message, t);
	}

	public static final BaseException createException(int id, String codigo, String message, Throwable t) {
		return new BaseException(id, codigo, message, t);
	}

	// factories para T
	public static final <T extends BaseException> T createException(Class<T> targetExceptionType, Throwable error) {
		if (targetExceptionType.isInstance(error)) {
			return (T) error;
		} else {
			Class<?>[] intArgsClass = new Class[] { Throwable.class };
			try {
				Constructor<T> c = targetExceptionType.getConstructor(intArgsClass);
				return c.newInstance(error);
			} catch (Exception e) {
				log.error("Erro no tratamento ExceptionFactory " + targetExceptionType + " Throwable: " + error);
				log.error(e);
				try {
					T returnValue = targetExceptionType.newInstance();
					return returnValue;
				} catch (Exception e1) {
					return (T) error;
				}
			}
		}
	}

	public static final <T extends BaseException> T createException(Class<T> targetExceptionType, int id, Throwable error) {
		if (targetExceptionType.isInstance(error)) {
			((T) error).setId(id);
			return (T) error;
		} else {
			Class<?>[] intArgsClass = new Class[] { Throwable.class };
			try {
				Constructor<T> c = targetExceptionType.getConstructor(intArgsClass);
				T returnValue = c.newInstance(error);
				((T) error).setId(id);
				return returnValue;
			} catch (Exception e) {
				log.error("Erro no tratamento ExceptionFactory " + targetExceptionType + "  id: " + id + " Throwable: " + error);
				log.error(e);
				try {
					T returnValue = targetExceptionType.newInstance();
					return returnValue;
				} catch (Exception e1) {
					return (T) error;
				}
			}
		}
	}

	public static final <T extends BaseException> T createException(Class<T> targetExceptionType, String message, Throwable error) {
		if (targetExceptionType.isInstance(error) && error != null && StringUtil.isEqual(error.getMessage(), message)) {
			return (T) error;
		} else {
			Class<?>[] intArgsClass = new Class[] { String.class, Throwable.class };
			try {
				Constructor<T> c = targetExceptionType.getConstructor(intArgsClass);
				return c.newInstance(message, error);
			} catch (Exception e) {
				log.error("Erro no tratamento ExceptionFactory " + targetExceptionType + "  message: " + message + " Throwable: " + error);
				log.error(e);
				try {
					T returnValue = targetExceptionType.newInstance();
					return returnValue;
				} catch (Exception e1) {
					return (T) error;
				}
			}
		}
	}

	public static final <T extends BaseException> T createException(Class<T> targetExceptionType, int id, String message, Throwable error, String msgBundleError) {
		T returnValue = createException(targetExceptionType, message, error);
		returnValue.setId(id);
		returnValue.setMsgBundleError(msgBundleError);
		return returnValue;
	}

	public static final <T extends BaseException> T createException(Class<T> targetExceptionType, int id, String codigo, String message, Throwable error) {
		T returnValue = createException(targetExceptionType, id, message, error, null);
		returnValue.setCodigo(codigo);
		return returnValue;
	}

	public static final <T extends BaseException> T createException(Class<T> targetExceptionType, int id, String message) {
		Class<?>[] intArgsClass = new Class[] { Integer.TYPE, String.class };
		try {
			Constructor<T> c = targetExceptionType.getConstructor(intArgsClass);
			T returnValue = c.newInstance(id, message);
			return returnValue;
		} catch (Exception e) {
			log.error("Erro no tratamento ExceptionFactory " + targetExceptionType + " id :" + id + " message: " + message);
			log.error(e);
			try {
				T returnValue = targetExceptionType.newInstance();
				return returnValue;
			} catch (Exception e1) {
				return (T) null;
			}
		}
	}

}