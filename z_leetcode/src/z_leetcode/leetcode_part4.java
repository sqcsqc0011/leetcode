package z_leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class leetcode_part4 {

	//151. Reverse Words in a String
	public String reverseWords(String s) {
		String[] parts = s.trim().split("\\s+");
		String out = "";
		for (int i = parts.length - 1; i > 0; i--) {
		    out += parts[i] + " ";
		}
		return out + parts[0];
    }
	
	//152. Maximum Product Subarray
	//有可能是负数， 记录max和min
	public int maxProduct(int[] nums) {
        if( nums == null || nums.length == 0) return 0;
        int result = nums[0], max = nums[0], min = nums[0];
        for(int i = 1; i < nums.length; i++){
        	int productmax = max * nums[i];
        	int productmin = min * nums[i];
        	max = Math.max(Math.max(productmax, productmin), nums[i]);
        	min = Math.min(Math.min(productmax, productmin), nums[i]);
        	result = Math.max(max, result);
        }
        return result;
    }
	
	//153. Find Minimum in Rotated Sorted Array
	public int findMin(int[] nums) {
//        Arrays.sort(nums);
//        return nums[0];
		for(int i = 1; i < nums.length; i++){
			if(nums[i] < nums[i - 1]) return nums[i]; 
		}
		return nums[0];
    }
	
	//154. Find Minimum in Rotated Sorted Array II with duplicates
	public int findMinII(int[] nums) {
		for(int i = 1; i < nums.length; i++){
			if(nums[i] < nums[i - 1]) return nums[i]; 
		}
		return nums[0];  
	}
	
	//155. Min Stack
	public class MinStack {
	    long min;
	    Stack<Long> stack;

	    public MinStack(){
	        stack=new Stack<>();
	    }
	    
	    public void push(int x) {
	        if (stack.isEmpty()){
	            stack.push(0L);
	            min=x;
	        }else{
	            stack.push(x-min);//Could be negative if min value needs to change
	            if (x<min) min=x;
	        }
	    }

	    public void pop() {
	        if (stack.isEmpty()) return;
	        long pop=stack.pop();
	        if (pop<0)  min=min-pop;//If negative, increase the min value
	    }

	    public int top() {
	        long top=stack.peek();
	        if (top>0){
	            return (int)(top+min);
	        }else{
	           return (int)(min);
	        }
	    }

	    public int getMin() {
	        return (int)min;
	    }
	}
	
	//160. Intersection of Two Linked Lists
	public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
	    int lenA = getNodeLength(headA), lenB = getNodeLength(headB);
	    // move headA and headB to the same start point
	    while (lenA > lenB) {
	        headA = headA.next;
	        lenA--;
	    }
	    while (lenA < lenB) {
	        headB = headB.next;
	        lenB--;
	    }
	    // find the intersection until end
	    while (headA != headB) {
	        headA = headA.next;
	        headB = headB.next;
	    }
	    return headA;
	}

	private int getNodeLength(ListNode node) {
	    int length = 0;
	    while (node != null) {
	        node = node.next;
	        length++;
	    }
	    return length;
	}
	
	//162. Find Peak Element
	//O(n)
