package org.sonar.samples.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.*;

import java.util.Iterator;

/**
 * @author zhaojian 2023-1-4
 * 所有的覆写方法，必须加@Override 注解校验
 */
@Rule(key = "MissingOverrideAnnotationRule")
public class MissingOverrideAnnotationRule extends BaseTreeVisitor implements JavaFileScanner {

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
    }

    @Override
    public void visitMethod(MethodTree tree) {
        if (Boolean.TRUE.equals(tree.isOverriding())){
            Iterator<AnnotationTree> iterator = tree.modifiers().annotations().iterator();
            AnnotationTree annotationTree;
            String name = "Override";
            do {
                if (tree.is(Tree.Kind.CONSTRUCTOR)){
                    continue;
                }
                if (!iterator.hasNext()) {
                    context.reportIssue(this, tree, String.format("所有的覆写方法，必须加@%s 注解", name));
                    continue;
                }
                annotationTree = iterator.next();
                TypeTree annotationType = annotationTree.annotationType();
                if (annotationType.is(Tree.Kind.IDENTIFIER)) {
                    IdentifierTree identifier = (IdentifierTree) annotationType;
                    if (identifier.name().equals(name)) {
                        break;
                    }
                }
            }while (iterator.hasNext());
        }

        // The call to the super implementation allows to continue the visit of the AST.
        // Be careful to always call this method to visit every node of the tree.
        super.visitMethod(tree);
    }
}
