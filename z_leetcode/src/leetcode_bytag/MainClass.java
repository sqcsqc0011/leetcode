package leetcode_bytag;

import leetcode_contest.Weekly_50;
import z_leetcode.ListNode;
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
		LinkedListTag linkedListTag = new LinkedListTag();
		Weekly_50 weekly_50 = new Weekly_50();
		DFSTag dfsTag = new DFSTag();
		
		String text = "civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth";
		String[] texts = {"root/a 1.txt(abcd) 2.txt(efgh)", "root/c 3.txt(abcd)", "root/c/d 4.txt(efgh)", "root 4.txt(efgh)"};
		String[] str1 = {"MagicDictionary", "buildDict", "search", "search", "search", "search"};
		String[] str2 = {"Piatti","The Grill at Torrey Pines","Hungry Hunter Steakhouse","Shogun"};
		
		String[][] strss = {{"EZE","TIA"},{"EZE","HBA"},{"AXA","TIA"},{"JFK","AXA"},{"ANU","JFK"},{"ADL","ANU"},{"TIA","AUA"},{"ANU","AUA"},{"ADL","EZE"},{"ADL","EZE"},{"EZE","ADL"},{"AXA","EZE"},{"AUA","AXA"},{"JFK","AXA"},{"AXA","AUA"},{"AUA","ADL"},{"ANU","EZE"},{"TIA","ADL"},{"EZE","ANU"},{"AUA","ANU"}};
		
		int[] nums = {1,1,2,2,2};
		
		int[][] numss = {{1,2,2,3,5},{3,2,3,4,4},{2,4,5,3,1},{6,7,1,4,5},{5,1,1,2,4}};
		//,{4,300},{5,500},{5,400},{5,250},{6,370},{6,360},{7,380}};
		
		int[][] numss2 = {{9, 9, 4},{6, 6, 8},{2, 1, 1}};
		
		//treeNode
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
		
		//LinkedList
		ListNode node1 = new ListNode(5);
		ListNode node2 = new ListNode(6);
		ListNode node3 = new ListNode(7);
		ListNode node4 = new ListNode(8);
		ListNode node5 = new ListNode(3);
		ListNode node6 = new ListNode(2);
		node1.next = node2;
		node2.next = node3;
		node3.next = node4;
		//node4.next = node5;
		//node5.next = node6;
		ListNode[] nodes = {};
		
		System.out.println(dfsTag.makesquare(nums));		
	}
}
