package com.timmax.realestate.web.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

/**
 * <p>
 * Handling Hibernate lazy-loading
 *
 * @link <a href="https://github.com/FasterXML/jackson">jackson</a>
 * @link <a href="https://github.com/FasterXML/jackson-datatype-hibernate">jackson-datatype-hibernate</a>
 * @link <a href="https://github.com/FasterXML/jackson-docs/wiki/JacksonHowToCustomSerializers">JacksonHowToCustomSerializers</a>
 */

public class JacksonObjectMapper extends ObjectMapper {
    private JacksonObjectMapper() {
        // Регистрируем конструктор Hibernate5Module, который не делает сериализацию ленивых коллекций
        registerModule(new Hibernate5Module());

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
        //  Не сериализовать null поля
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
