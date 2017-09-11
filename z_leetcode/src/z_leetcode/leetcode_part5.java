package z_leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class leetcode_part5 {

	//217. Contains Duplicate
	public boolean containsDuplicate(int[] nums) {
        Arrays.sort(nums);
        for(int i = 1; i < nums.length; i++){
        	if( nums[i] == nums[i - 1] ) return true;
        }
        return false;
    }
	
	//218. The Skyline Problem
	//not fixed!!!!!!!!!hard
//	(1) 自建一个名为Height的数据结构，保存一个building的index和height。约定，当height为负数时表示这个高度为height的building起始于index；height为正时表示这个高度为height的building终止于index。
//	(2) 对building数组进行处理，每一行[ Li, Ri, Hi ]，根据Height的定义，转换为两个Height的对象，即，Height(Li, -Hi) 和 Height(Ri, Hi)。 将这两个对象存入heights这个List中。
//	(3) 写个Comparator对heights进行升序排序，首先按照index的大小排序，若index相等，则按height大小排序，以保证一栋建筑物的起始节点一定在终止节点之前。
//	(4) 将heights转换为结果。使用PriorityQueue对高度值进行暂存。遍历heights，遇到高度为负值的对象时，表示建筑物的起始节点，此时应将这个高度加入PriorityQueue。 //PriorityQueue是个基于优先级堆的极大优先级队列。
//	遇到高度为正值的对象时，表示建筑物的终止节点，此时应将这个高度从PriorityQueue中除去。
//	且在遍历的过程中检查，当前的PriorityQueue的peek()是否与上一个iteration的peek()值（prev）相同，若否，则应在结果中加入[当前对象的index, 当前PriorityQueue的peek()]，并更新prev的值。
	public List<int[]> getSkyline(int[][] buildings) {
		List<int[]> result = new ArrayList<int[]>();
		List<int[]> heights = new ArrayList<int[]>();
		for(int[] building : buildings){
			heights.add(new int[]{building[0], -building[2]});
			heights.add(new int[]{building[1], building[2]});
		}
		//collection排序heights
		Collections.sort(heights, new Comparator<int[]>() {
			@Override
			public int compare(int[] h1, int[] h2) {
                return h1[0] != h2[0] ? h1[0] - h2[0] : h1[1] - h2[1];
			}
        });
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(1000, Collections.reverseOrder());
        pq.offer(0);
        int prev = 0;
        for (int[] h : heights) {
            if (h[1] < 0) {
                pq.offer(-h[1]);
            } else {
                pq.remove(h[1]);
            }
            int cur = pq.peek();
            if (cur != prev) {
                result.add(new int[]{h[0], cur});
                prev = cur;
            }
        }		
		return result;
    }
	
	//219. Contains Duplicate II
	public boolean containsNearbyDuplicate(int[] nums, int k) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < nums.length; i++){
        	if( map.containsKey(nums[i]) && (i - map.get(nums[i]) <= k ))  return true;
        	else map.put(nums[i], i);
        }
        return false;
    }
	
	//220. Contains Duplicate III
	//treeset
	public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums == null || nums.length == 0 || k <= 0) return false;
        TreeSet<Long> values = new TreeSet<Long>();
        for (int ind = 0; ind < nums.length; ind++) {
            Long floor = values.floor((long)nums[ind] + t);
            Long ceil = values.ceiling((long)nums[ind] - t);
            if ((floor != null && floor >= (long)nums[ind]) || (ceil != null && ceil <= (long)nums[ind]))  return true;
            values.add((long) nums[ind]);
            if (ind >= k) values.remove((long)nums[ind - k]);
        }
        return false;
    }
	
	//221. Maximal Square
	public int maximalSquare(char[][] matrix) {
        int result = 0;
        for(int i = 0; i < matrix.length; i++){
        	for(int j = 0; j < matrix[0].length; j++){
        		if(matrix[i][j] == '1'){
        			int top = i, left = j, bottom = i + 1, right = j + 1;
        			while(bottom < matrix.length && right < matrix[0].length && checkSquare(top, left, bottom, right, matrix)){
        				bottom++;
        				right++;
        			}
        			result = Math.max(result, (bottom - i)*(right - j));
        		}
        	}
        }
        return result;
    }

	private boolean checkSquare(int top, int left, int bottom, int right, char[][] matrix) {
		for(int i = top; i <= bottom; i++){
			if( matrix[i][right] != '1') return false;
		}
		for(int i = left; i <= right; i++){
			if( matrix[bottom][i] != '1') return false;
		}
		return true;
	}
	
	//222. Count Complete Tree Nodes
	public int countNodes(TreeNode root) {
        int hLeft = leftHeight(root);
        int hRight = rightHeight(root);
        if (hLeft == hRight) return (1 << hLeft) - 1;
        return countNodes(root.left) + countNodes(root.right) + 1;
    }
	private int leftHeight(TreeNode root) {
		int dep = 0;
		while (root != null) {
			root = root.left;
			dep++;
		}
		return dep;
    }
	private int rightHeight(TreeNode root) {
		int dep = 0;
		while (root != null) {
			root = root.right;
			dep++;
		}
		return dep;
    }

	//223. Rectangle Area
