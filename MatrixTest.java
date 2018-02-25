//-----------------------------------------------------------------------------
//  MatrixTest.java
//  A test for the Matrix ADT
//  Kate Miller
//-----------------------------------------------------------------------------

public class MatrixTest{

   public static void main(String[] args){

      Matrix A = new Matrix(3);

      A.changeEntry(1, 1, 1);
      A.changeEntry(1, 2, 2);
      A.changeEntry(1, 3, 3);
      A.changeEntry(2, 1, 4);
      A.changeEntry(2, 2, 5);
      A.changeEntry(2, 3, 6);
      A.changeEntry(3, 1, 7);
      A.changeEntry(3, 2, 8);
      A.changeEntry(3, 3, 9);
      System.out.println(A.toString());

      Matrix B = A.transpose();
      System.out.println(B.toString());

      Matrix C;
      Matrix D;

      A.changeEntry(2, 2, 57);
      A.changeEntry(4, 4, 26);
      A.makeZero();

      A.changeEntry(4, 3, 27);
      A.changeEntry(2, 2, 0);
      System.out.println(A.toString());

      B.changeEntry(4, 3, 27);
      B.changeEntry(4, 4, 26);
      B.changeEntry(4, 4, 0);
      System.out.println(B.toString());

      C = A.copy();
      System.out.println(C.toString());
      System.out.println(C.equals(A));
      
      System.out.println("Size: "+A.getSize()+"\nNonzero: "+A.getNNZ());
      System.out.println(A.equals(B));
      B.changeEntry(1, 1, 1);
      System.out.println(B.toString());
      System.out.println(A.equals(B));

      D = A.transpose();
      System.out.println(D.toString());

      A.makeZero();
      B.makeZero();
      C.makeZero();
      D.makeZero();

      A.changeEntry(1, 1, 2);
      A.changeEntry(1, 2, 3);
      A.changeEntry(2, 3, 43);
      A.changeEntry(2, 2, 31);

      B.changeEntry(1, 1, 10);
      B.changeEntry(1, 2, 4.5);
      
      System.out.println(A.toString());
      System.out.println(B.toString());

      Matrix E = A.mult(B);
      System.out.println(E.toString());

      E = A.scalarMult(1.5);
      System.out.println(E.toString());

      E = A.sub(B);
      System.out.println(E.toString());

      E = A.add(B);
      System.out.println(E.toString());


  }
}


