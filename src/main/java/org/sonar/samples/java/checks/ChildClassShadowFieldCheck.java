package org.sonar.samples.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.*;

import javax.annotation.CheckForNull;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author zhaojian 2023-1-6
 * 避免在子父类的成员变量中采用完全相同的命名
 */
@Rule(key = "ChildClassShadowFieldCheck")
public class ChildClassShadowFieldCheck extends IssuableSubscriptionVisitor {

    private static final Set<String> IGNORED_FIELDS = Collections.singleton("serialVersionUID");

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Collections.singletonList(Tree.Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        TypeTree superClass = ((ClassTree) tree).superClass();
        if (superClass != null) {
            Symbol.TypeSymbol superclassSymbol = superClass.symbolType().symbol();
            ((ClassTree) tree).members().stream()
                    .filter(m -> m.is(Tree.Kind.VARIABLE))
                    .map(VariableTree.class::cast)
                    .map(VariableTree::simpleName)
                    .forEach(fieldSimpleName -> {
                        if (!IGNORED_FIELDS.contains(fieldSimpleName.name())) {
                            checkForIssue(superclassSymbol, fieldSimpleName);
                        }
                    });
        }
    }

    private void checkForIssue(Symbol.TypeSymbol classSymbol, IdentifierTree fieldSimpleName) {
        for (Symbol.TypeSymbol symbol = classSymbol; symbol != null; symbol = getSuperclass(symbol)) {
            if (checkMembers(fieldSimpleName, symbol)) {
                return;
            }
        }
    }

    private boolean checkMembers(IdentifierTree fieldSimpleName, Symbol.TypeSymbol symbol) {
        for (Symbol member : symbol.memberSymbols()) {
            if (member.isVariableSymbol()
                    && member.name().equals(fieldSimpleName.name())) {
                reportIssue(fieldSimpleName, String.format("\"%s\" 变量名称在父类 \"%s\" 中存在.", fieldSimpleName.name(), symbol.name()));
                return true;
            }
        }
        return false;
    }

    @CheckForNull
    private static Symbol.TypeSymbol getSuperclass(Symbol.TypeSymbol symbol) {
        Type superType = symbol.superClass();
        if (superType != null) {
            return superType.symbol();
        }
        return null;
    }
}