//	Calculate the area of each rectangle at first. Judge whether they have intersection. 
//	If not, return the sum area of them. Otherwise, count the intersection area and subtract it by one time of total area.
	public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        int rectOne = (C - A) * (D - B);
        int rectTwo = (G - E) * (H - F);
        if(A >= G || B >= H || C <= E || D <= F){
            return rectOne + rectTwo;
        }
        int length = Math.min(C, G) - Math.max(A, E);
        int height = Math.min(D, H) - Math.max(B, F);
        return rectOne + rectTwo - length * height;
    }
	
	//224. Basic Calculator
	//use sign to detemine plus or minus
	//use stack to get each () result;
	public int calculateI(String s) {
		int len = s.length(), sign = 1, result = 0;
		Stack<Integer> stack = new Stack<Integer>();
		for (int i = 0; i < len; i++) {
			if (Character.isDigit(s.charAt(i))) {
				int sum = s.charAt(i) - '0';
				while (i + 1 < len && Character.isDigit(s.charAt(i + 1))) {
					sum = sum * 10 + s.charAt(i + 1) - '0';
					i++;
				}
				result += sum * sign;
			} else if (s.charAt(i) == '+')
				sign = 1;
			else if (s.charAt(i) == '-')
				sign = -1;
			else if (s.charAt(i) == '(') {
				stack.push(result);
				stack.push(sign);
				result = 0;
				sign = 1;
			} else if (s.charAt(i) == ')') {
				result = result * stack.pop() + stack.pop();
			}
		}
		return result;
	}
	
	//225. Implement Stack using Queues
	public class MyStack {
	    /** Initialize your data structure here. */
	    public MyStack() {
	    }
	    
	    //one Queue solution
	    private Queue<Integer> q = new LinkedList<Integer>();
	    /** Push element x onto stack. */
	    public void push(int x) {
	    	q.add(x);
	        for(int i = 1; i < q.size(); i ++) { //rotate the queue to make the tail be the head
	            q.add(q.poll());
	        }
	    }
	    
	    /** Removes the element on top of the stack and returns that element. */
	    public int pop() {
	    	return q.poll();
	    }
	    
	    /** Get the top element. */
	    public int top() {
	    	return q.peek();        
	    }
	    
	    /** Returns whether the stack is empty. */
	    public boolean empty() {
	        return q.isEmpty();
	    }
	}
	
	//226. Invert Binary Tree
	public TreeNode invertTree(TreeNode root) {
		if(root == null) return null;
        TreeNode tmp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(tmp);
        return root;
    }
	
	//227. Basic Calculator II
	public int calculate(String s) {
		int result = 0, i = 0;
		int preNum = 0, curNum = 0;
		char sign = '+';
		while( i < s.length()){
			while( i < s.length() && s.charAt(i) == ' ') i++;
			if( i >= s.length()) break;
 			if(Character.isDigit( s.charAt(i) ) ){
				curNum = Character.getNumericValue(s.charAt(i));
				while( i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))){
					curNum = curNum * 10 + Character.getNumericValue(s.charAt(i + 1));
					i++;
				}
			} 
 			while( i < s.length() && s.charAt(i) == ' ') i++;
			if( i >= s.length()) break;
 			if( sign == '+' ){
				result += preNum;
				preNum = curNum;
			} else if (sign == '-'){
				result += preNum;
				preNum = -curNum;
			} else if(sign == '*'){
				preNum = preNum * curNum;
			} else if(sign == '/'){
				preNum = preNum/curNum;
			}
 			sign = s.charAt(i);
			i++;
		}
		result += preNum;
		return result;
    }
	
	//228. Summary Ranges
	public List<String> summaryRanges(int[] nums) {
        List<String> result = new ArrayList<String>();
        if(nums.length == 0) return result;
        int begin = nums[0], end = nums[0];
        for(int i = 1; i < nums.length; i++){
        	if( nums[i] - nums[i - 1] != 1){
        		if( begin == end) result.add(begin + "");
        		else result.add(begin + "->" + end);
        		begin = nums[i];
        		end = nums[i];
        	}else end = nums[i];
        }
        if( begin == end) result.add(begin + "");
        else result.add(begin + "->" + end);
        return result;
    }
	
	//229. Majority Element II
//	类似于 Majority Element 中的Boyer-Moore Majority Vote Algorithm，由于最多有两个可能的元素，所以我们使用两个 candidate，每个 candidate 对应一个 counter。
//	Majority Element 中若两个元素不同，则去除这两个元素并不影响剩余数组的多数元素。
//	类似的，在本题中，如果去除三个不同的元素，并不影响剩余数组的出现次数多于⌊n/3⌋的元素。
//	先判断当前元素是否与 candidate 相匹配，若不匹配，则判断是否要更新 candidate，若也不需要更新，则已经获取了三个不同的元素，即当前元素和两个 candidate，去除的方式是两个 counter 同时减一。
//	需要注意的是，循环中判断的顺序很重要，需要先判断当前元素是否与两个 candidate 之一相匹配，若均不匹配，再判断 counter。
//	这就需要考虑，最初的 candidate 需要如何确定。即便数组的长度大于等于2，依旧不能类似 Majority Element 那样，使用数组的前两个元素作为两个 candidate，因为数组的前两个元素可能是相同的。
//	其实解决办法也很简单，只要给两个 candidate 赋予不同的初值，并且两个 counter 的初值均为 0 即可。
	public List<Integer> majorityElement(int[] nums) {
		int n1 = 1, n2 = 2, count1 = 0, count2 = 0;
		for(int num : nums){
			if( num == n1){
				count1++;
			}else if( num == n2){
				count2++;
			}else if(count1 == 0){
				n1 = num;
				count1++;
			}else if(count2 == 0){
				n2 = num;
				count2++;
			}else{
				count1--;
				count2--;
			}
		}
		List<Integer> result = new ArrayList<Integer>();
		if(checkCount(nums, n1)) result.add(n1);
		if(checkCount(nums, n2)) result.add(n2);
		return result;
    }

	private boolean checkCount(int[] nums, int n2) {
		int count = 0;
		for(int num : nums){
			if( num == n2) count++;
		}
		if( count > nums.length/3 ) return true;
		return false;
	}
	
	//230. Kth Smallest Element in a BST
