package org.sonar.samples.java.checks;

import org.junit.jupiter.api.Test;
import org.sonar.java.checks.verifier.CheckVerifier;

public class MyFirstCustomCheckTest {

    @Test
    void test() {
        CheckVerifier.newVerifier().onFile("src/test/files/MyFirstCustomCheck.java")
                .withCheck(new MyFirstCustomCheck()).verifyIssues();
    }

}
