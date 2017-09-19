package mianjing;

import z_leetcode.TreeNode;

public class Paypal {

	//reverse integer
	public int reverseInteger(int n) {
		long res = 0;
		boolean positive = n >= 0 ? true : false;
		n = Math.abs(n);
		while(n > 0) {
			res = res * 10 + n%10;
			n = n/10;
		}
		if(res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) return 0;
		return positive ? (int) res : (int) -res;
	}
	
	//lca 235. Lowest Common Ancestor of a Binary Search Tree
	public TreeNode lowestCommonAncestor235(TreeNode root, TreeNode p, TreeNode q) {
		if(root == null) return null;
		if(root.val > p.val && root.val > q.val) {
			return lowestCommonAncestor(root.left, p, q);
		} else if(root.val < p.val && root.val < q.val) {
			return lowestCommonAncestor(root.right, p, q);
		} else return root;
    }
	//236. Lowest Common Ancestor of a Binary Tree
	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
	    if (root == null || root == p || root == q) return root;
	    TreeNode left = lowestCommonAncestor(root.left, p, q);
	    TreeNode right = lowestCommonAncestor(root.right, p, q);
	    return left == null ? right : right == null ? left : root;
	}

	//big integer add/multiply
	//43 Multiply Strings  
	public String multiply(String num1, String num2) {
		if(num1.equals("0") || num2.equals("0")) return "0";
		String res = "";
        for(int i = 0; i < num1.length(); i++) {
        	int cur = Character.getNumericValue(num1.charAt(i));
        	String curStr = "";
        	for(int k = 1; k <= cur; k++) {
        		curStr = addStr(curStr, num2);
        	}
        	res = addStr(res + "0", curStr);
        }
        return res;
    }
	//415 Add Strings 
	public String addStr(String num1, String num2) {
		StringBuilder sb = new StringBuilder();
        int carry = 0;
        for(int i = num1.length() - 1, j = num2.length() - 1; i >= 0 || j >= 0 || carry == 1; i--, j--){
            int x = i < 0 ? 0 : num1.charAt(i) - '0';
            int y = j < 0 ? 0 : num2.charAt(j) - '0';
            sb.append((x + y + carry) % 10);
            carry = (x + y + carry) / 10;
        }
        return sb.reverse().toString();
	}
	
	
	
	
	
	
	
}
