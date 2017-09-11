package leetcode_bytag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DynamicProgrammingTag {
	
	//300. Longest Increasing Subsequence
	//use binary search, complexity: nlogn
    public int lengthOfLIS(int[] nums) {
    	int[] dp = new int[nums.length];
    	int res = 0;
    	for(int num : nums){
    		int index = Arrays.binarySearch(dp, 0, res, num);
    		if( index < 0) index = -( index + 1);
    		dp[index] = num;
    		if( index == res) res++;
    	}
    	return res;
    }
	
	//309. Best Time to Buy and Sell Stock with Cooldown
	public int maxProfit(int[] prices) {
		if( prices.length == 0) return 0;
        int todaysell = 0, presell = 0, prepresell = 0, todaybuy = 0, prebuy = -prices[0];
		for(int i = 1; i < prices.length; i++){
			todaybuy = Math.max(prepresell - prices[i], prebuy);
			todaysell = Math.max(prebuy + prices[i], presell);
			int tmp = presell;
			presell = todaysell;
			prepresell = tmp;
			prebuy = todaybuy;
		}
		return todaysell;
    }
	
	//312. Burst Balloons
	// hard!!!!!!!!!
	public int maxCoins(int[] oldnums) {
		int[] nums = new int[oldnums.length + 2];
        int index = 1;
        nums[0] = 1;
        for(int i: oldnums) nums[index++] = i;
        nums[index] = 1;
        int[][] dp = new int[nums.length][nums.length];
        return maxCoins(nums, dp, 0, nums.length - 1);
    }
	private int maxCoins(int[] nums, int[][] dp, int left, int right) {
        if(dp[left][right] > 0) return dp[left][right];
		for(int i = left + 1; i < right; i++){
			dp[left][right] = Math.max(dp[left][right], 
						maxCoins(nums, dp, left, i) + maxCoins(nums, dp, i, right) + nums[i] * nums[right] * nums[left]);
		}
		return dp[left][right];
	}
	
	//321. Create Maximum Number
	//hard!!!!!!!!!!
	
	//322. Coin Change
	//dp[i]： 钱数为i时， 硬币数的最小值
	//dp[i] = min(dp[i], dp[i - coins[j]] + 1);
	public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        for(int i = 1; i < dp.length; i++) dp[i] = Integer.MAX_VALUE;
        for( int i = 1; i <= amount; i++){
        	for( int j = 0; j < coins.length; j++){
        		if( i - coins[j] >= 0 && dp[i - coins[j]] != Integer.MAX_VALUE)
        			dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
        	}
        }
        return dp[dp.length - 1] == Integer.MAX_VALUE ? -1 : dp[dp.length - 1];
    }
	
	//338. Counting Bits
    public int[] countBits(int num) {
    	int[] res = new int[num + 1];
    	for( int i = 1; i < res.length; i++){
    		res[i] = res[i>>1] + (i & 1);
    	}
    	return res;
    }
	
    //343. Integer Break
    public int integerBreak(int n) {
    	if( n <= 1) return 0;
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for(int i = 2; i < dp.length; i++){
        	for( int j = 1; j <= i/2; j++){
                dp[i] = Math.max(dp[i], (Math.max(j,dp[j])) * (Math.max(i - j, dp[i - j])));
        	}
        }
        return dp[n];
    }
	
    //354. Russian Doll Envelopes
    public int maxEnvelopes(int[][] envelopes) {
    	 if(envelopes == null || envelopes.length == 0  || envelopes[0] == null || envelopes[0].length != 2)  return 0;
    	 Arrays.sort(envelopes, new Comparator<int[]>(){
			@Override
			public int compare(int[] e1, int[] e2) {
				if(e1[0] != e2[0]) return e1[0] - e2[0];
				else return e2[1] - e1[1];
			}
    	 });
    	 //height des
    	 int[] dp = new int[envelopes.length];
    	 int res = 0;
    	 for(int[] envelope : envelopes){
    		int index = Arrays.binarySearch(dp, 0, res, envelope[1]);
    		if( index < 0) index = -(index + 1);
    		dp[index] = envelope[1];
    		if( index == res) res++;
    	 }
    	 return res;
    }
	
    //357. Count Numbers with Unique Digits