//	public int findPeakElement(int[] nums) {
//        for(int i = 0; i < nums.length; i++){
//        	if( i == 0 && nums.length >= 1) {
//        		if (nums[0] > nums[1]) return 0;
//        	}
//        	else if( i == nums.length - 1 && nums.length - 1 > 0){
//        		if( nums[nums.length - 1] > nums[nums.length - 2]) return nums.length - 1;
//        	}
//        	else if( i > 0 && i < nums.length - 1 && nums[i] > nums[i - 1] && nums[i] > nums[i + 1]) return i;
//        }
//        return 0;
//    }
	
	//O(logN) 二分法
	public int findPeakElement(int[] nums) {
		return getPeak(nums, 0, nums.length - 1);
	}

	private int getPeak(int[] nums, int left, int right) {
		if( right - left <= 1) return (nums[left] > nums[right]) ? left : right;
		int mid = (right - left)/2 + left;
		if( nums[mid] > nums[mid + 1]) return getPeak(nums, left, mid);
		else return getPeak(nums, mid + 1, nums.length - 1);
	}
	
	
	//164. Maximum Gap
	//桶排序
	//HARD!!!!!!!!!!NOT FIXED!!!!!!!
	public int maximumGap(int[] nums) {
        Arrays.sort(nums);
        int result = 0;
        for(int i = 1; i < nums.length; i++){
        	if( nums[i] - nums[i - 1] > result)
        		result = nums[i] - nums[i - 1];
        }
        return result;
    }
	
	
	//165. Compare Version Numbers
	//因为split方法输入的是一个正则表达式所以不能直接用.，而是要用\.，而java的\要转义，所有要用\\.
	public int compareVersion(String version1, String version2) {
        // 按照.进行分割
        String[] v1 = version1.split("\\.");
        String[] v2 = version2.split("\\.");
        int i = 0;
        // 比对相应的子串
        for(; i < v1.length && i < v2.length; i++){
            int val1 = Integer.parseInt(v1[i]);
            int val2 = Integer.parseInt(v2[i]);
            if(val1 < val2) return -1;
            if(val1 > val2) return 1;
        }
        // 如果某个版本号更长，判断其多余部分是否是0，如果不是0，则较长的较大，否则是一样的。
        if(v2.length > v1.length){
            for(; i < v2.length; i++){
                int val = Integer.parseInt(v2[i]);
                if(val != 0) return -1;
            }
        } else if(v1.length > v2.length){
            for(; i < v1.length; i++){
                int val = Integer.parseInt(v1[i]);
                if(val != 0) return 1;
            }
        }
        return 0;
    }
	
	//166. Fraction to Recurring Decimal
	public String fractionToDecimal(int numerator, int denominator) {
        if (numerator == 0)	return "0";
        StringBuilder res = new StringBuilder();
        // "+" or "-"
        res.append(((numerator > 0) ^ (denominator > 0)) ? "-" : "");
        long num = Math.abs((long)numerator);
        long den = Math.abs((long)denominator);
        
        // integral part
        res.append(num / den); //整数部分
        num %= den;//余数
        if (num == 0)  return res.toString();
        
        // fractional part
        //用哈希表记录重复出现的部分
        res.append(".");
        HashMap<Long, Integer> map = new HashMap<Long, Integer>();
        map.put(num, res.length());
        while (num != 0) {
            num *= 10;
            res.append(num / den);
            num %= den;
            if (map.containsKey(num)) {
                int index = map.get(num);
                res.insert(index, "(");
                res.append(")");
                break;
            }
            else {
                map.put(num, res.length());
            }
        }
        return res.toString();
    }

	//167. Two Sum II - Input array is sorted
	public int[] twoSum(int[] nums, int target) {
        int[] result = {1, nums.length};
        int left = 0, right = nums.length - 1;
        while(left < right){
        	if( nums[left] + nums[right] == target){
        		result[0] = left + 1;
        		result[1] = right + 1;
        		return result;
        	}
        	if( nums[left] + nums[right] < target) left++;
        	if( nums[left] + nums[right] > target) right--;
        }
        return result;
    }
	
	//168. Excel Sheet Column Title
	public String convertToTitle(int n) {
        StringBuilder result = new StringBuilder();
        while(n > 0){
            n--;//1:A, 2:B, 3:B
            result.insert(0, (char)('A' + n % 26));
            n /= 26;
        }
        return result.toString();
    }
	
	//169. Majority Element
