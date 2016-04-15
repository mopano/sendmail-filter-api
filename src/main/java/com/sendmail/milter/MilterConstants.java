/*
 * Copyright (c) 2001-2004 Sendmail, Inc. All Rights Reserved
 */
package com.sendmail.milter;

import java.nio.ByteBuffer;

/**
 * Class containing common constants.
 * <p>
 * Commands are prefixed with SMFIC. Many correspond directly to an SMTP command.
 * <p>
 * Reply (actions) codes are prefixed with SMFIR.
 * <p>
 * SMFIP prefixes define flags for what the MTA should and should not send to the filter.
 */
public final class MilterConstants {

	/**
	 * Connection information.
	 */
	public static final int SMFIC_CONNECT = 'C';

	/**
	 * Define macro.
	 */
	public static final int SMFIC_MACRO = 'D';

	/**
	 * HELO/EHLO.
	 */
	public static final int SMFIC_HELO = 'H';

	/**
	 * MAIL FROM.
	 */
	public static final int SMFIC_MAIL = 'M';

	/**
	 * RCPT TO.
	 */
	public static final int SMFIC_RCPT = 'R';

	/**
	 * Final body chunk (End).
	 */
	public static final int SMFIC_BODYEOB = 'E';

	/**
	 * Header.
	 */
	public static final int SMFIC_HEADER = 'L';

	/**
	 * End of headers? This one is guesswork.
	 */
	public static final int SMFIC_EOH = 'N';

	/**
	 * Option negotiation.
	 */
	public static final int SMFIC_OPTNEG = 'O';

	/**
	 * QUIT.
	 */
	public static final int SMFIC_QUIT = 'Q';

	/**
	 * QUIT but new connection follows. Reuse object?
	 */
	public static final int SMFIC_QUIT_NC = 'K';

	/**
	 * Body chunk.
	 */
	public static final int SMFIC_BODY = 'B';

	/**
	 * DATA.
	 */
	public static final int SMFIC_DATA = 'T';

	/**
	 * Abort.
	 */
	public static final int SMFIC_ABORT = 'A';

	/**
	 * Any unknown command.
	 */
	public static final int SMFIC_UNKNOWN = 'U';

	/**
	 * Accept sender.
	 */
	public static final int SMFIR_ACCEPT = 'a';

	/**
	 * Add or replace header.
	 */
	public static final int SMFIR_ADDHEADER = 'h';

	/**
	 * Add header without replacing.
	 */
	public static final int SMFIR_INSHEADER = 'i';

	/**
	 * Set list of symbols (macros).
	 */
	public static final int SMFIR_SETSYMLIST = 'l';

	/**
	 * Add recipient.
	 */
	public static final int SMFIR_ADDRCPT = '+';

	/**
	 * Add recipient (including ESMTP args).
	 */
	public static final int SMFIR_ADDRCPT_PAR = '2';

	/**
	 * Replace header. Error if it doesn't exist.
	 */
	public static final int SMFIR_CHGHEADER = 'm';

	/**
	 * Change "MAIL FROM" value.
	 */
	public static final int SMFIR_CHGFROM = 'e';

	/**
	 * Cause a connection failure.
	 */
	public static final int SMFIR_CONN_FAIL = 'f';

	/**
	 * Continue with default operation. No changes.
	 */
	public static final int SMFIR_CONTINUE = 'c';

	/**
	 * Remove recipient.
	 */
	public static final int SMFIR_DELRCPT = '-';

	/**
	 * Discard the message. Pretend to accept it.
	 */
	public static final int SMFIR_DISCARD = 'd';

	/**
	 * Operation still in progress. Sent only from EoM handler during long operations to keep the connection alive.
	 */
	public static final int SMFIR_PROGRESS = 'p';

	/**
	 * Quarantine the message. Sent only from EoM handler.
	 */
	public static final int SMFIR_QUARANTINE = 'q';

	/**
	 * Skip further BODY chunks. Continue to EoM.
	 */
	public static final int SMFIR_SKIP = 's';

	/**
	 * Reject the message.
	 */
	public static final int SMFIR_REJECT = 'r';

	/**
	 * Replace body (chunk).
	 */
	public static final int SMFIR_REPLBODY = 'b';

	public static final int SMFIR_REPLYCODE = 'y';

	/**
	 * Temporary error. (Try again later).
	 */
	public static final int SMFIR_TEMPFAIL = 't';