//    This is a digit combination problem. Can be solved in at most 10 loops.
//    When n == 0, return 1. I got this answer from the test case.
//    When n == 1, _ can put 10 digit in the only position. [0, ... , 10]. Answer is 10.
//    When n == 2, _ _ first digit has 9 choices [1, ..., 9], second one has 9 choices excluding the already chosen one. So totally 9 * 9 = 81. answer should be 10 + 81 = 91
//    When n == 3, _ _ _ total choice is 9 * 9 * 8 = 684. answer is 10 + 81 + 648 = 739
//    When n == 4, _ _ _ _ total choice is 9 * 9 * 8 * 7.
//    ...
//    When n == 10, _ _ _ _ _ _ _ _ _ _ total choice is 9 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1
//    When n == 11, _ _ _ _ _ _ _ _ _ _ _ total choice is 9 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1 * 0 = 0
    public int countNumbersWithUniqueDigits(int n) {
    	if (n == 0)  return 1;
        int ans = 10, base = 9;
        for (int i = 2; i <= n && i <= 10; i++) {
            base = base * (9 - i + 2);
            ans += base;
        }
        return ans;
    }
    
    //363. Max Sum of Rectangle No Larger Than K
    //hard.....
    
    
    //368. Largest Divisible Subset
    public List<Integer> largestDivisibleSubset(int[] nums) {
    	List<Integer> res = new ArrayList<Integer>();
    	if(nums.length == 0) return res;
        Arrays.sort(nums);
    	int[] dp = new int[nums.length];
    	dp[0] = 1;
    	for(int i = 1; i < nums.length; i++){
			dp[i] = 1;
    		for(int j = i - 1; j >= 0; j--){
    			if(nums[i]%nums[j] == 0){
    				dp[i] = Math.max(dp[i], dp[j] + 1);
    			}
    		}
    	}
    	int max = 0, p = -1;
    	for(int i = 0; i < dp.length; i++){
    		if(dp[i] > max){
    			max = dp[i];
    			p = i;
    		}
    	}
    	res.add(nums[p]);
    	int cur = nums[p];
    	for(int i = p - 1; i >= 0; i--){
    		if(cur%nums[i]  == 0 && dp[i] == max - 1){
    			res.add(nums[i]);
    			cur = nums[i];
    			max--;
    		}
    	}
        return res;
    }
    
    //375. Guess Number Higher or Lower II
    //dp[i][j] = min (i<=k<=j) { k + max(dp[i][k-1], dp[k+1][j]) }
    public int getMoneyAmount(int n) {
        int[][] dp = new int[n + 2][n + 2];
        for(int len = 1; len < n; len++){
        	for(int from = 1, to = from + len; to <= n; from++, to++){
        		dp[from][to] = Integer.MAX_VALUE;
        		for(int k = from; k <= to; k++){
        			dp[from][to] = Math.min(dp[from][to], Math.max(dp[from][k - 1], dp[k + 1][to]) + k);
        		}
        	}
        }
        return dp[1][n];
    }
    
    //376. Wiggle Subsequence
    public int wiggleMaxLength(int[] nums) {
    	if( nums.length <= 1) return nums.length;
    	int len = nums.length;
        int[] dpP = new int[len + 1];
        int[] dpN = new int[len + 1];
        dpP[1] = 1; dpN[1] = 1;
        for(int i = 1; i < len; i++){
        	dpP[i + 1] = 1;
        	dpN[i + 1] = 1;
        	for(int j = i - 1; j >= 0;j--){
        		if(nums[i] > nums[j]) {
                	dpP[i + 1] = Math.max(dpP[i + 1], dpN[j + 1] + 1);
        		} else if(nums[i] < nums[j]){
                	dpN[i + 1] = Math.max(dpN[i + 1], dpP[j + 1] + 1);
        		}
        	}
        }
        return Math.max(dpP[len], dpN[len]);
    }
    
    //377. Combination Sum IV
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for(int i = 1; i <= target; i++){
        	for(int j = 0; j < nums.length; j++){
        		if(i - nums[j] >= 0){
        			dp[i] += dp[i - nums[j]];
        		}
        	}
        }
        return dp[target];
    }
    //recursive
    public int combinationSum4_Solution2(int[] nums, int target) {
    	int res = 0;
    	if(target == 0) return 1;
    	for (int i = 0; i < nums.length; i++) {
            if (target >= nums[i]) {
                res += combinationSum4(nums, target - nums[i]);
            }
        }
        return res;
    }
    
    //392. Is Subsequence
    public boolean isSubsequence(String s, String t) {
        boolean res = false;
        if(s.length() <= 1 && t.indexOf(s) >= 0){
        	return true;
        }
        int pos = t.indexOf(s.charAt(0));
        if(pos < 0) return false;
        res = isSubsequence(s.substring(1), t.substring(pos + 1));
        return res;
    }
    
    //416. Partition Equal Subset Sum
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int i = 0; i < nums.length; i++){
        	sum += nums[i];
        }
        if(sum%2 != 0) return false;
        int target = sum/2;
        return findSum(nums, target);
    }

    private boolean findSum(int[] nums, int target) {
		boolean[] dp = new boolean[target + 1];
		dp[0] = true;
		for (int i = 0; i < nums.length; i++) {
		    for (int j = target; j >= nums[i]; j--) {
	    		dp[j] = dp[j] || dp[j - nums[i]];
		    }
		}
        return dp[target];
	}
    
	//464. Can I Win
    
    
    //474. Ones and Zeroes
    public int findMaxForm(String[] strs, int m, int n) {
    	int[][] dp = new int[m + 1][n + 1];
    	for(String str : strs){
    		int c0 = 0, c1 = 0;
    		for(int k = 0; k < str.length(); k++){
    			if(str.charAt(k) == '0') c0++;
    			else if(str.charAt(k) == '1') c1++;
    		}
    		for(int i = m; i >= 0; i--){
    			for(int j = n; j >= 0; j--){
    				if( c0 <= i && c1 <= j){
    					dp[i][j] = Math.max(dp[i][j], dp[i - c0][j - c1] + 1);
    				} 
    			}
    		}
    	}
        return dp[m][n];
    }
	
	//486. Predict the Winner
    public boolean PredictTheWinner(int[] nums) {
        int[][] dp = new int[nums.length + 1][nums.length + 1];
        int[][] sum = new int[nums.length + 1][nums.length + 1];
        for(int i = 0; i < nums.length; i++){
        	sum[i][i] = nums[i];
        	for(int j = i + 1; j < nums.length; j++){
        		sum[i][j] = sum[i][j - 1] + nums[j];
        	}
        }
        for(int i = 0; i < nums.length; i++) dp[i][i] = nums[i];
        for(int len = 1; len < nums.length; len++){
        	for(int i = 0; i < nums.length && i + len < nums.length; i++){
        		int j = i + len;
        		dp[i][j] = Math.max((sum[i + 1][j] - dp[i + 1][j]) + nums[i], 
        				(sum[i][j - 1] - dp[i][j - 1]) + nums[j]);
        		
        	}
        }
        return dp[0][nums.length - 1] >= sum[0][nums.length - 1] - dp[0][nums.length - 1];
    }
    //recursive solution
    public boolean PredictTheWinner_Recursive(int[] nums) {
        return helper(nums, 0, nums.length - 1, new Integer[nums.length][nums.length]) >= 0;
    }
    private int helper(int[] nums, int s, int e, Integer[][] mem){    
        if(mem[s][e] == null)
            mem[s][e] = s==e ? nums[e] : Math.max(nums[e] - helper(nums, s, e - 1, mem), nums[s] - helper(nums, s + 1, e, mem));
        return mem[s][e];
    }
    
    //494. Target Sum
    //sum(p) - sum(n) = target  ====>>  sum(p) = target + sum(nums)
    public int findTargetSumWays(int[] nums, int S) {
    	int sum = 0;
    	for(int n : nums) sum += n;
    	if(sum < S || (sum + S)%2 != 0) return 0;
    	int target = (sum + S)/2;
    	int[] dp = new int[target + 1];
    	dp[0] = 1;
    	for(int n : nums){
    		for(int i = target; i >= n; i--){
    			dp[i] += dp[i - n];
    		}
    	}
    	return dp[target];
    }
    //recursive
    public int findTargetSumWays_Recursive(int[] nums, int S) {
    	return helper(nums, 0, nums.length - 1, S);
    }

	private int helper(int[] nums, int begin, int end, int s) {
		if(begin > end){
			if( s == 0) return 1;
			else return 0;
		}
		return helper(nums, begin, end - 1, s - nums[end]) 
				+ helper(nums, begin, end - 1, s + nums[end]);
	}
	
	//638. Shopping Offers
	public int shoppingOffers_Recursive(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
		int res = 0;
		for(int i = 0; i < needs.size(); i++){
			res += needs.get(i) * price.get(i);
		}
        for(int i = 0; i < special.size(); i++){
        	List<Integer> thisSp = special.get(i);
        	List<Integer> newNeeds = new ArrayList<Integer>();
        	for(int k = 0; k < needs.size(); k++){
        		if(thisSp.get(k) > needs.get(k)) break;
        		newNeeds.add(needs.get(k) - thisSp.get(k));
        	}
        	if(newNeeds.size() < needs.size()) continue;
        	res = Math.min(res, shoppingOffers_Recursive(price, special, newNeeds) + thisSp.get(thisSp.size() - 1));
         }
        return res;
    }
	
	//646. Maximum Length of Pair Chain
	public int findLongestChain(int[][] pairs) {
        Arrays.sort(pairs, new Comparator<int[]>() {
			@Override
			public int compare(int[] a, int[] b) {
				if(a[0] != b[0])
					return a[0] > b[0] ? 1 : -1;
				return a[1] >= b[1] ? 1 : -1;
			}
        });
        int[] dp = new int[pairs.length + 1];
        for(int i = 0; i < pairs.length; i++){
        	dp[i + 1] = 1;
        	for(int j = i - 1; j >= 0; j--){
        		if(pairs[i][0] > pairs[j][1]){
        			dp[i + 1] = Math.max(dp[i + 1], dp[j + 1] + 1);
        		}
        	}
        }
        return dp[pairs.length];
    }
	
	//647. Palindromic Substrings
	public int countSubstrings(String s) {
        int[] dp = new int[s.length() + 1];
        for(int i = 0; i < s.length(); i++){
        	dp[i + 1] = dp[i] + 1;
        	for(int j = i - 1; j >= 0; j--){
        		if(helper(j, i, s)) dp[i + 1]++;
        	}
        }
        return dp[s.length()];
    }

	private boolean helper(int right, int left, String s) {
		while(right < left){
			if(s.charAt(right) != s.charAt(left)) return false;
			right++;
			left--;
		}
		return true;
	}
	
	//650. 2 Keys Keyboard
	public int minSteps(int n) {
        int[] dp = new int[n + 1];
        for(int i = 2; i <= n; i++){
        	dp[i] = i;
        	for(int j = 2; j <= Math.sqrt(i); j++){
        		if(i%j == 0){
        			int min = Math.min(dp[j] + i/j, dp[i/j] + j);
        			dp[i] = Math.min(dp[i], min);
        		}
        	}
        }
        return dp[n];
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
