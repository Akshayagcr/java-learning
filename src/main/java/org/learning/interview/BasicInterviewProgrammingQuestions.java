package org.learning.interview;

import java.util.*;

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
        var arr1 = s1.toCharArray(); var arr2 = s2.toCharArray();
        Arrays.sort(arr1); Arrays.sort(arr2);
        return Arrays.equals(arr1, arr2);
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

    /*
        If it's n * n matrix iterating over arr[i].length or arr.length does not matter as both will be equal
        arr[row][column] iterating over row or column will depend on i and j
     */
    public static List<List<Integer>> getRows(int[][] arr){
        var list = new ArrayList<List<Integer>>();
        for(int i = 0; i < arr.length; i++){
            var row = new ArrayList<Integer>();
            for(int j = 0; j < arr.length; j++){
                row.add(arr[i][j]);
            }
            list.add(row);
        }
        return list;
    }

    public static List<List<Integer>> getColumns(int[][] arr){
        var list = new ArrayList<List<Integer>>();
        for(int i = 0; i < arr.length; i++){
            var column = new ArrayList<Integer>();
            for(int j = 0; j < arr.length; j++){
                column.add(arr[j][i]);
            }
            list.add(column);
        }
        return list;
    }

    /*
        https://leetcode.com/problems/valid-sudoku/
     */
    class Solution {
        public boolean isValidSudoku(char[][] board) {
            return isRowValid(board)
                    && isColumnValid(board)
                    && isCellValid(board);
        }

        public static boolean isRowValid(char[][] board){
            for(int i = 0; i < board.length; i++){
                var row = new ArrayList<Character>();
                for(int j = 0; j < board.length; j++){
                    row.add(board[i][j]);
                }
                if(!checkIfValid(row)){
                    return false;
                }
            }
            return true;
        }

        public static boolean isColumnValid(char[][] board){
            for(int i = 0; i < board.length; i++){
                var column = new ArrayList<Character>();
                for(int j = 0; j < board.length; j++){
                    column.add(board[j][i]);
                }
                if(!checkIfValid(column)){
                    return false;
                }
            }
            return true;
        }

        public static boolean isCellValid(char[][] board){
            for(int i = 0; i < board.length; i += 3){
                for(int j = 0; j < board.length; j += 3){
                    var cell = new ArrayList<Character>();
                    for(int k = i; k < i + 3; k++){
                        for(int p = j; p < j + 3; p++){
                            cell.add(board[k][p]);
                        }
                    }
                    if(!checkIfValid(cell)){
                        return false;
                    }
                }
            }
            return true;
        }

        public static boolean checkIfValid(List<Character> arr){
            var s = new HashSet<Character>();
            for(var c : arr){
                if(!c.equals('.')){
                    if(s.contains(c)){
                        return false;
                    }
                    s.add(c);
                }
            }
            return true;
        }
    }

}