//	1、计算左子树元素个数left。
//	2、 left+1 = K，则根节点即为第K个元素
//	      left >=k, 则第K个元素在左子树中，
//	     left +1 <k, 则转换为在右子树中，寻找第K-left-1元素。
	public int kthSmallest(TreeNode root, int k) {
		if(root == null) return 0;
        int leftNum = getLeftNum(root.left);
        if( leftNum == k - 1) return root.val;
        else if( leftNum > k - 1) return kthSmallest(root.left, k);
        else return kthSmallest(root.right, k - 1 - leftNum);
    }

	private int getLeftNum(TreeNode root) {
		if(root == null) return 0;
		return getLeftNum(root.left) + getLeftNum(root.right) + 1;
	}
	
	//231. Power of Two
//	Method 3: Bit operation
//	If n is the power of two:
//	n = 2 ^ 0 = 1 = 0b0000...00000001, and (n - 1) = 0 = 0b0000...0000.
//	n = 2 ^ 1 = 2 = 0b0000...00000010, and (n - 1) = 1 = 0b0000...0001.
//	n = 2 ^ 2 = 4 = 0b0000...00000100, and (n - 1) = 3 = 0b0000...0011.
//	n = 2 ^ 3 = 8 = 0b0000...00001000, and (n - 1) = 7 = 0b0000...0111.
//	we have n & (n-1) == 0b0000...0000 == 0
//	Otherwise, n & (n-1) != 0.
//	For example, n =14 = 0b0000...1110, and (n - 1) = 13 = 0b0000...1101.
	public boolean isPowerOfTwo(int n) {
        return n>0 && Integer.bitCount(n) == 1;
    }
//	public boolean isPowerOfTwo(int n) {
//		if( n == 0) return false;
//		else if( n == 2 || n == 1) return true;
//        else if( n%2 == 1) return false;
//        return isPowerOfTwo(n/2);
//    }
	
	//232. Implement Queue using Stacks
	public class MyQueue {
	    private Stack<Integer> q = new Stack<Integer>();
		
	    /** Initialize your data structure here. */
	    public MyQueue() {
	        
	    }
	    
	    /** Push element x to the back of queue. */
	    public void push(int x) {
		    Stack<Integer> tmp = new Stack<Integer>();
	    	while( !q.isEmpty() ){
	    		tmp.push(q.pop());
	    	}
	    	q.push(x);
	    	while(!tmp.isEmpty()){
	    		q.push(tmp.pop());
	    	}
	    }
	    
	    /** Removes the element from in front of queue and returns that element. */
	    public int pop() {
	        return q.pop();
	    }
	    
	    /** Get the front element. */
	    public int peek() {
	        return q.peek();
	    }
	    
	    /** Returns whether the queue is empty. */
	    public boolean empty() {
	        return q.isEmpty();
	    }
	}
	
	//233. Number of Digit One
