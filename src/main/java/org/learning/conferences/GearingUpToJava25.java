package org.learning.conferences;

/**
 * https://www.youtube.com/watch?v=bbS_p4A3xfc&t=1466s
 */
public class GearingUpToJava25 {

    /*
        Features:-
            1. Pattern matching with primitive types
                Switch -> Expression -> Pattern matching
                Prior to Java 25 primitive types were not supported !!!

            2. Reduced verbosity for beginners
                We can now run Java code as a script
                java MyScript.java

                MyScript.java

                void main(){
                    var name = IO.readln();
                    IO.println("Hello %s".formatted(name));
                }

            3. importing module
                import statement is for compiler to what we are referring to
                class path is for runtime/compile time to know where to get binaries from

                import module java.net.http;

                It automatically refrences all packages withing a module
                To resolve ambiguity use standard import along with module import to disambiguate

            4. Flexible constructor bodies
                In java 25 we are allowed to call things before call to super constructor. But we cannot call methods before it
                as object is still not created
     */
}
