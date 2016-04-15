package com.sendmail.milter.spi;

import com.sendmail.milter.IMilterHandler;

/**
 * Factory to produce FilterHanlder
 */
public interface IMilterHandlerFactory {

	/**
	 * @return A new instance of <u>your</u> fitler handler
	 */
	public IMilterHandler newInstance();
}
