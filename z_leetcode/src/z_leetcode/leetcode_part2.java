package z_leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class leetcode_part2 {

	//51. N-Queens
	public List<List<String>> solveNQueens(int n){
		List<List<String>> result = new ArrayList<List<String>>();
		int[] queens = new int[n];
		checkNQueens(n, queens, 0, result);		
		return result;
	}

	private void checkNQueens(int n, int[] queens, int column, List<List<String>> result) {
		if( column == n){
			List<String> oneSolution = new ArrayList<String>();
			for(int i : queens){
				String oneLine = "";
				for(int j = 0; j <= n - 1; j++ ){
					String ij = ( j == i) ? "Q":".";
					oneLine = oneLine + ij;
				}
				oneSolution.add(oneLine);
			}
			result.add(oneSolution);
			return;
		}
		for(int i = 0; i <= n - 1; i++){
			queens[column] = i;
			if( !isValid(queens, column)) continue;
			else {
				checkNQueens(n, queens, column + 1, result);
			}
		}
	}

	private boolean isValid(int[] queens, int column) {
		for(int i = 0; i <= column - 1; i++){
			if(queens[i] == queens[column] ||
					Math.abs(queens[i] - queens[column]) == column - i)
				return false;
		}
		return true;
	}
	
	//52. N-Queens II
	// return the total number of distinct solutions
	public int totalNQueens(int n) {
		int result = 0;
		int[] queens = new int[n];
		result = checkNQueens(n, queens, 0, result);		
		return result;
	}

	private int checkNQueens(int n, int[] queens, int column, int result) {
		if( column == n){
			return result + 1;
		}
		for(int i = 0; i <= n - 1; i++){
			queens[column] = i;
			if( !isValid(queens, column)) continue;
			else {
				result = checkNQueens(n, queens, column + 1, result);
			}
		}
		return result;
	}
	
	//53. Maximum Subarray
	public int maxSubArray(int[] nums) {
        int max = nums[0], begin = 0;
        int sum = 0;
        for(int i = 0; i < nums.length; i++){
        	if( nums[i] > 0){
        		begin = i;
        		break;
        	}else max = Math.max(nums[i], max);
        }
        for( int i = begin; i < nums.length; i++){
        	sum = sum + nums[i];
        	if(sum > max) max = sum;
        	else if ( sum < 0){
        		sum = 0;
        	}
        }
        return max;
    }
	
	//54. Spiral Matrix
	public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<Integer>();
        if(matrix.length == 0 || matrix[0].length == 0) return res;        
        int top = 0;
        int bottom = matrix.length-1;
        int left = 0;
        int right = matrix[0].length-1;
        while(true){
            for(int i = left; i <= right; i++) res.add(matrix[top][i]);
            top++;
            if(left > right || top > bottom) break;
            
            for(int i = top; i <= bottom; i++) res.add(matrix[i][right]);
            right--;
            if(left > right || top > bottom) break;
            
            for(int i = right; i >= left; i--) res.add(matrix[bottom][i]);
            bottom--;
            if(left > right || top > bottom) break;
            
            for(int i = bottom; i >= top; i--) res.add(matrix[i][left]);
            left++;
            if(left > right || top > bottom) break;
        }
        return res;
    }
	
	//55. Jump Game
	public boolean canJump(int[] nums) {
        boolean result = false;
        int next = 0;
        for(int i = 0; i < nums.length; i++){
        	if( i > next ) break;
        	int maxJump = i + nums[i];
        	if( maxJump >= nums.length - 1){
        		return true;
        	}
        	if(maxJump > next ) next = maxJump;
        }
        return result;
    }
	
	//56. Merge Intervals
	//改写Comparator
	public List<Interval> merge(List<Interval> intervals) {
		if (intervals.size() <= 1)
	        return intervals;
	    
	    // Sort by ascending starting point using an anonymous Comparator
	    Collections.sort(intervals, new Comparator<Interval>() {
	        @Override
	        public int compare(Interval i1, Interval i2) {
	            return Integer.compare(i1.start, i2.start);
	        }
	    });
	    
	    List<Interval> result = new ArrayList<Interval>();
	    int start = intervals.get(0).start;
	    int end = intervals.get(0).end;
	    
	    for (Interval interval : intervals) {
	        if (interval.start <= end) // Overlapping intervals, move the end if needed
	            end = Math.max(end, interval.end);
	        else {                     // Disjoint intervals, add the previous one and reset bounds
	            result.add(new Interval(start, end));
	            start = interval.start;
	            end = interval.end;
	        }
	    }
	    
	    // Add the last interval
	    result.add(new Interval(start, end));
	    return result;
	}
	
	//57. Insert Interval
	public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
		if (intervals.size() == 0) return new ArrayList<>(Arrays.asList(newInterval));
		intervals.add(newInterval);
		Collections.sort(intervals, new Comparator<Interval>() {
	        @Override
	        public int compare(Interval i1, Interval i2) {
	            return Integer.compare(i1.start, i2.start);
	        }
	    });
		List<Interval> result = new ArrayList<Interval>();
	    int start = intervals.get(0).start;
	    int end = intervals.get(0).end;
	    
	    for (Interval interval : intervals) {
	        if (interval.start <= end) // Overlapping intervals, move the end if needed
	            end = Math.max(end, interval.end);
	        else {                     // Disjoint intervals, add the previous one and reset bounds
	            result.add(new Interval(start, end));
	            start = interval.start;
	            end = interval.end;
	        }
	    }
	    result.add(new Interval(start, end));
		return result;
    }
	
	//58. Length of Last Word
    public int lengthOfLastWord(String s) {
        String[] strings = s.split(" ");
        if(strings.length == 0) return 0;
        return strings[strings.length - 1].length();
    }	
	
    //59. Spiral Matrix II
    public int[][] generateMatrix(int n) {
        int[][] result = new int[n][n];
        int left = 0,top = 0;
    	int right = n -1,down = n - 1;
    	int count = 1;
    	while (left <= right) {
    		for (int j = left; j <= right; j ++) {
    			result[top][j] = count++;
    		}
    		top ++;
    		for (int i = top; i <= down; i ++) {
    			result[i][right] = count ++;
    		}
    		right --;
    		for (int j = right; j >= left; j --) {
    			result[down][j] = count ++;
    		}
    		down --;
    		for (int i = down; i >= top; i --) {
    			result[i][left] = count ++;
    		}
    		left ++;
    	}
        return result;
    }
    
    //60. Permutation Sequence
    public String getPermutation(int n, int k) {
        List<Integer> numbers = new ArrayList<>();
        int[] factorial = new int[n+1];
        StringBuilder sb = new StringBuilder();
        
        // create an array of factorial lookup
        int sum = 1;
        factorial[0] = 1;
        for(int i=1; i<=n; i++){
            sum *= i;
            factorial[i] = sum;
        }
        // factorial[] = {1, 1, 2, 6, 24, ... n!}
        
        // create a list of numbers to get indices
        for(int i=1; i<=n; i++){
            numbers.add(i);
        }
        // numbers = {1, 2, 3, 4}
        k--;
        for(int i = 1; i <= n; i++){
            int index = k/factorial[n-i];
            sb.append(String.valueOf(numbers.get(index)));
            numbers.remove(index);
            k-=index*factorial[n-i];
        }
        return String.valueOf(sb);
	}
    
    //61. Rotate List
    public ListNode rotateRight(ListNode head, int k) {
    	if (head==null||head.next==null) return head;
        ListNode dummy=new ListNode(0);
        dummy.next=head;
        ListNode fast=dummy,slow=dummy;

        int i;
        for (i=0;fast.next!=null;i++)//Get the total length 
        	fast=fast.next;
        
        for (int j=i-k % i;j>0;j--) //Get the i-n%i th node
        	slow=slow.next;
        
        fast.next=dummy.next; //Do the rotation
        dummy.next=slow.next;
        slow.next=null;
        
        return dummy.next;
    }
    
    //62. Unique Paths
    public int uniquePaths(int m, int n) {
    	if( m == 0 || n == 0) return 0;
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        for( int i = 0; i < n; i++) dp[0][i] = 1;
        for( int i = 0; i < m; i++) dp[i][0] = 1;
        for( int i = 1; i < m; i++){
        	for( int j = 1; j < n; j++)
        		dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
        }
        return dp[m-1][n-1];
    }
    
    //63. Unique Paths II
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
    	int m = obstacleGrid.length, n = obstacleGrid[0].length;
    	if( m == 0 || n == 0) return 0;
        if(obstacleGrid[0][0] == 1) return 0;
        int[][] dp = new int[m][n];
        dp[0][0] = 1;
        for( int i = 1; i < n; i++) {
        	if(obstacleGrid[0][i] == 1) dp[0][i] = 0;
        	else dp[0][i] = dp[0][i - 1];
        }
        for( int i = 1; i < m; i++) {
        	if(obstacleGrid[i][0] == 1) dp[i][0] = 0;
        	else dp[i][0] = dp[i - 1][0];
        }
        for( int i = 1; i < m; i++){
        	for( int j = 1; j < n; j++){
        		if(obstacleGrid[i][j] == 1) dp[i][j] = 0;
        		else dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
        	}
        }
        return dp[m-1][n-1];
    }
    
    //64. Minimum Path Sum
    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        if( m == 0 || n == 0) return 0;
        int[][] sums = new int[m][n];
        sums[0][0] = grid[0][0];
        for( int i = 1; i < n; i++) sums[0][i] = grid[0][i] + sums[0][i - 1];
        for( int i = 1; i < m; i++) sums[i][0] = grid[i][0] + sums[i - 1][0];
        for( int i = 1; i < m; i++){
        	for( int j = 1; j < n; j++)
        		sums[i][j] = Math.min(sums[i - 1][j], sums[i][j - 1]) + grid[i][j];
        }
        return sums[m-1][n-1];
    }
	
    //65. Valid Number
