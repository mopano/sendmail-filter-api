/*
 * Copyright (c) 2001-2004 Sendmail, Inc. All Rights Reserved
 */
package com.sendmail.milter;

import java.nio.ByteBuffer;

/**
 * Status class for methods in {@link IMilterHandler}.
 */
public interface IMilterStatus {

	/**
	 * @return The response code. Must be one of MilterConstants#SMFIR_CONTINUE, MilterConstants#SMFIR_REJECT,
	 * MilterConstants#SMFIR_DISCARD, MilterConstants#SMFIR_ACCEPT, MilterConstants#SMFIR_TEMPFAIL,
	 * MilterConstants#SMFIR_REPLYCODE, unless you really, really know what you're doing.
	 */
	public int getCode();

	/**
	 * @return null or a ByteBuffer containing an error/warning message.
	 */
	public ByteBuffer getMessage();

	/**
	 * Continue processing the current connection, message, or recipient.
	 */
	public static final IMilterStatus SMFIS_CONTINUE = new SimpleMilterStatus(MilterConstants.SMFIR_CONTINUE);

	/**
	 * Rejection. For a connection-oriented routine, reject this connection; call {@link IMilterHandler#close close}.
	 * For a message-oriented routine (except {@link IMilterHandler#eom eom} or {@link IMilterHandler#abort abort}),
	 * reject this message. For a recipient-oriented routine, reject the current recipient (but continue processing the
	 * current message).
	 */
	public static final IMilterStatus SMFIS_REJECT = new SimpleMilterStatus(MilterConstants.SMFIR_REJECT);

	/**
	 * Message discard. For a message- or recipient-oriented routine, accept this message, but silently discard it.
	 * {@link #SMFIS_DISCARD SMFIS_DISCARD} should not be returned by a connection-oriented routine.
	 */
	public static final IMilterStatus SMFIS_DISCARD = new SimpleMilterStatus(MilterConstants.SMFIR_DISCARD);

	/**
	 * Acceptance. For a connection-oriented routine, accept this connection without further filter processing; call
	 * {@link IMilterHandler#close close}. For a message- or recipient-oriented routine, accept this message without
	 * further filtering.
	 */
	public static final IMilterStatus SMFIS_ACCEPT = new SimpleMilterStatus(MilterConstants.SMFIR_ACCEPT);

	/**
	 * Skip further body processing. MTA should not send any more BODY chunks. Skip directly to End of Message
	 * processing.
	 */
	public static final IMilterStatus SMFIS_SKIP = new SimpleMilterStatus(MilterConstants.SMFIP_SKIP);

	/**
	 * Return a temporary failure, i.e., the corresponding SMTP command will return an appropriate 4xx status code. For
	 * a message-oriented routine (except {@link IMilterHandler#envfrom envfrom}), fail for this message. For a
	 * connection-oriented routine, fail for this connection; call {@link IMilterHandler#close close}. For a
	 * recipient-oriented routine, only fail for the current recipient; continue message processing.
	 */
	public static final IMilterStatus SMFIS_TEMPFAIL = new SimpleMilterStatus(MilterConstants.SMFIR_TEMPFAIL);

	/**
	 * Do not send a reply back to the MTA. If you set the SMFIP_NR_* protocol action for a callback, that callback must
	 * always reply with SMFIS_NOREPLY. If the MTA does not support NOREPLY, send CONTINUE for backwards compatibility.
	 */
	public static final IMilterStatus SMFIS_NOREPLY = new SimpleMilterStatus(MilterConstants.SMFIR_CONTINUE);
}
