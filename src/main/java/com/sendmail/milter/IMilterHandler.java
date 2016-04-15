/*
 * Copyright (c) 2001-2004 Sendmail, Inc. All Rights Reserved
 */
package com.sendmail.milter;

import java.net.InetAddress;

import java.nio.ByteBuffer;
import java.util.Properties;

import java.util.Map;
import java.util.Set;

/**
 * The main handler interface for writing a Java-based milter (mail filter).
 */
public interface IMilterHandler {

	/**
	 * Called once at the start of each SMTP connection.
	 *
	 * @param hostname The host name of the message sender, as determined by a reverse lookup on the host address.
	 * @param hostaddr The host address, as determined by a <code>getpeername()</code> call on the SMTP socket.
	 * @param properties Any properties (macros) received from the MTA.
	 * @return <code>SMFIS_</code> return codes from {@link IMilterStatus}. <b>NOTE:</b> The MTA will currently ignore
	 * any custom values (values other than <code>SMFIS_</code> values). Specifically, values created with
	 * {@link CustomMilterStatus#CustomMilterStatus} will not be honored.
	 */
	public IMilterStatus connect(String hostname, InetAddress hostaddr, Properties properties);

	/**
	 * Handle the HELO/EHLO command. Called whenever the client sends a HELO/EHLO command. It may therefore be called
	 * between zero and three times.
	 *
	 * @param helohost Value passed to HELO/EHLO command, which should be the domain name of the sending host (but is,
	 * in practice, anything the sending host wants to send).
	 * @param properties Any properties (macros) received from the MTA.
	 * @return <code>SMFIS_</code> return codes from {@link IMilterStatus}.
	 */
	public IMilterStatus helo(String helohost, Properties properties);

	/**
	 * Handle the envelope FROM command. Called once at the beginning of each message, before <code>envrcpt</code>.
	 *
	 * @param argv An array of SMTP command arguments. <code>argv[0]</code> is guaranteed to be the sender address.
	 * Later arguments are the ESMTP arguments. While the default encoding should be presumed to be ASCII, the value
	 * decoding is left to the user. Often simply the default {@link String#String(byte[])} constructor is enough.
	 * @param properties Any properties (macros) received from the MTA.
	 * @return <code>SMFIS_</code> return codes from {@link IMilterStatus}.
	 */
	public IMilterStatus envfrom(byte[][] argv, Properties properties);

	/**
	 * Handle the envelope RCPT command. Called once per recipient, hence one or more times per message, immediately
	 * after {@link #envfrom}.
	 *
	 * @param argv An array of SMTP command arguments. <code>argv[0]</code> is guaranteed to be the recipient address.
	 * Later arguments are the ESMTP arguments. While the default encoding should be presumed to be ASCII, the value
	 * decoding is left to the user. Often simply the default {@link String#String(byte[])} constructor is enough.
	 * @param properties Any properties (macros) received from the MTA.
	 * @return <code>SMFIS_</code> return codes from {@link IMilterStatus}.
	 */
	public IMilterStatus envrcpt(byte[][] argv, Properties properties);

	/**
	 * Handle a message header. Called zero or more times between {@link #envrcpt} and {@link #eoh}, once per message
	 * header.
	 *
	 * @param name Header field name.
	 * @param value Header field value. The content of the header may include folded white space (i.e. multiple lines
	 * with following white space). The trailing line terminator (CR/LF) is removed.
	 * <p>
	 * While the default encoding should be presumed to be ASCII, the value decoding is left to the user. Often simply
	 * the default {@link String#String(byte[])} constructor is enough. {@link String#String(byte[])} constructor is
	 * enough.
	 * @return <code>SMFIS_</code> return codes from {@link IMilterStatus}.
	 */
	public IMilterStatus header(byte[] name, byte[] value);

	/**
	 * End of a headers. Called once after all calls to {@link #header} for a given message.
	 *
	 * @param eohActions Interface for effecting message changes.
	 * @param properties Any properties (macros) received from the MTA.
	 * @return <code>SMFIS_</code> return codes from {@link IMilterStatus}.
	 */
	public IMilterStatus eoh(IMilterActions eohActions, Properties properties);

	/**
	 * Handle the DATA command. Called between the {@link #eoh} and {@link #body} commands.
	 *
	 * @param properties Any properties (macros) received from the MTA.
	 * @return <code>SMFIS_</code> return codes from {@link IMilterStatus}.
	 */
	public IMilterStatus data(Properties properties);

	/**
	 * Handle a piece of a message's body. Called zero or more times between {@link #eoh} and {@link #eom}.
	 *
	 * @param bodyp This block of body data.
	 * @return <code>SMFIS_</code> return codes from {@link IMilterStatus}.
	 */
	public IMilterStatus body(ByteBuffer bodyp);

	/**
	 * End of a message. Called once after all calls to {@link #body} for a given message.
	 *
	 * @param eomActions Interface for effecting message changes.
	 * @param properties Any properties (macros) received from the MTA.
	 * @return <code>SMFIS_</code> return codes from {@link IMilterStatus}.
	 */
	public IMilterStatus eom(IMilterActions eomActions, Properties properties);

	/**
	 * Handle the current message being aborted. Called at any time during message processing (i.e. between some
	 * message-oriented routine and {@link #eom}).
	 *
	 * @return <code>SMFIS_</code> return codes from {@link IMilterStatus}.
	 */
	public IMilterStatus abort();

	/**
	 * The current connection is being closed. Always called at the end of each connection.
	 *
	 * @return <code>SMFIS_</code> return codes from {@link IMilterStatus}.
	 */
	public IMilterStatus close();

