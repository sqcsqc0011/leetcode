package leetcode_bytag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BitManipulationTag {

	//78. Subsets
	//bit manipulation
    public List<List<Integer>> subsets(int[] nums) {
    	Arrays.sort(nums);
    	int totalNumber = 1 << nums.length;
    	List<List<Integer>> res = new ArrayList<List<Integer>>();
    	for(int i = 0 ; i < totalNumber; i++){
    		List<Integer> oneSet = new ArrayList<Integer>();
    		for( int j = 0; j < nums.length; j++){
    			int posj = 1 << j;
    			if( (i & posj) > 0) oneSet.add(nums[j]);
    		}
    		res.add(oneSet);
    	}
    	return res;
    }
	
    //backtracking soultion
    public List<List<Integer>> subsetsBactTracking(int[] nums) {
		Arrays.sort(nums);
		List<List<Integer>> result = new ArrayList<List<Integer>>();
        getOneSubsetSolution(result, new ArrayList<Integer>(), nums, 0);
        result.add(new ArrayList<Integer>());
        return result;
    }
	private void getOneSubsetSolution(List<List<Integer>> result, ArrayList<Integer> oneSubset, int[] nums, int position) {
		for(int i  = position; i < nums.length; i++){
			oneSubset.add(nums[i]);
			result.add(new ArrayList<>(oneSubset));
			getOneSubsetSolution(result, oneSubset, nums, i + 1);
			oneSubset.remove(oneSubset.size() - 1);
		}
	}
	
	//136. Single Number
	// a^a = 0;
    public int singleNumberI(int[] nums) {
    	int res = 0;
    	for(int num : nums){
    		res = res ^ num;
    	}
    	return res;
    }
	
	//137. Single Number II
    //solution1: 
    //用ones记录到当前计算的变量为止，二进制1出现“1次”（mod 3 之后的 1）的数位。
    //用twos记录到当前计算的变量为止，二进制1出现“2次”（mod 3 之后的 2）的数位。
    //当ones和twos中的某一位同时为1时表示二进制1出现3次，此时需要清零。即用二进制模拟三进制计算。最终ones记录的是最终结果。
    public int singleNumberII(int[] nums) {  
    	int ones = 0, twos = 0, threes = 0;
    	for(int num : nums){
    		twos = twos | ( ones & num);
    		ones = ones ^ num;
    		threes = ~(ones & twos);
    		ones = ones & threes;
    		twos = twos & threes;
    	}
    	return ones;
    }
	//solution2: 用一个32位的数的每一位表示某一位出现几次，出现3次就给它归零：
    public int singleNumberII2(int[] nums) {  
    	if(nums == null || nums.length == 0) return 0;
    	int res = 0;
    	for( int i = 0; i < 32; i++){
    		int sum = 0;
    		for( int j = 0; j < nums.length; j++){
    			if( ( (nums[j] >> i) & 1 ) > 0){
    				sum++;
    				sum %= 3;
    			}
    		}
    		res = res | sum << i;
    	}
    	return res;
    }
	
	//169. Majority Element
    public int majorityElement(int[] nums) {
        int[] bit = new int[32];
        for (int num: nums)
            for (int i = 0; i < 32; i++) 
                if ((num >> (31 - i) & 1) == 1)
                    bit[i]++;
        int res = 0;
        for (int i = 0; i < 32; i++) {
            bit[i] = bit[i] > nums.length/2 ? 1 : 0;
            res += bit[i] * ( 1 << (31-i) );
        }
        return res;
    }

    //190. Reverse Bits    
    public int reverseBits(int n) {
    	int res = 0;
    	for( int i = 1; i <= 32; i++){
    		res += n & 1;
    		n = n >>> 1;
            if( i <= 31) res = res << 1;
    	}
    	return res;
    }
	
	//191. Number of 1 Bits
    public int hammingWeight(int n) {
    	int res = 0;
    	for( int i = 0; i < 32; i++){
    		int tmp = n >>> i;
            if( (tmp & 1) > 0) res++;
    	}
    	return res;
    }
	
	//201. Bitwise AND of Numbers Range
    public int rangeBitwiseAnd(int m, int n) {
    	if(m == 0)  return 0;
    	int moveFactor = 1;
    	while( m != n){
    		m = m >> 1;
    		n = n >> 1;
    		moveFactor = moveFactor << 1;
    	}
    	return m * moveFactor;
    }
	
	//231. Power of Two
    public boolean isPowerOfTwo(int n) {
        return n > 0 && (n & ( n - 1)) == 0;
    }
	
    //260. Single Number III
    public int[] singleNumber(int[] nums) {
        int AXORB = 0;
        for (int num : nums) {
            AXORB ^= num; 
        }
        // pick one bit as flag
        int bitFlag = (AXORB & (~ (AXORB - 1)));
        int[] res = new int[2];
        for (int num : nums) {
            if ((num & bitFlag) == 0) {
                res[0] ^= num;
            } else {
                res[1] ^= num;
            }
        }
        return res;
    }
	
	//268. Missing Number
    //solution1: sumall - sum
    //solution2: bit manipulation
    public int missingNumber(int[] nums) {
    	int res = 0;
    	for(int i = 1; i <= nums.length; i++){
    		res = res ^ i;
    		res = res ^ nums[i - 1];
    	}
    	return res;
    }
	
	//318. Maximum Product of Word Lengths
    public int maxProduct(String[] words) {
    	int[] nums = new int[words.length];
        for(int i = 0; i < words.length; i++){
        	String word = words[i];
        	nums[i] = 0;
        	for(int j = 0; j < word.length(); j++){
        		nums[i] = nums[i] | ( 1 << word.charAt(j) - 'a');
        	}
        }
        int res = 0;
        for( int i = 0; i < nums.length; i++){
        	for( int j = i + 1; j < nums.length; j++){
        		if( (nums[i] & nums[j]) == 0 ) res = Math.max(res, words[i].length() * words[j].length());
        	}
        }
    	return res;
    }
    
    //338. Counting Bits
    //!!!brilliant!!!!!!
    public int[] countBits(int num) {
    	int[] res = new int[num + 1];
    	res[0] = 0;
        for(int i = 1; i <= num; i++){
        	res[i] = res[i >> 1] + ( i & 1);
        }
        return res;
    }
    
    //342. Power of Four
    //The basic idea is from power of 2, We can use "n&(n-1) == 0" to determine if n is power of 2.
    //For power of 4, the additional restriction is that in binary form, the only "1" should always located at the odd position. For example, 4^0 = 1, 4^1 = 100, 4^2 = 10000. 
    //So we can use "num & 0x55555555==num" to check if "1" is located at the odd position.
    public boolean isPowerOfFour(int num) {
        return (num > 0) && ((num & (num - 1)) == 0) && ((num & 0x55555555) == num);
    }
	
	//371. Sum of Two Integers
    public int getSum(int a, int b) {
    	if (a == 0) return b;
    	if (b == 0) return a;
    	if( b != 0 ) return getSum( a ^ b, (a & b) << 1);
    	return a;
    }
	
	//389. Find the Difference
    public char findTheDifference(String s, String t) {
    	char res = t.charAt(t.length() - 1);
    	for(int i = 0; i < s.length(); i++){
    		res ^=  s.charAt(i);
    		res ^=  t.charAt(i);
    	}
    	return res;
    }
    
    //393. UTF-8 Validation
    
    //397. Integer Replacement
    // n is even, n = n/2
    // n==3 || ((n >>> 1) & 1) == 0  n--; //like 1110101
    // n++ // like 1110111
    public int integerReplacement(int n) {
    	int c = 0;
        while (n != 1) {
            if ((n & 1) == 0) {
                n = n >>> 1;
            } else if (n == 3 || ((n >>> 1) & 1) == 0) {
                n--;
            } else {
                n++;
            }
            c++;
        }
        return c;
    }
    
    //401. Binary Watch
    public List<String> readBinaryWatch(int num) {
    	List<String> res = new ArrayList<String>();
        for( int h = 0; h < 12; h++){
        	for( int m = 0; m < 60; m++){
        		if(Integer.bitCount(h) + Integer.bitCount(m) == num)
        			res.add(String.format("%d:%02d", h, m));
        	}
        }
        return res;
    }
    
    //405. Convert a Number to Hexadecimal
    public String toHex(int num) {
    	String res = "";
        char[] map = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        if( num == 0) return res + map[0];
    	while( num != 0 ){
    		res = map[(num & 15)] + res;
    		num = num >>> 4;
    	}
    	return res;
    }
    
    //421. Maximum XOR of Two Numbers in an Array
    //!!!!!!!!!!!!!!!!!not fixed
    public int findMaximumXOR(int[] nums) {
        int max = 0, mask = 0;
        for (int i = 31; i >= 0; i--) {
            mask |= (1 << i);
            Set<Integer> set = new HashSet<Integer>();
            for (int num : nums) {
                set.add(num & mask); // reserve Left bits and ignore Right bits
            }
            int tmp = max | (1 << i); // in each iteration, there are pair(s) whoes Left bits can XOR to max
            for (int prefix : set) {
                if (set.contains(tmp ^ prefix)) {
                    max = tmp;
                }
            }
        }
        return max;
    }
    
    //461. Hamming Distance
    public int hammingDistance(int x, int y) {
        //return Integer.bitCount(x ^ y);
    	int xor = x ^ y, res = 0;
    	while( xor > 0){
    		res += xor & 1;
    		xor = xor >>> 1;
    	}
    	return res;
    }
    
    //476. Number Complement
    public int findComplement(int num) {
    	int sum = 1;
        for(int i = 0; i < 31; i++){
        	sum = sum << 1;
        	if( sum > num){
        		return sum - 1 - num;
        	}
        }
		sum = Integer.MAX_VALUE;
        return sum - num;
    }
    
    //477. Total Hamming Distance
    // for each bit, k is 1, n - k is 0, total distances for this bit is k*(n-k);
    public int totalHammingDistance(int[] nums) {
        int n = nums.length, res = 0;
        for( int i = 0; i < 32; i++){
        	int count1 = 0;
        	for( int num : nums){
        		int mask = 1 << i;
        		count1 += (mask & num) > 0 ? 1 : 0;
        	}
        	res += count1 * ( n - count1);
        }
        return res;
    }
    
    
    
    
    
    
    
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