//	public int majorityElement(int[] nums) {
//        Arrays.sort(nums);
//        return nums[nums.length/2];
//    }	
	public int majorityElement(int[] num) {
        int major = num[0], count = 1;
        for(int i = 1; i < num.length; i++){
            if(count == 0){
                count++;
                major = num[i];
            }else if(major == num[i]){
                count++;
            }else count--;
            
        }
        return major;
    }
	
	//171. Excel Sheet Column Number
	public int titleToNumber(String s) {
		int num = 0;
        for(int i = 0; i < s.length(); i++){
        	char ch = s.charAt(i);
        	num = (int)(ch - 'A') + 1 + num*26;
        }
        return num;
    }
	
	//172. Factorial Trailing Zeroes
	public int trailingZeroes(int n) {
        int i = 1;
        int result = 0;
        while(Math.pow(5, i) <= n){
        	result += n/Math.pow(5, i);
        	i++;
        }
        return result;
    }
	
	//173. Binary Search Tree Iterator
	public class BSTIterator {
	    private Queue<TreeNode> queue;
	    private void dfs(TreeNode root, Queue<TreeNode> queue){
	        if(root == null){
	            return;
	        }
	        dfs(root.left, queue);
	        queue.add(root);
	        dfs(root.right, queue);
	        
	    }
	    
	    public BSTIterator(TreeNode root) {
	        queue = new LinkedList<TreeNode>();
	        dfs(root,queue);
	    }
	    
	    /** @return whether we have a next smallest number */
	    public boolean hasNext() {
	        return !queue.isEmpty();
	    }

	    /** @return the next smallest number */
	    public int next() {
	        TreeNode node = queue.poll();
	        return node.val;
	    }
	}
	
	//174. Dungeon Game
	//从结尾开始往前倒推
	//递归方程是dp[i][j] = max(1, min(dp[i+1][j], dp[i][j+1]) - dungeon[i][j]). 
    public int calculateMinimumHP(int[][] dungeon) {
		if(dungeon.length == 0 || dungeon[0].length == 0) return 1;
		int m = dungeon.length - 1, n = dungeon[0].length - 1;
	    int[][] dp = new int[m + 1][n + 1];
	    dp[m][n] = (dungeon[m][n] > 0) ? 1 : 1 - dungeon[m][n];
	    for( int i = m - 1; i >= 0; i--){
	    	dp[i][n] = Math.max(1, dp[i + 1][n] - dungeon[i][n]);
	    }
	    for( int j = n - 1; j >= 0; j--){
	    	dp[m][j] = Math.max(1, dp[m][j + 1] - dungeon[m][j]);
	    }
	    for( int i = m - 1; i >= 0; i--){
		    for( int j = n - 1; j >= 0; j--){
		    	int minNext = Math.min(dp[i + 1][j], dp[i][j + 1]);
		    	dp[i][j] = Math.max(1, minNext - dungeon[i][j]);
		    }
	    }
	    return dp[0][0];
	}
	
    //179. Largest Number
    class NumbersComparator implements Comparator<String> {
    	@Override
    	public int compare(String s1, String s2) {
    		return (s2 + s1).compareTo(s1 + s2);
    	}
    }

    public String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            strs[i] = Integer.toString(nums[i]);
        }
        Arrays.sort(strs, new NumbersComparator());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            sb.append(strs[i]);
        }
        String result = sb.toString();
        int index = 0;
        while (index < result.length() && result.charAt(index) == '0') {
            index++;
        }
        
        if (index == result.length()) return "0";
        return result.substring(index);
    }
	
	//187. Repeated DNA Sequences
    public List<String> findRepeatedDnaSequences(String s) {
    	List<String> res = new LinkedList<String>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for(int index = 10; index <= s.length(); index++){
            // 从第10位开始作为结尾，位移一位，比较一次子串
            String substr = s.substring(index - 10, index);
            if(map.containsKey(substr)){
                // 如果是第一次遇到，则加入结果
                if(map.get(substr) == 1){
                    res.add(substr);
                }
                // 标记为已经遇到过一次了
                map.put(substr, 2);
            } else {
                // 如果不存在，则加入表中
                map.put(substr, 1);
            }
        }
        return res;
    }	
	
	//188. Best Time to Buy and Sell Stock IV
