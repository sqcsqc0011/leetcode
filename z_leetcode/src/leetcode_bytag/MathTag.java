package leetcode_bytag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MathTag {

	//535. Encode and Decode TinyURL
	public class Codec {
		List<String> urls = new ArrayList<String>();
	    // Encodes a URL to a shortened URL.
	    public String encode(String longUrl) {
	        urls.add(longUrl);
	        return String.valueOf(urls.size()-1);
	    }

	    // Decodes a shortened URL to its original URL.
	    public String decode(String shortUrl) {
	        int index = Integer.valueOf(shortUrl);
	        return (index<urls.size())?urls.get(index):"";
	    }
	}
	
	//523. Continuous Subarray Sum
	public boolean checkSubarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, -1);
        int sum = 0;
        for(int i = 0; i < nums.length; i++){
        	sum = sum + nums[i];
        	if( k != 0)
        		sum = sum%k;
        	if(map.containsKey(sum)) {
        		if(i - map.get(sum) > 2 )
        			return true;
        	}
        	else map.put(sum, i);
        }
        return false;
	}
	
	//517. Super Washing Machines
	public int findMinMoves(int[] machines) {
        int sum = 0;
        for(int num : machines){
        	sum += num;
        }
        if( sum%machines.length != 0) return -1;
        int ave = sum/machines.length;
        int cnt = 0, res = 0;
		for(int num : machines){
			cnt += num - ave;
			res = Math.max(res, Math.max(Math.abs(cnt), num - ave));
		}
		return res;
    }
	
	//
