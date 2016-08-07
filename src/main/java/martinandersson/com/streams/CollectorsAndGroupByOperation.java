
package martinandersson.com.streams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import static martinandersson.com.streams.Department.Type.ADMINISTRATION;
import static martinandersson.com.streams.Department.Type.LOOSER_DEPARTMENT;
import static martinandersson.com.streams.Department.Type.SOFTWARE_ENGINEERING;

/**
 *
 * @author Martin Andersson (webmaster at martinandersson.com)
 */
public class CollectorsAndGroupByOperation
{
    public static void main(String... ignored) {
        // We have some meta data about each employee. Here, amount of days they have been employed:
        Map<Employee, Integer> employeeToDaysEmployed = new HashMap<>();
        
        Department administration = new Department(ADMINISTRATION),
                   software       = new Department(SOFTWARE_ENGINEERING),
                   looser         = new Department(LOOSER_DEPARTMENT);
        
        
        // Anna belongs to the administration (type) department and has been employeed 3 days:
        employeeToDaysEmployed.put(new Employee("Anna",  administration), 3);
        
        employeeToDaysEmployed.put(new Employee("Oskar", administration), 2);
        employeeToDaysEmployed.put(new Employee("Karl",  software),       1);
        employeeToDaysEmployed.put(new Employee("Dale",  software),      30);
        employeeToDaysEmployed.put(new Employee("Kalle", looser),       999);
        
        // Now the goal is to group all these into the departments that each employee belong to.
        // But we want to keep the amount of days each employee has been employeed:
        Map<Department, Map<Employee, Integer>> departmentToEmployeesWithDays;
        
        // First try:
        Map<Department, List<Entry<Employee, Integer>>> depToListOfEntries
                = employeeToDaysEmployed.entrySet()
                        .stream()
                        .collect(Collectors.groupingBy(entry -> entry.getKey().getDepartment()));
        
        System.out.println("Keys: " + depToListOfEntries.keySet());
        System.out.println("Values: " + depToListOfEntries.values());
        
        // We're not quite there yet! A list of entries IS obviously a Map.
        
        // Second try:
        departmentToEmployeesWithDays = employeeToDaysEmployed.entrySet().stream().collect(
                        Collectors.groupingBy(entry -> entry.getKey().getDepartment(),
                                Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue())));
        
        System.out.println("Keys: " + departmentToEmployeesWithDays.keySet());
        System.out.println("Values: " + departmentToEmployeesWithDays.values());
        System.out.println();
        
        for (Entry<Department, Map<Employee, Integer>> e : departmentToEmployeesWithDays.entrySet()) {
            System.out.println("Key in entry: " + e.getKey());
            System.out.println("Value class: " + e.getClass());
            
            Map<Employee, Integer> map = e.getValue();
            
            for (Entry<Employee, Integer> e2 : map.entrySet()) {
                System.out.println("\tKey: " + e2.getKey());
                System.out.println("\tValue: " + e2.getValue());
            }
        }
    }
}

class Employee
{
    private final String name;
    private final Department department;
    
    // + whatever instance field I don't need for this example code.
    
    public Employee(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Employee[" + name + "]";
    }
}

class Department
{
    enum Type {
        ADMINISTRATION,
        SOFTWARE_ENGINEERING,
        LOOSER_DEPARTMENT
    }
    
    private final Type type;
    
    // + whatever instance field I don't need for this example code.
    
    public Department(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Department[" + type + "]";
    }
}