//	通过上面的列举我们可以发现，100以内的数字，除了10-19之间有11个‘1’之外，其余都只有1个。
//	如果我们不考虑[10, 19]区间上那多出来的10个‘1’的话，那么我们在对任意一个两位数，十位数上的数字(加1)就代表1出现的个数，这时候我们再把多出的10个加上即可。比如56就有(5+1)+10=16个。
//	如何知道是否要加上多出的10个呢，我们就要看十位上的数字是否大于等于2，是的话就要加上多余的10个'1'。那么我们就可以用(x+8)/10来判断一个数是否大于等于2。
//	对于三位数也是一样，除了[110, 119]之间多出的10个数之外，其余的每10个数的区间都只有11个‘1’，那么还是可以用相同的方法来判断并累加1的个数.
	public int countDigitOne(int n) {
		int count = 0;
		for (long k = 1; k <= n; k *= 10) {
			long r = n / k, m = n % k;
		    // sum up the count of ones on every place k
		    count += (r + 8) / 10 * k + (r % 10 == 1 ? m + 1 : 0);
		}    
		return count;
    }
	
	//234. Palindrome Linked List
	public boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null)  return true;
        ListNode fast = head;
        ListNode slow = head;
        while( fast != null && fast.next != null){
        	fast = fast.next.next;
        	slow = slow.next;
        }
        ListNode q = slow;
        ListNode p = head;
        //开始反转
        ListNode r = q.next;
        q.next = null;
        ListNode m;
        while(r != null) {
            m = r.next;
            r.next = q;
            q = r;
            r = m;
        }
        //依次比较,直到其中一个或者两个链遍历完
        while(q != null && p != null) {
            if(p.val == q.val) {
                q = q.next;
                p = p.next;
            }else {
                return false;
            }
            
        }
        return true;
    }
	
	//235. Lowest Common Ancestor of a Binary Search Tree
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return null;
        if(root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if( left != null && right != null) return root;
        else return (left != null) ? left : right;
	}
	
	//236 Lowest Common Ancestor of a Binary Tree
	public TreeNode lowestCommonAncestorII(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null) return null;
        if(root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if( left != null && right != null) return root;
        else return (left != null) ? left : right;
	}
	
	//237. Delete Node in a Linked List
	public void deleteNode(ListNode node) {
		node.val=node.next.val;
	    node.next=node.next.next;
    }
	
	//238. Product of Array Except Self
	public int[] productExceptSelf(int[] nums) {
		int[] result = new int[nums.length];
		int left = 1;
		for(int i = 0; i < nums.length; i++){
			result[i] = left;
			left = nums[i] * left;
		}
		int right = 1;
		for(int i = nums.length - 1; i >= 0; i--){
			result[i] *= right;
			right = right * nums[i];
		}
		return result;
    }
	
	//239. Sliding Window Maximum
	public int[] maxSlidingWindow(int[] nums, int k) {
		if( nums.length == 0) return new int[0];
        int[] res = new int[nums.length + 1 - k];
        int max = nums[0], maxPos = 0;
        for( int i = 0; i < nums.length; i++){
        	if( i + 1 <= k){
        		if( nums[i] >= max){
        			max = nums[i];
        			maxPos = i;
        		}
        	}
        	if( i + 1 > k){
	    		if( nums[i] >= max){
	    			max = nums[i];
	    			maxPos = i;
	    		} else if( nums[i] < max && maxPos < i - k + 1){
    				max = nums[i - k + 1];
    				for( int j = i - k + 1; j <= i; j++){
    					if( nums[j] >= max){
    						max = nums[j];
    						maxPos = j;
    					}
    				}
	    		}
        	}
        	if( i + 1 >= k) res[i + 1 - k] = max;
        }
        return res;
    }
	
	//240. Search a 2D Matrix II
	//从右上角开始, 比较target 和 matrix[i][j]的值. 如果小于target, 则该行不可能有此数,  所以i++; 如果大于target, 则该列不可能有此数, 所以j--. 遇到边界则表明该矩阵不含target.
	public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix == null || matrix.length < 1 || matrix[0].length <1) {
            return false;
        }
        int col = matrix[0].length-1;
        int row = 0;
        while(col >= 0 && row <= matrix.length-1) {
            if(target == matrix[row][col]) {
                return true;
            } else if(target < matrix[row][col]) {
                col--;
            } else if(target > matrix[row][col]) {
                row++;
            }
        }
        return false;
    }
	
	//241. Different Ways to Add Parentheses
	//这里可以采用带备忘录的自顶向下法。即如果子问题的结果计算过就把它保存下来，下次需要用时直接取出。如果没有计算过再重新计算。
	public List<Integer> diffWaysToCompute(String input) {
        Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
        return diffWaysToCompute(input, map);
	}
	
	public List<Integer> diffWaysToCompute(String input, Map<String, List<Integer>> map) {
        List<Integer> res = new ArrayList<Integer>();
		for(int i = 0; i < input.length(); i++){
			char ch = input.charAt(i);
			if( ch == '+' || ch == '-' || ch == '*'){
				List<Integer> leftList = new ArrayList<Integer>();
				List<Integer> rightList = new ArrayList<Integer>();
				String leftStr = input.substring(0, i), rigthStr = input.substring(i + 1);
				if(map.containsKey(leftStr)) 
					leftList = map.get(leftStr);
				else {
					leftList = diffWaysToCompute(leftStr);
					map.put(leftStr, leftList);
				}
				if(map.containsKey(rigthStr)) 
					rightList = map.get(rigthStr);
				else {
					rightList = diffWaysToCompute(rigthStr);
					map.put(rigthStr, rightList);
				}
				for(int left : leftList){
					for( int right : rightList){
						if( ch == '+') res.add(left + right);
						if( ch == '-') res.add(left - right);
						if( ch == '*') res.add(left * right);
					}
				}
			}
		}
        if( res.isEmpty()) res.add(Integer.parseInt(input));
        return res;
    }
	
	//242. Valid Anagram
	public boolean isAnagram(String s, String t) {
        int[] alphabet = new int[26];
        for (int i = 0; i < s.length(); i++) alphabet[s.charAt(i) - 'a']++;
        for (int i = 0; i < t.length(); i++) alphabet[t.charAt(i) - 'a']--;
        for (int i : alphabet) if (i != 0) return false;
        return true;
    }
	
	//257. Binary Tree Paths
	public List<String> binaryTreePaths(TreeNode root) {
	    List<String> answer = new ArrayList<String>();
	    if (root != null) searchBT(root, "", answer);
	    return answer;
	}
	private void searchBT(TreeNode root, String path, List<String> answer) {
	    if (root.left == null && root.right == null) answer.add(path + root.val);
	    if (root.left != null) searchBT(root.left, path + root.val + "->", answer);
	    if (root.right != null) searchBT(root.right, path + root.val + "->", answer);
	}
	
	//258. Add Digits
