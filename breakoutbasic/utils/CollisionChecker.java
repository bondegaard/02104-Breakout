package breakoutbasic.utils;

import breakoutbasic.objects.Ball;
import breakoutbasic.objects.Entity;

public class CollisionChecker {

    public static boolean checkCollision(Entity entity1, Entity entity2) {

        return entity1.getPosX() <= entity2.getPosX() + entity2.getWidth() &&
                entity1.getPosX() + entity1.getWidth() >= entity2.getPosX() &&
                entity1.getPosY() <= entity2.getPosY() + entity2.getWidth() &&
                entity1.getPosY() + entity1.getWidth() >= entity2.getPosY();
    }

    public static EdgeHit checkCollison(Entity entity, Ball ball) {
        double[][] ballEdges = ballCollision(ball, 8);

        for (double[] ballEdge : ballEdges) {
            if (ballEdge[0] >= entity.getPosX() && ballEdge[0] <= entity.getPosX() + entity.getWidth()
                    && ballEdge[1] >= entity.getPosY() && ballEdge[1] <= entity.getPosY() + entity.getHeight()) {
                if (Math.abs(ball.getPosX()) - Math.abs(entity.getPosX()) < Math.abs(ball.getPosY()) - Math.abs(entity.getPosY())) {
                    return EdgeHit.XAXIS;
                } else if (Math.abs(ball.getPosX()) - Math.abs(entity.getPosX()) == Math.abs(ball.getPosY()) - Math.abs(entity.getPosY())) {
                    return EdgeHit.BOTH;
                } else {
                    return EdgeHit.YAXIS;
                }
            }
        }

        return EdgeHit.NONE;
    }

    /**
     *
     * @param Ball ball, int ballEdgesToCheck
     * @return double[ballEdgesToCheck][2] : foreach there are an x and y coordinat at double[i][0] = x, and double[i][1] = y
     */
    public static double[][] ballCollision(Ball ball, int ballEdgesToCheck) {
        double[][] ballEdges = new double[ballEdgesToCheck][2];

        for (int i = 0; i < ballEdgesToCheck; i++) {
            double x = ball.getPosX()+ball.getWidth()/2+ball.getWidth()*Math.cos(((double) i*Math.PI*2/ballEdgesToCheck));
            double y = ball.getPosY()+ball.getWidth()/2+ball.getWidth()*Math.sin(((double) i*Math.PI*2/ballEdgesToCheck));

            ballEdges[i][0] = x;
            ballEdges[i][1] = y;
        }

        return ballEdges;
    }
}
