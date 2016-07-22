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

import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.rap.json.JsonObject;
import org.eclipse.rap.json.JsonValue;

class RichTextConfigurationToJsonUtil {


  JsonObject getConfigurationAsJson( final RichTextEditorConfiguration editorConfiguration ){
    JsonObject jsonObject = new JsonObject();

    Map<String, Object> allOptions = editorConfiguration.getAllOptions();
    for( Entry<String, Object> entry : allOptions.entrySet() ) {
      String optionName = entry.getKey();
      Object optionValue = entry.getValue();
      JsonValue jsonValue = createJsonValue( optionValue );
      jsonObject.add( optionName, jsonValue );
    }

    return jsonObject;
  }



  private static JsonValue createJsonValue( final Object value ){
    if ( value instanceof Boolean ){
      Boolean bool = (Boolean)value;
      return JsonValue.valueOf( bool.booleanValue() );
    }

    if ( value instanceof String ){
      String str = (String)value;
      return processString( str );
    }

    if ( value instanceof Integer ){
      Integer num = (Integer)value;
      return JsonValue.valueOf( num.intValue() );
    }

    if ( value instanceof Long ){
      Long num = (Long)value;
      return JsonValue.valueOf( num.longValue() );
    }

    if ( value instanceof Float ){
      Float num = (Float)value;
      return JsonValue.valueOf( num.floatValue() );
    }

    if ( value instanceof Double ){
      Double num = (Double)value;
      return JsonValue.valueOf( num.doubleValue() );
    }
    throw new IllegalArgumentException( "Only a RichTextEditorConfiguration with Boolean, String, Integer, Long, Float and Double"
        + " values is currently supported" );
  }



  private static JsonValue processString( final String str ) {
    if (str == null || str.length() < 1 ){
      return JsonValue.valueOf( "" );
    }

    if ( str.charAt( 0 )=='[' || str.charAt( 0 )=='{' ) {
      return JsonValue.readFrom(  str );
    }
    return JsonValue.valueOf( str );
  }
}
