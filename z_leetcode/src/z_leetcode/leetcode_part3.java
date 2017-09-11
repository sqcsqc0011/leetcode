package z_leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class leetcode_part3 {
	
	//101. Symmetric Tree
	public boolean isSymmetric(TreeNode root) {
		if(root == null) return true;
		if( root.left == null || root.right == null) return root.left == root.right;
		if( root.left.val != root.right.val) 
			return false;
		else return compareTree( root.left, root.right);
    }

	private boolean compareTree(TreeNode left, TreeNode right) {
		if( left == null || right == null) return left == right;
		if( left.val != right.val) return false;
		return compareTree(left.left, right.right)&&compareTree(left.right, right.left);
	}	
	
	//102. Binary Tree Level Order Traversal
	public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        getEachNodeList(root, result, 0);
        return result;
    }

	private void getEachNodeList(TreeNode root, List<List<Integer>> result, int level) {
		if(root == null) return;
		if(level >= result.size())	result.add(new ArrayList<Integer>());
		List<Integer> levelList = result.get(level);
		levelList.add(root.val);
		getEachNodeList(root.left, result, level + 1);
		getEachNodeList(root.right, result, level + 1);
	}
	
	//103. Binary Tree Zigzag Level Order Traversal 
	public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		getZigZagEachNodeList(root, result, 0);
        return result;
    }

	private void getZigZagEachNodeList(TreeNode root, List<List<Integer>> result, int level) {
		if(root == null) return;
		if(level >= result.size())	result.add(new ArrayList<Integer>());
		List<Integer> levelList = result.get(level);
		if(level %2 == 0) levelList.add(root.val);
		else levelList.add(0, root.val);
		getZigZagEachNodeList(root.left, result, level + 1);
		getZigZagEachNodeList(root.right, result, level + 1);
    }
	
	//104. Maximum Depth of Binary Tree
	public int maxDepth(TreeNode root) {
        return getDepth(root, 0);
    }

	private int getDepth(TreeNode root, int level) {
		if( root == null) return level;
		else return Math.max(getDepth(root.left, level + 1), getDepth(root.right, level + 1));
	}
	
	//105. Construct Binary Tree from Preorder and Inorder Traversal
	public TreeNode buildTree(int[] preorder, int[] inorder) {
		if( preorder.length == 0 || preorder == null) return null;
        return buildTreeNode(preorder, inorder, 0, inorder.length - 1, 0);
    }

	private TreeNode buildTreeNode(int[] preorder, int[] inorder, int begin, int end, int preroot) {
		if( begin > end || preroot >= preorder.length) return null;
		int inroot = -1;
		for(int i = begin; i <= end; i++){
			if( inorder[i] == preorder[preroot]) {
				inroot = i;
				break;
			}
		}
		TreeNode result = new TreeNode(preorder[preroot]);
		result.left = buildTreeNode(preorder, inorder, begin, inroot - 1, preroot + 1 );
		//get right pre-order root: preroot + (inroot - begin + 1)
		result.right = buildTreeNode(preorder, inorder, inroot + 1, end, preroot + (inroot - begin + 1) );
		return result;
	}
	
	//106. Construct Binary Tree from Inorder and Postorder Traversal
	public TreeNode buildTree2(int[] inorder, int[] postorder) {
		if( inorder.length == 0 || inorder == null) return null;
        return buildTreeNode2(inorder, postorder, 0, inorder.length - 1, postorder.length - 1);
    }

	private TreeNode buildTreeNode2(int[] inorder, int[] postorder, int inbegin, int inend, int postroot) {
		if(inbegin > inend || postroot < 0) return null;
		int inroot = -1;
		for( int i = inbegin; i <= inend; i++){
			if( inorder[i] == postorder[postroot]){
				inroot = i;
				break;
			}
		}
		TreeNode result = new TreeNode(postorder[postroot]);
		//get left postroot postion: postroot - (inend - inroot + 1)
		result.left = buildTreeNode2(inorder, postorder, inbegin, inroot - 1, postroot - ( inend - inroot + 1));
		result.right = buildTreeNode2(inorder, postorder, inroot + 1, inend, postroot - 1);
		return result;
	} 
	
	//107. Binary Tree Level Order Traversal II
	public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        addTreeNodeLevel(root, result, 1);
        return result;
    }

	private void addTreeNodeLevel(TreeNode root, List<List<Integer>> result, int i) {
		if(root == null) return;
		if(result.size() - i < 0){
			result.add(0, new ArrayList<Integer>());
		}
		result.get(result.size() - i).add(root.val);
		addTreeNodeLevel(root.left, result, i + 1);
		addTreeNodeLevel(root.right, result, i + 1);
	}
	
	//108. Convert Sorted Array to Binary Search Tree
	public TreeNode sortedArrayToBST(int[] nums) {
        if( nums == null || nums.length == 0) return null;
        return getBST(0, nums.length - 1, nums);
    }

	private TreeNode getBST(int left, int right, int[] nums) {
		if(left > right) return null;
		int mid = ( right - left)/2 + left;
		TreeNode treeNode = new TreeNode(nums[mid]);
		treeNode.left = getBST(left, mid - 1, nums);
		treeNode.right = getBST(mid + 1, right, nums);
		return treeNode;
	}
	
	//109. Convert Sorted List to Binary Search Tree
	//fast and slow pointer, fast is 2x than slow
	public TreeNode sortedListToBST(ListNode head) {
        if( head == null) return null;
        return getBSTFromListNode(head, null);
    }

	private TreeNode getBSTFromListNode(ListNode head, ListNode end) {
		if( head == end ) return null;
		ListNode fast = head, slow = head;
		while( fast != end && fast.next != end){
			fast = fast.next.next;
			slow = slow.next;
		}
		TreeNode treeNode = new TreeNode(slow.val);
		treeNode.left = getBSTFromListNode(head, slow);
		treeNode.right = getBSTFromListNode(slow.next, end);
		return treeNode;
	}
	
	//110. Balanced Binary Tree
	public boolean isBalanced(TreeNode root) {
        return getHeight(root) != -1;
    }

	private int getHeight(TreeNode root) {
		if( root == null) return 0;
		if( root.left == null && root.right == null) return 1;
		int leftH = getHeight(root.left);
		int rightH = getHeight(root.right);
		if (leftH ==  -1 || rightH == -1 || Math.abs( leftH - rightH) > 1) {
	        return -1;
	    }		
		return 1 + Math.max(leftH, rightH);
	}

	//111. Minimum Depth of Binary Tree
	//if left or right equals 0, need to go deep
	public int minDepth(TreeNode root) {
		if(root == null) return 0;
        int left = minDepth(root.left);
        int right = minDepth(root.right);
        return (left == 0 || right == 0) ? left + right + 1: Math.min(left,right) + 1;
	}
	
	//112. Path Sum
	public boolean hasPathSum(TreeNode root, int sum) {
		if(root == null) return false;
		if( root.left == null && root.right == null && root.val == sum) return true;
		return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
	}

	//113. Path Sum II
	public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        getPathSum(root, sum, result, new ArrayList<Integer>());
        return result;
    }

	private void getPathSum(TreeNode root, int sum, List<List<Integer>> result, ArrayList<Integer> onePath) {
		if( root == null) return;
		if( root.val == sum && root.left == null && root.right == null){
			onePath.add(root.val);
			result.add(new ArrayList<Integer>(onePath));
			onePath.remove(onePath.size() - 1);
			return;
		}
		onePath.add(root.val);
		getPathSum(root.left, sum - root.val, result, onePath);
		getPathSum(root.right, sum - root.val, result, onePath);
		onePath.remove(onePath.size() - 1);
	}
	
	//114. Flatten Binary Tree to Linked List
	//递归
	public void flatten(TreeNode root) {
        if( root == null ) return;
        TreeNode left = root.left;
        TreeNode right = root.right;      
        flatten(left);
        flatten(right);
        root.left = null;
        root.right = left;
        TreeNode cur = root;
        while(cur.right != null) cur = cur.right;
        cur.right = right;
    }
	
	//115. Distinct Subsequences
	//dynamic programming
