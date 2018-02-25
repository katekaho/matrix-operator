//-----------------------------------------------------------------------------
// Sparse.java
// Takes in user input for Matrices and performs various matrix operations
// Kate Miller
//-----------------------------------------------------------------------------

import java.util.Scanner;
import java.io.*;

class Sparse {

	public static void main(String[] args) {

		// Checks for correct number of arguments
		if(args.length != 2) {
			System.err.println("Please enter 2 args, file input and output");
			System.exit(1);
		}

		Scanner in = null;
		try { // Open file args[0]
			in = new Scanner(new File(args[0]));
		}
		catch(FileNotFoundException e) {
			System.err.println(e.getMessage());
			System.err.println("File not found");
			System.exit(1);
		}

		PrintWriter out = null;
		try { // Create new file
			out = new PrintWriter(new FileWriter(args[1]));
		}
		catch(Exception e)
		{
			System.err.println("Unable to create file");
			System.exit(1);
		}

		int row, column;
		double data;

		// Take in first line, split, then assign to n, a, and b
		String line = in.nextLine()+" ";
		String[] token = null;
		token = line.split("\\s+");

		int n = Integer.valueOf(token[0]);
		int a = Integer.valueOf(token[1]);
		int b = Integer.valueOf(token[2]);

		// Create two Matrices of size n
		Matrix A = new Matrix(n);
		Matrix B = new Matrix(n);

		// Since next line blank, do nothing with it
		line = in.nextLine();

		int lineNum = 2;
		// Take in all the matrix entries for A 
		while(lineNum < a+2) {
			line = in.nextLine()+" ";
			token = line.split("\\s+");

			row = Integer.valueOf(token[0]);
			column = Integer.valueOf(token[1]);
			data = Double.valueOf(token[2]);

			if(lineNum >= 2) {
				A.changeEntry(row, column, data);
			}
			lineNum++;
		}

		// Another blank line separating two Matrices, do nothing with it
		line = in.nextLine();
		lineNum++;

		// Take in all the matrix entries for B
		while(in.hasNextLine()) {
			line = in.nextLine()+" ";
			token = line.split("\\s+");

			row = Integer.valueOf(token[0]);
			column = Integer.valueOf(token[1]);
			data = Double.valueOf(token[2]);

			if(lineNum > a+2 && lineNum <= 3+a+b) {
				B.changeEntry(row, column, data);
			}
			lineNum++;
		}

		// Print Matrices and perform operations

		out.println("A has "+A.getNNZ()+" non-zero entries:");
		out.println(A.toString()+"\n");

		out.println("B has "+B.getNNZ()+" non-zero entries:");
		out.println(B.toString()+"\n");

		Matrix C = A.scalarMult(1.5);
		out.println("(1.5)*A ="+"\n"+C.toString()+"\n");

		C = A.add(B);
		out.println("A+B ="+"\n"+C.toString()+"\n");

		C = A.add(A);
		out.println("A+A ="+"\n"+C.toString()+"\n");

		C = B.sub(A);
		out.println("B-A ="+"\n"+C.toString()+"\n");

		C = A.sub(A);
		out.println("A-A ="+"\n"+C.toString()+"\n");

		C = A.transpose();
		out.println("Transpose(A) ="+"\n"+C.toString()+"\n");

		C = A.mult(B);
		out.println("A*B ="+"\n"+C.toString()+"\n");

		C = B.mult(B);
		out.println("B*B ="+"\n"+C.toString()+"\n");
		
		out.close();
	}
}