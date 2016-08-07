package martinandersson.com.polymorphism;

import static java.lang.System.out;

/**
 * C extends B which extends A. All implement method "overridden". Call is
 * routed to C. How do C make sure that only B.overridden() is called?<p>
 * 
 * Answer: The regular super-call invoke B.overridden(). A.overridden() is never
 *         called.
 * 
 * So, what if we want to invoke A.overridden()? Apparently, one cannot do that.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class CallingMiddleMan
{
    public static void main(String... ignored) {
        C c = new C();
        c.overridden();
    }
}

class A {
    public void overridden() {
        out.println("A.overridden()");
    }
}

class B extends A {
    @Override
    public void overridden() {
        out.println("B.overridden()");
    }
}

class C extends B {
    @Override
    public void overridden() {
        out.println("C.overridden()");
        
        // I want to call B.overridden():
        super.overridden(); // <-- B.overridden() is called, not A.overridden().
        
        /*
         * This cause stack overflow. C.overridden() and B.overridden() is
         * repeatedly called. Again, A.overridden() is never called.
         */
//        A a = this;
//        a.overridden();
    }
}