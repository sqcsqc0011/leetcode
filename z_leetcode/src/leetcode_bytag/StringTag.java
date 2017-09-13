package leetcode_bytag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import z_leetcode.TreeNode;

public class StringTag {

	//344. Reverse String
	public String reverseString(String s) {
		StringBuilder sb = new StringBuilder(s);
        return sb.reverse().toString();
    }
	
	//345. Reverse Vowels of a String
	public String reverseVowels(String s) {
        Set<Character> set = new HashSet<Character>(Arrays.asList('a', 'o', 'i', 'u', 'e', 'A', 'O', 'I', 'U', 'E'));
        int left = 0, right = s.length() - 1;
        char[] arr = s.toCharArray();
        while(left < right){
        	while(left < s.length() && !set.contains(arr[left])){
        		left++;
        		if(left >= right ) break;
        	}
        	while(0 <= right && !set.contains(arr[right])){
        		right--;
        		if(left >= right ) break;
        	}
        	if(left >= right) break;
        	char tmp = arr[left];
        	arr[left] = arr[right];
        	arr[right] = tmp;
        	left++;right--;
        }
        return new String(arr);
    }
	
	//383. Ransom Note
	public boolean canConstruct(String ransomNote, String magazine) {
		int[] arr = new int[26];
		for(int i = 0; i < magazine.length(); i++){
			arr[magazine.charAt(i) - 'a']++;
		}
		for(int i = 0; i < ransomNote.length(); i++){
			arr[ransomNote.charAt(i) - 'a']--;
			if(arr[ransomNote.charAt(i) - 'a'] < 0) return false;
		}
		return true;
    }	
	
	//385. Mini Parser
	
	
	//434. Number of Segments in a String
	public int countSegments(String s) {
        int res = 0;
        for(int i = 0; i < s.length(); i++){
        	if(s.charAt(i) != ' ' && (i == 0 || s.charAt(i - 1) == ' ' ))
        		res++;
        }
        return res;
    }
	
	//459. Repeated Substring Pattern
	public boolean repeatedSubstringPattern(String s) {
        int num = s.length();
        for(int i = num/2; i >= 1; i--){
        	if( num%i == 0){
        		String str = s.substring(0, i);
				StringBuilder sb = new StringBuilder();
        		for( int j = 1; j <= num/i; j++){
        			sb.append(str);
        		}
        		if( sb.toString().equals(s)) return true;
        	}
        }
        return false;
    }
	
	//468. Validate IP Address
	public String validIPAddress(String IP) {
		if(isValidIPv4(IP)) return "IPv4";
		else if(isValidIPv6(IP)) return "IPv6";
		else return "Neither";
	}

	public boolean isValidIPv4(String ip) {
		if(ip.length()<7) return false;
		if(ip.charAt(0)=='.') return false;
		if(ip.charAt(ip.length()-1)=='.') return false;
		String[] tokens = ip.split("\\.");
		if(tokens.length!=4) return false;
		for(String token:tokens) {
			if(!isValidIPv4Token(token)) return false;
		}
		return true;
	}
	public boolean isValidIPv4Token(String token) {
		if(token.startsWith("0") && token.length()>1) return false;
		try {
			int parsedInt = Integer.parseInt(token);
			if(parsedInt<0 || parsedInt>255) return false;
			if(parsedInt==0 && token.charAt(0)!='0') return false;
		} catch(NumberFormatException nfe) {
			return false;
		}
		return true;
	}
		
	public boolean isValidIPv6(String ip) {
		if(ip.length()<15) return false;
		if(ip.charAt(0)==':') return false;
		if(ip.charAt(ip.length()-1)==':') return false;
		String[] tokens = ip.split(":");
		if(tokens.length!=8) return false;
		for(String token: tokens) {
			if(!isValidIPv6Token(token)) return false;
		}
		return true;
	}
	public boolean isValidIPv6Token(String token) {
		if(token.length()>4 || token.length()==0) return false;
		char[] chars = token.toCharArray();
		for(char c:chars) {
			boolean isDigit = c>=48 && c<=57;
			boolean isUppercaseAF = c>=65 && c<=70;
			boolean isLowerCaseAF = c>=97 && c<=102;
			if(!(isDigit || isUppercaseAF || isLowerCaseAF)) 
				return false;
		}
		return true;
	}
	
