package edu.sru.thangiah.zeus.top;

/**
 * <p>Title: TOP Ellipse</p>
 * <p>Description: Represents a two dimensional elliptical area for a TOP problem</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Slippery Rock University</p>
 * @author David Crissman
 * @version 1.0
 */
public class TOPEllipse {
  public static final long serialVersionUID = 1;
  private double focusAX, focusAY, focusBX, focusBY;               //Coordinates of the two foci of the ellipse
  private double sumDistance;                                      //Sum of the distances from the two foci
  private double centerX, centerY;                                 //Coordinates of the ellipse's center
  private double semiMajorAxis, semiMinorAxis;                     //Half the length of the ellipse's two axes

  //Constructor
  public TOPEllipse(double ax, double ay, double bx, double by, double dist) {
    double fociDistance;                //Distance between the ellipse's two foci

    focusAX = ax;
    focusAY = ay;
    focusBX = bx;
    focusBY = by;
    sumDistance = dist;

    centerX = focusAX + ( (focusBX - focusAX) / 2.0);
    centerY = focusAY + ( (focusBY - focusAY) / 2.0);

    fociDistance = Math.sqrt(Math.pow(focusBX - focusAY, 2) + Math.pow(focusBY - focusAY, 2));
    semiMinorAxis = Math.sqrt(Math.pow( (sumDistance / 2.0), 2) - Math.pow( (fociDistance / 2.0), 2));
    semiMajorAxis = sumDistance / 2.0;
  }

  /**
   * Returns the polar angle (between PI/2 and -PI/2) defined by the two foci of the ellipse
   * @return double
   */
  public double getAngle() {
    double ang = 0;

    if (focusBX == focusAX) {
      if (focusBY >= focusAY) {
	ang = Math.PI / 2.0;
      }
      else {
	ang = Math.PI / -2.0;
      }
    }
    else {
      ang = Math.atan( (focusBY - focusAY) / (focusBX - focusAX));
    }

    return ang;

  }

  /**
   * Returns the x-coordinate of the ellipse's center
   * @return double
   */
  public double getCenterX() {
    return centerX;
  }

  /**
   * Returns the y-coordinate of the ellipse's center
   * @return double
   */
  public double getCenterY() {
    return centerY;
  }

  /**
   * Returns the x-coordinate of the ellipse's first focus
   * @return double
   */
  public double getFocusAX() {
    return focusAX;
  }

  /**
   * Returns the y-coordinate of the ellipse's first focus
   * @return double
   */
  public double getFocusAY() {
    return focusAY;
  }

  /**
   * Returns the x-coordinate of the ellipse's second focus
   * @return double
   */
  public double getFocusBX() {
    return focusBX;
  }

  /**
   * Returns the y-coordinate of the ellipse's second focus
   * @return double
   */
  public double getFocusBY() {
    return focusBY;
  }

  /**
   * Returns the semi-major axis of the ellipse
   * @return double
   */
  public double getSemiMajorAxis() {
    return semiMajorAxis;
  }

  /**
   * Returns the semi-minor axis of the ellipse
   * @return double
   */
  public double getSemiMinorAxis() {
    return semiMinorAxis;
  }

  /**
   * Resizes the ellipse by changing the value of its semi-minor axis
   * @param axis double
   */
  public void setSemiMinorAxis(double axis) {
    if (axis >= 0.0) {
      semiMinorAxis = axis;
    }
    else {
      System.out.println("TOPEllipse::setSemiMinorAxis - Value must be non-negative. Using 0.0 instead");
      semiMinorAxis = 0.0;
    }
    sumDistance = Math.sqrt(Math.pow(2.0 * semiMinorAxis, 2) + Math.pow(focusBX - focusAX, 2) +
                            Math.pow(focusBY - focusAY, 2));
    semiMajorAxis = sumDistance / 2.0;
  }

  /**
   * Resizes the ellipse by changing the sum of the distances from the foci, which defines the edge of the ellipse
   * @param dist double
   */
  public void setDistance(double dist) {
    double fociDistance;

    fociDistance = Math.sqrt(Math.pow(focusBX - focusAY, 2) + Math.pow(focusBY - focusAY, 2));
    if (dist >= fociDistance) {
      sumDistance = dist;
    }
    else {
      System.out.println("TOPEllipse::setDistance - Value must be greater than or equal to the distance between the foci. Using fociDistance instead");
      sumDistance = fociDistance;
    }
    semiMinorAxis = Math.sqrt(Math.pow( (sumDistance / 2.0), 2) - Math.pow( (fociDistance / 2.0), 2));
    semiMajorAxis = sumDistance / 2.0;
  }

  /**
   * Moves the ellipse by the specified distance
   * @param x double
   * @param y double
   */
  public void translate(double x, double y) {
    focusAX += x;
    focusBX += x;
    focusAY += y;
    focusBY += y;
    centerX += x;
    centerY += y;
  }

  /**
   * Rotates the ellipse around a specified center point by a specified number of radians
   * @param angle double
   * @param cx double
   * @param cy double
   */
  public void rotate(double angle, double cx, double cy) {
    double tempX;

    focusAX += cx;
    focusAY += cy;
    tempX =  ((focusAX * Math.cos(angle)) - (focusAY * Math.sin(angle))) - cx;
    focusAY = ((focusAX * Math.sin(angle)) + (focusAY * Math.cos(angle))) - cy;
    focusAX = tempX;

    focusBX += cx;
    focusBY += cy;
    tempX = ((focusBX * Math.cos(angle)) - (focusBY * Math.sin(angle))) - cx;
    focusBY = ((focusBX * Math.sin(angle)) + (focusBY * Math.cos(angle))) - cy;
    focusBX = tempX;

    centerX += cx;
    centerY += cy;
    tempX = ((centerX * Math.cos(angle)) - (centerY * Math.sin(angle))) - cx;
    centerY = ((centerX * Math.sin(angle)) + (centerY * Math.cos(angle))) - cy;
    centerX = tempX;
  }

  /**
   * Returns whether or not the point is within the bounds of the ellipse
   * @param pointX double
   * @param pointY double
   * @return boolean
   */
  public boolean pointInsideEllipse(double pointX, double pointY) {
    double distA, distB;

    distA = Math.sqrt(Math.pow(focusAX - pointX, 2) + Math.pow(focusAY - pointY, 2));
    distB = Math.sqrt(Math.pow(focusBX - pointX, 2) + Math.pow(focusBY - pointY, 2));

    return ((distA + distB) < sumDistance);
  }
}
