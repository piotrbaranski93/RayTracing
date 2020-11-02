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
	  double xSphere, ySphere, zSphere;
	  double a,b,c;
	  double discriminant;
	  Vector3d t0,t1;
	  Vector3d numerator;
	  Vector3d denominator;
	  
	  //a = Math.pow(rayIn.direction.x - rayIn.origin.x,2) + Math.pow(rayIn.direction.y - rayIn.origin.y,2) + Math.pow(rayIn.direction.z - rayIn.origin.z,2);
	  //b = 2 * ((rayIn.direction.x-rayIn.origin.x) * (rayIn.origin.x-this.center.x) + (rayIn.direction.y-rayIn.origin.y) * (rayIn.origin.y-this.center.y)+ (rayIn.direction.z-rayIn.origin.z) * (rayIn.origin.z-this.center.z));
	  
	  a = rayIn.direction.dot(rayIn.direction);
	  b = 2 * ((rayIn.direction.x-rayIn.origin.x) * (rayIn.origin.x-this.center.x) + (rayIn.direction.y-rayIn.origin.y) * (rayIn.origin.y-this.center.y)+ (rayIn.direction.z-rayIn.origin.z) * (rayIn.origin.z-this.center.z));
	  c = Math.pow((rayIn.origin.x - this.center.x),2) +  Math.pow((rayIn.origin.y - this.center.y),2) +  Math.pow((rayIn.origin.z - this.center.z),2) - Math.pow(this.radius, 2);
	  discriminant = Math.pow(b, 2) - (4*a*c);
	  
	  if (discriminant < 0)
	  {
		  return false;
	  }
	  else if(discriminant == 0)
	  {
		  return true;
	  }
	  else
	  {
		  Vector3d d = rayIn.direction;
		  Vector3d e = rayIn.origin;
		  // -d * (e-c)
		  Vector3d partequation1 = d.mul(-1);
		  partequation1 = d.mul(e.sub(this.center));
		  // d*(e-c)]^2-(d*d)[(e-c)*(e-c)-R^2
		  Vector3d partequation2temp = d.mul(e.sub(this.center)); 
		  partequation2temp.x = partequation2temp.x * partequation2temp.x; 
		  partequation2temp.y = partequation2temp.y * partequation2temp.y;
		  partequation2temp.z = partequation2temp.z * partequation2temp.z;
		  Vector3d partequation3temp = d.mul(d).mul(e.sub(this.center).mul(e.sub(this.center).sub(this.radius*this.radius))); 
		  Vector3d partequation2 = partequation2temp.sub(partequation3temp);
		  partequation2.x = Math.sqrt(partequation2.x);
		  partequation2.y = Math.sqrt(partequation2.y);
		  partequation2.z = Math.sqrt(partequation2.z);
		  //t0
		  numerator = partequation1.add(partequation2);
		  denominator = d.mul(d);
		  t0 = numerator.div(denominator);
		  //t1
		  numerator = partequation1.sub(partequation2);
		  denominator = d.mul(d);
		  t0 = numerator.div(denominator);
		  outRecord.location.x = t0.x;
		  outRecord.location.y = t0.y;
		  outRecord.location.z = t0.z;
		  return true;
	  }

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