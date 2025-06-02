package com.timmax.realestate;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import com.timmax.realestate.web.json.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Factory for creating test matchers.
 * <p>
 * Comparing actual and expected objects via AssertJ
 */
public class MatcherFactory {
    public static <T> Matcher<T> usingIgnoringFieldsComparator(
            Class<T> clazz,
            String... fieldsToIgnore) {
        return new Matcher<>(clazz, fieldsToIgnore);
    }

    public static class Matcher<T> {
        private final Class<T> clazz;
        private final String[] fieldsToIgnore;

        private Matcher(Class<T> clazz, String... fieldsToIgnore) {
            this.clazz = clazz;
            this.fieldsToIgnore = fieldsToIgnore;
        }

        public void assertMatch(T actual, T expected) {
            assertThat(actual).usingRecursiveComparison().ignoringFields(fieldsToIgnore).isEqualTo(expected);
        }

        @SafeVarargs
        public final void assertMatch(Iterable<T> actual, T... expected) {
            assertMatch(actual, List.of(expected));
        }

        public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
            assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(expected);
        }

        public ResultMatcher contentJson(T expected) {
            return result -> assertMatch(JsonUtil.readValue(getContent(result), clazz), expected);
        }

        private static String getContent(MvcResult result) throws UnsupportedEncodingException {
            return result.getResponse().getContentAsString();
        }
    }
}
