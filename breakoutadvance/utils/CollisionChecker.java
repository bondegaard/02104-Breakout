package breakoutadvance.utils;

import breakoutadvance.objects.AbstractEntity;
import breakoutadvance.objects.Ball;
import breakoutadvance.objects.Paddle;
import breakoutadvance.objects.Powerup;

public class CollisionChecker {

    public static boolean checkCollision(AbstractEntity entity1, AbstractEntity entity2) {

        return entity1.getPosX() <= entity2.getPosX() + entity2.getWidth() &&
                entity1.getPosX() + entity1.getWidth() >= entity2.getPosX() &&
                entity1.getPosY() <= entity2.getPosY() + entity2.getWidth() &&
                entity1.getPosY() + entity1.getWidth() >= entity2.getPosY();
    }

    public static boolean checkCollision(Paddle paddle, Powerup powerup) {
        if (paddle.getPosY() + paddle.getHeight() < powerup.getPosY())
            return false;

        return paddle.getPosX() <= powerup.getPosX() + 8 + powerup.getWidth() &&
                paddle.getPosX() + paddle.getWidth() >= powerup.getPosX() + 8 &&
                paddle.getPosY() <= powerup.getPosY() + 8 + powerup.getWidth() &&
                paddle.getPosY() + paddle.getWidth() >= powerup.getPosY() + 8;
    }

    public static EdgeHit checkCollision(AbstractEntity entity, Ball ball) {
        double[][] ballEdges = ballCollision(ball, 8);

        for (double[] ballEdge : ballEdges) {
            // Check if this ballEdge is inside the entity bounds
            if (ballEdge[0] >= entity.getPosX() && ballEdge[0] <= entity.getPosX() + entity.getWidth()
                    && ballEdge[1] >= entity.getPosY() && ballEdge[1] <= entity.getPosY() + entity.getHeight()) {

                // Calculate centers
                double entityCenterX = entity.getPosX() + entity.getWidth() / 2.0;
                double entityCenterY = entity.getPosY() + entity.getHeight() / 2.0;
                double ballCenterX = ball.getPosX() + ball.getWidth() / 2.0;
                double ballCenterY = ball.getPosY() + ball.getWidth() / 2.0;

                // Distances to each edge
                double distanceToTop = ballEdge[1] - entity.getPosY();
                double distanceToBottom = (entity.getPosY() + entity.getHeight()) - ballEdge[1];
                double distanceToLeft = ballEdge[0] - entity.getPosX();
                double distanceToRight = (entity.getPosX() + entity.getWidth()) - ballEdge[0];

                // Determine quadrant
                boolean ballLeft = ballCenterX < entityCenterX;
                boolean ballAbove = ballCenterY < entityCenterY;

                if (ballLeft) {
                    // LEFT side
                    if (ballAbove) {
                        // TOP-LEFT
                        if (distanceToTop < distanceToLeft) {
                            return EdgeHit.YAXIS; // Collided with top edge
                        } else if (distanceToTop == distanceToLeft) {
                            return EdgeHit.BOTH;  // Perfect corner
                        } else {
                            return EdgeHit.XAXIS; // Collided with left edge
                        }
                    } else {
                        // BOTTOM-LEFT
                        if (distanceToBottom < distanceToLeft) {
                            return EdgeHit.YAXIS;
                        } else if (distanceToBottom == distanceToLeft) {
                            return EdgeHit.BOTH;
                        } else {
                            return EdgeHit.XAXIS;
                        }
                    }
                } else {
                    // RIGHT side
                    if (ballAbove) {
                        // TOP-RIGHT
                        if (distanceToTop < distanceToRight) {
                            return EdgeHit.YAXIS;
                        } else if (distanceToTop == distanceToRight) {
                            return EdgeHit.BOTH;
                        } else {
                            return EdgeHit.XAXIS;
                        }
                    } else {
                        // BOTTOM-RIGHT
                        if (distanceToBottom < distanceToRight) {
                            return EdgeHit.YAXIS;
                        } else if (distanceToBottom == distanceToRight) {
                            return EdgeHit.BOTH;
                        } else {
                            return EdgeHit.XAXIS;
                        }
                    }
                }
            }
        }

        return EdgeHit.NONE;
    }


    /**
     * @param Ball ball
     * @param int  ballEdgesToCheck
     * @return double[ballEdgesToCheck][2] : foreach there are an x and y coordinate at double[i][0] = x, and double[i][1] = y
     */
    public static double[][] ballCollision(Ball ball, int ballEdgesToCheck) {
        double[][] ballEdges = new double[ballEdgesToCheck][2];

        for (int i = 0; i < ballEdgesToCheck; i++) {
            double x = ball.getPosX() + ball.getWidth() + ball.getWidth() * Math.cos(((double) i * Math.PI * 2 / ballEdgesToCheck));
            double y = ball.getPosY() + ball.getWidth() + ball.getWidth() * Math.sin(((double) i * Math.PI * 2 / ballEdgesToCheck));

            ballEdges[i][0] = x;
            ballEdges[i][1] = y;
        }

        return ballEdges;
    }
}
