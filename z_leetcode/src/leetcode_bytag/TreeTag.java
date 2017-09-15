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
	//����ȫ�ֱ�������ÿ���ڵ�ĺͣ�����õ���ǰtilt�����뵽ȫ�ֱ���
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
	public TreeNode addOneRow(TreeNode root, int v, int d) {
        return addOneRow(root, v, d, 1, true);
    }
	private TreeNode addOneRow(TreeNode root, int v, int d, int curD, boolean left) {
		if(d == curD){
			TreeNode node = new TreeNode(v);
			if(root != null){
				if(left) node.left = root;
				else node.right = root;
			}
			return node;
		}
		if(root == null) return null;
		curD++;
		root.left = addOneRow(root.left, v, d, curD, true);
		root.right = addOneRow(root.right, v, d, curD, false);
		return root;
	}
	
	//637. Average of Levels in Binary Tree
	List<Double> averageOfLevelsRes = new ArrayList<Double>();
	List<Integer> countOfLevelsRes = new ArrayList<Integer>();
	public List<Double> averageOfLevels(TreeNode root) {
        getAverageOfLevels(root, 0);
		return averageOfLevelsRes;
    }
	private void getAverageOfLevels(TreeNode root, int level) {
		if(root != null){
			if(averageOfLevelsRes.size() < level + 1){
				averageOfLevelsRes.add((double)root.val);
				countOfLevelsRes.add(1);
			}else{
				averageOfLevelsRes.set(level, (averageOfLevelsRes.get(level) * countOfLevelsRes.get(level) + root.val)/(countOfLevelsRes.get(level) + 1));
				countOfLevelsRes.set(level, countOfLevelsRes.get(level) + 1);
			}
			getAverageOfLevels(root.left, level + 1);
			getAverageOfLevels(root.right, level + 1);
		}
		return;
	}
	
	//652. Find Duplicate Subtrees
	//将每一个treenode转化成str，判断是否有重复的str
	HashMap<String, List<TreeNode>> findDuplicateSubtreesMaps = new HashMap<String, List<TreeNode>>();
	public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
		getNodeStr(root);
		List<TreeNode> res = new ArrayList<TreeNode>();
		for(List<TreeNode> value : findDuplicateSubtreesMaps.values()){
			if(value.size() > 1) res.add(value.get(0));
		}
		return res;
    }
	private String getNodeStr(TreeNode root) {
		if(root == null) return "";
		String rootStr = root.val + "(" + getNodeStr(root.left) + ")" + "(" + getNodeStr(root.right) + ")";
		if(!findDuplicateSubtreesMaps.containsKey(rootStr)) findDuplicateSubtreesMaps.put(rootStr, new ArrayList<TreeNode>());
		findDuplicateSubtreesMaps.get(rootStr).add(root);
		return rootStr;
	}
	
	//653. Two Sum IV - Input is a BST
	public boolean findTarget(TreeNode root, int k) {
        return getSum(root, root, k);
    }
	private boolean getSum(TreeNode a, TreeNode b, int k) {
		if(a == null || b == null) return false;
		if(a.equals(b)) return getSum(a, a.right, k) || getSum(a.left, a, k);
		if(a.val + b.val == k) return true;
		if(a.val + b.val < k){
			return getSum(a.right, b, k) || getSum(a, b.right, k);
		} else{
			return getSum(a.left, b, k) || getSum(a, b.left, k);
		}
	}
	
	//654. Maximum Binary Tree
	public TreeNode constructMaximumBinaryTree(int[] nums) {
        return constructMaximumBinaryTree(nums, 0, nums.length - 1);
    }
	private TreeNode constructMaximumBinaryTree(int[] nums, int s, int e) {
		if(s > e || s < 0  || e < 0 || s >= nums.length || e >= nums.length) return null;
		int index = s, max = nums[index];
		for(int i = s + 1;i <= e; i++){
			if(nums[i] > max){
				max = nums[i];
				index = i;
			}
		}
		TreeNode node = new TreeNode(max);
		node.left = constructMaximumBinaryTree(nums, s, index - 1);
		node.right = constructMaximumBinaryTree(nums, index + 1, e);
		return node;
	}
	
	//655. Print Binary Tree
	//先求tree的高度，得到res的m和n，再遍历数，找到每个root.val放在数组的哪个位置上
	public List<List<String>> printTree(TreeNode root) {
		int deep = getRootDeep(root);
		int len = 0;
		for(int i = 1; i <= deep; i++){
			len += Math.pow(2, i - 1);
		}
		List<List<String>> res = new ArrayList<List<String>>();
		List<String> row = new ArrayList<>();
	    for(int i = 0; i < len; i++) row.add("");
	    for(int i = 0; i < deep; i++) res.add(new ArrayList<>(row));
		setPrintTree(root, res, 0, 0, len - 1, len - 1);
		return res;
    }
	private void setPrintTree(TreeNode root, List<List<String>> res, int d, int s, int e, int max) {
        if(root == null) return;
		res.get(d).set((e - s)/2 + s, root.val + "");
		setPrintTree(root.left, res, d + 1, s, (e - s)/2 + s - 1, max);
		setPrintTree(root.right, res, d + 1, (e - s)/2 + s + 1, e, max);
	}
	private int getRootDeep(TreeNode root) {
		if(root == null) return 0;
		return 1 + Math.max(getRootDeep(root.left), getRootDeep(root.right));
	}
	
	//662. Maximum Width of Binary Tree
	//time out
	public int widthOfBinaryTree(TreeNode root) {
		List<Integer> start = new ArrayList<Integer>();
		List<Integer> end = new ArrayList<Integer>();
		getStartAndEndForTree(root, start, end, 0, 0);
		int width = 1;
		for(int i = 0; i < start.size(); i++){
			width = Math.max(width, end.get(i) - start.get(i) + 1);
		}
		return width;
    }
	private void getStartAndEndForTree(TreeNode root, List<Integer> start, List<Integer> end, int d, int index) {
		if(root == null) return;
		if(start.size() < d + 1) {
			start.add(index);
			end.add(index);
		} else end.set(d, index);
		getStartAndEndForTree(root.left, start, end, d + 1, 2 * index);
		getStartAndEndForTree(root.right, start, end, d + 1, 2 * index + 1);
	}
	
	//669. Trim a Binary Search Tree
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
