import org.omg.CORBA.INTERNAL;

import javax.swing.tree.TreeNode;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;


public class CodingIsFun {
    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
        public TreeNode(int val) {
          this.val = val;
        }
  }
    public static class Element {
        public String str;
        public int time;
        public Element(String str, int time) {
            this.str = str;
            this.time = time;
        }
    }

    public String mostCommonWord(String paragraph, String[] banned) {
       String[] str = paragraph.replaceAll("\\W+" , " ").toLowerCase().split("\\s+");
       Map<String, Integer> freqMap = new HashMap<>();
       Set<String> hashSet = new HashSet<>(Arrays.asList(banned));

       for (String s : str) {
           if (!hashSet.contains(s)) {
               Integer freq = freqMap.get(s);
               if (freq == null) {
                   freqMap.put(s, 1);
               } else {
                   freqMap.put(s, freq + 1);
               }
           }
       }


       return Collections.max(freqMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public int[] prisonAfterNDays(int[] cells, int N) {
        for (int i = 0; i < N; i++) {
            cells = prisonAfterNDays(cells);
        }
        return cells;
    }

    public int[] prisonAfterNDays(int[] cells) {
        for (int i = 1; i < cells.length; i++) {
            if (cells[i] == 0 && cells[i - 1] == 0 && cells[i + 1] == 0) {
                cells[i] = 1;
            } else if (cells[i] == 0 && cells[i - 1] == 1 && cells[i + 1] == 1) {
                cells[i] = 1;
            } else {
                cells[i] = 0;
            }
        }
        return cells;
    }
    //lc #215
    public static int findKthLargest(int[] nums, int k) {
        if (nums.length <= 1) {
            return nums[0];
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 == o2) {
                    return 0;
                }
                return o1 > o2 ? -1 : 1;
            }
        });

        //int[] result = new int[k];
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            pq.add(nums[i]);
        }

        for (int j = 0; j < k; j++) {
            result = pq.poll();
        }
        return result;
    }


    // lc #1 : Two Sum
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int[] result = {0, 0};

        for (int i = 0; i < nums.length; i++) {
            int tar = target - nums[i];

            if (map.containsKey(tar)) {
                result[0] = map.get(tar);
                result[1] = i;
            } else {
                map.put(nums[i], i);
            }
        }

        return result;
    }
    // lc #344
    public static void reverseString(char[] s) {
        int j = s.length - 1;
        for (int i = 0; i < s.length / 2; i++, j--) {
            char tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
        }
    }

    // lc #20
    public static boolean isValid(String s) {
        if (s.isEmpty()) {
            return true;
        }

        Deque<Character> stack = new ArrayDeque<>();
        char[] input = s.toCharArray();

        stack.push(input[0]);

        for (int i = 1; i < input.length; i++) {
            if (input[i] == '}' || input[i] == ']' || input[i] == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                if (input[i] == '}' && stack.peekFirst() != '{') {
                    return false;
                }

                if (input[i] == ']' && stack.peekFirst() != '[') {
                    return false;
                }

                if (input[i] == ')' && stack.peekFirst() != '(') {
                    return false;
                }
                stack.pop();
            } else {
                stack.offerFirst(input[i]);
            }
        }

        if (stack.isEmpty()) {
            return true;
        }
        return false;
    }

    // lc #347
    public static List<Integer> topKFrequent(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> freqMap = new HashMap<>();

        for (int num : nums) {
            Integer freq = freqMap.get(num);

            if (freq == null) {
                freqMap.put(num, 1);
            } else {
                freqMap.put(num, freq + 1);
            }
        }

        //System.out.println(freqMap);

        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>(k, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            if (minHeap.size() < k) {
                minHeap.offer(entry);
            } else if (entry.getValue() > minHeap.peek().getValue()) {
                minHeap.poll();
                minHeap.offer(entry);
            }
        }
        //System.out.println(minHeap);

        for (int i = minHeap.size() - 1; i >= 0; i--) {
            result.add(minHeap.poll().getKey());
        }

        return result;
    }

    // lc #21
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;

        while (l1 != null && l2 != null) {
             if (l1.val > l2.val) {
                 cur.next = l2;
                 l2 = l2.next;
             } else {
                 cur.next = l1;
                 l1 = l1.next;
             }
             cur = cur.next;
        }

        if (l1 != null) {
            cur.next = l1;
        } else {
            cur.next = l2;
        }
        return dummy.next;
    }

    public boolean isCompleted(TreeNode root) {
        // Write your solution here
        if (root == null) {
            return true;
        }

        Queue<TreeNode> que = new LinkedList<TreeNode>();
        boolean flag = false;
        que.offer(root);

        while (!que.isEmpty()) {
            TreeNode cur = que.poll();

            if (cur.left == null) {
                flag = true;
            } else if (flag) {
                return false;
            } else {
                que.offer(cur.left);
            }

            if (cur.right == null) {
                flag = true;
            } else if (flag) {
                return false;
            } else {
                que.offer(cur.right);
            }
        }
        return true;
    }



    public static int[] sort(int[] arr) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        int[] a = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            minHeap.add(arr[i]);
        }

        for (int j = 0; j < arr.length; j++) {
            a[j] = minHeap.poll();
        }

        return a;
    }

    public static int strstr(String large, String small) {
        // Write your solution here
        if (large.length() < small.length()) {
            return -1;
        }
        if (small.isEmpty()) {
            return 0;
        }

        char[] smallChar = small.toCharArray();
        char[] largeChar = large.toCharArray();

        for (int i = 0; i <= largeChar.length - smallChar.length; i++) {
            if (equals(largeChar, smallChar, i)) {
                return i;
            }
        }
        return -1;
    }


    private static boolean equals(char[] large, char[] small, int i) {
        for (int j = 0; j < small.length; j++) {
            if (large[j + i] != small[j]) {
                return false;
            }
        }
        return true;
    }

    public static String longestCommon(String source, String target) {
        // Write your solution here
        int end = 0;
        boolean flag = false;
        for (int i = 0; i < source.length(); i++) {
            if (end <= target.length() - 1 && source.charAt(i) == target.charAt(end)) {
                end++;
                flag = true;
            }
        }
        if (!flag) {
            return "";
        } else {
            return target.substring(0, end);
        }
    }

    public static int maxProfit(int[] prices) {
        if (prices.length <= 1) {
            return 0;
        }
        int max = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < min) {
                min = prices[i];
            } else if (prices[i] - min > max) {
                max = prices[i] - min;
            }
        }
        return max;
    }

    public static int romanToInt(String s) {
        char[] arr = s.toCharArray();
        int[] nums = {1, 5, 10, 50, 100, 500, 1000};
        char[] roms = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
        int[] temp = new int[s.length() + 1];
        int result = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < roms.length; j++) {
                if (arr[i] == roms[j]) {
                    temp[i] = nums[j];
                }
            }
        }

        System.out.println(Arrays.toString(temp));

        for (int k = 0; k < temp.length - 1; k++) {
            if (temp[k] >= temp[k + 1]) {
                result += temp[k];
            } else {
                result += temp[k + 1] - temp[k];
                k++;
            }
        }

        return result;
    }
    public static void printNode(ListNode head) {
        System.out.print("[");
        while (head != null) {
           System.out.print(" " + head.val);
           head = head.next;
        }
        System.out.print(" ]");
    }

    public static int getInt(ListNode head) {
        StringBuilder st = new StringBuilder();

        while (head != null) {
            st.append(head.val);
            head = head.next;
        }

        return Integer.parseInt(st.toString());

    }

    public static ListNode getNode(int a) {
        String temp = Integer.toString(a);
        char[] arr = temp.toCharArray();
        ListNode head = new ListNode(0);

        for (int i = 0; i < arr.length; i++) {
            head = insert(head, Character.getNumericValue(arr[i]));
        }

        return head.next;
    }

    public static ListNode insert(ListNode head, int val) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = head;
        ListNode newNode = new ListNode(val);

        while (cur.next != null) {
            cur = cur.next;
        }

        newNode.next = cur.next;
        cur.next = newNode;

        return dummy.next;
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        l1 = reverse(l1);
        l2 = reverse(l2);

        int a = getInt(l1);
        int b = getInt(l2);

        ListNode result = getNode(a + b);
        result = reverse(result);

        return result;
    }

    public static ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {
           return head;
        }

        ListNode newHead = reverse(head.next);
        head.next.next = head;
        head.next = null;

        return newHead;
    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int j = 0;
        int i = m;

        while (i < nums1.length && j < nums2.length) {
            nums1[i] = nums2[j];
            i++;
            j++;
        }

      //  System.out.println(Arrays.toString(nums1));
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0) {
            return null;
        }

        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        PriorityQueue<ListNode> pq = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                if (o1.val == o2.val) {
                    return 0;
                }
                return o1.val < o2.val ? -1 : 1;
            }
        });

        for (ListNode nodes : lists) {
            if (nodes != null) {
                pq.offer(nodes);
            }
        }

        while (!pq.isEmpty()) {
            cur.next = pq.poll();

            if (cur.next.next != null) {
                pq.offer(cur.next.next);
            }
            cur = cur.next;
        }

        return dummy.next;
    }

    public static ListNode singleMerger(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
       // dummy.next = a;
        ListNode cur = dummy;

        while (a != null && b != null) {
           // System.out.println(1);
            if (a.val < b.val) {
                cur.next = a;
                a = a.next;
            } else {
                cur.next = b;
                b = b.next;
            }
            cur = cur.next;
        }

        if (a != null) {
            cur.next = a;
        } else {
            cur.next = b;
        }

        return dummy.next;
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int j = 1; j < nums.length; j++) {
            int tar = (nums[j - 1] + nums[j]) * -1;
            if (map.containsValue(tar)) {
                ArrayList<Integer> list = new ArrayList<Integer>();
                list.add(j - 1);
                list.add(j);
                list.add(map.get(tar));
                result.add(list);
            }
        }
        return result;
    }

    public static void heapSort(int[] array) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int i = 0; i < array.length; i++) {
            minHeap.offer(array[i]);
        }

        int index = 0;
        while (!minHeap.isEmpty()) {
            array[index++] = minHeap.poll();
        }

    }

    public static int numUniqueEmails(String[] emails) {
        Set<String> set = new HashSet<>();
        for (String email : emails) {
            int a = email.indexOf('@');
            String firstPart = email.substring(0, a);
            String secondPart = email.substring(a, email.length());

            if (firstPart.contains("+")) {
                int breakPoint = firstPart.indexOf("+");
                firstPart = firstPart.substring(0, breakPoint);
            }

            firstPart = firstPart.replaceAll("\\.", "");

            set.add(firstPart + secondPart);
        }

        return set.size();
    }

    public static String licenseKeyFormatting(String S, int K) {
        S = S.toUpperCase();
        S = S.replaceAll("-","");

        StringBuilder sb = new StringBuilder(S);
        int i = S.length() - K;

        while (i > 0) {
            System.out.println(i);
            sb.insert(i, '-');
            i -= K;
        }
        return sb.toString();
    }

    public static int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        int len = 0;
        for (int num : nums) {
            System.out.println("num: " + num);
            int i = Arrays.binarySearch(dp, 0, len, num);
            System.out.println("len: " + len);
            System.out.println("i: " + i);
            if (i < 0) {
                i = -(i + 1);
            }
            dp[i] = num;
            System.out.println(Arrays.toString(dp));
            if (i == len) {
                len++;
            }
            System.out.println("-------------------");
        }
        return len;
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int right = maxDepth(root.right);
        int left = maxDepth(root.left);

        return Math.max(right, left) + 1;
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);

        root.left = right;
        root.right = left;

        return root;
    }

    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            t1 = t2;
            return t2;
        } else if (t2 == null) {
            t2 = t1;
            return t1;
        }
        Deque<TreeNode> a = new ArrayDeque<>();

        TreeNode left = mergeTrees(t1.left, t2.left);
        TreeNode rihgt = mergeTrees(t1.right, t2.right);

        t1.val += t2.val;

        return t1;
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }

        if (p == null || q == null) {
            return false;
        }

        if (p.val != q.val) {
            return false;
        }


        boolean left = isSameTree(p.left, q.left);
        boolean right = isSameTree(p.right, q.right);

        return left && right;
    }


    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode one, TreeNode two) {
        if (root == null) {
            return root;
        }

        if (one.val < root.val && two.val < root.val) {
            return lowestCommonAncestor(root.left, one, two);
        } else if (one.val > root.val && two.val > root.val) {
            return lowestCommonAncestor(root.right, one, two);
        } else {
            return root;
        }
    }


    public int h(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = h(root.left);

        if (left == -1) {
            return -1;
        }

        int right = h(root.right);

        if (right == -1) {
            return -1;
        }

        if (Math.abs(left - right) > 1) {
            return -1;
        }

        return Math.max(left, right) + 1;
    }

    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums.length == 0) {
            return null;
        }

        if (nums.length == 1) {
            return new TreeNode(nums[0]);
        }

        return helper(nums, 0, nums.length - 1);
    }

    public TreeNode helper(int[] nums, int left, int right) {
        int mid = (left + right) / 2 + 1;
        TreeNode root = new TreeNode(max(nums, left, right));

        root.left = helper(nums, left, mid);
        root.right = helper(nums, mid + 1, right);
        return root;
    }

    public int max(int[] nums, int left, int right) {
        int max = 0;
        for (int i = left; i < right; i++) {
            max = Math.max(nums[i], max);
        }
        return max;
    }


    public boolean isValidBST(TreeNode root, int low, int high) {
        if (root == null) {
            return false;
        }

        if (root.right.val < root.val) {
            return false;
        }

        if (root.left.val > root.val) {
            return false;
        }

       return isValidBST(root.left, low, root.val - 1) && isValidBST(root.right, root.val + 1, high);

    }

    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int num = 1;
        TreeNode right = root.right;
        TreeNode left = root.left;

        while (right != null) {
            right = right.right;
            left = left.left;
            num += 1;
        }


        return num + ((left != null)?countNodes(root.right):countNodes(root.left));

    }

    public int[] productExceptSelf(int[] nums) {
        int[] answer = new int[nums.length];
        answer[0] = 1;

        for (int i = 1; i < nums.length; i++) {
            answer[i] = nums[i - 1] * answer[i - 1];
        }

        int rightSide = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            answer[i] *= rightSide;
            rightSide *= nums[i];
        }

        return answer;
    }

    public int[][] kClosest(int[][] points, int K) {
        PriorityQueue<int[]> heap = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                int c1 = o1[0] * o1[0] + o1[1] * o1[1];
                int c2 = o2[0] * o2[0] + o2[1] * o2[1];
                if (c1 == c2) {
                    return 0;
                }
                return  c1 < c2 ? 1 : -1;
            }
        });

        int[][] result = new int[K][2];
        for (int i = 0; i < points.length; i++) {
            heap.offer(points[i]);
            if (heap.size() > K) {
                heap.poll();
            }
        }

        for (int i = K; K > 0; i--) {
            result[i] = heap.poll();
        }


        return result;
    }

    public int numJewelsInStones(String J, String S) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < J.length(); i++) {
            set.add(J.charAt(i));
        }
        char[] str = S.toCharArray();
        int count = 0;

        for (Character c : str) {
            if (set.contains(c)) {
                count++;
               // set.remove(c);
            }
        }

        return count;
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List> map = new HashMap<>();

        for (String str : strs) {
            char[] tmp = str.toCharArray();
            Arrays.sort(tmp);
            String key = Arrays.toString(tmp);

            if (!map.containsKey(key)) {
                map.put(key, new ArrayList());
            } else {
                map.get(key).add(str);
            }
        }

        return new ArrayList(map.values());
    }

    public static List<String> letterCombinations(String digits) {
        List<String> ans = new ArrayList<>();

        if(digits == null || digits.length() == 0) {
            return ans;
        }

        char[][] map = new char[8][];
        map[0]="abc".toCharArray();
        map[1]="def".toCharArray();
        map[2]="ghi".toCharArray();
        map[3]="jkl".toCharArray();
        map[4]="mno".toCharArray();
        map[5]="pqrs".toCharArray();
        map[6]="tuv".toCharArray();
        map[7]="wxyz".toCharArray();

        char[] ca = digits.toCharArray();
        ans.add("");
        for (char c : ca) {
            System.out.println("----------");
            ans = expand(map[c - '2'], ans);
        }
        return ans;
    }

    public static List<String> expand(char[] map, List<String> ans) {
        List<String> next = new ArrayList<String>();
        for (String s : ans) {
            System.out.println("1: " + s);
            for (char c : map) {
                System.out.println("2: " + c);
                next.add(s + c);
                System.out.println(s + c + "\n--------------");
            }
        }
        return next;
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        if (wordDict.isEmpty() || s.isEmpty()) {
            return false;
        }

        if (wordDict.size() == 1 && !s.equals(wordDict.get(0))) {
            return false;
        }

        int visted = 0;

        for (int i = 0; i < wordDict.size() - 1; i++) {
            if (wordDict.get(i).length() > s.length()) {
                break;
            }
            int len = wordDict.get(i).length() - 1;
            int cur = visted + len;
            if (s.charAt(cur + 1) != wordDict.get(i + 1).charAt(0)) {
                return false;
            }
        }
        return true;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] max = new int[nums.length];
        int index = 0;
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            if (!deque.isEmpty() && deque.peekLast() <= i - k) {
                deque.pollFirst();
            }
            deque.offerLast(i);

            if (i > k - 1) {
                max[index++] = nums[deque.peekFirst()];
            }
        }
        return Arrays.copyOfRange(max, 0, index);
    }

    public int[][] merge(int[][] intervals) {
        if (intervals.length == 1) {
            return intervals;
        }

        int[][] ans = new int[intervals.length][2];

        if (intervals.length == 0) {
            return ans;
        }

        if (intervals.length == 2) {
            int[] tmp = new int[2];
            if (intervals[1][0] <= intervals[0][1]) {
                tmp[0] = intervals[0][0];
                tmp[1] = intervals[1][1];

                ans[0] = tmp;
                return Arrays.copyOfRange(ans, 0, 1);
            }
        }

        int index = 0;

        for (int i = 0; i < intervals.length; i++) {
            int[] tmp = new int[2];

            if (intervals[i + 1][0] <= intervals[i][1]) {
                tmp[0] = intervals[i][0];
                tmp[1] = intervals[i + 1][1];
                ans[index++] = tmp;
                i++;
            } else {
                ans[index++] = intervals[i];
            }

            if (i + 1 >= intervals.length - 1) {
                ans[index++] = intervals[i + 1];
                break;
            }

        }

        return Arrays.copyOfRange(ans, 0, index);
    }

    public int missingNumber(int[] nums) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] - nums[i - 1] >= 2) {
                return nums[i] - 1;
            }
        }
        return -1;
    }


    public static void main(String[] args) {

        System.out.println('z'-'a');
        String a = "";
        List<Integer> index = new ArrayList<>();
        index.remove()
        index.re

    }
}
