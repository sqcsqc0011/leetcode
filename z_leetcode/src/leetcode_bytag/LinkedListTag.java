package leetcode_bytag;

import java.util.Comparator;
import java.util.PriorityQueue;

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
	//two pointers. fast, slow 先找到到第几个listnode需要删除
	public ListNode removeNthFromEnd(ListNode head, int n) {
		ListNode start = new ListNode(0);
	    ListNode slow = start, fast = start;
	    start.next = head;
	    for(int i = 0; i <= n; i++) {
	    	fast = fast.next;
	    }
	    while(fast != null) {
	    	fast = fast.next;
	    	slow = slow.next;
	    }
	    slow.next = slow.next.next;
		return start.next;
	}
	
	//21. Merge Two Sorted Lists
	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode helper = new ListNode(-1);
        ListNode cur = helper;
        while(l1 != null || l2 != null) {
        	int num1 = l1 == null ? Integer.MAX_VALUE : l1.val;
        	int num2 = l2 == null ? Integer.MAX_VALUE : l2.val;
        	cur.next = new ListNode(Math.min(num1, num2));
        	cur = cur.next;
        	if(num1 <= num2) l1 = l1 == null ? null : l1.next;
        	if(num1 > num2) l2 = l2 == null ? null : l2.next;
        }
        return helper.next;
    }
	
	//23. Merge k Sorted Lists
	//use priorityqueue
	public ListNode mergeKLists(ListNode[] lists) {
		if(lists.length == 0) return null;
		Comparator<ListNode> listNodeComparator = new Comparator<ListNode>(){
			@Override
			public int compare(ListNode n1, ListNode n2) {
				if(n1.val <= n2.val) return -1;
				else return 1;
			}
		};
		PriorityQueue<ListNode> q = new PriorityQueue<ListNode>(lists.length, listNodeComparator);
		for(ListNode node : lists) {
			if(node != null ) q.add(node);
		}
		ListNode res = new ListNode(-1);
		ListNode cur = res;
		while(!q.isEmpty()) {
			ListNode node = q.poll();
			cur.next = new ListNode(node.val);
			cur = cur.next;
			
			node = node.next;
			if(node != null) q.add(node);
		}
		return res.next;
	}
	
	//24. Swap Nodes in Pairs
	//get cur and swap cur.next and cur.next.next
	public ListNode swapPairs(ListNode head) {
        ListNode res = new ListNode(-1);
        res.next = head;
        ListNode cur = res;
        while(cur.next != null && cur.next.next!= null) {
        	ListNode first = cur.next;
        	ListNode second = cur.next.next;
        	first.next = second.next;
        	second.next = first;
        	cur.next = second;
        	cur = cur.next.next;
        }
        return res.next;
    }
	
	//25. Reverse Nodes in k-Group
	//finde begin and end, reverse nodes from begin to end
	public ListNode reverseKGroup(ListNode head, int k) {
	    if (head==null || head.next ==null || k==1)
	    	return head;
	    ListNode dummyhead = new ListNode(-1);
	    dummyhead.next = head;
	    ListNode begin = dummyhead;
	    int i = 0;
	    while (head != null){
	    	i++;
	    	if (i%k == 0){
	    		begin = reverse(begin, head.next);
	    		head = begin.next;
	    	} else {
	    		head = head.next;
	    	}
	    }
	    return dummyhead.next;
	    
	}
	//reverse from begin to end
	public ListNode reverse(ListNode begin, ListNode end){
		ListNode cur = begin.next, pre = begin, next = cur.next;
		ListNode res = cur;
		while(cur != end) {
			next = cur.next;
			cur.next = pre;
			pre = cur;
			cur = next;
		}
		//begin.next = pre, get sorted listnodes
		begin.next = pre;
		res.next = cur;
		return res;
	}
	
	//61. Rotate List
	//two pointers
	public ListNode rotateRight(ListNode head, int k) {
		if (head == null || head.next == null) return head;
	    ListNode dummy = new ListNode(0);
	    dummy.next = head;
	    ListNode fast = dummy, slow = dummy;

	    int i;
	    for (i = 0; fast.next != null; i++)//Get the total length 
	    	fast = fast.next;
	    
	    for (int j = i - k%i;j > 0; j--) //Get the i-n%i th node
	    	slow = slow.next;
	    
	    fast.next = dummy.next; //Do the rotation
	    dummy.next = slow.next;
	    slow.next = null;
	    
	    return dummy.next;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
