/*
 * Copyright (c) 2001-2004 Sendmail, Inc. All Rights Reserved
 */
package com.sendmail.milter;

import java.nio.ByteBuffer;

/**
 * Milter status class for simple SMFIR_ status codes.
 */
public final class SimpleMilterStatus implements IMilterStatus {

	private final int status;
	private final ByteBuffer message;

	public SimpleMilterStatus(int status) {
		this(status, null);
	}

	public SimpleMilterStatus(int status, byte[] message) {
		this.status = status;
		this.message = message == null ? null : ByteBuffer.wrap(message);
	}

	@Override
	public int getCode() {
		return status;
	}

	@Override
	public ByteBuffer getMessage() {
		return message;
	}
}
