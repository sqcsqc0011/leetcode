package z_leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class leetcode_part1 {
	
	
	//1 two sum
	//use map, O(n) solution
	public int[] twoSum(int[] nums, int target) {
		int[] result = new int[2];
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	    for (int i = 0; i < nums.length; i++) {
	    	if(map.containsKey(target - nums[i])){
	    		result[0] = map.get(target - nums[i]);
	    		result[1] = i;
	    		return result;
	    	}
	    	map.put(nums[i], i);
	    }
	    return result;
    }
	
	//2 Add Two Numbers
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        result = getNext(l1, l2, 0);
        return result;
    }
	
	public ListNode getNext(ListNode l1, ListNode l2, int i){
		ListNode result = new ListNode(0);
		if( l1 == null && l2 == null & i == 0){
		    return null;
		}
		int sum = ((l1 == null) ? 0 : l1.val) + ((l2 == null) ? 0: l2.val) + i;
		result.val = sum % 10;
		result.next = getNext( (l1 == null) ? null : l1.next, (l2 == null) ? null : l2.next, sum/10 );
        return result;
	}
	
	//3. Longest Substring Without Repeating Characters
	//use map to get the dublicate charater
	public int lengthOfLongestSubstring(String s) {
		if (s.length()==0) return 0;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int max=0, count = 0;
        for (int i=0; i<s.length(); ++i){
            if (map.containsKey(s.charAt(i))){
            	count = Math.max(count, map.get(s.charAt(i))+1);
            }
            map.put(s.charAt(i),i);
            max = Math.max(max,i - count + 1);
        }
        return max;
    }
	
	//4. Median of Two Sorted Arrays
	public double findMedianSortedArrays(int[] A, int[] B) {
		int m = A.length;
		int n = B.length;
		int total = m+n;
		if (total%2 != 0)
			return (double) findKth(A, 0, m-1, B, 0, n-1, total/2+1);//k传得是第k个，index实则k-1
		else {
			double x = findKth(A, 0, m-1, B, 0, n-1, total/2);//k传得是第k个，index实则k-1
			double y = findKth(A, 0, m-1, B, 0, n-1, total/2+1);//k传得是第k个，index实则k-1
			return (double)(x+y)/2;
		}
	}
	
	public int findKth(int[] A, int astart, int aend, int[] B, int bstart, int bend, int k) {
		int m = aend - astart + 1;
		int n = bend - bstart + 1;
		
		if(m>n)
			return findKth(B,bstart,bend,A,astart,aend,k);
		if(m==0)
			return B[k-1];
		if(k==1)
			return Math.min(A[astart],B[bstart]);

		int partA = Math.min(k/2,m);
		int partB = k - partA;
		if(A[astart+partA-1] < B[bstart+partB-1])
			return findKth(A,astart+partA,aend,B,bstart,bend,k-partA);
		else if(A[astart+partA-1] > B[bstart+partB-1])
			return findKth(A,astart,aend,B,bstart+partB,bend,k-partB);
		else
			return A[astart+partA-1];
	}
	
	
	//5 Longest Palindromic Substring
	private int low = 0, maxlength = 0;
	public String longestPalindrome(String s) {
        if( s.length() < 2) return s;
        for(int i = 0; i < s.length(); i++){
        	getPalindrome(s, i , i);
        	getPalindrome(s, i, i + 1);
        }        
        return s.substring(low, low + maxlength);
    }

	private void getPalindrome(String s, int i1, int i2) {
		while( i1 >= 0 && i2 < s.length() && s.charAt(i1) == s.charAt(i2)){
			i1--;
			i2++;
		}
		if( maxlength < i2 - i1 - 1){
			maxlength = i2 - i1 - 1;
			low = i1 + 1;
		}
	}
	
	//6  ZigZag Conversion
	public String zigzagConvert(String s, int nRows) {
		if(s == null || s.length()==0 || nRows <=0)  
            return "";  
        if(nRows == 1)  
            return s;
            
        StringBuilder res = new StringBuilder();  
        int size = 2*nRows-2;  
        for(int i=0;i<nRows;i++){  
            for(int j=i;j<s.length();j+=size){  
                res.append(s.charAt(j));  
                if(i != 0 && i != nRows - 1){//except the first row and the last row
                    int temp = j+size-2*i;
                    if(temp<s.length())
                        res.append(s.charAt(temp));
                }
            }                  
        }  
        return res.toString();  
    }
	
	// 7. Reverse Integer
	// add overflow check
	public int reverseInteger(int x) {
        int result = 0;
        while( x != 0){
        	int newresult = result * 10 + (x % 10);
        	if( (newresult - (x % 10)) / 10 != result ) 
        		return 0;
        	result = newresult;
        	x = x/10;
        }
        return result;
    }
	
	//8 String to Integer (atoi)
	public int myAtoi(String str) {
        int index = 0, sign = 1, total = 0;
        //1. Empty string
        if(str.length() == 0) return 0;
    
        //2. Remove Spaces
        while(str.charAt(index) == ' ' && index < str.length())
            index ++;
    
        //3. Handle signs
        if(str.charAt(index) == '+' || str.charAt(index) == '-'){
            sign = str.charAt(index) == '+' ? 1 : -1;
            index ++;
        }
        
        //4. Convert number and avoid overflow
        while(index < str.length()){
            int digit = str.charAt(index) - '0';
            if(digit < 0 || digit > 9) break;
    
            //check if total will be overflow after 10 times and add digit
            if(Integer.MAX_VALUE/10 < total || Integer.MAX_VALUE/10 == total && Integer.MAX_VALUE %10 < digit)
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
    
            total = 10 * total + digit;
            index ++;
        }
        return total * sign;
    }
	
	//9 Palindrome Number
	public boolean isPalindrome(int origin) {
		int result = 0, x = origin;
        while( x > 0){
        	int newresult = result * 10 + (x % 10);
        	if( (newresult - (x % 10)) / 10 != result ) 
        		return false;
        	result = newresult;
        	x = x/10;
        }
        if( origin == result) return true;
        return false;
    }
	
	//10. Regular Expression Matching