//	动态规划，定义dp[i][j]为字符串i变换到j的变换方法。
//	如果S[i]==T[j]，那么dp[i][j] = dp[i-1][j-1] + dp[i-1][j]。意思是：如果当前S[i]==T[j]，那么当前这个字母即可以保留也可以抛弃，所以变换方法等于保留这个字母的变换方法加上不用这个字母的变换方法。
//	如果S[i]!=T[i]，那么dp[i][j] = dp[i-1][j]，意思是如果当前字符不等，那么就只能抛弃当前这个字符。
//	递归公式中用到的dp[0][0] = 1，dp[i][0] = 0（把任意一个字符串变换为一个空串只有一个方法）
	public int numDistinct(String s, String t) {
		int[][] dp = new int[s.length() + 1][t.length() + 1];
		dp[0][0] = 1;
    	for( int i = 0; i < s.length(); i++)
    		dp[i + 1][0] = 1;
        for(int i = 0; i < s.length(); i++){
        	for( int j = 0; j < t.length(); j++){
        		dp[i + 1][j + 1] = dp[i][j + 1];
        		if( s.charAt(i) == t.charAt(j)) dp[i + 1][j + 1] += dp[i][j];
        	}
        }
        return dp[s.length()][t.length()];
    }
	
	//116. Populating Next Right Pointers in Each Node
	public void connect1(TreeLinkNode root) {
        TreeLinkNode curlevel = root;
        while( curlevel != null ){
        	TreeLinkNode cur = curlevel;
        	while( cur != null ){
            	if(cur.left != null ) cur.left.next = cur.right;
            	if( cur.right != null && cur.next != null) cur.right.next = cur.next.left;
            	cur = cur.next;
            }
        	curlevel = curlevel.left;
        }
    }
	
	//117. Populating Next Right Pointers in Each Node II
	//based on level order traversal
    public void connect(TreeLinkNode root) {
        TreeLinkNode head = null; //head of the next level
        TreeLinkNode prev = null; //the leading node on the next level
        TreeLinkNode cur = root;  //current node of current level
        while( cur != null){
        	while( cur != null){
        		if( cur.left != null){
        			if( prev != null) prev.next = cur.left;
        			else head = cur.left;
        			prev = cur.left;
        		}
        		if( cur.right != null){
        			if( prev != null) prev.next = cur.right;
        			else head = cur.right;
        			prev = cur.right;
        		}
                //move to next node
        		cur = cur.next;
        	}
            //move to next level
        	cur = head;
        	prev = null;
        	head = null;
        }
    }
	
	//118. Pascal's Triangle
    public List<List<Integer>> generate(int numRows) {
    	List<List<Integer>> allrows = new ArrayList<List<Integer>>();
    	ArrayList<Integer> row = new ArrayList<Integer>();
    	for(int i = 0; i < numRows; i++){
    		row.add(0, 1);
    		for(int j = 1; j < row.size() - 1; j++)
    			row.set(j, row.get(j) + row.get(j + 1));
    		allrows.add(new ArrayList<Integer>(row));
    	}
    	return allrows;
    }
    
    //119. Pascal's Triangle II
    public List<Integer> getRow(int rowIndex) {
    	ArrayList<Integer> row = new ArrayList<Integer>();
    	if( rowIndex < 0) return row;
    	for(int i = 0; i <= rowIndex; i++){
    		row.add(0, 1);
    		for(int j = 1; j < row.size() - 1; j++)
    			row.set(j, row.get(j) + row.get(j + 1));
    	}
    	return row;
    }
    
    //120. Triangle
//    Go from bottom to top.
//    We start form the row above the bottom row [size()-2].
//    Each number add the smaller number of two numbers that below it.
//    And finally we get to the top we the smallest sum.
    public int minimumTotal(List<List<Integer>> triangle) {
        for(int i = triangle.size() - 2; i >= 0; i--)
            for(int j = 0; j <= i; j++)
                triangle.get(i).set(j, triangle.get(i).get(j) + Math.min(triangle.get(i + 1).get(j), triangle.get(i + 1).get(j + 1)));
        return triangle.get(0).get(0);
    }
    
	//121. Best Time to Buy and Sell Stock
    public int maxProfit1 (int[] prices) {
    	//int[] dp = new int[prices.length];
    	if(prices.length == 0) return 0;
    	int result = 0;
    	int cheap = prices[0];
    	for( int i = 1; i < prices.length; i++){
    		if(prices[i] >= cheap ){
    			result = Math.max(result, prices[i] - cheap);
    		}else cheap = prices[i];
    	}
    	return result;
    }
    
    //122. Best Time to Buy and Sell Stock II
    //贪心法。从前向后遍历数组，只要当天的价格高于前一天的价格，就算入收益。
    public int maxProfit2(int[] prices) {
    	int profit = 0;
    	for(int i = 1; i < prices.length; i++){
    		if( prices[i] > prices[i - 1]){
    			profit += prices[i] - prices[i - 1];
    		}
    	}
        return profit;
    }
    
    //123. Best Time to Buy and Sell Stock III
