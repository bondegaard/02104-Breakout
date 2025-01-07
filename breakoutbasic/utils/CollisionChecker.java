package breakoutbasic.utils;

import breakoutbasic.objects.Ball;
import breakoutbasic.objects.Entity;

public class CollisionChecker {

    public static boolean checkCollision(Entity entity1, Entity entity2) {

        if(entity1.getPosX() <= entity2.getPosX() + entity2.getWidth() &&
            entity1.getPosX() + entity1.getWidth() >= entity2.getPosX() &&
            entity1.getPosY() <= entity2.getPosY() + entity2.getWidth() &&
            entity1.getPosY() + entity1.getWidth() >= entity2.getPosY())
        {
            return true;
        }
        return false;
    }

    /**
     *
     * @param the object ball.
     * @return
     */
    public static double[][] ballCollision(Ball ball, int ballEdgesToCheck) {
        double[][] ballEdges = new double[ballEdgesToCheck][2];

        for (int i = 0; i < ballEdgesToCheck; i++) {
            double x = ball.getPosX()+ball.getWidth()*Math.cos(((double) i*Math.PI*2/ballEdgesToCheck));
            double y = ball.getPosY()+ball.getWidth()*Math.sin(((double) i*Math.PI*2/ballEdgesToCheck));

            ballEdges[i][0] = x;
            ballEdges[i][1] = y;
        }

        return ballEdges;
    }
}
