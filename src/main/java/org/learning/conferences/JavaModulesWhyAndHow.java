package org.learning.conferences;

/*
    Java modules were primarily created for java to modularize java platform itself
    Module requires another module. Module export packages
    We have classPath which is legacy, and then we have module path

    Three types of module
        1. Unnamed module :- everything in class path -only one
        2. Automatic module :- old stuff(jars) in module path
        3. Explicitely named module :- new stuff in module path - has a module descriptor

    module-info.java - under the first folder of the package/folder hierarchy
        module firstModuleName {
            exports com.example.package;
        }

    With modules even if classes in package are public they will not be available to other without export.

    Event if firstModule has exported the package second module cannot access package as it need to requier first module

    modue secondModule{
        requires firstModuleName;
    }

    TODO: Need to complete!!!!


 */
public class JavaModulesWhyAndHow {
}
