package fr.triedge.minecraft.plugin.v2.exceptions;

public class MCLoadingException extends Exception{

	private static final long serialVersionUID = -5135125210363776448L;
	
	public MCLoadingException(String text) {
		super(text);
	}

	public MCLoadingException() {
		super();
	}

	public MCLoadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MCLoadingException(String message, Throwable cause) {
		super(message, cause);
	}

	public MCLoadingException(Throwable cause) {
		super(cause);
	}
	
	

}
