//-----------------------------------------------------------------------------
// Matrix.java
// Matrix that only holds the non-zero values using an array of lists
// Kate Miller
//-----------------------------------------------------------------------------

class Matrix {

	private class Entry {
		
		// Fields
		int column;
		double value;

		// Constructor
		Entry(int column, double value) {
			this.column = column;
			this.value = value;
		}

		// Checks if two Entries are equal
		public boolean equals(Object x) {
			boolean eq = false;
			Entry that;
			if(x instanceof Entry) {
				that = (Entry) x;
				eq = (this.column==that.column);
				eq = (this.value==that.value);
			}
			return eq;
		}

		// Overrides Object's toString method
		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("("+column+", "+value+")");
			return new String(sb);
		}
	}

	// Fields
	private int size;
	private int nonZero;
	private List[] lists;

	// Constructor

	// Makes a new n x n zero Matrix. pre: n>=1
	Matrix(int n) {
		if(n>=1){
			size = n;
			nonZero = 0;
			lists = new List[n+1];
			// Create lists for each index
			for(int i = 1; i <= n; i++) {
				lists[i] = new List();
			}
		}
	}

	// Access functions

	// Returns n, the number of rows and columns of this Matrix
	int getSize() {
		return size;
	}

	// Returns the number of non-zero entries in this Matrix
	int getNNZ() {
		return nonZero;
	}

	// Overrides Object's equals() method
	public boolean equals(Object x) {
		boolean eq = false;
		Entry A, B;
		Matrix M;
		if(x instanceof Matrix) { // checks if x is matrix object
			M = (Matrix)x;
			// Checks if matrix sizes are same
			eq = (getSize()==M.getSize());
			if(eq){
				// Goes through each list in list array
				for(int i = 1; i <= getSize(); i++) {
					lists[i].moveFront(); // Move cursors to front
					M.lists[i].moveFront();
					// Checks if length of list is same
					if(lists[i].length()!=M.lists[i].length()) {
						eq = false;
						return eq;
					}
					// Goes through two lists simultaneously and compares entries
					while(lists[i].index()!=-1) {
						A = (Entry)lists[i].get();
						B = (Entry)M.lists[i].get();
						eq = A.equals(B);
						if(!eq) { return eq; }
						lists[i].moveNext();
						M.lists[i].moveNext();
					}
				}
				// If gets through entrie Matrix and still true, return true
				return eq;
			}
		}
		return eq;
	}

	// Manipulation procedures

	// Computes vector dot product of two rows and returns it
	private static double dot(List R1, List R2) {
		double sum = 0;
		Entry A, B;
		// Move cursors to front of both lists
		R1.moveFront();
		R2.moveFront();
		while((R1.index() != -1) && (R2.index() != -1)) {
			// Get entries from cursor position
			A = (Entry)R1.get();
			B = (Entry)R2.get();
			// If in same column, multiply values and add to sum
			if(A.column == B.column) {
				sum += (A.value * B.value);
				R1.moveNext();
				R2.moveNext();
			}
			// If columns aren't the same, move cursor of lesser column
			if(A.column < B.column) {
				R1.moveNext();
			}
			if(B.column < A.column) {
				R2.moveNext();
			}
		}
		return sum;
	}

	// Adds two matrix rows and returns list of them added
	List adder(List R1, List R2, boolean equalM) {
		double sum = 0;
		Entry A, B;
		// Move cursors to front of both lists
		R1.moveFront();
		R2.moveFront();
		List L = new List();
		int colA = 0;
		int colB = 0;
		// Keeps going until both cursors become undefined
		// In other words, goes through both lists completely
		while((R1.index() != -1) || (R2.index() != -1)) {
			// Get entries from cursor position
			// If any cursor undefined, set column to 0
			if((R1.index() != -1)) {
				A = (Entry)R1.get();
				colA = A.column;
			}
			else {colA = 0;}
			if((R2.index() != -1)) {
				B = (Entry)R2.get();
				colB = B.column;
			}
			else {colB = 0;}

			// If same column, add values, put in new Entry, then prepend it
			if(colA == colB) {
				A = (Entry)R1.get();
				B = (Entry)R2.get();
				sum = (A.value + B.value);
				Entry C = new Entry(A.column, sum);
				L.prepend(C);
				// If they are the same lists, make sure not to move at same time
				if(!(equalM)){
					R1.moveNext();
				}
				R2.moveNext();				
			}
			else if(colA < colB) {
				if(colA == 0) { // If A undefined, add B to list
					B = (Entry)R2.get();
					L.prepend(B);
					R2.moveNext();
				}
				else { // Put A in list and move cursor
					A = (Entry)R1.get();
					L.prepend(A);
					R1.moveNext();
				}
			}
			else if(colA > colB) {
				if(colB == 0){ // If B undefined, add A to list
					A = (Entry)R1.get();
					L.prepend(A);
					R1.moveNext();
				}
				else { // Put B in list and move cursor
					B = (Entry)R2.get();
					L.prepend(B);
					R2.moveNext();
				}
			}
		}
		return L;
	}


	// Subtracts two matrix rows and returns list of them subtracted
	List subber(List R1, List R2) {
		double total = 0;
		Entry A, B;
		// Move cursors to front of both lists
		R1.moveFront();
		R2.moveFront();
		List L = new List();
		int colA = 0;
		int colB = 0;
		// While at least one of the cursors are defined
		while((R1.index() != -1) || (R2.index() != -1)) {
			// Get entries from cursor position
			// If any cursor undefined, set its column to 0
			if((R1.index() != -1)) {
				A = (Entry)R1.get();
				colA = A.column;
			}
			else {colA = 0;}
			if((R2.index() != -1)) {
				B = (Entry)R2.get();
				colB = B.column;
			}
			else {colB = 0;}

			// If in same column, subtract values
			if(colA == colB) {
				A = (Entry)R1.get();
				B = (Entry)R2.get();
				total = (A.value - B.value);
				Entry C = new Entry(A.column, total);
				L.prepend(C);
				R1.moveNext();
				R2.moveNext();				
			}
			else if(colA < colB) {
				if(colA == 0) { // If A undefined, add B to list
					B = (Entry)R2.get();
					// Do this to subtract from A
					// Have to make new Entry so we don't affect B
					Entry D = new Entry(B.column, B.value*-1);
					L.prepend(D);
					R2.moveNext();
				}
				else { // Put A in list and move cursor
					A = (Entry)R1.get();
					L.prepend(A);
					R1.moveNext();
				}
			}
			else if(colA > colB) {
				if(colB == 0) { // If B undefined, add A to list
					A = (Entry)R1.get();
					L.prepend(A);
					R1.moveNext();
				}
				else { // Put B in list and move cursor
					B = (Entry)R2.get();
					Entry D = new Entry(B.column, B.value*-1);
					L.prepend(D);
					R2.moveNext();
				}
			}
		}
		return L;
	}

	// Sets this Matrix to the zero state
	void makeZero() {
		for(int i = 1; i <= getSize(); i++) {
			while(lists[i].length()!=0) {
				lists[i].deleteBack();
			}
		}
		nonZero = 0;
	}
	
	// Returns a new Matrix having the same entries as this Matrix
	Matrix copy() {
		// Make a new Matrix
		Matrix C = new Matrix(size);
		if(nonZero == 0) {
			return C;
		}
		for(int i = 1; i <= getSize(); i++) {
			lists[i].moveFront();
			while(lists[i].index()!=-1){
				Entry A = (Entry)lists[i].get();
				C.lists[i].append(A);
				lists[i].moveNext();
			}
		}
		C.nonZero = this.nonZero;
		return C;
	}

	// Changes ith row, jth column of this Matrix to x
	// Pre: 1<=i<=getSize(), 1<=j<=getSize()
	void changeEntry(int i, int j, double x) {
		if((1 <= i) && (1 <= j)) {
			if((i <= getSize()) && (j <= getSize())) {
				Entry D;
				// If empty, create new
				if((lists[i].length() == 0) && (x != 0)) {
					D = new Entry(j, x);
					lists[i].append(D);
					nonZero++;
					return;
				}
				lists[i].moveFront();
				// If list is not empty, search for spot
				while(lists[i].index() != -1) {
					Entry E = (Entry)lists[i].get();
					// Checks if number exists in column
					if(j == E.column) {
						if(x == 0){ // Delete existing if x is 0
							lists[i].delete();
							nonZero--;
							return;
						}
						// Overwrite entry
						E.column = j;
						E.value = x;
						return;
					}
					// If we've passed its spot, insert before
					else if((j < E.column) && (x != 0)) {
						D = new Entry(j, x);
						lists[i].insertBefore(D);
						nonZero++;
						return;
					}
					// If it's the last element put it at the end
					else if((j > E.column)) {
						if(lists[i].index() == (lists[i].length())-1) {
							if(x != 0) {
								D = new Entry(j, x);
								lists[i].append(D);
								nonZero++;
								return;
							}
						}
					}
					lists[i].moveNext();
				}

			}
		}
	}
	 
	// returns a new Matrix that is the scalar product of this Matrix with x
	Matrix scalarMult(double x) {
		// Essentially same as copy except multiply value by x before appending
		Matrix C = new Matrix(size);
		for(int i = 1; i <= getSize(); i++) {
			lists[i].moveFront();
			while(lists[i].index()!=-1){
				Entry A = (Entry)lists[i].get();
				// Make new Entry to ensure that A isn't modified
				Entry D = new Entry(A.column, A.value*x);
				C.lists[i].append(D);
				lists[i].moveNext();
			}
			C.nonZero = this.nonZero;
		}
		return C;
	}

	// returns a new Matrix that is the sum of this Matrix with M
	// pre: getSize()==M.getSize()
	Matrix add(Matrix M) {
		if(getSize() != M.getSize()) {
			System.err.println("Invalid Matrix, matrix must be same size");
			System.exit(1);
		}
		Matrix N = new Matrix(size);
		List L;
		boolean equalM = this.equals(M);
		// Call adder for each index in array then put list from adder in Matrix
		for(int i = 1; i <= getSize(); i++) {
			L = adder(lists[i], M.lists[i], equalM);
			L.moveFront();
			while(L.index() != -1) {
				Entry E = (Entry)L.get();
				N.changeEntry(i, E.column, E.value);
				L.moveNext();
			}
			
		}
		return N;
	}

	// returns a new Matrix that is the difference of this Matrix with M
	// pre: getSize()==M.getSize()
	Matrix sub(Matrix M) {
		if(getSize() != M.getSize()) {
			System.err.println("Invalid Matrix, matrix must be same size");
			System.exit(1);
		}
		Matrix N = new Matrix(size);
		List L;
		// Call subber for each index in array then put list from adder in Matrix
		for(int i = 1; i <= getSize(); i++) {
			L = subber(lists[i], M.lists[i]);
			L.moveFront();
			while(L.index() != -1) {
				Entry E = (Entry)L.get();
				N.changeEntry(i, E.column, E.value);
				L.moveNext();
			}
			
		}
		return N;
	}

	// Returns a new Matrix that is the transpose of this Matrix
	Matrix transpose() {
		Matrix C = new Matrix(size);
		if(nonZero == 0){
			return C;
		}
		for(int i = 1; i <= getSize(); i++) {
			lists[i].moveFront();
			int count = 1;
			while(lists[i].index()!=-1) {
				Entry A = (Entry)lists[i].get();
				// Switch row and column
				int row = A.column;
				C.changeEntry(row, i, A.value);
				lists[i].moveNext();
			}
		}
		return C;
	}

	// returns a new Matrix that is the product of this Matrix with M
	// pre: getSize()==M.getSize()
	Matrix mult(Matrix M) {
		if(getSize() != M.getSize()) {
			System.err.println("Invalid Matrix, matrix must be same size");
			System.exit(1);
		}
		Matrix N = new Matrix(size);
		// Transpose M to make multiplying easier
		Matrix T = M.transpose();
		double total = 0;
		// Compute dot product of lists we are comparing and put in new matrix
		for(int i = 1; i <= getSize(); i++) {
			for(int k = 1; k <= getSize(); k++) {
				total = dot(lists[i], T.lists[k]);
				N.changeEntry(i, k, total);
			}
		}
		return N;
	}
	 
	// Other functions
	// overrides Object's toString() method
	// Returns a String representation of this Matrix which
	// states all the non-zero entries of the Matrix
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if(nonZero >= 1) {
			for(int i = 1; i <= getSize(); i++) {
				if(lists[i].length() > 0){
					lists[i].moveFront();
					sb.append(i+": ");
					while(lists[i].index() != -1) {
						Entry E = (Entry)lists[i].get();
						sb.append(E.toString());
						// Ensures no extra space after last entry
						if(lists[i].index() != lists[i].length()-1){
							sb.append(" ");
						}
						lists[i].moveNext();
					}
					sb.append("\n");
				}
			}
		}
		return new String(sb);
	}

}