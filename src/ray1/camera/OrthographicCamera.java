package ray1.camera;

import ray1.Ray;
import egl.math.Vector3d;

public class OrthographicCamera extends Camera {

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
    	// TODO#Ray Task 1:  Fill in this function.
        // 1) Set the 3 basis vectors in the orthonormal basis, 
        //    based on viewDir and viewUp
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
        //System.out.print(d);
    	//System.out.println(origin.x);
        //System.out.println(origin.y);
        //System.out.println(origin.z);
    	
    }

    /**
     * Set outRay to be a ray from the camera through a point in the image.
     *
     * @param outRay The output ray (not normalized)
     * @param inU The u coord of the image point (range [0,1])
     * @param inV The v coord of the image point (range [0,1])
     */
    public void getRay(Ray outRay, float inU, float inV) {
    	double u_temp;
    	double v_temp;
    	Ray temp;
    	u_temp = inU * viewWidth * this.u.x;
    	v_temp = inV * viewHeight * this.v.y;
    	
    	System.out.println(this.origin.x);
        System.out.println(this.origin.y);
        System.out.println(this.origin.z);
    	this.pointOnScreen = new Vector3d(w.x,u_temp,v_temp);
    	outRay = new Ray(this.origin, this.pointOnScreen.sub(viewPoint));
    	//outRay.direction = 
    	
        // TODO#Ray Task 1: Fill in this function.
        // 1) Transform inU so that it lies between [-viewWidth / 2, +viewWidth / 2] 
        //    instead of [0, 1]. Similarly, transform inV so that its range is
        //    [-vieHeight / 2, +viewHeight / 2]
        // 2) Set the origin field of outRay for an orthographic camera. 
        //    In an orthographic camera, the origin should depend on your transformed
        //    inU and inV and your basis vectors u and v.
        // 3) Set the direction field of outRay for an orthographic camera.

        

    }

}
