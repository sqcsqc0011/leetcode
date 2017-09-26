package leetcode_contest;

import java.util.ArrayList;
import java.util.List;

public class Weekly_51 {

	//681. Next Closest Time
	public String nextClosestTime(String time) {
        int[] t = new int[]{Character.getNumericValue(time.charAt(0)), Character.getNumericValue(time.charAt(1)), Character.getNumericValue(time.charAt(3)), Character.getNumericValue(time.charAt(4))};
        int preH = t[0] * 10 + t[1], preM = t[2] * 10 + t[3];
        List<Integer> hours = new ArrayList<Integer>();
        List<Integer> mins = new ArrayList<Integer>();
        for(int i = 0; i < t.length; i++){
        	for(int j = 0; j < t.length; j++){
        		if(t[i] * 10 + t[j] < 24) hours.add(t[i] * 10 + t[j]);
        		if(t[i] * 10 + t[j] < 60) mins.add(t[i] * 10 + t[j]);
        	}
        }
        int nextM = -1, minM = preM;
        for(int m : mins){
        	if(m > preM && nextM == -1) nextM = m;
        	else if( m > preM && m < nextM) nextM = m;
        	if(m < minM) minM = m;
        }
        if(nextM != -1) return (preH < 10 ? "0" + preH : "" + preH) + ":" + (nextM < 10 ? "0" + nextM : "" + nextM);
        int nextH = -1, minH = preH;
        for(int h : hours){
        	if(h > preH && nextH == -1) nextH = h;
        	else if(h > preH && h < nextH) nextH = h;
        	if(h < minH) minH = h;
        }
        if(nextH != -1) return (nextH < 10 ? "0" + nextH : "" + nextH) + ":" + (minM < 10 ? "0" + minM : "" + minM);
        return (minH < 10 ? "0" + minH : "" + minH) + ":" +(minM < 10 ? "0" + minM : "" + minM);
    }
	
	//682. Baseball Game
	public int calPoints(String[] ops) {
        int sum = 0;
        List<Integer> helper = new ArrayList<Integer>();
        for(int i = 0; i < ops.length; i++){
        	int cur = helper.size();
        	if(ops[i].equals("+")) {
        		int last1 = cur >= 1 ? helper.get(cur - 1) : 0;
        		int last2 = cur >= 2 ? helper.get(cur - 2) : 0;
        		sum += last1 + last2;
        		helper.add(last1 + last2);
        	} else if(ops[i].equals("D")){
        		int last1 = cur >= 1 ? helper.get(cur - 1) : 0;
        		sum += 2 * last1;
        		helper.add(2 * last1);
        	} else if(ops[i].equals("C")){
        		if(cur >= 1) {
        			sum -= helper.get(cur - 1);
        			helper.remove(cur - 1);
        		}
        	} else {
        		int num = Integer.parseInt(ops[i]);
        		sum += num;
        		helper.add(num);
        	}
        }
		return sum;
    }
	
	//683. K Empty Slots
	public int kEmptySlots(int[] flowers, int k) {
		boolean[] helper = new boolean[flowers.length];
		for(int i = 0; i < flowers.length; i++){
			int curPos = flowers[i]; 
			helper[curPos - 1] = true;
			int max = -1;
			for(int j = curPos; j < flowers.length; j++){
				if(helper[j] == true) {
					max = j - curPos;
					break;
				}
			}
			int min = -1;
			for(int j = curPos - 2; j >= 0; j--){
				if(helper[j] == true) {
					min = curPos - j - 2;
					break;
				}
			}
			if(min == k || max == k) return i + 1;
		}
		return -1;
    }
	
	//684. Redundant Connection
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