//	public int addDigits(int num) {
//		int result = num;
//        while(result >= 10){
//        	int newNum = 0;
//        	while( result >= 10){
//        		newNum += result%10;
//        		result /= 10;
//        	}
//        	result += newNum;
//        }
//        return result;
//    }
//	For base b (decimal case b = 10), the digit root of an integer is:
//		dr(n) = 0 if n == 0
//		dr(n) = (b-1) if n != 0 and n % (b-1) == 0
//		dr(n) = n mod (b-1) if n % (b-1) != 0
//	or
//		dr(n) = 1 + (n - 1) % 9
	public int addDigits(int num) {
        return 1 + (num - 1) % 9;
    }
	
	//260. Single Number III
	public int[] singleNumber(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();
        for(int num : nums){
        	if( !set.contains(num)) set.add(num);
        	else set.remove(num);
        }
        int[] res = new int[2];
        Object[] test = set.toArray();
        res[0]=(int)test[0];
        res[1]=(int)test[1];
        return res;
    }
	
	//263. Ugly Number
	public boolean isUgly(int num) {
		int res = num;
		if( res <= 0) return false;
        while( res > 5 &&  res%2 == 0) res /= 2;
        while( res > 5 &&  res%3 == 0) res /= 3;
        while( res > 5 &&  res%5 == 0) res /= 5;
        if( res <= 5 ) return true;
        return false;
    }
	
	//264. Ugly Number II
	public int nthUglyNumber(int n) {
        int[] ugly = new int[n];
        ugly[0] = 1;
        int index2 = 0, index3 = 0, index5 = 0;
        int factor2 = 2, factor3 = 3, factor5 = 5;
        for(int i=1;i<n;i++){
            int min = Math.min(Math.min(factor2,factor3),factor5);
            ugly[i] = min;
            if(factor2 == min) factor2 = 2*ugly[++index2];
            if(factor3 == min) factor3 = 3*ugly[++index3];
            if(factor5 == min) factor5 = 5*ugly[++index5];
        }
        return ugly[n-1];
    }
	
	//268. Missing Number
	//Pretty simple since we are told that we are missing only one number in [1,n],
	//we just need to look at the difference between the sum([1,n]) = n * (n+1) / 2 and the sum of nums in our array.
	public int missingNumber(int[] nums) {
		int sum = 0;
		for(int num : nums) sum += num;
		return nums.length * (nums.length + 1)/2 - sum;
	}
//	public int missingNumber(int[] nums) {
//        Arrays.sort(nums);
//        for( int i = 1; i < nums.length; i++){
//        	if( nums[i] != nums[i - 1] + 1)
//        		return nums[i - 1] + 1;
//        }
//        if(nums[0] > 0) return 0;
//        else return nums[nums.length - 1] + 1;
//    }
	
	//273. Integer to English Words
	private final String[] LESS_THAN_20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
	private final String[] TENS = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
	private final String[] THOUSANDS = {"", "Thousand", "Million", "Billion"};
	public String numberToWords(int num) {
	    if (num == 0) return "Zero";
	    int i = 0;
	    String words = "";
	    while (num > 0) {
	        if (num % 1000 != 0) words = get4DigitsWords(num % 1000) +THOUSANDS[i] + " " + words;
	    	num /= 1000; i++;
	    }    
	    return words.trim();
	}

	private String get4DigitsWords(int num) {
	    if (num == 0)  return "";
	    else if (num < 20) return LESS_THAN_20[num] + " ";
	    else if (num < 100) return TENS[num / 10] + " " + get4DigitsWords(num % 10);
	    else return LESS_THAN_20[num / 100] + " Hundred " + get4DigitsWords(num % 100);
	}
	
	//274. H-Index
//	H指数的计算基于其研究者的论文数量及其论文被引用的次数。赫希认为：一个人在其所有学术文章中有N篇论文分别被引用了至少N次，他的H指数就是N。[1][2]如美国耶鲁大学免疫学家理查德·弗来沃发表的900篇文章中，有107篇被引用了107次以上，他的H指数是107。
//	可以按照如下方法确定某人的H指数：
//	将其发表的所有SCI论文按被引次数从高到低排序；
//	从前往后查找排序后的列表，直到某篇论文的序号大于该论文被引次数。所得序号减一即为H指数。
	public int hIndex(int[] citations) {
         Arrays.sort(citations);
         for(int i = citations.length - 1; i >= 0;i--){
        	 if(citations[i] < citations.length - i) return citations.length - i - 1;
         }
         return citations.length;
	}
	
	//275 H-Index II
	public int hIndexII(int[] citations) {
		int left = 0, right = citations.length - 1;
		return getIndexMid(citations, left, right);
	}

	private int getIndexMid(int[] citations, int left, int right) {
		if( left > right) return 0;
		if(left == right) return (citations[left] >= citations.length - left) ?  citations.length - left : citations.length - left - 1;
		int mid = (right - left)/2 + left + 1;
		if( citations[mid] >= citations.length - mid)
			return getIndexMid(citations, left, mid - 1);
		else return getIndexMid(citations, mid, right);
	}
	
	//278. First Bad Version
	public int firstBadVersion(int n) {
		int start = 1, end = n;
	    while (start < end) {
	        int mid = start + (end-start) / 2;
	        if (!isBadVersion(mid)) start = mid + 1;
	        else end = mid;            
	    }        
	    return start;
    }
	private boolean isBadVersion(int mid) {
		return false;
	}
	
	//279. Perfect Squares
