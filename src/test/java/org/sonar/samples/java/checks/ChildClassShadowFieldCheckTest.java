package org.sonar.samples.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

public class ChildClassShadowFieldCheckTest {

    @Test
    void test() {
        CheckVerifier.newVerifier().onFile("src/test/files/ChildClassShadowFieldCheck.java")
                .withCheck(new ChildClassShadowFieldCheck()).verifyIssues();
    }
}