//    动态规划法。以第i天为分界线，计算第i天之前进行一次交易的最大收益preProfit[i]，和第i天之后进行一次交易的最大收益postProfit[i]，
//    最后遍历一遍，max{preProfit[i] + postProfit[i]} (0≤i≤n-1)就是最大收益。
//    第i天之前和第i天之后进行一次的最大收益求法同Best Time to Buy and Sell Stock I。
//未通过测试
//    public int maxProfit(int[] prices) {
//    	int result = 0;
//        for(int i = 0; i < prices.length; i++){
//        	int count = getProfit(prices, 0, i) + getProfit(prices,i, prices.length - 1);
//        	result = Math.max(result, count);
//        }
//        return result;
//    }
//
//	private int getProfit(int[] prices, int begin, int end) {
//		int result = 0;
//		int cheap = prices[begin];
//    	for( int i = 1 + begin; i <= end; i++){
//    		if(prices[i] >= cheap ){
//    			result = Math.max(result, prices[i] - cheap);
//    		}else cheap = prices[i];
//    	}
//    	return result;
//	}
    public int maxProfit(int[] prices) {
        // these four variables represent your profit after executing corresponding transaction
        // in the beginning, your profit is 0. 
        // when you buy a stock ,the profit will be deducted of the price of stock.
        int firstBuy = Integer.MIN_VALUE, firstSell = 0;
        int secondBuy = Integer.MIN_VALUE, secondSell = 0;
        for (int curPrice : prices) {
            if (firstBuy < -curPrice) firstBuy = -curPrice; // the max profit after you buy first stock
            if (firstSell < firstBuy + curPrice) firstSell = firstBuy + curPrice; // the max profit after you sell it
            if (secondBuy < firstSell - curPrice) secondBuy = firstSell - curPrice; // the max profit after you buy the second stock
            if (secondSell < secondBuy + curPrice) secondSell = secondBuy + curPrice; // the max profit after you sell the second stock
        }
        return secondSell; // secondSell will be the max profit after passing the prices
    }
    
    //124. Binary Tree Maximum Path Sum
    public int maxPathSum(TreeNode root) {  
        int[] max = {Integer.MIN_VALUE};        // 因为Java里都是pass by value所以利用数组传！  
        rec(root, max);  
        return max[0];  
    }  
      
    public static int rec(TreeNode root, int[] max){  
        if(root == null) return 0;  
        int leftSubtreeMaxSum = rec(root.left, max);        // 左子树的最大和  
        int rightSubtreeMaxSum = rec(root.right, max);      // 右子树的最大和  
        int arch = leftSubtreeMaxSum + root.val + rightSubtreeMaxSum;       // 从左子树经过root到右子树的最大和  
        // 表示通过root节点能走到root的parent的最大和，这个值作为返回对象返给调用父函数  
        // 只有3中情况: 1 从左子树到root再到parent  
        // 2 从右子树到root再到parent  
        // 3 直接从root到parent  
        // 注意arch那条路是不可能走到parent，因为那条路已经经过root了，除非折回来重复走！但这是不允许的  
        int maxPathAcrossRootToParent = Math.max(root.val, Math.max(leftSubtreeMaxSum, rightSubtreeMaxSum)+root.val);  
        // max[0] 保存在所有递归过程中的最大值，这时也要考虑arch可能最大  
        max[0] = Math.max(max[0], Math.max(arch, maxPathAcrossRootToParent));  
        return maxPathAcrossRootToParent;  
    }  
    
    //125. Valid Palindrome
    public boolean isPalindrome(String s) {
    	if (s.isEmpty()) return true;
        int left = 0, right = s.length() - 1;
        char cleft, cright;
        while(left <= right){
        	cleft = s.charAt(left);
        	cright = s.charAt(right);
        	if( !Character.isLetterOrDigit(cleft)) left++;
        	if( !Character.isLetterOrDigit(cright)) right--;
        	if( Character.isLetterOrDigit(cleft) && Character.isLetterOrDigit(cright) ){
        		if( Character.toLowerCase(cleft) != Character.toLowerCase(cright)) return false;
        		left++;
        		right--;
        	}
        }
        return  true;
    }
    
    //126. Word Ladder II
    //记录每个单词所在的层数
    //hard!!!!!!!!!!!!!!!!!Not fixed!!!!!!!!!!!!!!!!!!
    HashMap<String,Integer> path = new HashMap<String,Integer>();
    //bfs生成path
    void bfs(String start, String end, HashSet<String> dict) {
        Queue queue = new LinkedList<String>();
        queue.add(start);
        path.put(start,0);
        String current;
        while(!queue.isEmpty()) {
            current = (String)queue.poll();
            if(current==end) {
                continue;
            }
            for(int i=0;i<current.length();i++) {
                char[] strCharArr = current.toCharArray();
                for(char ch='a';ch<='z';ch++) {
                    if(strCharArr[i]==ch) {
                        continue;
                    }
                    strCharArr[i] = ch;
                    String newWord = new String(strCharArr);
                    if(newWord.equals(end)==true||dict.contains(newWord)) {
                        //每个单词在path中只能出现一次，也就是每个单词只能出现在一层中，这样就很巧妙的解决了环的问题。
			if(path.get(newWord)==null) {
                            int depth = (int)path.get(current);
                            path.put(newWord,depth + 1);
                            queue.add(newWord);
                        }
                    }
                }
            }
        }
    }
    //从目标单词往回找开始单词，记录所有路径
    void dfs(String start, String end, HashSet<String> dict, List<String> pathArray, List<List<String>> result) {
        //找到了，需要reverse加入的所有单词
	if(start.equals(end)==true) {
            pathArray.add(start);
            Collections.reverse(pathArray);
            result.add(pathArray);
            return;
        }
        if(path.get(start)==null) {
            return;
        }
        pathArray.add(start);
        int nextDepth = (int)path.get(start) - 1;
        for(int i=0;i<start.length();i++) {
            char[] strCharArr = start.toCharArray();
            for(char ch='a';ch<='z';ch++) {
                if(strCharArr[i]==ch) {
                    continue;
                }
                strCharArr[i] = ch;
                String newWord = new String(strCharArr);
		//只相差一个字母同时这个单词所在的层数也是当前单词的上一层
                if(path.get(newWord)!=null&&(path.get(newWord)==nextDepth)) {
                    ArrayList<String> newPathArray = new ArrayList<String>(pathArray);
                    dfs(newWord,end,dict,newPathArray,result);
                }
            }
        }
    }
    
    public List<List<String>> findLadders(String start, String end, List<String> dict) {
    	HashSet<String> dictSet = new HashSet<>(dict);
        List<List<String>> result = new ArrayList<List<String>>();
        List<String> path = new ArrayList<String>();
        if(start==null||end==null||start.length()!=end.length()) {
            return result;
        }
        bfs(start, end, dictSet);
        dfs(end,start, dictSet, path, result);
        return result;
    }
    
    //127. Word Ladder
