package org.learning.core;

public class Ch1LanguageBasics {

    void main(){

        long aadharNumber = 1234_4568_9876_3456L;   // Add _ to improve readability

        // Arrays
        int[] arr = new int[5];
        int[] arr2 = {1, 2, 3, 4, 5};

        int[][] arr3 = new int[2][2];
        int[][] arr4 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        IO.println("arr.length = " + arr.length);

        // Local variable type inference
        var arr5 = new int[]{1, 2, 3}; // var can be used in methods, constructors, initializer blocks
        for(var ele : arr5){
            IO.println(ele);
        }

        // Switch expression (Refer conferences.TheThreeAmigos)
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
