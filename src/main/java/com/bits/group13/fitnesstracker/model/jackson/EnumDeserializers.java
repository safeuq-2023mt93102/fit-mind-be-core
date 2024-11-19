// Copyright (c) 2022 - Safeuq Mohamed, mohamedsafeuq@outlook.com
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.bits.group13.fitnesstracker.model.jackson;

import com.bits.group13.fitnesstracker.model.EnumSerializable;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import java.io.IOException;
import java.lang.reflect.Modifier;
import lombok.SneakyThrows;

public class EnumDeserializers extends Deserializers.Base {

  @Override
  @SuppressWarnings("unchecked")
  public JsonDeserializer<?> findBeanDeserializer(
      JavaType type, DeserializationConfig config, BeanDescription beanDesc)
      throws JsonMappingException {
    Class<?> rawClass = type.getRawClass();
    if (rawClass != EnumSerializable.class
        && EnumSerializable.class.isAssignableFrom(rawClass)
        && (rawClass.isInterface() || Modifier.isAbstract(rawClass.getModifiers()))) {
      return new DeserializerImpl((Class<? extends EnumSerializable>) rawClass);
    }
    return super.findBeanDeserializer(type, config, beanDesc);
  }

  private static class DeserializerImpl extends StdDeserializer<EnumSerializable> {
    private DeserializerImpl(Class<? extends EnumSerializable> messageClass) {
      super(messageClass);
    }

    @Override
    @SneakyThrows
    @SuppressWarnings({"unchecked", "rawtypes"})
    public EnumSerializable deserialize(JsonParser parser, DeserializationContext context)
        throws IOException {
      Class<?> enumReturnType = handledType().getDeclaredMethod("getType").getReturnType();
      if (!enumReturnType.isEnum()) {
        throw new JsonParseException(
            "Expected " + handledType().getSimpleName() + ".getType to have an Enum return type");
      }
      Class<Enum> enumClass = (Class<Enum>) enumReturnType;
      ObjectNode treeNode = parser.getCodec().readTree(parser);
      ValueNode typeValue = (ValueNode) treeNode.remove("type");
      if (typeValue == null) {
        throw new JsonParseException("Expected 'type' to have a non-null value");
      }
      EnumSerializable.Type<?> type =
          (EnumSerializable.Type<?>) Enum.valueOf(enumClass, typeValue.asText());
      JsonParser jsonParser = treeNode.traverse();
      if (jsonParser.currentToken() == null) {
        jsonParser.nextToken();
      }
      return context.readValue(jsonParser, type.getTypeClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends EnumSerializable> handledType() {
      return (Class<? extends EnumSerializable>) super.handledType();
    }
  }
}
