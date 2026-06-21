package xmorph.edu.usu.algebra;

public class And extends Operator {

    public And(Operator left, Operator right) {
        this.child = left;
        this.right = right;
    }
}