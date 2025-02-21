package minecraftose.test;

import jpize.app.Jpize;
import jpize.app.JpizeApplication;
import jpize.gl.Gl;
import jpize.glfw.input.Key;
import jpize.util.camera.OrthographicCamera;
import jpize.util.math.axisaligned.AARect;
import jpize.util.math.axisaligned.AARectBody;
import jpize.util.math.axisaligned.AARectCollider;
import jpize.util.math.vector.Vec2f;
import jpize.util.mesh.TextureBatch;

public class CollisionTest extends JpizeApplication {

    AARectBody box1 = new AARectBody(new AARect(0, 0, 10, 10), new Vec2f(0, 0));
    AARectBody box2 = new AARectBody(new AARect(0, 0, 10, 10), new Vec2f(20, 20));
    TextureBatch batch = new TextureBatch();
    OrthographicCamera camera = new OrthographicCamera(100, 100);

    public void render() {
        Gl.clearColorBuffer();
        batch.setup(camera);

        Vec2f pos1 = box1.position();
        Vec2f pos2 = box2.position();

        Vec2f movement = new Vec2f(
            Key.A.down() ? -1 : Key.D.down() ? 1 : 0,
            Key.S.down() ? -1 : Key.W.down() ? 1 : 0
        );

        final Vec2f clipped = AARectCollider.clipMovement(movement, box1, box2);
        pos1.add(movement);

        batch.drawRect(pos1.x, pos1.y, 10, 10,  0F, 1F, 0F);
        batch.drawRect(pos2.x, pos2.y, 10, 10,  1F, 0F, 0F);

        System.out.println(box1.overlaps(box2));

        batch.render();
    }



    public static void main(String[] args) {
        Jpize.create(720, 720, "Test").build().setApp(new CollisionTest());
        Jpize.run();
    }

}
