package leetcode_bytag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Random;

public class ArrayTag {

	//495. Teemo Attacking
//	For each begin followed by t
//	If t is within previous duration [begin, begin + duration] then increase total by t - begin
//	If t in out of previous duration [begin, begin + duration] then increase total by duration
//	In both cases update begin to the new begin time t
	public int findPoisonedDuration(int[] timeSeries, int duration) {
		if( timeSeries.length == 0 ) return 0;
		int result = 0;
		int begin = timeSeries[0], end = timeSeries[0] + duration - 1;
		result += end - begin + 1;
		for( int i = 1; i < timeSeries.length; i++){
			if( timeSeries[i] <= end){
				result += timeSeries[i] + duration - 1 - end;
				end = timeSeries[i] + duration - 1;
			}else {
				begin = timeSeries[i];
				end = timeSeries[i] + duration - 1;
				result += end - begin + 1;
			}
		}
        return result;
    }
	
	//485. Max Consecutive Ones
	public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0, count = 0;
        for(int i = 0; i < nums.length; i++){
        	if( nums[i] == 1){
        		count++;
        	}else if( nums[i] == 0){
        		max = Math.max(max, count);
        		count = 0;
        	}
        	if( i == nums.length - 1) max = Math.max(max, count);
        }
        return max;
    }
	
	//448. Find All Numbers Disappeared in an Array
	public List<Integer> findDisappearedNumbers(int[] nums) {
		List<Integer> result = new ArrayList<Integer>();
        for( int i = 0; i < nums.length; i++){
            int index = nums[i];
            if(nums[Math.abs(index) - 1] > 0){
                nums[Math.abs(index) - 1] = -nums[Math.abs(index)-1];
            }
        }
        for(int j = 1 ;j <= nums.length ; j++){
            if(nums[j - 1] > 0) result.add(j);
        }
        return result;
    }
	
	//442. Find All Duplicates in an Array
	public List<Integer> findDuplicates(int[] nums) {
		List<Integer> result = new ArrayList<Integer>();
        for( int i = 0; i < nums.length; i++){
            int index = nums[i];
            if(nums[Math.abs(index) - 1] < 0) result.add(Math.abs(index));
            nums[Math.abs(index) - 1] = -nums[Math.abs(index)-1];
        }
        return result;
    }
	
	//414. Third Maximum Number
	public int thirdMax(int[] nums) {
        Integer max1 = null, max2 = null, max3 = null;
        for( int i = 0; i < nums.length; i++){
        	if( max1 == null || nums[i] > max1){
        		max3 = max2;
        		max2 = max1;
        		max1 = nums[i];
        	} else if( max2 == null || nums[i] > max2 ){
        		if( nums[i] < max1) {
	        		max3 = max2;
	        		max2 = nums[i];
        		}
        	} else if( max3 == null || nums[i] > max3 ){
        		if(nums[i] < max2) max3 = nums[i];
        	}
        }
        if( max3 == null) return max1;
        return max3;
    }
	
	//380. Insert Delete GetRandom O(1)
	public class RandomizedSet {
	    ArrayList<Integer> nums;
	    HashMap<Integer, Integer> locs;
	    Random random = new Random();
	    /** Initialize your data structure here. */
	    public RandomizedSet() {
	        nums = new ArrayList<Integer>();
	        locs = new HashMap<Integer, Integer>();
	    }
	    
	    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
	    public boolean insert(int val) {
	        boolean contain = locs.containsKey(val);
	        if ( contain ) return false;
	        locs.put( val, nums.size());
	        nums.add(val);
	        return true;
	    }
	    
	    /** Removes a value from the set. Returns true if the set contained the specified element. */
	    public boolean remove(int val) {
	        boolean contain = locs.containsKey(val);
	        if ( ! contain ) return false;
	        int loc = locs.get(val);
	        if (loc < nums.size() - 1 ) { // not the last one than swap the last one with this val
	            int lastone = nums.get(nums.size() - 1 );
	            nums.set( loc , lastone );
	            locs.put(lastone, loc);
	        }
	        locs.remove(val);
	        nums.remove(nums.size() - 1);
	        return true;
	    }
	    
	    /** Get a random element from the set. */
	    public int getRandom() {
	        return nums.get( random.nextInt(nums.size()) );
	    }
	}
	
	//381. Insert Delete GetRandom O(1) - Duplicates allowed
	public class RandomizedCollection {
		Comparator<Integer> cmp = new Comparator<Integer>() {
	      public int compare(Integer e1, Integer e2) {
	        return e2 - e1;
	      }
	    };
		ArrayList<Integer> nums;
	    HashMap<Integer, PriorityQueue<Integer>> locs;
	    Random random = new Random();
	    /** Initialize your data structure here. */
	    public RandomizedCollection() {
	        nums = new ArrayList<Integer>();
	        locs = new HashMap<Integer, PriorityQueue<Integer>>();
	    }
	    
	    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
	    public boolean insert(int val) {
	        boolean contain = locs.containsKey(val);
	        if ( contain ) {
	        	PriorityQueue<Integer> list = locs.get(val);
	        	list.add(nums.size());
		        nums.add(val);
	        	return false;
	        }
	        PriorityQueue<Integer> list = new PriorityQueue<Integer>(cmp);
	        list.add(nums.size());
	        locs.put( val, list);
	        nums.add(val);
	        return true;
	    }
	    
	    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
	    public boolean remove(int val) {
	        boolean contain = locs.containsKey(val);
	        if ( ! contain ) return false;
	        PriorityQueue<Integer> list = locs.get(val);
	        if ( !list.contains(nums.size() - 1) ) { // not the last one than swap the last one with this val
	            int lastone = nums.get(nums.size() - 1 );
	            PriorityQueue<Integer> lastlist = locs.get(lastone);
	            nums.set( list.peek() , lastone );
	            lastlist.poll();
	            lastlist.add(list.peek());
	            locs.put(lastone, lastlist);
	        }
	        if( list.size() == 1){
		        locs.remove(val);
	        } else {
	        	list.poll();
	        	locs.put(val, list);
	        }
	        nums.remove(nums.size() - 1);
	        return true;
	    }
	    
	    /** Get a random element from the collection. */
	    public int getRandom() {
	        return nums.get( random.nextInt(nums.size()) );
	    }
	}
	
	//532. K-diff Pairs in an Array
	public int findPairs(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 0) return 0;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int num : nums){
        	map.put(num, map.getOrDefault(num, 0) + 1);
        }
        int res = 0;
        for(Entry<Integer, Integer> entry : map.entrySet() ){
        	if(k == 0 && entry.getValue() >= 2) res++;
        	else if( k != 0 && map.containsKey(entry.getKey() + k)) res ++;
        }
        return res;
    }
	
	//560. Subarray Sum Equals K
	//get sum from i to j, equals k
	//get sum from 0 to j, minus sum from 0 - i
	public int subarraySum(int[] nums, int k) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(0, 1);
		int cur = 0, res = 0;
		for(int i = 0; i < nums.length; i++){
			cur += nums[i];
			if(map.containsKey(cur - k)) res += map.get(cur - k);
			map.put(cur, map.getOrDefault(cur, 0) + 1);
		}
		return res;
    }
	
	//561. Array Partition I
	public int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        int res = 0;
        for(int i = 0; i < nums.length; i = i + 2){
        	res += nums[i];
        }
        return res;
    }
	
	//566. Reshape the Matrix
	public int[][] matrixReshape(int[][] nums, int r, int c) {
        if(nums.length == 0 || nums[0].length == 0 
        		|| nums.length * nums[0].length != r * c) return nums;
        int[] numss = new int[nums.length * nums[0].length];
        int begin = 0;
        for(int i = 0; i < nums.length; i++){
        	for(int j = 0; j < nums[0].length; j++){
        		numss[begin] = nums[i][j];
        		begin++;
        	}
        }
        int[][] res = new int[r][c];
        for(int i = 0; i < r; i++){
        	for(int j = 0; j < c; j++){
        		res[i][j] = numss[i * c + j];
        	}
        }
        return res;
    }
	
	//581. Shortest Unsorted Continuous Subarray
	public int findUnsortedSubarray(int[] A) {
		int n = A.length, max = A[0], end = -1, start = 0, min = A[n - 1];
		for(int i = 1; i < n; i++){
			if(A[i] < max) {
				end = i;
			} else max = A[i];
			if(A[n - 1 - i] > min){
				start = n - 1 - i;
			} else min = A[n - 1 - i];
		}
		return end - start + 1;
    }
	
	 
	//605. Can Place Flowers
	public boolean canPlaceFlowers(int[] flowerbed, int n) {
		boolean res = false;
		int begin = -2;
		for(int i = 0; i < flowerbed.length; i++){
			if(flowerbed[i] == 1) begin = i;
			else {
				if( i - begin == 2 && ( i == flowerbed.length - 1 || flowerbed[i + 1] == 0 )){
					n--;
					if( n == 0) return true;
					begin = i;
				}
			}
		}
		return res;
    }
	
	//611. Valid Triangle Number
	public int triangleNumber(int[] nums) {
        int res = 0;
        Arrays.sort(nums);
        for(int i = 1; i <= nums.length - 2; i++){
        	int begin = 0, end = i + 1;
        	while(begin < i && end < nums.length){
        		if(nums[begin] + nums[i] > nums[end]){
        			res += i - begin;
        			end++;
        		}else{
        			begin++;
        		}
        	}
        }
        return res;
    }
	
	//621. Task Scheduler
	public int leastInterval(char[] tasks, int n) {
		int max = 0, same = 0;
		int[] c = new int[26];
        for(char t : tasks){
            c[t - 'A']++;
        }
        for(int num : c){
        	if(num > max) {
        		max = num;
        		same = 1;
        	} else if(num == max){
        		same++;
        	}
        }
		return Math.max(tasks.length, (n + 1) * (max - 1) + same);
	}
	
	// 628. Maximum Product of Three Numbers
	public int maximumProduct(int[] nums) {
//        Arrays.sort(nums);
//        return Math.max(nums[0] * nums[1] * nums[nums.length - 1], 
//        		nums[nums.length - 1] * nums[nums.length - 2] * nums[nums.length - 3]);
	//solution 2
		int a1 = Integer.MIN_VALUE, a2 = Integer.MIN_VALUE, a3 = Integer.MIN_VALUE, 
				a_1 = Integer.MAX_VALUE, a_2 = Integer.MAX_VALUE;
		for(int a : nums){
			if( a > a3){
				a1 = a2;
				a2 = a3;
				a3 = a;
			} else if (a > a2){
				a1 = a2;
				a2 = a;
			} else if( a > a1){
				a1 = a;
			}
			if( a < a_1){
				a_2 = a_1;
				a_1 = a;
			}else if(a < a_2){
				a_2 = a;
			}
		}
		return Math.max(a1 * a2 * a3, a_1 * a_2 * a3);
    }
	
	//643. Maximum Average Subarray I
	public double findMaxAverage(int[] nums, int k) {
        int max = 0, cur = 0;
        for(int i = 0; i < k; i++){
        	max += nums[i];
        	cur += nums[i];
        }
        for(int i = k; i < nums.length; i++){
        	cur = cur + nums[i] - nums[i -k];
        	max = Math.max(max, cur);
        }
        return (double) max/k;
    }
	
	//661. Image Smoother
	public int[][] imageSmoother(int[][] M) {
		int[][] res = new int[M.length][M[0].length];
        for(int i = 0; i < M.length; i++){
        	for(int j = 0; j <M[0].length; j++){
        		int sum = 0, count = 0;
        		if(i > 0) {
        			if(j > 0) {
        				sum += M[i - 1][j - 1];
        				count++;
        			}
        			sum += M[i - 1][j];
        			count++;
        			if(j < M[0].length - 1) {
        				sum += M[i - 1][j + 1];
        				count++;
        			}
        		}
        		if(j > 0) {
        			sum += M[i][j - 1];
        			count++;
        		}
        		sum += M[i][j];
        		count++;
    			if(j < M[0].length - 1) {
    				sum += M[i][j + 1];
    				count++;
    			}
    			if(i < M.length - 1) {
        			if(j > 0) {
        				sum += M[i + 1][j - 1];
        				count++;
        			}
    				sum += M[i + 1][j];
    				count++;
    				if(j < M[0].length - 1){
    					sum += M[i + 1][j + 1];
    					count++;
    				}
    			}
    			res[i][j] = sum/count;
        	}
        }
        return res;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
