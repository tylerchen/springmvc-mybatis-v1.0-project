/*******************************************************************************
 * Copyright (c) 2011-12-7 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package org.iff.infra.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * A socket util.
 * <pre>
 * Usage:
 * 1) test("localhost", 80): if return true, the port is accessable.
 * b) getContent(new FileInputStream(file), false): return the text read from input stream with utf-8 encoding, 
 * and close input stream after reading.
 * c) getByte(new FileInputStream(file), false): return the bytes read from input stream, 
 * and close input stream after reading.
 * d) closeWithoutError(Closeable close): close object implements Closeable without throw Exception event pass the nullable value.
 * </pre>
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> 
 * @since 2011-12-7
 */
public class SocketHelper {

	/**
	 * test the ip:port is connect-able
	 * @param ip
	 * @param port
	 * @return
	 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> 
	 * @since 2015-2-6
	 */
	public static boolean test(String ip, int port) {
		Socket client = null;
		try {
			client = new Socket();
			client.connect(new InetSocketAddress(ip, port), 1000);
			return true;
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			closeWithoutError(client);
		}
		return false;
	}

	/**
	 * get the input stream content, default charset UTF-8.
	 * @param is
	 * @param notClose
	 * @return
	 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> 
	 * @since 2015-2-6
	 */
	public static String getContent(InputStream is, boolean notClose) {
		StringBuilder sb = new StringBuilder(102400);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			char[] cs = new char[102400];
			int len = -1;
			while ((len = br.read(cs)) != -1) {
				sb.append(cs, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (!notClose) {
					is.close();
				}
			} catch (Exception e) {
			}
		}
		return sb.toString();
	}

	/**
	 * get the input stream byte content.
	 * @param is
	 * @param notClose
	 * @return
	 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> 
	 * @since 2015-2-6
	 */
	public static byte[] getByte(InputStream is, boolean notClose) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(102400);
		try {
			byte[] bs = new byte[102400];
			int len = -1;
			while ((len = is.read(bs)) != -1) {
				baos.write(bs, 0, len);
			}
		} catch (Exception e) {
		} finally {
			try {
				if (!notClose) {
					is.close();
				}
			} catch (Exception e) {
			}
		}
		return baos.toByteArray();
	}

	/**
	 * close the Closeable object, without any exception.
	 * @param close
	 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> 
	 * @since Aug 18, 2015
	 */
	public static void closeWithoutError(Object close) {
		try {
			if (close == null) {
				return;
			}
			if (close instanceof Closeable) {
				((Closeable) close).close();
			}
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		System.out.println(test("10.103.117.8", 80));
	}
}