//    我们定义local[i][j]为在到达第i天时最多可进行j次交易并且最后一次交易在最后一天卖出的最大利润，此为局部最优。然后我们定义global[i][j]为在到达第i天时最多可进行j次交易的最大利润，此为全局最优。它们的递推式为：
//    local[i][j] = max(global[i - 1][j - 1] + max(diff, 0), local[i - 1][j] + diff)
//    global[i][j] = max(local[i][j], global[i - 1][j])，
//    其中局部最优值是比较前一天并少交易一次的全局最优加上大于0的差值，和前一天的局部最优加上差值后相比，两者之中取较大值，而全局最优比较局部最优和前一天的全局最优。
    public int maxProfit(int k, int[] prices) {
    	//如果k的值远大于prices的天数， 直接算每次操作最优
    	if( k > prices.length - 1){
    		int result = 0;
    		for( int i = 1; i < prices.length; i++){
    			if( prices[i] > prices[i - 1])
    				result += prices[i] - prices[i - 1];
    		}
    		return result;
    	}
    	int[][] local = new int[prices.length][k + 1];
    	int[][] global =new int[prices.length][k + 1];
    	for( int i = 1; i < prices.length; i++){
    		int diff = prices[i] - prices[i - 1];
    		for( int j = 1; j <= k && j <= i; j++){
    			local[i][j] = Math.max(global[i - 1][j - 1] + Math.max(diff, 0),  local[i - 1][j] + diff);
    			global[i][j] = Math.max(local[i][j], global[i - 1][j]);
    		}
    	}
    	return global[prices.length - 1][k];
    }

    //189. Rotate Array
    public void rotate(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
	
	//190. Reverse Bits
    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result += n & 1;
            n >>>= 1;   // CATCH: must do unsigned shift
            if (i < 31) // CATCH: for last digit, don't shift!
                result <<= 1;
        }
        return result;
    }

	//191. Number of 1 Bits
    public int hammingWeight(int n) {
    	int ones = 0;
    	while(n != 0) {
    		ones = ones + (n & 1);
    		n = n>>>1;
    	}
    	return ones;
    }
	
	//198. House Robber
    public int robI(int[] num) {
        int prevNo = 0;
        int prevYes = 0;
        for (int n : num) {
            int temp = prevNo;
            prevNo = Math.max(prevNo, prevYes);
            prevYes = n + temp;
        }
        return Math.max(prevNo, prevYes);
    }
	
	//199. Binary Tree Right Side View
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        addrightSide(result, root, 0);     
        return result;
    }

	private void addrightSide(List<Integer> result, TreeNode root, int i) {
		if(root == null) return;
		if(result.size() <= i)
			result.add(root.val);
		else result.set(i, root.val);
		addrightSide(result, root.left, i + 1);
		addrightSide(result, root.right, i + 1);
	}
	
	//200 Number of Islands
	//DFS、BFS。只要遍历一遍，碰到一个1，就把它周围所有相连的1都标记为非1，这样整个遍历过程中碰到的1的个数就是所求解。
	public int numIslands(char[][] grid) {
		if(grid.length == 0 || grid[0].length == 0) return 0;
		int result = 0;
        for(int i = 0; i < grid.length; i++){
        	for( int j = 0; j < grid[0].length; j++){
        		if( grid[i][j] == '1'){
        			checkAllConnectOnes(grid, i, j);
        			result++;
        		}
        	}
        }
        return result;
	}

	private void checkAllConnectOnes(char[][] grid, int i, int j) {
		if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) return;  
        if (grid[i][j] == '1') {  
            grid[i][j] = 'x';  
            checkAllConnectOnes(grid, i - 1, j);  
            checkAllConnectOnes(grid, i + 1, j);  
            checkAllConnectOnes(grid, i, j - 1);  
            checkAllConnectOnes(grid, i, j + 1);  
        }  
	}
	
	//201. Bitwise AND of Numbers Range
