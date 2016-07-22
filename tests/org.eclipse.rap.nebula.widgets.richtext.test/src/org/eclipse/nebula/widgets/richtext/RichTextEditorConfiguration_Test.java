/*******************************************************************************
 * Copyright (c) 2016 arxes-tolina GmbH
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Wojtek Polcwiartek <wojciech.polcwiartek@tolina.de> - initial API and implementation
 ******************************************************************************/
package org.eclipse.nebula.widgets.richtext;

import static org.junit.Assert.assertEquals;

import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class RichTextEditorConfiguration_Test {

  private RichTextEditorConfiguration config;

  @Before
  public void setUp() {
    config = new RichTextEditorConfiguration();
  }

  @Test
  public void testToString_withDefaultConfigurations() {
    Map<String, Object> customConfiguration = config.getAllOptions();

    assertEquals( Locale.getDefault().getLanguage(), customConfiguration.get( RichTextEditorConfiguration.LANGUAGE ) );
    assertEquals( "en", customConfiguration.get( RichTextEditorConfiguration.DEFAULT_LANGUAGE ) );

  }

  @Test
  public void testToString_withCustomConfigurations() {

    config.setDefaultLanguage( Locale.GERMAN );
    config.setLanguage( Locale.FRANCE );
    config.setOption( "OTHER_KEY", "OTHER_VALUE" );

    Map<String, Object> customConfiguration = config.getAllOptions();

    assertEquals( "fr", customConfiguration.get( RichTextEditorConfiguration.LANGUAGE ) );
    assertEquals( "de", customConfiguration.get( RichTextEditorConfiguration.DEFAULT_LANGUAGE ) );
    assertEquals( "OTHER_VALUE", customConfiguration.get( "OTHER_KEY" ) );
  }


}
