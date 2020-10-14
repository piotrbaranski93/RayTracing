package ray1.surface;

import ray1.IntersectionRecord;
import ray1.Ray;
import egl.math.Vector3;
import egl.math.Vector3d;
import ray1.accel.BboxUtils;

/**
 * Represents a sphere as a center and a radius.
 *
 * @author ags
 */
public class Sphere extends Surface {
  
  /** The center of the sphere. */
  protected final Vector3 center = new Vector3();
  public void setCenter(Vector3 center) { this.center.set(center); }
  public Vector3 getCenter() {return this.center.clone();}
  
  /** The radius of the sphere. */
  protected float radius = 1.0f;
  public void setRadius(float radius) { this.radius = radius; }
  public float getRadius() {return this.radius;}
  
  protected final double M_2PI = 2 * Math.PI;
  
  public Sphere() { }
  
  /**
   * Tests this surface for intersection with ray. If an intersection is found
   * record is filled out with the information about the intersection and the
   * method returns true. It returns false otherwise and the information in
   * outRecord is not modified.
   *
   * @param outRecord the output IntersectionRecord
   * @param ray the ray to intersect
   * @return true if the surface intersects the ray
   */
  public boolean intersect(IntersectionRecord outRecord, Ray rayIn) {
	  	// TODO#Ray Task 2: fill in this function.
	  	Vector3d rayOrigin = rayIn.origin;
	  	Vector3d rayDirection = rayIn.direction;
	  	double t1 = 0;
	  	double t2 = 0;
	  	double t;
	  	double y;
	  	
	  	//calculations
	  	Vector3d temp = new Vector3d(this.center.x - rayOrigin.x, this.center.y - rayOrigin.y, this.center.z - rayOrigin.z);
	  	t = temp.dot(rayIn.direction);
	  	
	  	//outRecord.
	    // If there was an intersection, fill out the intersection record

	  	outRecord.t = t;
	  	outRecord.location.x = rayDirection.x;
	    
	    return true;
  }
  
  /**
   * Compute Bounding Box for sphere
   * */
  public void computeBoundingBox() {
	  BboxUtils.sphereBBox(this);
  }
  
  /**
   * @see Object#toString()
   */
  public String toString() {
    return "sphere " + center + " " + radius + " " + shader + " end";
  }

}