//    这道题是套用BFS同时也利用BFS能寻找最短路径的特性来解决问题。
//    把每个单词作为一个node进行BFS。当取得当前字符串时，对他的每一位字符进行从a~z的替换，如果在字典里面，就入队，并将下层count++，
//    并且为了避免环路，需把在字典里检测到的单词从字典里删除。这样对于当前字符串的每一位字符安装a~z替换后，在queue中的单词就作为下一层需要遍历的单词了。
//    正因为BFS能够把一层所有可能性都遍历了，所以就保证了一旦找到一个单词equals（end），那么return的路径肯定是最短的。
    //over time
//    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
//    	if( beginWord == null || beginWord.length() == 0 || endWord == null || endWord.length() == 0 || !wordList.contains(endWord)
//    			|| beginWord.length() != endWord.length() || wordList.size() == 0) return 0;
//    	List<String> levelWords = new ArrayList<>();
//    	levelWords.add(beginWord);
//    	return checkLevelWords(wordList, levelWords, 1, endWord);
//    }

//	private int checkLevelWords(List<String> wordList, List<String> levelWords, int level, String endWord) {
//		if(levelWords.size() == 0) return 0;
//		List<String> newlevel = new ArrayList<String>();
//		for(int i = 0; i < levelWords.size(); i++){
//			String curWord = levelWords.get(i);
//			for(int j = 0; j < curWord.length(); j++){
//				char[] curWordChar = curWord.toCharArray();
//				for(char ch = 'a'; ch <= 'z'; ch++){
//					curWordChar[j] = ch;
//					String newWord = new String(curWordChar);
//					if( newWord.equals(endWord)) return level + 1;
//					else if( wordList.contains(newWord)){
//						newlevel.add(newWord);
//						wordList.remove(newWord);
//					}
//				}
//			}
//		}
//		return checkLevelWords(wordList, newlevel, level + 1, endWord);
//	}
    
    //same idea as above, over time
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    	if( beginWord == null || beginWord.length() == 0 || endWord == null || endWord.length() == 0 || !wordList.contains(endWord)
    			|| beginWord.length() != endWord.length() || wordList.size() == 0) return 0;
    	List<String> reached = new ArrayList<String>();
        reached.add(beginWord);
        int distance = 1;
        while (!reached.contains(endWord)) {
            List<String> toAdd = new ArrayList<String>();
            for (String each : reached) {
                for (int i = 0; i < each.length(); i++) {
                    char[] chars = each.toCharArray();
                    for (char ch = 'a'; ch <= 'z'; ch++) {
                        chars[i] = ch;
                        String word = new String(chars);
                        if (wordList.contains(word)) {
                            toAdd.add(word);
                            wordList.remove(word);
                        }
                    }
                }
            }
            distance++;
            if (toAdd.size() == 0) return 0;
            reached = toAdd;
        }
        return distance + 1;
    }
    
    //128. Longest Consecutive Sequence
    public int longestConsecutive(int[] nums) {
    	if( nums == null || nums.length == 0) return 0;
        Set<Integer> set = new HashSet<Integer>();
        for(Integer num : nums) set.add(num);
        
        int result = 0;
        for(int i = 0; i < nums.length; i++){
        	if( set.contains( nums[i])){
        		int count = 1;
        		set.remove(nums[i]);
        		
        		int low = nums[i] - 1, high = nums[i] + 1;
        		while(set.contains(low)){
        			set.remove(low);
        			count++;
        			low--;
        		}
        		while( set.contains(high)){
        			set.remove(high);
        			count++;
        			high++;
        		}
        		result = Math.max(result, count);
        	}
        }
        return result;
    }
    
    //129. Sum Root to Leaf Numbers
    // the sum in one node equal 10*parent + node.val
    public int sumNumbers(TreeNode root) {
        return getNodeSum(0, root);
    }

	private int getNodeSum(int before, TreeNode root) {
		if( root == null) return before;
		int rootSum = before * 10 + root.val;
		if( root.left == null && root.right == null) return rootSum;
		int left = (root.left == null) ? 0 : getNodeSum(rootSum, root.left);
		int right = (root.right == null) ? 0 : getNodeSum(rootSum, root.right);
		return left + right;
	}
    
	//130. Surrounded Regions
