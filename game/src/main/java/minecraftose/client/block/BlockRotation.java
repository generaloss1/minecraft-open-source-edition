package minecraftose.client.block;


import jpize.util.math.matrix.Matrix4f;
import jpize.util.math.vector.Vec3i;

public enum BlockRotation{

    X90 (1, 0, 0),
    Y90 (0, 1, 0),
    Z90 (0, 0, 1);

    private final Vec3i axis;
    private final Matrix4f matrix;

    BlockRotation(int x, int y, int z){
        this.axis = new Vec3i(x, y, z);
        this.matrix = new Matrix4f();

        if(x == 1)
            matrix.setRotationX(90);
        else if(y == 1)
            matrix.setRotationY(90);
        else if(z == 1)
            matrix.setRotationZ(90);
    }

    public Vec3i getAxis(){
        return axis;
    }

    public Matrix4f getMatrix(){
        return matrix;
    }

}
