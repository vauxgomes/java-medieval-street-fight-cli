package core;

import java.io.Serializable;

public class Message implements Serializable {

	/** Types */
	public enum Type {
		ATTACK(), UPDATE(), MESSAGE(), END();
	}

	/** SERIAL ID */
	private static final long serialVersionUID = 1L;

	/* Variables */
	private Type type;
	private Object data;

	/**
	 * @param type
	 */
	public Message(Type type) {
		this(type, null);
	}

	/**
	 * @param type
	 * @param data
	 */
	public Message(Type type, Object data) {
		this.type = type;
		this.data = data;
	}

	public Type getType() {
		return type;
	}

	public Object getData() {
		return data;
	}
}