	//520. Detect Capital
	public boolean detectCapitalUse(String word) {
        int numUpper = 0;
        for (int i=0;i<word.length();i++) {
            if (Character.isUpperCase(word.charAt(i))) numUpper++;
        }
        if (numUpper == 1) return Character.isUpperCase(word.charAt(0));
        return numUpper == 0 || numUpper == word.length();
    }
	
	//539. Minimum Time Difference
	public int findMinDifference(List<String> timePoints) {
        int[] time = new int[timePoints.size()];
        for(int i = 0; i < timePoints.size(); i++){
        	String[] timePoint = timePoints.get(i).split(":");
        	time[i] = Integer.parseInt(timePoint[0]) * 60 + Integer.parseInt(timePoint[1]);
        }
        Arrays.sort(time);
        int res = Math.min(time[time.length - 1] - time[0], 1440 - time[time.length - 1] + time[0]);
        for(int i = 1; i < time.length; i++){
        	res = Math.min(res, time[i] - time[i - 1]);
        }
        return res;
    }
	
	//541. Reverse String II
	public String reverseStr(String s, int k) {
		char[] arr = s.toCharArray();
		int begin = 0, end = 0;
		while( begin < s.length()){
			end = begin + k - 1;
			if( end < s.length()) reverseStr(arr, begin, end);
			else reverseStr(arr, begin, s.length() - 1);
			begin += 2 * k;
		}
		return new String(arr);
    }

	private void reverseStr(char[] arr, int begin, int end) {
		while( begin < end){
			char tmp = arr[begin];
			arr[begin] = arr[end];
			arr[end] = tmp;
			begin++; end--;
		}
	}
	
	//521. Longest Uncommon Subsequence I
	public int findLUSlengthI(String a, String b) {
		return a.equals(b) ? -1 : Math.max(a.length(), b.length());
	}
	
	//522. Longest Uncommon Subsequence II
	public int findLUSlength(String[] strs) {
        boolean allEqual = true;
        for(int i = 1; i < strs.length; i++){
        	if(!strs[i].equals(strs[i - 1])) allEqual = false;
        }
        if(allEqual) return -1;
        Arrays.sort(strs, (a, b) -> Integer.compare(b.length(), a.length()));
        for(int i = 0; i < strs.length; ++i) {
            String first = strs[i];
            boolean allOtherSub = true;
            for(int j = 0; j < strs.length; ++j) {
                if(j == i) continue;
                String second = strs[j];
                if(first.equals(second) || aSubB(first, second)) {
                	allOtherSub = false;
                    break;
                }
            }
            if(allOtherSub) return first.length();
        }
        return -1;
    }
    
    private boolean aSubB(String a, String b) {
        int index = -1;
        for(int k = 0; k < a.length(); k++) {
            char c = a.charAt(k);
            index = b.indexOf(c, index + 1);
            if(index == -1) return false;
        }
        return true;
    }
	
	//537. Complex Number Multiplication
    public String complexNumberMultiply(String a, String b) {
    	String a1 = a.split("\\+")[0];
        int a_1 = a1.indexOf("-") == 0 ? 0 - Integer.parseInt(a1.substring(1)) : Integer.parseInt(a1);
    	String a2 = a.split("\\+")[1];
    	a2 = a2.substring(0, a2.length() - 1);
    	int a_2 = a2.indexOf("-") == 0 ? 0 - Integer.parseInt(a2.substring(1)) : Integer.parseInt(a2);
    	
    	String b1 = b.split("\\+")[0];
        int b_1 = b1.indexOf("-") == 0 ? 0 - Integer.parseInt(b1.substring(1)) : Integer.parseInt(b1);
    	String b2 = b.split("\\+")[1];
    	b2 = b2.substring(0, b2.length() - 1);
    	int b_2 = b2.indexOf("-") == 0 ? 0 - Integer.parseInt(b2.substring(1)) : Integer.parseInt(b2);

    	int res_1 = a_1 * b_1 - a_2 * b_2;
    	int res_2 = a_2 * b_1 + a_1 * b_2;
    	String res = res_1 + "+" + res_2 + "i";
    	return res;
    }
	