//	1, If p.charAt(j) == s.charAt(i) :  dp[i][j] = dp[i-1][j-1];
//	2, If p.charAt(j) == '.' : dp[i][j] = dp[i-1][j-1];
//	3, If p.charAt(j) == '*': 
//	   here are two sub conditions:
//	               1   if p.charAt(j-1) != s.charAt(i) : dp[i][j] = dp[i][j-2]  //in this case, a* only counts as empty
//	               2   if p.charAt(i-1) == s.charAt(i) or p.charAt(i-1) == '.':
//	                              dp[i][j] = dp[i-1][j]    //in this case, a* counts as multiple a 
//	                           or dp[i][j] = dp[i][j-1]   // in this case, a* counts as single a
//	                           or dp[i][j] = dp[i][j-2]   // in this case, a* counts as empty
	public boolean isMatchLeetCode10(String s, String p) {
		if (s == null || p == null) {
	        return false;
	    }
	    boolean[][] dp = new boolean[s.length()+1][p.length()+1];
	    dp[0][0] = true;
	    for (int i = 0; i < p.length(); i++) {
	        if (p.charAt(i) == '*' && dp[0][i-1]) {
	            dp[0][i+1] = true;
	        }
	    }
	    for (int i = 0 ; i < s.length(); i++) {
	        for (int j = 0; j < p.length(); j++) {
	            if (p.charAt(j) == '.' || p.charAt(j) == s.charAt(i)) {
	                dp[i+1][j+1] = dp[i][j];
	            }
	            else if (p.charAt(j) == '*') {
	                if (p.charAt(j-1) != s.charAt(i) && p.charAt(j-1) != '.') {
	                    dp[i+1][j+1] = dp[i+1][j-1];
	                } else {
	                    dp[i+1][j+1] = (dp[i+1][j] || dp[i][j+1] || dp[i+1][j-1]);
	                }
	            }
	        }
	    }
	    return dp[s.length()][p.length()];
	}	
	
	//11. Container With Most Water
	public int maxArea(int[] height) {
		int left = 0, right = height.length - 1, maxArea = 0;
        while(left < right){
            // 每次更新最大面积（盛水量）
            maxArea = Math.max(maxArea, Math.min(height[left], height[right]) * (right - left));
            // 如果左边较低，则将左边向中间移一点
            if(height[left] < height[right]){
                left++;
            // 如果右边较低，则将右边向中间移一点
            } else {
                right--;
            }
        }
        return maxArea;
    }
	
	//12. Integer to Roman
	public String intToRoman(int num) {
        String M[] = {"", "M", "MM", "MMM"};
        String C[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String X[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String I[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return M[num/1000] + C[(num%1000)/100] + X[(num%100)/10] + I[num%10];
    }
	
	
	//13. Roman to Integer
	public int romanToInt(String s) {
	    int sum=0;
	    if(s.indexOf("IV")!=-1){sum-=2;}
	    if(s.indexOf("IX")!=-1){sum-=2;}
	    if(s.indexOf("XL")!=-1){sum-=20;}
	    if(s.indexOf("XC")!=-1){sum-=20;}
	    if(s.indexOf("CD")!=-1){sum-=200;}
	    if(s.indexOf("CM")!=-1){sum-=200;}
	    
	    char c[]=s.toCharArray();
	    int count=0;
	    
	    for(;count<=s.length()-1;count++){
	       if(c[count]=='M') sum+=1000;
	       if(c[count]=='D') sum+=500;
	       if(c[count]=='C') sum+=100;
	       if(c[count]=='L') sum+=50;
	       if(c[count]=='X') sum+=10;
	       if(c[count]=='V') sum+=5;
	       if(c[count]=='I') sum+=1;
	       
	    }
	    return sum;
	}
	
	//14. Longest Common Prefix
	public String longestCommonPrefix(String[] strs) {
		if(strs == null || strs.length == 0)    return "";
	    String pre = strs[0];
	    int i = 1;
	    while(i < strs.length){
	        while(strs[i].indexOf(pre) != 0)
	            pre = pre.substring(0,pre.length()-1);
	        i++;
	    }
	    return pre;
    }
	
	//15. 3Sum
	//get target, more than target high--, less low++
	public List<List<Integer>> threeSum(int[] nums) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
	    Arrays.sort(nums);
	    for( int i = 0; i < nums.length - 2; i++){
	    	if( !(i >0 && nums[i] == nums[ i - 1]) ) {
	    		int lo = i + 1, high = nums.length - 1;
	    		while( lo < high){
	    			if( nums[i] + nums[lo] + nums[high] == 0){
	    				result.add(Arrays.asList(nums[i], nums[lo], nums[high]));
	    				while( lo < high && nums[lo] == nums[lo + 1]) lo++;
	    				while( lo < high && nums[high] == nums[high - 1]) high--;
	    				lo++;
	    				high--;
	    			} else if(nums[i] + nums[lo] + nums[high] > 0 ) high--;
	    			else lo++;
	    		}
	    	}
	    }	    
		return result;
    }
	
	//16. 3Sum closest
	public int threeSumClosest(int[] nums, int target) {
		Arrays.sort(nums);
		int result = nums[nums.length - 1] + nums[nums.length - 2] + nums[nums.length - 3];
		int gap = Math.abs(result - target);
	    for( int i = 0; i < nums.length - 2; i++){
	    	if( !(i >0 && nums[i] == nums[ i - 1]) ) {
	    		int lo = i + 1, high = nums.length - 1;
	    		while( lo < high){
	    			int sum = nums[i] + nums[lo] + nums[high];
	    			if( sum == target)
	    				return target;
	    			else if( sum > target){
	    				if( Math.abs(sum - target) <= gap){
	    					result = sum;
		    				gap = Math.abs(result - target);	
	    				}
	    				high--;
	    			} else {
	    				if( Math.abs(sum - target) <= gap){
	    					result = sum;
		    				gap = Math.abs(result - target);	
	    				}
	    				lo++;
	    			}
	    		}
	    	}
	    }	    
		return result;
    }
	
	//17. Letter Combinations of a Phone Number
	private static final String[] phoneLetterKey = { "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz" };
	public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<String>();
        if(digits.length() == 0) return result;
        String phoneLetter = "";
        getPhoneLetters(result, phoneLetter, digits, 0);
        return result;
    }

	private void getPhoneLetters(List<String> result, String phoneLetter, String digits, int i) {
		if(phoneLetter.length() == digits.length()){
			result.add(phoneLetter);
		}else {
			String letterKey = phoneLetterKey[Character.getNumericValue(digits.charAt(i))];
			for( int k = 0; k < letterKey.length(); k++){
				getPhoneLetters(result, phoneLetter + letterKey.charAt(k), digits, i + 1);
			}
		}
	}	
	
	//18. 4Sum
	public List<List<Integer>> fourSum(int[] nums, int target) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
	    Arrays.sort(nums);
        for( int i = 0; i < nums.length; i++){
        	if( !(i >0 && nums[i] == nums[ i - 1]) ) {
        		result = threeSumWithTarget(result, nums, target - nums[i], i);
        	}
        }
        return result;
    }
	
	public List<List<Integer>> threeSumWithTarget(List<List<Integer>> result, int[] nums, int target ,int digit) {
	    for( int i = digit + 1; i < nums.length - 2; i++){
	    	if( !(i > digit + 1 && nums[i] == nums[ i - 1]) ) {
	    		int lo = i + 1, high = nums.length - 1;
	    		while( lo < high){
	    			if( nums[i] + nums[lo] + nums[high] == target){
	    				result.add(Arrays.asList(nums[digit], nums[i], nums[lo], nums[high]));
	    				while( lo < high && nums[lo] == nums[lo + 1]) lo++;
	    				while( lo < high && nums[high] == nums[high - 1]) high--;
	    				lo++;
	    				high--;
	    			} else if(nums[i] + nums[lo] + nums[high] > target ) high--;
	    			else lo++;
	    		}
	    	}
	    }	    
		return result;
    }
	
	//19. Remove Nth Node From End of List
	//这道题也是经典题，利用的是faster和slower双指针来解决。
	//首先先让faster从起始点往后跑n步。
	//然后再让slower和faster一起跑，直到faster==null时候，slower所指向的node就是需要删除的节点。
	//注意，一般链表删除节点时候，需要维护一个prev指针，指向需要删除节点的上一个节点。
	//为了方便起见，当让slower和faster同时一起跑时，就不让 faster跑到null了，让他停在上一步，faster.next==null时候，这样slower就正好指向要删除节点的上一个节点，充当了prev指针。这样一来，就很容易做删除操作了。
	//slower.next = slower.next.next(类似于prev.next = prev.next.next)。
	//同时，这里还要注意对删除头结点的单独处理，要删除头结点时，没办法帮他维护prev节点，所以当发现要删除的是头结点时，直接让head = head.next并returnhead就够了。
	public ListNode removeNthFromEnd(ListNode head, int n) {        
		ListNode start = new ListNode(0);
	    ListNode slow = start, fast = start;
	    slow.next = head;
	    
	    //Move fast in front so that the gap between slow and fast becomes n
	    for(int i=1; i<=n+1; i++)   {
	        fast = fast.next;
	    }
	    //Move fast to the end, maintaining the gap
	    while(fast != null) {
	        slow = slow.next;
	        fast = fast.next;
	    }
	    //Skip the desired node
	    slow.next = slow.next.next;
	    return start.next;
    }
	
	//20. Valid Parentheses
	public boolean isValid(String s) {
		Stack<Character> stack = new Stack<Character>();
        // Iterate through string until empty
        for(int i = 0; i<s.length(); i++) {
            // Push any open parentheses onto stack
            if(s.charAt(i) == '(' || s.charAt(i) == '[' || s.charAt(i) == '{')
                stack.push(s.charAt(i));
            // Check stack for corresponding closing parentheses, false if not valid
            else if(s.charAt(i) == ')' && !stack.empty() && stack.peek() == '(')
                stack.pop();
            else if(s.charAt(i) == ']' && !stack.empty() && stack.peek() == '[')
                stack.pop();
            else if(s.charAt(i) == '}' && !stack.empty() && stack.peek() == '{')
                stack.pop();
            else
                return false;
        }
        // return true if no open parentheses left in stack
        return stack.empty();
    }
	
	//21. Merge Two Sorted Lists
	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		if(l1 == null) return l2;
        if(l2 == null) return l1;
        ListNode result = new ListNode(0);
        if(l1.val < l2.val){
        	result = l1;
        	result.next = mergeTwoLists(l1.next, l2);
        } else {
        	result = l2;
        	result.next = mergeTwoLists(l1, l2.next);
        }
        return result;
    }
	
	//22. Generate Parentheses
	public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<String>();
        addNewParenthesis(result, "", 0, 0, n);
        return result;
    }

	private void addNewParenthesis(List<String> result, String string, int open, int close, int n) {
		if(string.length() == n * 2 )	result.add(string);
		if( open < n) addNewParenthesis(result,string + '(', open + 1, close, n);
		if( close < open) addNewParenthesis(result, string + ')', open, close + 1, n);
	}

