//-----------------------------------------------------------------------------
// List.java
// Doubly linked list with a cursor
// Kate Miller
//-----------------------------------------------------------------------------

class List {

	private class Node {
		
		// Fields
		Object data; // Matrix parameters
		Node next; 
		Node prev;

		// Constructor
		Node(Object data) {
			this.data = data;
			this.next = null;
			this.prev = null;
		}

		// toString: overrides Object’s toString method
		public String toString() {
			return String.valueOf(data);
		}

		public boolean equals(Object x) {
			boolean eq = false;
			Node that;
			if(x instanceof Node) {
				that = (Node) x;
				eq = (this.data==that.data);
			}
			return eq;
		}
	}
	// Fields
	private Node front;
	private Node back;
	private Node cursor;
	private int length; // Length of list
	private int cIndex; // Index of cursor

	// Constructors
	// Creates a new empty list.
	List() {
		front = back = cursor = null;
		length = 0;
		cIndex = -1;
	}

	// Access functions

	// Returns the number of elements in this List.
	int length() {
		return length;
	}
	// If cursor is defined, returns the index of the cursor element,
 	// otherwise returns -1
	int index() {
		if(cursor != null){
			return cIndex;
		}
		return -1;
	}
	// Returns front element. Pre: length()>0
	Object front() {
		if(length() <= 0){
			throw new RuntimeException(
				"List Error: getFront() called on empty List");
		}
		return front.data;
	}
	// Returns back element. Pre: length()>0
	Object back() {
		if(length() <= 0) {
			throw new RuntimeException(
				"Liste Error: back() called on empty List");
		}
		return back.data;
	}
	// Returns cursor element. Pre: length()>0, index()>=0
	Object get() {
		if((length() > 0) && (index() >= 0)) {
			return cursor.data;
		}
		else {
			return -1;
		}
	}

	// equals()
	// Overrides Object's equals() method. Returns true if x is a List storing
	// the same integer sequence as this list, false otherwise.
	public boolean equals(Object x) {
		boolean eq = false;
		List Q;
		Node N, M;

		if(x instanceof List) {
			Q = (List)x;
			N = this.front;
			M = Q.front;
			eq = (this.length==Q.length);
			while( eq && N!=null ){
				eq = N.equals(M);
				N = N.next;
				M = M.next;
			}
		}
		return eq;
	}

	// Manipulation procedures

	// Resets this List to its original empty state
	void clear() {
		front = back = cursor = null;
		cIndex = 0;
		length = 0;
	}

	// If List is non-empty, places the cursor under the front element,
	// otherwise does nothing
	void moveFront() {
		if(length > 0) {
			cIndex = 0;
			cursor = front;
		}
	}

	// If List is non-empty, places the cursor under the back element,
 	// otherwise does nothing.
	void moveBack() {
		if(length > 0) {
			cIndex = length-1;
			cursor = back;
		}
	}

	// If cursor is defined and not at front, moves cursor one step toward
	// front of this List, if cursor is defined and at front, cursor becomes
	// undefined, if cursor is undefined does nothing.
	void movePrev() {
		if (cursor != null && cIndex > 0) {
			cursor = cursor.prev;
			cIndex --;
		}
		else if (cursor != null && cIndex == 0) {
			cursor = null;
			cIndex = -1;
		}
	}

	// If cursor is defined and not at back, moves cursor one step toward
	// back of this List, if cursor is defined and at back, cursor becomes
	// undefined, if cursor is undefined does nothing.
	void moveNext() {
		if (cursor != null && cIndex != length) {
			cursor = cursor.next;
			cIndex++;
		}
		if (cursor != null && cIndex == length) {
			cursor = null;
			cIndex = -1;
		}
	}

	// Insert new element into this List. If List is non-empty,
	// insertion takes place before front element.
	void prepend(Object data) {
		Node N = new Node(data); // Create new node
		if(length == 0) {
			front = back = N; // If empty, set N to front and back
		}
		else { // If not empty, set N to prev of front and make N new front
			front.prev = N;
			N.next = front;
			front = N;
		}
		if(cIndex != -1) { // If cursor defined
			cIndex++;
		}
		length++;
	}

	// Insert new element into this List. If List is non-empty,
	// insertion takes place after back element.
	void append(Object data) {
		Node N = new Node(data); // Create new node
		if(length == 0) {
			front = back = N; // If empty, set N to front and back
		}
		else { // If not empty, set N to next of back and make N new back
			back.next = N;
			N.prev = back;
			back = N;
		}
		length++;
	}

	// Insert new element before cursor.
	// Pre: length()>0, index()>=0
	void insertBefore(Object data) {
		if(length()>0 && index()>=0) {
			// Put in front if first element
			if ((cIndex == 0)) { 
				prepend(data);
			}
			else {
				Node N = new Node(data); // Create new node
				N.prev = cursor.prev;
				N.next = cursor;
				(cursor.prev).next = N;
				cursor.prev = N;
				length++;
				cIndex++;
			}
		}
	}

	// Inserts new element after cursor.
	// Pre: length()>0, index()>=0
	void insertAfter(Object data) {
		if(length()>0 && index()>=0) {
			// Put in back if last element
			if (cIndex == length-1) { 
				append(data);
			}
			else {
				Node N = new Node(data); // Create new node
				N.next = cursor.next;
				N.prev = cursor;
				(N.next).prev = N;
				cursor.next = N;
				length++;
			}
		}
	}

	// Deletes the front element. Pre: length()>0
	void deleteFront() {
		if(length()>0){ 
			if(this.length>1){ // If more than one element
				front = front.next; // Move the front to the next over
				front.prev = null; // Set new fronts previous to null
			}
			else {
				front = back = null; // Only one element, everything becomes null
			}
			if(cIndex == 0) { // If cursor on front, becomes undefined
				cursor = null;
				cIndex = -1;
			}
			else if(cIndex != -1) { // If cursor defined, move index
				cIndex--;
			}
			length--;
		}
	}

	// Deletes the back element. Pre: length()>0
	void deleteBack() {
		if(length()>0){ 
			if(this.length>1){ // If more than one element
				back = back.prev; // Move the back to the next over
				back.next = null; // Set new backs next to null
			}
			else {
				front = back = null; // Only one element, everything becomes null
			}
			if(cIndex == length-1) {// If cursor in back, becomes undefined
				cursor = null;
				cIndex= -1;
			}
			length--;
		}
	}

	// Deletes cursor element, making cursor undefined.
	// Pre: length()>0, index()>=0
	void delete() {
		if(length()>0 && index()>=0) {
			if(cIndex == 0) { // Front of list
				deleteFront();
			}
			else if(cIndex == length-1) { // Back of list
				deleteBack();
			}
			else { // Middle of list
				(cursor.prev).next = cursor.next;
				(cursor.next).prev = cursor.prev;
				length--;
			}
			cIndex = -1;
			cursor = null;
		}
	}

	// Other methods

	// Overrides Object's toString method. Returns a String
 	// representation of this List consisting of a space
	// separated sequence of integers, with front on left.
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Node N = front;
		int count = 0;
		while(N!=null){
			if(count>0){
				sb.append(" ");
			}
			count++;
			sb.append(N.toString());
			N = N.next;
		}
		return new String(sb);
	}

}