	//551. Student Attendance Record I
    public boolean checkRecord(String s) {
        int A = 0, L = 0;
        for(int i = 0; i < s.length(); i++){
        	if(s.charAt(i) == 'L') L++;
        	else {
        		L = 0;
        		if(s.charAt(i) == 'A') A++;
        	}
        	if(A > 1 || L > 2) return false;
        }
        return true;
    }
	
	//553. Optimal Division
    //answer will always be (x1*x3*x4.....)/x2
    public String optimalDivision(int[] nums) {
    	if(nums.length == 1) return nums[0] + "";
    	if(nums.length == 2) return nums[0] + "/" + nums[1];
    	String res = nums[0] + "/(" + nums[1];
    	for(int i = 2; i < nums.length; i++){
    		res += "/" + nums[i];
    	}
    	res += ")";
    	return res;
    }
	
	//556. Next Greater Element III
    //consider overflow!!
    public int nextGreaterElement(int n) {
        char[] number = (n + "").toCharArray();
        int i = number.length - 1, j = i + 1;
        for (i = number.length - 1; i > 0; i--){
            if (number[i - 1] < number[i]) break;
        }
        if (i == 0) return -1;
        int index = i;
        char smallest = number[i];
        for (j = i + 1; j < number.length; j++){
            if(number[j] > number[i - 1] && number[j] < smallest) {
            	index = j;
            	smallest = number[j];
            }
        }
        number[index] = number[i - 1];
        number[i - 1] = smallest;
        Arrays.sort(number, i, number.length);
        long val = Long.parseLong(new String(number));
        return (val <= Integer.MAX_VALUE) ? (int) val : -1;
    }