//	dp[0] = 0 
//	dp[1] = dp[0]+1 = 1
//	dp[2] = dp[1]+1 = 2
//	dp[3] = dp[2]+1 = 3
//	dp[4] = Min{ dp[4-1*1]+1, dp[4-2*2]+1 } 
//	      = Min{ dp[3]+1, dp[0]+1 } 
//	      = 1				
//	dp[5] = Min{ dp[5-1*1]+1, dp[5-2*2]+1 } 
//	      = Min{ dp[4]+1, dp[1]+1 } 
//	      = 2
//							.
//							.
//							.
//	dp[13] = Min{ dp[13-1*1]+1, dp[13-2*2]+1, dp[13-3*3]+1 } 
//	       = Min{ dp[12]+1, dp[9]+1, dp[4]+1 } 
//	       = 2
//							.
//							.
//							.
//	dp[n] = Min{ dp[n - i*i] + 1 },  n - i*i >=0 && i >= 1
	public int numSquares(int n) {
		int[] dp = new int[n + 1];
		dp[0] = 0;
		for( int i = 1; i < dp.length; i++){
			dp[i] = i;
			for( int j = 1; j*j <= i; j++){
				dp[i] = Math.min(dp[i], dp[i - j * j] + 1);
			}
		}
		return dp[n];
	}
	
	//282. Expression Add Operators
	public List<String> addOperators(String num, int target) {
        List<String> result = new ArrayList<String>();
        addOperators(num, target, result, "", 0, 0, 0);
        return result;
    }

	private void addOperators(String num, int target, List<String> res, String str, int pos, long sumbefore, long numbefore) {
		if( pos == num.length()) {
			if( sumbefore == target ) res.add(str);
			return;
		}
		for( int i = pos; i < num.length(); i++){
			if(num.charAt(pos) == '0' && i > pos) break;
			long cur = Long.parseLong(num.substring(pos, i + 1));
			if( pos == 0)
				addOperators(num, target, res, str + cur, i + 1, cur, cur);
			else{
				addOperators(num, target, res, str + "+" + cur, i + 1, sumbefore + cur, cur); //+
				addOperators(num, target, res, str + "-" + cur, i + 1, sumbefore - cur, -cur); //-
				addOperators(num, target, res, str + "*" + cur, i + 1, sumbefore - numbefore + numbefore * cur, numbefore * cur); //*
			}
		}
	}

	//284. Peeking Iterator
	class PeekingIterator implements Iterator<Integer> {
		private Integer next = null;
		private Iterator<Integer> iter;
		    
		public PeekingIterator(Iterator<Integer> iterator) {
		    // initialize any member here.
			iter = iterator;
	        if (iter.hasNext()) next = iter.next();
		}

	    // Returns the next element in the iteration without advancing the iterator.
		public Integer peek() {
	        return next;
		}

		// hasNext() and next() should behave the same as in the Iterator interface.
		// Override them if needed.
		@Override
		public Integer next() {
			Integer res = next;
	        next = iter.hasNext() ? iter.next() : null;
	        return res; 
		}

		@Override
		public boolean hasNext() {
		    return next != null;
		}
	}
	
	//287. Find the Duplicate Number
	public int findDuplicate(int[] nums) {
		int slow = nums[0];
		int fast = nums[nums[0]];
		while (slow != fast)
		{
			slow = nums[slow];
			fast = nums[nums[fast]];
		}
		fast = 0;
		while (fast != slow)
		{
			fast = nums[fast];
			slow = nums[slow];
		}
		return slow;
    }
	
	//289. Game of Life
	public void gameOfLife(int[][] board) {
        int[][] dir ={{1,-1},{1,0},{1,1},{0,-1},{0,1},{-1,-1},{-1,0},{-1,1}};
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                int live=0;
                for(int[] d:dir){
                    if(d[0]+i<0 || d[0]+i>=board.length || d[1]+j<0 || d[1]+j>=board[0].length) continue;
                    if(board[d[0]+i][d[1]+j]==1 || board[d[0]+i][d[1]+j]==2) live++;
                }
                if(board[i][j]==0 && live==3) board[i][j]=3;
                if(board[i][j]==1 && (live<2 || live>3)) board[i][j]=2;
            }
        }
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                board[i][j] %=2;
            }
        }
    }
	
	//290. Word Pattern
	public boolean wordPattern(String pattern, String str) {
		Map<Character, String> map = new HashMap<>();
		String[] strs = str.split(" ");
		if(pattern.length() != strs.length) return false;
        for(int i = 0; i < pattern.length(); i++){
        	char ch = pattern.charAt(i);
        	if(map.containsKey(ch) ){
        		if( !map.get(ch).equals(strs[i])) return false;
        	} else {
        		if( map.containsValue(strs[i])) return false;
        	}
        	map.put(ch, strs[i]);
        }
        return true;
    }
	
	//292. Nim Game
	public boolean canWinNim(int n) {
        if( n%4  == 0) return false;
        return true;
    }
	
	//295. Find Median from Data Stream
	class MedianFinder {
	    // max queue is always larger or equal to min queue
	    PriorityQueue<Integer> min = new PriorityQueue<Integer>();
	    PriorityQueue<Integer> max = new PriorityQueue<Integer>(1000, Collections.reverseOrder());
	    // Adds a number into the data structure.
	    public void addNum(int num) {
	        max.offer(num);
	        min.offer(max.poll());
	        if (max.size() < min.size()){
	            max.offer(min.poll());
	        }
	    }

	    // Returns the median of current data stream
	    public double findMedian() {
	        if (max.size() == min.size()) return (max.peek() + min.peek()) /  2.0;
	        else return max.peek();
	    }
	}
	
	//297. Serialize and Deserialize Binary Tree
	public class Codec {
	    private static final String spliter = ",";
	    private static final String NN = "X";

	    // Encodes a tree to a single string.
	    public String serialize(TreeNode root) {
	        StringBuilder sb = new StringBuilder();
	        buildString(root, sb);
	        return sb.toString();
	    }

	    private void buildString(TreeNode node, StringBuilder sb) {
	        if (node == null) {
	            sb.append(NN).append(spliter);
	        } else {
	            sb.append(node.val).append(spliter);
	            buildString(node.left, sb);
	            buildString(node.right,sb);
	        }
	    }
	    // Decodes your encoded data to tree.
	    public TreeNode deserialize(String data) {
	        Deque<String> nodes = new LinkedList<>();
	        nodes.addAll(Arrays.asList(data.split(spliter)));
	        return buildTree(nodes);
	    }
	    
	    private TreeNode buildTree(Deque<String> nodes) {
	        String val = nodes.remove();
	        if (val.equals(NN)) return null;
	        else {
	            TreeNode node = new TreeNode(Integer.valueOf(val));
	            node.left = buildTree(nodes);
	            node.right = buildTree(nodes);
	            return node;
	        }
	    }
	}
	
	//299. Bulls and Cows