	public static final int SMFIA_INET = '4';
	public static final int SMFIA_INET6 = '6';

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should NOT send connect info.
	 */
	public static final int SMFIP_NOCONNECT = 0x00000001;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should NOT send HELO/EHLO info.
	 */
	public static final int SMFIP_NOHELO = 0x00000002;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should NOT send MAIL FROM command info.
	 */
	public static final int SMFIP_NOMAIL = 0x00000004;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should NOT send RCPT TO command info. Can be
	 * multiple per message.
	 */
	public static final int SMFIP_NORCPT = 0x00000008;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should NOT send body.
	 */
	public static final int SMFIP_NOBODY = 0x00000010;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should NOT send headers.
	 */
	public static final int SMFIP_NOHDRS = 0x00000020;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should NOT send End of Headers.
	 */
	public static final int SMFIP_NOEOH = 0x00000040;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate Negotiation option: No reply for headers.
	 */
	public static final int SMFIP_NR_HDR = 0x00000080;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should NOT send unknown commands.
	 */
	public static final int SMFIP_NOUNKNOWN = 0x00000100;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should NOT send DATA.
	 */
	public static final int SMFIP_NODATA = 0x00000200;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA understands the SKIP command, only sent in
	 * response to BODY chunks.
	 */
	public static final int SMFIP_SKIP = 0x00000400;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should also send rejected RCPTs.
	 */
	public static final int SMFIP_RCPT_REJ = 0x00000800;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should not expect a reply for CONNECT.
	 */
	public static final int SMFIP_NR_CONN = 0x00001000;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should not expect a reply for HELO.
	 */
	public static final int SMFIP_NR_HELO = 0x00002000;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should not expect a reply for MAIL.
	 */
	public static final int SMFIP_NR_MAIL = 0x00004000;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should not expect a reply for RCPT.
	 */
	public static final int SMFIP_NR_RCPT = 0x00008000;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should not expect a reply for DATA.
	 */
	public static final int SMFIP_NR_DATA = 0x00010000;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should not expect a reply for UNKNOWN command
	 * processing.
	 */
	public static final int SMFIP_NR_UNKN = 0x00020000;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should not expect a reply for End of Headers.
	 */
	public static final int SMFIP_NR_EOH = 0x00040000;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate MTA should not expect a reply for body chunk.
	 */
	public static final int SMFIP_NR_BODY = 0x00080000;

	/**
	 * Flag for {@link IMilterHandler#getProtocolFlags()} to indicate a milter shluld receive header field values with
	 * all leading spaces by requesting the SMFIP_HDR_LEADSPC protocol action. Also, if the flag is set then the MTA
	 * does not add a leading space to headers that are added, inserted, or replaced..
	 */
	public static final int SMFIP_HDR_LEADSPC = 0x00100000;

	/**
	 * Flags for {@link IMilterHandler#getActionFlags()} equivalent of {@link #SMFIF_ADDHDRS}, {@link #SMFIF_CHGBODY},
	 * {@link #SMFIF_ADDRCPT}, {@link #SMFIF_DELRCPT}.
	 */
	public static final int SMFI_V1_ACTS = 0x0000000F;

	/**
	 * Flags for {@link IMilterHandler#getActionFlags()} equivalent of {@link #SMFIF_ADDHDRS}, {@link #SMFIF_CHGBODY},
	 * {@link #SMFIF_ADDRCPT}, {@link #SMFIF_DELRCPT}, {@link #SMFIF_CHGHDRS}, {@link #SMFIF_QUARANTINE}.
	 */
	public static final int SMFI_V2_ACTS = 0x0000003F;

	/**
	 * Flags for {@link IMilterHandler#getActionFlags()} equivalent of {@link #SMFIF_ADDHDRS}, {@link #SMFIF_CHGBODY},
	 * {@link #SMFIF_ADDRCPT}, {@link #SMFIF_DELRCPT}, {@link #SMFIF_CHGHDRS}, {@link #SMFIF_QUARANTINE},
	 * {@link #SMFIF_CHGFROM}, {@link #SMFIF_ADDRCPT_PAR}, {@link #SMFIF_SETSYMLIST}.
	 * <p>
	 * The value of this constant intentionally differs from it's equivalent in the C libmilter, updated to support all
	 * the current actions.
	 */
	public static final int SMFI_CURRENT_ACTSIONS = 0x000001FF;

