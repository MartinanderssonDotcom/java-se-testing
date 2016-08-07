package martinandersson.com.anonymousclasses;

/**
 * Demonstrate that an anonymous class has runtime access to enclosing fields.
 * 
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class AnonymousAccessToField
{
    int myfield = 1;
    
    public static void main(String... ignored) {
        new AnonymousAccessToField().main0();
    }
    
    public void main0() {
        ReadTheInt x = new ReadTheInt() {
            @Override public void printTheInt() {
                System.out.println(myfield);
            }
        };
        
        // 1
        x.printTheInt();
        
        myfield = 2;
        
        // 2
        x.printTheInt();
    }
}

interface ReadTheInt
{
    void printTheInt();
}