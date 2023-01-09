package org.sonar.samples.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

public class MissingOverrideAnnotationRuleTest {

    @Test
    void test() {
        CheckVerifier.newVerifier().onFile("src/test/files/MissingOverrideAnnotationCheck.java")
                .withCheck(new MissingOverrideAnnotationRule()).verifyIssues();
    }
}