	/**
	 * Handle an unknown SMTP command. The SMTP command will always be rejected by the server, it is only possible to
	 * return a different error code.
	 *
	 * @param command the incoming command.
	 * @param properties Any properties (macros) received from the MTA.
	 * @return <code>SMFIS_</code> return codes from {@link IMilterStatus}.
	 */
	public IMilterStatus unknown(byte[] command, Properties properties);

	/**
	 * Get the Milter Action flags as to what this milter uses.
	 *
	 * @return a combination of values from the {@link MilterConstants#SMFIF_ADDHDRS},
	 * {@link MilterConstants#SMFIF_CHGBODY}, {@link MilterConstants#SMFIF_ADDRCPT},
	 * {@link MilterConstants#SMFIF_DELRCPT}, {@link MilterConstants#SMFIF_CHGHDRS},
	 * {@link MilterConstants#SMFIF_QUARANTINE}, {@link MilterConstants#SMFIF_CHGFROM},
	 * {@link MilterConstants#SMFIF_ADDRCPT_PAR}, {@link MilterConstants#SMFIF_SETSYMLIST} constants.
	 */
	public int getActionFlags();

	/**
	 * Get the list of calls the MTA should not await replies for.
	 *
	 * @return A combination of {@link MilterConstants#SMFIP_NOCONNECT}, {@link MilterConstants#SMFIP_NOHELO},
	 * {@link MilterConstants#SMFIP_NOMAIL}, {@link MilterConstants#SMFIP_NORCPT},
	 * {@link MilterConstants#SMFIP_NOBODY}, {@link MilterConstants#SMFIP_NOHDRS},
	 * {@link MilterConstants#SMFIP_NOEOH}, {@link MilterConstants#SMFIP_NOUNKNOWN},
	 * {@link MilterConstants#SMFIP_NODATA}, {@link MilterConstants#SMFIP_SKIP}, {@link MilterConstants#SMFIP_RCPT_REJ},
	 * {@link MilterConstants#SMFIP_NR_HDR}, {@link MilterConstants#SMFIP_NR_CONN},
	 * {@link MilterConstants#SMFIP_NR_HELO}, {@link MilterConstants#SMFIP_NR_MAIL},
	 * {@link MilterConstants#SMFIP_NR_RCPT}, {@link MilterConstants#SMFIP_NR_DATA},
	 * {@link MilterConstants#SMFIP_NR_UNKN}, {@link MilterConstants#SMFIP_NR_EOH},
	 * {@link MilterConstants#SMFIP_NR_BODY}, {@link MilterConstants#SMFIP_HDR_LEADSPC} or 0.
	 */
	public int getProtocolFlags();

	/**
	 * Prepares for protocol version negotiation. {@link #getActionFlags()}, {@link #getProtocolFlags()} and
	 * {@link #getMacros()} are subsequently called and verified.
	 * <p>
	 * If {@link #getActionFlags()} requires flags not supported by the MTA, the negotiation will fail and the
	 * connection will be terminated with a {@link IMilterStatus#SMFIS_TEMPFAIL} response.
	 * <p>
	 * If {@link #getProtocolFlags()} requires flags not supported by the MTA, other than <code>SMFIP_NR_</code> flags,
	 * the negotiation will fail and the connection will be terminated with a {@link IMilterStatus#SMFIS_TEMPFAIL}
	 * response.
	 * <p>
	 * If {@link #getProtocolFlags()} requires SMFIP_NR_ flags not supported by the MTA, the Milter server will emulate
	 * them by sending {@link IMilterStatus#SMFIS_CONTINUE} responses.
	 * <p>
	 * Normally your filter's flags should be constant, but here we give you a chance to turn some features off or do
	 * workarounds. Note that your implemented handler functions are always called if a packet arrives for them, even if
	 * you report that you don't use them. Sendmail accepts the results and complains in the logs that your filter is a
	 * liar.
	 *
	 * @param mtaVersion Milter Protocol version of the Mail Transport Agent.
	 * @param actionFlags Actions flags for actions supported by the MTA. Prefixed with SMFIF_ in
	 * {@link MilterConstants}.
	 * @param protocolFlags Protocol flags supported by the MTA. Prefixed with SMFIP_ in {@link MilterConstants}.
	 * @return the protocol version. Minimum acceptable is 2. Current maximum is 6.
	 */
	public int negotiateVersion(int mtaVersion, int actionFlags, int protocolFlags);

	/**
	 * Called when receiving a {@link MilterConstants#SMFIC_QUIT_NC} command from the MTA. A stateful Milter must reset
	 * itself to the same state it was in before the {@link #connect} call, or after the calls to
	 * {@link #negotiateVersion(int, int, int) }, {@link #getActionFlags()}, {@link #getProtocolFlags()} and
	 * {@link #getMacros()}, because it will be reused.
	 */
	public void reset();

	/**
	 * Get desired macros during negotiation.
	 * <p>
	 * The macros themselves are available scattered with no particular grouping or order, with little to no description
	 * in the Sendmail documentation and some of them, but with proper description in the Postfix Milter documents.
	 *
	 * @see <a href="http://www.postfix.org/MILTER_README.html">Postfix Milter documentation</a>
	 * @see <a href="http://www.sendmail.com/sm/open_source/docs/configuration_readme/">Sendmail documentation</a>
	 * @return Map of Strings for each phase key defined from {@link MilterConstants.Macros}.
	 */
	public Map<Integer, Set<String>> getMacros();

}
