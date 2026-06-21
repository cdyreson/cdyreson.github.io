package xmorph.edu.usu.algebra;

public class Or extends Operator {

    public Or(Operator left, Operator right) {
        this.child = left;
        this.right = right;
    }

}