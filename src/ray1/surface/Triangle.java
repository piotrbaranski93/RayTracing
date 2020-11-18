package ray1.surface;

import ray1.IntersectionRecord;
import ray1.Ray;
import egl.math.Vector2d;
import egl.math.Vector3;
import egl.math.Vector3d;
import ray1.shader.Shader;
import ray1.OBJFace;
import ray1.accel.BboxUtils;

/**
 * Represents a single triangle, part of a triangle mesh
 *
 * @author ags
 */
public class Triangle extends Surface {
  /** The normal vector of this triangle, if vertex normals are not specified */
  Vector3 norm;
  
  /** The mesh that contains this triangle */
  public Mesh owner;
  
  /** The face that contains this triangle */
  public OBJFace face = null;
  
  double a, b, c, d, e, f; // what are these ???? why are they not vector3d ???
  public Triangle(Mesh owner, OBJFace face, Shader shader) {
    this.owner = owner;
    this.face = face;

    Vector3 v0 = owner.getMesh().getPosition(face,0);
    Vector3 v1 = owner.getMesh().getPosition(face,1);
    Vector3 v2 = owner.getMesh().getPosition(face,2);
    
    if (!face.hasNormals()) {
      Vector3 e0 = new Vector3(), e1 = new Vector3();
      e0.set(v1).sub(v0);
      e1.set(v2).sub(v0);
      norm = new Vector3();
      norm.set(e0).cross(e1).normalize();
    }

    // what is that doing ???
    a = v0.x-v1.x;
    b = v0.y-v1.y;
    c = v0.z-v1.z;
    
    d = v0.x-v2.x;
    e = v0.y-v2.y;
    f = v0.z-v2.z;
    
    this.setShader(shader);
  }

  /**
   * Tests this surface for intersection with ray. If an intersection is found
   * record is filled out with the information about the intersection and the
   * method returns true. It returns false otherwise and the information in
   * outRecord is not modified.
   *
   * @param outRecord the output IntersectionRecord
   * @param rayIn the ray to intersect
   * @return true if the surface intersects the ray
   */
  public boolean intersect(IntersectionRecord outRecord, Ray rayIn) {
    // TODO#Ray Part 1 Task 2: fill in this function.
	//computing t
	//double a = a.x - b.x;
	//double b = a.y - b.y;
	//double c = a.z - b.z;
	//********************************************************
	//Local variables
	//********************************************************
	Double A, Aa, Ab, Ac, abs_alpha, abs_beta, abs_gamma;
	Vector3d p = new Vector3d();
	Vector3d a = new Vector3d(owner.getMesh().getPosition(face, 0));
	Vector3d b = new Vector3d(owner.getMesh().getPosition(face, 1));
	Vector3d c = new Vector3d(owner.getMesh().getPosition(face, 2));
	Vector3d n = b.clone().sub(a).cross(c.clone().sub(a));
	double dn = rayIn.direction.clone().dot(n);
	double t = a.clone().sub(rayIn.origin).dot(n)/dn;
	rayIn.evaluate(p, t);
	A = n.len() / 2;
	Aa = p.clone().sub(b).cross(c.clone().sub(b)).len() / 2;
	Ab = p.clone().sub(c).cross(a.clone().sub(c)).len() / 2;
	Ac = p.clone().sub(a).cross(b.clone().sub(a)).len() / 2;
	abs_alpha = Aa/A;
	abs_beta = Ab/A;
	abs_gamma = Ac/A;
	//********************************************************
	//Checking for intersection
	//********************************************************
	if(dn == 0)
	{
		return false;
	}
	//#1 scenario
	if(t < rayIn.start || t > rayIn.end)
	{
		return false;
	}
	if(abs_alpha + abs_beta + abs_gamma > 1.0000000001) {
		return false;
	}
	outRecord.surface = this;
	outRecord.t = t;
	rayIn.evaluate(outRecord.location, outRecord.t);
	if (norm != null) {
		outRecord.normal.set(norm);
	} else {
		Vector3d nA = new Vector3d(owner.getMesh().getNormal(face, 0 ));
		Vector3d nB = new Vector3d(owner.getMesh().getNormal(face, 1 ));
		Vector3d nC = new Vector3d(owner.getMesh().getNormal(face, 2 ));
		outRecord.normal.set(nA.clone().mul(abs_alpha).addMultiple(abs_beta, nB).addMultiple(abs_beta, nB).addMultiple(abs_gamma, nC).normalize());
	}
	if(!owner.getMesh().uvs.isEmpty()) {
		Vector2d tA = new Vector2d(owner.getMesh().getUV(face, 0));
		Vector2d tB = new Vector2d(owner.getMesh().getUV(face, 1));
		Vector2d tC = new Vector2d(owner.getMesh().getUV(face, 2));
		outRecord.texCoords.set(tA.clone().mul(abs_alpha).addMultiple(abs_beta, tB).addMultiple(abs_gamma, tC));
	}
	return true;
	
  }
  
  public void computeBoundingBox(){
	  BboxUtils.triangleBBox(this);
  }

  /**
   * @see Object#toString()
   */
  public String toString() {
    return "Triangle ";
  }
}