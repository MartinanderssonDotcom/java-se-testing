package martinandersson.com.classes;

/**
 * We're used of getting the qualified name @ hashcode for objects that do not
 * provide their own toString() implementation. What do class objects return?
 * 
 * Answer: It uses a "class " prefix and no suffix "@hashcode" !
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class ClassToString
{
    public static void main(String... ignored) {
        // java.lang.Object@15db9742
        System.out.println(new Object().toString());
        
        // class martinandersson.com.classes.ClassToString
        System.out.println(ClassToString.class.toString());
    }
}