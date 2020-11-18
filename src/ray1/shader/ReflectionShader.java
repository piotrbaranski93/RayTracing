package ray1.shader;

import egl.math.Color;
import egl.math.Colorf;
import egl.math.Vector2;
import egl.math.Vector3;
import egl.math.Vector3d;
import ray1.IntersectionRecord;
import ray1.Light;
import ray1.Ray;
import ray1.RayTracer;
import ray1.Scene;

public abstract class ReflectionShader extends Shader {

	/** BEDF used by this shader. */
	protected BRDF brdf = null;

	/** Coefficient for mirror reflection. */
	protected final Colorf mirrorCoefficient = new Colorf();
	public void setMirrorCoefficient(Colorf mirrorCoefficient) { this.mirrorCoefficient.set(mirrorCoefficient); }
	public Colorf getMirrorCoefficient() {return new Colorf(mirrorCoefficient);}

	public ReflectionShader() {
		super();
	}

	/**
	 * Evaluate the intensity for a given intersection using the Microfacet shading model.
	 *
	 * @param outIntensity The color returned towards the source of the incoming ray.
	 * @param scene The scene in which the surface exists.
	 * @param ray The ray which intersected the surface.
	 * @param record The intersection record of where the ray intersected the surface.
	 * @param depth The recursion depth.
	 */
	@Override
	public void shade(Colorf outIntensity, Scene scene, Ray ray, IntersectionRecord record, int depth) {
		//********************************************************
		//Local variables
		//********************************************************
		Vector3d incoming = new Vector3d();
		Vector3d outgoing = new Vector3d();
		double cosine_T;
		double r2 = 0.0;	
		Vector3d d_vector;
		Vector3d r_vector;
		Ray rayR_cal;
		Vector3 reflectance;
		Colorf outColor = new Colorf();
		outgoing.set(ray.origin).sub(record.location).normalize();
		Vector3d surfaceNormal = record.normal;
		Vector2 texCoords = new Vector2(record.texCoords);
		Colorf BRDFVal = new Colorf();
		outIntensity.setZero();
		
		//********************************************************
		//Calculations and setting outIntensity
		//********************************************************
		//looping over light in scene
		for (Light l : scene.getLights()) {
			if (isShadowed(scene, l, record)) {
				continue;
			}
			incoming.set(l.position).sub(record.location);
			if (incoming.dot(surfaceNormal) < 0.0) {
				continue;
			}
			r2 = incoming.lenSq();
			incoming.normalize();
			brdf.EvalBRDF(incoming, outgoing, surfaceNormal, texCoords, BRDFVal);
			outIntensity.add(BRDFVal.clone().mul(l.intensity).mul((float)(Math.max(surfaceNormal.dot(incoming),0) / r2 )));
		}
		if (getMirrorCoefficient().equals(new Vector3 (0,0,0))) {
			return;
		}
		d_vector = outgoing.clone().negate();
		r_vector = d_vector.subMultiple(2 *d_vector.dot(surfaceNormal),surfaceNormal);
		rayR_cal = new Ray(record.location, r_vector);
		rayR_cal.makeOffsetRay();
		cosine_T = surfaceNormal.dot(outgoing);
		reflectance = getMirrorCoefficient().addMultiple((float)Math.pow(1-cosine_T, 5), new Colorf(1,1,1).sub(getMirrorCoefficient()));
		RayTracer.shadeRay(outColor, scene, rayR_cal, depth+1);
		outIntensity.add(outColor.mul(reflectance));	
	}

}