//	这道题是说一个O周围都是X那么这个O就得变成X。那么就可以发现四周这一圈如果有O肯定不能四周都被X包围，同时这个O也将会是潜在的内部的O的缺口，让内部的O不能都被X覆盖。
//	因此，思路就是先对四周的O进行特殊处理，用BFS走，把所有这个O连接的O（包括这个O）都涂成#。这样子，对于原来的棋盘来说，
//	没有变成#的O就是四周没有被O污染的，四周都是X，那么对其变成X。而所有#就是那些原来是O但是不是四周都被X包围的，把它们再还原成O。
	public void solve(char[][] board) {
		if(board.length == 0 || board[0].length == 0) return;
		int row = board.length, column = board[0].length;
        for(int i = 0; i < row; i++){
        	if( board[i][0] == 'O'){
        		board[i][0] = 'S';
        		checkSurrond(board, i, 0);
        	}
        	if(board[i][column - 1] == 'O'){
        		board[i][column - 1] = 'S';
        		checkSurrond(board, i, column - 1);
        	}
        }
        for(int j = 0; j < column; j++){
        	if( board[0][j] == 'O'){
        		board[0][j] = 'S';
        		checkSurrond(board, 0, j);
        	}
        	if(board[row - 1][j] == 'O'){
        		board[row - 1][j] = 'S';
        		checkSurrond(board,  row - 1, j);
        	}
        }
        for( int i = 0; i < row; i++){
        	for( int j = 0; j < column; j++){
        		if(board[i][j] == 'S') board[i][j] = 'O';
        		else board[i][j] = 'X';
        	}
        }
    }

	private void checkSurrond(char[][] board, int i, int j) {
		int row = board.length, column = board[0].length;
		if( i < 0 || j < 0 || i >= row || j>= column) return;
		if( i > 1){
			if(board[i - 1][j] == 'O'){
				board[i - 1][j] = 'S';
        		checkSurrond(board, i - 1, j);
			}
		} 
		if( j > 1 ){
			if(board[i][j - 1] == 'O'){
				board[i][j - 1] = 'S';
        		checkSurrond(board, i, j - 1);
			}
		} 
		if( j < column - 2){
			if(board[i][j + 1] == 'O'){
				board[i][j + 1] = 'S';
        		checkSurrond(board, i, j + 1);
			}
		}
		if( i < row - 2 ){
			if(board[i + 1][j] == 'O'){
				board[i + 1][j] = 'S';
        		checkSurrond(board, i + 1, j);
			}
		}
	}
    
    //131. Palindrome Partitioning
	public List<List<String>> partition(String s) {
		List<List<String>> result = new ArrayList<List<String>>();
        if( s == null || s.length() == 0) return result;
        getOnePalindrome(result, new ArrayList<String>(), s, 0);
        return result;
    }

	private void getOnePalindrome(List<List<String>> result, ArrayList<String> oneSolution, String s, int begin) {
		if(begin > s.length() - 1) return;
		String last = s.substring(begin, s.length());
		if(validPalindrome(last)){
			oneSolution.add(last);
			result.add(new ArrayList<String>(oneSolution));
			oneSolution.remove(oneSolution.size() - 1);
		}
		for( int i = begin; i < s.length() - 1; i++){
			String next = s.substring(begin, i + 1);
			if(validPalindrome(next)){
				oneSolution.add(next);
				getOnePalindrome(result, oneSolution, s, i + 1);
				oneSolution.remove(oneSolution.size() - 1);
			}
		}
	}

	public boolean validPalindrome(String str){
		if( str.length() == 1) return true;
		int left = 0, right = str.length() - 1;
        while(left < right){
            if(str.charAt(left) != str.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }
    
    //132. Palindrome Partitioning II
//	首先设置dp变量 cuts[len+1]。cuts[i]表示从第i位置到第len位置（包含，即[i, len])的切割数（第len位置为空）。
//	初始时，是len-i。比如给的例子aab，cuts[0]=3，就是最坏情况每一个字符都得切割：a|a|b|' '。cuts[1] = 2, 即从i=1位置开始，a|b|' '。
//	cuts[2] = 1 b|' '。cuts[3]=0,即第len位置，为空字符，不需要切割。
//	上面的这个cuts数组是用来帮助算最小cuts的。
//	还需要一个dp二维数组matrixs[i][j]表示字符串[i,j]从第i个位置（包含）到第j个位置（包含） 是否是回文。
//	如何判断字符串[i,j]是不是回文？
//	 1. matrixs[i+1][j-1]是回文且 s.charAt(i) == s.charAt(j)。
//	 2. i==j（i，j是用一个字符）
//	 3. j=i+1（i，j相邻）且s.charAt(i) == s.charAt(j)
//	当字符串[i,j]是回文后，说明从第i个位置到字符串第len位置的最小cut数可以被更新了，
//	那么就是从j+1位置开始到第len位置的最小cut数加上[i,j]|[j+1,len - 1]中间的这一cut。
//	即，Math.min(cuts[i], cuts[j+1]+1) 
//	最后返回cuts[0]-1。把多余加的那个对于第len位置的切割去掉，即为最终结果。
	public int minCut(String s) {  
        int min = 0;  
        int len = s.length();  
        boolean[][] matrix = new boolean[len][len];  
        int cuts[] = new int[len+1];  
          
        if (s == null || s.length() == 0)  
            return min;  
         
        for (int i=0; i<len; ++i){  
            cuts[i] = len - i;  //cut nums from i to len [i,len]
        }  
          
        for (int i=len-1; i>=0; --i){  
            for (int j=i; j<len; ++j){  
                if ((s.charAt(i) == s.charAt(j) && (j-i<2))  
                        || (s.charAt(i) == s.charAt(j) && matrix[i+1][j-1]))  
                {  
                    matrix[i][j] = true;  
                    cuts[i] = Math.min(cuts[i], cuts[j+1]+1);  
                }  
            }  
        }  
        min = cuts[0]-1;  
        return min;  
    }
	
	//133. Clone Graph
	//!!!!!!!!!!!not fixed
	public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if(node == null)
            return null;
            
        HashMap<UndirectedGraphNode, UndirectedGraphNode> hm = new HashMap<UndirectedGraphNode, UndirectedGraphNode>();
        UndirectedGraphNode head = new UndirectedGraphNode(node.label);
        hm.put(node, head);
        
        DFS(hm, node);//DFS
        return head;
    }
    public void DFS(HashMap<UndirectedGraphNode, UndirectedGraphNode> hm, UndirectedGraphNode node){
        if(node == null)
            return;
            
        for(UndirectedGraphNode aneighbor: node.neighbors){ 
            if(!hm.containsKey(aneighbor)){
                UndirectedGraphNode newneighbor = new UndirectedGraphNode(aneighbor.label);
                hm.put(aneighbor, newneighbor);
                DFS(hm, aneighbor);//DFS
            }
            hm.get(node).neighbors.add(hm.get(aneighbor));
        }
    }
	
	//134. Gas Station
//    1 如果总的gas - cost小于零的话，那么没有解返回-1
//    2 如果前面所有的gas - cost加起来小于零，那么前面所有的点都不能作为出发点。
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int result = -1;
        int sum = 0;
        int total = 0;
        for( int i = 0; i < gas.length; i++){
        	sum += gas[i] - cost[i];
        	total += gas[i] - cost[i];
        	if( sum < 0){
        		result = i;
        		sum = 0;
        	}
        }
        if( total < 0) return -1;
        else return result + 1;
    }
    
    //135. Candy