//	在处理不是bulls的位置时，我们看如果secret当前位置数字的映射值小于0，则表示其在guess中出现过，cows自增1，然后映射值加1，
//	如果guess当前位置的数字的映射值大于0，则表示其在secret中出现过，cows自增1，然后映射值减1.
	public String getHint(String secret, String guess) {
        int countA = 0, countB = 0;
        int[] m = new int[246];
        for(int i = 0; i < secret.length(); i++){
        	if(secret.charAt(i) == guess.charAt(i)) {
        		countA++;
        	} else {
        		if( m[secret.charAt(i)] < 0) countB++;
        		if( m[guess.charAt(i)] > 0) countB++;
            	m[secret.charAt(i)]++; m[guess.charAt(i)]--;
        	}
        }
        return countA + "A" + countB + "B";
    }
	
	//300. Longest Increasing Subsequence
//	我们维护一个一维dp数组，其中dp[i]表示以nums[i]为结尾的最长递增子串的长度，对于每一个nums[i]，我们从第一个数再搜索到i，如果发现某个数小于nums[i]，我们更新dp[i]，
//	更新方法为dp[i] = max(dp[i], dp[j] + 1)，即比较当前dp[i]的值和那个小于num[i]的数的dp值加1的大小
	public int lengthOfLIS(int[] nums) {
		if( nums.length == 0) return 0;
        int[] dp = new int[nums.length + 1];
        int result = 0;
        for(int i = 0; i < nums.length; i++){
        	dp[i + 1] = 1;
        	for( int j = 0; j < i; j++){
        		if(nums[j] < nums[i])
        			dp[i + 1] = Math.max(dp[i + 1], dp[j + 1] + 1);
        	}
        	result = Math.max(result, dp[i + 1]);
        }
        return result;
    }
	
	//301. Remove Invalid Parentheses
//	We all know how to check a string of parentheses is valid using a stack. Or even simpler use a counter.
//	The counter will increase when it is ‘(‘ and decrease when it is ‘)’. Whenever the counter is negative, we have more ‘)’ than ‘(‘ in the prefix.
//
//	To make the prefix valid, we need to remove a ‘)’. The problem is: which one? The answer is any one in the prefix. 
//	However, if we remove any one, we will generate duplicate results, for example: s = ()), we can remove s[1] or s[2] but the result is the same (). 
//	Thus, we restrict ourself to remove the first ) in a series of concecutive )s.
//
//	After the removal, the prefix is then valid. We then call the function recursively to solve the rest of the string. 
//	However, we need to keep another information: the last removal position. 
//	If we do not have this position, we will generate duplicate by removing two ‘)’ in two steps only with a different order.
//	For this, we keep tracking the last removal position and only remove ‘)’ after that.
//
//	Now one may ask. What about ‘(‘? What if s = ‘(()(()’ in which we need remove ‘(‘?
//	The answer is: do the same from right to left.
//	However a cleverer idea is: reverse the string and reuse the code!
	public List<String> removeInvalidParentheses(String s) {
        List<String> ans = new ArrayList<>();
        remove(s, ans, 0, 0, new char[]{'(', ')'});
        return ans;
    }

    public void remove(String s, List<String> ans, int last_i, int last_j,  char[] par) {
        for (int stack = 0, i = last_i; i < s.length(); ++i) {
            if (s.charAt(i) == par[0]) stack++;
            if (s.charAt(i) == par[1]) stack--;
            if (stack >= 0) continue;
            else {
                for (int j = last_j; j <= i; ++j)
                    if (s.charAt(j) == par[1] && (j == last_j || s.charAt(j - 1) != par[1]))
                        remove(s.substring(0, j) + s.substring(j + 1, s.length()), ans, i, j, par);
                return;
            }
        }
        String reversed = new StringBuilder(s).reverse().toString();
        if (par[0] == '(') // finished left to right
            remove(reversed, ans, 0, 0, new char[]{')', '('});
        else // finished right to left
            ans.add(reversed);
    }
	
	//303. Range Sum Query - Immutable
    public class NumArrayImmutable {
    	int[] nums;
    	
        public NumArrayImmutable(int[] nums) {
    		for(int i = 1; i < nums.length; i++)
    	        nums[i] += nums[i - 1];
    	    this.nums = nums;
        }
        
        public int sumRange(int i, int j) {
        	if(i == 0)
        		return nums[j];
        	return nums[j] - nums[i - 1];
        }
    }
    
    //304. Range Sum Query 2D - Immutable
