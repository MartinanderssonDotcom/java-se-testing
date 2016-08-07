package martinandersson.com.access;

/**
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class OwnerOfNestedClass
{
    static class PackageNested
    {
        void packageMethod() {}
        
        private void privateMethod() {} 
    }
    
    private static class PrivateNested
    {
        private void privateMethod() {}
    }
    
    public PrivateNested leakPrivateNested() {
        return new PrivateNested();
    }
}