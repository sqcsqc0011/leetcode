package z_leetcode;

public class TreeNode {
	public int val;
	public TreeNode left;
	public TreeNode right;
	public TreeNode(int x) { val = x; }
	
	public static void inOrderTraversal(TreeNode node){
        if(node == null){
            return;
        }else{
            inOrderTraversal(node.left);
        System.out.println(node.val);
        inOrderTraversal(node.right);
        }
    }
}
