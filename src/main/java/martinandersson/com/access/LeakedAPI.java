package martinandersson.com.access;

import martinandersson.com.access.OwnerOfNestedClass.PackageNested;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class LeakedAPI
{
    public static void main(String... ignored) {
        PackageNested nested = new OwnerOfNestedClass.PackageNested();
        
        nested.packageMethod();
//        nested.privateMethod();
        
        OwnerOfNestedClass owner = new OwnerOfNestedClass();
        
//        PrivateNested privateNested = owner.leakPrivateNested();
        Object privateNested = owner.leakPrivateNested();
    }
}