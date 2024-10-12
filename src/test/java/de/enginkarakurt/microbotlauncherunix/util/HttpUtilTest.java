package de.enginkarakurt.microbotlauncherunix.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.http.HttpResponse;
import java.util.stream.Stream;

class HttpUtilTest {

    private static Stream<Arguments> urlData() {
        return Stream.of(
                Arguments.of("https://example.org", 200),
                Arguments.of("https://example.org/404", 404)
                );
    }

    @ParameterizedTest
    @MethodSource("urlData")
    void testSendRequestAndReceiveResponse(String url, int expectedStatusCode) {
        // Act
        HttpResponse<String> response = HttpUtil.sendRequestAndReceiveResponse(url);

        // Assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedStatusCode, response.statusCode());
        Assertions.assertEquals(url, response.uri().toString());
    }
}