//	private void addNewParenthesis(List<String> result, int count, String parenthesis, int n, int k) {
//		if(count == 0){
//			if (k == n) result.add(parenthesis);
//			else if( k < n) addNewParenthesis(result, count + 1, parenthesis + '(', n , k + 1);
//		}else if( count != 0){
//			if( k < n){
//				addNewParenthesis(result, count + 1, parenthesis + '(', n , k + 1);
//				addNewParenthesis(result, count - 1, parenthesis + ')', n , k);
//			} else if( k == n)
//				addNewParenthesis(result, count - 1, parenthesis + ')', n , k);
//		}
//	}
	
	//23 Merge k Sorted Lists
	//二分法检索
	public ListNode mergeKLists(ListNode[] lists){
	    return partionKLists(lists, 0, lists.length-1);
	}

	public ListNode partionKLists(ListNode[] lists,int start,int end){
	    if(start == end)  return lists[start];
	    if(start < end){
	        int divide = (start + end)/2;
	        ListNode l1=partionKLists(lists, start , divide);
	        ListNode l2=partionKLists(lists, divide+1 , end);
	        return mergeTwoLists(l1,l2);
	    }else
	        return null;
	}
	
	//24. Swap Nodes in Pairs
	public ListNode swapPairs(ListNode head) {
		if( head == null || head.next == null) return head;
		ListNode result = head.next;
		head.next = swapPairs(head.next.next);
		result.next = head;
		return result;
    }
	
	//25. Reverse Nodes in k-Group
	public ListNode reverseKGroup(ListNode head, int k) {
        int i = 0;
		ListNode check = head;
        while(i < k && check != null){// find the k+1 node
        	check = check.next;
        	i++;        	
        }
        
    	if(i == k){
    		check = reverseKGroup(check, k);
    		while (i-- > 0) { // reverse current k-group: 
                ListNode tmp = head.next; // tmp - next head in direct part
                head.next = check; // preappending "direct" head to the reversed list 
                check = head; // move head of reversed part to a new node
                head = tmp; // move "direct" head to the next node in direct part
            }
            head = check;
    	} 
		return head;
    }
	
	//26. Remove Duplicates from Sorted Array
	public int removeDuplicates(int[] nums) {
        int result = 0;
        for(int i = 0; i < nums.length; i++){
        	if( i > 0 && nums[i] == nums[i - 1]) continue;
        	nums[result] = nums[i];
        	result++;
        }
        return result;
    }
	
	//27. Remove Element
	public int removeElement(int[] nums, int val) {
        int result = 0;
        for(int i = 0; i < nums.length; i++){
        	if( nums[i] != val) {
        		nums[result] = nums[i];
        		result++;
        	}
        }
        return result;
    }
	
	//28. Implement strStr()
	public int strStr(String haystack, String needle) {
		int l1 = haystack.length(), l2 = needle.length();
        if (l1 < l2) {
            return -1;
        } else if (l2 == 0) {
            return 0;
        }
        int threshold = l1 - l2;
        for (int i = 0; i <= threshold; ++i) {
            if (haystack.substring(i,i+l2).equals(needle)) {
                return i;
            }
        }
        return -1;
    }
	
	//29. Divide Two Integers
	//recursion
	public int divide(int dividend, int divisor) {
		int sign = 1;
		if( (dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0))
			sign = -1;
		if( divisor == 0) return Integer.MAX_VALUE;
		if( dividend == 0) return 0;
		long num1 = Math.abs(Long.valueOf(dividend));
		long num2 = Math.abs(Long.valueOf(divisor));
		long result = ldivide(num1, num2);
		return (int) (( result > Integer.MAX_VALUE) ? ( (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE) : (int) sign * result);
	}

	private long ldivide(long ldividend, long ldivisor) {
		// Recursion exit condition
		if (ldividend < ldivisor) return 0;
		//  Find the largest multiple so that (divisor * multiple <= dividend), 
		//  whereas we are moving with stride 1, 2, 4, 8, 16...2^n for performance reason.
		//  Think this as a binary search.
		long sum = ldivisor;
		long multiple = 1;
		while ((sum+sum) <= ldividend) {
			sum += sum;
			multiple += multiple;
		}
		//Look for additional value for the multiple from the reminder (dividend - sum) recursively.
		return multiple + ldivide(ldividend - sum, ldivisor);
	}
	
	// hard!!!!!!not fixed!!!!!!!!!!!
	//30. Substring with Concatenation of All Words
	public List<Integer> findSubstring(String s, String[] words) {
		int N = s.length();
		List<Integer> indexes = new ArrayList<Integer>(s.length());
		if (words.length == 0) {
			return indexes;
		}
		int M = words[0].length();
		if (N < M * words.length) {
			return indexes;
		}
		int last = N - M + 1;
		
		//map each string in words array to some index and compute target counters
		Map<String, Integer> mapping = new HashMap<String, Integer>(words.length);
		int [][] table = new int[2][words.length];
		int failures = 0, index = 0;
		for (int i = 0; i < words.length; ++i) {
			Integer mapped = mapping.get(words[i]);
			if (mapped == null) {
				++failures;
				mapping.put(words[i], index);
				mapped = index++;
			}
			++table[0][mapped];
		}
		
		//find all occurrences at string S and map them to their current integer, -1 means no such string is in words array
		int [] smapping = new int[last];
		for (int i = 0; i < last; ++i) {
			String section = s.substring(i, i + M);
			Integer mapped = mapping.get(section);
			if (mapped == null) {
				smapping[i] = -1;
			} else {
				smapping[i] = mapped;
			}
		}
		
		//fix the number of linear scans
		for (int i = 0; i < M; ++i) {
			//reset scan variables
			int currentFailures = failures; //number of current mismatches
			int left = i, right = i;
			Arrays.fill(table[1], 0);
			//here, simple solve the minimum-window-substring problem
			while (right < last) {
				while (currentFailures > 0 && right < last) {
					int target = smapping[right];
					if (target != -1 && ++table[1][target] == table[0][target]) {
						--currentFailures;
					}
					right += M;
				}
				while (currentFailures == 0 && left < right) {
					int target = smapping[left];
					if (target != -1 && --table[1][target] == table[0][target] - 1) {
						int length = right - left;
						//instead of checking every window, we know exactly the length we want
						if ((length / M) ==  words.length) {
							indexes.add(left);
						}
						++currentFailures;
					}
					left += M;
				}
			}
			
		}
		return indexes;
	}
	
	//31. Next Permutation
	public void nextPermutation(int[] nums) {
		int index = -1;
        for( int i = nums.length - 2; i >= 0 ; i--){
        	if( nums[i] < nums[i + 1]) {
        		index = i;
        		break;
        	}
        }
        int left = index + 1, right = nums.length - 1;
        while(left < right){
        	int tmp = nums[left];
        	nums[left] = nums[right];
        	nums[right] = tmp;
        	left++;
        	right--;
        }
        
        if( index != -1){
        	for( int k = index + 1; k < nums.length; k++){
        		if( nums[k] > nums[index]){
        			int tmp = nums[index];
        			nums[index] = nums[k];
        			nums[k] = tmp;
        			break;
        		}
        	}
        }
    }
	
	//32. Longest Valid Parentheses
	public int longestValidParentheses(String s) {
		if (s==null||s.length()==0)	return 0;
        Stack<Integer> stack = new Stack<Integer>();
        int start = 0, maxlength = 0;
        for( int i = 0; i < s.length(); i++){
        	if( s.charAt(i) == '(')
        		stack.push(i);
        	else{
        		if ( stack.isEmpty()){ // record the position before first left parenthesis
        			start = i + 1;
        		}else {
        			stack.pop(); 
        			if(stack.isEmpty())
        				maxlength = Math.max(i - start + 1, maxlength); //if stack is empty mean the positon before the valid left parenthesis is "last"
        			else
        				maxlength = Math.max(i - stack.peek(), maxlength); // if stack is not empty, then for current i the longest valid parenthesis length is i-stack.peek()
        		}
        	}
        }
        return maxlength;
    }
		
	//33 Search in Rotated Sorted Array
	public int search(int[] nums, int target) {
        for( int i = 0; i < nums.length; i++){
            if( nums[i] == target) return i;
        }
        return -1;
    }

	//34. Search for a Range
	public int[] searchRange(int[] nums, int target) {
        int[] result = {nums.length - 1, -1};
        if(nums.length == 0) return result;
		int begin = 0, end = nums.length - 1;
        searchTargetRange(begin, end, nums, target, result);
        if( result[0] > result[1]) result[0] = -1;
        return result;
    }

	private void searchTargetRange(int left, int right, int[] nums, int target, int[] result) {
		if(left > right) return;
		int mid = (right + left)/2;
		if(nums[mid] == target){
			if( mid > result[1]){
				result[1] = mid;
				searchTargetRange(mid + 1, right, nums, target, result);
			}
			if( mid < result[0]){
				result[0] = mid;
				searchTargetRange(left, mid - 1, nums, target, result);
			}
		} else if( nums[mid] < target)
			searchTargetRange(mid + 1, right, nums, target, result);
		else 
			searchTargetRange(left, mid - 1, nums, target, result);
	}

	//35 Search Insert Position
	public int searchInsert(int[] nums, int target) {
        return findFirstEqualOrGreat(nums, target, 0, nums.length - 1, nums.length);
    }

	private int findFirstEqualOrGreat(int[] nums, int target, int left, int right, int result) {
		if( left > right) return result;
		int mid = (left + right)/2;
		if( nums[mid] < target){
			result = findFirstEqualOrGreat(nums, target, mid + 1, right, result);
		} else {
			result = Math.min(mid, result);
			result = findFirstEqualOrGreat(nums, target, left, mid - 1, result);
		}
		return result;
	}
	
	//36. Valid Sudoku
	//hashet唯一性
	public boolean isValidSudoku(char[][] board) {
        for (int i=0; i<9; i++) {
            if (!isParticallyValid(board,i,0,i,8)) return false;
            if (!isParticallyValid(board,0,i,8,i)) return false;
        }
        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if (!isParticallyValid(board,i*3,j*3,i*3+2,j*3+2)) return false;
            }
        }
        return true;
    }
    private boolean isParticallyValid(char[][] board, int x1, int y1,int x2,int y2){
        Set<Character> singleSet = new HashSet<Character>();
        for (int i= x1; i<=x2; i++){
            for (int j=y1;j<=y2; j++){
                if (board[i][j]!='.') if(!singleSet.add(board[i][j])) return false;
            }
        }
        return true;
    }
	
	//37. Sudoku Solver
    public void solveSudoku(char[][] board) {
    	insertSudoku(board);
    }
    
    public boolean insertSudoku(char[][] board){
    	for(int i=0; i<board.length; i++){
    		for (int j=0; j<board[0].length; j++){
    			if (board[i][j]=='.'){
    				for (char num='1'; num<='9'; num++){//尝试
    					if(isValidSudoku(board, i, j, num)){
    						board[i][j]=num;
    						if (insertSudoku(board))
    							return true;
    						else
    							board[i][j]='.';//回退
    					}
    				}
    				return false;
    			}
    		}
    	}
    	return true;
    }

	private boolean isValidSudoku(char[][] board, int i, int j, char num) {
		//check column
		for (int row=0; row<9; row++)
			if (board[row][j] == num)
				return false;
		//check row
		for (int col = 0; col < 9; col++)
			if (board[i][col] == num)
				return false;
		// check block
		for(int row = i/3*3; row < i/3*3 + 3; row++)
			for (int col = j/3*3; col < j/3*3+3; col++)
				if (board[row][col] == num)
					return false;
		return true;
	}
    
    //38. Count and Say
	public String countAndSay(int n) {
		String result = "1";
		for( int i = 2; i <= n; i++){
			result = readStringAndSay(result);
		}
		return result + "";
    }

	private String readStringAndSay(String n) {
		String result = "";
		char index = n.charAt(0);
		int count = 1;
		if(n.length() == 1) return count + "" + index;
		for( int i = 1; i < n.length(); i++){
			if( n.charAt(i) == index)
				count++;
			else{
				result = result + ""+ count + "" + index;
				count = 1;
				index = n.charAt(i);
			}
		}
		result = result + ""+ count + "" + index;
		return result;
	}
	
	//39. Combination Sum
	public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        getCombinationSum(candidates, target, result, new ArrayList<Integer>(), 0);
        return result;
    }

	private void getCombinationSum(int[] candidates, int target, List<List<Integer>> result, List<Integer> combination, int start) {
		if( target == 0) {
		    result.add(new ArrayList<>(combination));
		    return;
		}
		if( target < 0) return;
		for(int i = start; i < candidates.length && candidates[i] <= target; i++){
			combination.add(candidates[i]);
	        getCombinationSum(candidates, target - candidates[i], result, combination, i);
	        combination.remove(combination.size() - 1);
		}
	}
	
	//40. Combination Sum II
	// if i > start !!!!!
	public List<List<Integer>> combinationSum2(int[] candidates, int target) {
		Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        getCombinationSumWithNoDub(candidates, target, result, new ArrayList<Integer>(), 0);
        return result;
    }

	private void getCombinationSumWithNoDub(int[] candidates, int target, List<List<Integer>> result,
			ArrayList<Integer> combination, int start) {
		if( target == 0) {
		    result.add(new ArrayList<>(combination));
		    return;
		}
		if( target < 0) return;
		for(int i = start; i < candidates.length && candidates[i] <= target; i++){
			if( i > start && candidates[i] == candidates[i-1]){
				continue;
			}else {
				combination.add(candidates[i]);
				getCombinationSumWithNoDub(candidates, target - candidates[i], result, combination, i + 1);
		        combination.remove(combination.size() - 1);
			}
		}		
	}
	
	//41. First Missing Positive
	// not fixed!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public int firstMissingPositive(int[] A) {
        int i = 0;
        while(i < A.length){
            if(A[i] == i+1 || A[i] <= 0 || A[i] > A.length) i++;
            else if(A[A[i]-1] != A[i]){
            	swap(A, i, A[i]-1);
            }
            else i++;
        }
        i = 0;
        while(i < A.length && A[i] == i+1) i++;
        return i+1;
    }
    
    private void swap(int[] A, int i, int j){
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }
	
    //42. Trapping Rain Water
    //遍历每一个i，找到右边最短板和左边的最短板
    public int trap(int[] height) {
    	int result = 0;
        for(int i = 1; i < height.length - 1; i++ ){
        	result = result + getTrap(i, height);
        }
        return result;
    }
	
    public int getTrap(int i, int[] height){
    	int leftH = height[0], rightH = height[height.length - 1];
    	for (int left = 1; left < i; left++){
    		leftH = Math.max(leftH, height[left]);
    	}
    	for(int right = height.length - 1; right > i; right--){
    		rightH = Math.max(rightH, height[right]);
    	}
    	return ( Math.min(leftH, rightH) - height[i] > 0 ) ? Math.min(leftH, rightH) - height[i] : 0;
    }
    
    public int trap2(int[] height){
    	if (height.length < 3) return 0;
    	int result = 0;
    	int left = 0, right = height.length - 1;
    	// find the left and right edge which can hold water
    	while(left < right && height[left] <= height[left + 1]) left++;
    	while(left < right && height[right] <= height[right - 1]) right--;
    	//loop
    	while( left < right){
    		int leftEdge = height[left];
    		int rightEdge = height[right];
    		if( leftEdge <= rightEdge ){
                // add volum until an edge larger than the left edge
    			while(left < right && leftEdge > height[++left]){
    				result += leftEdge - height[left];
    			}
    		} else {
                // add volum until an edge larger than the right volum
    			while(left < right && rightEdge > height[--right]){
    				result += rightEdge - height[right];
    			}
    		}
    	}
    	return result;
    }
	
    //43. Multiply Strings
    //`num1[i] * num2[j]` will be placed at indices `[i + j`, `i + j + 1]` 
    public String multiply(String num1, String num2) {
        int m = num1.length(), n = num2.length();
        int[] pos = new int[m + n];
       
        for(int i = m - 1; i >= 0; i--) {
            for(int j = n - 1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0'); 
                int p1 = i + j, p2 = i + j + 1;
                int sum = mul + pos[p2];

                pos[p1] += sum / 10;
                pos[p2] = (sum) % 10;
            }
    }  	
    
        StringBuilder sb = new StringBuilder();
        for(int p : pos) if(!(sb.length() == 0 && p == 0)) sb.append(p);
        return sb.length() == 0 ? "0" : sb.toString();
    }
    
    //44. Wildcard Matching
    public boolean isMatch(String s, String p) {
    	if (s == null || p == null)  return false;
	    boolean[][] dp = new boolean[s.length()+1][p.length()+1];
	    dp[0][0] = true;
	    for(int j = 0; j < p.length(); j++){
	    	if(p.charAt(j) != '*') break;
	    	dp[0][j + 1] = true;
	    	for( int i = 0; i < s.length(); i++){
	    		dp[i + 1][j + 1] = true;
	    	}
	    }
	    for (int i = 0 ; i < s.length(); i++) {
	        for (int j = 0; j < p.length(); j++) {
	            if (p.charAt(j) == '?' || p.charAt(j) == s.charAt(i)) {
	                dp[i+1][j+1] = dp[i][j];
	            }
	            else if (p.charAt(j) == '*') {
	                dp[i+1][j+1] = dp[i + 1][j] || dp[i][j + 1] || dp[i][j];
	            }
	        }
	    }
	    return dp[s.length()][p.length()];    
    }

    //45. Jump Game II
    //贪心法， 算出i+A[i]到达的最远距离
    public int jumpGameII(int[] nums) {
    	if( nums.length <= 1) return 0;
    	int cur = 0, next = 0;
    	int steps = 0;
    	while( cur <= next){
    		steps++;
    		int lastNext = next;
    		for( int i = cur; i <= lastNext; i++){
    			int maxJump = i + nums[i];
    			if( maxJump >= nums.length - 1){
    				return steps;
    			}
    			if(maxJump > next ){
    				next = maxJump;
    			}
    		}
    		cur = lastNext + 1;
    	}
    	return 0;
    }
    //遍历O(n),   from previous cur + 1 to cur, get the farthest i + nums[i]
    // make the farthest i+nums[i] to next // loop nums, when i == cur,  cur = next, step++
    public int jumpGameIISolution2(int[] nums){
    	if(nums.length <= 1) return 0;
    	int steps = 0;
    	int cur = 0, next = 0;
    	for( int i = 0; i < nums.length - 1; i++){
    		int maxJump = i + nums[i];
    		if(maxJump >= nums.length - 1)
    			return steps + 1;
    		next = Math.max(next, maxJump);
    		if( i == cur){
    			steps++;
    			cur = next;
    		}
    	}
    	return steps;
    }
    
    //46. Permutations
    // DFS, 遍历i， 判断i是否在数组中， 不在返回继续遍历i， 直到长度一样
    // 得到一种之后结果之后返回，依次删掉结果最后一位，继续遍历i
    public List<List<Integer>> permute(int[] nums) {
    	List<List<Integer>> result = new ArrayList<List<Integer>>();
    	getPermutationsDistinict(nums, result, new ArrayList<Integer>());
    	return result;
    }

	private void getPermutationsDistinict(int[] nums, List<List<Integer>> result, ArrayList<Integer> permutation) {
		if(permutation.size() == nums.length ){
			result.add(new ArrayList<Integer>(permutation));
			return;
		}
		for( int i = 0; i <= nums.length - 1; i++){
			if( permutation.contains(nums[i])) continue;
			else {
				permutation.add(nums[i]);
				getPermutationsDistinict(nums, result, permutation);
				permutation.remove(permutation.size() - 1);
			}
		}
	}
    
	//47 Permutations II
	public List<List<Integer>> permuteUnique(int[] nums) {
		Arrays.sort(nums);
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		boolean[] usedMark = new boolean[nums.length];
		getPermutationsWithDuplicates(nums, result, new ArrayList<Integer>(), usedMark);
    	return result;
    }
	
	private void getPermutationsWithDuplicates(int[] nums, List<List<Integer>> result, ArrayList<Integer> permutation, boolean[] usedMark) {
		if(permutation.size() == nums.length ){
			result.add(new ArrayList<Integer>(permutation));
			return;
		}
		for( int i = 0; i <= nums.length - 1; i++){
			if( (!permutation.contains(nums[i])) 
					|| ( (usedMark[i] != true) && (i > 0) && nums[i] == nums[i - 1] && (usedMark[i - 1] == true) )){
				permutation.add(nums[i]);
				usedMark[i] = true;
				getPermutationsWithDuplicates(nums, result, permutation, usedMark);
				permutation.remove(permutation.size() - 1);
				usedMark[i] = false;
			}
		}
	}
	
    //48. Rotate Image
	public void rotate(int[][] matrix) {
		//以对角线为轴，swap A[i][j] and A[j][i]
		for(int i = 0; i < matrix.length; i++){
			for(int j = i; j < matrix[i].length; j++){
				int temp = matrix[i][j];
				matrix[i][j] = matrix[j][i];
				matrix[j][i] = temp;
			}
		}
		// 以 y为轴，第0列与最后一列交换，依次交换
		for(int i = 0; i < matrix.length; i++){
			int left = 0, right = matrix[0].length - 1;
			while(left < right){
				int temp = matrix[i][right];
				matrix[i][right] = matrix[i][left];
				matrix[i][left] = temp;
				left++;
				right--;
			}
		}
    }
	
	//49. Group Anagrams
	public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<List<String>>();
        Map<String, List<String>> map = new HashMap<>();
        for(String str : strs ){
        	char[] strChar = str.toCharArray();
        	Arrays.sort(strChar);
        	String strSorted = String.valueOf(strChar);
        	if( !map.containsKey(strSorted)) map.put(strSorted, new ArrayList<String>());
        	map.get(strSorted).add(str);
        }
        return new ArrayList<List<String>>(map.values());
    }
    
	
	//50. Pow(x, n)
	// 每次平方， n = n/2, if mod is 1, multiply one more x
	public double myPow(double x, int n) {
		if (n < 0) return 1 / getPow(x, -n);
        return getPow(x, n);
    }	
	
	private double getPow(double x, int n){
		if( n == 0) return 1;
		if( n%2 == 0) return getPow(x * x, n/2);
		else return getPow(x * x, n/2) * x;
	}
	
}