	//557. Reverse Words in a String III
    public String reverseWords(String s) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        char[] a = s.toCharArray();
        for(int i = 0; i<a.length;i++){
            if(a[i] != ' '){
                sb1.append(a[i]);
            }else{
                sb2.append(sb1.reverse().toString());
                sb1.setLength(0);
                sb2.append(" ");
            }
        }
        sb2.append(sb1.reverse().toString());
        return sb2.toString();
    }
    
    //564. Find the Closest Palindrome
    public String nearestPalindromic(String n) {
        Long num = Long.parseLong(n);
        Long highBound = getNextPalindrom(num + 1, true);
        Long lowBound = getNextPalindrom(num - 1, false);
        if (Math.abs(lowBound - num) <= Math.abs(highBound - num)) {
        	return String.valueOf(lowBound);
        }
        return String.valueOf(highBound);
    }

    private Long getNextPalindrom(Long limit, boolean up) {
        int inc = up ? 1 : -1;
        char[] limitArr = String.valueOf(limit).toCharArray();
        char[] base = limitArr.clone();
        replicateFirstHalf(base);
        for (int i = 0; i < limitArr.length; i++) {
          if ((up && base[i] > limitArr[i]) || (!up && base[i] < limitArr[i])) {
        	  return Long.parseLong(String.valueOf(base));
          } else if ((up && base[i] < limitArr[i]) ||
                     (!up && base[i] > limitArr[i])) {
            for (int j = (limitArr.length - 1) / 2; j >= 0; j--) {
              if (base[j] + inc < '0' || base[j] + inc > '9') {
                base[j] = '0';
              } else {
                base[j] += inc;
                break;
              }
            }
            if (base[0] == '0') {
              char[] temp = new char[base.length - 1];
              Arrays.fill(temp, '9');
              return Long.parseLong(String.valueOf(temp));
            }
            replicateFirstHalf(base);
            return Long.parseLong(String.valueOf(base));
          }
        }
        return Long.parseLong(String.valueOf(base));
    }

    private void replicateFirstHalf(char[] base) {
        for (int i = 0; i < base.length / 2; i++) {
        	base[base.length - 1 - i] = base[i];
        }
    }
    
    //583. Delete Operation for Two Strings
    //find the longest common subsequence
    public int minDistance(String word1, String word2) {
        int len1 = word1.length(), len2 = word2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];
        for(int i = 1; i <= len1; i++) {
        	for(int j = 1; j <= len2; j++) {
        		if(word1.charAt(i - 1) == word2.charAt(j - 1)) {
        			dp[i][j] = dp[i - 1][j - 1] + 1;
        		}
        		dp[i][j] = Math.max(dp[i][j], dp[i][j - 1]);
        		dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);        		
        	}
        }
        int longest = dp[len1][len2];
        return len1 + len2 - 2 * longest;
    }
    
    //591. Tag Validator
    public boolean isValid(String code) {
        Stack<String> stack = new Stack<>();
        for(int i = 0; i < code.length();){
            if(i>0 && stack.isEmpty()) return false;
            if(code.startsWith("<![CDATA[", i)){
                int j = i+9;
                i = code.indexOf("]]>", j);
                if(i < 0) return false;
                i += 3;
            }else if(code.startsWith("</", i)){
                int j = i + 2;
                i = code.indexOf('>', j);
                if(i < 0 || i == j || i - j > 9) return false;
                for(int k = j; k < i; k++){
                    if(!Character.isUpperCase(code.charAt(k))) return false;
                }
                String s = code.substring(j, i++);
                if(stack.isEmpty() || !stack.pop().equals(s)) return false;
            }else if(code.startsWith("<", i)){
                int j = i + 1;
                i = code.indexOf('>', j);
                if(i < 0 || i == j || i - j > 9) return false;
                for(int k = j; k < i; k++){
                    if(!Character.isUpperCase(code.charAt(k))) return false;
                }
                String s = code.substring(j, i++);
                stack.push(s);
            }else{
                i++;
            }
        }
        return stack.isEmpty();
    }
    
    //606. Construct String from Binary Tree
	public String tree2str(TreeNode t) {
		if(t == null) return "";
		String str = t.val + "";
		if(t.left == null && t.right == null) return str;
		if(t.left != null) {
			str += "(" + tree2str(t.left) + ")";
			if(t.right != null) {
				str += "(" + tree2str(t.right) + ")";
			}
		}
		else if(t.left == null) str += "()" + "(" + tree2str(t.right) + ")";
		return str;
	}
    
    //609. Find Duplicate File in System
	public List<List<String>> findDuplicate(String[] paths) {
        HashMap<String, List<String>> maps = new HashMap<String, List<String>>();
        for(String path : paths){
        	String[] files = path.split("\\s+");
        	String root = files[0];
        	for(int i = 1; i < files.length; i++){
        		String file = files[i];
        		for(int j = file.length() - 1; j >= 0; j--){
        			if(file.charAt(j) == '('){
        				String dir = root + "/" + file.substring(0, j);
        				String content = file.substring(j + 1, file.length() - 1);
        				List<String> strs = maps.getOrDefault(content, new ArrayList<String>());
        				strs.add(dir);
        				maps.put(content, strs);
        				break;
        			}
        		}
        	}
        }
        List<List<String>> res = new ArrayList<List<String>>();
        for(String key : maps.keySet()){
        	if(maps.get(key).size() > 1) res.add(maps.get(key));
        }
        return res;
    }
    
    //632. Smallest Range
	//hard!!! priorityqueue
//	public int[] smallestRange(List<List<Integer>> nums) {
//        
//    }
    
    //657. Judge Route Circle
	public boolean judgeCircle(String moves) {
		int u = 0, d = 0, l = 0, r = 0;
		for(int i = 0; i < moves.length(); i++){
			if(moves.charAt(i) == 'U') u++;
			else if(moves.charAt(i) == 'D') d++;
			else if(moves.charAt(i) == 'L') l++;
			else if(moves.charAt(i) == 'R') r++;
		}
		if( u == d && l == r) return true;
		return false;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
	
	
	
	
	
	
	
	
	
	
	
}
