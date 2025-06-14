package org.learning.core;

/**
 * Nested classes, Switch expressions, Enum, Records
 */
class LanguageBasics {

    void testSwitchExpression(){
        var choice  = 10;

        var result = switch(choice){
            case 1, 2, 3 -> "Case one";
            case 4, 5 -> {
                var s = "two";
                yield "Case " + s;  // Use yield to return values from block
            }
            case 10 -> "Case three";
            default -> "Default";   // Default not required in case of Enum
        };

    }

}
