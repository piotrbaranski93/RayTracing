package ray1.camera;

import ray1.Ray;
import egl.math.Vector3d;
import egl.math.Vector3;

public class OrthographicCamera extends Camera {

	//TODO#Ray Task 1: create necessary new variables/objects here, including an orthonormal basis
    //          formed by three basis vectors and any other helper variables 
    //          if needed.
	public float aspectRatio;
	public Vector3d rayOrigin;
	public Vector3d rayDirection;
	public Vector3d u;
	public Vector3d v;
	public Vector3d w;
    /**
     * Initialize the derived view variables to prepare for using the camera.
     */
    public void init() {
    	// TODO#Ray Task 1:  Fill in this function.
        // 1) Set the 3 basis vectors in the orthonormal basis, 
        //    based on viewDir and viewUp
        // 2) Set up the helper variables if needed
    	
    	
    	w = new Vector3d(viewDir.clone().negate().normalize());
    	u = new Vector3d(viewUp).cross(w).normalize();
    	v = w.clone().cross(u).normalize();
    	
    	//local variables
    	Vector3d U;
    	Vector3d W;
    	
    	//calculate d from viewDir
    	//this.d = Math.sqrt(Math.pow(viewDir.x,2) + Math.pow(viewDir.y,2) + Math.pow(viewDir.z,2));
    	
    	//constructing w vector first toward direction of the image
    	//this.w = new Vector3d(viewDir.x, viewDir.y, viewDir.z);
    	
    	//constructing u vector
    	//this.u = new Vector3d(-viewUp.y, viewUp.x, 0);
    	//this.u.normalize();
    	
    	
    	//constructing v vector 
    	U = new Vector3d(this.u.x, this.u.y, this.u.z);
    	W = new Vector3d(this.w.x, this.w.y, this.w.z);
    	//this.v = W.cross(U);
    	//this.v.normalize();
    	
    	//setting aspect ratio for the screen
    	this.aspectRatio = viewWidth/viewHeight;
        
    	//get the origin based on the position of the eye
    	//this.rayOrigin = new Vector3d(viewPoint.x, viewPoint.y, viewPoint.z);

    	// Basis
    	System.out.println("Orthonormal basis");
    	System.out.println(this.w);
    	System.out.println(this.u);
    	System.out.println(this.v);
    	//System.out.println("Distance:" + this.d);
    	//Ray direction
    	//rayDirection = new Vector3d(viewDir.x,viewDir.y,viewDir.z);
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
        // 2) Set the origin field of outRay for an orthographic camera. 
        //    In an orthographic camera, the origin should depend on your transformed
        //    inU and inV and your basis vectors u and v.
        // 3) Set the direction field of outRay for an orthographic camera.
    	Vector3d origin;
    	Vector3d newDirection;
    	Vector3d e = new Vector3d(super.viewPoint.x,super.viewPoint.y,super.viewPoint.z);
    	//double rayOriginU = 0.0f;
    	//double rayOriginV = 0.0f;
    	//double rayOriginZ = 0.0f;
    	
    	//double left = -viewWidth/2;
    	//double right = viewWidth/2;
    	
    	//double bottom = -viewHeight/2;
    	//double top = viewHeight/2;
    	 
    	//double u = left + ((right-left) * (inU + 0.5))/viewWidth;
    	//double v = bottom + ((top-bottom) * (inV + 0.5))/viewHeight;
    	
    	//computing origin
    	//Vector3d vv = this.v.mul(v);
    	//Vector3d uu = this.u.mul(u);
    	//Vector3d uuvv = uu.add(vv);
    	//origin = e.add(uuvv);
    	
    	
    	//setting ray direction
    	//outRay.direction.x = this.w.x;
    	//outRay.direction.y = this.w.y;
    	//outRay.direction.z = this.w.z;
    	
    	//setting ray origin
    	//outRay.origin.x = origin.x;
    	//outRay.origin.y = origin.y;
    	//outRay.origin.z = origin.z;
    	
    	
    	origin = new Vector3d(viewPoint).addMultiple((inU - 0.5) * viewWidth, u).addMultiple((inV - 0.5) * viewHeight, v);
    	newDirection = new Vector3d(viewDir);
    	outRay.set(origin, newDirection);
        

    }

}
