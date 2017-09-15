package leetcode_bytag;

import z_leetcode.ListNode;

public class LinkedListTag {
	
	//2. Add Two Numbers
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		return addTwoNumbers(l1, l2, 0);
    }
	private ListNode addTwoNumbers(ListNode l1, ListNode l2, int over) {
		if(l1 == null && l2 == null && over == 0) return null;
        int sum = (l1 == null ? 0 : l1.val) +  (l2 == null ? 0 : l2.val) + over;
        over = 0;
        if(sum >= 10){
        	sum = sum - 10;
        	over = 1;
        }
        ListNode node = new ListNode(sum);
        node.next = addTwoNumbers(l1 == null ? null : l1.next, l2 == null ? null : l2.next, over);
        return node;
	}
	
	//19. Remove Nth Node From End of List
	
	
	
	
}
