package leetcode_contest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import z_leetcode.Employee;
import z_leetcode.TreeNode;

public class Weekly_52 {

	//686. Repeated String Match
	public int repeatedStringMatch(String A, String B) {
		int count = 0;
	    StringBuilder sb = new StringBuilder();
	    while (sb.length() < B.length()) {
	        sb.append(A);
	        count++;
	    }
	    if(sb.toString().contains(B)) return count;
	    if(sb.append(A).toString().contains(B)) return count + 1;
	    return -1;
    }
	
	//687. Longest Univalue Path
	int max = 0;
	public int longestUnivaluePath(TreeNode root) {
        getLongestUnivaluePath(root);
		return max;
    }
	private int getLongestUnivaluePath(TreeNode root) {
		if(root == null) return 0;
		int left = getLongestUnivaluePath(root.left);
		int right = getLongestUnivaluePath(root.right);
		int count = 0, res = 0;
		if(root.left != null && root.val == root.left.val) {
			count += left + 1;
			res = Math.max(res, left + 1);
		}
		if(root.right != null && root.val == root.right.val){
			count += right + 1;
			res = Math.max(res, right + 1);
		}
		max = Math.max(max, count);
		return res;
	}
	
	//690. Employee Importance
	public int getImportance(List<Employee> employees, int id) {
		HashSet<Integer> helper = new HashSet<Integer>();
		helper.add(id);
		int res = 0;
		while(!helper.isEmpty()){
			for(int i = 0; i < employees.size(); i++){
				if(helper.contains(employees.get(i).id)){
					Employee em = employees.get(i);
					res += em.importance;
					helper.remove(em.id);
					helper.addAll(em.subordinates);
				}
			}
		} 
		return res;
    }
	
	//688. Knight Probability in Chessboard
	List<int[]> dirs = new ArrayList<>(Arrays.asList(new int[]{-2, -1}, new int[]{-2, 1}, new int[]{-1, -2}, new int[]{-1, 2}, 
								new int[]{2, -1}, new int[]{2, 1}, new int[]{1, -2}, new int[]{1, 2}));
	//dp[r][c][k], use double 注意数字极限！！！
	public double knightProbability(int N, int K, int r, int c) {
		double[][][] dp = new double[N][N][K + 1];
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++) dp[i][j][0] = 1;
		}
		for(int k = 1; k <= K; k++){
			for(int i = 0; i < N; i++){
				for(int j = 0; j < N; j++){
					for(int[] dir : dirs){
						int x = i + dir[0], y = j + dir[1];
						if(x >= 0 && y >= 0 && x < N && y < N){
							dp[i][j][k] += dp[x][y][k - 1];
						}
					}
				}
			}
		}
		return (double) dp[r][c][K]/Math.pow(8, K);
	}
	
	//689. Maximum Sum of 3 Non-Overlapping Subarrays
	//hard
//	public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
//        
//    }
	
	//637. Average of Levels in Binary Tree
	public List<Double> averageOfLevels(TreeNode root) {
		List<Double> res = new ArrayList<Double>();
		Queue<TreeNode> q = new LinkedList<TreeNode>();
		if(root == null) return res;
		q.offer(root);
		while(!q.isEmpty()){
			double levelSize = q.size(), levelSum = 0;
			for(int i = 0; i < levelSize; i++){
				TreeNode cur = q.poll();
				levelSum += cur.val;
				if(cur.left != null) q.offer(cur.left);
				if(cur.right != null) q.offer(cur.right);
			}
			res.add(levelSum/levelSize);
		}
		return res;
	}
	
	//129. Sum Root to Leaf Numbers
	int res = 0;
	public int sumNumbers(TreeNode root) {
        getSum(root, 0);
        return res;
    }
	private void getSum(TreeNode root, int p) {
		if(root == null) return;
		if(root.left == null && root.right == null) {
			res += p * 10 + root.val;
		}
		if(root.left != null){
			getSum(root.left, p * 10 + root.val);
		}
		if(root.right != null){
			getSum(root.right, p * 10 + root.val);
		}
	}
	
	
	
	
}