//	1.last bit of (odd number & even number) is 0.
//	2.when m != n, There is at least an odd number and an even number, so the last bit position result is 0.
//	3.Move m and n rigth a position.
//	Keep doing step 1,2,3 until m equal to n, use a factor to record the iteration time.
	public int rangeBitwiseAnd(int m, int n) {
        if(m == 0) return 0;
        int moveFactor = 1;
        while(m != n){
            m >>= 1;
            n >>= 1;
            moveFactor <<= 1;
        }
        return m * moveFactor;
    }
	
	//202. Happy Number
	public boolean isHappy(int n) {
		Set<Integer> sets = new HashSet<Integer>();
		while( n > 1){
			int cur = n;
			n = 0;
			while( cur/10 > 0){
				n += Math.pow(cur%10, 2);
				cur = cur/10;
			}
			n += Math.pow(cur%10, 2);
			if( n == 1) return true;
			if( sets.contains(n)) return false;
			else sets.add(n);
		}
		return true;
    }
	
	//203. Remove Linked List Elements
	public ListNode removeElements(ListNode head, int val) {
        ListNode newNode = new ListNode(0);
        newNode.next = head;
        ListNode cur = newNode;
        while( cur != null && cur.next != null){
        	if(cur.next.val == val){
        		cur.next = cur.next.next;
        	}
        	else cur = cur.next;
        }
        return newNode.next;
    }
	
	//204. Count Primes
	public int countPrimes(int n) {
		int result = 0;
		boolean[] isNotPrime = new boolean[n];
        for( int i = 2; i < n; i++){
        	if(!isNotPrime[i]){
        		result++;
        		for(int j = 2; j * i < n; j++){
        			isNotPrime[i * j] = true;
        		}
        	}
        }
        return result;
    }
	
	//205. Isomorphic Strings
	public boolean isIsomorphic(String s, String t) {
        if( s.length() != t.length()) return false;
        HashMap<Character, Character> map = new HashMap<Character, Character>();
        for( int i = 0; i < s.length(); i++){
    		if( !map.containsKey(s.charAt(i)) && !map.containsValue(t.charAt(i)))
    			map.put(s.charAt(i), t.charAt(i));
    		else {
    			if( !map.containsKey(s.charAt(i)) || map.get(s.charAt(i)) != t.charAt(i) )
    				return false;
    		}
        }
        return true;
    }
	
	//206. Reverse Linked List
	public ListNode reverseList(ListNode head) {
		ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        head = prev;
        return head;
    }	
	
	//207. Course Schedule
	//拓扑排序
	public boolean canFinish(int numCourses, int[][] prerequisites) {
		if (numCourses <= 0) return false;
		Stack<Integer> st = new Stack<Integer>();
		int[] InDegrees = new int[numCourses];
		for(int i = 0; i < prerequisites.length; i++){
			InDegrees[prerequisites[i][0]]++;
		}
		for(int i = 0; i < InDegrees.length; i++){
			if(InDegrees[i] == 0 ) st.push(i);
		}
		while( !st.isEmpty() ){
			int pre = st.pop();
			for(int i = 0; i < prerequisites.length; i++){
				if( prerequisites[i][1] == pre){
					InDegrees[prerequisites[i][0]]--;
					if(InDegrees[prerequisites[i][0]] == 0 ) st.push(prerequisites[i][0]);
				}
			}
		}
		for (int i = 0; i < InDegrees.length; i++) {
			if (InDegrees[i] != 0)
				return false;
		}
		return true;
    }
	
	//208. Implement Trie (Prefix Tree)
	//no idea...
	
	//209. Minimum Size Subarray Sum
	//left and right, right右移，找到边界，left右移，找到最小边界
	public int minSubArrayLen(int s, int[] nums) {
		int res = nums.length + 1, left = 0, right = 0, count = 0;
        while( right < nums.length){
        	while( right < nums.length && count < s){
        		count += nums[right];
        		right++;
        	}
        	while( count >= s){
        		count -= nums[left];
        		left++;
        	}
        	res = Math.min(res, right - left + 1);
        }
        return (res == nums.length + 1) ? 0 : res;
    }
	
	//210 Course Schedule II  
	public int[] findOrder(int numCourses, int[][] prerequisites) {
		List<Integer> res = new ArrayList<Integer>();
		if (numCourses <= 0) return new int[0];
		Stack<Integer> st = new Stack<Integer>();
		int[] InDegrees = new int[numCourses];
		for(int i = 0; i < prerequisites.length; i++){
			InDegrees[prerequisites[i][0]]++;
		}
		for(int i = 0; i < InDegrees.length; i++){
			if(InDegrees[i] == 0 ) st.push(i);
		}
		while( !st.isEmpty() ){
			int pre = st.pop();
			res.add(pre);
			for(int i = 0; i < prerequisites.length; i++){
				if( prerequisites[i][1] == pre){
					InDegrees[prerequisites[i][0]]--;
					if(InDegrees[prerequisites[i][0]] == 0 ) st.push(prerequisites[i][0]);
				}
			}
		}
		if( res.size() != numCourses) return new int[0];
		int[] result = new int[numCourses];
		for(int i = 0; i < res.size(); i++) result[i] = res.get(i);
		return result;
    }

	//211. Add and Search Word - Data structure design
	//not fixed
	
	//212. Word Search II
	//use trienode prefix tree 
	
	//213. House Robber II
	public int rob(int[] nums) {
		if( nums.length == 1) return nums[0];
        int preYes = 0, preNo = 0;
        for( int i = 0; i < nums.length - 1; i++){
        	int tmp = preYes;
        	preYes = preNo + nums[i];
        	preNo = Math.max(tmp, preNo);
        }
        int result1 = Math.max(preYes, preNo);
        preYes = 0; preNo = 0;
        for( int i = 1; i < nums.length; i++){
        	int tmp = preYes;
        	preYes = preNo + nums[i];
        	preNo = Math.max(tmp, preNo);
        }
        int result2 = Math.max(preYes, preNo);
        return Math.max(result1, result2);
    }
	
	//214. Shortest Palindrome
	//hard!!!!!!!!!!!!!!NOT FIXED!!!!!!KMP!!!!!!
	public String shortestPalindrome(String s) {
	    String temp = s + "#" + new StringBuilder(s).reverse().toString();
	    int[] table = getTable(temp);
	    //get the maximum palin part in s starts from 0
	    return new StringBuilder(s.substring(table[table.length - 1])).reverse().toString() + s;
	}

	public int[] getTable(String s){
	    //get lookup table
	    int[] table = new int[s.length()];
	    //pointer that points to matched char in prefix part   
	    int index = 0;
	    //skip index 0, we will not match a string with itself
	    for(int i = 1; i < s.length(); i++){
	        if(s.charAt(index) == s.charAt(i)){
	            //we can extend match in prefix and postfix
	            table[i] = table[i-1] + 1;
	            index ++;
	        }else{
	            //match failed, we try to match a shorter substring
	            //by assigning index to table[i-1], we will shorten the match string length, and jump to the 
	            //prefix part that we used to match postfix ended at i - 1
	            index = table[i-1];
	            while(index > 0 && s.charAt(index) != s.charAt(i)){
	                //we will try to shorten the match string length until we revert to the beginning of match (index 1)
	                index = table[index-1];
	            }
	            //when we are here may either found a match char or we reach the boundary and still no luck
	            //so we need check char match
	            if(s.charAt(index) == s.charAt(i)){
	                //if match, then extend one char 
	                index ++ ;
	            }
	            table[i] = index;
	        }
	    }
	    return table;
	}
	
	//215. Kth Largest Element in an Array
	public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }
	
	//216. Combination Sum III
	public List<List<Integer>> combinationSum3(int k, int n) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		backTracking(result, new ArrayList<Integer>(), k, n);
		return result;
    }

	private void backTracking(List<List<Integer>> result, ArrayList<Integer> oneCombination, int k, int n) {
		if( k == 0 && n == 0){
			result.add(new ArrayList<Integer>(oneCombination));
			return;
		}
		int i = 1;
		if( oneCombination.size() > 0) i = oneCombination.get(oneCombination.size() - 1) + 1;
		for( ; i <= 9; i++){
			oneCombination.add(i);
			backTracking(result, oneCombination, k - 1, n - i);
			oneCombination.remove(oneCombination.size() - 1);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
