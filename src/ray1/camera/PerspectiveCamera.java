package ray1.camera;

import ray1.Ray;
import egl.math.Vector3d;

/**
 * Represents a camera with perspective view. For this camera, the view window
 * corresponds to a rectangle on a plane perpendicular to viewDir but at
 * distance projDistance from viewPoint in the direction of viewDir. A ray with
 * its origin at viewPoint going in the direction of viewDir should intersect
 * the center of the image plane. Given u and v, you should compute a point on
 * the rectangle corresponding to (u,v), and create a ray from viewPoint that
 * passes through the computed point.
 */
public class PerspectiveCamera extends Camera {

    protected float projDistance = 1.0f;
    public float getProjDistance() { return projDistance; }
    public void setprojDistance(float projDistance) {
        this.projDistance = projDistance;
    }


    //TODO#Ray Task 1: create necessary new variables/objects here, including an orthonormal basis
    //          formed by three basis vectors and any other helper variables 
    //          if needed.
    public float aspectRatio;
	public Vector3d origin; 
	public Vector3d rayOrigin;
	public Vector3d rayDirection;
	public Vector3d pointOnScreen;
	public Vector3d u;
	public Vector3d v;
	public Vector3d w;
	public double d;
	

    /**
     * Initialize the derived view variables to prepare for using the camera.
     */
    public void init() {
        // TODO#Ray Task 1: Fill in this function.
        // 1) Set the 3 basis vectors in the orthonormal basis,
        // based on viewDir and viewUp
        // 2) Set up the helper variables if needed
    	//calculate d from viewDir
    	this.d = Math.sqrt(Math.pow(viewDir.x,2) + Math.pow(viewDir.y,2) + Math.pow(viewDir.z,2));
    	
    	//get the origin
    	this.origin = new Vector3d(viewPoint.x, viewPoint.y, viewPoint.z);
    	
    	//orthonormal basis
    	this.u = new Vector3d(1,0,0); //orthonormal basis
    	this.v = new Vector3d(0,1,0); //orthonormal basis
    	this.w = new Vector3d(viewDir.x, viewDir.y, -viewDir.z);
		
    	//setting aspect ratio for the screen
    	this.aspectRatio = viewWidth/viewHeight;
        	
    	//Testing
    	System.out.println(origin);

    }

    /**
     * Set outRay to be a ray from the camera through a point in the image.
     *
     * @param outRay The output ray (not normalized)
     * @param inU The u coord of the image point (range [0,1])
     * @param inV The v coord of the image point (range [0,1])
     */
    public void getRay(Ray outRay, float inU, float inV) {
        // TODO#Ray Task 1: Fill in this function.
        // 1) Transform inU so that it lies between [-viewWidth / 2, +viewWidth / 2] 
        //    instead of [0, 1]. Similarly, transform inV so that its range is
        //    [-vieHeight / 2, +viewHeight / 2]
        // 2) Set the origin field of outRay for a perspective camera.
        // 3) Set the direction field of outRay for an perspective camera. This
        //    should depend on your transformed inU and inV and your basis vectors,
        //    as well as the projection distance.

    	//setting origin
    	outRay.origin.x = this.origin.x;
    	outRay.origin.y = this.origin.y;
    	outRay.origin.z = this.origin.z;
    	
    	//setting direction
    	Vector3d direction;
    	double rayOriginU = 0.0f;
    	double rayOriginV = 0.0f;
    	double rayOriginZ = 0.0f;
    	
    	double l = -viewWidth/2;
    	double r = viewWidth/2;
    	
    	double b = -viewHeight/2;
    	double t = viewHeight/2;
    	 
    	double u = (l + (r-l) * (inU + 0.5))/viewWidth;
    	double v = (b + (t-b) * (inV + 0.5))/viewHeight;
    	
    	Vector3d vv = this.v.mul(v);
    	Vector3d uu = this.u.mul(u);
    	Vector3d uuvv = uu.add(vv);
    	
    	direction = new Vector3d(viewPoint.x + uuvv.x,viewPoint.y + uuvv.y,viewPoint.z + uuvv.z);
    	System.out.printn(direction);
    }
}