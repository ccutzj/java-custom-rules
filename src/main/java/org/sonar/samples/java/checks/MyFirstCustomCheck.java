package org.sonar.samples.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.*;

import java.util.Collections;
import java.util.List;

/**
 * 处理规则校验
 */
@Rule(key = "MyFirstCustomRule")
public class MyFirstCustomCheck extends IssuableSubscriptionVisitor {

    /**
     * 对哪些方法进行规则校验
     * @return
     */
    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.METHOD);
    }

    /**
     * 定义了分析文件时将使用的策略
     * @param tree
     */
    @Override
    public void visitNode(Tree tree) {
        MethodTree method = (MethodTree) tree;
        if (method.parameters().size() == 1){
            Symbol.MethodSymbol symbol = method.symbol();
            Type fistParameterType = symbol.parameterTypes().get(0);
            Type returnType = symbol.returnType().type();
            if (returnType.is(fistParameterType.fullyQualifiedName())){
                reportIssue(method.simpleName(), "Never do that!");
            }
        }
    }

}
