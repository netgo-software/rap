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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.eclipse.nebula.widgets.richtext.toolbar.ToolbarConfiguration;
import org.eclipse.rap.json.JsonArray;
import org.eclipse.rap.json.JsonObject;
import org.eclipse.rap.json.JsonValue;
import org.junit.Test;


public class RichTextConfigurationToJsonUtil_Test {

  private final RichTextConfigurationToJsonUtil util = new RichTextConfigurationToJsonUtil();

  @Test
  public void testDefaultConfiguration() {

    RichTextEditorConfiguration editorConfiguration = new RichTextEditorConfiguration();
    JsonObject jsonObject = util.getConfigurationAsJson( editorConfiguration );

    assertDefaultConfiguiration( jsonObject );
  }


  @Test
  public void testCustomConfiguration() {

    RichTextEditorConfiguration editorConfiguration = new RichTextEditorConfiguration();
    editorConfiguration.setDefaultLanguage( Locale.ITALIAN );
    editorConfiguration.setLanguage( Locale.JAPANESE );
    editorConfiguration.setRemoveStyles( false );
    editorConfiguration.setRemoveFormat( false );
    editorConfiguration.setRemovePasteFromWord( false );
    editorConfiguration.setRemovePasteText( false );
    editorConfiguration.setToolbarCollapsible( true );
    editorConfiguration.setToolbarInitialExpanded( false );
    JsonObject jsonObject = util.getConfigurationAsJson( editorConfiguration );

    assertEquals( "it", jsonObject.get( "defaultLanguage" ).asString());
    assertEquals( "ja", jsonObject.get( "language" ).asString());

    assertFalse( jsonObject.get( "toolbarStartupExpanded" ).asBoolean() );
    assertTrue( jsonObject.get( "toolbarCanCollapse" ).asBoolean() );

    JsonValue toolbarGroupsValue = jsonObject.get( "toolbarGroups" );
    JsonArray toolbarGroupsArray = toolbarGroupsValue.asArray();
    assertEquals( 5, toolbarGroupsArray.size() );


    assertEquals( "", jsonObject.get( "removeButtons" ).asString());
  }

  @Test
  public void testCustomConfigurationWithNumber() {

    RichTextEditorConfiguration editorConfiguration = new RichTextEditorConfiguration();
    editorConfiguration.setOption( "integer", Integer.valueOf( 10 ) );
    editorConfiguration.setOption( "long", Long.valueOf( 11 ) );
    editorConfiguration.setOption( "float", Float.valueOf( 12 ) );
    editorConfiguration.setOption( "double", Double.valueOf( 13 ) );
    JsonObject jsonObject = util.getConfigurationAsJson( editorConfiguration );

    assertEquals( 10, jsonObject.get( "integer" ).asInt());
    assertEquals( 11, jsonObject.get( "long" ).asLong());
    assertEquals( 12, jsonObject.get( "float" ).asFloat(), 0.0001 );
    assertEquals( 13, jsonObject.get( "double" ).asDouble(), 0.0001);

  }



  @Test
  public void testDefaultConfiguration_FromToolbarConfiguration() {

    RichTextEditorConfiguration editorConfiguration = new RichTextEditorConfiguration(new ToolbarConfiguration());
    JsonObject jsonObject = util.getConfigurationAsJson( editorConfiguration );

    assertDefaultConfiguiration( jsonObject );
  }


  private static void assertDefaultConfiguiration( JsonObject jsonObject ) {

    assertEquals( "en", jsonObject.get( "defaultLanguage" ).asString());
    assertEquals( Locale.getDefault().getLanguage(), jsonObject.get( "language" ).asString());


    assertTrue( jsonObject.get( "toolbarStartupExpanded" ).asBoolean() );
    assertFalse( jsonObject.get( "toolbarCanCollapse" ).asBoolean() );

    JsonValue toolbarGroupsValue = jsonObject.get( "toolbarGroups" );
    JsonArray toolbarGroupsArray = toolbarGroupsValue.asArray();
    assertEquals( 5, toolbarGroupsArray.size() );

    JsonObject basicStylesGroup = toolbarGroupsArray.get( 0 ).asObject();
    assertEquals( "basicstyles", basicStylesGroup.get( "name" ).asString() );
    JsonArray basicStylesGroups = basicStylesGroup.get( "groups" ).asArray();
    assertEquals( 2, basicStylesGroups.size() );

    JsonObject paragraphGroup = toolbarGroupsArray.get( 1 ).asObject();
    assertEquals( "paragraph", paragraphGroup.get( "name" ).asString() );
    JsonArray paragraphGroups = paragraphGroup.get( "groups" ).asArray();
    assertEquals( 3, paragraphGroups.size() );

    assertEquals( "/", toolbarGroupsArray.get( 2 ).asString() );

    JsonObject styles = toolbarGroupsArray.get( 3 ).asObject();
    assertEquals( "styles", styles.get( "name" ).asString() );

    JsonObject colors = toolbarGroupsArray.get( 4 ).asObject();
    assertEquals( "colors", colors.get( "name" ).asString() );

    assertEquals( "PasteText,PasteFromWord,Styles,Format", jsonObject.get( "removeButtons" ).asString());
  }

}
