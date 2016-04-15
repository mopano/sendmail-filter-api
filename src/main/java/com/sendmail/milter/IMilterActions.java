/*
 * Copyright (c) 2001-2004 Sendmail, Inc. All Rights Reserved
 */
package com.sendmail.milter;

import java.io.IOException;

import java.nio.ByteBuffer;

/**
 * Contains the actions available during {@link IMilterHandler#eom eom} and {@link IMilterHandler#eoh eoh} processing.
 */
public interface IMilterActions {

	/**
	 * Add a header to the current message, replacing if exists.
	 *
	 * @param name The header name.
	 * @param value The header value.
	 *
	 * @throws IOException if a connection error occurs.
	 */
	public void addheader(String name, String value) throws IOException;

	/**
	 * Change or delete a message header.
	 *
	 * @param name the header name.
	 * @param index header index value (1-based). A index value of 1 will modify the first occurrence of a header named
	 * {@code name}. If {@code index} is greater than the number of times {@code name} appears, a new copy of
	 * {@code name} will be added.
	 * @param value the new value of the given header. value == <code>null</code> indicates that the header should be
	 * deleted.
	 *
	 * @throws IOException if a connection error occurs.
	 */
	public void chgheader(String name, int index, String value) throws IOException;

	/**
	 * Insert a header to the current message, without replacing.
	 *
	 * @param name The header name.
	 * @param value The header value.
	 *
	 * @throws IOException if a connection error occurs.
	 */
	public void insheader(String name, String value) throws IOException;

	/**
	 * Add a recipient for the current message.
	 *
	 * @param rcpt the new recipient's address.
	 *
	 * @throws IOException if a connection error occurs.
	 */
	public void addrcpt(String rcpt) throws IOException;

	/**
	 * Removes the named recipient from the current message's envelope.
	 *
	 * @param rcpt the recipient address to be removed.
	 *
	 * @throws IOException if a connection error occurs.
	 */
	public void delrcpt(String rcpt) throws IOException;

	/**
	 * Replaces the body of the current message. If called more than once, subsequent calls result in data being
	 * appended to the new body.
	 *
	 * @param bodyp a buffer containing the new body data. Body data should be in CR/LF form.
	 *
	 * @throws IOException if a connection error occurs.
	 */
	public void replacebody(ByteBuffer bodyp) throws IOException;

	/**
	 * Notify the MTA that an operation is still in progress.
	 *
	 * @throws IOException if a connection error occurs.
	 */
	public void progress() throws IOException;

	/**
	 * Set the resulting EOM status. Note: Calling the method essentially invalidates this object. The result of any
	 * subsequent calls to methods on this object is undefined.
	 *
	 * @param status send the following status code (optional).
	 * @throws IOException if a connection error occurs.
	 */
	public void finish(IMilterStatus status) throws IOException;

}
