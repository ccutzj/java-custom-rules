class ParentClass {
    protected boolean doSomething(){
        return true;
    }

    class FirstChildClass extends ParentClass {
        protected boolean doSomething(){return true;}  // Noncompliant
    }

    class ChildClass extends ParentClass {
        @Override
        protected boolean doSomething(){return true;}  // Compliant
    }
}
