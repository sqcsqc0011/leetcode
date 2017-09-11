package leetcode_bytag;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import z_leetcode.TreeNode;

public class HashTableTag {

	//525. Contiguous Array
//	The idea is to change 0 in the original array to -1. Thus, if we find SUM[i, j] == 0 then we know there are even number of -1 and 1 between index i and j. 
//	Also put the sum to index mapping to a HashMap to make search faster.
	public int findMaxLength(int[] nums) {
        for(int i = 0; i < nums.length; i++){
        	if( nums[i] == 0) nums[i] = -1;
        }
        //sum, index
        int sum = 0, max = 0;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, 0);
        for(int i = 0; i < nums.length; i++){
        	sum += nums[i];
        	if( map.containsKey(sum) ){
        		max = Math.max(max, i + 1 - map.get(sum));
        	} else map.put(sum, i + 1);
        }
        return max;
    }
	
	//508. Most Frequent Subtree Sum
	public int[] findFrequentTreeSum(TreeNode root) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        getNodeSum(root, map);
        List<Integer> result = new ArrayList<Integer>();
        int max = 0;
        for(int key : map.keySet()){
        	if( map.get(key) > max){
        		max = map.get(key);
        		result = new ArrayList<Integer>();
        		result.add(key);
        	} else if( map.get(key) == max) result.add(key);
        }
        int[] res = new int[result.size()];
        for(int i = 0; i < result.size(); i++){
        	res[i] = result.get(i);
        }
        return res;
    }

	private int getNodeSum(TreeNode root, HashMap<Integer, Integer> map) {
		if(root == null) return 0;
		int nodeSum = getNodeSum(root.left, map) + getNodeSum(root.right, map) + root.val;
		if( map.containsKey(nodeSum)) map.put(nodeSum, map.get(nodeSum) + 1);
		else map.put(nodeSum, 1);
		return nodeSum;
	}
	
	//500. Keyboard Row
	public String[] findWords(String[] words) {
		HashMap<Character, Integer> rows = new HashMap<>();
		
		rows.put('q', 1);rows.put('w', 1);rows.put('e', 1);rows.put('r', 1);rows.put('t', 1);rows.put('y', 1);rows.put('u', 1);rows.put('i', 1);rows.put('o', 1);rows.put('p', 1);        
		rows.put('a', 2);rows.put('s', 2);rows.put('d', 2);rows.put('f', 2);rows.put('g', 2);rows.put('h', 2);rows.put('j', 2);rows.put('k', 2);rows.put('l', 2);
		rows.put('z', 3);rows.put('x', 3);rows.put('c', 3);rows.put('v', 3);rows.put('b', 3);rows.put('n', 3);rows.put('m', 3);
        
        List<String> result = new ArrayList<>();
        for(int j = 0; j < words.length; j++) {
        	String word = words[j].toLowerCase();
            int row = rows.get(word.charAt(0));
            boolean inRow = true;
            for( int i = 1; i < word.length(); i++){
            	if( rows.get(word.charAt(i)) != row ) {
            		inRow = false;
            		break;
            	}
            }
            if(inRow) result.add(words[j]);
        }
        return result.toArray(new String[result.size()]);
    }
	
	//463. Island Perimeter
	//get all edges for all islands, minus 2*connected islands
	public int islandPerimeter(int[][] grid) {
        int res = 0;
        for(int i = 0; i < grid.length; i++){
        	for(int j = 0; j < grid[0].length; j++){
        		if(grid[i][j] == 1){
        			res += 4;
        			if(i >= 1 && grid[i - 1][j] == 1) res -= 2;
        			if(j >= 1 && grid[i][j - 1] == 1) res -= 2;
        		}
        	}
        }
		return res;
    }
	
	//454. 4Sum II
	public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
		//sum count
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	    int res=0;
	    
	    for(int i = 0; i < C.length; i++) {
	        for(int j = 0; j < D.length; j++) {
	            int sum = C[i] + D[j];
	            if( map.containsKey(sum))
	            	map.put(sum, map.get(sum) + 1);
	            else map.put(sum, 1);
	        }
	    }
	    for(int i = 0; i < A.length; i++) {
	        for(int j = 0; j < B.length; j++) {
	            int sum2 = A[i] + B[j];
	            if( map.containsKey(-sum2)) res += map.get(-sum2);
	        }
	    }
	    return res;
    }
	
	//451. Sort Characters By Frequency
	public String frequencySort(String s) {
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		for(int i = 0; i < s.length(); i++){
			char ch = s.charAt(i);
			if( map.containsKey(ch)) map.put(ch, map.get(ch) + 1);
			else map.put(ch, 1);
		}
		PriorityQueue<Map.Entry<Character, Integer>> pq = new PriorityQueue<>(
		    new Comparator<Map.Entry<Character, Integer>>() {
		        @Override
		        public int compare(Map.Entry<Character, Integer> a, Map.Entry<Character, Integer> b) {
		            return b.getValue() - a.getValue();
		        }
		    }
		);
		pq.addAll(map.entrySet());
		StringBuilder sb = new StringBuilder();
		while (!pq.isEmpty()) {
		    Map.Entry<Character, Integer> e = pq.poll();
		    for (int i = 0; i < (int)e.getValue(); i++) {
		        sb.append(e.getKey());
		    }
		}
		return sb.toString();
	}
	
	//447. Number of Boomerangs
	public int numberOfBoomerangs(int[][] points) {
		int result = 0;
		for(int i = 0; i < points.length; i++){
	        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	        int[] point = points[i];
	        for(int j = 0; j < points.length; j++){
	        	if(j == i) continue;
	        	int distance = (points[j][0] - point[0]) * (points[j][0] - point[0]) + (points[j][1] - point[1]) * (points[j][1] - point[1]);
	            map.put(distance, map.getOrDefault(distance, 0) + 1);
	        }
	        for(int value : map.values()){
	        	result += value * (value - 1);
	        }
		}
		return result;
    }
	
	//438. Find All Anagrams in a String
	//Sliding Window algorithm
	public List<Integer> findAnagrams(String s, String p) {
		List<Integer> list = new ArrayList<>();
		if (s == null || s.length() == 0 || p == null || p.length() == 0) return list;
	    int[] hash = new int[256]; //character hash
	    //record each character in p to hash
	    for (char c : p.toCharArray()) {
	        hash[c]++;
	    }
	    //two points, initialize count to p's length
	    int left = 0, right = 0, count = p.length();
	    while (right < s.length()) {
	        //move right everytime, if the character exists in p's hash, decrease the count
	        //current hash value >= 1 means the character is existing in p
	        if (hash[s.charAt(right)] >= 1) count--;
	        hash[s.charAt(right)]--;
	        right++;
	        //when the count is down to 0, means we found the right anagram
	        //then add window's left to result list
	        if (count == 0) list.add(left);
	        //if we find the window's size equals to p, then we have to move left (narrow the window) to find the new match window
	        //++ to reset the hash because we kicked out the left
	        //only increase the count if the character is in p
	        //the count >= 0 indicate it was original in the hash, cuz it won't go below 0
	        if (right - left == p.length() ) {
	            if (hash[s.charAt(left)] >= 0) {
	                count++;
	            }
	            hash[s.charAt(left)]++;
	            left++;
	        } 
	    }
	    return list;	    
    }
	
	//409. Longest Palindrome
	public int longestPalindrome(String s) {
		Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            if (set.contains(c)) set.remove(c);
            else set.add(c);
        }

        int odd = set.size();
        return s.length() - (odd == 0 ? 0 : odd - 1);
    }
	
	//389. Find the Difference
	public char findTheDifference(String s, String t) {
		HashMap<Character, Integer> map = new HashMap<>();
		for(int i = 0; i < s.length(); i++){
			char sch = s.charAt(i);
			char tch = t.charAt(i);
			if(map.containsKey(sch)) map.put(sch, map.get(sch) + 1);
			else map.put(sch, 1);
			if(map.containsKey(tch)) map.put(tch, map.get(tch) - 1);
			else map.put(tch, -1);
		}
		char tch = t.charAt(t.length() - 1);
		if(map.containsKey(tch)) map.put(tch, map.get(tch) - 1);
		else map.put(tch, -1);
		for(char key : map.keySet()){
			if( map.get(key) != 0)
				tch = key;
		}
		return tch;
    }
	
	//355. Design Twitter
	public class Twitter {
	    Map<Integer, Set<Integer>> fans = new HashMap<>();
	    Map<Integer, LinkedList<Tweet>> tweets = new HashMap<>();
	    int cnt = 0;

	    public void postTweet(int userId, int tweetId) {
	        if (!fans.containsKey(userId)) fans.put(userId, new HashSet<>());
	        fans.get(userId).add(userId);
	        if (!tweets.containsKey(userId)) tweets.put(userId, new LinkedList<>());
	        tweets.get(userId).addFirst(new Tweet(cnt++, tweetId));
	    }

	    public List<Integer> getNewsFeed(int userId) {
	        if (!fans.containsKey(userId)) return new LinkedList<>();
	        PriorityQueue<Tweet> feed = new PriorityQueue<>((t1, t2) -> t2.time - t1.time);
	        fans.get(userId).stream()
	            .filter(f -> tweets.containsKey(f))
	            .forEach(f -> tweets.get(f).forEach(feed::add));
	        List<Integer> res = new LinkedList<>();
	        while (feed.size() > 0 && res.size() < 10) res.add(feed.poll().id);
	        return res;
	    }

	    public void follow(int followerId, int followeeId) {
	        if (!fans.containsKey(followerId)) fans.put(followerId, new HashSet<>());
	        fans.get(followerId).add(followeeId);
	    }

	    public void unfollow(int followerId, int followeeId) {
	        if (fans.containsKey(followerId) && followeeId != followerId) fans.get(followerId).remove(followeeId);
	    }

	    class Tweet {
	        int time;
	        int id;

	        Tweet(int time, int id) {
	            this.time = time;
	            this.id = id;
	        }
	    }
	}
	
	//349. Intersection of Two Arrays
	public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<Integer>();
        Set<Integer> set2 = new HashSet<Integer>();
        for(int i = 0; i < nums1.length; i++){
        	set1.add(nums1[i]);
        }
        for(int j = 0; j < nums2.length; j++){
        	if( set1.contains(nums2[j]))
        		set2.add(nums2[j]);
        }
        int[] res = new int[set2.size()];
        int i = 0;
        for(int key : set2){
        	res[i] = key;
        	i++;
        }
        return res;
    }
	
	//350. Intersection of Two Arrays II
	public int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map1 = new HashMap<Integer, Integer>();
        List<Integer> res = new ArrayList<Integer>();
        for(int i = 0; i < nums1.length; i++){
        	if( map1.containsKey(nums1[i])) map1.put(nums1[i], map1.get(nums1[i]) + 1);
        	else map1.put(nums1[i], 1);
        }
        for(int j = 0; j < nums2.length; j++){
        	if( map1.containsKey(nums2[j])){
        		res.add(nums2[j]);
        		int newCount = map1.get(nums2[j]) - 1;
        		if( newCount == 0) map1.remove(nums2[j]);
        		else map1.put(nums2[j], newCount);
        	}
        }
        int[] result = new int[res.size()];
        for(int i = 0; i < res.size(); i++){
        	result[i] = res.get(i);
        }
        return result;
    }
	
	//347. Top K Frequent Elements
	public List<Integer> topKFrequent(int[] nums, int k) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int i = 0; i < nums.length; i++){
			if(map.containsKey(nums[i])) map.put(nums[i], map.get(nums[i]) + 1);
			else map.put(nums[i], 1);
		}
		//由大到小排列
		PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(
		    new Comparator<Map.Entry<Integer, Integer>>() {
		        @Override
		        public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
		            return b.getValue() - a.getValue();
		        }
		    }
		);
		for(Map.Entry<Integer, Integer> entry : map.entrySet()){
			pq.add(entry);
		}
		List<Integer> res = new ArrayList<Integer>();
		for(int i = 1; i <=k; i++){
			res.add(pq.poll().getKey());
		}
		return res;
    }
	
	//336. Palindrome Pairs
	public List<List<Integer>> palindromePairs(String[] words) {
		List<List<Integer>> ret = new ArrayList<>(); 
	    if (words == null || words.length < 2) return ret;
	    Map<String, Integer> map = new HashMap<String, Integer>();
	    for (int i=0; i<words.length; i++) map.put(words[i], i);
	    for (int i=0; i<words.length; i++) {
	        for (int j=0; j<=words[i].length(); j++) { // notice it should be "j <= words[i].length()"
	            String str1 = words[i].substring(0, j);
	            String str2 = words[i].substring(j);
	            if (isPalindrome(str1)) {
	                String str2rvs = new StringBuilder(str2).reverse().toString();
	                if (map.containsKey(str2rvs) && map.get(str2rvs) != i) {
	                    List<Integer> list = new ArrayList<Integer>();
	                    list.add(map.get(str2rvs));
	                    list.add(i);
	                    ret.add(list);
	                }
	            }
	            if (isPalindrome(str2)) {
	                String str1rvs = new StringBuilder(str1).reverse().toString();
	                if (map.containsKey(str1rvs) && map.get(str1rvs) != i && str2.length()!=0) { 
	                    List<Integer> list = new ArrayList<Integer>();
	                    list.add(i);
	                    list.add(map.get(str1rvs));
	                    ret.add(list);
	                }
	            }
	        }
	    }
	    return ret;
    }
	
	private boolean isPalindrome(String str) {
	    int left = 0;
	    int right = str.length() - 1;
	    while (left <= right) {
	        if (str.charAt(left++) !=  str.charAt(right--)) return false;
	    }
	    return true;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