//    We start with trimming.
//    If we see [0-9] we reset the number flags.
//    We can only see . if we didn't see e or ..
//    We can only see e if we didn't see e but we did see a number. We reset numberAfterE flag.
//    We can only see + and - in the beginning and after an e
//    any other character break the validation.
//    At the and it is only valid if there was at least 1 number and if we did see an e then a number after it as well.
//    So basically the number should match this regular expression:
//    [-+]?(([0-9]+(.[0-9]*)?)|.[0-9]+)(e[-+]?[0-9]+)?
    public boolean isNumber(String s) {
        s = s.trim();   
        boolean pointSeen = false;
        boolean eSeen = false;
        boolean numberSeen = false;
        boolean numberAfterE = true;
        for(int i=0; i<s.length(); i++) {
            if('0' <= s.charAt(i) && s.charAt(i) <= '9') {
                numberSeen = true;
                numberAfterE = true;
            } else if(s.charAt(i) == '.') {
                if(eSeen || pointSeen) {
                    return false;
                }
                pointSeen = true;
            } else if(s.charAt(i) == 'e') {
                if(eSeen || !numberSeen) {
                    return false;
                }
                numberAfterE = false;
                eSeen = true;
            } else if(s.charAt(i) == '-' || s.charAt(i) == '+') {
                if(i != 0 && s.charAt(i-1) != 'e') {
                    return false;
                }
            } else {
                return false;
            }
        }
        return numberSeen && numberAfterE;
    }
    
    //66. Plus One
    public int[] plusOne(int[] digits) {
    	int n = digits.length;
        for(int i=n-1; i>=0; i--) {
            if(digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }
        int[] newNumber = new int [n+1];
        newNumber[0] = 1;       
        return newNumber;
    }
    
    //67. Add Binary
    public String addBinary(String a, String b) {
    	StringBuilder sb = new StringBuilder();
        int i = a.length() - 1, j = b.length() -1, carry = 0;
        while (i >= 0 || j >= 0) {
            int sum = carry;
            if (j >= 0) sum += b.charAt(j--) - '0';
            if (i >= 0) sum += a.charAt(i--) - '0';
            sb.append(sum % 2);
            carry = sum / 2;
        }
        if (carry != 0) sb.append(carry);
        return sb.reverse().toString();
    }
    
    //68. Text Justification
    public List<String> fullJustify(String[] words, int maxWidth) {
    	List<String> result = new ArrayList<String>();
    	int start = 0;
    	int empty = maxWidth;
        for(int i = 0; i < words.length; i++){
        	empty = empty - words[i].length();
        	if( empty == 0){
        		String oneline = "";
        		for( int j = start; j < i; j++){
        			oneline += words[j] + " ";
        		}
        		oneline += words[i];
        		result.add(oneline);
        		start = i + 1;
        		empty = maxWidth;
        	}else if( empty < 0){
        		String oneline = "";
        		int moreSpace = empty + words[i].length() + 1;
        		if( start == i - 1){
        			oneline = words[start];
        			for(int k = 0; k < moreSpace; k++){
        				oneline += " ";
        			}
        			result.add(oneline);
        		}else {
        			int count = i - start;
        			int eachSpace = moreSpace/(count - 1);
        			int mod = moreSpace%(count - 1);
        			for( int j = start; j < i - 1; j++){
            			oneline += words[j] + " ";
            			for( int k = 0; k < eachSpace; k++){
            				oneline +=" ";
            			}
            			if( j - start + 1 <= mod){
            				oneline += " ";
            			}
            		}
            		oneline += words[i - 1];
            		result.add(oneline);
        		}
        		start = i;
        		empty = maxWidth - words[i].length();
        		if( empty == 0){
            		result.add(words[i]);
            		start = i + 1;
            		empty = maxWidth;
        		} else empty--;
        	}else{
        		empty--;
        	}
        }
        if(empty != maxWidth){
        	String oneline = "";
        	empty = maxWidth;
    		for( int j = start; j < words.length - 1; j++){
    			oneline += words[j] + " ";
    			empty = empty - words[j].length() - 1;
    		}
    		oneline += words[words.length - 1];
    		empty = empty - words[words.length - 1].length();
    		for(int j = 0; j < empty; j++){
    			oneline += " ";
    		}
    		result.add(oneline);
        }
        return result;
    }
    
    //69. Sqrt(x)
    public int mySqrt(int x) {
    	if (x == 0)
            return 0;
        int left = 1, right = Integer.MAX_VALUE;
        while (true) {
            int mid = left + (right - left)/2;
            if (mid > x/mid) {
                right = mid - 1;
            } else {
                if (mid + 1 > x/(mid + 1))
                    return mid;
                left = mid + 1;
            }
        }
    }
    
    //70. Climbing Stairs
    public int climbStairs(int n) {
    	if(n <= 0) return 0;
        if(n == 1) return 1;
        if(n == 2) return 2;        
        int one_step_before = 2;
        int two_steps_before = 1;
        int all_ways = 0;       
        for(int i=2; i<n; i++){
        	all_ways = one_step_before + two_steps_before;
        	two_steps_before = one_step_before;
            one_step_before = all_ways;
        }
        return all_ways;
    }
    
    //71. Simplify Path
    public String simplifyPath(String path) {
    	Stack<String> stack = new Stack<String>();
        Set<String> skip = new HashSet<>(Arrays.asList("..",".",""));
        for (String dir : path.split("/")) {
            if (dir.equals("..") && !stack.isEmpty()) stack.pop();
            else if (!skip.contains(dir)) stack.push(dir);
        }
        String res = "";
        for (String dir : stack) res = res + "/" + dir;
        return res.isEmpty() ? "/" : res;
    }
    
    //72. Edit Distance
    public int minDistance(String word1, String word2) {
    	int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];
        dp[0][0] = 0;
        for(int i = 1; i <= m; i++){
        	dp[i][0] = 1 + dp[i - 1][0];
        }
        for(int j = 1; j <= n; j++){
        	dp[0][j] = 1 + dp[0][j - 1];
        }
        for(int i = 1; i <= m; i++){
        	for(int j = 1; j <= n; j++){
        		if(word1.charAt(i - 1) == word2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
        		else dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
        	}
        }
    	return dp[m][n];
    }
    
    //73. Set Matrix Zeroes
    //用第一行 第一列来确定该行该列是否需要变成0
    public void setZeroes(int[][] matrix) {
        boolean fr = false,fc = false;
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] == 0) {
                    if(i == 0) fr = true;
                    if(j == 0) fc = true;
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }
        for(int i = 1; i < matrix.length; i++) {
            for(int j = 1; j < matrix[0].length; j++) {
                if(matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        if(fr) {
            for(int j = 0; j < matrix[0].length; j++) {
                matrix[0][j] = 0;
            }
        }
        if(fc) {
            for(int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }
    
    //74. Search a 2D Matrix
    public boolean searchMatrix(int[][] matrix, int target) {
    	if( matrix.length == 0 || matrix[0].length == 0) return false;
        int i = 0, j = matrix[0].length - 1;
        while (i < matrix.length && j >= 0) {
                if (matrix[i][j] == target) {
                    return true;
                } else if (matrix[i][j] > target) {
                    j--;
                } else {
                    i++;
                }
            }
        return false;
    }
    
    //75. Sort Colors
    public void sortColors(int[] nums) {
    	int count0 = 0, count1 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {count0++;}
            if (nums[i] == 1) {count1++;}
        }
        for(int i = 0; i < nums.length; i++) {
            if (i < count0) {nums[i] = 0;}
            else if (i < count0 + count1) {nums[i] = 1;}
            else {nums[i] = 2;}
        }
    }
    
    //76. Minimum Window Substring
//    用一个哈希表记录目标字符串每个字母的个数，一个哈希表记录窗口中每个字母的个数。
//    先找到第一个有效的窗口，用两个指针标出它的上界和下界。
//    然后每次窗口右界向右移时，将左边尽可能的右缩，右缩的条件是窗口中字母的个数不小于目标字符串中字母的个数。
    public String minWindow(String s, String t) {
    	int begin = -1, found = 0;
    	String result = s;
    	HashMap<Character, Integer> t_map = new HashMap<Character, Integer>();
    	for( int i = 0; i < t.length(); i++){
    		char ch = t.charAt(i);
    		if(t_map.containsKey(ch)) t_map.put(ch, t_map.get(ch) + 1);
    		else t_map.put(ch, 1);
    	}
    	HashMap<Character, Integer> s_map = new HashMap<Character, Integer>();
    	for( int i = 0; i < s.length(); i++){
    		char ch = s.charAt(i);
    		if( t_map.containsKey( ch )){
    			if( s_map.containsKey( ch )) s_map.put(ch, s_map.get(ch) + 1);
    			else s_map.put(ch, 1);
    			int s_count = s_map.get(ch);
    			int t_count = t_map.get(ch);
    			if( s_count == t_count) {
    				found++;
    			}
    		}
    		if( found == t_map.size()){
    			if( i - begin <= result.length()){
    				result = s.substring(begin + 1, i + 1);
    			}
    			for( int j = begin + 1; j <= i; j++){
    				char ch2 = s.charAt(j);
    				if(t_map.containsKey(ch2)){
    					int count = s_map.get(ch2) - 1;
    					if( count < t_map.get(ch2) ){
    						if( count == 0) s_map.remove(ch2);
    						else s_map.put(ch2, count);
    						begin = j;
    						found--;
    						break;
    					}else{
    						s_map.put(ch2, count);
    						if( i - j < result.length()) result = s.substring(j + 1, i + 1);
    					}
    				}else {
    					if( i - j < result.length()) result = s.substring(j + 1, i + 1);
    				}
    			}
    		}
    	}
    	if( begin == -1) return "";
    	return result;
    }
    
    //77. Combinations
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        getOneCombineSolution(result, new ArrayList<Integer>(), n, k);
        return result;
    }

	private void getOneCombineSolution(List<List<Integer>> result, ArrayList<Integer> oneSolution, int n, int k) {
		if(oneSolution.size() == k ){
			result.add(new ArrayList<>(oneSolution));
			return;
		}
		for( int i = 1; i <= n; i++){
			if(oneSolution.size() == 0 || (oneSolution.size() > 0 && i > oneSolution.get(oneSolution.size() - 1))) {
				oneSolution.add(i);
				getOneCombineSolution(result, oneSolution, n, k);
				oneSolution.remove(oneSolution.size() - 1);
			}
		}
	}
    
	//78. Subsets
	public List<List<Integer>> subsets(int[] nums) {
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
	
	//79. Word Search
	public boolean exist(char[][] board, String word) {
		boolean[][] dp = new boolean[board.length][board[0].length];
        for(int i = 0; i < board.length; i++){
        	for( int j = 0; j < board[i].length; j++){
        		if( wordExist(board, dp, word, i, j, 0) ) return true;
        	}
        }
        return false;
    }

	private boolean wordExist(char[][] board, boolean[][] dp, String word, int i, int j, int k) {
		if(k == word.length() ) return true;
		if( i < 0 || j < 0 || i >= board.length || j >= board[0].length || dp[i][j]) return false;
		if( board[i][j] != word.charAt(k)) return false;
		dp[i][j] = true;
		if(wordExist(board, dp, word, i-1, j, k+1) || 
				wordExist(board, dp, word, i+1, j, k+1) ||
				wordExist(board, dp, word, i, j-1, k+1) || 
				wordExist(board, dp, word, i, j+1, k+1)){
            return true;
        }     
		dp[i][j] = false;
        return false;
	}
	
	//80. Remove Duplicates from Sorted Array II
	public int removeDuplicates(int[] nums) {
		int i = 0;
		for (int n : nums)
			if (i < 2 || n > nums[i - 2]) nums[i++] = n;
		return i;
    }
	
	//81. Search in Rotated Sorted Array II
	public boolean search(int[] nums, int target) {
		int start = 0, end = nums.length - 1, mid = -1;
        while(start <= end) {
            mid = (start + end) / 2;
            if (nums[mid] == target) {
                return true;
            }
            //If we know for sure right side is sorted or left side is unsorted
            if (nums[mid] < nums[end] || nums[mid] < nums[start]) {
                if (target > nums[mid] && target <= nums[end]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            //If we know for sure left side is sorted or right side is unsorted
            } else if (nums[mid] > nums[start] || nums[mid] > nums[end]) {
                if (target < nums[mid] && target >= nums[start]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            //If we get here, that means nums[start] == nums[mid] == nums[end], then shifting out
            //any of the two sides won't change the result but can help remove duplicate from
            //consideration, here we just use end-- but left++ works too
            } else {
                end--;
            }
        }
        return false;
    }
	
	//82. Remove Duplicates from Sorted List II
	// remove all duplicates
	public ListNode deleteDuplicatesII(ListNode head) {
        if( head == null || head.next == null) return head;
		
        if( head.val == head.next.val){
        	while( head.next != null && head.val == head.next.val){
        		head = head.next;
        	}
        	return deleteDuplicates(head.next);
        }else head.next = deleteDuplicates(head.next);
        return head;
    }
	
	//83 Remove Duplicates from Sorted List
	// keep first duplicates
	public ListNode deleteDuplicates(ListNode head) {
        if( head == null || head.next == null) return head;
        if( head.val == head.next.val){
        	while( head.next != null && head.val == head.next.val){
        		head = head.next;
        	}
        	return deleteDuplicates(head);
        }else head.next = deleteDuplicates(head.next);
        return head;
    }
	
	//84 Largest Rectangle in Histogram
	//!!!!!!!!!!!!hard!!!!!!!!!!didn't fix
	public int largestRectangleArea(int[] heights) {
		Stack<Integer> stack = new Stack<Integer>();
		int i = 0;
		int maxArea = 0;
		int[] h = new int[heights.length + 1];
		h = Arrays.copyOf(heights, heights.length + 1);
		while(i < h.length){
			if(stack.isEmpty() || h[stack.peek()] <= h[i]){
				stack.push(i++);
			}else {
				int t = stack.pop();
				maxArea = Math.max(maxArea, h[t] * (stack.isEmpty() ? i : i - stack.peek() - 1));
			}
		}
		return maxArea;
	}
	
	//85. Maximal Rectangle
	//!!!!!!!!!!!!hard!!!!!!!!!!didn't fix
	public int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        int n = m == 0 ? 0 : matrix[0].length;
        int[][] height = new int[m][n + 1];
        int maxArea = 0;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++) {
                if(matrix[i][j] == '0'){
                    height[i][j] = 0;
                }else {
                    height[i][j] = i == 0 ? 1 : height[i - 1][j] + 1;
                }
            }
        }
        for(int i = 0; i < m; i++){
            int area = maxAreaInHist(height[i]);
            if(area > maxArea){
                maxArea = area;
            }
        }
        return maxArea;
     }
     
     private int maxAreaInHist(int[] height){
         Stack<Integer> stack = new Stack<Integer>();
         int i = 0;
         int maxArea = 0;
         while(i < height.length){
             if(stack.isEmpty() || height[stack.peek()] <= height[i]){
                 stack.push(i++);
             }else {
                 int t = stack.pop();
                 maxArea = Math.max(maxArea, height[t] * (stack.isEmpty() ? i : i - stack.peek() - 1));
             }
         }
         return maxArea;
     }
     
     //86. Partition List
     public ListNode partition(ListNode head, int x) {
    	 if(head == null || head.next == null) return head;         
         ListNode dummy1 = new ListNode(0), dummy2 = new ListNode(0);  //dummy heads of the 1st and 2nd queues
         ListNode curr1 = dummy1, curr2 = dummy2;      //current tails of the two queues;
         while (head!=null){
             if (head.val<x) {
                 curr1.next = head;
                 curr1 = head;
             }else {
                 curr2.next = head;
                 curr2 = head;
             }
             head = head.next;
         }
         curr2.next = null;          //important! avoid cycle in linked list. otherwise u will get TLE.
         curr1.next = dummy2.next;
         return dummy1.next;
     }
     
     //87. Scramble String
     //!!!!!!!!!hard!!!!!!!!!!!!!!!!!!!
     public boolean isScramble(String s1, String s2) {
         if(s1.length() != s2.length())  
             return false;  
         
         if(s1.length()==1 && s2.length()==1)
             return s1.charAt(0) == s2.charAt(0);  
     
        char[] t1 = s1.toCharArray(), t2 = s2.toCharArray();
        Arrays.sort(t1);
        Arrays.sort(t2);
        if(!new String(t1).equals(new String(t2)))
          return false;
          
        if(s1.equals(s2)) 
          return true;
          
        for(int split = 1; split < s1.length(); split++){
            String s11 = s1.substring(0, split);
            String s12 = s1.substring(split);
            
            String s21 = s2.substring(0, split);
            String s22 = s2.substring(split);
            if(isScramble(s11, s21) && isScramble(s12, s22))
              return true;
            
            s21 = s2.substring(0, s2.length() - split);
            s22 = s2.substring(s2.length() - split);
            if(isScramble(s11, s22) && isScramble(s12, s21))
             return true;
        }
        return false;
     }
     
     //88. Merge Sorted Array
     public void merge(int[] nums1, int m, int[] nums2, int n) {
    	 while(m > 0 && n > 0){
             if(nums1[m-1] > nums2[n-1]){
                 nums1[m+n-1] = nums1[m-1];
                 m--;
             }else{
                 nums1[m+n-1] = nums2[n-1];
                 n--;
             }
         }
         while(n > 0){
             nums1[m+n-1] = nums2[n-1];
             n--;
         }
     }
     
     //89. Gray Code
//     1位格雷码有两个码字 
//     (n+1)位格雷码中的前2^n个码字等于n位格雷码的码字，按顺序书写，加前缀0 
//     (n+1)位格雷码中的后2^n个码字等于n位格雷码的码字，按逆序书写，加前缀1。
     public ArrayList<Integer> grayCode(int n) {  
         if(n==0) {  
             ArrayList<Integer> result = new ArrayList<Integer>();  
             result.add(0);  
             return result;  
         }  
           
         ArrayList<Integer> result = grayCode(n-1);  
         int addNumber = 1 << (n-1);
         int originalsize=result.size();
         
         for(int i=originalsize-1;i>=0;i--) {  
             result.add(addNumber + result.get(i));  
         }  
         return result;  
     }     
     
    //90. Subsets II
    public List<List<Integer>> subsetsWithDup(int[] nums) {
    	Arrays.sort(nums);
    	List<List<Integer>> result = new ArrayList<List<Integer>>();
    	oneSubsetWithDup(result, new ArrayList<Integer>(), nums, 0);
    	result.add(new ArrayList<>());
    	return result;
    }
	private void oneSubsetWithDup(List<List<Integer>> result, ArrayList<Integer> oneSubset, int[] nums, int position) {
		for( int i = position; i < nums.length; i++){
			if( i > position && nums[i] == nums[ i - 1]){
				continue;
			}
			oneSubset.add(nums[i]);
			result.add(new ArrayList<>(oneSubset));
			oneSubsetWithDup(result, oneSubset, nums, i + 1);
			oneSubset.remove(oneSubset.size() - 1);
		}
	}
     
    //91. Decode Ways
	//动态规划 dp[i + 1]
	public int numDecodings(String s) {  
        if (s.length() == 0 || s == null || s.charAt(0) == '0') 
            return 0; 

        int[] dp = new int[s.length()+1];  
        dp[0] = 1;  
        
        if (isValid(s.substring(0,1)))
            dp[1] = 1;  
        else 
            dp[1] = 0; 
        
        for(int i=2; i<=s.length();i++){  
            if (isValid(s.substring(i-1,i)))  
                dp[i] += dp[i-1];  
            if (isValid(s.substring(i-2,i)))  
                dp[i] += dp[i-2];  
        }  
        return dp[s.length()];  
    }  
      
    public boolean isValid(String s){  
        if (s.charAt(0)=='0') 
            return false;  
        int code = Integer.parseInt(s);  
        return code >= 1 && code <= 26;  
    }
	
	//92. Reverse Linked List II
//    经典的题目就是链表逆序啦，一般的链表逆序是让把链表从前到后都逆序，这个是给定了起始位置和结束位置，方法是一样的。
//    就是维护3个指针，startpoint，node1和node2。
//    startpoint永远指向需要开始reverse的点的前一个位置。
//    node1指向正序中第一个需要rever的node，node2指向正序中第二个需要reverse的node。 
//    交换后，node1 在后，node2在前。这样整个链表就逆序好了。
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if(head == null || head.next == null || m == n) return head;
        ListNode newhead = new ListNode(-1);
        newhead.next = head;
        ListNode startpoint = newhead;
        ListNode node1 = null;
        ListNode node2 = null;
        
        for(int i = 0; i < n; i++){
        	if( i < m - 1) startpoint = startpoint.next;
        	else if( i == m - 1) {
        		node1 = startpoint.next;
        		node2 = node1.next;
        	}else {
        		node1.next = node2.next;//node1交换到node2的后面
        		node2.next = startpoint.next;//node2交换到最开始
        		startpoint.next = node2;//node2作为新的点
        		node2 = node1.next;//node2回归到node1的下一个，继续遍历
        	}
        }
        return newhead.next;
    }
    
    //93. Restore IP Addresses
    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<String>();
        getOneIPAddress(s, result, new ArrayList<String>(), 0);
        return result;
    }

	private void getOneIPAddress(String s, List<String> result, ArrayList<String> arrayList, int position) {
		if(position >= s.length()) return;
		if( arrayList.size() == 3){
			if( s.length() - position > 3) return;
			int lastSum = Integer.parseInt( s.substring(position, s.length()) );
			if( (lastSum >= 1 && lastSum <= 255 && s.charAt(position) != '0') 
					|| s.substring(position, s.length()).equals("0")) {
				String oneResult = "";
				for(String string : arrayList){
					oneResult += string + ".";
				}
				oneResult += s.substring(position, s.length());
				result.add(oneResult);
				return;
			}
		}
		for( int i = position; i < position + 3 && i < s.length(); i++){
			int oneNum = Integer.parseInt( s.substring(position, i + 1) );
			if( (oneNum >= 1 && oneNum <= 255 && s.charAt(position) != '0') 
					|| s.substring(position, i + 1).equals("0")){
				arrayList.add(s.substring(position, i + 1));
				getOneIPAddress(s, result, arrayList, i + 1);
				arrayList.remove(arrayList.size() - 1);
			}
		}
	}
    
	//94. Binary Tree Inorder Traversal
	//inorder traversal
	public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        Stack<TreeNode> st = new Stack<TreeNode>();
        TreeNode cur = root;
        while( cur != null || !st.isEmpty()){
        	while(cur != null){
        		st.push(cur);
        		cur = cur.left;
            }
            cur = st.pop();
            result.add(cur.val);
            cur = cur.right;
        }
        return result;
    }

	//95. Unique Binary Search Trees II
