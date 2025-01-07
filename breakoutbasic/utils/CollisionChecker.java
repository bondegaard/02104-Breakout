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

    public static boolean checkCollison(Entity entity, Ball ball) {
        double[][] ballEdges = ballCollision(ball, 8);
        double[] entityEdges = {entity.getPosX(), entity.getPosY()};

        for (int i = 0; i < ballEdges.length; i++) {

        }

        return false;
    }

    /**
     *
     * @param Ball ball, int ballEdgesToCheck
     * @return double[ballEdgesToCheck][2] : foreach there are an x and y coordinat at double[i][0] = x, and double[i][1] = y
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

    public static double[][] entityCollision(Entity entity) {
        double[][] entityEdges = new double[4][2];

        entityEdges[0] = new double[]{entity.getPosX() - entity.getWidth() / 2, entity.getPosY() - entity.getHeight() / 2};
        entityEdges[1] = new double[]{entity.getPosX() + entity.getWidth() / 2, entity.getPosY() - entity.getHeight() / 2};
        entityEdges[2] = new double[]{entity.getPosX() - entity.getWidth() / 2, entity.getPosY() + entity.getHeight() / 2};
        entityEdges[3] = new double[]{entity.getPosX() + entity.getWidth() / 2, entity.getPosY() + entity.getHeight() / 2};

        return entityEdges;
    }
}
