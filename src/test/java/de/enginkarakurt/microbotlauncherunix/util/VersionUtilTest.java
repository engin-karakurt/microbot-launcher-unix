package de.enginkarakurt.microbotlauncherunix.util;


import org.apache.maven.artifact.versioning.ComparableVersion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class VersionUtilTest {

    private static Stream<Arguments> compareVersionsData() {
        return Stream.of(
                Arguments.of(new ComparableVersion("1.0.0"), new ComparableVersion("1.0.1"), -1),
                Arguments.of(new ComparableVersion("1.0.1"), new ComparableVersion("1.0.0"), 1),
                Arguments.of(new ComparableVersion("1.0.0"), new ComparableVersion("1.0.0"), 0)
        );
    }

    @ParameterizedTest
    @MethodSource("compareVersionsData")
    void testCompareVersions(ComparableVersion version1, ComparableVersion version2, int expected) {
        Assertions.assertEquals(VersionUtil.isVersionNewer(version1, version2), expected);
    }
}
