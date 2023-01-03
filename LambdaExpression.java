import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

// Lambda expression is only invoked by functional interface (which includes only one abstract method but can have several default, static methods)
interface LambdaFunctionalInterface{
    public void abstractMethod();
}

// When functional Interface used, abstract method of the Interface can be defined by Implementation class either defined directly by Lambda expression
class ImplementationClass implements LambdaFunctionalInterface{
    public void abstractMethod(){
        System.out.println("Hi!");
    };
}

// Abstract method of interface can contain parameters as well
interface LambdaFunctionalInterface2{
    public void abstractMethod2(String parameter1, String parameter2);
}

//pre-defined functional Interface in Java
//1) Predicate <Parameter Type>: with single abstract method "test()"
//2) Function <Parameter Type, Return Type>: with single abstract method "apply()"
//3) Consumer <Parameter Type>: with single abstract method "accept()"
//4) Supplier <Return Type>: with single abstract method "get()"

public class LambdaExpression {
    public static void main(String[] args) {
        ImplementationClass implementationClass = new ImplementationClass();
        implementationClass.abstractMethod();

        LambdaFunctionalInterface lambdaFunctionalInterface=()->System.out.println("To implement Lambda expression!!");
        lambdaFunctionalInterface.abstractMethod();

        LambdaFunctionalInterface2 lambdaFunctionalInterface2=(parameter1, parameter2)-> System.out.println("Parameters can be passed as well such as "+parameter1+" and "+parameter2);
        lambdaFunctionalInterface2.abstractMethod2("Sample Parameter1","Sample parameter2");

        Predicate<Integer> predicate=i->(i>10);
        System.out.println(predicate.test(20));

        Predicate<String> predicate2=i->(i.length()<5);
        System.out.println(predicate2.test("Misoya"));

        //Check value in the array using lambda expression with Predicate interface
        String names[] = {"David", "Scott", "Smith","John","Mary"};

        for (String name:names){
            if (predicate2.test(name)){
                System.out.println(name);
            }
        }
// Set multiple conditions using lambda expression with Predicate interface
        Predicate<Employee> predicate3 = e->(e.salary>9000 && e.experience>3);

        ArrayList<Employee> arrayList = new ArrayList<>();
        arrayList.add(new Employee("Aiso",9000,4));
        arrayList.add(new Employee("Biso",10000,2));
        arrayList.add(new Employee("Ciso",20000,4));

        for (Employee employee:arrayList){
            if (predicate3.test(employee)){
                System.out.println(employee.name+" "+employee.salary);
            }
        }
    }
}

// Object of class used as ArrayList Elements
class Employee{
    String name;
    int salary;
    int experience;

    public Employee(String name, int salary, int experience) {
        this.name = name;
        this.salary = salary;
        this.experience = experience;
    }
}

// Predicate Joining - and, or, negate
public class LambdaExpression {
    public static void main(String[] args) {
        int array[] = {5,15,20,25,30,35,40,45,50,55,60,65};
        Predicate<Integer> p1 =i->i%2 ==0;
        Predicate<Integer> p2 =i->i>50;

        System.out.println("Following are numbers Even & Greater than 50...");

        for (int arrayElement:array){
            if (p1.and(p2).test(arrayElement)){ // p1.test(arrayElement) && p2.test(arrayElement)
                System.out.println(arrayElement );
            }
        }

        System.out.println("Following are numbers Even or Greater than 50...");
        for (int arrayElement:array){
            if (p1.or(p2).test(arrayElement)){ // p1.test(arrayElement) && p2.test(arrayElement)
                System.out.println(arrayElement );
            }
        }

        for (int arrayElement:array){
            if (p1.negate().test(arrayElement)){ // p1.test(arrayElement) && p2.test(arrayElement)
                System.out.println(arrayElement );
            }
        }
    }
}

// To demonstrate Function Interface
public class LambdaExpression {
    public static void main(String[] args) {
        ArrayList<Employee> arrayList = new ArrayList<>();
        arrayList.add(new Employee("Aiso",10000,2));
        arrayList.add(new Employee("Biso",25000,5));
        arrayList.add(new Employee("Ciso",40000,5));

        Function<Employee,Integer> function = e->{
          int sal = e.salary;
          if (sal>=10000 && sal<=20000)
              return (sal*10/100);
          else if(sal>20000 && sal<=30000)
              return (sal*30/100);
          else if (sal>30000 && sal<=50000)
              return (sal*30/100);
         else return (sal*40/100);
        };

        for (Employee employee:arrayList){
            int bonus = function.apply(employee);
            System.out.println(employee.name+" Salary is "+employee.salary+" Bonus is "+bonus);
    }

        // Apply Predicate & Function interfaces at the same time
        // Predicate ----> Parameter Type with predefined abstract method "test()" ----> boolean
        // Function ----> Parameter Type, Return Type with predefined abstract method "apply()" ----> some type
        // Consumer ----> Parameter Type with predefined abstract method "accept()" predefined ----> No return value
        // Supplier ----> Not accept parameter but return value with predefined abstract method "get()"

        System.out.println(" ");
        System.out.println("Following lists are for those who receives bonus more than 5,000");

        Predicate<Integer> predicate = bonus-> bonus>5000;
        for (Employee employee:arrayList){
            int bonus = function.apply(employee);
            if (predicate.test(bonus)){
            System.out.println(employee.name+" Salary is "+employee.salary+" Bonus is "+bonus);
        }}

        //Function chaining for Function Interface: andThen(), compose()
        Function<Integer, Integer> function1 = n->n*2;
        Function<Integer, Integer> function2 = n->n*n*n;
        System.out.println(function1.andThen(function2).apply(2));;
        System.out.println(function1.compose(function2).apply(2));;

}
}

    // To demo Consumer interface
    class Employee{
    String ename;
    int salary;
    String gender;

        public Employee(String ename, int salary, String gender) {
            this.ename = ename;
            this.salary = salary;
            this.gender = gender;
        }
    }

    public class LambdaExpression{
        public static void main(String[] args) {
            ArrayList<Employee> employees = new ArrayList<Employee>();
            employees.add(new Employee("David", 50000, "Male"));
            employees.add(new Employee("John", 30000, "Male"));
            employees.add(new Employee("Mary", 20000, "Female"));
            employees.add(new Employee("Scott", 60000, "Male"));

            // Function Interface for task 1 (Calculate bonus and return)
            Function<Employee,Integer> function = employee -> (employee.salary*10)/100;

            // Predicate Interface for task 2 (check if employee's bonus is more than 5000)
            Predicate <Integer> predicate = bonus -> bonus>=5000;

            // Consumer Interface for task 3
            Consumer<Employee> consumer = employee -> {
                System.out.println(employee.ename+" "+employee.salary+" "+employee.gender);
            };

            for(Employee employee:employees){
                int bonus = function.apply(employee); // Function invoked by "apply()" method
                if(predicate.test(bonus)){ // Predicate invoked by "test()" method
                    consumer.accept(employee); // Consumer invoked by "apply()" method
                    System.out.println(bonus);
                }
            }

            // Consumer Chaining
            Consumer<String> consumer1 = obj-> System.out.println(obj+" "+"is white");
            Consumer<String> consumer2 = obj-> System.out.println(obj+" "+"has four legs");
            Consumer<String> consumer3 = obj-> System.out.println(obj+" "+"is eating grass");

            Consumer<String> consumer4 = consumer1.andThen(consumer2).andThen(consumer3);
            consumer4.accept("Cow");

            // Supplier Interface
            Supplier<Date> supplier=()-> new Date();
            System.out.println(supplier.get());
        }
    }