//	姹備竴涓猙ase锛屼娇寰楃粰瀹氱殑鏁拌浆鍖栦负base涓嬬殑鏁版椂姣忎竴浣嶉兘鏄�1
//	鏁版嵁閲忔槸10^18, 鏃堕棿澶嶆潅搴︿笉鑳借秴杩嘜(log(n))
//	鎸夎浆鍖栧悗1鐨勪綅鏁版潵閬嶅巻, 鍒ゅ畾瀵瑰簲浣嶆暟鐨�1琛ㄧず鐨勫�兼槸鍚︾瓑浜巒
//	鍋囪base涓簁, 杞寲鍚庣殑鏁颁腑1鐨勪綅鏁颁负m+1, 鍒欐湁n=k^0+k^1+k^2+...+k^m
//	鏄剧劧n>k^m
//	鍙堟湁n=k^0+k^1+k^2+...+k^m < (k+1)^m
//	鍥犳鍙互寰楀埌n^(1/m - 1) < k < n^(1/m)
//	鐢变簬k涓烘暣鏁�, 鍥犳鍙緱k=鈱妌^(1/m)鈱�
	public String smallestGoodBase(String n) {
        long num = Long.parseLong(n);
        long k = 0;
        for( long i = 5; i > 1; i--){
        	k = (long) Math.pow(num, 1.0/i);
        	if( k >= 2 && isGoodBase(num, k, i)) 
        		return String.valueOf(k);
        }
        return String.valueOf(num - 1);
    }
	
	private boolean isGoodBase(long num, long base, long m) {
        long sum = num;
        long val = 1;
        for(long i = 0; i <= m; i++) {
            sum -= val;
            val *= base;
        }
        return sum == 0;
    }
	
	//453. Minimum Moves to Equal Array Elements
	public int minMoves(int[] nums) {
		int min = nums[0], sum = 0;
		for(int num : nums){
			if( num < min) min = num;
			sum += num;
		}
		return sum - min * nums.length;
    }
	
	//462. Minimum Moves to Equal Array Elements II
	public int minMoves2(int[] nums) {
		Arrays.sort(nums);
		int i = 0, j = nums.length - 1, moves = 0;
		while(i < j) {
			moves += nums[j] - nums[i];
			j--;i++;
		}
		return moves;
    }
	
	//441. Arranging Coins
	public int arrangeCoins(int n) {
		long nLong = (long)n;
        long st = 0, ed = nLong, mid = 0;
        while (st <= ed){
            mid = st + (ed - st) / 2;
            if (mid * (mid + 1) <= 2 * nLong){
                st = mid + 1;
            }else{
                ed = mid - 1;
            }
        }
        return (int)(st - 1);
    }
	
	//423. Reconstruct Original Digits from English
	public String originalDigits(String s) {
	    int[] count = new int[10];
	    for (int i = 0; i < s.length(); i++){
	        char c = s.charAt(i);
	        if (c == 'z') count[0]++;
	        if (c == 'w') count[2]++;
	        if (c == 'x') count[6]++;
	        if (c == 's') count[7]++; //7-6
	        if (c == 'g') count[8]++;
	        if (c == 'u') count[4]++; 
	        if (c == 'f') count[5]++; //5-4
	        if (c == 'h') count[3]++; //3-8
	        if (c == 'i') count[9]++; //9-8-5-6
	        if (c == 'o') count[1]++; //1-0-2-4
	    }
	    count[7] -= count[6];
	    count[5] -= count[4];
	    count[3] -= count[8];
	    count[9] = count[9] - count[8] - count[5] - count[6];
	    count[1] = count[1] - count[0] - count[2] - count[4];
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i <= 9; i++){
	        for (int j = 0; j < count[i]; j++){
	            sb.append(i);
	        }
	    }
	    return sb.toString();
	}
	
	//415. Add Strings
	public String addStrings(String num1, String num2) {
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
	
	//413. Arithmetic Slices
	public int numberOfArithmeticSlices(int[] A) {
	    int curr = 0, sum = 0;
	    for (int i = 2; i < A.length; i++)
	        if (A[i] - A[i-1] == A[i-1] - A[i-2]) {
	            curr += 1;
	            sum += curr;
	        } else {
	            curr = 0;
	        }
	    return sum;
	}
	
	//400. Nth Digit
	public int findNthDigit(int n) {
        int digit = 1, start = 1;
        long count = 9;
        while( n > digit * count){
        	n -= digit * count;
        	digit++;
        	count *= 10;
        	start *= 10;
        }
        start += (n - 1) / digit;
		String s = Integer.toString(start);
		return Character.getNumericValue(s.charAt((n - 1) % digit));  
    }
	
	//397. Integer Replacement
	public int integerReplacement(int n) {
		if(n==Integer.MAX_VALUE) return 32;
		if(n == 1) return 0;
        if(n%2 == 0) return integerReplacement(n/2) + 1;
        else {
        	return Math.min(integerReplacement(n - 1), integerReplacement(n + 1)) + 1;
        }
    }
	
	//396. Rotate Function
	public int maxRotateFunction(int[] A) {
		if( A.length <= 1) return 0;
		int sum = 0, firstsum = 0;
        for( int i = 0; i < A.length; i++){
        	sum += A[i];
        	firstsum += A[i] * i;
        }
        int totalgap = 0, res = firstsum;
        for( int i = A.length - 1; i >= 0; i--){
        	int gap = sum - A[i] - A[i] * (A.length - 1);
        	totalgap += gap;
        	res = Math.max(res,firstsum + totalgap);
        }
        return res;
    }
	
	//372. Super Pow
	//杩欎釜鍏紡鐨勬剰鎬濆氨鏄紝(a*b)%c=(a%c)*(b%c),鍥犳鎴戜滑鍙互鍦ㄦ瘡涓�姝ヨ绠楃粨鏋滀箣鍚庨兘杩欎箞澶勭悊锛岄槻姝㈡孩鍑恒��
	public boolean morethanzero(int[] x){
        for(int i = x.length - 1;i >= 0;i--){
            if(x[i] > 0) return true;
        }
        return false;
    }

	//楂樼簿搴﹂櫎娉�
    public void div(int[] x,int y){
        int tmp = 0;
        for(int i = 0;i < x.length;i++){
           x[i] += tmp*10;
           tmp = x[i] % y;
           x[i] = x[i] /y;
        }

    }

    public int superPow(int a, int[] b) {
        if (morethanzero(b) == false) return 1;
        a = a%1337;
        boolean isEven = false;
        if(b[b.length - 1] % 2 == 0) isEven = true;
        div(b, 2);
        int result = superPow(a, b);
        result = result % 1337;
        result *= result;
        result = result % 1337;
        if(isEven == false){
            result *= a;
            result = result % 1337;
        }
        return result;
    }
    
    //264. Ugly Number II
    //idx琛ㄧず褰撳墠prime涔樼殑鏈�澶ф暟鐨勪綅缃�
    public int nthUglyNumber(int n) {
        int[] ugs = new int[n];
        ugs[0] = 1;
        int idx2 = 0, idx3 = 0, idx5 = 0;
        for(int i = 1; i < n; i++){
        	int min = Math.min( Math.min(2 * ugs[idx2], 3 * ugs[idx3]), 5 * ugs[idx5]);
        	ugs[i] = min;
        	if(min == 2 * ugs[idx2]) idx2++;
        	if(min == 3 * ugs[idx3]) idx3++;
        	if(min == 5 * ugs[idx5]) idx5++;
        }
        return ugs[n - 1];
    }
	
	//313. Super Ugly Number
    public int nthSuperUglyNumber(int n, int[] primes) {
        int[] ugn = new int[n];
        ugn[0] = 1;
        int[] index = new int[primes.length];
        for(int i = 1; i < n; i++){
        	int min = Integer.MAX_VALUE;
        	for(int j = 0; j < primes.length; j++){
        		min = Math.min(ugn[index[j]] * primes[j], min);
        	}
        	ugn[i] = min;
        	for(int j = 0; j < primes.length; j++){
        		if(min == ugn[index[j]] * primes[j]) index[j]++;
        	}
        }
        return ugn[n - 1];
    }
	
	//319. Bulb Switcher
    public int bulbSwitch(int n) {
    	return (int) Math.sqrt(n);
    }
    
    //672. Bulb Switcher II
//  We only need to consider special cases which n<=2 and m < 3. When n >2 and m >=3, the result is 8.
//	The four buttons:
//	Flip all the lights.
//	Flip lights with even numbers.
//	Flip lights with odd numbers.
//	Flip lights with (3k + 1) numbers, k = 0, 1, 2, ...
//	If we use button 1 and 2, it equals to use button 3.
//	Similarly...
//
//	1 + 2 --> 3, 1 + 3 --> 2, 2 + 3 --> 1
//	So, there are only 8 cases.
//	All_on, 1, 2, 3, 4, 1+4, 2+4, 3+4
//	And we can get all the cases, when n>2 and m>=3.
    public int flipLights(int n, int m) {
    	if(m==0) return 1;
        if(n==1) return 2;
        if(n==2&&m==1) return 3;
        if(n==2) return 4;
        if(m==1) return 4;
        if(m==2) return 7;
        if(m>=3) return 8;
        return 8;
    }
    
    //326. Power of Three
    public boolean isPowerOfThree(int n) {
    	if(n <= 0) return false;
        while(n > 1){
        	if(n%3 != 0) return false;
        	n = n/3;
        }
        return true;
    }
    
    //335. Self Crossing
    //hard.....
    public boolean isSelfCrossing(int[] x) {
        for(int i=3, l=x.length; i<l; i++) {
            if(x[i]>=x[i-2] && x[i-1]<=x[i-3]) return true;  // Case 1: current line crosses the line 3 steps ahead of it
            else if(i>=4 && x[i-1]==x[i-3] && x[i]+x[i-4]>=x[i-2]) return true; // Case 2: current line crosses the line 4 steps ahead of it
            else if(i>=5 && x[i-2]>=x[i-4] && x[i]+x[i-4]>=x[i-2] && x[i-1]<=x[i-3] && x[i-1]+x[i-5]>=x[i-3]) return true;  // Case 3: current line crosses the line 6 steps ahead of it
        }
        return false;
    }
    
    //365. Water and Jug Problem
    public boolean canMeasureWater(int x, int y, int z) {
    	//limit brought by the statement that water is finallly in one or both buckets
        if(x + y < z) return false;
        //case x or y is zero
        if( x == z || y == z || x + y == z ) return true;
        int gcd = getGCD(x, y);
        return z%gcd == 0;
    }
    
    //367. Valid Perfect Square
	// Newtons's method.
    // Find square root of num and square it
    // square == num ? true : false;
	public boolean isPerfectSquare(int num) {
		long t = num;
	    while (t * t > num) {
	        t = (t + num / t) / 2;
	    }
	    return t * t == num;
    }
    
	//592. Fraction Addition and Subtraction
	public String fractionAddition(String expression) {
		int a1 = 0, a2 = 1, signIndex = -1;
		boolean sign = true;
		for(int i = 0; i < expression.length(); i++) {
			if(expression.charAt(i) == '-' || expression.charAt(i) == '+' || i == expression.length() - 1) {
				String b = expression.substring(signIndex + 1, i);
				if(i == expression.length() - 1)  b = expression.substring(signIndex + 1, i + 1);
				if(b.length() == 0) {
					sign = expression.charAt(i) == '+'; 
					signIndex = i;
					continue;
				}
				int b1 = Integer.parseInt(b.split("/")[0]), b2 = Integer.parseInt(b.split("/")[1]);
				if(sign) a1 = a1 * b2 + a2 * b1;
				else a1 = a1 * b2 - a2 * b1;
				a2 = a2 * b2;
				int gcd = getGCD(a1, a2);
				a1 = a1/gcd;
				a2 = a2/gcd;
				if(a2 < 0) {
					a2 = -a2;
					a1 = -a1;
				}
				sign = expression.charAt(i) == '+'; 
				signIndex = i;
			}
		}
		return a1 + "/" + a2;
    }
    
	private int getGCD(int x, int y) {
		while(y != 0 ){
	        int temp = y;
	        y = x%y;
	        x = temp;
	    }
	    return x;
	}
    
	//593. Valid Square
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
