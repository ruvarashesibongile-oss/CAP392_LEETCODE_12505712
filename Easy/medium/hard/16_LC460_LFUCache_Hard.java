

class LFUCache {

    class Node {
        int key, value, freq;
        Node prev, next;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.freq = 1;
        }
    }

    class DoublyLinkedList {
        Node head, tail;
        int size;

        DoublyLinkedList() {
            head = new Node(0, 0);
            tail = new Node(0, 0);

            head.next = tail;
            tail.prev = head;
            size = 0;
        }

        void add(Node node) {
            node.next = head.next;
            node.prev = head;

            head.next.prev = node;
            head.next = node;

            size++;
        }

        void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;

            size--;
        }

        Node removeLast() {
            if (size == 0) {
                return null;
            }

            Node node = tail.prev;
            remove(node);

            return node;
        }
    }

    private int capacity;
    private int minFreq;

    private Map<Integer, Node> nodeMap;
    private Map<Integer, DoublyLinkedList> freqMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;

        nodeMap = new HashMap<>();
        freqMap = new HashMap<>();
    }

    private void updateFrequency(Node node) {

        int oldFreq = node.freq;

        DoublyLinkedList oldList = freqMap.get(oldFreq);
        oldList.remove(node);

        if (oldFreq == minFreq && oldList.size == 0) {
            minFreq++;
        }

        node.freq++;

        DoublyLinkedList newList = freqMap.get(node.freq);

        if (newList == null) {
            newList = new DoublyLinkedList();
            freqMap.put(node.freq, newList);
        }

        newList.add(node);
    }

    public int get(int key) {

        if (!nodeMap.containsKey(key)) {
            return -1;
        }

        Node node = nodeMap.get(key);

        updateFrequency(node);

        return node.value;
    }

    public void put(int key, int value) {

        if (capacity == 0) {
            return;
        }

        if (nodeMap.containsKey(key)) {

            Node node = nodeMap.get(key);

            node.value = value;
            updateFrequency(node);

            return;
        }

        if (nodeMap.size() == capacity) {

            DoublyLinkedList list = freqMap.get(minFreq);

            Node removed = list.removeLast();

            nodeMap.remove(removed.key);
        }

        Node newNode = new Node(key, value);

        nodeMap.put(key, newNode);

        DoublyLinkedList list = freqMap.get(1);

        if (list == null) {
            list = new DoublyLinkedList();
            freqMap.put(1, list);
        }

        list.add(newNode);

        minFreq = 1;
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