//    这道题和Trapping water那个是一样的想法，因为无论是水坑还是得到糖的小朋友，影响因素都不只一边，都是左右两边的最小值/最大值来决定的。
//    所以这道题跟上一道一样，也是左右两边遍历数组。
//    leftnums数组存从左边遍历，当前小朋友对比其左边小朋友，他能拿到糖的数量；
//    rightnums数组存从右边遍历，当前小朋友对比其右边小朋友，他能拿到的糖的数量。
//    最后针对这两个数组，每个小朋友能拿到的糖的数量就是这两个数最大的那个数，求总加和就好了。
    public int candy(int[] ratings) {
    	if(ratings == null || ratings.length == 0) return 0;
    	int result = 0;
    	int len = ratings.length;
    	int[] leftCandy = new int[len];
    	int[] rightCandy = new int[len];
    	leftCandy[0] = 1;
    	rightCandy[len - 1] = 1;
    	for(int i = 1; i < len; i++){
    		if( ratings[i] > ratings[i - 1]) leftCandy[i] = leftCandy[i - 1] + 1;
    		else leftCandy[i] = 1;
    	}
    	for(int i = len - 2; i >= 0; i--){
    		if( ratings[i] > ratings[i + 1]) rightCandy[i] = rightCandy[i + 1] + 1;
    		else rightCandy[i] = 1;
    	}
    	for(int i = 0; i < len; i++){
    		result += Math.max(leftCandy[i], rightCandy[i]);
    	}
    	return result;
    }
    //over time
//    public int candy(int[] ratings) {
//    	if(ratings == null || ratings.length == 0) return 0;
//    	int sum = 1;
//    	int leftPos = 0;
//    	int[] candy = new int[ratings.length];
//    	candy[0] = 1;
//        for(int i = 1; i < ratings.length; i++){
//        	if(ratings[i] <= ratings[i - 1]) {
//        		if( candy[i - 1] == 1) {
//        			candy[i] = 1;
//        			sum++;
//        			int j = i;
//        			while( j > leftPos && candy[j - 1] <= candy[j] && ratings[j - 1] > ratings[j]){
//        				candy[j - 1]++;
//        				sum++;
//        				j--;
//        			}
//        		}
//        		else {
//        			candy[i] = 1;
//        			sum++;
//        		}
//        	}else if (ratings[i] > ratings[i - 1]){
//        		candy[i] = candy[i - 1] + 1;
//        		sum = sum + candy[i];
//        	}
//    		if(ratings[i] >= ratings[leftPos]) leftPos = i;
//        }
//        return sum;
//    }
    
    //136. Single Number
    //对于任何数x，都有x^x=0，x^0=x
    public int singleNumberI(int[] nums) {
    	int result = 0;
    	for(int i = 0; i < nums.length; i++){
    		result = result ^ nums[i];
    	}
    	return result;
    }
    
    //137. Single Number II
//    利用位运算来消除重复3次的数。以一个数组[14 14 14 9]为例，将每个数字以二进制表达：
//	1110
//	1110
//	1110
//	1001
//	_____
//	4331    对每一位进行求和
//	1001    对每一位的和做%3运算，来消去所有重复3次的数
    public int singleNumber(int[] A) {  
        int [] count = new int[32];
        int result = 0;
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < A.length; j++) {
                if (((A[j] >> i) & 1)==1) {
                    count[i]++;
                }
            }
            result |= ((count[i] % 3) << i);
        }
        return result;
    }
	
	//138. Copy List with Random Pointer
    public RandomListNode copyRandomList(RandomListNode head) {
        if(head==null) return null;
        HashMap<RandomListNode,RandomListNode> map = new HashMap<RandomListNode,RandomListNode>();
        RandomListNode newhead = new RandomListNode(head.label);
        map.put(head,newhead);
        RandomListNode oldp = head.next;
        RandomListNode newp = newhead;
        while(oldp!=null){
            RandomListNode newnode = new RandomListNode(oldp.label);
            map.put(oldp,newnode);
            newp.next = newnode;
            
            oldp = oldp.next;
            newp = newp.next;
        }
        oldp = head;
        newp = newhead;
        while(oldp!=null){
            newp.random = map.get(oldp.random);
            oldp = oldp.next;
            newp = newp.next;
        }
        return newhead;
    }
	
	//139. Word Break
    //dynamic programming
    public boolean wordBreakI(String s, List<String> wordDict) {
    	if( s == null || s.length() == 0 ) return false;
    	boolean[] dp = new boolean[s.length() + 1];
    	dp[0] = false;
    	for(int i = 1; i <= s.length(); i++){
    		String str = s.substring(0, i);
    		if( wordDict.contains(str)){
    			dp[i] = true;
    		}else{
        		for( int j = 1; j < i; j++){
        			if(dp[j] == true && wordDict.contains(s.substring(j, i))){
        				dp[i] = true;
        				break;
        			}
        		}
    		}
    	}
    	return dp[s.length()];
    }

    //140. Word Break II
    //增加一个判断是否可以word break的方法 减少复杂度
    public List<String> wordBreak(String s, List<String> wordDict) {
    	List<String> result = new ArrayList<String>();
    	HashSet<String> wordDictSet = new HashSet<String>(wordDict);
    	if( !isWordBreak(s, wordDictSet)) return result;
    	getOneWordBreak(result, new ArrayList<String>(), s, wordDictSet);
    	return result;
    }

	private void getOneWordBreak(List<String> result, ArrayList<String> oneSolution, String s, HashSet<String> wordDictSet) {
    	if( !isWordBreak(s, wordDictSet)) return;
		if( s == null || s.length() == 0 ) return;
		if( wordDictSet.contains(s)){
			String oneStr = "";
			for(String str : oneSolution){
				oneStr += str + " ";
			}
			oneStr += s;
			result.add(oneStr);
		}
		for( int i = 0; i < s.length(); i++){
			String next = s.substring(0, i + 1);
			if(wordDictSet.contains(next)){
				oneSolution.add(next);
		    	getOneWordBreak(result, oneSolution, s.substring(i + 1), wordDictSet);
		    	oneSolution.remove(oneSolution.size() - 1);
			}
		}
	}
    
	public boolean isWordBreak(String s, HashSet<String> wordDictSet) {
    	if( s == null || s.length() == 0 ) return false;
    	boolean[] dp = new boolean[s.length() + 1];
    	dp[0] = false;
    	for(int i = 1; i <= s.length(); i++){
    		String str = s.substring(0, i);
    		if( wordDictSet.contains(str)){
    			dp[i] = true;
    		}else{
        		for( int j = 1; j < i; j++){
        			if(dp[j] == true && wordDictSet.contains(s.substring(j, i))){
        				dp[i] = true;
        				break;
        			}
        		}
    		}
    	}
    	return dp[s.length()];
    }
    
    //141. Linked List Cycle
