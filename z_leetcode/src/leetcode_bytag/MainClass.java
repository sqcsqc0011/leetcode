package leetcode_bytag;

import z_leetcode.TreeNode;

public class MainClass {
	
	public static void main(String [ ] args){
		ArrayTag arrayTag = new ArrayTag();
		HashTableTag hashtag = new HashTableTag();
		MathTag mathTag = new MathTag();
		BitManipulationTag bitManipulationTag = new BitManipulationTag();
		StringTag stringTag = new StringTag();
		TreeTag treeTag = new TreeTag();
		DynamicProgrammingTag dynamicProgrammingTag = new DynamicProgrammingTag();
		
		String text = "civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth";
		String[] texts = {"aba", "aba", "aa"};
		
		int[] nums = {1,1,1,1,1};
		
		int[][] numss = {{1,2},{3,4},{5,6},{7,8},{11,12},{9,10}};
		//,{4,300},{5,500},{5,400},{5,250},{6,370},{6,360},{7,380}};
		
		int[][] numss2 = {{30,50},{12,2},{3,4},{12,15}};
		
		TreeNode root1 = new TreeNode(5);
		TreeNode root2 = new TreeNode(3);
		TreeNode root3 = new TreeNode(4);
		TreeNode root4 = null;
		TreeNode root5 = new TreeNode(2);
		TreeNode root6 = null;
		TreeNode root7 = new TreeNode(6);
		TreeNode root8 = new TreeNode(7);
		root1.left = root2;
		root1.right = root7;
		root7.right = root8;
		root2.left = root5;
		root2.right = root3;
		
		Integer[] arr = {3,2,5,null,9,null,1};
		char[] tasks = {'A','A','A','A','A','A','A','A','A','A','B','B','B'};
		
		System.out.println(stringTag.minDistance("seateat", "eata"));
		
	}
}
