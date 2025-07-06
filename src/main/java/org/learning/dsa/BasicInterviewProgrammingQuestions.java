package org.learning.dsa;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BasicInterviewProgrammingQuestions {

    public static String reverse(String ip){
        var sb = new StringBuilder(ip);
        int i = 0, j = sb.length() - 1;
        char temp;
        while(i < j){
            temp = sb.charAt(j);
            sb.setCharAt(j, sb.charAt(i));
            sb.setCharAt(i, temp);
            i++; j--;
        }
        return sb.toString();
    }

    public static boolean isPalindrome(String s){
        int i = 0, j = s.length() - 1;
        while(i < j){
            if(s.charAt(i) != s.charAt(j)){
                return false;
            }
            i++; j--;
        }
        return true;
    }

    public static Map<Character, Integer> characterFrequency(String ip){
        var m = new HashMap<Character, Integer>();
        ip.chars()
                .mapToObj(c -> (char)c)
                .forEach(c -> {
                    m.compute(c, (ch, count) -> Objects.isNull(count) ? 1 : count + 1);
                    /*
                    if(m.containsKey(c)){
                        var count = m.get(c);
                        m.put(c, ++count);
                    } else {
                        m.put(c, 1);
                    }
                     */
                });
        return m;
    }

    public static boolean isAnagram(String s1, String s2){
        var m1 = characterFrequency(s1);
        var m2 = characterFrequency(s2);
        for(var entry : m1.entrySet()){
            if(!m2.containsKey(entry.getKey())){
                return false;
            }
            var val = m2.get(entry.getKey());
            if(!val.equals(entry.getValue())){
                return false;
            }
        }
        return true;
    }

    public static int[] insertionSort(int[] ip){
        int i, j, key;
        int[] arr = Arrays.copyOf(ip, ip.length);
        for(i = 1; i < arr.length; i++){
            key = arr[i];
            j = i - 1;
            while(j >= 0 && arr[j] > key){
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
        return arr;
    }

    public static int reverserInteger(int i){
        int res = 0;
        while(i > 0){
            res = res * 10 + i % 10;
            i = i / 10;
        }
        return res;
    }
}