//    +---------------+   +--------------+   +---------------+   +--------------+   +--------------+
//    |               |   |         |    |   |   |           |   |         |    |   |   |          |
//    |   (r1,c1)     |   |         |    |   |   |           |   |         |    |   |   |          |
//    |   +------+    |   |         |    |   |   |           |   +---------+    |   +---+          |
//    |   |      |    | = |         |    | - |   |           | - |      (r1,c2) | + |   (r1,c1)    |
//    |   |      |    |   |         |    |   |   |           |   |              |   |              |
//    |   +------+    |   +---------+    |   +---+           |   |              |   |              |
//    |        (r2,c2)|   |       (r2,c2)|   |   (r2,c1)     |   |              |   |              |
//    +---------------+   +--------------+   +---------------+   +--------------+   +--------------+
// return sumRegion[row2 + 1][col2 + 1] - sumRegion[row1][col2 + 1] - sumRegion[row2 + 1][col1] + sumRegion[row1][col1]
    public class NumMatrix {
    	int[][] sumRegion;
    	
    	public NumMatrix(int[][] matrix) {
    	    if (matrix.length != 0)  sumRegion = new int[matrix.length + 1][matrix[0].length + 1];
    	    for (int i = 0; i < matrix.length; i++) {
    	        int sum = 0;
    	        for (int j = 0; j < matrix[0].length; j++) {
    	            sum += matrix[i][j];
    	            sumRegion[i + 1][j + 1] = sum + sumRegion[i][j + 1]; 
    	        }
    	    }
    	}

    	public int sumRegion(int row1, int col1, int row2, int col2) {
    	    return sumRegion[row2 + 1][col2 + 1] - sumRegion[row1][col2 + 1] - sumRegion[row2 + 1][col1] + sumRegion[row1][col1];
    	}
    }
    
    //306. Additive Number
    public boolean isAdditiveNumber(String num) {
        for(int i = 0; i <= num.length()/3; i++){
        	long firstNum = Long.parseLong(num.substring(0, i + 1));
        	if( num.charAt(0) == '0' && i > 0) break;
        	for( int j = i + 1; j <= (num.length() - i)/2 + i; j++){
        		long secNum = Long.parseLong(num.substring(i + 1, j + 1));
            	if( num.charAt(i + 1) == '0' && j > i + 1) break;
        		if( checkNum(num, firstNum, secNum, j + 1)) return true;
        	}
        }
        return false;
    }

	private boolean checkNum(String num, long first, long sec, int begin) {
		long sum = first + sec;
		String sumStr = sum + "";
		String next = num.substring(begin);
		if( next.length() < sumStr.length()) return false;
		if( next.equals(sumStr)) return true;
		if( next.startsWith(sumStr)) {
			return checkNum(num, sec, sum, begin + sumStr.length());
		}
		return false;
	}
    
    //307. Range Sum Query - Mutable
	//!!!!!!!!!!!!!!!!!!!!!!!not fixed!!!!!!!!
	public class NumArray {
		/**
		 * Binary Indexed Trees (BIT or Fenwick tree):
		 * https://www.topcoder.com/community/data-science/data-science-
		 * tutorials/binary-indexed-trees/
		 * 
		 * Example: given an array a[0]...a[7], we use a array BIT[9] to
		 * represent a tree, where index [2] is the parent of [1] and [3], [6]
		 * is the parent of [5] and [7], [4] is the parent of [2] and [6], and
		 * [8] is the parent of [4]. I.e.,
		 * 
		 * BIT[] as a binary tree:
		 *            ______________*
		 *            ______*
		 *            __*     __*
		 *            *   *   *   *
		 * indices: 0 1 2 3 4 5 6 7 8
		 * 
		 * BIT[i] = ([i] is a left child) ? the partial sum from its left most
		 * descendant to itself : the partial sum from its parent (exclusive) to
		 * itself. (check the range of "__").
		 * 
		 * Eg. BIT[1]=a[0], BIT[2]=a[1]+BIT[1]=a[1]+a[0], BIT[3]=a[2],
		 * BIT[4]=a[3]+BIT[3]+BIT[2]=a[3]+a[2]+a[1]+a[0],
		 * BIT[6]=a[5]+BIT[5]=a[5]+a[4],
		 * BIT[8]=a[7]+BIT[7]+BIT[6]+BIT[4]=a[7]+a[6]+...+a[0], ...
		 * 
		 * Thus, to update a[1]=BIT[2], we shall update BIT[2], BIT[4], BIT[8],
		 * i.e., for current [i], the next update [j] is j=i+(i&-i) //double the
		 * last 1-bit from [i].
		 * 
		 * Similarly, to get the partial sum up to a[6]=BIT[7], we shall get the
		 * sum of BIT[7], BIT[6], BIT[4], i.e., for current [i], the next
		 * summand [j] is j=i-(i&-i) // delete the last 1-bit from [i].
		 * 
		 * To obtain the original value of a[7] (corresponding to index [8] of
		 * BIT), we have to subtract BIT[7], BIT[6], BIT[4] from BIT[8], i.e.,
		 * starting from [idx-1], for current [i], the next subtrahend [j] is
		 * j=i-(i&-i), up to j==idx-(idx&-idx) exclusive. (However, a quicker
		 * way but using extra space is to store the original array.)
		 */

		int[] nums;
		int[] BIT;
		int n;

		public NumArray(int[] nums) {
			this.nums = nums;

			n = nums.length;
			BIT = new int[n + 1];
			for (int i = 0; i < n; i++)
				init(i, nums[i]);
		}

		public void init(int i, int val) {
			i++;
			while (i <= n) {
				BIT[i] += val;
				i += (i & -i);
			}
		}

		void update(int i, int val) {
			int diff = val - nums[i];
			nums[i] = val;
			init(i, diff);
		}

		public int getSum(int i) {
			int sum = 0;
			i++;
			while (i > 0) {
				sum += BIT[i];
				i -= (i & -i);
			}
			return sum;
		}

		public int sumRange(int i, int j) {
			return getSum(j) - getSum(i - 1);
		}
	}
	// Your NumArray object will be instantiated and called as such:
	// NumArray numArray = new NumArray(nums);
	// numArray.sumRange(0, 1);
	// numArray.update(1, 10);
	// numArray.sumRange(1, 2);
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
