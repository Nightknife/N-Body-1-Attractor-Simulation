package gui;

import domein.Attractor;
import domein.Particle;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;


public class DrawScreen extends Canvas {

    private GraphicsContext gtx;

    private int amount_particles = 1;
    private int particles_per_klik = 249999;

    private final List<Particle> particles;
    private Attractor attractor;

    public DrawScreen() {
        particles = new ArrayList<>();
        init();
        build();
    }

    private void init() {
        this.setWidth(1920);
        this.setHeight(1000);
        gtx = this.getGraphicsContext2D();
    }
    

    private void build() {
        attractor = new Attractor(this.getWidth() / 2.5, this.getHeight() / 2.5);

        this.setOnMouseDragged(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                attractor.setX(e.getX());
                attractor.setY(e.getY());
            }

        });
        
        gtx.setFill(Color.WHITE);
        gtx.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        this.setOnMouseClicked(e -> {
            //get type of mouse click
            MouseButton button = e.getButton();

            //if left mouse click, change pos of attractor to mouse pos
            if (button == MouseButton.PRIMARY) {
                attractor.setX(e.getX());
                attractor.setY(e.getY());

                //else if rightclick, create particles
            } else if (button == MouseButton.SECONDARY) {
                

                if (amount_particles + particles_per_klik <= 500000) {
                    amount_particles += particles_per_klik;
                    for (int i = 0; i < particles_per_klik; i++) {
                        createParticle(e.getX(), e.getY(), attractor);
                    }
                }

            }
        });
    }

    private void createParticle(double x, double y, Attractor a) {
        Particle p = new Particle(x, y, a);
        particles.add(p);
    }

    public void draw() {
        gtx.setFill(Color.WHITE);
        gtx.fillRect(0, 0, this.getWidth(), this.getHeight());

        particles.forEach(p -> {
            p.draw(gtx);
        });

        attractor.draw(gtx);

        gtx.fillText("Particles: " + amount_particles, 20, 50);
    }

    public void update() {
        particles.forEach(p -> {
            p.update();
        });
    }

}