//	当数组为 1，2，3，4，.. i，.. n时，基于以下原则的BST建树具有唯一性：
//	以i为根节点的树，其左子树由[1, i-1]构成， 其右子树由[i+1, n]构成。 
	public ArrayList<TreeNode> generateTrees(int n) {
		if( n == 0) return new ArrayList<TreeNode>();
        return generateTrees(1, n);//从1作为root开始，到n作为root结束
    }
     
    private ArrayList<TreeNode> generateTrees(int left, int right){
    	ArrayList<TreeNode> result = new ArrayList<TreeNode>();
    	if( left > right){
    		result.add(null);
    		return result;
    	}
    	for( int i = left; i <= right; i++){
    		ArrayList<TreeNode> leftTrees = generateTrees(left, i - 1);//以i作为根节点，左子树由[1,i-1]构成
    		ArrayList<TreeNode> rightTrees = generateTrees(i + 1, right);//右子树由[i+1, n]构成
    		//存储所有可能行
    		for( TreeNode leftTree : leftTrees){
    			for( TreeNode rightTree : rightTrees){
    				TreeNode oneRes = new TreeNode(i);
    				oneRes.left = leftTree;
    				oneRes.right = rightTree;
    				result.add(oneRes);
    			}
    		}
    	}
        return result;
    }
	
    //96. Unique Binary Search Trees
    //动态规划, dp
    //递推式是Ct+1 += Ci*Ct-i(0<= i <= t)
    //令num = t+1
    //则 t = num-1;
    //因此递推式化为：
    //Cnum += Ci*Cnum-1-i(0<=i<=num-1, 1<=num<=n)
    //C0 = 1
    public int numTrees(int n) {
    	int [] dp = new int[n+1];
        dp[0] = 1;
        dp[1] = 1;
        for(int level = 2; level <= n; level++)
            for(int root = 1; root <= level; root++)
                dp[level] += dp[level - root] * dp[root - 1];
        return dp[n];	 
    }

	//97. Interleaving String
    //“When you see string problem that is about subsequence or matching,
    //	dynamic programming method should come to your mind naturally. ”
    public boolean isInterleave(String s1, String s2, String s3) {
        if(s3.length() != s1.length() + s2.length()) return false;
        boolean[][] dp = new boolean[s1.length() + 1][s2.length() + 1];
        dp[0][0] = true;
        for(int i = 0; i < s1.length() && s1.charAt(i) == s3.charAt(i); i++)
        	dp[i + 1][0] = true;
        for(int j = 0; j < s2.length() && s2.charAt(j) == s3.charAt(j); j++)
        	dp[0][j + 1] = true;
        for( int i = 0; i < s1.length(); i++){
        	for( int j = 0; j < s2.length(); j++){
        		char c = s3.charAt(i + j + 1);
        		if( s1.charAt(i) == c && dp[i][j + 1]) dp[i + 1][j + 1] = true;
        		if( s2.charAt(j) == c && dp[i + 1][j]) dp[i + 1][j + 1] = true;
        	}
        }
        return dp[s1.length()][s2.length()];
    }
    
    //98. Validate Binary Search Tree
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    public boolean isValidBST(TreeNode root, long minVal, long maxVal) {
        if (root == null) return true;
        if (root.val >= maxVal || root.val <= minVal) return false;
        return isValidBST(root.left, minVal, root.val) && isValidBST(root.right, root.val, maxVal);
    }
    
    //99. Recover Binary Search Tree
    //!!!!!!!!!hard
    
    //100. Same Tree
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null) return true;
        if(p == null || q == null) return false;
        if(p.val == q.val)
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        return false;
    }
}