//	而如何判断有环，那么就需要引入Faster和Slower的概念了（也是一种双指针方法）。顾名思义，同个时间Faster走的比Slower走的多。一般来说，Slower每次走一步，Faster每次走2步（通常这个概念可以判断链表中间点）。
//	在这里，Faster和Slower同时从起点遍历链表，如果有环那么Slower和Faster肯定会相遇。
	public boolean hasCycle(ListNode head) {
        if(head == null || head.next == null) return false;
        ListNode Faster = head, Slower = head;       
        while(Faster.next!=null && Faster.next.next!=null){
            Slower = Slower.next;
            Faster = Faster.next.next;
            
            if(Faster == Slower) return true;
        }
        return false;
    }
	
	//142. Linked List Cycle II
//	假设从开始到相遇，Slower走过的路程长为s，由于Faster的步速是Slower的2倍，那么Faster在这段时间走的路程长为2s。
//	而对于Faster来说，他走的路程还等于之前绕整个环跑的n圈的路程nc，加上最后这一次遇见Slower的路程s。
//	所以我们有：
//	                   2s = nc + s 
//	对于Slower来说，他走的路程长度s还等于他从链表起始处到相遇点的距离，所以有：
//	                    s = a + x 
//	通过以上两个式子代入化简有：
//	                    a + x = nc
//	                    a = nc - x
//	                    a = (n-1)c + c-x
//	                    a = kc + (c-x)
//	那么可以看出，c-x，就是从相遇点继续走回到环入口的距离。上面整个式子可以看出，如果此时有个pointer1从起始点出发并且同时还有个pointer2从相遇点出发继续往前走（都只迈一步），那么绕过k圈以后， pointer2会和pointer1在环入口相遇。这样，换入口就找到了。
	public ListNode detectCycle(ListNode head) {
        if(head==null||head.next==null)
            return null;
        
        ListNode fast = head,slow=head;
        while (true) {
            if (fast == null || fast.next == null) {
            return null;   
        }
            slow = slow.next;
            fast = fast.next.next;
            
            if(fast==slow)
                break;
        }
        
        slow = head;//slow back to start point
        while(slow != fast){
            slow = slow.next;
            fast = fast.next;
        }
        return slow; //when slow == fast, it is where cycle begins
    }
    
	//143. Reorder List
//	题目要重新按照 L0→Ln→L1→Ln-1→L2→Ln-2→…来排列，看例子1->2->3->4会变成1->4->2->3，拆开来看，是{1，2}和{4，3}的组合，而{4，3}是{3，4}的逆序。这样问题的解法就出来了。
//	第一步，将链表分为两部分。
//	第二步，将第二部分链表逆序。
//	第三步，将链表重新组合。
	public void reorderList(ListNode head) {
        if(head==null||head.next==null) return;
        ListNode slow=head, fast=head;
        ListNode firsthalf = head;
        while(fast.next!=null&&fast.next.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode secondhalf = slow.next;
        slow.next = null;
        secondhalf = reverseOrder(secondhalf);
        while (secondhalf != null) {
            ListNode temp1 = firsthalf.next;
            ListNode temp2 = secondhalf.next;
            firsthalf.next = secondhalf;
            secondhalf.next = temp1;        
            firsthalf = temp1;
            secondhalf = temp2;
        } 
    }
    
    public static ListNode reverseOrder(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode pre = head;
        ListNode curr = head.next;
        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = pre;
            pre = curr;
            curr = temp;
        }
        head.next = null;
        return pre;
    }
	
    //144. Binary Tree Preorder Traversal
    //recursive solution
    public List<Integer> preorderTraversalRecursive(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        preorderTraversal(result, root);
        return result;
    }
	private void preorderTraversal(List<Integer> result, TreeNode root) {
		if( root == null) return;
		result.add(root.val);
		preorderTraversal(result, root.left);
		preorderTraversal(result, root.right);
	}
 
	//iterative solution using stack.
	public List<Integer> preorderTraversal(TreeNode node) {
		List<Integer> list = new LinkedList<Integer>();
		Stack<TreeNode> rights = new Stack<TreeNode>();
		while(node != null) {
			list.add(node.val);
			if (node.right != null) {
				rights.push(node.right);
			}
			node = node.left;
			if (node == null && !rights.isEmpty()) {
				node = rights.pop();
			}
		}
	    return list;
	}
	
	//145. Binary Tree Postorder Traversal
	//recursive solution
	public List<Integer> postorderTraversalRecursive(TreeNode root) {
		List<Integer> result = new ArrayList<Integer>();
		postorderTraversal(result, root);
		return result;
    }
	private void postorderTraversal(List<Integer> result, TreeNode root) {
		if(root == null) return;
		postorderTraversal(result, root.left);
		postorderTraversal(result, root.right);
		result.add(root.val);
	}
	//iterative solution using stack.
	public List<Integer> postorderTraversal(TreeNode root) {
		LinkedList<Integer> ans = new LinkedList<>();
		Stack<TreeNode> stack = new Stack<>();
		if (root == null) return ans;
		stack.push(root);
		while (!stack.isEmpty()) {
			TreeNode cur = stack.pop();
			ans.addFirst(cur.val);
			if (cur.left != null) {
				stack.push(cur.left);
			}
			if (cur.right != null) {
				stack.push(cur.right);
			} 
		}
		return ans;
	}
	
	//146. LRU Cache
	//!!!!!!!!!!!!!hard!!!!!!!!!!!!!!Not fixed!!!!!
	
	//147. Insertion Sort List
