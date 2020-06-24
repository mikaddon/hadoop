package com.test.demo;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DynamicProgramming {
    private static Map<Integer, BigInteger> mapAccum = new HashMap<>();
    private Map<BigInteger, BigInteger> deltaVals = new HashMap<BigInteger, BigInteger>();
    private Set<Integer> longestIncreasingSubsequence = new TreeSet<>();
    private int idx = -1;

    public static void main(String[] args) {
        DynamicProgramming dynamicProgramming = new DynamicProgramming();
        
        int lastVal = 2000;//100990; 8890 
        long startTime = System.currentTimeMillis(); 
        BigInteger result1 = null; 
        result1 = dynamicProgramming.fibonacciNumbersIterativeTabulation(lastVal); 
        long endTime= System.currentTimeMillis();
        
        System.out.println("Memoization approach took "+(endTime - startTime)/1000
        +" seconds\n And the result1 is \n "+result1); mapAccum = new HashMap<>();
        startTime = System.currentTimeMillis(); 
        BigInteger result = dynamicProgramming.fibonacciNumbersRecursiveMemoization(lastVal); 
        endTime = System.currentTimeMillis();
        
        System.out.println("REcursive approach took "+(endTime - startTime)/1000
        +" seconds\n And the result is \n "+result);
        
        int[] arrNum = new int[]{10, 22, 9, 33, 21, 50, 41, 60};
        System.out.println("longestIncreasingSubsequence " +
        dynamicProgramming.longestIncreasingSubsequence(arrNum, arrNum.length) );
        dynamicProgramming.printFizzBuzz(100);
        
        BigInteger[] arrBigInts = new BigInteger[6]; 
        arrBigInts[0] = BigInteger.valueOf(2); 
        arrBigInts[1] = BigInteger.valueOf(1000000000);
        arrBigInts[2] = BigInteger.valueOf(1000000000); 
        arrBigInts[3] = BigInteger.valueOf(3); 
        arrBigInts[4] = BigInteger.valueOf(7); 
        arrBigInts[5] = BigInteger.valueOf(8); 
        BigInteger[] twoSums = null;
        
        twoSums = dynamicProgramming.twoSums(arrBigInts, BigInteger.valueOf(5));
        System.out.println("Result of twoSums ->"+arrBigInts[twoSums[0].intValue()]
        +" + "+arrBigInts[twoSums[1].intValue()]+" = "+5); 
        twoSums = dynamicProgramming.twoSums(arrBigInts, BigInteger.valueOf(2000000000));
        System.out.println("Result of twoSums ->"+arrBigInts[twoSums[0].intValue()]
        +" + "+arrBigInts[twoSums[1].intValue()]+" = "+200000000); 
        twoSums = dynamicProgramming.twoSums(arrBigInts, BigInteger.valueOf(10));
        System.out.println("Result of twoSums ->"+arrBigInts[twoSums[0].intValue()]
        +" + "+arrBigInts[twoSums[1].intValue()]+" = "+1); 
        twoSums = dynamicProgramming.twoSums(arrBigInts, BigInteger.valueOf(1000000008));
        System.out.println("Result of twoSums ->"+arrBigInts[twoSums[0].intValue()]
        +" + "+arrBigInts[twoSums[1].intValue()]+" = "+100000008);
        
        System.out.println("Reverse palyndrom " + dynamicProgramming.reverseString("palyndrom"));
        System.out.println("Reverse 1234567890 " + dynamicProgramming.reverseInteger(1234567890));

        System.out.println("Factorial of 50 is "+
                dynamicProgramming.factorial(50));
}
    private BigInteger factorial(int number) {
        return IntStream
        .range(1, number + 1)
        .mapToObj(e -> BigInteger.valueOf(e))
        .reduce(BigInteger.valueOf(1), (c, e) -> c.multiply(e));
    }
    private String reverseString(String toReverse) {
        StringBuilder sb = new StringBuilder(toReverse);
        System.out.println("Reverse a string with StringBuilder " + sb.reverse());
          
        return IntStream.rangeClosed(1, toReverse.length())
        .mapToObj(e -> (""+toReverse.charAt(toReverse.length() - e)))
        .reduce("", (c, e) -> c + e);
    }

    private Integer reverseInteger(int numToReverse) {
        String toReverse = String.valueOf(numToReverse);
        System.out.println(numToReverse +" as string "+
            Integer.parseInt(
                IntStream.rangeClosed(1, toReverse.length())
                .mapToObj(e -> (""+toReverse.charAt(toReverse.length() - e)))
                .reduce("", (c, e) -> c + e)
            )
        );

        int reverse = 0;
        while(numToReverse !=0){
            reverse = (reverse * 10) + //Place in begin order
                (numToReverse % 10);//Take off last digigt of number
                numToReverse /= 10; //Take of the previous processed digit and process next one
        }


        return reverse;
    }

    private BigInteger[] twoSums(BigInteger[] nums, BigInteger target) {
        Object[] val = IntStream.range(0, nums.length)
                .mapToObj(n -> processDelta(n, target, nums[n]))
                .collect(Collectors.toSet())
                .stream()
                .filter(fn -> fn != null)
                .collect(Collectors.toSet()).toArray();
        return (BigInteger[]) val[0];
    }



    private BigInteger[] processDelta(int idx, BigInteger target, BigInteger value){
        if(deltaVals.containsKey(target.subtract(value))){
            return (new BigInteger[] { BigInteger.valueOf(idx), deltaVals.get(target.subtract(value)) });
        } 
        deltaVals.put(value, BigInteger.valueOf(idx));
        return null;
    }

    private void printFizzBuzz(int num){
        IntStream.range(0, num)
        .forEach(n -> {
            if(n % 3 == 0 && n % 5 == 0){
                System.out.println("FizzBuzz");
            }else if(n % 3 == 0){
                System.out.println("Fizz");
            }else if(n % 5 == 0){
                System.out.println("Buzz");
            }
            System.out.println(n);
        });
    }

    private int longestIncreasingSubsequence(int[] arrNum, int numLIS){
        int max = 0;

        int[] arrLis = IntStream.range(0, numLIS)
                 .map(e -> 1)
                 .toArray();

        IntStream.range(0, numLIS)
        .forEach(i -> {
            IntStream.range(0, i)
            .forEach(j -> {
                if(arrNum[i] > arrNum[j] && arrLis[i] < arrLis[j] + 1){
                    arrLis[i] = arrLis[j] + 1;
                }
            });
        });
        max = IntStream.range(0, numLIS)
        .map(e -> e)
        .reduce(0, (val, e) -> max(val, arrLis[e], arrNum));
        System.out.println("longestIncreasingSubsequence " + longestIncreasingSubsequence.toString());
        return max;
    }

    private int max(int val1, int val2, int[] arrNum){
        if((val1 < val2)){
            longestIncreasingSubsequence.add(arrNum[val2]);
        }
        
        return (val1 < val2)?val2:val1;
    }

    // Top Down Approach
    private BigInteger fibonacciNumbersRecursiveMemoization(int number) {
        if (number == 0) {
            mapAccum.put(0, BigInteger.valueOf(0));
            return BigInteger.valueOf(0);
        } else if (number == 1) {
            mapAccum.put(1, BigInteger.valueOf(1));
            return BigInteger.valueOf(1);
        } else {
            BigInteger n1 = null;
            BigInteger n2 = null;
            if(!mapAccum.containsKey(number - 1)){
                n1 = fibonacciNumbersRecursiveMemoization(number - 1);
                mapAccum.put(number - 1, n1);
            }else{
                n1 = mapAccum.get(number - 1);
            }
            if(!mapAccum.containsKey(number - 2)){
                n2 = fibonacciNumbersRecursiveMemoization(number - 2);
                mapAccum.put(number- 2, n2);
            }else{
                n2 = mapAccum.get(number - 2);
            }
            return n1.add(n2);
        }
    }
    //Bottom Up Approach
    private BigInteger fibonacciNumbersIterativeTabulation(int number) {
        if (number == 0) {
            return BigInteger.valueOf(0);
        } else if (number == 1) {
            return BigInteger.valueOf(1);
        } else {
            mapAccum.put(0, BigInteger.valueOf(0));
            mapAccum.put(1, BigInteger.valueOf(1));
            Map<Integer, BigInteger> accum = new HashMap<>();
            accum.put(0, BigInteger.valueOf(0));
            accum.put(1, BigInteger.valueOf(1));
            accum.putAll(
                IntStream.range(2, number + 1)
                .mapToObj(e -> e)
                .collect(Collectors.toMap(e -> e, e -> addToMap(e)))
            );
            return accum.get(number-1).add(accum.get(number-2));
        }
    }
    private BigInteger addToMap(int e){
        mapAccum.put(e, mapAccum.get(e - 1).add(mapAccum.get(e - 2)));
        return mapAccum.get(e);    
    }



}