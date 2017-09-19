package leetcode_bytag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import z_leetcode.TreeNode;

public class DFSTag {

	//329. Longest Increasing Path in a Matrix
	//use cache[i][j] to save the max number in matrix[i][j]
	//matrix maybe []!!!!!!!!
	//use int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}} to determint next point
	public int longestIncreasingPath(int[][] matrix) {
		if(matrix.length == 0 || matrix[0].length == 0) return 0;
		int[][] cache = new int[matrix.length][matrix[0].length];
		int res = 1;
        for(int i = 0; i < matrix.length; i++) {
        	for(int j = 0; j < matrix[0].length; j++) {
        		res = Math.max(res, getDirection(matrix, i ,j, cache));
        	}
        }
		return res;
    }
	private int getDirection(int[][] matrix, int i, int j, int[][] cache) {
		if(cache[i][j] > 0) return cache[i][j];
		int res = 1;
		for(int[] dir : dirs) {
			int i1 = i + dir[0], j1 = j + dir[1];
			if(i1 < 0 || i1 > matrix.length - 1 || j1 < 0 || j1 > matrix[0].length - 1 || matrix[i1][j1] <= matrix[i][j]) continue;
			res = Math.max(res, getDirection(matrix, i1, j1, cache) + 1);
		}
		cache[i][j] = res;
		return res;
	}
	
	//332. Reconstruct Itinerary
	//use priorityqueue
	//dfs
	public List<String> findItinerary(String[][] tickets) {
        HashMap<String, PriorityQueue<String>> maps = new HashMap<String, PriorityQueue<String>>();
        for(String[] ticket : tickets) {
        	PriorityQueue<String> q = maps.getOrDefault(ticket[0], new PriorityQueue<String>());
        	q.add(ticket[1]);
        	maps.put(ticket[0], q);
        }
        List<String> res = new ArrayList<String>();
        getList(maps, res, "JFK");
        return res;
    }
	private void getList(HashMap<String, PriorityQueue<String>> maps, List<String> res, String str) {
		while(maps.containsKey(str) && !maps.get(str).isEmpty()) {
			getList(maps, res, maps.get(str).poll());
		}
		res.add(0, str);
	}
	
	//337. House Robber III //dfs
	//nums[0]����root�����ֵ�� nums[1]������root�����ֵ // nums[1] = max(left[0], left[1]) + max(right[0], right[1])
	public int rob(TreeNode root) {
        int[] nums = robIIIHelper(root);
        return Math.max(nums[0], nums[1]);
    }
	private int[] robIIIHelper(TreeNode root) {
		int[] nums = new int[2];
		if(root == null) return nums;
		int[] left = robIIIHelper(root.left);
		int[] right = robIIIHelper(root.right);
		nums[0] = root.val + left[1] + right[1];
		nums[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
		return nums;
	}
	
	//394. Decode String
	public String decodeString(String s) {
        Stack<int[]> st = new Stack<int[]>();
        for(int i = 0; i < s.length(); i++) {
        	if(s.charAt(i) == '[') {
        		int start = 0;
        		for(int j = i - 1; j >= 0; j--) {
        			if(s.charAt(j) < '0' || s.charAt(j) > '9') {
        				start = j + 1;
        				break;
        			}
        		}
        		st.push(new int[] {start, i});
        	} else if(s.charAt(i) == ']') {
        		int[] last = st.pop();
        		String reStr = s.substring(last[1] + 1, i);
        		String count = s.substring(last[0], last[1]);
        		int re = count.length() == 0 ? 0 : Integer.parseInt(count);
        		String replace = "";
        		for(int k = 0; k < re; k++) {
        			replace += reStr;
        		}
        		s = s.substring(0, last[0]) + replace + s.substring(i + 1);
        		i = last[0] + replace.length() - 1;
        	}
        }        
        return s;
    }
	
	//417. Pacific Atlantic Water Flow
	public static final int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
	public List<int[]> pacificAtlantic(int[][] matrix) {
        List<int[]> res = new ArrayList<int[]>();
		if(matrix.length == 0 || matrix[0].length == 0) return res;
		int m = matrix.length, n = matrix[0].length;
        boolean[][] toPacific = new boolean[m][n];
        boolean[][] toAtlantic = new boolean[m][n];
		for(int i = 0; i < m; i++) {
			toPacific[i][0] = true;
			toAtlantic[i][n - 1] = true;
		}
		for(int j = 0; j < n; j++) {
			toPacific[0][j] = true;
			toAtlantic[m - 1][j] = true;
		}
		for(int j = 0; j < n; j++) {
			setPacific(matrix, m, n, 0, j, toPacific);
			setPacific(matrix, m, n, m - 1, j, toAtlantic);
		}
		for(int i = 0; i < m; i++) {
			setPacific(matrix, m, n, i, 0, toPacific);
			setPacific(matrix, m, n, i, n - 1, toAtlantic);
		}
		
		for(int i = 0; i < m; i++) {
			for(int j = 0; j < n; j++) {
				if(toPacific[i][j] && toAtlantic[i][j]) res.add(new int[] {i, j});
			}
		}
        return res;
    }
	private void setPacific(int[][] matrix, int m, int n, int i, int j, boolean[][] used) {
		for(int[] dir : dirs) {
			int x = i + dir[0], y = j + dir[1];
			if(x < 0 || x >= m|| y < 0 || y >= n || used[x][y] || matrix[x][y] < matrix[i][j]) continue;
			used[x][y] = true;
			setPacific(matrix, m, n, x, y, used);
		}
	}
	
	//472. Concatenated Words
	//用TrieNode可以很简单，学习TrieNode!!!!!
	public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> res = new ArrayList<String>();
        //use set to save the previous combine string
        HashSet<String> set = new HashSet<String>(Arrays.asList(words));
        for(String word : words){
        	set.remove(word);
        	if(findAllConcatenatedWordsInADictHelper(word, "", set)) res.add(word);
        	set.add(word);
        }
        return res;
    }
	private boolean findAllConcatenatedWordsInADictHelper(String word, String pre, HashSet<String> set) {
		if(set.contains(word)) return true;
		for(int i = 1; i < word.length(); i++){
			String pre2 = word.substring(0, i);
			if(set.contains(pre2)){
				set.add(pre + pre2);
				if(findAllConcatenatedWordsInADictHelper(word.substring(i), pre + pre2, set)) return true;
			}
		}
		return false;
	}
	
	//473. Matchsticks to Square
	public boolean makesquare(int[] nums) {
		//edge problem!!!!
    	if (nums == null || nums.length < 4) return false;
    	int sum = 0;
        for(int num : nums) sum += num;
        if(sum%4 != 0) return false;
        int len = sum/4;
        Arrays.sort(nums);        
        return makeSquareHelper(nums, len, new int[4], nums.length - 1);
    }
	private boolean makeSquareHelper(int[] nums, int len, int[] sum, int idx) {
		if(idx == -1){
			if(sum[0] == sum[1] && sum[0] == sum[2] && sum[0] == sum[3] && sum[0] == len) return true;
			else return false;
		}
		//sort之后判断最高值是否大于边长，减少时间
		if(nums[idx] > len) return false;
		//dfs
		for(int i = 0; i < sum.length; i++){
			if(sum[i] + nums[idx] > len) continue;
			sum[i] += nums[idx];
			if(makeSquareHelper(nums, len, sum, idx - 1)) return true;
			sum[i] -= nums[idx];
		}
		return false;
	}
	
	//488. Zuma Game
	public int findMinStep(String board, String hand) {
        
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
