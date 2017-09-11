package mianjing;

import java.util.Arrays;

import z_leetcode.TreeNode;

public class amazon {

	//find the distance in bst tree
	//first generate BST
	public int bstDistance(int values[], int n, int node1, int node2){
		Arrays.sort(values);
		TreeNode root = generateBST(values, 0, n);
	    return findDistance(root, node1, node2);
	}
	
	public TreeNode generateBST(int[] values, int left, int right) {
		if (left > right) return null;
		int mid = (left + right)/2;
		TreeNode root = new TreeNode(values[mid]);
		root.left =  generateBST(values, left, mid-1);
		root.right = generateBST(values, mid+1, right);
		return root;
	}

//	Distance(X, Y) = Distance(root, X) +Distance(root, Y) — 2*(Distance(root to LCA(X,Y)
//	where LCA(X,Y) = Low­est Com­mon Ances­tor of X,Y
	public int findDistance(TreeNode root, int n1, int n2) {
		int x = Pathlength(root, n1) - 1;
		int y = Pathlength(root, n2) - 1;
		TreeNode lcaNode = findLCA(root, n1, n2);
		int lcaDistance = Pathlength(root, lcaNode.val) - 1;
		return (x + y) - 2 * lcaDistance;
	}

	public int Pathlength(TreeNode root, int n1) {
		if (root != null) {
			int x = 0;
			if ((root.val == n1) || (x = Pathlength(root.left, n1)) > 0
					|| (x = Pathlength(root.right, n1)) > 0) {
				return x + 1;
			}
			return 0;
		}
		return 0;
	}

	public TreeNode findLCA(TreeNode root, int n1, int n2) {
		if (root != null) {
			if (root.val == n1 || root.val == n2) {
				return root;
			}
			TreeNode left = findLCA(root.left, n1, n2);
			TreeNode right = findLCA(root.right, n1, n2);
			if (left != null && right != null) return root;
			if (left != null) return left;
			if (right != null) return right;
		}
		return null;
	}
	
	//throw baseball
	//dynamic programing
	public int totalScore(String[] blocks, int n){
		int result = 0;
		int[] dp = new int[n + 1];
		dp[0] = 0;
		for(int i = 0; i < n; i++){
			String ch = blocks[i];
			if( ch.equals("X") ){
				dp[i + 1] = dp[i] * 2;
				result += dp[i + 1];
			}else if( ch.equals("+") ){
				if( i == 0) dp[i + 1] = dp[i];
				else if( i > 0) dp[i + 1] = dp[i] + dp[i - 1];
				result += dp[i + 1];
				
			} else if( ch.equals("Z") ){
				dp[i + 1] = 0;
				result = result - dp[i];
				dp[i] = 0;
			} else {
				dp[i + 1] = Integer.parseInt(ch);
				result += dp[i + 1];
			}
		}
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
