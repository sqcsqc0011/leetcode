package leetcode_bytag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
