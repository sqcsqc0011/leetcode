package leetcode_bytag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import z_leetcode.TreeNode;

public class TreeTag {

	//337. House Robber III
	//use int[] to store res, make it AC
	public int rob(TreeNode root) {
		int[] res = dfsRob(root);
        return Math.max(res[0], res[1]);
    }
	//res[0] means contains root, res[1] not contains root
	private int[] dfsRob(TreeNode root) {
		if( root == null) return new int[]{0,0};
		else{
			int[] res = new int[2];
			int[] left = dfsRob(root.left);
			int[] right = dfsRob(root.right);
			res[0] = left[1] + right[1] + root.val;
			res[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
			return res;
		}
	}
	
	//404. Sum of Left Leaves
	public int sumOfLeftLeaves(TreeNode root) {
        int res = 0;
        return getSumOfLeftLeaves(root, res, false);
    }
	private int getSumOfLeftLeaves(TreeNode root, int res, boolean leftNode) {
		if( root == null) return res;
		if(leftNode && root.left == null && root.right == null) return res + root.val;
		return res + getSumOfLeftLeaves(root.left, 0, true) + getSumOfLeftLeaves(root.right, 0, false);
	}
	
	//437. Path Sum III
	public int pathSum(TreeNode root, int sum) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, 1);
        return getPathSum(root, sum, 0, map);
    }
	private int getPathSum(TreeNode root, int sum, int cur, HashMap<Integer, Integer> map) {
		if(root == null) return 0;
		int res = 0;
		cur += root.val;
		if(map.containsKey(cur - sum)) res += map.get(cur - sum); 
		if(map.containsKey(cur)) map.put(cur, map.get(cur) + 1);
		else map.put(cur, 1);
		res += getPathSum(root.left, sum, cur, map) + getPathSum(root.right, sum, cur, map);
		map.put(cur, map.get(cur) - 1);
		return res;
	}

	//449. Serialize and Deserialize BST
	public class Codec {

	    // Encodes a tree to a single string.
	    public String serialize(TreeNode root) {
	        StringBuilder sb = new StringBuilder();
	        if(root == null) return "null";
	        Stack<TreeNode> st = new Stack<>();
	        st.push(root);
	        while( !st.isEmpty()){
	        	root = st.pop();
	        	sb.append(root.val).append(",");
	        	if(root.right != null) st.push(root.right);
	        	if(root.left != null) st.push(root.left);
	        }
	        return sb.toString();
	    }

	    // Decodes your encoded data to tree.
	    public TreeNode deserialize(String data) {
	    	if (data.equals("null")) return null;
	    	String[] strs = data.split(",");
	    	Queue<Integer> q = new LinkedList<Integer>();
	        for (String e : strs) {
	            q.offer(Integer.parseInt(e));
	        }
	        return generateBST(q);
	    }
	    
	    public TreeNode generateBST(Queue<Integer> q) {
	    	if( q.isEmpty()) return null;
	    	TreeNode root = new TreeNode(q.poll());
	    	Queue<Integer> smallQ = new LinkedList<Integer>();
	    	while( !q.isEmpty() && (q.peek() < root.val)){
	    		smallQ.offer(q.poll());
	    	}
	    	root.left = generateBST(smallQ);
	    	root.right = generateBST(q);
	    	return root;
		}
	}
	
	//450. Delete Node in a BST
	public TreeNode deleteNode(TreeNode root, int key) {
		if(root == null) return root;
    	if(root.val == key) return getNewTree(root);
        Stack<TreeNode> st = new Stack<TreeNode>();
        st.push(root);
        while(!st.isEmpty()){
        	TreeNode node = st.pop();
        	if(node.right != null){
        		if( node.right.val == key) node.right = getNewTree(node.right);
        		else st.push(node.right);
        	}
        	if(node.left != null){
        		if( node.left.val == key) node.left = getNewTree(node.left);
        		else st.push(node.left);
        	}
        }
        return root;
    }
	private TreeNode getNewTree(TreeNode root) {
		Queue<Integer> q = new LinkedList<Integer>();
        Stack<TreeNode> st = new Stack<TreeNode>();
        st.push(root);
        while( !st.isEmpty()){
        	TreeNode node = st.pop();
        	if( node.val != root.val) q.offer(node.val);
        	if(node.right != null) st.push(node.right);
        	if(node.left != null) st.push(node.left);
        }
        return generateBST(q);
	}
	public TreeNode generateBST(Queue<Integer> q) {
    	if( q.isEmpty()) return null;
    	TreeNode root = new TreeNode(q.poll());
    	Queue<Integer> smallQ = new LinkedList<Integer>();
    	while( !q.isEmpty() && (q.peek() < root.val)){
    		smallQ.offer(q.poll());
    	}
    	root.left = generateBST(smallQ);
    	root.right = generateBST(q);
    	return root;
	}
	
	//501. Find Mode in Binary Search Tree
	
	//508. Most Frequent Subtree Sum
    public int[] findFrequentTreeSum(TreeNode root) {
    	HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        getNodeSum(root, map);
        List<Integer> nums = new ArrayList<Integer>();
        int max = 0;
        for(int key : map.keySet()){
        	if( map.get(key) > max){
        		max = map.get(key);
        		nums = new ArrayList<Integer>();
        		nums.add(key);
        	} else if ( map.get(key) == max ) nums.add(key);
        }
        int[] res = new int[nums.size()];
        for(int i = 0; i < nums.size(); i++){
        	res[i] = nums.get(i);
        }
        return res;
    }
	private int getNodeSum(TreeNode root, HashMap<Integer, Integer> map) {
		if( root == null) return 0;
		int sum = root.val + getNodeSum(root.left, map) + getNodeSum(root.right, map);
		if( map.containsKey(sum)) map.put(sum, map.get(sum) + 1);
		else map.put(sum, 1);
		return sum;
	}
	
	//513. Find Bottom Left Tree Value
	public int findBottomLeftValue(TreeNode root) {
        return findBottomLeftValue(root, 1, new int[]{0,0});
    }
    public int findBottomLeftValue(TreeNode root, int maxH, int[] res) {
        if (res[1] < maxH) {
        	res[0] = root.val;
        	res[1] = maxH;
        }
        if (root.left != null) findBottomLeftValue(root.left, maxH + 1, res);
        if (root.right != null) findBottomLeftValue(root.right, maxH + 1, res);
        return res[0];
    }

    //515. Find Largest Value in Each Tree Row
    public List<Integer> largestValues(TreeNode root) {
    	List<Integer> res = new ArrayList<Integer>();
    	largestValues(res, 1, root);
    	return res;
    }
	private void largestValues(List<Integer> res, int h, TreeNode root) {
		if(root == null) return;
		if(res.size() < h) res.add(root.val);
		else if( root.val > res.get(h - 1)) res.set(h - 1, root.val);
		largestValues(res, h + 1, root.left);
		largestValues(res, h + 1, root.right);
	}
	
    //538. Convert BST to Greater Tree
	public TreeNode convertBST(TreeNode root) {
		convertBST(root, 0);
		return root;
	}
	private int convertBST(TreeNode root, int sum) {
		if( root == null) return sum;
		int right = convertBST(root.right, sum);
		int left = convertBST(root.left, right + root.val);
		root.val = right + root.val;
		return left;
	}
	
	//543. Diameter of Binary Tree
	public int diameterOfBinaryTree(TreeNode root) {
		if( root == null) return 0;
		int[] max = new int[1];
        getMaxDepth(root, max);
        return max[0];
    }
	private int getMaxDepth(TreeNode root, int[] max) {
		if(root == null ) return 0;
		int left = getMaxDepth(root.left, max);
		int right = getMaxDepth(root.right, max);
		max[0] = Math.max(max[0], left + right);
		return Math.max(left, right) + 1; 
	}

	
	//563. Binary Tree Tilt
	//设置全局变量，求每个节点的和，相减得到当前tilt，加入到全局变量
	int findTiltResult = 0;
    public int findTilt(TreeNode root) {
        postOrder(root);
        return findTiltResult;
    }
    
    private int postOrder(TreeNode root) {
        if (root == null) return 0;
        int left = postOrder(root.left);
        int right = postOrder(root.right);
        findTiltResult += Math.abs(left - right);
        return left + right + root.val;
    }
	
	//572. Subtree of Another Tree
    public boolean isSubtree(TreeNode s, TreeNode t) {
    	if(t == null || s == null) return false;
        boolean res = checkSame(t , s);
        if(res) return true;
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }
	private boolean checkSame(TreeNode t, TreeNode s) {
		if(t == null && s == null) return true;
		if(t == null || s == null) return false;
		if(t.val != s.val ) return false;
		return checkSame(t.left, s.left) && checkSame(t.right, s.right);
	}
	
	//606. Construct String from Binary Tree
	public String tree2str(TreeNode t) {
		String res = "";
		if(t == null) return res;
		res += t.val;
		if(t.left == null && t.right == null) return res;
		if(t.left == null) res += "()";
		else res += "(" + tree2str(t.left) +")";
		if(t.right != null) res += "(" + tree2str(t.right) +")";
		return res;
	}
	
	//617. Merge Two Binary Trees
	public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if(t1 == null && t2 == null) return null;
        if(t1 == null) return t2;
        if(t2 == null) return t1;
        TreeNode node = new TreeNode(t1.val + t2.val);
        node.left = mergeTrees(t1.left, t2.left);
        node.right = mergeTrees(t1.right, t2.right);
        return node;
    }
	
	//623. Add One Row to Tree
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
