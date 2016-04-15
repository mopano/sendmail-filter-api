/*
 * Copyright (c) 2001-2004 Sendmail, Inc. All Rights Reserved
 */
package com.sendmail.milter;

import java.nio.ByteBuffer;
import java.util.regex.Pattern;

public class CustomMilterStatus implements IMilterStatus {

	String reply = null;

	/* From RFC 2034 Section 4
	 * 
	 * status-code ::= class "." subject "." detail
	 * class       ::= "2" / "4" / "5"
	 * subject     ::= 1*3digit
	 * detail      ::= 1*3digit
	 */
	private static final Pattern validator1 = Pattern.compile("^[245]\\d\\d$");
	private static final Pattern validator2 = Pattern.compile("^[245]\\.\\d{1,3}\\.\\d{1,3}$");

	private static void validateRcode(String rcode)
			throws IllegalArgumentException {
		if (rcode == null) {
			throw new IllegalArgumentException("rcode cannot be null");
		}
		if (!validator1.matcher(rcode).matches()) {
			throw new IllegalArgumentException("rcode must be a 4xx or 5xx code");
		}
	}

	private static void validateXcode(String xcode, String rcode)
			throws IllegalArgumentException {
		if (xcode == null) {
			return;
		}

		if (!validator2.matcher(xcode).matches()) {
			throw new IllegalArgumentException("xcode must be a 4.x.x or 5.x.x code");
		}

		// Classes must match -- 4xx rcode must match 4.x.x xcode, and 5xx rcode must match 5.x.x xcode
		if (rcode.charAt(0) != xcode.charAt(0)) {
			throw new IllegalArgumentException("xcode class must match rcode class");
		}
	}

	/**
	 * Creates a new IMilterStatus with a custom error reply code.
	 *
	 * @param rcode The three-digit (RFC 821/2821) SMTP reply code. rcode cannot be null, and must be a valid 4XX or 5XX
	 * reply code.
	 * @param xcode The extended (RFC 1893/2034) reply code. If xcode is null, no extended code is used. Otherwise,
	 * xcode must conform to RFC 1893/2034.
	 * @param messageLines An array of single lines of text which will be used as the text part of the SMTP reply. If
	 * messageLines has zero lines, an empty message is used.
	 * @throws IllegalArgumentException if rcode or xcode is invalid
	 */
	public CustomMilterStatus(String rcode, String xcode, String[] messageLines)
			throws IllegalArgumentException {
		validateRcode(rcode);
		validateXcode(xcode, rcode);

		if (messageLines == null || messageLines.length == 0) {
			this.reply = rcode + " ";
			if (xcode != null) {
				this.reply += xcode;
			}
		}
		else {
			this.reply = "";
			for (int counter = 0; counter < messageLines.length; ++counter) {
				boolean isLastLine = (counter == (messageLines.length - 1));

				this.reply += (rcode + ((isLastLine) ? " " : "-"));
				if (xcode != null) {
					this.reply += xcode;
					this.reply += " ";
				}

				this.reply += messageLines[counter];

				this.reply += ((isLastLine) ? "" : "\r\n");
			}
		}
	}

	@Override
	public int getCode() {
		return MilterConstants.SMFIR_REPLYCODE;
	}

	@Override
	public ByteBuffer getMessage() {
		return ByteBuffer.wrap(reply.getBytes());
	}
}
