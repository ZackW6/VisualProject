package Canvas.Shapes.PhysicsObjects;

import java.awt.Color;
import java.util.ArrayList;

import Canvas.Shapes.Circle;
import Canvas.Shapes.Square;

public class Particle extends Circle{
    public static ArrayList<Particle> particleList = new ArrayList<Particle>();
    private double mass;
    private Vector2D velocity;
    private Vector2D acceleration;
    public Particle(int X, int Y, int Diameter, Color Color, boolean fill, double mass, double[] initVelocity, double[] initAcceleration) {
        super(X, Y, Diameter, Color, fill);
        this.mass = mass;
        this.velocity = new Vector2D(initVelocity[0], initVelocity[1]);
        this.acceleration = new Vector2D(initAcceleration[0], initAcceleration[1]);
        particleList.add(0,this);
    }

    public void handleBorderCollision(int borderX1, int borderX2, int borderY1, int borderY2, double elasticity){
        if (this.coords.x<=borderX1){
            velocity.x = Math.abs(velocity.x)*elasticity;
            coords.x  = borderX1;//+this.width/2;
        }
        if (this.coords.x+this.width>=borderX2){
            velocity.x = Math.abs(velocity.x)*-elasticity;
            coords.x  = borderX2-this.width;
        }
        if(this.coords.y<=borderY1){
            velocity.y = Math.abs(velocity.y)*elasticity;
            coords.y  = borderY1;
        }
        if(this.coords.y+this.width>=borderY2){
            velocity.y = Math.abs(velocity.y)*-elasticity;
            coords.y  = borderY2-this.width;
        }
    }
    public void handleSquareCollision(int borderX1, int borderX2, int borderY1, int borderY2, double elasticity){
        double dx = (coords.x+width/2) - (borderX1+borderX2)/2;
        double dy = (coords.y+width/2) - (borderY1+borderY2)/2;

        // Check for horizontal dominance
        if (Math.abs(dx) > Math.abs(dy)) {
            if (Math.abs(dx) < (borderX2-borderX1)/2) {
                if (dx > 0){
                    velocity.x = Math.abs(velocity.x)*elasticity;
                    coords.x  = borderX2;
                }else{
                    velocity.x = Math.abs(velocity.x)*-elasticity;
                    coords.x  = borderX1-this.width;
                }
            }
        }
        // Check for vertical dominance
        else {
            if (Math.abs(dy) < (borderY2-borderY1)/2) {
                if (dy > 0){
                    velocity.y = Math.abs(velocity.y)*elasticity;
                    coords.y  = borderY2;
                }else{
                    velocity.y = Math.abs(velocity.y)*-elasticity;
                    coords.y  = borderY1-this.width;
                    
                }
            }
        }
    }
    public void handleSquareCollision(Square square){
        handleSquareCollision((int)square.getCoords().x, (int)square.getCoords().x+(int)square.getWidth(), (int)square.getCoords().y, (int)square.getCoords().y+(int)square.getWidth(), 1);
    }
    public void handleCircleCollision(double elasticity){
        for (int i = findLeastIndex(this.coords.x-width); i < findGreatestIndex(this.coords.x+width*2); i++){
        // for (int i = 0; i < particleList.size(); i++){
            Particle secondParticle = particleList.get(i);
            double radius1 = this.width / 2;
            double radius2 = secondParticle.width / 2;

            // Center positions of each particle
            Vector2D center1 = new Vector2D(this.coords.x + radius1, this.coords.y + radius1);
            Vector2D center2 = new Vector2D(secondParticle.coords.x + radius2, secondParticle.coords.y + radius2);

            // Distance calculation
            double dist = center1.distanceTo(center2);

            if (dist <= radius1 + radius2 && !this.equals(secondParticle)) {
                Vector2D n = center2.subtract(center1);
                double distance = n.magnitude();
                double overlap = 0.5 * (radius1 + radius2 - distance);

                // Normalizing the vector
                n = n.normalize(); // Normalize before computing correction

                Vector2D t = new Vector2D(-n.y, n.x); // Tangent

                double v1n = this.velocity.dot(n);
                double v2n = secondParticle.velocity.dot(n);
                double v1t = this.velocity.dot(t);
                double v2t = secondParticle.velocity.dot(t);

                // Calculate new normal velocities after collision
                double v1nPrime = (v1n * (this.mass - secondParticle.mass) + 2 * secondParticle.mass * v2n) / (this.mass + secondParticle.mass);
                double v2nPrime = (v2n * (secondParticle.mass - this.mass) + 2 * this.mass * v1n) / (this.mass + secondParticle.mass);

                Vector2D v1nPrimeVec = n.multiply(v1nPrime);
                Vector2D v2nPrimeVec = n.multiply(v2nPrime);
                Vector2D v1tPrimeVec = t.multiply(v1t);
                Vector2D v2tPrimeVec = t.multiply(v2t);

                // Update velocities
                this.velocity = v1nPrimeVec.add(v1tPrimeVec).multiply(elasticity);
                secondParticle.velocity = v2nPrimeVec.add(v2tPrimeVec).multiply(elasticity);

                // Positional correction to avoid sinking issues
                if (overlap > 0) {
                    Vector2D correction = n.multiply(overlap);
                    this.coords = this.coords.subtract(correction.multiply(secondParticle.mass / (this.mass + secondParticle.mass)));
                    secondParticle.coords = secondParticle.coords.add(correction.multiply(this.mass / (this.mass + secondParticle.mass)));
                }
            }
        }
    }
    /**
     *  will be removed, only for presentation purposes
     * @param elasticity
     */
    public void handleCircleCollisionOld(double elasticity){
        // for (int i = findLeastIndex(this.coords.x-width); i < findGreatestIndex(this.coords.x+width*2); i++){
        for (int i = 0; i < particleList.size(); i++){
            Particle secondParticle = particleList.get(i);
            double radius1 = this.width / 2;
            double radius2 = secondParticle.width / 2;

            // Center positions of each particle
            Vector2D center1 = new Vector2D(this.coords.x + radius1, this.coords.y + radius1);
            Vector2D center2 = new Vector2D(secondParticle.coords.x + radius2, secondParticle.coords.y + radius2);

            // Distance calculation
            double dist = center1.distanceTo(center2);

            if (dist <= radius1 + radius2 && !this.equals(secondParticle)) {
                Vector2D n = center2.subtract(center1);
                double distance = n.magnitude();
                double overlap = 0.5 * (radius1 + radius2 - distance);

                // Normalizing the vector
                n = n.normalize(); // Normalize before computing correction

                Vector2D t = new Vector2D(-n.y, n.x); // Tangent

                double v1n = this.velocity.dot(n);
                double v2n = secondParticle.velocity.dot(n);
                double v1t = this.velocity.dot(t);
                double v2t = secondParticle.velocity.dot(t);

                // Calculate new normal velocities after collision
                double v1nPrime = (v1n * (this.mass - secondParticle.mass) + 2 * secondParticle.mass * v2n) / (this.mass + secondParticle.mass);
                double v2nPrime = (v2n * (secondParticle.mass - this.mass) + 2 * this.mass * v1n) / (this.mass + secondParticle.mass);

                Vector2D v1nPrimeVec = n.multiply(v1nPrime);
                Vector2D v2nPrimeVec = n.multiply(v2nPrime);
                Vector2D v1tPrimeVec = t.multiply(v1t);
                Vector2D v2tPrimeVec = t.multiply(v2t);

                // Update velocities
                this.velocity = v1nPrimeVec.add(v1tPrimeVec).multiply(elasticity);
                secondParticle.velocity = v2nPrimeVec.add(v2tPrimeVec).multiply(elasticity);

                // Positional correction to avoid sinking issues
                if (overlap > 0) {
                    Vector2D correction = n.multiply(overlap);
                    this.coords = this.coords.subtract(correction.multiply(secondParticle.mass / (this.mass + secondParticle.mass)));
                    secondParticle.coords = secondParticle.coords.add(correction.multiply(this.mass / (this.mass + secondParticle.mass)));
                }
            }
        }
    }