//	Insertion Sort就是把一个一个元素往已排好序的list中插入的过程。
//	初始时，sorted list是空，把一个元素插入sorted list中。然后，在每一次插入过程中，都是找到最合适位置进行插入。
//	因为是链表的插入操作，需要维护pre，cur和next3个指针。
//	pre始终指向sorted list的fakehead，cur指向当前需要被插入的元素，next指向下一个需要被插入的元素。
//	当sortedlist为空以及pre.next所指向的元素比cur指向的元素值要大时，需要把cur元素插入到pre.next所指向元素之前。否则，pre指针后移。最后返回fakehead的next即可。
	public ListNode insertionSortList(ListNode head) {  
        if(head == null||head.next == null)  
            return head;  
        ListNode sortedlisthead = new ListNode(0);  
        ListNode cur = head;
        while(cur!=null){  
            ListNode next = cur.next;  
            ListNode pre = sortedlisthead;  
            while( pre.next != null && pre.next.val < cur.val)  
                pre = pre.next;  
            cur.next = pre.next;  
            pre.next = cur;  
            cur = next;  
        }  
        return sortedlisthead.next;  
    }
	
	//148. Sort List
	//merge two sorted list, return result head
    public ListNode merge(ListNode h1, ListNode h2){
        if(h1 == null)  return h2;
        if(h2 == null)  return h1;        
        if(h1.val < h2.val){
            h1.next = merge(h1.next, h2);
            return h1;
        }
        else{
            h2.next = merge(h1, h2.next);
            return h2;
        }
    }
    
    public ListNode sortList(ListNode head) {
        //bottom case
        if(head == null) return head;
        if(head.next == null) return head;        
        //p1 move 1 step every time, p2 move 2 step every time, pre record node before p1
        ListNode p1 = head;
        ListNode p2 = head;
        ListNode pre = head;
        while(p2 != null && p2.next != null){
            pre = p1;
            p1 = p1.next;
            p2 = p2.next.next;
        }
        //change pre next to null, make two sub list(head to pre, p1 to p2)
        pre.next = null;
        //handle those two sub list
        ListNode h1 = sortList(head);
        ListNode h2 = sortList(p1);
        return merge(h1, h2);
    }
	
    //149. Max Points on a Line
//    这道题就是给你一个2D平面，然后给你的数据结构是由横纵坐标表示的点，然后看哪条直线上的点最多。
//    （1）两点确定一条直线
//    （2）斜率相同的点落在一条直线上
//    （3）坐标相同的两个不同的点 算作2个点
//    利用HashMap，Key值存斜率，Value存此斜率下的点的个数。同时考虑特殊情况，如果恰巧遍历到一个相同坐标的点，那么就维护一个local的counter来记录相同的点。
//    维护一个localmax，计算当前情况下的最大值；再维护一个全局Max来计算总的最大值。
//    返回全局Max即可
//这个方法精度不够 通不过测试
    public int maxPoints(Point[] points) {  
        if(points.length == 0||points == null)  return 0;  
        if(points.length == 1)  return 1;  
        int max = 1;  //the final max value, at least one
        for(int i = 0; i < points.length; i++) {  
            HashMap<Double, Integer> hm = new HashMap<Double, Integer>();  
            int same = 0;
            int localmax = 1; //the max value of current slope, at least one
            for(int j = i + 1; j < points.length; j++) {  
                if(i == j)  continue;  
                if(points[i].x == points[j].x && points[i].y == points[j].y){
                    same++; 
                    continue;
                }
                double slope = ((double)(points[i].y - points[j].y))/(points[i].x - points[j].x); 
                if( Math.abs(slope) == 0 || Math.abs(slope) == Double.POSITIVE_INFINITY) slope = Math.abs(slope);
                if(hm.containsKey(slope))  hm.put(slope, hm.get(slope) + 1);  
                else  hm.put(slope, 2);  //two points form a line
            }
            for (Integer value : hm.values())   
                localmax = Math.max(localmax, value);  
            localmax += same;  
            max = Math.max(max, localmax);  
        }  
        return max; 
    }
    
    /*
     *  A line is determined by two factors,say y=ax+b
     *  If two points(x1,y1) (x2,y2) are on the same line(Of course). 
     *  Consider the gap between two points.
     *  We have (y2-y1)=a(x2-x1),a=(y2-y1)/(x2-x1) a is a rational, b is canceled since b is a constant
     *  If a third point (x3,y3) are on the same line. So we must have y3=ax3+b
     *  Thus,(y3-y1)/(x3-x1)=(y2-y1)/(x2-x1)=a
     *  Since a is a rational, there exists y0 and x0, y0/x0=(y3-y1)/(x3-x1)=(y2-y1)/(x2-x1)=a
     *  So we can use y0&x0 to track a line;
     */
    public int maxPointsByGCD(Point[] points) {
    	if (points==null) return 0;
    	if (points.length<=2) return points.length;
    	
    	Map<Integer,Map<Integer,Integer>> map = new HashMap<Integer,Map<Integer,Integer>>();
    	int result=0;
    	for (int i = 0; i < points.length; i++){ 
    		map.clear();
    		int overlap = 0, max = 0;
    		for (int j = i + 1; j < points.length;j++){
    			int x = points[j].x - points[i].x;
    			int y = points[j].y - points[i].y;
    			if (x==0 && y==0){
    				overlap++;
    				continue;
    			}
    			int gcd = generateGCD(x,y);
    			if (gcd != 0){
    				x/=gcd;
    				y/=gcd;
    			}
    			
    			if (map.containsKey(x)){
    				if (map.get(x).containsKey(y)){
    					map.get(x).put(y, map.get(x).get(y)+1);
    				}else{
    					map.get(x).put(y, 1);
    				}   					
    			}else{
    				Map<Integer,Integer> m = new HashMap<Integer,Integer>();
    				m.put(y, 1);
    				map.put(x, m);
    			}
    			max = Math.max(max, map.get(x).get(y));
    		}
    		result = Math.max(result, max + overlap + 1);
    	}
    	return result;   	
    }
    private int generateGCD(int a, int b){
    	if (b == 0) return a;
    	else return generateGCD(b, a%b);
    }
    
	//150. Evaluate Reverse Polish Notation
    public int evalRPN(String[] tokens) {
        int first, next;
        Stack<Integer> st = new Stack<Integer>();
        for(String str : tokens){
        	if(str.equals("+")){
        		next = st.pop();
        		first = st.pop();
        		st.push(first + next);
        	}else if(str.equals("-")){
        		next = st.pop();
        		first = st.pop();
        		st.push(first - next);
        	}else if(str.equals("*")){
        		next = st.pop();
        		first = st.pop();
        		st.push(first * next);
        	}else if(str.equals("/")){
        		next = st.pop();
        		first = st.pop();
        		st.push(first / next);
        	}else{
        		st.push(Integer.parseInt(str));
        	}
        }
        return st.pop();
    }
	
	
	
	
	
	
	
}
