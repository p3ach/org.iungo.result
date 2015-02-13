package org.iungo.result.api;

import java.io.Serializable;


public class Result implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Result FALSE = new Result(false, null, null);
	
	public static final Result TRUE = new Result(true, null, null);
	
	public static final Result UNDEFINED = new Result(null, null, null);
	
	public static void requireTrue(final Result result) {
		if (!result.isTrue()) {
			throw new UnsupportedOperationException();
		}
	}
	
	public static Result valueOf(final Throwable throwable) {
		return new Result(false, toString(throwable), throwable);
	}

	protected static String toString(final Throwable throwable) {
		if (throwable == null) {
			return "null";
		}
		final StringBuilder text = new StringBuilder();
		/*
		 * Throwable class + message.
		 */
		text.append("Class : " + throwable.getClass().getName() + "\n\nMessage : " + throwable.getMessage());
		/*
		 * Throwable stack trace. 
		 */
		text.append("\n\nStackTrace :");
		StackTraceElement[] stackTrace = throwable.getStackTrace();
		for (int i = 0; i < stackTrace.length; i++) {
			text.append("\n" + stackTrace[i]);
		}
		/*
		 * Throwable cause. 
		 */
		text.append("\n\nCause : \n[" + toString(throwable.getCause()) + "\n]");
		/*
		 * Throwable suppressed. 
		 */
		text.append("\n\nSuppressed :");
		Throwable[] suppressed = throwable.getSuppressed();
		for (int i = 0; i < suppressed.length; i++) {
			text.append("\n" + toString(suppressed[i]));
		}
		/*
		 * 
		 */
		return text.toString();
	}
	
	public static Result valueOf(final String text, final Exception exception) {
		return new Result(false, String.format("%s\n%s", text, toString(exception)), exception);
	}
	
	public static Result valueOf(final Boolean value) {
		return (value ? TRUE : FALSE);
	}
	
	public static void print(final Result result) {
		System.out.println(result);
	}
	
	protected final Boolean state;
	
	protected final String text;
	
	protected final Object value;
	
	public Result(final Boolean state, final String text, final Object value) {
		super();
		this.state = state;
		this.text = text;
		this.value = value;
	}

	public Boolean getState() {
		return state;
	}

	public String getText() {
		return text;
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue() {
		return (T) value;
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(T ifNull) {
		return (value == null ? ifNull : (T) value);
	}

	public Boolean isFalse() {
		final Boolean state = getState();
		return (state == null ? false : !state);
	}
	
	public Boolean isTrue() {
		final Boolean state = getState();
		return (state == null ? false : state);
	}
	
	public Boolean isUndefined() {
		return (getState() == null);
	}
	
	public void print() {
		print(this);
	}
	
	@Override
	public String toString() {
		return String.format("[%s]:[%s] [%s]", getState(), getText(), (getValue() == null ? null : getValue().getClass().getName()));
	}

}
