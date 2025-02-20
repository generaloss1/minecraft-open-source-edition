package minecraftose.test;

import jpize.app.Jpize;
import jpize.app.JpizeApplication;
import jpize.gl.Gl;
import jpize.glfw.input.Key;
import jpize.glfw.input.MouseBtn;
import jpize.util.math.vector.Vec2f;
import jpize.util.mesh.TextureBatch;
import jpize.util.time.TickGenerator;
import jpize.util.time.Tickable;
import minecraftose.client.time.ClientGameTime;
import minecraftose.client.time.var.TickFloat;
import minecraftose.main.time.GameTime;

public class LerpTest extends JpizeApplication implements Tickable {

    public static final int SIZE = 70;

    TickGenerator tickGen = new TickGenerator(GameTime.TICKS_PER_SECOND);
    ClientGameTime time = new ClientGameTime();

    TextureBatch batch = new TextureBatch();

    TickFloat x = new TickFloat();
    TickFloat y = new TickFloat();
    Vec2f velocity = new Vec2f();

    public void init(){
        tickGen.startAsync(this);
    }

    public void tick(){
        time.tick();

        velocity.mul(0.95);

        float speed = 5F;
        if(Key.W.pressed()) velocity.y += speed;
        if(Key.A.pressed()) velocity.x -= speed;
        if(Key.S.pressed()) velocity.y -= speed;
        if(Key.D.pressed()) velocity.x += speed;
        if(MouseBtn.LEFT.pressed()){
            velocity.add(
                    (Jpize.getX() - x.value()) / 10,
                    (Jpize.getY() - y.value()) / 10
            );
        }


        if(x.value() + velocity.x < 0){
            x.set(0);
            velocity.x = 0;
        }else if(x.value() + velocity.x >= Jpize.getWidth() - SIZE){
            x.set(Jpize.getWidth() - 1 - SIZE);
            velocity.x = 0;
        }else{
            x.add(velocity.x);
        }

        if(y.value() + velocity.y < 0){
            y.set(0);
            velocity.y = 0;
        }else if(y.value() + velocity.y >= Jpize.getHeight() - SIZE){
            y.set(Jpize.getHeight() - 1 - SIZE);
            velocity.y = 0;
        }else{
            y.add(velocity.y);
        }
    }

    public void render(){
        Gl.clearColorBuffer();
        batch.setup();

        float t = time.getTickLerpFactor();
        batch.drawRect(x.value(), y.value(), SIZE, SIZE, 0.4, 0, 0, 1);
        batch.drawRect(x.getLerp(t), y.getLerp(t), SIZE, SIZE, 0, 1, 0, 0.75);

        batch.render();
    }

    public void update(){
        if(Key.ESCAPE.down())
            Jpize.exit();
        System.out.println(Jpize.getX());
    }

}
