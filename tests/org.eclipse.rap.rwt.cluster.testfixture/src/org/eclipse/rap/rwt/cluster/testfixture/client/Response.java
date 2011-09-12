/*******************************************************************************
 * Copyright (c) 2011 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package org.eclipse.rap.rwt.cluster.testfixture.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLConnection;


public class Response {
  private static final String HTML_PROLOGUE = "<!DOCTYPE HTML";
  private static final String JAVASCRIPT_PROLOGUE
    = "org.eclipse.rwt.protocol.Processor.processMessage";
  
  private final int responseCode;
  private final byte[] content;

  Response( HttpURLConnection connection ) throws IOException {
    this.responseCode = connection.getResponseCode();
    this.content = readResponseContent( connection );
  }
  
  public int getResponseCode() {
    return responseCode;
  }
  
  public byte[] getContent() {
    return content.clone();
  }
  
  public String getContentText() {
    String result;
    try {
      result = new String( content, "UTF-8" );
    } catch( UnsupportedEncodingException unexpected ) {
      throw new RuntimeException( unexpected );
    }
    return result;
  }

  public boolean isValidJavascript() {
    return responseCode == 200 && getContentText().startsWith( JAVASCRIPT_PROLOGUE );
  }

  public boolean isValidStartupPage() {
    return responseCode == 200 && getContentText().startsWith( HTML_PROLOGUE );
  }

  private static byte[] readResponseContent( URLConnection connection ) throws IOException {
    byte[] result;
    InputStream inputStream = connection.getInputStream();
    try {
      result = readResponseContent( inputStream );
    } finally {
      inputStream.close();
    }
    return result;
  }

  private static byte[] readResponseContent( InputStream inputStream ) throws IOException {
    ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    int read = inputStream.read();
    while( read != -1 ) {
      byteStream.write( read );
      read = inputStream.read();
    }
    return byteStream.toByteArray();
  }
}
