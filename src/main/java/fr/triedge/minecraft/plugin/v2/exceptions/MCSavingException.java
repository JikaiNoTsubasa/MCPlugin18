package fr.triedge.minecraft.plugin.v2.exceptions;

public class MCSavingException extends Exception{

	private static final long serialVersionUID = 4206327276687351021L;

	public MCSavingException() {
		super();
	}

	public MCSavingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MCSavingException(String message, Throwable cause) {
		super(message, cause);
	}

	public MCSavingException(String message) {
		super(message);
	}

	public MCSavingException(Throwable cause) {
		super(cause);
	}
	
}