	/**
	 * Flags for {@link IMilterHandler#getProtocolFlags()} equivalent of {@link #SMFIP_NOCONNECT}, {@link #SMFIP_NOHELO},
	 * {@link #SMFIP_NOMAIL}, {@link #SMFIP_NORCPT}, {@link #SMFIP_NOBODY}, {@link #SMFIP_NOHDRS}.
	 */
	public static final int SMFI_V1_PROT = 0x0000003F;

	/**
	 * Flags for {@link IMilterHandler#getProtocolFlags()} equivalent of {@link #SMFIP_NOCONNECT}, {@link #SMFIP_NOHELO},
	 * {@link #SMFIP_NOMAIL}, {@link #SMFIP_NORCPT}, {@link #SMFIP_NOBODY}, {@link #SMFIP_NOHDRS}, {@link #SMFIP_NOEOH}.
	 */
	public static final int SMFI_V2_PROT = 0x0000007F;

	/**
	 * Flags for {@link IMilterHandler#getProtocolFlags()} equivalent of all <code>SMFIP_</code> flags.
	 */
	public static final int SMFI_CURRENT_PROTOCOL = 0x001FFFFF;

	/**
	 * Flag for {@link IMilterHandler#getActionFlags()} to indicate that no modifications will be made.
	 */
	public static final int SMFIF_NONE = 0x00000000;

	/**
	 * Flag for {@link IMilterHandler#getActionFlags()} to indicate that headers may be added.
	 */
	public static final int SMFIF_ADDHDRS = 0x00000001;

	/**
	 * Flag for {@link IMilterHandler#getActionFlags()} to indicate that the body may be changed. Synonym of
	 * {@link #SMFIF_MODBODY}.
	 */
	public static final int SMFIF_CHGBODY = 0x00000002;

	/**
	 * Flag for {@link IMilterHandler#getActionFlags()} to indicate that the body may be changed. Synonym of
	 * {@link #SMFIF_CHGBODY}.
	 */
	public static final int SMFIF_MODBODY = SMFIF_CHGBODY;

	/**
	 * Flag for {@link IMilterHandler#getActionFlags()} to indicate that recipients may be added.
	 */
	public static final int SMFIF_ADDRCPT = 0x00000004;

	/**
	 * Flag for {@link IMilterHandler#getActionFlags()} to indicate that recipients may be deleted.
	 */
	public static final int SMFIF_DELRCPT = 0x00000008;

	/**
	 * Flag for {@link IMilterHandler#getActionFlags()} to indicate that headers may be changed or deleted.
	 */
	public static final int SMFIF_CHGHDRS = 0x00000010;

	/**
	 * Flag for {@link IMilterHandler#getActionFlags()} to indicate that the envelope may be quarantined.
	 */
	public static final int SMFIF_QUARANTINE = 0x00000020;

	/**
	 * Flag for {@link IMilterHandler#getActionFlags()} to indicate that the filter may change the envelope sender.
	 */
	public static final int SMFIF_CHGFROM = 0x00000040;

	/**
	 * Flag for {@link IMilterHandler#getActionFlags()} to indicate that the filter may add recipients, including ESMTP
	 * parameter to the envelope.
	 */
	public static final int SMFIF_ADDRCPT_PAR = 0x00000080;

	/**
	 * Flag for {@link IMilterHandler#getActionFlags()} to indicate that the filter can send set of symbols (macros)
	 * that it wants.
	 */
	public static final int SMFIF_SETSYMLIST = 0x00000100;

	public static final ByteBuffer EMPTY_BUFFER = ByteBuffer.wrap(new byte[0]).asReadOnlyBuffer();

	public static final class Macros {

		/**
		 * Macro stage for incoming connection.
		 */
		public static final int SMFIM_CONNECT = 0;

		/**
		 * Macro stage for HELO/EHLO SMTP command.
		 */
		public static final int SMFIM_HELO = 1;

		/**
		 * Macro stage for MAIL FROM SMTP command.
		 */
		public static final int SMFIM_ENVFROM = 2;

		/**
		 * Macro stage for RCPT TO SMTP command.
		 */
		public static final int SMFIM_ENVRCPT = 3;

		/**
		 * Macro stage for DATA SMTP command.
		 */
		public static final int SMFIM_DATA = 4;

		/**
		 * Macro stage for End of Message (final dot).
		 */
		public static final int SMFIM_EOM = 5;

		/**
		 * Macro stage for End of Headers.
		 */
		public static final int SMFIM_EOH = 6;

	}

	private MilterConstants() {
		throw new IllegalStateException("You have no class!");
	}
}