    public static int findLeastIndex(double startX) {
        int left = 0;
        int right = particleList.size() - 1;
        int leastIndex = particleList.size()-1; // Initialize leastIndex to the maximum index

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (particleList.get(mid).coords.x + particleList.get(mid).width >= startX) {
                leastIndex = mid; // Update leastIndex if current element is greater than or equal to startX
                right = mid - 1; // Search left subarray for smaller indices
            } else {
                left = mid + 1; // Search right subarray for greater elements
            }
        }

        return leastIndex;
    }

    // Find the greatest index that is less than or equal to the ending range
    public static int findGreatestIndex(double endX) {
        int left = 0;
        int right = particleList.size() - 1;
        int greatestIndex = 0; // Initialize greatestIndex to the minimum index

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (particleList.get(mid).coords.x <= endX) {
                greatestIndex = mid; // Update greatestIndex if current element is less than or equal to endX
                left = mid + 1; // Search right subarray for greater indices
            } else {
                right = mid - 1; // Search left subarray for smaller elements
            }
        }

        return greatestIndex;
    }

    public static void insertionSort() {
        if (particleList.size() == 0) {
            return;
        }
        for (int i = 1; i < particleList.size(); i++) {
            Particle current = particleList.get(i);
            int j = i - 1;
            while (j >= 0 && current.coords.x+current.width/2 < particleList.get(j).coords.x+particleList.get(j).width/2) {
                particleList.set(j + 1, particleList.get(j));
                j--;
            }
            particleList.set(j + 1, current);
        }
    }

    public void applyMovement(){
        velocity.x+=acceleration.x;
        velocity.y+=acceleration.y;
        this.move(velocity.x,velocity.y);
    }

    public int getIndex(){
        if (particleList.indexOf(this)==-1){
            return 0;
        }
        return particleList.indexOf(this);
    }
    
}
