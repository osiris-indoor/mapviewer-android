package com.sromku.polygon;

import java.util.ArrayList;
import java.util.List;

/**
 * The 2D polygon. <br>
 *
 * @author Roman Kushnarenko (sromku@gmail.com)
 * @see {@link Builder}
 */
public class Polygon {

    private final BoundingBox mBoundigBox;
    private final List<Line> mSides;

    private Polygon(List<Line> sides, BoundingBox boundingBox) {
        mSides = sides;
        mBoundigBox = boundingBox;
    }

    /**
     * Get the builder of the polygon
     *
     * @return The builder
     */
    public static Builder Builder() {
        return new Builder();
    }

    /**
     * Builder of the polygon
     *
     * @author Roman Kushnarenko (sromku@gmail.com)
     */
    public static class Builder {

        private List<Point> mVertexes = new ArrayList<Point>();
        private List<Line> mSides = new ArrayList<Line>();
        private BoundingBox mBoundingBox = null;

        private boolean mFirstPoint = true;
        private boolean mIsClosed = false;

        /**
         * Add vertex points of the polygon.<br>
         * It is very important to add the vertexes by order, like you were drawing them one by one.
         *
         * @param point The vertex point
         * @return The builder
         */
        public Builder addVertex(Point point) {

            if (mIsClosed) {

                // each hole we start with the new array of vertex points
                mVertexes = new ArrayList<Point>();
                mIsClosed = false;
            }

            updateBoundingBox(point);
            mVertexes.add(point);

            // add line (edge) to the polygon
            if (mVertexes.size() > 1) {
                Line Line = new Line(mVertexes.get(mVertexes.size() - 2), point);
                mSides.add(Line);
            }

            return this;
        }

        /**
         * Close the polygon shape. This will create a new side (edge) from the <b>last</b> vertex point to the <b>first</b> vertex point.
         *
         * @return The builder
         */
        public Builder close() {

            validate();

            // add last Line
            mSides.add(new Line(mVertexes.get(mVertexes.size() - 1), mVertexes.get(0)));
            mIsClosed = true;

            return this;
        }

        /**
         * Build the instance of the polygon shape.
         *
         * @return The polygon
         */
        public Polygon build() {

            validate();

            // in case you forgot to close
            if (!mIsClosed) {

                // add last Line
                mSides.add(new Line(mVertexes.get(mVertexes.size() - 1), mVertexes.get(0)));
            }

            Polygon polygon = new Polygon(mSides, mBoundingBox);
            return polygon;
        }

        /**
         * Update bounding box with a new point.<br>
         *
         * @param point New point
         */
        private void updateBoundingBox(Point point) {

            if (mFirstPoint) {

                mBoundingBox = new BoundingBox();
                mBoundingBox.xMax = point.x;
                mBoundingBox.xMin = point.x;
                mBoundingBox.yMax = point.y;
                mBoundingBox.yMin = point.y;

                mFirstPoint = false;
            } else {
                // set bounding box
                if (point.x > mBoundingBox.xMax) {
                    mBoundingBox.xMax = point.x;
                } else if (point.x < mBoundingBox.xMin) {
                    mBoundingBox.xMin = point.x;
                }
                if (point.y > mBoundingBox.yMax) {
                    mBoundingBox.yMax = point.y;
                } else if (point.y < mBoundingBox.yMin) {
                    mBoundingBox.yMin = point.y;
                }
            }
        }

        private void validate() {
            if (mVertexes.size() < 3) {
                throw new RuntimeException("Polygon must have at least 3 points");
            }
        }
    }

    /**
     * Check if the the given point is inside of the polygon.<br>
     *
     * @param point The point to check
     * @return <code>True</code> if the point is inside the polygon, otherwise return <code>False</code>
     */
    public boolean contains(Point point) {
        if (inBoundingBox(point)) {

            Line ray = createRay(point);
            int intersection = 0;
            for (Line side : mSides) {
                if (intersect(ray, side)) {
                    intersection++;
                }
            }

			/*
             * If the number of intersections is odd, then the point is inside the polygon
			 */
            if (intersection % 2 == 1) {
                return true;
            }
        }
        return false;
    }

    public List<Line> getSides() {
        return mSides;
    }

    /**
     * By given ray and one side of the polygon, check if both lines intersect.
     *
     * @param ray
     * @param side
     * @return <code>True</code> if both lines intersect, otherwise return <code>False</code>
     */
    private boolean intersect(Line ray, Line side) {

        Point intersectPoint = null;

        // if both vectors aren't from the kind of x=1 lines then go into
        if (!ray.isVertical() && !side.isVertical()) {

            // check if both vectors are parallel. If they are parallel then no intersection point will exist
            if (ray.getA() - side.getA() == 0) {
                return false;
            }

            double x = ((side.getB() - ray.getB()) / (ray.getA() - side.getA())); // x = (b2-b1)/(a1-a2)
            double y = side.getA() * x + side.getB(); // y = a2*x+b2
            intersectPoint = new Point(x, y);
        } else if (ray.isVertical() && !side.isVertical()) {

            double x = ray.getStart().x;
            double y = side.getA() * x + side.getB();
            intersectPoint = new Point(x, y);
        } else if (!ray.isVertical() && side.isVertical()) {

            double x = side.getStart().x;
            double y = ray.getA() * x + ray.getB();
            intersectPoint = new Point(x, y);
        } else {
            return false;
        }

        if (side.isInside(intersectPoint) && ray.isInside(intersectPoint)) {
            return true;
        }

        return false;
    }

    /**
     * Create a ray. The ray will be created by given point and on point outside of the polygon.<br>
     * The outside point is calculated automatically.
     *
     * @param point
     * @return
     */
    private Line createRay(Point point) {

        // create outside point
        double epsilon = (mBoundigBox.xMax - mBoundigBox.xMin) / 100f;
        Point outsidePoint = new Point(mBoundigBox.xMin - epsilon, mBoundigBox.yMin);

        Line vector = new Line(outsidePoint, point);
        return vector;
    }

    /**
     * Check if the given point is in bounding box
     *
     * @param point
     * @return <code>True</code> if the point in bounding box, otherwise return <code>False</code>
     */
    private boolean inBoundingBox(Point point) {

        if (point.x < mBoundigBox.xMin || point.x > mBoundigBox.xMax || point.y < mBoundigBox
                .yMin || point.y > mBoundigBox.yMax) {
            return false;
        }
        return true;
    }

    private static class BoundingBox {

        public double xMax = Double.NEGATIVE_INFINITY;
        public double xMin = Double.NEGATIVE_INFINITY;
        public double yMax = Double.NEGATIVE_INFINITY;
        public double yMin = Double.NEGATIVE_INFINITY;
    }
}
