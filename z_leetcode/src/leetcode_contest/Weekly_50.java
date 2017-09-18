package leetcode_contest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class Weekly_50 {

	//680. Valid Palindrome II
	public boolean validPalindrome(String s) {
		int right = 0, left = s.length() - 1;
		while(right < left) {
			if(s.charAt(right) != s.charAt(left)) {
				return validPalindrome(s, right + 1, left) || validPalindrome(s, right, left - 1);
			}
			left--;
			right++;
		}
		return true;
    }
	private boolean validPalindrome(String s, int right, int left) {
		while(right < left) {
			if(s.charAt(right) != s.charAt(left)) return false;
			left--;
			right++;
		}
		return true;
	}

	//677. Map Sum Pairs
	class MapSum {
		HashMap<String, Integer> maps = new HashMap<String, Integer>();
		HashMap<String, Integer> strs = new HashMap<String, Integer>();
	    /** Initialize your data structure here. */
	    public MapSum() {
	        
	    }
	    
	    public void insert(String key, int val) {
	    	if(strs.containsKey(key)) {
	    		for(int i = 1; i <= key.length(); i++) {
   	        		int count = maps.get(key.substring(0, i));
   		        	count = count + val - strs.get(key);
   		        	maps.put(key.substring(0, i), count);
   	        	}
		        strs.put(key, val);
        	} else {
        		for(int i = 1; i <= key.length(); i++) {
   	        		int count = maps.getOrDefault(key.substring(0, i), 0);
   		        	maps.put(key.substring(0, i), count + val);
   	        	}
		        strs.put(key, val);
        	}
	    }
	    
	    public int sum(String prefix) {
	    	if(!maps.containsKey(prefix)) return 0;
	        return maps.get(prefix);
	    }
	}
	
	//678. Valid Parenthesis String
	public boolean checkValidString(String s) {
		Stack<Integer> st1 = new Stack<Integer>();
		Stack<Integer> st2 = new Stack<Integer>();
		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == '(') {
				st1.push(i);
			} else if(s.charAt(i) == ')') {
				if(st1.isEmpty() && st2.isEmpty()) return false;
				else if(!st1.isEmpty()) {
					st1.pop();
				} else if(!st2.isEmpty()) {
					st2.pop();
				}
			} else if(s.charAt(i) == '*') {
				st2.push(i);
			}
		}
		if(st1.isEmpty()) return true;
		while(!st1.isEmpty()) {
			if(st2.isEmpty() || st1.pop() > st2.pop() ) return false;
		}
		return true;
	}
	
	//679. 24 Game
	public boolean judgePoint24(int[] nums) {
        List<Double> sum2_01 = getSums(nums[0], nums[1]); 
        List<Double> sum2_02 = getSums(nums[0], nums[2]); 
        List<Double> sum2_03 = getSums(nums[0], nums[3]); 
        List<Double> sum2_12 = getSums(nums[1], nums[2]); 
        List<Double> sum2_13 = getSums(nums[1], nums[3]); 
        List<Double> sum2_23 = getSums(nums[2], nums[3]); 
        List<Double> sum3_01_2 = getSums(sum2_01, nums[2]);
        List<Double> sum3_01_3 = getSums(sum2_01, nums[3]);
        List<Double> sum3_02_1 = getSums(sum2_02, nums[1]);
        List<Double> sum3_02_3 = getSums(sum2_02, nums[3]);
        List<Double> sum3_03_1 = getSums(sum2_03, nums[1]);
        List<Double> sum3_03_2 = getSums(sum2_03, nums[2]);
        List<Double> sum3_12_0 = getSums(sum2_12, nums[0]);
        List<Double> sum3_12_3 = getSums(sum2_12, nums[3]);
        List<Double> sum3_13_0 = getSums(sum2_13, nums[0]);
        List<Double> sum3_13_2 = getSums(sum2_13, nums[2]);
        List<Double> sum3_23_0 = getSums(sum2_23, nums[0]);
        List<Double> sum3_23_1 = getSums(sum2_23, nums[1]);
        HashSet<Double> res = new HashSet<Double>();
        getSums(sum2_01, sum2_23, res);
        getSums(sum2_02, sum2_13, res);
        getSums(sum2_03, sum2_12, res);
        getSums(sum3_01_2, nums[3], res);
        getSums(sum3_01_3, nums[2], res);
        getSums(sum3_02_1, nums[3], res);
        getSums(sum3_02_3, nums[1], res);
        getSums(sum3_03_1, nums[2], res);
        getSums(sum3_03_2, nums[1], res);
        getSums(sum3_12_0, nums[3], res);
        getSums(sum3_12_3, nums[0], res);
        getSums(sum3_13_0, nums[2], res);
        getSums(sum3_13_2, nums[0], res);
        getSums(sum3_23_0, nums[1], res);
        getSums(sum3_23_1, nums[0], res);
        for(double count : res) {
        	if(Math.abs(count - 24) <= 0.01) return true;
        }
        return false;
    }
	private void getSums(List<Double> sum3, int n1, HashSet<Double> res) {
		double i = n1;
		for(double j : sum3) {
			res.add((double) (i + j));
			res.add((double) (i - j));
			res.add((double) (j - i));
			res.add((double) (i * j));
			if(j != 0 ) res.add((double) (i / j));
			if(i != 0 )res.add((double) (j / i));
		}		
	}
	private void getSums(List<Double> sum2_1, List<Double> sum2_2, HashSet<Double> res) {
		for(double i : sum2_1) {
			for(double j : sum2_2) {
				res.add((double) (i + j));
				res.add((double) (i - j));
				res.add((double) (j - i));
				res.add((double) (i * j));
				if(j != 0 ) res.add((double) (i / j));
				if(i != 0 )res.add((double) (j / i));
			}
		}		
	}
	private List<Double> getSums(List<Double> sum2, int n1) {
		double i = n1;
		List<Double> res = new ArrayList<Double>();
		for(double j : sum2) {
			res.add((double) (i + j));
			res.add((double) (i - j));
			res.add((double) (j - i));
			res.add((double) (i * j));
			if(j != 0 ) res.add((double) (i / j));
			if(i != 0 )res.add((double) (j / i));
		}
		return res;
	}
	private List<Double> getSums(int n1, int n2) {
		double i = n1, j = n2;
		List<Double> res = new ArrayList<Double>();
		res.add((double) (i + j));
		res.add((double) (i - j));
		res.add((double) (j - i));
		res.add((double) (i * j));
		if(j != 0 ) res.add((double) (i / j));
		if(i != 0 )res.add((double) (j / i));
		return res;
	}
	
	
	
	
	
	
	

	
	
	
	
}
