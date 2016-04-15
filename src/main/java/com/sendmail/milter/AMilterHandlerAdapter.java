/*
 * Copyright (c) 2005 Sendmail, Inc. All Rights Reserved
 */
package com.sendmail.milter;

import java.net.InetAddress;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Map;

import java.util.Properties;
import java.util.Set;

/**
 * An adapter to implement methods in {@link IMilterHandler} interface so subclasses may override only those methods
 * they desire.
 */
public abstract class AMilterHandlerAdapter implements IMilterHandler {

	protected final IMilterStatus DEFAULT_CONTINUE = IMilterStatus.SMFIS_CONTINUE;
	protected final IMilterStatus DEFAULT_ACCEPT = IMilterStatus.SMFIS_ACCEPT;
	protected final IMilterStatus DEFAULT_DISCARD = IMilterStatus.SMFIS_DISCARD;
	protected final IMilterStatus DEFAULT_REJECT = IMilterStatus.SMFIS_REJECT;
	protected final IMilterStatus DEFAULT_TEMPFAIL = IMilterStatus.SMFIS_TEMPFAIL;

	@Override
	public IMilterStatus connect(String hostname, InetAddress hostaddr, Properties properties) {
		return DEFAULT_CONTINUE;
	}

	@Override
	public IMilterStatus helo(String helohost, Properties properties) {
		return DEFAULT_CONTINUE;
	}

	@Override
	public IMilterStatus envfrom(byte[][] argv, Properties properties) {
		return DEFAULT_CONTINUE;
	}

	@Override
	public IMilterStatus envrcpt(byte[][] argv, Properties properties) {
		return DEFAULT_CONTINUE;
	}

	@Override
	public IMilterStatus header(byte[] name, byte[] value) {
		return DEFAULT_CONTINUE;
	}

	@Override
	public IMilterStatus body(ByteBuffer bodyp) {
		return DEFAULT_CONTINUE;
	}

	@Override
	public IMilterStatus eoh(IMilterActions eomActions, Properties properties) {
		return DEFAULT_CONTINUE;
	}

	@Override
	public IMilterStatus eom(IMilterActions eomActions, Properties properties) {
		return DEFAULT_CONTINUE;
	}

	@Override
	public IMilterStatus unknown(byte[] command, Properties properties) {
		return DEFAULT_CONTINUE;
	}

	@Override
	public IMilterStatus data(Properties properties) {
		return DEFAULT_CONTINUE;
	}

	@Override
	public IMilterStatus abort() {
		return DEFAULT_CONTINUE;
	}

	@Override
	public IMilterStatus close() {
		return DEFAULT_CONTINUE;
	}

	@Override
	public int getActionFlags() {
		// By default, do not modify anything
		return 0;
	}

	@Override
	public int getProtocolFlags() {
		// By default respond to all messages
		return 0;
	}

	@Override
	public int negotiateVersion(int mtaVersion, int actionFlags, int protocolFlags) {
		return 6; // Current protocol version
	}

	@Override
	public void reset() {
		// Do nothing.
	}

	@Override
	@SuppressWarnings({"unchecked"})
	public Map<Integer, Set<String>> getMacros() {
		return Collections.EMPTY_MAP;
	}
}
