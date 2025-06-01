package com.timmax.realestate.web.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * <p>
 * Handling Hibernate lazy-loading
 *
 * @link <a href="https://github.com/FasterXML/jackson">jackson</a>
 * @link <a href="https://github.com/FasterXML/jackson-datatype-hibernate">jackson-datatype-hibernate</a>
 * @link <a href="https://github.com/FasterXML/jackson-docs/wiki/JacksonHowToCustomSerializers">JacksonHowToCustomSerializers</a>
 */

public class JacksonObjectMapper extends ObjectMapper {
    private static final ObjectMapper MAPPER = new JacksonObjectMapper();

    private JacksonObjectMapper() {
        // Регистрируем конструктор Hibernate5Module, который не делает сериализацию ленивых коллекций
        registerModule(new Hibernate5Module());

        //  Теперь даты будут записываться в JSON в виде TimeStamp
        registerModule(new JavaTimeModule());
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        /*  Эти настройки - альтернатива
        @JsonAutoDetect(
            fieldVisibility = ANY,      // jackson видит все поля
            getterVisibility = NONE,    // ... но не видит геттеров
            isGetterVisibility = NONE,  // ... не видит is-геттеров (т.е. начинающихся с is... и возвращающих boolean) (и тогда значение метода isNew не будет сериализоваться)
            setterVisibility = NONE     // ... не видит сеттеров
        )
        */
        //  Сначала запрещаем все аксессоры
        setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        //  Разрешаем акссесоры ч/з поля
        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        //  Не сериализовывать null поля
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}
